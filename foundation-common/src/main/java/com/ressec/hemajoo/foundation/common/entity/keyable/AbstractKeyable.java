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
    public final Key getKey()
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
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            AlternateKey alternate = field.getAnnotation(AlternateKey.class);
            if (alternate != null && alternate.name().equals(name))
            {
                try
                {
                    field.setAccessible(true);
                    Object value = field.get(this);
                    field.setAccessible(false);

                    return Key.builder()
                            .keyable(this)
                            .name(alternate.name())
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
        for (Annotation annotation : getAnnotationKeys())
        {
            if (annotation instanceof PrimaryKey)
            {
                if (((PrimaryKey) annotation).name().equals(name))
                {
                    return annotation;
                }
            }
            else
            {
                if (annotation instanceof AlternateKey && ((AlternateKey) annotation).name().equals(name))
                {
                    return annotation;
                }
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
            if (annotation instanceof PrimaryKey)
            {
                keys.add(getKey());
            }
            else if (annotation instanceof AlternateKey)
            {
                keys.add(getKey(((AlternateKey) annotation).name()));
            }
        }

        return Collections.unmodifiableList(keys);
    }

    @Override
    public final List<IKey> getUniqueKeyList()
    {
        PrimaryKey primary;
        AlternateKey alternate;
        IKey key;
        List<IKey> keys = new ArrayList<>();

        for (Annotation annotation : getAnnotationKeys())
        {
            if (annotation instanceof PrimaryKey)
            {
                primary = (PrimaryKey) annotation;
                if (primary.unique())
                {
                    key = getKey();
                    if (key != null && key.isUnique())
                    {
                        keys.add(getKey());
                    }
                }
            }
            else if (annotation instanceof AlternateKey)
            {
                alternate = (AlternateKey) annotation;
                if (alternate.unique())
                {
                    key = getKey(((AlternateKey) annotation).name());
                    if (key != null && key.isUnique())
                    {
                        keys.add(key);
                    }
                }
            }
        }

        return Collections.unmodifiableList(keys);
    }

    @Override
    public final List<IKey> getMandatoryKeyList()
    {
        PrimaryKey primary;
        AlternateKey alternate;
        IKey key;
        List<IKey> keys = new ArrayList<>();

        List<Annotation> annotations = getAnnotationKeys();
        for (Annotation annotation : annotations)
        {
            if (annotation instanceof PrimaryKey)
            {
                primary = (PrimaryKey) annotation;
                if (primary.mandatory())
                {
                    key = getKey();
                    if (key != null && key.isMandatory())
                    {
                        keys.add(key);
                    }
                }
            }
            else if (annotation instanceof AlternateKey)
            {
                alternate = (AlternateKey) annotation;
                if (alternate.mandatory())
                {
                    key = getKey(((AlternateKey) annotation).name());
                    if (key != null && key.isMandatory())
                    {
                        keys.add(key);
                    }
                }
            }
        }

        return Collections.unmodifiableList(keys);
    }

    @Override
    public final List<IKey> getAutoKeyList()
    {
        PrimaryKey primary;
        AlternateKey alternate;
        IKey key;
        List<IKey> keys = new ArrayList<>();

        for (Annotation annotation : getAnnotationKeys())
        {
            if (annotation instanceof PrimaryKey)
            {
                primary = (PrimaryKey) annotation;
                if (primary.auto())
                {
                    key = getKey();
                    if (key != null && key.isAuto())
                    {
                        keys.add(getKey());
                    }
                }
            }
            else if (annotation instanceof AlternateKey)
            {
                alternate = (AlternateKey) annotation;
                if (alternate.auto())
                {
                    key = getKey(((AlternateKey) annotation).name());
                    if (key != null && key.isAuto())
                    {
                        keys.add(key);
                    }
                }
            }
        }

        return Collections.unmodifiableList(keys);
    }
}
