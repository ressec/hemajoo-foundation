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

/**
 * Provides a concrete implementation of a key.
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
    private Class<? extends IKeyable> reference;

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
     * @param keyable Keyable entity the key refers to.
     * @param name Key name.
     * @param value Key value.
     */
    @Builder
    public Key(final @NonNull IKeyable keyable, final @NonNull String name, final @NonNull Object value)
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
                    }
                }
            }
        }
    }
}

