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
import com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.model.KeyableWithPrimaryAutoKeyAsPrimitiveByte;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for the {@link KeyableWithPrimaryAutoKeyAsPrimitiveByte} entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public class TestKeyableWithPrimaryAutoKeyAsPrimitiveByte
{
    @Before
    public void setUp() throws Exception
    {
        KeyManager.getInstance().unregisterKeysByKeyableType(KeyableWithPrimaryAutoKeyAsPrimitiveByte.class);
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
    public void expectSuccessToCreateKeyableWithPrimaryAutoKeyAsPrimitiveByte()
    {
        KeyableWithPrimaryAutoKeyAsPrimitiveByte entity = KeyableWithPrimaryAutoKeyAsPrimitiveByte.builder()
                .build();

        Assert.assertNotNull(entity);
        Assert.assertNotEquals(0, entity.getPrimaryKey().getValue());
    }

    /**
     * Ensure the success to create a keyable with an auto primary key of type primitive short.
     */
    @Test
    public void expectSuccessToCreateKeyableWithPrimaryAutoKeyAsPrimitiveByteWithValueEqualZero()
    {
        KeyableWithPrimaryAutoKeyAsPrimitiveByte entity = KeyableWithPrimaryAutoKeyAsPrimitiveByte.builder()
                .primitiveByte((byte) 0)
                .build();

        Assert.assertNotNull(entity);
    }

    /**
     * Ensure the failure to create a keyable with an auto primary key of type primitive short when the value
     * is greater than zero.
     */
    @Test(expected = KeyException.class)
    public void expectFailureToCreateKeyableWithPrimaryAutoKeyAsPrimitiveByteWithValueGreaterThanZero()
    {
        KeyableWithPrimaryAutoKeyAsPrimitiveByte entity = KeyableWithPrimaryAutoKeyAsPrimitiveByte.builder()
                .primitiveByte((byte) 28)
                .build();

        Assert.assertNotNull(entity);
    }

    /**
     * Ensure the success to create one hundred keyables in less than 100 milliseconds with an auto primary key of
     * type primitive byte when the value is greater than zero.
     */
    @Test(timeout = 100)
    public void expectSuccessToCreateMultipleKeyableWithPrimaryAutoKeyAsPrimitiveByte()
    {
        byte previous = 0;
        KeyableWithPrimaryAutoKeyAsPrimitiveByte entity;

        for (int i = 0; i < 100; i++)
        {
            entity = KeyableWithPrimaryAutoKeyAsPrimitiveByte.builder()
                    .primitiveByte((byte) 0)
                    .build();

            Assert.assertNotNull(entity);
            Assert.assertNotEquals(previous, entity.getPrimaryKey().getValue());

            previous = (byte) entity.getPrimaryKey().getValue();
        }

        int count = KeyManager.getInstance().countByKeyableClass(KeyableWithPrimaryAutoKeyAsPrimitiveByte.class);
        Assert.assertEquals(100, count);
    }

    /**
     * Ensure the failure to create more than 127 keyables with an auto primary key of type primitive byte.
     */
    @Test(expected = KeyException.class)
    public void expectFailureToCreateMultipleKeyableWithPrimaryAutoKeyAsPrimitiveByteByReachingKeyLimit()
    {
        byte previous = 0;
        KeyableWithPrimaryAutoKeyAsPrimitiveByte entity;

        for (int i = 0; i < Byte.MAX_VALUE + 1; i++)
        {
            entity = KeyableWithPrimaryAutoKeyAsPrimitiveByte.builder()
                    .primitiveByte((byte) 0)
                    .build();

            Assert.assertNotNull(entity);
            Assert.assertNotEquals(previous, (byte) entity.getPrimaryKey().getValue());

            previous = (byte) entity.getPrimaryKey().getValue();
        }
    }
}