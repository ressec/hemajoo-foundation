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
public class KeyableWithPrimaryAutoKeyAsString extends Keyable
{
    /**
     * Primary string key with auto property set to true.
     */
    @PrimaryKey(name = "autoAsString", auto = true)
    @Getter
    private String autoAsString;

    /**
     * Avoid direct instantiation of entity.
     */
    private KeyableWithPrimaryAutoKeyAsString()
    {
        // Empty.
    }

    /**
     * Creates a new test keyable entity.
     * @param autoAsString Auto as a String.
     */
    @Builder
    public KeyableWithPrimaryAutoKeyAsString(final String name, final String autoAsString)
    {
        this.autoAsString = autoAsString;

        super.register();
    }
}
