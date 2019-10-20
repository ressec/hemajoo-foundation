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

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;

import java.lang.reflect.Field;
import java.util.UUID;

/**
 * Provides a concrete implementation of a key.
 *
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@EqualsAndHashCode
public final class Key implements IKey
{
    /**
     * Name of the key.
     */
    @Getter
    private String name;

    /**
     * Type of the key (its class name).
     */
    @Getter
    private Class<?> type;

    /**
     * Keyable entity class this key refers to.
     */
    @Getter
    private Class<? extends Keyable> reference;

    /**
     * Value of the key.
     */
    @Getter
    private Object value;

    /**
     * Is it the primary key ?
     */
    @Getter
    private boolean isPrimary;

    /**
     * Does the key value is mandatory ?
     */
    @Getter
    private boolean isMandatory;

    /**
     * Does the key value must be unique ?
     */
    @Getter
    private boolean isUnique;

    /**
     * Does the key value is automatically generated ?
     */
    @Getter
    private boolean isAuto;

    /**
     * Prevents direct key instantiation!
     */
    private Key()
    {
        // Prevent instantiating Key entities!
    }

    /**
     * Creates a new key.
     *
     * @param keyable Keyable entity the key refers to.
     * @param name    Key name.
     * @param value   Key value.
     */
    @Builder
    public Key(final @NonNull Keyable keyable, final @NonNull String name, final @NonNull Object value)
    {
        Field[] fields = keyable.getClass().getDeclaredFields();
        for (Field field : fields)
        {
            PrimaryKey primary = field.getAnnotation(PrimaryKey.class);
            if (primary != null)
            {
                if (name.equals(primary.name()))
                {
                    this.name = primary.name();
                    this.type = field.getType();
                    this.isMandatory = primary.mandatory();
                    this.isUnique = primary.unique();
                    this.isPrimary = true;
                    this.isAuto = primary.auto();
                    this.reference = keyable.getClass();
                    this.value = value;
                    break;
                }
            }
            else
            {
                AlternateKey alternate = field.getAnnotation(AlternateKey.class);
                if (alternate != null)
                {
                    if (name.equals(alternate.name()))
                    {
                        this.name = alternate.name();
                        this.type = field.getType();
                        this.isMandatory = alternate.mandatory();
                        this.isUnique = alternate.unique();
                        this.isPrimary = false;
                        this.isAuto = alternate.auto();
                        this.reference = keyable.getClass();
                        this.value = value;
                        break;
                    }
                }
            }
        }
    }

    /**
     * Creates a new key given its type, its value and the keyable entity it refers to.
     *
     * @param type      Key type.
     * @param value     Key value.
     * @param reference Keyable entity the key refers to.
     * @return Newly created key.
     */
    public static Key create(final @NonNull Class<?> type, final @NonNull Object value,
                             final @NonNull Class<? extends Keyable> reference)
    {
        if (type != Integer.class && type != Long.class && type != String.class && type != UUID.class)
        {
            throw new KeyException(
                    String.format("Cannot create key because key of type: %s is not allowed!", type.getName()));
        }

        Key key = new Key();

        key.type = type;
        key.value = value;

        return key;
    }

    /**
     * Creates a new key given its type and the keyable entity it refers to.
     * <br><br>
     * The key value is generated using the key manager.
     *
     * @param type      Key type.
     * @param reference Keyable entity the key refers to.
     * @return Newly created key.
     */
    public static Key create(final @NonNull Class<?> type, final @NonNull Class<? extends Keyable> reference)
    {
        if (type != Integer.class && type != Long.class && type != String.class && type != UUID.class)
        {
            throw new KeyException(
                    String.format("Cannot create key because key of type: %s is not allowed!", type.getName()));
        }

//        Object value = KeyManager.getInstance().getNextValue(type, reference);

        Key key = new Key();
//
//        key.type = type;
//        key.value = value;

        return key;
    }

//    /**
//     * Checks if this key is valid.
//     *
//     * @return True if the key is valid, false otherwise.
//     */
//    public final boolean isValid()
//    {
//        return getType() != null && getUuid() != null;
//    }
//
//    /**
//     * Checks if the given key is valid.
//     *
//     * @param key Key to validate.
//     * @return True if the key is valid, false otherwise.
//     */
//    public static boolean isValid(final @NonNull Key key)
//    {
//        return key.getType() != null && key.getUuid() != null;
//    }
}

