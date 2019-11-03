/*
 * (C) Copyright IBM Corp. 2019 - All Rights Reserved
 * ---------------------------------------------------------------------------
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 * ---------------------------------------------------------------------------
 */
package com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.model.primitive.primary;

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
public class KeyablePrimaryNotAutoPrimitiveIntKey extends Keyable
{
    /**
     * Primary key.
     */
    @PrimaryKey(name = "primary", auto = false)
    @Getter
    private int primary;

    /**
     * Avoid direct instantiation of entity.
     */
    private KeyablePrimaryNotAutoPrimitiveIntKey()
    {
        // Empty.
    }

    /**
     * Creates a new test keyable entity.
     * @param primary int value.
     */
    @Builder
    public KeyablePrimaryNotAutoPrimitiveIntKey(final int primary)
    {
        this.primary = primary;

        super.register();
    }
}
