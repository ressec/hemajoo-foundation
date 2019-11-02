/*
 * (C) Copyright Hemajoo Systems Inc.  2019 - All Rights Reserved
 * -----------------------------------------------------------------------------------------------
 * All information contained herein is, and remains the property of
 * Hemajoo Inc. and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to Hemajoo Inc. and its
 * suppliers and may be covered by U.S. and Foreign Patents, patents
 * in process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained from
 * Hemajoo Systems Inc.
 * -----------------------------------------------------------------------------------------------
 */
package com.ressec.hemajoo.foundation.common.entity.keyable;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.ressec.hemajoo.foundation.common.entity.keyable.exception.KeyException;
import com.ressec.hemajoo.foundation.common.entity.keyable.exception.KeyManagerException;
import lombok.NonNull;
import lombok.Synchronized;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * A singleton manager responsible to manage keys.
 * <br><br>
 * The key manager has the following responsibilities:<br>
 * - register keyable entities and key fields annotated with the {@link PrimaryKey}
 * and/or {@link AlternateKey} annotations<br>
 * - provide services to manage the registered keys<br>
 * - provide services to manage the keyable entities<br>
 * - provide service to retrieve keyable entities based on keys
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public final class KeyManager
{
    /**
     * Key manager (unique) instance.
     */
    private static KeyManager instance = new KeyManager();

    /**
     * Collection of keyable entities grouped by: keyable entity type (keyable class), then by key type (key class)
     * then by key name and then by key value.
     */
    private Map<Class<? extends IKeyable>, Map<Class<?>, Map<String, Multimap<Object, IKeyable>>>> entities = new HashMap<>();

    /**
     * Collection storing the latest key value for keys with property 'auto' set to true.
     * Keyable class, then key type, then key name, then value of the latest key.
     */
    private Map<Class<? extends IKeyable>, Map<Class<?>, Map<String, Object>>> values = new HashMap<>();

    /**
     * Temporary collection of keys used when registering a keyable entity.
     */
    private Map<String, Annotation> keys = new HashMap<>();

    /**
     * Avoid creating directly key manager instance!
     */
    private KeyManager()
    {
        initialize();
    }

    /**
     * Returns the (unique) instance of the key manager.
     * @return {@link KeyManager} instance.
     */
    public static KeyManager getInstance()
    {
        return instance;
    }

    /**
     * Key manager initialization.
     */
    private void initialize()
    {
        // Empty.
    }

    /**
     * Key manager shutdown.
     */
    public void shutdown()
    {
        // Empty.
    }

    /**
     * Registers the given keyable entity and all its keys against the key manager.
     * @param keyable Keyable entity.
     */
    public final void register(final @NonNull IKeyable keyable)
    {
        keys.clear();

        // Register all keys of this keyable entity.
        Field[] fields = keyable.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            // Check if the field is annotated as a primary key ?
            PrimaryKey primary = field.getAnnotation(PrimaryKey.class);
            if (primary != null)
            {
                checkKey(primary, field, keyable);
                addKey(primary, field, keyable);
            }
            else
            {
                // Check if the field is annotated as an alternate key ?
                AlternateKey alternate = field.getAnnotation(AlternateKey.class);
                if (alternate != null)
                {
                    checkKey(alternate, field, keyable);
                    addKey(alternate, field, keyable);
                }
            }
        }

        checkPrimaryKey(keyable);
        checkForKeyDuplicate(keyable);

        registerKeyable(keyable);
    }

    /**
     * Unregisters the given keyable entity (and all its keys).
     * @param keyable Keyable entity.
     */
    public final void unregister(final @NonNull IKeyable keyable)
    {
        Field field;

        for (Annotation annotation : keyable.getAnnotationKeys())
        {
            field = getKeyField(keyable, annotation);
            if (field != null)
            {
                unregisterKey(keyable, field, annotation);
            }
        }
    }

    /**
     * Unregisters all keys of given a key type and a given keyable type.
     * @param keyableClass Keyable class.
     * @param keyType Key type.
     */
    public final void unregisterKeysByKeyType(final @NonNull Class<? extends IKeyable> keyableClass, final @NonNull Class<?> keyType)
    {
        entities.get(keyableClass).remove(keyType);

        try
        {
            values.get(keyableClass).remove(keyType);
        }
        catch (NullPointerException e)
        {
            // Do nothing ... it means no latest value!
        }
    }

    /**
     * Unregisters all keys and of all keyables of a given keyable type.
     * @param keyableClass Keyable type.
     */
    public final void unregisterKeysByKeyableType(final @NonNull Class<? extends IKeyable> keyableClass)
    {
        // Remove the keys.
        entities.remove(keyableClass);

        try
        {
            values.remove(keyableClass);
        }
        catch (NullPointerException e)
        {
            // Do nothing ... it means no latest value!
        }
    }

    /**
     * Unregisters a key given its name.<br>
     * This service will unregister all keys matching the given key name for all keyable entities of the given type.
     * @param keyableClass Keyable class.
     * @param keyName Key name.
     */
    public final void unregisterKeysByName(final @NonNull Class<? extends IKeyable> keyableClass, final @NonNull String keyName)
    {
        Class<?> keyType = getKeyTypeFor(keyableClass, keyName);

        entities.get(keyableClass).get(keyType).remove(keyName);

        try
        {
            values.get(keyableClass).get(keyType).remove(keyName);
        }
        catch (NullPointerException e)
        {
            // Do nothing ... it means no latest value!
        }
    }

    /**
     * Unregisters a specific key of a given keyable entity.
     * @param keyable Keyable entity.
     * @param field Field holding the key value.
     * @param annotation Annotation of the key.
     */
    private void unregisterKey(final @NonNull IKeyable keyable, final @NonNull Field field, final @NonNull Annotation annotation)
    {
        Object value;
        String name = annotation instanceof PrimaryKey ? ((PrimaryKey) annotation).name() : ((AlternateKey) annotation).name();
        Class<?> type = field.getType();

        try
        {
            field.setAccessible(true);
            value = field.get(keyable);
        }
        catch (IllegalAccessException e)
        {
            throw new KeyException(String.format(
                    "Cannot unregister key with name: %s, of type: %s, declared on keyable entity: '%s' due to: '%s'",
                    name,
                    field.getType().getName(),
                    keyable.getClass().getName(),
                    e.getMessage()));
        }
        finally
        {
            field.setAccessible(false);
        }

        Multimap<Object, IKeyable> map = entities.get(keyable.getClass()).get(type).get(name);
        if (map != null && map.containsKey(value))
        {
            map.remove(value, keyable);
        }
    }

    /**
     * Returns the field annotated with the given annotation.
     * @param keyable Keyable.
     * @param annotation Annotation.
     * @return Field annotated with the given annotation.
     */
    private Field getKeyField(final @NonNull IKeyable keyable, final @NonNull Annotation annotation)
    {
        Field[] fields = keyable.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            if (annotation instanceof PrimaryKey)
            {
                PrimaryKey reference = (PrimaryKey) annotation;
                PrimaryKey primary = field.getAnnotation(PrimaryKey.class);
                if (primary != null && primary.name().equals(reference.name()))
                {
                    return field;
                }
            }

            if (annotation instanceof AlternateKey)
            {
                AlternateKey reference = (AlternateKey) annotation;
                AlternateKey alternate = field.getAnnotation(AlternateKey.class);
                if (alternate != null && alternate.name().equals(reference.name()))
                {
                    return field;
                }
            }
        }

        return null;
    }

    /**
     * Registers the keys of a keyable entity against the key manager.
     * @param keyable Keyable entity.
     */
    @Synchronized
    private void registerKeyable(final @NonNull IKeyable keyable)
    {
        // Register all keys of this keyable entity.
        Field[] fields = keyable.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            PrimaryKey primary = field.getAnnotation(PrimaryKey.class);
            if (primary != null)
            {
                registerKey(primary, field, keyable);
            }
            else
            {
                AlternateKey alternate = field.getAnnotation(AlternateKey.class);
                if (alternate != null)
                {
                    registerKey(alternate, field, keyable);
                }
            }
        }
    }

    /**
     * Checks that the given key can be registered by the key manager.
     * @param key Key.
     * @param field Annotated field.
     * @param keyable Keyable.
     */
    private void checkKey(final @NonNull Annotation key, final @NonNull Field field, final @NonNull IKeyable keyable)
    {
        validateTypeOfKey(key, field, keyable);
        validateMandatoryKey(key, field, keyable);
        validateAutoKey(key, field, keyable);
        validateValueOfKey(key, field, keyable);
    }

    /**
     * Validates the type of the key.
     * @param key Key.
     * @param field Annotated field.
     * @param keyable Keyable.
     */
    private void validateTypeOfKey(final @NonNull Annotation key, final @NonNull Field field, final @NonNull IKeyable keyable)
    {
        String name = key instanceof PrimaryKey ? ((PrimaryKey) key).name() : ((AlternateKey) key).name();

        if (!isAuthorizedType(field))
        {
            String message = String.format("Key with name: '%s', of type: '%s' for keyable: '%s' has an invalid type!",
                    name,
                    field.getType().getName(),
                    keyable.getClass().getName());

            log.error(message);

            throw new KeyException(message);
        }
    }

    /**
     * Validates the key when it has the 'mandatory' property set to true.
     * @param key Key.
     * @param field Annotated field.
     * @param keyable Keyable.
     */
    private void validateMandatoryKey(final @NonNull Annotation key, final @NonNull Field field, final @NonNull IKeyable keyable)
    {
        String name = key instanceof PrimaryKey ? ((PrimaryKey) key).name() : ((AlternateKey) key).name();
        boolean mandatory = key instanceof PrimaryKey || ((AlternateKey) key).mandatory();
        boolean auto = key instanceof PrimaryKey ? ((PrimaryKey) key).auto() : ((AlternateKey) key).auto();

        Object value = getFieldValue(key, field, keyable);

        if (mandatory && !auto && value == null)
        {
            String message = String.format(
                    "Cannot initialize mandatory key with name: %s, of type: %s, declared on keyable entity: '%s' because key value is not set!",
                    name,
                    field.getType().getName(),
                    keyable.getClass().getName());

            log.error(message);

            throw new KeyException(message);
        }
    }

    /**
     * Validates the key when it has the 'auto' property set to true.
     * @param key Annotation of the key.
     * @param field Annotated field.
     * @param keyable Keyable holding the annotated field.
     */
    private void validateAutoKey(final @NonNull Annotation key, final @NonNull Field field, final @NonNull IKeyable keyable)
    {
        if (ClassUtils.isPrimitiveOrWrapper(field.getType()))
        {
            validateAutoPrimitiveOrWrapperKey(key, field, keyable);
        }
        else
        {
            validateAutoStandardKey(key, field, keyable);
        }
    }

    /**
     * Validates an auto key value when type is primitive or wrapper.
     * @param key Annotation of the key.
     * @param field Annotated field.
     * @param keyable Keyable holding the annotated field.
     */
    private void validateAutoPrimitiveOrWrapperKey(final @NonNull Annotation key, final @NonNull Field field, final @NonNull IKeyable keyable)
    {
        final String ILLEGAL_ACCESS_EXCEPTION_MESSAGE = "Cannot initialize key with name: %s, of type: %s, declared on keyable entity: '%s' due to: %s";
        final String AUTO_KEY_VALUE_PROVIDED_MESSAGE = "Cannot initialize (auto) key with name: %s, of type: %s, declared on keyable entity: '%s' because key value is provided!";

        String name = key instanceof PrimaryKey ? ((PrimaryKey) key).name() : ((AlternateKey) key).name();
        boolean auto = key instanceof PrimaryKey ? ((PrimaryKey) key).auto() : ((AlternateKey) key).auto();

        if (auto)
        {
            try
            {
                if (field.getType() == Byte.class || field.getType() == byte.class)
                {
                    if ((Byte) field.get(keyable) != 0)
                    {
                        String message = String.format(
                                AUTO_KEY_VALUE_PROVIDED_MESSAGE,
                                name,
                                field.getType().getName(),
                                keyable.getClass().getName());

                        log.error(message);

                        throw new KeyException(message);
                    }
                }
                else if (field.getType() == Short.class || field.getType() == short.class)
                {
                    if ((Short) field.get(keyable) != 0)
                    {
                        String message = String.format(
                                AUTO_KEY_VALUE_PROVIDED_MESSAGE,
                                name,
                                field.getType().getName(),
                                keyable.getClass().getName());

                        log.error(message);

                        throw new KeyException(message);
                    }
                }
                else if (field.getType() == Integer.class || field.getType() == int.class)
                {
                    if ((Integer) field.get(keyable) != 0)
                    {
                        String message = String.format(
                                AUTO_KEY_VALUE_PROVIDED_MESSAGE,
                                name,
                                field.getType().getName(),
                                keyable.getClass().getName());

                        log.error(message);

                        throw new KeyException(message);
                    }
                }
                else if (field.getType() == Long.class || field.getType() == long.class)
                {
                    if ((Long) field.get(keyable) != 0)
                    {
                        String message = String.format(
                                AUTO_KEY_VALUE_PROVIDED_MESSAGE,
                                name,
                                field.getType().getName(),
                                keyable.getClass().getName());

                        log.error(message);

                        throw new KeyException(message);
                    }
                }
            }
            catch (IllegalAccessException e)
            {
                String message = String.format(
                        ILLEGAL_ACCESS_EXCEPTION_MESSAGE,
                        name,
                        field.getType().getName(),
                        keyable.getClass().getName(),
                        e.getMessage());

                log.error(message);

                throw new KeyException(message);
            }
        }
    }

    /**
     * @param key
     * @param field
     * @param keyable
     */
    private void validateAutoStandardKey(final @NonNull Annotation key, final @NonNull Field field, final @NonNull IKeyable keyable)
    {

    }

    /**
     * Validates the value of the key.
     * @param key Key.
     * @param field Annotated field.
     * @param keyable Keyable.
     */
    private void validateValueOfKey(final @NonNull Annotation key, final @NonNull Field field, final @NonNull IKeyable keyable)
    {
        String name = key instanceof PrimaryKey ? ((PrimaryKey) key).name() : ((AlternateKey) key).name();
        boolean auto = key instanceof PrimaryKey ? ((PrimaryKey) key).auto() : ((AlternateKey) key).auto();

        try
        {
            field.setAccessible(true);

            if (field.getType() == String.class)
            {
                if (auto)
                {
                    field.setAccessible(false);
                    String message = String.format(
                            "Cannot initialize key with name: %s, of type: %s, on keyable entity: '%s'. A key of type String cannot have the 'auto' property set to true!",
                            name,
                            field.getType().getName(),
                            keyable.getClass().getName());

                    log.error(message);

                    throw new KeyException(message);
                }
            }

            field.get(keyable);
        }
        catch (IllegalAccessException e)
        {
            field.setAccessible(false);
            String message = String.format(
                    "Cannot initialize key with name: %s, of type: %s, on keyable entity: '%s', due to: %s",
                    name,
                    field.getType().getName(),
                    keyable.getClass().getName(),
                    e.getMessage());

            log.error(message);

            throw new KeyException(message);
        }
    }

    /**
     * Adds a primary key to the collection of defined keys for a keyable entity.
     * @param key Key.
     * @param field Field annotated as a primary key.
     * @param keyable Keyable entity holding the annotated field.
     */
    private void addKey(final @NonNull Annotation key, final @NonNull Field field, final @NonNull IKeyable keyable)
    {
        String name = key instanceof PrimaryKey ? ((PrimaryKey) key).name() : ((AlternateKey) key).name();

        if (keys.containsKey(name))
        {
            String message = String.format(
                    "Key with name: '%s' of type: '%s' for keyable entity: '%s' already exist with the same name!",
                    name,
                    field.getType().getName(),
                    keyable.getClass().getName());

            log.error(message);

            throw new KeyException(message);
        }
        else
        {
            keys.put(name, key);
        }
    }

    /**
     * Registers a key of a keyable entity.
     * @param key Key.
     * @param field Field annotated as a primary key.
     * @param keyable Keyable entity holding the annotated field.
     */
    private void registerKey(final @NonNull Annotation key, final @NonNull Field field, final @NonNull IKeyable keyable)
    {
        Map<Class<?>, Map<String, Multimap<Object, IKeyable>>> map1;
        Map<String, Multimap<Object, IKeyable>> map2;
        Multimap<Object, IKeyable> map3;

        String name = key instanceof PrimaryKey ? ((PrimaryKey) key).name() : ((AlternateKey) key).name();
        boolean unique = key instanceof PrimaryKey || ((AlternateKey) key).unique();
        boolean auto = key instanceof PrimaryKey ? ((PrimaryKey) key).auto() : ((AlternateKey) key).auto();
        boolean mandatory = key instanceof PrimaryKey || ((AlternateKey) key).mandatory();

        Object value = getFieldValue(key, field, keyable);

        if (isAutoKeyValueToBeGenerated(key, field, keyable))
        {
            value = generateNextKeyValue(keyable, field.getType(), name);

            try
            {
                field.setAccessible(true);
                field.set(keyable, value);
            }
            catch (IllegalAccessException e)
            {
                String message = String.format(
                        "Cannot register key with name: '%s' of type: '%s' for keyable entity: '%s', due to: '%s'",
                        name,
                        field.getType().getName(),
                        keyable.getClass().getName(),
                        e.getMessage());

                log.error(message);

                throw new KeyException(message);
            }
            finally
            {
                field.setAccessible(false);
            }
        }

        map1 = getCollectionByKeyable(keyable);
        map2 = getCollectionByKeyType(map1, field.getType());
        map3 = getCollectionByKeyName(map2, name);

        boolean skip = false;
        if (!mandatory)
        {
            if (field.getType().isPrimitive())
            {
                if (value instanceof Integer && (Integer) value == 0)
                {
                    skip = true;
                }
                else if (value instanceof Long && (Long) value == 0)
                {
                    skip = true;
                }
                else if (value instanceof Byte && (Byte) value == 0)
                {
                    skip = true;
                }
                else if (value instanceof Double && (Double) value == 0.0)
                {
                    skip = true;
                }
                else if (value instanceof Float && (Float) value == 0.0f)
                {
                    skip = true;
                }
            }
            else
            {
                if (value == null)
                {
                    skip = true;
                }
            }
        }

        if (!skip)
        {
            if (unique)
            {
                if (!map3.containsKey(value))
                {
                    map3.put(value, keyable);
                }
                else
                {
                    String message = String.format(
                            "Cannot register key with name: '%s' with value: '%s', of type: '%s' for keyable entity: '%s', because key value is not unique!",
                            name,
                            value,
                            field.getType().getName(),
                            keyable.getClass().getName());

                    log.error(message);

                    throw new KeyException(message);
                }
            }
            else
            {
                map3.put(value, keyable);
            }
        }
    }

    /**
     * Checks if the key value needs to be generated ?
     * @return True if the key value is to be generated, false otherwise.
     */
    private boolean isAutoKeyValueToBeGenerated(final @NonNull Annotation key, final @NonNull Field field, final @NonNull IKeyable keyable)
    {
        boolean auto = key instanceof PrimaryKey ? ((PrimaryKey) key).auto() : ((AlternateKey) key).auto();

        if (auto)
        {
            Object value = getFieldValue(key, field, keyable);

            if (field.getType() == Integer.class || field.getType() == int.class)
            {
                return value == null || (Integer) value == 0;
            }
            else if (field.getType() == Long.class || field.getType() == long.class)
            {
                return value == null || ((Long) value) == 0L;
            }
            else if (field.getType() == Short.class || field.getType() == short.class)
            {
                return value == null || ((Short) value) == 0;
            }
            else if (field.getType() == Byte.class || field.getType() == byte.class)
            {
                return value == null || ((Byte) value) == 0;
            }
            else if (field.getType() == UUID.class)
            {
                return value == null;
            }
        }

        return false;
    }

    private Object getFieldValue(final @NonNull Annotation key, final @NonNull Field field, final @NonNull IKeyable keyable)
    {
        Object value;

        String name = key instanceof PrimaryKey ? ((PrimaryKey) key).name() : ((AlternateKey) key).name();

        field.setAccessible(true);

        try
        {
            value = field.get(keyable);
        }
        catch (IllegalAccessException e)
        {
            String message = String.format(
                    "Cannot register key with name: '%s' of type: '%s' for keyable entity: '%s', due to: '%s'",
                    name,
                    field.getType().getName(),
                    keyable.getClass().getName(),
                    e.getMessage());

            log.error(message);

            throw new KeyException(message);
        }

        return value;
    }

    /**
     * Returns the collection of keyables by keyable type.
     * @param keyable Keyable entity.
     * @return Collection of keyable.
     */
    private Map<Class<?>, Map<String, Multimap<Object, IKeyable>>> getCollectionByKeyable(final @NonNull IKeyable keyable)
    {
        Map<Class<?>, Map<String, Multimap<Object, IKeyable>>> collection;

        collection = entities.computeIfAbsent(keyable.getClass(), k -> new HashMap<>());

        return collection;
    }

    /**
     * Returns the collection of keyables by keyable type.
     * @param keyableClass Keyable entity class.
     * @return Collection of keyable.
     */
    private Map<Class<?>, Map<String, Multimap<Object, IKeyable>>> getCollectionByKeyableClass(final @NonNull Class<? extends IKeyable> keyableClass)
    {
        Map<Class<?>, Map<String, Multimap<Object, IKeyable>>> collection;

        collection = entities.computeIfAbsent(keyableClass, k -> new HashMap<>());

        return collection;
    }

    /**
     * Returns the collection of keyables by key type.
     * @param map Source map.
     * @param type Key type.
     * @return Collection of keyables.
     */
    private Map<String, Multimap<Object, IKeyable>> getCollectionByKeyType(final @NonNull Map<Class<?>, Map<String, Multimap<Object, IKeyable>>> map, final @NonNull Class<?> type)
    {
        Map<String, Multimap<Object, IKeyable>> collection;

        collection = map.computeIfAbsent(type, k -> new HashMap<>());

        return collection;
    }

    /**
     * Returns the collection of keyables by key name.
     * @param map Source map.
     * @param name Key name.
     * @return Collection of keyables.
     */
    private Multimap<Object, IKeyable> getCollectionByKeyName(final @NonNull Map<String, Multimap<Object, IKeyable>> map, final @NonNull String name)
    {
        Multimap<Object, IKeyable> collection;

        collection = map.get(name);
        if (collection == null)
        {
            collection = ArrayListMultimap.create();
            map.put(name, collection);
        }

        return collection;
    }

    /**
     * Checks for key duplicates.
     * @param keyable Keyable entity.
     */
    private void checkForKeyDuplicate(final @NonNull IKeyable keyable)
    {
        // Check at least one key is the primary one.
        boolean found = false;
        for (Annotation key : keys.values())
        {
            if (key instanceof PrimaryKey)
            {
                found = true;
                break;
            }
        }

        if (!found)
        {
            String message = String.format(
                    "Keyable entity: '%s' does not contain a primary key! One of the defined keys must be set as the primary key",
                    keyable.getClass().getName());

            log.error(message);

            throw new KeyException(message);
        }
    }

    /**
     * Checks only one field can be annotated with the primary key annotation.
     * @param keyable Keyable entity.
     */
    private void checkPrimaryKey(final @NonNull IKeyable keyable)
    {
        boolean found = false;
        for (Annotation key : keys.values())
        {
            if (key instanceof PrimaryKey)
            {
                if (!found)
                {
                    found = true;
                }
                else
                {
                    String message = String.format(
                            "Keyable entity: '%s' contains multiple fields annotated as primary keys! Only one field can be annotated as the primary key",
                            keyable.getClass().getName());

                    log.error(message);

                    throw new KeyException(message);
                }
            }
        }
    }

    /**
     * Checks if the given key name exist for the given keyable class ?
     * @param keyableClass Keyable class.
     * @param keyName Key name.
     * @return True if the given key name exist for the given keyable class, false otherwise.
     */
    public final boolean isKeyExist(final @NonNull Class<? extends IKeyable> keyableClass, final @NonNull String keyName)
    {
        // Key Class | Key Name | Key Value | Keyable
        Map<Class<?>, Map<String, Multimap<Object, IKeyable>>> keyables = entities.get(keyableClass);
        if (keyables != null)
        {
            for (Class<?> clazz : keyables.keySet())
            {
                for (String name : keyables.get(clazz).keySet())
                {
                    if (name.equals(keyName))
                    {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * Checks if the given key value exist for the given keyable class, key name and key value ?
     * @param keyableClass Keyable class.
     * @param keyName Key name.
     * @param keyValue Key value.
     * @return True if the given key name exist for the given keyable class, false otherwise.
     */
    public final boolean isKeyValueExist(final @NonNull Class<? extends IKeyable> keyableClass, final @NonNull String keyName, final @NonNull Object keyValue)
    {
        // Key Class | Key Name | Key Value | Keyable
        Map<Class<?>, Map<String, Multimap<Object, IKeyable>>> keyables = entities.get(keyableClass);
        if (keyables != null)
        {
            for (Class<?> clazz : keyables.keySet())
            {
                for (String name : keyables.get(clazz).keySet())
                {
                    if (name.equals(keyName))
                    {
                        for (Object value : keyables.get(clazz).get(name).keySet())
                        {
                            if (value == keyValue)
                            {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    /**
     * Generates the next key value.
     * @param keyable Keyable entity the key refers to.
     * @param type Key type.
     * @param name Key name.
     * @return Next generated key value.
     */
    private Object generateNextKeyValue(final @NonNull IKeyable keyable, final @NonNull Class<?> type, final @NonNull String name)
    {
        if (type == Byte.class || type == byte.class)
        {
            Byte latest = (Byte) getLatestKeyValue(keyable, type, name);

            if (latest == null)
            {
                latest = 1;
            }
            else
            {
                if (latest == Byte.MAX_VALUE)
                {
                    String message = String.format(
                            "Key name: '%s' with type: '%s' for keyable entity: '%s' has reached its limit: '%d', no more value can be generated!",
                            name,
                            type.getName(),
                            keyable.getClass().getName(),
                            Byte.MAX_VALUE);

                    log.error(message);

                    throw new KeyException(message);
                }

                latest = (byte) (latest + 1);
            }

            updateLatestKeyValue(keyable, type, name, latest);

            return latest;
        }
        else if (type == Short.class || type == short.class)
        {
            Short latest = (Short) getLatestKeyValue(keyable, type, name);

            if (latest == null)
            {
                latest = 1;
            }
            else
            {
                if (latest == Short.MAX_VALUE)
                {
                    String message = String.format(
                            "Key name: '%s' with type: '%s' for keyable entity: '%s' has reached its limit: '%d', no more value can be generated!",
                            name,
                            type.getName(),
                            keyable.getClass().getName(),
                            Short.MAX_VALUE);

                    log.error(message);

                    throw new KeyException(message);
                }

                latest = (short) (latest + 1);
            }

            updateLatestKeyValue(keyable, type, name, latest);

            return latest;
        }
        else if (type == Integer.class || type == int.class)
        {
            Integer latest = (Integer) getLatestKeyValue(keyable, type, name);
            if (latest == null)
            {
                latest = 1;
            }
            else
            {
                if (latest == Integer.MAX_VALUE)
                {
                    String message = String.format(
                            "Key name: '%s' with type: '%s' for keyable entity: '%s' has reached its limit: '%d', no more value can be generated!",
                            name,
                            type.getName(),
                            keyable.getClass().getName(),
                            Integer.MAX_VALUE);

                    log.error(message);

                    throw new KeyException(message);
                }

                latest += 1;
            }

            updateLatestKeyValue(keyable, type, name, latest);

            return latest;
        }
        else if (type == Long.class || type == long.class)
        {
            Long latest = (Long) getLatestKeyValue(keyable, type, name);

            if (latest == null)
            {
                latest = 1L;
            }
            else
            {
                if (latest == Long.MAX_VALUE)
                {
                    String message = String.format(
                            "Key name: '%s' with type: '%s' for keyable entity: '%s' has reached its limit: '%d', no more value can be generated!",
                            name,
                            type.getName(),
                            keyable.getClass().getName(),
                            Long.MAX_VALUE);

                    log.error(message);

                    throw new KeyException(message);
                }

                latest += 1;
            }

            updateLatestKeyValue(keyable, type, name, latest);

            return latest;
        }
        else if (type == UUID.class)
        {
            return UUID.randomUUID();
        }

        throw new KeyException("Unsupported key type!");
    }

    /**
     * Retrieves the latest generated key value.
     * @param keyable Keyable entity the key refers to.
     * @param type Key type.
     * @param name Key name.
     * @return Next generated key value.
     */
    private Object getLatestKeyValue(final @NonNull IKeyable keyable, final @NonNull Class<?> type, final @NonNull String name)
    {
        Map<Class<?>, Map<String, Object>> keyTypes = values.get(keyable.getClass());
        if (keyTypes != null)
        {
            Map<String, Object> keyNames = keyTypes.get(type);
            if (keyNames != null)
            {
                return keyNames.get(name);
            }
        }

        return null;
    }

    /**
     * Updates the latest generated key value.
     * @param keyable Keyable entity the key refers to.
     * @param type Key type.
     * @param name Key name.
     * @param value New latest key value.
     */
    private void updateLatestKeyValue(final @NonNull IKeyable keyable, final @NonNull Class<?> type, final @NonNull String name, final @NonNull Object value)
    {
        Map<Class<?>, Map<String, Object>> keyTypes;
        Map<String, Object> keyNames = null;

        keyTypes = values.get(keyable.getClass());
        if (keyTypes == null)
        {
            keyTypes = new HashMap<>();
        }

        keyNames = keyTypes.get(type);
        if (keyNames == null)
        {
            keyNames = new HashMap<>();
        }

        keyNames.put(name, value);
        keyTypes.put(type, keyNames);
        values.put(keyable.getClass(), keyTypes);
    }

    /**
     * Retrieves the type of a key.
     * @param keyableClass Keyable entity class.
     * @param keyName Key name.
     * @return Type of the key.
     * @throws KeyManagerException Thrown in case the type of the key cannot be determined.
     */
    private Class<?> getKeyTypeFor(final @NonNull Class<? extends IKeyable> keyableClass, final @NonNull String keyName)
    {
        Map<String, Multimap<Object, IKeyable>> names;

        Map<Class<?>, Map<String, Multimap<Object, IKeyable>>> types = entities.get(keyableClass);
        if (types != null)
        {
            for (Class<?> type : types.keySet())
            {
                names = types.get(type);
                if (names != null && names.containsKey(keyName))
                {
                    return type;
                }
            }
        }

        String message = String.format(
                "Cannot determine type for key name: '%s' on keyable entity type: '%s'. Are you sure it's a real key?",
                keyName,
                keyableClass);

        log.error(message);

        throw new KeyManagerException(message);
    }

    /**
     * Returns a list of keyables matching the given key name and value.
     * @param keyableClass Keyable class.
     * @param keyName Key name.
     * @param keyValue Key value.
     * @return List of keyables or an empty list if no keyable has been found matching the given criteria.
     */
    public final List<IKeyable> get(final @NonNull Class<? extends IKeyable> keyableClass, final @NonNull String keyName, final @NonNull Object keyValue)
    {
        Class<?> type = null;

        try
        {
            type = getKeyTypeFor(keyableClass, keyName);

            if (type == null)
            {
                log.error(String.format("Cannot retrieve type of key name: '%s', for keyable class: '%s'", keyName, keyableClass));
                return new ArrayList<>();
            }
        }
        catch (KeyManagerException e)
        {
            log.error(e.getMessage());
            return new ArrayList<>();
        }

        return new ArrayList<>(entities.get(keyableClass).get(type).get(keyName).get(keyValue));
    }

    /**
     * Returns a list of keyables matching the given key.
     * @param keyableClass Keyable class.
     * @param key Key.
     * @return List of keyables or an empty list if no keyable has been found matching the given criteria.
     */
    public final List<IKeyable> get(final @NonNull Class<? extends IKeyable> keyableClass, final @NonNull IKey key)
    {
        return (List<IKeyable>) entities.get(keyableClass).get(key.getType()).get(key.getName()).get(key.getValue());
    }

    /**
     * Returns the number of entities stored in the key manager for the given keyable class.
     * @param keyableClass Keyable class.
     * @return Number of entities stored.
     */
    public final int countByKeyableClass(final @NonNull Class<? extends IKeyable> keyableClass)
    {
        PrimaryKey annotation;

        for (Field field : keyableClass.getDeclaredFields())
        {
            annotation = field.getAnnotation(PrimaryKey.class);
            if (annotation != null)
            {
                return getKeyables(keyableClass, field.getType(), annotation.name()).size();
            }
        }

        return 0;
    }

    /**
     * Returns the number of entities stored in the key manager for the given keyable class and key name.
     * @param keyableClass Keyable class.
     * @param keyName Key name.
     * @return Number of entities stored.
     */
    public final int countByKeyName(final @NonNull Class<? extends IKeyable> keyableClass, final @NonNull String keyName)
    {
        Annotation fieldAnnotation;
        Annotation annotation = getAnnotationForKeyName(keyableClass, keyName);

        if (annotation != null)
        {
            for (Field field : keyableClass.getDeclaredFields())
            {
                if (annotation instanceof PrimaryKey)
                {
                    fieldAnnotation = field.getAnnotation(PrimaryKey.class);
                    if (fieldAnnotation != null && ((PrimaryKey) fieldAnnotation).name().equals(keyName))
                    {
                        return getKeyables(keyableClass, field.getType(), keyName).size();
                    }
                }
                else if (annotation instanceof AlternateKey)
                {
                    fieldAnnotation = field.getAnnotation(AlternateKey.class);
                    if (fieldAnnotation != null && ((AlternateKey) fieldAnnotation).name().equals(keyName))
                    {
                        return getKeyables(keyableClass, field.getType(), keyName).size();
                    }
                }
            }
        }

        return 0;
    }

    /**
     * Returns the key annotation for a given keyable class matching the given key name.
     * @param keyableClass Keyable class to query.
     * @param keyName Key name.
     * @return Annotation matching the given key name, null otherwise.
     */
    private Annotation getAnnotationForKeyName(final @NonNull Class<? extends IKeyable> keyableClass, final @NonNull String keyName)
    {
        for (Field field : keyableClass.getDeclaredFields())
        {
            for (Annotation annotation : field.getAnnotations())
            {
                if (annotation instanceof PrimaryKey && ((PrimaryKey) annotation).name().equals(keyName))
                {
                    return annotation;
                }

                if (annotation instanceof AlternateKey && ((AlternateKey) annotation).name().equals(keyName))
                {
                    return annotation;
                }
            }
        }

        return null;
    }

//    /**
//     * Returns the field for a given keyable class annotated with the given key name.
//     * @param keyableClass Keyable class to query.
//     * @param keyName Key name.
//     * @return Field matching the given key name, null otherwise.
//     */
//    private Field getFieldForKeyName(final @NonNull Class<? extends IKeyable> keyableClass, final @NonNull String keyName)
//    {
//        for (Field field : keyableClass.getFields())
//        {
//            for (Annotation annotation : field.getAnnotations())
//            {
//                if (annotation instanceof PrimaryKey && ((PrimaryKey) annotation).name().equals(keyName))
//                {
//                    return field;
//                }
//
//                if (annotation instanceof AlternateKey && ((AlternateKey) annotation).name().equals(keyName))
//                {
//                    return field;
//                }
//            }
//        }
//
//        return null;
//    }

    /**
     * Returns a list of registered keyables matching the given parameters.
     * @param keyableClass Keyable class.
     * @param keyType Key type.
     * @param keyName Key name.
     * @return List of matching keyables or an empty list if no keyable found.
     */
    private List<? extends IKeyable> getKeyables(final @NonNull Class<? extends IKeyable> keyableClass, final @NonNull Class<?> keyType, final @NonNull String keyName)
    {
        Multimap<Object, IKeyable> map = null;

        if (!entities.isEmpty())
        {
            try
            {
                map = entities.get(keyableClass).get(keyType).get(keyName);
            }
            catch (NullPointerException e)
            {
                // Do nothing!
            }
        }

        return map == null ? new ArrayList<>() : new ArrayList<>(map.values());
    }

    /**
     * Returns if the given field (annotated with a key annotation) is of an authorized type?
     * @param field Annotated field.
     * @return True if the field has an authorized type, false otherwise.
     */
    private boolean isAuthorizedType(final @NonNull Field field)
    {
        return isAuthorizedType(field.getType());
    }

    /**
     * Returns if the given field (annotated with a key annotation) is of an authorized type?
     * @param clazz Class.
     * @return True if the class is an authorized type, false otherwise.
     */
    private boolean isAuthorizedType(final @NonNull Class<?> clazz)
    {
        return clazz == Byte.class || clazz == byte.class ||
                clazz == Short.class || clazz == short.class ||
                clazz == Integer.class || clazz == int.class ||
                clazz == Long.class || clazz == long.class ||
                clazz == String.class || clazz == UUID.class;
    }
}
