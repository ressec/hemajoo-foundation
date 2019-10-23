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
import com.ressec.hemajoo.foundation.common.entity.keyable.IKeyable;
import com.ressec.hemajoo.foundation.common.entity.keyable.KeyManager;
import com.ressec.hemajoo.foundation.common.entity.keyable.Keyable;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for the {@link KeyableCountryWithPrimaryKey} entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class TestKeyableCountryWithPrimaryKey
{
    @Before
    public void setUp() throws Exception
    {
        KeyManager.getInstance().unregisterKeysByKeyableType(KeyableCountryWithPrimaryKey.class);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    /**
     * Ensure an empty entity can be created.
     */
    @Test
    public void expectSuccessToCreateEmptyKeyableWhenKeyableHasPrimaryKey()
    {
        IKeyable entity = KeyableCountryWithPrimaryKey.empty();
        Assert.assertNotNull(entity);
    }

    /**
     * Ensure an empty keyable entity return a {@link IKeyable} not null instance.
     */
    @Test
    public void expectSuccessToGetEmptyKeyable()
    {
        IKeyable entity = Keyable.empty();
        Assert.assertNotNull(entity);
    }

    /**
     * Ensure the primary key of a keyable is retrievable.
     */
    @Test
    public void expectSuccessToRetrievePrimaryKey()
    {
        String name = "Spain";

        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name(name)
                .build();
        Assert.assertNotNull(entity);

        IKey primary = entity.getKey();

        Assert.assertNotNull(primary);
        Assert.assertEquals("name", primary.getName());
        Assert.assertEquals(name, primary.getValue());
        Assert.assertEquals(String.class, primary.getType());
        Assert.assertTrue(primary.isMandatory());
        Assert.assertTrue(primary.isUnique());
        Assert.assertTrue(primary.isPrimary());
    }

    /**
     * Ensure a keyable is registered against the key manager when it defines a primary key.
     */
    @Test
    public void expectSuccessToRegisterKeyableWithPrimaryKey()
    {
        Assert.assertEquals(0, KeyManager.getInstance().countKeyable(KeyableCountryWithPrimaryKey.class));

        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name("France")
                .build();
        Assert.assertNotNull(entity);

        int count = KeyManager.getInstance().countKeyable(KeyableCountryWithPrimaryKey.class);
        Assert.assertEquals(1, count);
    }

    /**
     * Ensure a keyable is retrievable from the key manager using its primary key name.
     */
    @Test
    public void expectSuccessToRetrieveKeyableByPrimaryKeyName()
    {
        String name = "Germany";

        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name(name)
                .build();
        Assert.assertNotNull(entity);

        KeyableCountryWithPrimaryKey object = (KeyableCountryWithPrimaryKey) KeyManager.getInstance().get(KeyableCountryWithPrimaryKey.class, "name", name).get(0);
        Assert.assertNotNull(object);
        Assert.assertEquals(name, object.getName());
    }

    /**
     * Ensure a keyable is retrievable from the key manager using static service based on key field value.
     */
    @Test
    public void expectSuccessToRetrieveKeyableByPrimaryKeyFieldValue()
    {
        String name = "Italy";

        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name(name)
                .iso3("ITA")
                .build();
        Assert.assertNotNull(entity);

        KeyableCountryWithPrimaryKey o = (KeyableCountryWithPrimaryKey) Keyable.get(KeyableCountryWithPrimaryKey.class, "name", name);
        Assert.assertEquals(name, o.getName());
    }

    /**
     * Ensure a keyable is retrievable by iys primary key.
     */
    @Test
    public void expectSuccessToRetrieveKeyableByPrimaryKey()
    {
        String name = "Spain";

        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name(name)
                .build();
        Assert.assertNotNull(entity);

        IKey primary = entity.getKey();

        KeyableCountryWithPrimaryKey country = (KeyableCountryWithPrimaryKey) Keyable.get(KeyableCountryWithPrimaryKey.class, primary);

        Assert.assertNotNull(country);
        Assert.assertEquals(name, country.getName());
    }
}