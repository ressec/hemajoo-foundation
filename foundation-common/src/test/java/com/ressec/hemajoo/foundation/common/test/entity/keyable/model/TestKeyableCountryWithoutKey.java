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
package com.ressec.hemajoo.foundation.common.test.entity.keyable.model;

import com.ressec.hemajoo.foundation.common.entity.keyable.IKey;
import com.ressec.hemajoo.foundation.common.entity.keyable.KeyException;
import com.ressec.hemajoo.foundation.common.entity.keyable.KeyManager;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for the {@link KeyableCountryWithoutKey} entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class TestKeyableCountryWithoutKey
{
    @Before
    public void setUp() throws Exception
    {
        KeyManager.getInstance().unregisterKeysByKeyableType(KeyableCountry.class);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    /**
     * An empty entity can be created even if the keyable has no key!
     */
    @Test
    public void expectSuccessToCreateEmptyKeyableWhenKeyableHasNoKey()
    {
        KeyableCountry entity = KeyableCountry.empty();

        Assert.assertNotNull(entity);
    }

    /**
     * An empty keyable entity can query its own fields that will return null or 0!
     */
    @Test
    public void expectSuccessToQueryFieldOfEmptyKeyableWhenKeyableHasNoKey()
    {
        KeyableCountry entity = KeyableCountry.empty();
        Assert.assertNotNull(entity);

        Assert.assertNull(entity.getIso3());
        Assert.assertEquals(0, entity.getNumeric());
    }

    /**
     * A key instance is not retrievable on a keyable empty instance!
     */
    @Test(expected = KeyException.class)
    public void expectFailureToGetKeyWhenKeyableHasNoKey()
    {
        KeyableCountry entity = KeyableCountry.empty();
        IKey key = entity.getKey();
    }

    /**
     * A keyable entity is not buildable if it does not set a primary key!
     */
    @Test(expected = KeyException.class)
    public void expectFailureToBuildKeyableWhenKeyableHasNoKey()
    {
        KeyableCountryWithoutKey entity = KeyableCountryWithoutKey.builder()
                .iso3("FRA")
                .build();
    }

    /**
     * An empty keyable entity can query its own fields that will return null or 0!
     */
    @Test
    public void expectSuccessTo()
    {
        KeyableCountry entity = KeyableCountry.empty();
        Assert.assertNotNull(entity);

        IKey key = entity.getKey("name", "name");

        System.out.println("");
    }
}