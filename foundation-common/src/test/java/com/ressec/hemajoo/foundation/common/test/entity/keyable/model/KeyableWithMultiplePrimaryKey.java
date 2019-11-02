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
import com.ressec.hemajoo.foundation.common.entity.keyable.Keyable;
import com.ressec.hemajoo.foundation.common.entity.keyable.PrimaryKey;
import lombok.Builder;
import lombok.Getter;

/**
 * A keyable entity test class.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Internal
public class KeyableWithMultiplePrimaryKey extends Keyable
{
    /**
     * Numeric.
     */
    @PrimaryKey(name = "numeric")
    @Getter
    private int numeric;

    /**
     * Name.
     */
    @PrimaryKey(name = "name")
    @Getter
    private String name;

    /**
     * Avoid direct instantiation of this entity.
     */
    private KeyableWithMultiplePrimaryKey()
    {
        // Empty.
    }

    /**
     * Creates a new keyable entity.
     * @param numeric Numeric.
     * @param name Name.
     */
    @Builder
    public KeyableWithMultiplePrimaryKey(final int numeric, final String name, final String other)
    {
        this.numeric = numeric;
        this.name = name;

        super.register();
    }
}
