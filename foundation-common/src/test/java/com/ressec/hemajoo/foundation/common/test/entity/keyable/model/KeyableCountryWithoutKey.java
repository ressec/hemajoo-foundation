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
import lombok.Builder;
import lombok.Getter;

/**
 * A keyable entity test class that does not declare any key!
 * <br><br>
 * This class is used for testing purpose only!
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Internal
public class KeyableCountryWithoutKey extends Keyable
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
    @Getter
    private String iso3;

    /**
     * Name of the country.
     */
    @Getter
    private String name;

    /**
     * Official name of the country.
     */
    @Getter
    private String officialName;

    /**
     * Avoid direct instantiation of country entity.
     */
    private KeyableCountryWithoutKey()
    {
        // Empty.
    }

    /**
     * Creates a new test country entity.
     * @param numericCode Country ISO numeric code.
     * @param iso2 Country ISO Alpha-2 code.
     * @param iso3 Country ISO Alpha-3 code.
     * @param name Country name.
     * @param officialName Country official name.
     */
    @Builder
    public KeyableCountryWithoutKey(final int numericCode, final String iso2, final String iso3, final String name, final String officialName)
    {
        this.numeric = numericCode;
        this.iso2 = iso2;
        this.iso3 = iso3;
        this.name = name;
        this.officialName = officialName;

        super.register();
    }
}
