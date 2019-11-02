/*
 * (C) Copyright IBM Corp. 2019 - All Rights Reserved
 * ---------------------------------------------------------------------------
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 * ---------------------------------------------------------------------------
 */
package com.ressec.hemajoo.foundation.common.test.entity.exception;

import com.ressec.hemajoo.foundation.common.entity.keyable.KeyManager;
import com.ressec.hemajoo.foundation.common.entity.keyable.exception.KeyException;
import com.ressec.hemajoo.foundation.common.exception.AbstractCheckedException;
import com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.model.KeyableWithOnlyAlternateAutoKey;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for the {@link AbstractCheckedException} entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public class TestAbstractCheckedException
{
    @Before
    public void setUp() throws Exception
    {
        KeyManager.getInstance().unregisterKeysByKeyableType(KeyableWithOnlyAlternateAutoKey.class);
    }

    @After
    public void tearDown() throws Exception
    {
        KeyManager.getInstance().shutdown();
    }

    /**
     * Ensure the failure to create a keyable with only an auto alternate key of type primitive integer.
     * <br><br>
     * We cannot create a keyable entity without a primary key.
     */
    @Test(expected = KeyException.class)
    public void expectFailureToCreateKeyableWithAlternateAutoKeyAsPrimitiveInt()
    {
        KeyableWithOnlyAlternateAutoKey entity = KeyableWithOnlyAlternateAutoKey.builder()
                .build();
    }
}