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
import com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.model.KeyableWithPrimaryAutoKeyAsPrimitiveLong;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for the {@link KeyableWithPrimaryAutoKeyAsPrimitiveLong} entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public class TestKeyableWithPrimaryAutoKeyAsPrimitiveLong
{
    @Before
    public void setUp() throws Exception
    {
        KeyManager.getInstance().unregisterKeysByKeyableType(KeyableWithPrimaryAutoKeyAsPrimitiveLong.class);
    }

    @After
    public void tearDown() throws Exception
    {
        KeyManager.getInstance().shutdown();
    }

    /**
     * Ensure the success to create a keyable with an auto primary key of type primitive long.
     */
    @Test
    public void expectSuccessToCreateKeyableWithPrimaryAutoKeyAsPrimitiveLong()
    {
        KeyableWithPrimaryAutoKeyAsPrimitiveLong entity = KeyableWithPrimaryAutoKeyAsPrimitiveLong.builder()
                .build();
        Assert.assertNotNull(entity);
    }

    /**
     * Ensure the success to create a keyable with an auto primary key of type primitive long.
     */
    @Test
    public void expectSuccessToCreateKeyableWithPrimaryAutoKeyAsPrimitiveLongWithValueEqualZero()
    {
        KeyableWithPrimaryAutoKeyAsPrimitiveLong entity = KeyableWithPrimaryAutoKeyAsPrimitiveLong.builder()
                .primitiveLong(0L)
                .build();
        Assert.assertNotNull(entity);
    }

    /**
     * Ensure the failure to create a keyable with an auto primary key of type primitive integer when the value
     * is greater than zero.
     */
    @Test(expected = KeyException.class)
    public void expectFailureToCreateKeyableWithPrimaryAutoKeyAsPrimitiveLongWithValueGreaterThanZero()
    {
        KeyableWithPrimaryAutoKeyAsPrimitiveLong entity = KeyableWithPrimaryAutoKeyAsPrimitiveLong.builder()
                .primitiveLong(154456555664L)
                .build();
        Assert.assertNotNull(entity);
    }

    /**
     * Ensure the success to create one hundred keyables in less than 100 milliseconds with an auto primary key of
     * type primitive long when the value is greater than zero.
     */
    @Test(timeout = 100)
    public void expectSuccessToCreateOneHundredKeyableWithPrimaryAutoKeyAsPrimitiveLong()
    {
        long previous = 0;
        KeyableWithPrimaryAutoKeyAsPrimitiveLong entity;

        for (int i = 0; i < 100; i++)
        {
            entity = KeyableWithPrimaryAutoKeyAsPrimitiveLong.builder()
                    .primitiveLong(0)
                    .build();

            Assert.assertNotNull(entity);
            Assert.assertNotEquals(previous, (long) entity.getPrimaryKey().getValue());

            previous = (long) entity.getPrimaryKey().getValue();
        }

        int count = KeyManager.getInstance().countByKeyableClass(KeyableWithPrimaryAutoKeyAsPrimitiveLong.class);
        Assert.assertEquals(100, count);

        count = KeyManager.getInstance().countByKeyName(KeyableWithPrimaryAutoKeyAsPrimitiveLong.class, "primitiveLong");
        Assert.assertEquals(100, count);
    }
}