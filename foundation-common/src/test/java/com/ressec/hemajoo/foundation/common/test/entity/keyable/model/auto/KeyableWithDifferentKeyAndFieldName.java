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
public class KeyableWithDifferentKeyAndFieldName extends Keyable
{
    /**
     * Primary field key where the key name is different from teh field name.
     */
    @PrimaryKey(name = "official")
    @Getter
    private String name;

    /**
     * Avoid direct instantiation of entity.
     */
    private KeyableWithDifferentKeyAndFieldName()
    {
        // Empty.
    }

    /**
     * Creates a new test keyable entity.
     * @param name Name.
     */
    @Builder
    public KeyableWithDifferentKeyAndFieldName(final String name)
    {
        this.name = name;

        super.register();
    }
}
