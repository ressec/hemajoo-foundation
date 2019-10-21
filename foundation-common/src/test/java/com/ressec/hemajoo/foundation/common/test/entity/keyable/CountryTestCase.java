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

import com.ressec.hemajoo.foundation.common.entity.keyable.IKey;
import com.ressec.hemajoo.foundation.common.entity.keyable.Key;
import com.ressec.hemajoo.foundation.common.entity.keyable.KeyManager;
import com.ressec.hemajoo.foundation.common.test.entity.keyable.model.TestKeyableCountry;
import com.ressec.hemajoo.foundation.common.test.entity.keyable.model.TestKeyableNoKey;
import org.junit.*;

import java.util.List;

/**
 * Test case for the {@link Key} entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class CountryTestCase
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
//        KeyManager.getInstance().clear(Keyable.class);
    }

    /**
     * Test the creation of a keyable country and its registration against the key manager.
     */
    @Test
    public final void testCreateCountry()
    {
        TestKeyableCountry france = TestKeyableCountry.builder()
                .name("France")
                .numericCode(250)
                .iso3("FRA")
                .iso2("FR")
                .build();

        Assert.assertNotNull(france);
    }

    /**
     * Test the retrieving of a keyable country through the key manager.
     */
    @Test
    public final void testRetrieveCountry()
    {
        TestKeyableCountry germany = TestKeyableCountry.builder()
                .name("Germany")
                .numericCode(276)
                .iso3("DEU")
                .iso2("DE")
                .build();

        Assert.assertNotNull(germany);

        @SuppressWarnings("unchecked")
        List<TestKeyableNoKey> countries = (List<TestKeyableNoKey>) germany.from("iso3", "DEU");
        Assert.assertEquals(1, countries.size());

        TestKeyableNoKey country = (TestKeyableNoKey) germany.firstFrom("numericCode", 276);
        Assert.assertNotNull(country);
    }

    /**
     * Test the retrieving of a keyable country through the key manager.
     */
    @Test
    public final void testRetrieveOneCountry()
    {
        TestKeyableCountry germany = TestKeyableCountry.builder()
                .name("Germany")
                .numericCode(276)
                .iso3("DEU")
                .iso2("DE")
                .build();

        Assert.assertNotNull(germany);

        TestKeyableCountry country = (TestKeyableCountry) germany.from("iso3", "DEU").get(0);
        Assert.assertNotNull(country);
    }

    /**
     * Test the unregistering of a specific keyable entity and all of its keys.
     */
    @Test
    public final void testUnregisterKeyable()
    {
        TestKeyableCountry france = TestKeyableCountry.builder()
                .name("France")
                .numericCode(250)
                .iso3("FRA")
                .iso2("FR")
                .build();

        IKey key = france.getKey();

        Assert.assertNotNull(france);

        KeyManager.getInstance().unregister(france);

        TestKeyableCountry country = TestKeyableCountry.get(key);

        Assert.assertNull(country);
    }

    /**
     * Test the unregistering of a specific key for a keyable.
     */
    @Test
    public final void testUnregisterKeyByName()
    {
        TestKeyableCountry france = TestKeyableCountry.builder()
                .name("France")
                .numericCode(250)
                .iso3("FRA")
                .iso2("FR")
                .build();

        Assert.assertNotNull(france);

        KeyManager.getInstance().unregisterKeysByName(TestKeyableCountry.class, "numericCode");

        TestKeyableCountry country = TestKeyableCountry.get("numericCode", "250");

        Assert.assertNull(country);
    }

    /**
     * Test the unregistering of a specific key for a keyable.
     */
    @Test
    public final void testUnregisterKeyByKeyType()
    {
        TestKeyableCountry france = TestKeyableCountry.builder()
                .name("France")
                .numericCode(250)
                .iso3("FRA")
                .iso2("FR")
                .build();

        Assert.assertNotNull(france);

        KeyManager.getInstance().unregisterKeysByKeyType(TestKeyableCountry.class, String.class);

        TestKeyableCountry result = TestKeyableCountry.get("iso3", "FRA");

        Assert.assertNull(result);
    }

    /**
     * Test the unregistering of a specific key for a keyable.
     */
    @Test
    public final void testUnregisterKeyByKeyableType()
    {
        TestKeyableCountry france = TestKeyableCountry.builder()
                .name("France")
                .numericCode(250)
                .iso3("FRA")
                .iso2("FR")
                .build();

        Assert.assertNotNull(france);

        KeyManager.getInstance().unregisterKeysByKeyableType(TestKeyableCountry.class);

        TestKeyableCountry result = TestKeyableCountry.get("iso3", "FRA");

        Assert.assertNull(result);
    }

    /**
     * Test the retrieving of a keyable entity based on a given key.
     */
    @Test
    public final void testRetrieveEntityByKeyableType()
    {
        TestKeyableCountry france = TestKeyableCountry.builder()
                .name("France")
                .numericCode(250)
                .iso3("FRA")
                .iso2("FR")
                .build();

        Assert.assertNotNull(france);

        @SuppressWarnings("unchecked")
        List<TestKeyableCountry> countries = (List<TestKeyableCountry>) KeyManager.getInstance().get(TestKeyableCountry.class, "numericCode", 250);

        Assert.assertEquals(1, countries.size());
    }
}

