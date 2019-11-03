/*
 * (C) Copyright IBM Corp. 2019 - All Rights Reserved
 * ---------------------------------------------------------------------------
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 * ---------------------------------------------------------------------------
 */
package com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.model.uuid;

import com.ressec.hemajoo.foundation.common.annotation.Internal;
import com.ressec.hemajoo.foundation.common.entity.keyable.AlternateKey;
import com.ressec.hemajoo.foundation.common.entity.keyable.Keyable;
import com.ressec.hemajoo.foundation.common.entity.keyable.PrimaryKey;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

/**
 * A keyable entity test class.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Internal
public class KeyableAlternateMandatoryUniqueUuidKey extends Keyable
{
    /**
     * Field defined as a primary UUID auto key.
     */
    @PrimaryKey(name = "primaryUuid", auto = true)
    @Getter
    private UUID primaryUuid;

    /**
     * Field defined as an alternate UUID unique key.
     */
    @AlternateKey(name = "alternateMandatoryUniqueUuid", auto = false, mandatory = true, unique = true)
    @Getter
    private UUID alternateMandatoryUniqueUuid;

    /**
     * Avoid direct instantiation of entity.
     */
    private KeyableAlternateMandatoryUniqueUuidKey()
    {
        // Empty.
    }

    /**
     * Creates a new test keyable entity.
     * @param primaryUuid UUID value.
     * @param alternateMandatoryUniqueUuid UUID value.
     */
    @Builder
    public KeyableAlternateMandatoryUniqueUuidKey(final UUID primaryUuid, final UUID alternateMandatoryUniqueUuid)
    {
        this.primaryUuid = primaryUuid;
        this.alternateMandatoryUniqueUuid = alternateMandatoryUniqueUuid;

        super.register();
    }
}