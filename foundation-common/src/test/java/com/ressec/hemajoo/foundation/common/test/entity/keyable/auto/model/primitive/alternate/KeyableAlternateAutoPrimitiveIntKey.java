/*
 * (C) Copyright IBM Corp. 2019 - All Rights Reserved
 * ---------------------------------------------------------------------------
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 * ---------------------------------------------------------------------------
 */
package com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.model.primitive.alternate;

import com.ressec.hemajoo.foundation.common.annotation.Internal;
import com.ressec.hemajoo.foundation.common.entity.keyable.AlternateKey;
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
public class KeyableAlternateAutoPrimitiveIntKey extends Keyable
{
    /**
     * Primary key.
     */
    @PrimaryKey(name = "primary", auto = true)
    @Getter
    private int primary;

    /**
     * Alternate key.
     */
    @AlternateKey(name = "alternate", auto = true, mandatory = false, unique = false)
    @Getter
    private int alternate;

    /**
     * Avoid direct instantiation of entity.
     */
    private KeyableAlternateAutoPrimitiveIntKey()
    {
        // Empty.
    }

    /**
     * Creates a new test keyable entity.
     * @param primary Primary key value.
     * @param alternate Alternate key value.
     */
    @Builder
    public KeyableAlternateAutoPrimitiveIntKey(final int primary, final int alternate)
    {
        this.primary = primary;
        this.alternate = alternate;

        super.register();
    }
}
