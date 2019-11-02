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

import java.util.UUID;

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
    @AlternateKey(name = "numeric", mandatory = false, unique = true)
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
     * A character key.
     */
    @AlternateKey(name = "charKey", unique = false, mandatory = false, auto = false)
    @Getter
    private char charKey;

    /**
     * A byte key.
     */
    @AlternateKey(name = "byteKey", unique = false, mandatory = false, auto = false)
    @Getter
    private byte byteKey;

    /**
     * A short key.
     */
    @AlternateKey(name = "shortKey", unique = false, mandatory = false, auto = false)
    @Getter
    private short shortKey;

    /**
     * A integer key.
     */
    @AlternateKey(name = "integerKey", unique = false, mandatory = false, auto = false)
    @Getter
    private int integerKey;

    /**
     * A long key.
     */
    @AlternateKey(name = "longKey", unique = false, mandatory = false, auto = false)
    @Getter
    private long longKey;

    /**
     * A double key.
     */
    @AlternateKey(name = "doubleKey", unique = false, mandatory = false, auto = false)
    @Getter
    private double doubleKey;

    /**
     * A float key.
     */
    @AlternateKey(name = "floatKey", unique = false, mandatory = false, auto = false)
    @Getter
    private float floatKey;

    /**
     * A boolean key.
     */
    @AlternateKey(name = "booleanKey", unique = false, mandatory = false, auto = false)
    @Getter
    private boolean booleanKey;

    /**
     * A UUID key.
     */
    @AlternateKey(name = "uuidKey", unique = true, mandatory = false, auto = false)
    @Getter
    private UUID uuidKey;

    /**
     * Comment.
     */
    @AlternateKey(name = "comment", unique = false, mandatory = false, auto = false)
    @Getter
    private String comment;

    /**
     * An auto generated key.
     */
    @AlternateKey(name = "auto", unique = false, mandatory = false, auto = true)
    @Getter
    private int autoKey;

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
     * @param iso2 Country ISO Alpha-2 code.
     * @param iso3 Country ISO Alpha-3 code.
     * @param numeric Country ISO numeric code.
     * @param booleanKey A primitive boolean key.
     * @param charKey A primitive char key.
     * @param byteKey A primitive byte key.
     * @param shortKey A primitive short key.
     * @param integerKey A primitive integer key.
     * @param longKey A primitive long key.
     * @param doubleKey A primitive double key.
     * @param floatKey A primitive float key.
     * @param uuidKey A UUID key.
     * @param autoKey An auto generated key.
     * @param comment Country comment.
     */
    @Builder
    public KeyableCountryWithPrimaryAndAlternateKey(
            final String name,
            final String iso2,
            final String iso3,
            final int numeric,
            final boolean booleanKey,
            final byte byteKey,
            final char charKey,
            final short shortKey,
            final int integerKey,
            final long longKey,
            final double doubleKey,
            final float floatKey,
            final UUID uuidKey,
            final int autoKey,
            final String comment)
    {
        this.name = name;
        this.iso2 = iso2;
        this.iso3 = iso3;
        this.numeric = numeric;
        this.booleanKey = booleanKey;
        this.charKey = charKey;
        this.byteKey = byteKey;
        this.shortKey = shortKey;
        this.integerKey = integerKey;
        this.longKey = longKey;
        this.doubleKey = doubleKey;
        this.floatKey = floatKey;
        this.uuidKey = uuidKey;
        this.autoKey = autoKey;
        this.comment = comment;

        // Invoke the key manager for registration.
        super.register();
    }
}
