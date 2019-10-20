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
import java.util.List;

/**
 * Provides an abstract implementation of the {@link Keyable} interface.
 *
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public abstract class AbstractKeyable implements Keyable
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
    public final List<? extends Keyable> from(final @NonNull String name, final @NonNull Object value)
    {
        return KeyManager.getInstance().get(this.getClass(), name, value);
    }

    @Override
    public final List<Keyable> from(final @NonNull IKey key)
    {
        return KeyManager.getInstance().get(this.getClass(), key);
    }

    @Override
    public final Keyable firstFrom(final @NonNull String name, final @NonNull Object value)
    {
        List<? extends Keyable> keyables;

        try
        {
            keyables = KeyManager.getInstance().get(this.getClass(), name, value);
        }
        catch (Exception e)
        {
            return null;
        }

        return keyables.isEmpty() ? null : keyables.get(0);
    }

    @Override
    public final Keyable firstFrom(final @NonNull IKey key)
    {
        List<Keyable> keyables = KeyManager.getInstance().get(this.getClass(), key);

        return keyables.isEmpty() ? null : keyables.get(0);
    }

    @Override
    public Key getKey()
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
    public final Key getKey(final @NonNull String name, final @NonNull Object value)
    {
        return Key.builder()
                .keyable(this)
                .name(name)
                .value(value)
                .build();
    }

    @Override
    public final List<Annotation> getKeys()
    {
        List<Annotation> keys = new ArrayList<>();

        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            PrimaryKey primary = field.getAnnotation(PrimaryKey.class);
            if (primary != null)
            {
                keys.add(primary);
            }
            else
            {
                AlternateKey alternate = field.getAnnotation(AlternateKey.class);
                if (alternate != null)
                {
                    keys.add(alternate);
                }
            }
        }

        return keys;
    }

    @Override
    public final Annotation getKeyAnnotation(final @NonNull String name)
    {
        for (Annotation annotation : getKeys())
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
                if (annotation instanceof AlternateKey)
                {
                    if (((AlternateKey) annotation).name().equals(name))
                    {
                        return annotation;
                    }
                }
            }
        }

        return null;
    }

    @Override
    public final Field getAnnotatedField(final @NonNull Annotation annotation)
    {
        String name = annotation instanceof PrimaryKey ? ((PrimaryKey) annotation).name() :
                ((AlternateKey) annotation).name();

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
                if (alternate != null)
                {
                    if (alternate.name().equals(name))
                    {
                        return field;
                    }
                }
            }
        }

        return null;
    }
}