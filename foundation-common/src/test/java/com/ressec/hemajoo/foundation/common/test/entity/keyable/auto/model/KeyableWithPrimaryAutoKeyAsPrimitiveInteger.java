/*
 * (C) Copyright IBM Corp. 2019 - All Rights Reserved
 * ---------------------------------------------------------------------------
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 * ---------------------------------------------------------------------------
 */
package com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.model;

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
public class KeyableWithPrimaryAutoKeyAsPrimitiveInteger extends Keyable
{
    /**
     * Field defined as a primary key with property 'auto' set to true and of type primitive int.
     */
    @PrimaryKey(name = "primitiveInt", auto = true)
    @Getter
    private int primitiveInt;

    /**
     * Avoid direct instantiation of entity.
     */
    private KeyableWithPrimaryAutoKeyAsPrimitiveInteger()
    {
        // Empty.
    }

    /**
     * Creates a new test keyable entity.
     * @param primitiveInt Primitive int value.
     */
    @Builder(toBuilder = true)
    public KeyableWithPrimaryAutoKeyAsPrimitiveInteger(final int primitiveInt)
    {
        this.primitiveInt = primitiveInt;

        super.register();
    }
}
