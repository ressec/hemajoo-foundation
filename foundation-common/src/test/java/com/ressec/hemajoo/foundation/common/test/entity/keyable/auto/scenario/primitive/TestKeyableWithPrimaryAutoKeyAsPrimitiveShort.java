/*
 * (C) Copyright IBM Corp. 2019 - All Rights Reserved
 * ---------------------------------------------------------------------------
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 * ---------------------------------------------------------------------------
 */
package com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.scenario.primitive;

import com.ressec.hemajoo.foundation.common.entity.keyable.KeyManager;
import com.ressec.hemajoo.foundation.common.entity.keyable.exception.KeyException;
import com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.model.primitive.KeyableWithPrimaryAutoKeyAsPrimitiveShort;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for the {@link KeyableWithPrimaryAutoKeyAsPrimitiveShort} entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public class TestKeyableWithPrimaryAutoKeyAsPrimitiveShort
{
    @Before
    public void setUp() throws Exception
    {
        KeyManager.getInstance().unregisterKeysByKeyableType(KeyableWithPrimaryAutoKeyAsPrimitiveShort.class);
    }

    @After
    public void tearDown() throws Exception
    {
        KeyManager.getInstance().shutdown();
    }

    /**
     * Ensure the success to create a keyable with an auto primary key of type primitive short.
     */
    @Test
    public void expectSuccessToCreateKeyableWithPrimaryAutoKeyAsPrimitiveShort()
    {
        KeyableWithPrimaryAutoKeyAsPrimitiveShort entity = KeyableWithPrimaryAutoKeyAsPrimitiveShort.builder()
                .build();

        Assert.assertNotNull(entity);
        Assert.assertNotEquals(0, entity.getPrimaryKey().getValue());
    }

    /**
     * Ensure the success to create a keyable with an auto primary key of type primitive short.
     */
    @Test
    public void expectSuccessToCreateKeyableWithPrimaryAutoKeyAsPrimitiveShortWithValueEqualZero()
    {
        KeyableWithPrimaryAutoKeyAsPrimitiveShort entity = KeyableWithPrimaryAutoKeyAsPrimitiveShort.builder()
                .primitiveShort((short) 0)
                .build();

        Assert.assertNotNull(entity);
    }

    /**
     * Ensure the failure to create a keyable with an auto primary key of type primitive short when the value
     * is greater than zero.
     */
    @Test(expected = KeyException.class)
    public void expectFailureToCreateKeyableWithPrimaryAutoKeyAsPrimitiveShortWithValueGreaterThanZero()
    {
        KeyableWithPrimaryAutoKeyAsPrimitiveShort entity = KeyableWithPrimaryAutoKeyAsPrimitiveShort.builder()
                .primitiveShort((short) 154)
                .build();

        Assert.assertNotNull(entity);
    }

    /**
     * Ensure the success to create one hundred keyables in less than 1000 milliseconds with an auto primary key of
     * type primitive short when the value is greater than zero.
     */
    @Test(timeout = 1000)
    public void expectSuccessToCreateMultipleKeyableWithPrimaryAutoKeyAsPrimitiveShort()
    {
        short previous = 0;
        KeyableWithPrimaryAutoKeyAsPrimitiveShort entity;

        for (int i = 0; i < 100; i++)
        {
            entity = KeyableWithPrimaryAutoKeyAsPrimitiveShort.builder()
                    .primitiveShort((short) 0)
                    .build();

            Assert.assertNotNull(entity);
            Assert.assertNotEquals(previous, (short) entity.getPrimaryKey().getValue());

            previous = (short) entity.getPrimaryKey().getValue();
        }

        int count = KeyManager.getInstance().countByKeyableClass(KeyableWithPrimaryAutoKeyAsPrimitiveShort.class);
        Assert.assertEquals(100, count);
    }

    /**
     * Ensure the failure to create more than 32'767 keyables with an auto primary key of type primitive short.
     */
    @Test(expected = KeyException.class)
    public void expectFailureToCreateMultipleKeyableWithPrimaryAutoKeyAsPrimitiveShortByReachingKeyLimit()
    {
        short previous = 0;
        KeyableWithPrimaryAutoKeyAsPrimitiveShort entity;

        for (int i = 0; i < Short.MAX_VALUE + 1; i++)
        {
            entity = KeyableWithPrimaryAutoKeyAsPrimitiveShort.builder()
                    .primitiveShort((short) 0)
                    .build();

            Assert.assertNotNull(entity);
            Assert.assertNotEquals(previous, (short) entity.getPrimaryKey().getValue());

            previous = (short) entity.getPrimaryKey().getValue();
        }
    }
}