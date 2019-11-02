/*
 * (C) Copyright IBM Corp. 2019 - All Rights Reserved
 * ---------------------------------------------------------------------------
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 * ---------------------------------------------------------------------------
 */
package com.ressec.hemajoo.foundation.common.test.entity.exception;

import com.ressec.hemajoo.foundation.common.entity.keyable.exception.KeyInitializationException;
import com.ressec.hemajoo.foundation.common.entity.keyable.exception.KeyViolationException;
import com.ressec.hemajoo.foundation.common.exception.AbstractCheckedException;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for the {@link AbstractCheckedException} entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public class TestCheckedException
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
     * Ensure the success to create a checked exception.
     */
    @Test
    public void expectSuccessToCreateCheckedException()
    {
        KeyViolationException e = new KeyViolationException("Key violation!");
        Assert.assertNotNull(e);
    }

    /**
     * Ensure the success to create a checked exception based on another exception.
     */
    @Test
    public void expectSuccessToCreateCheckedExceptionBasedOnException()
    {
        KeyInitializationException e = new KeyInitializationException("Key initialization error!");
        Assert.assertNotNull(e);

        KeyViolationException ve = new KeyViolationException(e);
        Assert.assertNotNull(ve);
        Assert.assertNotNull(ve.getMessage());
    }

    /**
     * Ensure the success to create a checked exception based on a message and another exception.
     */
    @Test
    public void expectSuccessToCreateCheckedExceptionBasedOnMessageAndException()
    {
        KeyInitializationException e = new KeyInitializationException("Key initialization error!");
        Assert.assertNotNull(e);

        KeyViolationException ve = new KeyViolationException("Key violation error due to: ", e);
        Assert.assertNotNull(ve);
        Assert.assertNotNull(ve.getMessage());
    }
}