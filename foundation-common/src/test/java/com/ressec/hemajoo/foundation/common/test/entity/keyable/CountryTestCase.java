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
import com.ressec.hemajoo.foundation.common.test.entity.keyable.model.KeyableCountry;
import com.ressec.hemajoo.foundation.common.test.entity.keyable.model.KeyableCountryWithoutKey;
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
        KeyManager.getInstance().unregisterKeysByKeyableType(KeyableCountry.class);
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
        KeyableCountryWithoutKey france = KeyableCountryWithoutKey.builder()
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
        KeyableCountryWithoutKey germany = KeyableCountryWithoutKey.builder()
                .name("Germany")
                .numericCode(276)
                .iso3("DEU")
                .iso2("DE")
                .build();

        Assert.assertNotNull(germany);

        @SuppressWarnings("unchecked")
        List<KeyableCountry> countries = (List<KeyableCountry>) germany.from("iso3", "DEU");
        Assert.assertEquals(1, countries.size());

        KeyableCountry country = (KeyableCountry) germany.firstFrom("numericCode", 276);
        Assert.assertNotNull(country);
    }

    /**
     * Test the retrieving of a keyable country through the key manager.
     */
    @Test
    public final void testRetrieveOneCountry()
    {
        KeyableCountryWithoutKey germany = KeyableCountryWithoutKey.builder()
                .name("Germany")
                .numericCode(276)
                .iso3("DEU")
                .iso2("DE")
                .build();

        Assert.assertNotNull(germany);

        KeyableCountryWithoutKey country = (KeyableCountryWithoutKey) germany.from("iso3", "DEU").get(0);
        Assert.assertNotNull(country);
    }

    /**
     * Test the unregistering of a specific keyable entity and all of its keys.
     */
    @Test
    public final void testUnregisterKeyable()
    {
        KeyableCountryWithoutKey france = KeyableCountryWithoutKey.builder()
                .name("France")
                .numericCode(250)
                .iso3("FRA")
                .iso2("FR")
                .build();

        IKey key = france.getKey();

        Assert.assertNotNull(france);

        KeyManager.getInstance().unregister(france);

        KeyableCountryWithoutKey country = KeyableCountryWithoutKey.get(key);

        Assert.assertNull(country);
    }

    /**
     * Test the unregistering of a specific key for a keyable.
     */
    @Test
    public final void testUnregisterKeyByName()
    {
        KeyableCountryWithoutKey france = KeyableCountryWithoutKey.builder()
                .name("France")
                .numericCode(250)
                .iso3("FRA")
                .iso2("FR")
                .build();

        Assert.assertNotNull(france);

        KeyManager.getInstance().unregisterKeysByName(KeyableCountryWithoutKey.class, "numericCode");

        KeyableCountryWithoutKey country = KeyableCountryWithoutKey.get("numericCode", "250");

        Assert.assertNull(country);
    }

    /**
     * Test the unregistering of a specific key for a keyable.
     */
    @Test
    public final void testUnregisterKeyByKeyType()
    {
        KeyableCountryWithoutKey france = KeyableCountryWithoutKey.builder()
                .name("France")
                .numericCode(250)
                .iso3("FRA")
                .iso2("FR")
                .build();

        Assert.assertNotNull(france);

        KeyManager.getInstance().unregisterKeysByKeyType(KeyableCountryWithoutKey.class, String.class);

        KeyableCountryWithoutKey result = KeyableCountryWithoutKey.get("iso3", "FRA");

        Assert.assertNull(result);
    }

    /**
     * Test the unregistering of a specific key for a keyable.
     */
    @Test
    public final void testUnregisterKeyByKeyableType()
    {
        KeyableCountryWithoutKey france = KeyableCountryWithoutKey.builder()
                .name("France")
                .numericCode(250)
                .iso3("FRA")
                .iso2("FR")
                .build();

        Assert.assertNotNull(france);

        KeyManager.getInstance().unregisterKeysByKeyableType(KeyableCountryWithoutKey.class);

        KeyableCountryWithoutKey result = KeyableCountryWithoutKey.get("iso3", "FRA");

        Assert.assertNull(result);
    }

    /**
     * Test the retrieving of a keyable entity based on a given key.
     */
    @Test
    public final void testRetrieveEntityByKeyableType()
    {
        KeyableCountryWithoutKey france = KeyableCountryWithoutKey.builder()
                .name("France")
                .numericCode(250)
                .iso3("FRA")
                .iso2("FR")
                .build();

        Assert.assertNotNull(france);

        @SuppressWarnings("unchecked")
        List<KeyableCountryWithoutKey> countries = (List<KeyableCountryWithoutKey>) KeyManager.getInstance().get(
                KeyableCountryWithoutKey.class, "numericCode", 250);

        Assert.assertEquals(1, countries.size());
    }
}

