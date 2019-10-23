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
import com.ressec.hemajoo.foundation.common.entity.keyable.AlternateKey;
import com.ressec.hemajoo.foundation.common.entity.keyable.PrimaryKey;
import lombok.Builder;
import lombok.Getter;

/**
 * A keyable entity test class that declare two keys: a primary and an alternate.
 * <br><br>
 * This class is used for testing purpose only!
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Internal
public class KeyableCountryWithPrimaryAndAlternateKey extends AbstractKeyable
{
    /**
     * Numeric ISO code of the country.
     */
    @Getter
    private int numeric;

    /**
     * ISO Alpha-2 code of the country.
     */
    @Getter
    private String iso2;

    /**
     * ISO Alpha-3 code of the country.
     */
    @AlternateKey(name = "iso3", mandatory = true, unique = true)
    @Getter
    private String iso3;

    /**
     * Name.
     */
    @PrimaryKey(name = "name", unique = true, mandatory = true, auto = false)
    @Getter
    private String name;

    /**
     * Official name of the country.
     */
    @Getter
    private String officialName;
    /**
     * Avoid direct instantiation of objects of this type!
     */
    private KeyableCountryWithPrimaryAndAlternateKey()
    {
        // Empty.
    }

    /**
     * Creates a new country.
     * @param name Country name.
     * @param iso3 Country ISO Alpha-3 code.
     */
    @Builder
    public KeyableCountryWithPrimaryAndAlternateKey(final String name, final String iso3)
    {
        this.name = name;
        this.iso3 = iso3;

        // Invoke the key manager for registration.
        super.register();
    }
}
