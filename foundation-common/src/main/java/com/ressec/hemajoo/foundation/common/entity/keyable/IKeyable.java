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
 * Classes implementing the {@link IKeyable} interface can declare any field as a key field by annotating the
 * field with the {@link Key} annotation.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface IKeyable
{
    /**
     * Returns the primary key of the keyable entity, if one has been defined.
     * @return Primary {@link IKey} or null if no primary key set.
     */
    IKey getKey();

    /**
     * Returns the key matching the given name.
     * @param name Key name.
     * @return {@link IKey} if one has been found, null otherwise.
     */
    IKey getKey(final @NonNull String name);

    /**
     * Returns a list of all keys.
     * @return List of {@link IKey}, or an empty list if no key has been found.
     */
    List<IKey> getKeyList();

    /**
     * Returns a list of unique keys.
     * @return List of {@link IKey}, or an empty list if no matching key has been found.
     */
    List<IKey> getUniqueKeyList();

    /**
     * Returns a list of mandatory keys.
     * @return List of {@link IKey}, or an empty list if no matching key has been found.
     */
    List<IKey> getMandatoryKeyList();

    /**
     * Returns a list of automatically generated keys.
     * @return List of {@link IKey}, or an empty list if no matching key has been found.
     */
    List<IKey> getAutoKeyList();

    /**
     * Retrieves a list of keyable entities according to the given key name and value.
     * @param clazz Keyable class to retrieve.
     * @param name Key name.
     * @param value Key value.
     * @return List of {@link IKeyable} entities or null if no keyable entity has been found.
     */
    List<? extends IKeyable> getList(final @NonNull Class<? extends IKeyable> clazz, final @NonNull String name, final @NonNull Object value);

    /**
     * Retrieves a list of keyable entity according to the given key.
     * @param clazz Keyable class to retrieve.
     * @param key Key.
     * @return List of {@link IKeyable} entities or null if no keyable entity has been found.
     */
    List<? extends IKeyable> getList(final @NonNull Class<? extends IKeyable> clazz, final @NonNull IKey key);

    /**
     * Retrieves the first keyable entity matching the given key name and key value.
     * This service can be used in case the key used is set with the property 'unique' set to true.
     * @param clazz Keyable class to retrieve.
     * @param name Key name.
     * @param value Key value.
     * @return {@link IKeyable} entity or null if no keyable entity has been found.
     */
    IKeyable get(final @NonNull Class<? extends IKeyable> clazz, final @NonNull String name, final @NonNull Object value);

    /**
     * Retrieves the first keyable matching the given key.
     * This service can be used in case the key used is set with the property 'unique' set to true.
     * @param clazz Keyable class to retrieve.
     * @param key Key.
     * @return {@link IKeyable} entity or null if no keyable entity has been found.
     */
    IKeyable get(final @NonNull Class<? extends IKeyable> clazz, final @NonNull IKey key);

    /**
     * Returns the key annotation matching the given key name.
     * @param name Key name.
     * @return Key annotation if found, null otherwise.
     */
    Annotation getAnnotationKey(final @NonNull String name);

    /**
     * Returns a list of all key annotations.
     * @return List of key annotations or an empty list if no key has been defined.
     */
    List<Annotation> getAnnotationKeys();

    /**
     * Returns the field annotated with the given annotation.
     * @param annotation Annotation.
     * @return Field.
     */
    Field getAnnotatedField(final @NonNull Annotation annotation);
}

