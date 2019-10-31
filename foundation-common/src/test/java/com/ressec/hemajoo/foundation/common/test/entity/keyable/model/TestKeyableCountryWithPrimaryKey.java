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
    public void setUp()
    {
        KeyManager.getInstance().unregisterKeysByKeyableType(KeyableCountryWithPrimaryKey.class);
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Ensure the success to create a keyable with only the primary key value set.
     */
    @Test
    public void expectSuccessToCreateKeyableWithPrimaryKey()
    {
        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name("France")
                .build();
        Assert.assertNotNull(entity);
    }

    /**
     * Ensure an exception is raised while trying to retrieve a keyable when the query is based on a non-existent key name.
     */
    @Test
    public void expectSuccessToGetNullKeyableWhileQueryingNonExistentKeyName()
    {
        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name("France")
                .iso2("FR")
                .build();
        Assert.assertNotNull(entity);

        Keyable.retrieve(KeyableCountryWithPrimaryKey.class, "iso2", "FR");
    }

    /**
     * Ensure the success to retrieve a null keyable when the query is based on a non-existent key value.
     */
    @Test
    public void expectSuccessToRetrieveNullKeyableWhenQueryingOnNonExistentKeyValue()
    {
        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name("France")
                .iso2("FR")
                .build();
        Assert.assertNotNull(entity);

        IKeyable country = Keyable.retrieve(KeyableCountryWithPrimaryKey.class, "name", "GE");
        Assert.assertNull(country);
    }

    /**
     * Ensure the success to retrieve a keyable using a key entity.
     */
    @Test
    public void expectSuccessToRetrieveKeyableUsingKey()
    {
        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name("France")
                .build();

        IKeyable country = Keyable.retrieve(KeyableCountryWithPrimaryKey.class, entity.getKey());
        Assert.assertNotNull(country);
    }

    /**
     * Ensure the success to retrieve a keyable using a key name and value.
     */
    @Test
    public void expectSuccessToRetrieveKeyableUsingKeyNameAndValue()
    {
        KeyableCountryWithPrimaryKey.builder()
                .name("France")
                .build();

        IKeyable country = Keyable.retrieve(KeyableCountryWithPrimaryKey.class, "name", "France");
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

        List<? extends IKeyable> countries = Keyable.query(KeyableCountryWithPrimaryKey.class, entity.getKey());
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

        KeyableCountryWithPrimaryKey.builder()
                .name(name)
                .build();

        List<? extends IKeyable> countries = Keyable.query(KeyableCountryWithPrimaryKey.class, "name", name);
        Assert.assertNotNull(countries);
        Assert.assertFalse(countries.isEmpty());
    }

    /**
     * Ensure a key manager exception is raised while trying to retrieve a keyable when querying on a non-existent key name.
     */
    @Test
    public void expectSuccessToGetNullKeyableWhenQueryingNonExistentKeyName()
    {
        String name = "France";

        KeyableCountryWithPrimaryKey.builder()
                .name(name)
                .build();

        IKeyable entity = Keyable.retrieve(KeyableCountryWithPrimaryKey.class, "other", "France");
        Assert.assertNotNull(entity);
    }

    /**
     * Ensure the success to retrieve null while querying for keyable when key value does not exist.
     */
    @Test
    public void expectSuccessToRetrieveNullWhenQueryingNonExistentKeyValue()
    {
        String name = "France";

        KeyableCountryWithPrimaryKey.builder()
                .name(name)
                .build();

        IKeyable country = Keyable.retrieve(KeyableCountryWithPrimaryKey.class, "name", "Switzerland");
        Assert.assertNull(country);
    }

    /**
     * Ensure an exception is raised while trying to retrieve a keyable when querying on a null key name.
     */
    @Test(expected = NullPointerException.class)
    public void expectNullPointerExceptionWhenUsingNullKeyName()
    {
        String name = "France";

        KeyableCountryWithPrimaryKey.builder()
                .name(name)
                .build();

        Keyable.retrieve(KeyableCountryWithPrimaryKey.class, null, "Switzerland");
    }

    /**
     * Ensure an exception is raised while trying to retrieve a keyable when querying on a null key value.
     */
    @Test(expected = NullPointerException.class)
    public void expectNullPointerExceptionWhenUsingNullKeyValue()
    {
        String name = "France";

        KeyableCountryWithPrimaryKey.builder()
                .name(name)
                .build();

        Keyable.retrieve(KeyableCountryWithPrimaryKey.class, "name", null);
    }

    /**
     * Ensure an exception is raised while trying to retrieve a keyable when querying on a null key.
     */
    @Test(expected = NullPointerException.class)
    public void expectNullPointerExceptionWhenUsingNullKey()
    {
        String name = "France";

        KeyableCountryWithPrimaryKey.builder()
                .name(name)
                .build();

        Keyable.retrieve(KeyableCountryWithPrimaryKey.class, null);
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
        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyableCountryWithPrimaryKey.class));

        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name("France")
                .build();
        Assert.assertNotNull(entity);

        int count = KeyManager.getInstance().countByKeyableClass(KeyableCountryWithPrimaryKey.class);
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

        KeyableCountryWithPrimaryKey o = (KeyableCountryWithPrimaryKey) Keyable.retrieve(KeyableCountryWithPrimaryKey.class, "name", name);
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

        KeyableCountryWithPrimaryKey country = (KeyableCountryWithPrimaryKey) Keyable.retrieve(KeyableCountryWithPrimaryKey.class, primary);

        Assert.assertNotNull(country);
        Assert.assertEquals(name, country.getName());
    }

    /**
     * Ensure a keyable cannot be retrieved after it has been unregistered.
     */
    @Test
    public void expectSuccessToUnregisterKeyableInstance()
    {
        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name("France")
                .build();

        KeyManager.getInstance().unregister(entity);

        IKeyable country = Keyable.retrieve(KeyableCountryWithPrimaryKey.class, "name", "France");

        Assert.assertNull(country);
    }

    /**
     * Ensure a keyable cannot be retrieved after it has been unregistered by its key type.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByKeyType()
    {
        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name("France")
                .build();

        KeyManager.getInstance().unregisterKeysByKeyType(KeyableCountryWithPrimaryKey.class, String.class);

        IKeyable country = Keyable.retrieve(KeyableCountryWithPrimaryKey.class, "name", "France");

        Assert.assertNull(country);
    }

    /**
     * Ensure a keyable cannot be retrieved after it has been unregistered by its keyable class.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByKeyableType()
    {
        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name("France")
                .build();

        KeyManager.getInstance().unregisterKeysByKeyableType(KeyableCountryWithPrimaryKey.class);

        IKeyable country = Keyable.retrieve(KeyableCountryWithPrimaryKey.class, "name", "France");

        Assert.assertNull(country);
    }

    /**
     * Ensure a keyable cannot be retrieved after it has been unregistered by its keyable class.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByKeyName()
    {
        KeyableCountryWithPrimaryKey entity = KeyableCountryWithPrimaryKey.builder()
                .name("France")
                .build();

        KeyManager.getInstance().unregisterKeysByName(KeyableCountryWithPrimaryKey.class, "name");

        IKeyable country = Keyable.retrieve(KeyableCountryWithPrimaryKey.class, "name", "France");

        Assert.assertNull(country);
    }
}