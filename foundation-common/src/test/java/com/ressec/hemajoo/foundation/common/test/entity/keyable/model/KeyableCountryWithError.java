/*
 * (C) Copyright IBM Corp. 2019 - All Rights Reserved
 * ---------------------------------------------------------------------------
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 * ---------------------------------------------------------------------------
 */
package com.ressec.hemajoo.foundation.common.test.entity.keyable.model;

import com.ressec.hemajoo.foundation.common.annotation.Internal;
import com.ressec.hemajoo.foundation.common.entity.keyable.AlternateKey;
import com.ressec.hemajoo.foundation.common.entity.keyable.Keyable;
import com.ressec.hemajoo.foundation.common.entity.keyable.PrimaryKey;
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
public class KeyableCountryWithError extends Keyable
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
    @PrimaryKey(name = "iso3")
    @AlternateKey(name = "iso33")
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
    private KeyableCountryWithError()
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
    public KeyableCountryWithError(final int numericCode, final String iso2, final String iso3, final String name, final String officialName)
    {
        this.numeric = numericCode;
        this.iso2 = iso2;
        this.iso3 = iso3;
        this.name = name;
        this.officialName = officialName;

        super.register();
    }
}
