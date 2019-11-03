/*
 * (C) Copyright IBM Corp. 2019 - All Rights Reserved
 * ---------------------------------------------------------------------------
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 * ---------------------------------------------------------------------------
 */
package com.ressec.hemajoo.foundation.common.test.entity.keyable;

import com.ressec.hemajoo.foundation.common.annotation.Internal;
import com.ressec.hemajoo.foundation.common.entity.keyable.Keyable;
import com.ressec.hemajoo.foundation.common.entity.keyable.PrimaryKey;
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
public class KeyableWithPrimaryAutoKeyAsPrimitiveLong extends Keyable
{
    /**
     * Field defined as a primary key with property 'auto' set to true and of type primitive long.
     */
    @PrimaryKey(name = "primitiveLong", auto = true)
    @Getter
    private long primitiveLong;

    /**
     * Avoid direct instantiation of entity.
     */
    private KeyableWithPrimaryAutoKeyAsPrimitiveLong()
    {
        // Empty.
    }

    /**
     * Creates a new test keyable entity.
     * @param primitiveLong Primitive long value.
     */
    @Builder
    public KeyableWithPrimaryAutoKeyAsPrimitiveLong(final long primitiveLong)
    {
        this.primitiveLong = primitiveLong;

        super.register();
    }
}
