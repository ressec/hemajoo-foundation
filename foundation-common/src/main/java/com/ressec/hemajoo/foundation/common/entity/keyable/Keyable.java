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

/**
 * Provides a concrete implementation of a keyable entity.
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
     * Creates an empty (fake) country. Used to query the key manager.
     * @return Empty keyable.
     */
    public static IKeyable empty()
    {
        return new Keyable();
    }

    /**
     * Retrieves a keyable from the key manager given a key name and a key value.
     * @param clazz Class of the caller.
     * @param keyName Key name.
     * @param keyValue Key value.
     * @return Keyable if found, null otherwise.
     */
    public static IKeyable get(final @NonNull Class<? extends IKeyable> clazz, final @NonNull String keyName, final @NonNull Object keyValue)
    {
        return Keyable.empty().firstFrom(clazz, keyName, keyValue);
    }

    /**
     * Retrieves a keyable from the key manager given a key.
     * @param clazz Class of the caller.
     * @param key Key.
     * @return Keyable if found, null otherwise.
     */
    public static IKeyable get(final @NonNull Class<? extends IKeyable> clazz, final @NonNull IKey key)
    {
        return Keyable.empty().firstFrom(clazz, key);
    }
}
