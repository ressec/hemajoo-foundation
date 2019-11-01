/*
 * (C) Copyright IBM Corp. 2019 - All Rights Reserved
 * ---------------------------------------------------------------------------
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 * ---------------------------------------------------------------------------
 */
package com.ressec.hemajoo.foundation.common.test.entity.keyable.model.auto;

import com.ressec.hemajoo.foundation.common.entity.keyable.KeyException;
import com.ressec.hemajoo.foundation.common.entity.keyable.KeyManager;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for the {@link KeyableWithPrimaryAutoKeyAsPrimitiveInt} entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public class TestKeyableWithPrimaryAutoKeyAsPrimitiveInt
{
    @Before
    public void setUp() throws Exception
    {
        KeyManager.getInstance().unregisterKeysByKeyableType(KeyableWithPrimaryAutoKeyAsPrimitiveInt.class);
    }

    @After
    public void tearDown() throws Exception
    {
        KeyManager.getInstance().shutdown();
    }

    /**
     * Ensure the success to create a keyable with an auto primary key of type primitive integer.
     */
    @Test
    public void expectSuccessToCreateKeyableWithPrimaryAutoKeyAsPrimitiveInt()
    {
        KeyableWithPrimaryAutoKeyAsPrimitiveInt entity = KeyableWithPrimaryAutoKeyAsPrimitiveInt.builder()
                .build();

        Assert.assertNotNull(entity);
        Assert.assertNotEquals(0, entity.getPrimaryKey().getValue());
    }

    /**
     * Ensure the success to create a keyable with an auto primary key of type primitive integer.
     */
    @Test
    public void expectSuccessToCreateKeyableWithPrimaryAutoKeyAsPrimitiveIntWithValueEqualZero()
    {
        KeyableWithPrimaryAutoKeyAsPrimitiveInt entity = KeyableWithPrimaryAutoKeyAsPrimitiveInt.builder()
                .primitiveInt(0)
                .build();

        Assert.assertNotNull(entity);
    }

    /**
     * Ensure the failure to create a keyable with an auto primary key of type primitive integer when the value
     * is greater than zero.
     */
    @Test(expected = KeyException.class)
    public void expectFailureToCreateKeyableWithPrimaryAutoKeyAsPrimitiveIntWithValueGreaterThanZero()
    {
        KeyableWithPrimaryAutoKeyAsPrimitiveInt entity = KeyableWithPrimaryAutoKeyAsPrimitiveInt.builder()
                .primitiveInt(154)
                .build();

        Assert.assertNotNull(entity);
    }

    /**
     * Ensure the success to create one hundred keyables in less than 100 milliseconds with an auto primary key of
     * type primitive integer when the value is greater than zero.
     */
    @Test(timeout = 100)
    public void expectSuccessToCreateMultipleKeyableWithPrimaryAutoKeyAsPrimitiveInt()
    {
        int previous = 0;
        KeyableWithPrimaryAutoKeyAsPrimitiveInt entity;

        for (int i = 0; i < 100; i++)
        {
            entity = KeyableWithPrimaryAutoKeyAsPrimitiveInt.builder()
                    .primitiveInt(0)
                    .build();

            Assert.assertNotNull(entity);
            Assert.assertNotEquals(previous, (int) entity.getPrimaryKey().getValue());

            previous = (int) entity.getPrimaryKey().getValue();
        }

        int count = KeyManager.getInstance().countByKeyableClass(KeyableWithPrimaryAutoKeyAsPrimitiveInt.class);
        Assert.assertEquals(100, count);
    }
}