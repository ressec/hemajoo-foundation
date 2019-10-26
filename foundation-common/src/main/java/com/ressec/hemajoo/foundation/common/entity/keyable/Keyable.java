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

import com.ressec.hemajoo.foundation.common.annotation.Internal;
import lombok.NonNull;

import java.util.List;

/**
 * Provides a concrete implementation of a keyable entity.
 * <br>
 * This class should be used instead of the {@link AbstractKeyable} when creating new implementations of a
 * {@link IKeyable}.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Internal
public class Keyable extends AbstractKeyable
{
    /**
     * Creates a new keyable entity.
     */
    protected Keyable()
    {
        // Empty.
    }

    /**
     * Queries the key manager to retrieve a list of {@link IKeyable} entities matching the given parameters.
     * <br>
     * Should be called when querying a non-unique key.
     * @param clazz Keyable class.
     * @param keyName Key name.
     * @param keyValue Key value.
     * @return List of found entities or an empty list if no matching entities have been found.
     */
    public static List<? extends IKeyable> query(final @NonNull Class<? extends IKeyable> clazz, final @NonNull String keyName, final @NonNull Object keyValue)
    {
        return KeyManager.getInstance().get(clazz, keyName, keyValue);
    }

    /**
     * Queries the key manager to retrieve a list of {@link IKeyable} entities matching the given parameters.
     * <br>
     * Should be called when querying a non-unique key.
     * @param clazz Keyable class.
     * @param key Key.
     * @return List of found entities or an empty list if no matching entities have been found.
     */
    public static List<? extends IKeyable> query(final @NonNull Class<? extends IKeyable> clazz, final @NonNull IKey key)
    {
        return KeyManager.getInstance().get(clazz, key);
    }

    /**
     * Queries the key manager to retrieve a {@link IKeyable} entity matching the given parameters.
     * <br>
     * Should be called when querying a unique key.
     * @param clazz Keyable class.
     * @param keyName Key name.
     * @param keyValue Key value.
     * @return Keyable entity or null if no matching entities have been found.
     */
    public static IKeyable retrieve(final @NonNull Class<? extends IKeyable> clazz, final @NonNull String keyName, final @NonNull Object keyValue)
    {
        List<? extends IKeyable> entities = KeyManager.getInstance().get(clazz, keyName, keyValue);

        if (entities.size() > 0)
        {
            return entities.get(0);
        }

        return null;
    }

    /**
     * Queries the key manager to retrieve a {@link IKeyable} entity matching the given parameters.
     * <br>
     * Should be called when querying a unique key.
     * @param clazz Keyable class.
     * @param key Key.
     * @return Keyable entity or null if no matching entities have been found.
     */
    public static IKeyable retrieve(final @NonNull Class<? extends IKeyable> clazz, final @NonNull IKey key)
    {
        return retrieve(clazz, key.getName(), key.getValue());
    }
}
