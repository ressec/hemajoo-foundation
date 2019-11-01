/*
 * (C) Copyright IBM Corp. 2019 - All Rights Reserved
 * ---------------------------------------------------------------------------
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 * ---------------------------------------------------------------------------
 */
package com.ressec.hemajoo.foundation.common.test.entity.keyable.model.auto;

import com.ressec.hemajoo.foundation.common.annotation.Internal;
import com.ressec.hemajoo.foundation.common.entity.keyable.AlternateKey;
import com.ressec.hemajoo.foundation.common.entity.keyable.Keyable;
import lombok.Builder;
import lombok.Getter;

/**
 * A keyable entity test class.
 * <br><br>
 * This class is used for testing purpose only!
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Internal
public class KeyableWithOnlyAlternateAutoKey extends Keyable
{
    /**
     * Field defined as an alternate key with property 'auto' set to true and of type primitive int.
     */
    @AlternateKey(name = "numeric", auto = true)
    @Getter
    private int numeric;

    /**
     * Avoid direct instantiation of entity.
     */
    private KeyableWithOnlyAlternateAutoKey()
    {
        // Empty.
    }

    /**
     * Creates a new test keyable entity.
     * @param numeric Numeric value.
     */
    @Builder
    public KeyableWithOnlyAlternateAutoKey(final int numeric)
    {
        this.numeric = numeric;

        super.register();
    }
}
