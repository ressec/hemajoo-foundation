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

import java.util.List;

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
     * Ensure the success to create an empty keyable entity.
     */
    @Test
    public void expectSuccessToCreateEmptyKeyableWhenKeyableHasPrimaryKey()
    {
        IKeyable entity = KeyableCountryWithPrimaryKey.empty();
        Assert.assertNotNull(entity);
    }

    /**
     * Ensure the success to retrieve the first keyable using the static empty service and the key name and value.
     */
    @Test
    public void expectSuccessToRetrieveFirstFromKeyableUsingEmptyWithNameAndValue()
    {
        String name = "USA";

        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name(name)
                .build();

        IKeyable country = Keyable.empty().firstFrom(KeyableCountryWithPrimaryKey.class, "name", name);
        Assert.assertNotNull(country);
    }

    /**
     * Ensure the success to retrieve the first keyable using the static empty service and the key.
     */
    @Test
    public void expectSuccessToRetrieveFirstFromKeyableUsingEmptyWithKey()
    {
        String name = "Spain";

        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name(name)
                .build();

        IKeyable country = Keyable.empty().firstFrom(KeyableCountryWithPrimaryKey.class, entity.getKey());
        Assert.assertNotNull(country);
    }

    /**
     * Ensure the success to retrieve a list of keyables using the static empty service and the key.
     */
    @Test
    public void expectSuccessToRetrieveKeyableListUsingEmptyWithKey()
    {
        String name = "France";

        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name(name)
                .build();

        List<? extends IKeyable> countries = Keyable.empty().from(KeyableCountryWithPrimaryKey.class, entity.getKey());
        Assert.assertNotNull(countries);
        Assert.assertFalse(countries.isEmpty());
    }

    /**
     * Ensure the success to retrieve a list of keyables using the static empty service and the key name and value.
     */
    @Test
    public void expectSuccessToRetrieveKeyableListUsingEmptyWithKeyNameAndValue()
    {
        String name = "France";

        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name(name)
                .build();

        List<? extends IKeyable> countries = Keyable.empty().from(KeyableCountryWithPrimaryKey.class, "name", name);
        Assert.assertNotNull(countries);
        Assert.assertFalse(countries.isEmpty());
    }

    /**
     * Ensure the success to retrieve null when querying for an unknown keyable.
     */
    @Test
    public void expectSuccessToRetrieveNullWhenQueryingNonExistentKeyable()
    {
        String name = "France";

        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name(name)
                .build();

        IKeyable country = Keyable.empty().firstFrom(KeyableCountryWithPrimaryKey.class, "name", "Switzerland");
        Assert.assertNull(country);
    }

    /**
     * Ensure the success to retrieve null while querying for keyable when key name does not exist.
     */
    @Test
    public void expectSuccessToRetrieveNullWhenQueryingNonExistentKeyName()
    {
        String name = "France";

        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name(name)
                .build();

        IKeyable country = Keyable.empty().firstFrom(KeyableCountryWithPrimaryKey.class, "other", "France");
        Assert.assertNull(country);
    }

    /**
     * Ensure the success to retrieve null while querying for keyable when key value does not exist.
     */
    @Test
    public void expectSuccessToRetrieveNullWhenQueryingNonExistentKeyValue()
    {
        String name = "France";

        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name(name)
                .build();

        IKeyable country = Keyable.empty().firstFrom(KeyableCountryWithPrimaryKey.class, "name", "Switzerland");
        Assert.assertNull(country);
    }

    /**
     * Ensure to raise a NullPointerException while querying for keyable when key name is null.
     */
    @Test(expected = NullPointerException.class)
    public void expectNullPointerExceptionWhenUsingNullKeyName()
    {
        String name = "France";

        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name(name)
                .build();

        IKeyable country = Keyable.empty().firstFrom(KeyableCountryWithPrimaryKey.class, null, "Switzerland");
    }

    /**
     * Ensure to raise a NullPointerException while querying for keyable when key value is null.
     */
    @Test(expected = NullPointerException.class)
    public void expectNullPointerExceptionWhenUsingNullKeyValue()
    {
        String name = "France";

        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name(name)
                .build();

        IKeyable country = Keyable.empty().firstFrom(KeyableCountryWithPrimaryKey.class, "name", null);
    }

    /**
     * Ensure the success to retrieve null when querying based on a non-existent key name.
     */
    @Test(expected = NullPointerException.class)
    public void expectNullPointerExceptionWhenUsingNullKey()
    {
        String name = "France";

        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name(name)
                .build();

        IKeyable country = Keyable.empty().firstFrom(KeyableCountryWithPrimaryKey.class, null);
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
    public void expectSuccessToRetrieveKeyableByPrimaryKeyNameAndValue()
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
     * Ensure a keyable is retrievable by its primary key.
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