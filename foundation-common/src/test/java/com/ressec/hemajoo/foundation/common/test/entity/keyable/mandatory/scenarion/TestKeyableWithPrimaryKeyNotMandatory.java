/*
 * (C) Copyright IBM Corp. 2019 - All Rights Reserved
 * ---------------------------------------------------------------------------
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 * ---------------------------------------------------------------------------
 */
package com.ressec.hemajoo.foundation.common.test.entity.keyable.mandatory.scenarion;

import com.ressec.hemajoo.foundation.common.entity.keyable.KeyManager;
import com.ressec.hemajoo.foundation.common.test.entity.keyable.mandatory.model.KeyableWithPrimaryKeyNotMandatory;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for the {@link KeyableWithPrimaryKeyNotMandatory} entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public class TestKeyableWithPrimaryKeyNotMandatory
{
    @Before
    public void setUp() throws Exception
    {
        KeyManager.getInstance().unregisterKeysByKeyableType(KeyableWithPrimaryKeyNotMandatory.class);
    }

    @After
    public void tearDown() throws Exception
    {
        KeyManager.getInstance().shutdown();
    }

    /**
     * Ensure the failure to create a keyable with a primary key with the property 'mandatory' set to false.
     */
    @Test
    public void expectFailureToCreateKeyableWithPrimaryKeyNotMandatory()
    {
        KeyableWithPrimaryKeyNotMandatory entity = KeyableWithPrimaryKeyNotMandatory.builder()
                .build();
    }
}