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

import com.ressec.hemajoo.foundation.common.entity.keyable.exception.KeyException;
import lombok.NonNull;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Provides an abstract implementation of the {@link IKeyable} interface.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public abstract class AbstractKeyable implements IKeyable
{
    /**
     * Creates an abstract keyable.
     */
    protected AbstractKeyable()
    {
    }

    /**
     * Registers a keyable entity (and its defined keys) against the key manager.
     */
    protected final void register()
    {
        KeyManager.getInstance().register(this);
    }

    @Override
    public final List<IKeyable> getList(final @NonNull Class<? extends IKeyable> clazz, final @NonNull String name, final @NonNull Object value)
    {
        return KeyManager.getInstance().get(clazz, name, value);
    }

    @Override
    public final List<IKeyable> getList(final @NonNull Class<? extends IKeyable> clazz, final @NonNull IKey key)
    {
        return KeyManager.getInstance().get(clazz, key);
    }

    @Override
    public final IKeyable get(final @NonNull Class<? extends IKeyable> clazz, final @NonNull String name, final @NonNull Object value)
    {
        List<? extends IKeyable> keyables;

        keyables = KeyManager.getInstance().get(clazz, name, value);

        return keyables.isEmpty() ? null : keyables.get(0);
    }

    @Override
    public final IKeyable get(final @NonNull Class<? extends IKeyable> clazz, final @NonNull IKey key)
    {
        List<IKeyable> keyables = KeyManager.getInstance().get(clazz, key);

        return keyables.isEmpty() ? null : keyables.get(0);
    }

    @Override
    public final Key getPrimaryKey()
    {
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            PrimaryKey primary = field.getAnnotation(PrimaryKey.class);
            if (primary != null)
            {
                try
                {
                    field.setAccessible(true);
                    Object value = field.get(this);
                    field.setAccessible(false);

                    return Key.builder()
                            .keyable(this)
                            .name(primary.name())
                            .value(value)
                            .build();
                }
                catch (IllegalAccessException e)
                {
                    throw new KeyException(
                            String.format("Cannot retrieve primary key on keyable: '%s', due to: '%s'",
                                    this.getClass().getName(),
                                    e.getMessage()));
                }
            }
        }

        throw new KeyException(
                String.format(
                        "Cannot retrieve primary key on keyable: '%s'. Check that at least one field should be annotated with the @PrimaryKey annotation!",
                        this.getClass().getName()));
    }

    @Override
    public final IKey getKey(final @NonNull String name)
    {
        String mode;

        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            mode = null;
            if (field.getAnnotation(PrimaryKey.class) != null && field.getAnnotation(PrimaryKey.class).name().equals(name))
            {
                mode = PrimaryKey.class.getSimpleName();
            }
            else if (field.getAnnotation(AlternateKey.class) != null && field.getAnnotation(AlternateKey.class).name().equals(name))
            {
                mode = AlternateKey.class.getSimpleName();
            }

            if (mode != null)
            {
                try
                {
                    field.setAccessible(true);
                    Object value = field.get(this);
                    field.setAccessible(false);

                    return Key.builder()
                            .keyable(this)
                            .name(name)
                            .value(value)
                            .build();
                }
                catch (IllegalAccessException e)
                {
                    throw new KeyException(
                            String.format("Cannot retrieve alternate key on keyable: '%s', due to: '%s'",
                                    this.getClass().getName(),
                                    e.getMessage()));
                }
            }
        }

        return null;
    }

    @Override
    public final List<Annotation> getAnnotationKeys()
    {
        List<Annotation> annotations = new ArrayList<>();

        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            PrimaryKey primary = field.getAnnotation(PrimaryKey.class);
            if (primary != null)
            {
                annotations.add(primary);
            }
            else
            {
                AlternateKey alternate = field.getAnnotation(AlternateKey.class);
                if (alternate != null)
                {
                    annotations.add(alternate);
                }
            }
        }

        return Collections.unmodifiableList(annotations);
    }

    @Override
    public final Annotation getAnnotationKey(final @NonNull String name)
    {
        boolean found;

        for (Annotation annotation : getAnnotationKeys())
        {
            found = annotation instanceof PrimaryKey ? ((PrimaryKey) annotation).name().equals(name) : ((AlternateKey) annotation).name().equals(name);
            if (found)
            {
                return annotation;
            }
        }

        return null;
    }

    @Override
    public final Field getAnnotatedField(final @NonNull Annotation annotation)
    {
        String name = annotation instanceof PrimaryKey ? ((PrimaryKey) annotation).name() : ((AlternateKey) annotation).name();

        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            PrimaryKey primary = field.getAnnotation(PrimaryKey.class);
            if (primary != null)
            {
                if (primary.name().equals(name))
                {
                    return field;
                }
            }
            else
            {
                AlternateKey alternate = field.getAnnotation(AlternateKey.class);
                if (alternate != null && alternate.name().equals(name))
                {
                    return field;
                }
            }
        }

        return null;
    }

    @Override
    public final List<IKey> getKeyList()
    {
        List<IKey> keys = new ArrayList<>();

        for (Annotation annotation : getAnnotationKeys())
        {
            keys.add(getKey(annotation instanceof PrimaryKey ? ((PrimaryKey) annotation).name() : ((AlternateKey) annotation).name()));
        }

        return Collections.unmodifiableList(keys);
    }

    @Override
    public final List<IKey> getUniqueKeyList()
    {
        boolean isUnique;
        IKey key;
        List<IKey> keys = new ArrayList<>();

        for (Annotation annotation : getAnnotationKeys())
        {
            key = annotation instanceof PrimaryKey ? getKey(((PrimaryKey) annotation).name()) : getKey(((AlternateKey) annotation).name());
            isUnique = annotation instanceof PrimaryKey || ((AlternateKey) annotation).unique();

            if (isUnique && key != null && key.isUnique())
            {
                keys.add(key);
            }
        }

        return Collections.unmodifiableList(keys);
    }

    @Override
    public final List<IKey> getMandatoryKeyList()
    {
        boolean isMandatory;
        IKey key;
        List<IKey> keys = new ArrayList<>();

        List<Annotation> annotations = getAnnotationKeys();
        for (Annotation annotation : annotations)
        {
            key = annotation instanceof PrimaryKey ? getKey(((PrimaryKey) annotation).name()) : getKey(((AlternateKey) annotation).name());
            isMandatory = annotation instanceof PrimaryKey || ((AlternateKey) annotation).mandatory();

            if (isMandatory && key != null && key.isMandatory())
            {
                keys.add(key);
            }
        }

        return Collections.unmodifiableList(keys);
    }

    @Override
    public final List<IKey> getAutoKeyList()
    {
        boolean isAuto;
        IKey key;
        List<IKey> keys = new ArrayList<>();

        for (Annotation annotation : getAnnotationKeys())
        {
            key = annotation instanceof PrimaryKey ? getKey(((PrimaryKey) annotation).name()) : getKey(((AlternateKey) annotation).name());
            isAuto = annotation instanceof PrimaryKey ? ((PrimaryKey) annotation).auto() : ((AlternateKey) annotation).auto();

            if (isAuto && key != null && key.isAuto())
            {
                keys.add(key);
            }
        }

        return Collections.unmodifiableList(keys);
    }
}
