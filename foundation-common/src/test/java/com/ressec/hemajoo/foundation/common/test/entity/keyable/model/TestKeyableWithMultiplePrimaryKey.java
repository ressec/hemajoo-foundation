/*
 * (C) Copyright IBM Corp. 2019 - All Rights Reserved
 * ---------------------------------------------------------------------------
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 * ---------------------------------------------------------------------------
 */
package com.ressec.hemajoo.foundation.common.test.entity.keyable.model;

import com.ressec.hemajoo.foundation.common.entity.keyable.KeyManager;
import com.ressec.hemajoo.foundation.common.entity.keyable.exception.KeyException;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for the {@link KeyableWithSameAlternateKeyName} entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public class TestKeyableWithMultiplePrimaryKey
{
    @Before
    public void setUp() throws Exception
    {
        KeyManager.getInstance().unregisterKeysByKeyableType(KeyableWithMultiplePrimaryKey.class);
    }

    @After
    public void tearDown() throws Exception
    {
        KeyManager.getInstance().shutdown();
    }

    /**
     * Ensure an exception is raised while trying to create a keyable having multiple primary keys.
     */
    @Test(expected = KeyException.class)
    public void expectFailureToCreateKeyableWhenMultiplePrimaryKey()
    {
        KeyableWithMultiplePrimaryKey entity = KeyableWithMultiplePrimaryKey.builder()
                .numeric(250)
                .name("FRA")
                .build();
    }
}