/*
 * (C) Copyright IBM Corp. 2019 - All Rights Reserved
 * ---------------------------------------------------------------------------
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 * ---------------------------------------------------------------------------
 */
package com.ressec.hemajoo.foundation.common.test.entity.exception;

import com.ressec.hemajoo.foundation.common.entity.keyable.exception.KeyException;
import com.ressec.hemajoo.foundation.common.entity.keyable.exception.KeyInitializationException;
import com.ressec.hemajoo.foundation.common.exception.AbstractUncheckedException;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for the {@link AbstractUncheckedException} entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public class TestUncheckedException
{
    @Before
    public void setUp() throws Exception
    {
    }

    @After
    public void tearDown() throws Exception
    {
    }

    /**
     * Ensure the success to create an unchecked exception.
     */
    @Test
    public void expectSuccessToCreateUncheckedException()
    {
        KeyException e = new KeyException("Key usage error!");
        Assert.assertNotNull(e);
    }

    /**
     * Ensure the success to create an unchecked exception based on another exception.
     */
    @Test
    public void expectSuccessToCreateUncheckedExceptionBasedOnException()
    {
        KeyInitializationException e = new KeyInitializationException("Key initialization error!");
        Assert.assertNotNull(e);

        KeyException ve = new KeyException(e);
        Assert.assertNotNull(ve);
        Assert.assertNotNull(ve.getMessage());
    }

    /**
     * Ensure the success to create an unchecked exception based on a message and another exception.
     */
    @Test
    public void expectSuccessToCreateUncheckedExceptionBasedOnMessageAndException()
    {
        KeyInitializationException e = new KeyInitializationException("Key initialization error!");
        Assert.assertNotNull(e);

        KeyException ve = new KeyException("Key violation error due to: ", e);
        Assert.assertNotNull(ve);
        Assert.assertNotNull(ve.getMessage());
    }
}