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
import com.ressec.hemajoo.foundation.common.entity.keyable.Keyable;
import com.ressec.hemajoo.foundation.common.entity.keyable.PrimaryKey;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/**
 * A keyable entity test class that only declare a primary key.
 * <br><br>
 * This class is used for testing purpose only!
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Internal
public class KeyableCountryWithPrimaryKey extends Keyable
{
    /**
     * Name.
     */
    @PrimaryKey(name = "name", unique = true, mandatory = true, auto = false)
    @Getter
    private String name;

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
    @Getter
    private String iso3;

    /**
     * Official name of the country.
     */
    @Getter
    private String officialName;
    /**
     * Avoid direct instantiation of objects of this type!
     */
    private KeyableCountryWithPrimaryKey()
    {
        // Empty.
    }

    /**
     * Creates a new entity.
     * @param name Country name.
     * @param iso2 Country ISO Alpha-2 code.
     * @param iso3 Country ISO Alpha-3 code.
     * @param numeric Country numeric ISO code.
     */
    @Builder
    public KeyableCountryWithPrimaryKey(final @NonNull String name, final String iso2, final String iso3, final int numeric)
    {
        this.name = name;
        this.iso2 = iso2;
        this.iso3 = iso3;
        this.numeric = numeric;

        // Invoke the key manager for registration.
        super.register();
    }
}
