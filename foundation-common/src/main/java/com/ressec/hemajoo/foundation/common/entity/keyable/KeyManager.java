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
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;

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
    private Map<Class<? extends IKeyable>, Map<Class<?>, Map<String, Multimap<Object, IKeyable>>>> entities =
            new HashMap<>();

    /**
     * Collection storing the latest key value for keys with property 'auto' set to true.
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

                // Check if the field is also annotated as an alternate key ?
                AlternateKey alternate = field.getAnnotation(AlternateKey.class);
                if (alternate != null)
                {
                    throw new KeyException(
                            String.format(
                                    "Field: '%s' of keyable entity: '%s' is already annotated as a primary key and cannot also be declared as an alternate key!",
                                    field.getName(),
                                    keyable.getClass().getName()));
                }
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
            else
            {
                String name = annotation instanceof PrimaryKey ? ((PrimaryKey) annotation).name() : ((AlternateKey) annotation).name();

                throw new KeyException(String.format(
                        "Cannot unregister key with name: %s, on keyable entity: '%s' because no related field found!",
                        name,
                        keyable.getClass().getName()));
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
    }

    /**
     * Unregisters all keys and of all keyables of a given keyable type.
     * @param keyableClass Keyable type.
     */
    public final void unregisterKeysByKeyableType(final @NonNull Class<? extends IKeyable> keyableClass)
    {
        entities.remove(keyableClass);
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
        if (map != null)
        {
            if (map.containsKey(value))
            {
                map.remove(value, keyable);
            }
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
        for (Field element : fields)
        {
            if (annotation instanceof PrimaryKey)
            {
                PrimaryKey reference = (PrimaryKey) annotation;
                PrimaryKey primary = element.getAnnotation(PrimaryKey.class);
                if (primary != null)
                {
                    if (primary.name().equals(reference.name()))
                    {
                        return element;
                    }
                }
            }
            else
            {
                if (annotation instanceof AlternateKey)
                {
                    AlternateKey reference = (AlternateKey) annotation;
                    AlternateKey alternate = element.getAnnotation(AlternateKey.class);
                    if (alternate != null)
                    {
                        if (alternate.name().equals(reference.name()))
                        {
                            return element;
                        }
                    }
                }
            }
        }

        return null;
    }

    /**
     * Registers the keys of a keyable entity against the key manager.
     * @param keyable Keyable entity.
     */
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
        Object value;
        String name = key instanceof PrimaryKey ? ((PrimaryKey) key).name() : ((AlternateKey) key).name();
        boolean mandatory = key instanceof PrimaryKey ? ((PrimaryKey) key).mandatory() : ((AlternateKey) key).mandatory();
        boolean auto = key instanceof PrimaryKey ? ((PrimaryKey) key).auto() : ((AlternateKey) key).auto();

        field.setAccessible(true);

        try
        {
            value = field.get(keyable);
        }
        catch (IllegalAccessException e)
        {
            throw new KeyException(String.format(
                    "Cannot initialize key with name: %s, of type: %s, on keyable entity: '%s', due to: %s",
                    name,
                    field.getType().getName(),
                    keyable.getClass().getName(),
                    e.getMessage()));
        }

        if (mandatory && !auto && value == null)
        {
            throw new KeyException(String.format(
                    "Cannot initialize key with name: %s, of type: %s, declared on keyable entity: '%s' because key value is not set!",
                    name,
                    field.getType().getName(),
                    keyable.getClass().getName()));
        }

        if (auto && value != null)
        {
            throw new KeyException(String.format(
                    "Cannot initialize key with name: %s, of type: %s, declared on keyable entity: '%s' because key is set to 'auto' but key value is provided!",
                    name,
                    field.getType().getName(),
                    keyable.getClass().getName()));
        }

        // Check the type property.
        if (!isFieldTypeAuthorized(field))
        {
            throw new KeyException(
                    String.format("Key with name: '%s', of type: '%s' for keyable: '%s' has an invalid type!",
                            name,
                            field.getType().getName(),
                            keyable.getClass().getName()));
        }

        // Check the mandatory property.
        if (mandatory && !auto)
        {
            try
            {
                if (field.get(keyable) == null)
                {
                    throw new KeyException(
                            String.format(
                                    "The key named: '%s', of type: '%s' for keyable: '%s' is declared as mandatory but its value is null!",
                                    name,
                                    field.getType().getName(),
                                    keyable.getClass().getName()));
                }
            }
            catch (IllegalAccessException e)
            {
                throw new KeyException(String.format(
                        "Cannot initialize key with name: %s, of type: %s, on keyable entity: '%s', due to: %s",
                        name,
                        field.getType().getName(),
                        keyable.getClass().getName(),
                        e.getMessage()));
            }
        }

        // Check the key value is of type String.
        if (field.getType() == String.class)
        {
            // A key of type string cannot be declared with auto = true
            if (auto)
            {
                throw new KeyException(String.format(
                        "Cannot initialize key with name: %s, of type: %s, on keyable entity: '%s'. A key of type String cannot have the 'auto' property set to true!",
                        name,
                        field.getType().getName(),
                        keyable.getClass().getName()));
            }

            try
            {
                String test = (String) field.get(keyable);
            }
            catch (IllegalAccessException e)
            {
                throw new KeyException(String.format(
                        "Cannot initialize key with name: %s, of type: %s, on keyable entity: '%s', due to: %s",
                        name,
                        field.getType().getName(),
                        keyable.getClass().getName(),
                        e.getMessage()));
            }
        }
        else
        {
            // Check the key value is of type Integer.
            if (field.getType() == Integer.class)
            {
                try
                {
                    Integer test = (Integer) field.get(keyable);
                }
                catch (IllegalAccessException e)
                {
                    throw new KeyException(String.format(
                            "Cannot initialize key with name: %s, of type: %s, on keyable entity: '%s', due to: %s",
                            name,
                            field.getType().getName(),
                            keyable.getClass().getName(),
                            e.getMessage()));
                }
            }
            else
            {
                // Check the key value is of type Long.
                if (field.getType() == Long.class)
                {
                    try
                    {
                        Long test = (Long) field.get(keyable);
                    }
                    catch (IllegalAccessException e)
                    {
                        throw new KeyException(String.format(
                                "Cannot initialize key with name: %s, of type: %s, on keyable entity: '%s', due to: %s",
                                name,
                                field.getType().getName(),
                                keyable.getClass().getName(),
                                e.getMessage()));
                    }
                }
                else
                {
                    // Check the key value is of type UUID.
                    if (field.getType() == UUID.class)
                    {
                        try
                        {
                            UUID test = (UUID) field.get(keyable);
                        }
                        catch (IllegalAccessException e)
                        {
                            throw new KeyException(String.format(
                                    "Cannot initialize key with name: %s, of type: %s, on keyable entity: '%s', due to: %s",
                                    name,
                                    field.getType().getName(),
                                    keyable.getClass().getName(),
                                    e.getMessage()));
                        }
                    }
                }
            }
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
            throw new KeyException(
                    String.format(
                            "Key with name: '%s' of type: '%s' for keyable entity: '%s' already exist with the same name!",
                            name,
                            field.getType().getName(),
                            keyable.getClass().getName()));
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
        Object value;
        Map<Class<?>, Map<String, Multimap<Object, IKeyable>>> map1;
        Map<String, Multimap<Object, IKeyable>> map2;
        Multimap<Object, IKeyable> map3;

        String name = key instanceof PrimaryKey ? ((PrimaryKey) key).name() : ((AlternateKey) key).name();
        boolean unique = key instanceof PrimaryKey ? ((PrimaryKey) key).unique() : ((AlternateKey) key).unique();
        boolean auto = key instanceof PrimaryKey ? ((PrimaryKey) key).auto() : ((AlternateKey) key).auto();
        boolean mandatory = key instanceof PrimaryKey ? ((PrimaryKey) key).mandatory() : ((AlternateKey) key).mandatory();

        field.setAccessible(true);

        try
        {
            value = field.get(keyable);
        }
        catch (IllegalAccessException e)
        {
            throw new KeyException(
                    String.format(
                            "Cannot register key with name: '%s' of type: '%s' for keyable entity: '%s', due to: '%s'",
                            name,
                            field.getType().getName(),
                            keyable.getClass().getName(),
                            e.getMessage()));
        }

        // Do we have to generate the key value (auto = true) ?
        if (value == null && auto)
        {
            value = generateNextKeyValue(keyable, field.getType(), name);

            try
            {
                field.setAccessible(true);
                field.set(keyable, value);
            }
            catch (IllegalAccessException e)
            {
                throw new KeyException(
                        String.format(
                                "Cannot register key with name: '%s' of type: '%s' for keyable entity: '%s', due to: '%s'",
                                name,
                                field.getType().getName(),
                                keyable.getClass().getName(),
                                e.getMessage()));
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
                    throw new KeyException(
                            String.format(
                                    "Cannot register key with name: '%s' with value: '%s', of type: '%s' for keyable entity: '%s', because key value is not unique!",
                                    name,
                                    value,
                                    field.getType().getName(),
                                    keyable.getClass().getName()));
                }
            }
            else
            {
                map3.put(value, keyable);
            }
        }
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
            throw new KeyException(
                    String.format(
                            "Keyable entity: '%s' does not contain a primary key! One of the defined keys must be set as the primary key",
                            keyable.getClass().getName()));
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
                    throw new KeyException(
                            String.format(
                                    "Keyable entity: '%s' contains multiple fields annotated as primary keys! Only one field can be annotated as the primary key",
                                    keyable.getClass().getName()));
                }
            }
        }
    }

    /**
     * Checks if the given key exist ?
     * @param keyableClass Keyable entity.
     * @param name Key name.
     * @param value Key value.
     * @return True if the given key exist, false otherwise.
     */
    private boolean exist(final @NonNull Class<? extends IKeyable> keyableClass, final @NonNull Field field, final @NonNull String name, final Object value)
    {
        if (value == null)
        {
            return false;
        }

        // Key Class | Key Name | Key Value | Keyable
        Map<Class<?>, Map<String, Multimap<Object, IKeyable>>> keyables = entities.get(keyableClass);
        if (keyables != null)
        {
            Map<String, Multimap<Object, IKeyable>> keys = keyables.get(field.getType());
            if (keys != null)
            {
                Multimap<Object, IKeyable> values = keys.get(name);
                if (values != null)
                {
                    return values.containsKey(value);
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
        if (type == UUID.class)
        {
            return UUID.randomUUID();
        }

        if (type == Integer.class)
        {
            Integer latest = (Integer) getLatestKeyValue(keyable, type, name);
            if (latest == null)
            {
                latest = 1;
            }
            else
            {
                latest += 1;
            }

            updateLatestKeyValue(keyable, type, name, latest);

            return latest;
        }

        if (type == Long.class)
        {
            Long latest = (Long) getLatestKeyValue(keyable, type, name);
            if (latest == null)
            {
                latest = 1L;
            }
            else
            {
                latest += 1;
            }

            updateLatestKeyValue(keyable, type, name, latest);

            return latest;
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
            Map<String, Object> keys = keyTypes.get(type);
            if (keys != null)
            {
                return keys.get(name);
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
        Map<String, Object> keys = null;

        keyTypes = values.get(keyable.getClass());
        if (keyTypes != null)
        {
            keys = keyTypes.get(type);
            if (keys == null)
            {
                keys = new HashMap<>();
            }

            keys.put(name, value);
        }
        else
        {
            keyTypes = new HashMap<>();
        }

        keyTypes.put(type, keys);
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

        throw new KeyManagerException(
                String.format(
                        "Cannot determine type for key name: '%s' on keyable entity type: '%s'. Are you sure it's a real key?",
                        keyName,
                        keyableClass));
    }

    /**
     * Returns a list of keyables matching the given key name and value.
     * @param keyableClass Keyable class.
     * @param keyName Key name.
     * @param keyValue Key value.
     * @return List of keyables or an empty list if no keyable has been found matching the given criteria.
     */
    public final List<? extends IKeyable> get(final @NonNull Class<? extends IKeyable> keyableClass, final @NonNull String keyName, final @NonNull Object keyValue)
    {
        Class<?> type = null;

        try
        {
            type = getKeyTypeFor(keyableClass, keyName);

            if (type == null)
            {
                log.error(String.format("Cannot retrieve type of key name: '%s', for keyable class: '%s'", keyName, keyableClass));
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
                return getKeyables(keyableClass, field.getType(), field.getName()).size();
            }
        }

        return 0;
    }

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
    private boolean isFieldTypeAuthorized(final @NonNull Field field)
    {
        return field.getType().isPrimitive()
                || field.getType() == Integer.class
                || field.getType() == Long.class
                || field.getType() == Double.class
                || field.getType() == Float.class
                || field.getType() == Boolean.class
                || field.getType() == Short.class
                || field.getType() == Byte.class
                || field.getType() == Character.class
                || field.getType() == UUID.class
                || field.getType() == String.class;
    }
}
