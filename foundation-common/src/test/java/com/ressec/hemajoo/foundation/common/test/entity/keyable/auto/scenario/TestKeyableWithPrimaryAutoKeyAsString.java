/*
 * (C) Copyright IBM Corp. 2019 - All Rights Reserved
 * ---------------------------------------------------------------------------
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 * ---------------------------------------------------------------------------
 */
package com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.scenario;

import com.ressec.hemajoo.foundation.common.entity.keyable.KeyException;
import com.ressec.hemajoo.foundation.common.entity.keyable.KeyManager;
import com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.model.KeyableWithPrimaryAutoKeyAsString;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for the {@link KeyableWithPrimaryAutoKeyAsString} entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public class TestKeyableWithPrimaryAutoKeyAsString
{
    @Before
    public void setUp() throws Exception
    {
        KeyManager.getInstance().unregisterKeysByKeyableType(KeyableWithPrimaryAutoKeyAsString.class);
    }

    @After
    public void tearDown() throws Exception
    {
        KeyManager.getInstance().shutdown();
    }

    /**
     * Ensure the failure to create a keyable with an auto primary key with a String type.
     */
    @Test(expected = KeyException.class)
    public void expectFailureToCreateKeyableWithPrimaryAutoKeyAsString()
    {
        KeyableWithPrimaryAutoKeyAsString entity = KeyableWithPrimaryAutoKeyAsString.builder()
                .build();
    }

    /**
     * Ensure the failure to create a keyable with an auto primary key with a String type.
     */
    @Test(expected = KeyException.class)
    public void expectFailureToCreateKeyableWithPrimaryAutoKeyAsStringWithValueProvided()
    {
        KeyableWithPrimaryAutoKeyAsString entity = KeyableWithPrimaryAutoKeyAsString.builder()
                .autoAsString("Paris")
                .build();
    }
}