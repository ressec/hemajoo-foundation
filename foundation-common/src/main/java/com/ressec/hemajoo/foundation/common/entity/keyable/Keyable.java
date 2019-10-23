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
import java.util.List;

/**
 * Interface providing the ability to define and use keys based on class fields.
 * Classes implementing the {@link Keyable} interface can declare any field as a key field by annotating the
 * field with the {@link Key} annotation.
 *
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface Keyable
{
    /**
     * Returns the primary key of the keyable entity, if one has been defined.
     *
     * @return Primary {@link IKey} or null if no primary key set.
     */
    IKey getKey();

    /**
     * Returns the key matching the given key name and value.
     *
     * @param name  Key name.
     * @param value Key value.
     * @return {@link IKey} if one has been found, null otherwise.
     */
    IKey getKey(final @NonNull String name, final @NonNull Object value);

    /**
     * Returns a list of the keys set for this keyable entity.
     *
     * @return List of keys or an empty list if no key defined.
     */
    List<Annotation> getKeys();

    /**
     * Retrieves a list of keyable entities according to the given key name and value.
     *
     * @param name  Key name.
     * @param value Key value.
     * @return List of {@link Keyable} entities or null if no keyable entity has been found.
     */
    List<? extends Keyable> from(final @NonNull String name, final @NonNull Object value);

    /**
     * Retrieves a list of keyable entity according to the given key.
     *
     * @param key Key.
     * @return List of {@link Keyable} entities or null if no keyable entity has been found.
     */
    List<? extends Keyable> from(final @NonNull IKey key);

    /**
     * Retrieves the first keyable entity matching the given key name and key value.
     * This service can be used in case the key used is set with the property 'unique' set to true.
     *
     * @param name  Key name.
     * @param value Key value.
     * @return {@link Keyable} entity or null if no keyable entity has been found.
     */
    Keyable firstFrom(final @NonNull String name, final @NonNull Object value);

    /**
     * Retrieves the first keyable matching the given key.
     * This service can be used in case the key used is set with the property 'unique' set to true.
     *
     * @param key Key.
     * @return {@link Keyable} entity or null if no keyable entity has been found.
     */
    Keyable firstFrom(final @NonNull IKey key);

    /**
     * Returns the key annotation matching the given key name.
     *
     * @param name Key name.
     * @return Key annotation if found, null otherwise.
     */
    Annotation getKeyAnnotation(final @NonNull String name);

    /**
     * Returns the field annotated with the given annotation.
     *
     * @param annotation Annotation.
     * @return Field.
     */
    Field getAnnotatedField(final @NonNull Annotation annotation);
}

