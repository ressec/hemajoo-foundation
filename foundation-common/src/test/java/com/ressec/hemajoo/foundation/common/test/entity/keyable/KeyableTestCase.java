/*
 * (C) Copyright Hemajoo Systems Inc.  2019 - All Rights Reserved
 * -----------------------------------------------------------------------------------------------
 * All information contained herein is, and remains the property of
 * Hemajoo Inc. and its suppliers, if any. The intellectual and technical
 * concepts contained herein are proprietary to Hemajoo Inc. and its
 * suppliers and may be covered by U.S. and Foreign Patents, patents
 * in process, and are protected by trade secret or copyright law.
 *
 * Dissemination of this information or reproduction of this material
 * is strictly forbidden unless prior written permission is obtained from
 * Hemajoo Systems Inc.
 * -----------------------------------------------------------------------------------------------
 */
package com.ressec.hemajoo.foundation.common.test.entity.keyable;

import com.ressec.hemajoo.foundation.common.entity.keyable.KeyException;
import com.ressec.hemajoo.foundation.common.entity.keyable.KeyManager;
import com.ressec.hemajoo.foundation.common.entity.keyable.Keyable;
import com.ressec.hemajoo.foundation.common.test.entity.keyable.model.TestKeyableMultipleKey;
import com.ressec.hemajoo.foundation.common.test.entity.keyable.model.TestKeyableNoKey;
import com.ressec.hemajoo.foundation.common.test.entity.keyable.model.TestKeyableOnlyPrimaryKey;
import org.junit.*;

/**
 * Test case for the {@link Keyable} entities hierarchy.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class KeyableTestCase
{
    /**
     * Invoked before each test class run.
     */
    @BeforeClass
    public static void setUpBeforeClass()
    {
    }

    /**
     * Invoked after each test class run.
     */
    @AfterClass
    public static void tearDownAfterClass()
    {
    }

    /**
     * Invoked before each test method run.
     */
    @Before
    public final void setUp()
    {
        KeyManager.getInstance().unregisterKeysByKeyableType(TestKeyableNoKey.class);
    }

    /**
     * Invoked after each test method run.
     */
    @After
    public final void tearDown()
    {
    }

    /**
     * Ensure a {@link KeyException} is thrown when we try to register a keyable entity not having a primary key set.
     */
    @Test(expected = KeyException.class)
    public final void testErrorOfCreateKeyableWhenKeyableHasNoPrimaryKey()
    {
        TestKeyableNoKey country = TestKeyableNoKey.builder()
                .name("Alexander")
                .build();
    }

    /**
     * Test the creation of a keyable entity and the registration of the keyable and its primary by the key manager.
     */
    @Test
    public final void testSuccessOfCreateKeyableWhenKeyableWithPrimaryKey()
    {
        TestKeyableOnlyPrimaryKey entity = TestKeyableOnlyPrimaryKey.builder()
                .name("Alexander")
                .build();

        Assert.assertNotNull(entity);
    }

    /**
     * Test the creation of a keyable entity and the registration of the keyable and its keys by the key manager.
     */
    @Test
    public final void testSuccessOfCreateKeyableWhenKeyableWithPrimaryAndAlternateKey()
    {
        TestKeyableMultipleKey entity = TestKeyableMultipleKey.builder()
                .name("Alexander")
                .code("ALEX")
                .build();

        Assert.assertNotNull(entity);
    }
}

