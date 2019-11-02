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
     * Numeric.
     */
    @Getter
    private int numeric;

    /**
     * Avoid direct instantiation of country entity.
     */
    private KeyableCountryWithoutKey()
    {
        // Empty.
    }

    /**
     * Creates a new test keyable entity.
     * @param numeric Numeric code.
     */
    @Builder
    public KeyableCountryWithoutKey(final int numeric)
    {
        this.numeric = numeric;

        super.register();
    }
}
