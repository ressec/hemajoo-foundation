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
package com.ressec.hemajoo.foundation.common.test.entity.keyable.model;

import com.ressec.hemajoo.foundation.common.annotation.Internal;
import com.ressec.hemajoo.foundation.common.entity.keyable.AbstractKeyable;
import com.ressec.hemajoo.foundation.common.entity.keyable.IKey;
import com.ressec.hemajoo.foundation.common.entity.keyable.PrimaryKey;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * A keyable entity test class that declare multiple keys (primary & alternate).
 * <br><br>
 * This class is used for testing purpose only!
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Internal
public class KeyableCountryWithPrimaryAndAlternateKey extends AbstractKeyable
{
    /**
     * Name.
     */
    @PrimaryKey(name = "name", unique = true, mandatory = true, auto = false)
    @Getter
    private String name;

    /**
     * Name.
     */
    @PrimaryKey(name = "code", unique = true, mandatory = true, auto = false)
    @Getter
    private String code;

    /**
     * Avoid direct instantiation of objects of this type!
     */
    private KeyableCountryWithPrimaryAndAlternateKey()
    {
        // Empty.
    }

    @Builder
    public KeyableCountryWithPrimaryAndAlternateKey(final String name, final String code)
    {
        this.name = name;
        this.code = code;

        // Invoke the key manager for registration.
        super.register();
    }

    /**
     * Creates an empty (fake) keyable object of this type. Generally used to query the key manager.
     * @return Empty keyable entity.
     */
    public static KeyableCountryWithPrimaryAndAlternateKey empty()
    {
        return new KeyableCountryWithPrimaryAndAlternateKey();
    }

    /**
     * Retrieves a keyable from the key manager given a key name and a key value.
     * @param keyName Key name.
     * @param keyValue Key value.
     * @return {@link KeyableCountryWithPrimaryAndAlternateKey} if found, null otherwise.
     */
    public static KeyableCountryWithPrimaryAndAlternateKey get(final @NonNull String keyName, final @NonNull Object keyValue)
    {
        return (KeyableCountryWithPrimaryAndAlternateKey) KeyableCountryWithPrimaryAndAlternateKey
                .empty().firstFrom(keyName, keyValue);
    }

    /**
     * Retrieves a keyable from the key manager given a key.
     * @param key Key.
     * @return {@link KeyableCountryWithPrimaryAndAlternateKey} if found, null otherwise.
     */
    public static KeyableCountryWithPrimaryAndAlternateKey get(final @NonNull IKey key)
    {
        return (KeyableCountryWithPrimaryAndAlternateKey) KeyableCountryWithPrimaryAndAlternateKey.empty().firstFrom(key);
    }
}
