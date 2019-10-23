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

import com.ressec.hemajoo.foundation.common.entity.keyable.Key;
import com.ressec.hemajoo.foundation.common.entity.keyable.KeyManager;
import com.ressec.hemajoo.foundation.common.entity.keyable.Keyable;
import com.ressec.hemajoo.foundation.common.test.entity.keyable.model.KeyableCountryWithoutKey;
import org.junit.*;

/**
 * Test case for the {@link Keyable} entity class hierarchy.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class KeyTestCase
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
        KeyManager.getInstance().unregisterKeysByKeyableType(KeyableCountryWithoutKey.class);
    }

    /**
     * Invoked after each test method run.
     */
    @After
    public final void tearDown()
    {
    }

    /**
     * Test the creation of a key.
     */
    @Test
    public final void createKey()
    {
        KeyableCountryWithoutKey country = KeyableCountryWithoutKey.builder()
                .numericCode(250)
                .iso2("FR")
                .iso3("FRA")
                .name("France")
                .officialName("Republic of France")
                .build();

        Assert.assertNotNull(country);

        Key key = Key.builder()
                .keyable(country)
                .name("numeric")
                .value(250)
                .build();

        Assert.assertNotNull(key);
    }
}

