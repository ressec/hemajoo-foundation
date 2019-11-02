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
import com.ressec.hemajoo.foundation.common.entity.keyable.exception.KeyException;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.UUID;

/**
 * Test case for the {@link KeyableCountryWithPrimaryAndAlternateKey} entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class TestKeyableCountryWithPrimaryAndAlternateKey
{
    @Before
    public void setUp()
    {
        KeyManager.getInstance().unregisterKeysByKeyableType(KeyableCountryWithPrimaryAndAlternateKey.class);
    }

    @After
    public void tearDown()
    {
    }

    /**
     * Ensure a {@link KeyException} is raised when a keyable is created and one of its mandatory key value is not set.
     */
    @Test(expected = KeyException.class)
    public void expectFailureWhenMandatoryKeyValueIsNotSet()
    {
        // The iso3 mandatory key value is not set at creation time.
        KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .comment("Hemajoo")
                .build();
    }

    /**
     * Ensure the success when querying for keyables using a non-unique alternate key name and value.
     */
    @Test
    public void expectSuccessWhenQueryingForKeyableListUsingNonUniqueAlternateKeyNameAndValue()
    {
        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .iso3("FRA")
                .comment("Hemajoo")
                .build();
        Assert.assertNotNull(entity);

        entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("Belgium")
                .iso2("BE")
                .iso3("BEL")
                .comment("Hemajoo")
                .build();
        Assert.assertNotNull(entity);

        List<? extends IKeyable> countries = Keyable.query(KeyableCountryWithPrimaryAndAlternateKey.class, "comment", "Hemajoo");
        Assert.assertNotNull(countries);
        Assert.assertTrue(countries.size() > 1);
    }

    /**
     * Ensure the success when querying for a keyable using an alternate key name and value.
     */
    @Test
    public void expectSuccessWhenQueryingForKeyableUsingAlternateKeyNameAndValue()
    {
        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .iso3("FRA")
                .comment("Hello")
                .build();
        Assert.assertNotNull(entity);

        IKeyable france = Keyable.retrieve(KeyableCountryWithPrimaryAndAlternateKey.class, "iso3", "FRA");
        Assert.assertNotNull(france);
    }

    /**
     * Ensure a null value is returned while trying to query a keyable based on a key name which is not a key.
     */
    @Test
    public void expectNullReturnedWhileQueryingKeyableOnNonExistentAlternateKeyName()
    {
        IKeyable entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .iso3("FRA")
                .comment("Hello")
                .build();
        Assert.assertNotNull(entity);

        IKeyable element = Keyable.retrieve(KeyableCountryWithPrimaryAndAlternateKey.class, "iso2", "FR");
        Assert.assertNull(element);
    }

    /**
     * Ensure the success to get a keyable using an alternate key.
     */
    @Test
    public void expectSuccessToGetKeyableUsingAlternateKey()
    {
        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .iso3("FRA")
                .comment("Hello")
                .build();

        IKeyable country = Keyable.retrieve(KeyableCountryWithPrimaryAndAlternateKey.class, entity.getPrimaryKey());
        Assert.assertNotNull(country);
    }

    /**
     * Ensure the success to retrieve a list of keyables using the static empty service and the key.
     */
    @Test
    public void expectSuccessToRetrieveKeyableListUsingEmptyWithKey()
    {
        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .iso3("FRA")
                .build();

        List<? extends IKeyable> countries = Keyable.query(KeyableCountryWithPrimaryAndAlternateKey.class, entity.getPrimaryKey());
        Assert.assertNotNull(countries);
        Assert.assertFalse(countries.isEmpty());
    }

    /**
     * Ensure the success to retrieve null when querying for an unknown keyable.
     */
    @Test
    public void expectSuccessToRetrieveNullWhenQueryingNonExistentKeyable()
    {
        KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .iso3("FRA")
                .build();

        IKeyable country = Keyable.retrieve(KeyableCountryWithPrimaryAndAlternateKey.class, "name", "Switzerland");
        Assert.assertNull(country);
    }

    /**
     * Ensure the success to retrieve null while querying for keyable when key name does not exist.
     */
    @Test
    public void expectSuccessToRetrieveNullWhenQueryingNonExistentKeyName()
    {
        KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .iso3("FRA")
                .build();

        IKeyable country = Keyable.retrieve(KeyableCountryWithPrimaryAndAlternateKey.class, "other", "France");
        Assert.assertNull(country);
    }

    /**
     * Ensure the success to retrieve null while querying for keyable when key value does not exist.
     */
    @Test
    public void expectSuccessToRetrieveNullWhenQueryingNonExistentKeyValue()
    {
        KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .iso3("FRA")
                .build();

        IKeyable country = Keyable.retrieve(KeyableCountryWithPrimaryAndAlternateKey.class, "name", "Switzerland");
        Assert.assertNull(country);
    }

    /**
     * Ensure an exception is raised while trying to query for keyable when key name is null.
     */
    @Test(expected = KeyException.class)
    public void expectNullPointerExceptionWhenUsingNullKeyName()
    {
        String name = "France";

        KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name(name)
                .build();

        Keyable.retrieve(KeyableCountryWithPrimaryAndAlternateKey.class, null, "Switzerland");
    }

    /**
     * Ensure to raise a NullPointerException while querying for keyable when key value is null.
     */
    @Test(expected = NullPointerException.class)
    public void expectNullPointerExceptionWhenUsingNullKeyValue()
    {
        KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .iso3("FRA")
                .build();

        Keyable.retrieve(KeyableCountryWithPrimaryAndAlternateKey.class, "name", null);
    }

    /**
     * Ensure an exception is raised when trying to retrieve a keyable using a null key.
     */
    @Test(expected = NullPointerException.class)
    public void expectNullPointerExceptionWhenUsingNullKey()
    {
        KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .iso3("FRA")
                .build();

        Keyable.retrieve(KeyableCountryWithPrimaryAndAlternateKey.class, null);
    }

    /**
     * Ensure the success to get a keyable primary key.
     */
    @Test
    public void expectSuccessToGetKeyablePrimaryKey()
    {
        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .iso3("FRA")
                .build();
        Assert.assertNotNull(entity);

        IKey primary = entity.getPrimaryKey();

        Assert.assertNotNull(primary);
        Assert.assertEquals("name", primary.getName());
        Assert.assertEquals("France", primary.getValue());
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
        KeyManager.getInstance().unregisterKeysByKeyableType(KeyableCountryWithPrimaryAndAlternateKey.class);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyableCountryWithPrimaryAndAlternateKey.class));

        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .iso3("FRA")
                .build();
        Assert.assertNotNull(entity);

        int count = KeyManager.getInstance().countByKeyableClass(KeyableCountryWithPrimaryAndAlternateKey.class);
        Assert.assertEquals(1, count);
    }

    /**
     * Ensure the success to retrievable a keyable using the name of its primary key.
     */
    @Test
    public void expectSuccessToRetrieveKeyableByPrimaryKeyName()
    {
        String name = "Germany";

        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name(name)
                .iso2("DE")
                .iso3("DEU")
                .build();
        Assert.assertNotNull(entity);

        KeyableCountryWithPrimaryAndAlternateKey object =
                (KeyableCountryWithPrimaryAndAlternateKey) KeyManager.getInstance().get(
                        KeyableCountryWithPrimaryAndAlternateKey.class,
                        "name",
                        name).get(0);
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

        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name(name)
                .iso3("ITA")
                .build();
        Assert.assertNotNull(entity);

        KeyableCountryWithPrimaryAndAlternateKey o = (KeyableCountryWithPrimaryAndAlternateKey) Keyable.retrieve(KeyableCountryWithPrimaryAndAlternateKey.class, "name", name);
        Assert.assertNotNull(o);
        Assert.assertEquals(name, o.getName());
    }

    /**
     * Ensure the success to retrieve a keyable using a keyable key.
     */
    @Test
    public void expectSuccessToRetrieveKeyableUsingKey()
    {
        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .iso3("FRA")
                .build();
        Assert.assertNotNull(entity);

        IKey primary = entity.getPrimaryKey();

        KeyableCountryWithPrimaryAndAlternateKey country = (KeyableCountryWithPrimaryAndAlternateKey) Keyable.retrieve(KeyableCountryWithPrimaryAndAlternateKey.class, primary);

        Assert.assertNotNull(country);
        Assert.assertEquals("France", country.getName());
    }

    /**
     * Ensure an exception is raised while trying to register a keyable with a unique key value twice.
     */
    @Test(expected = KeyException.class)
    public void expectFailureToRegisterUniqueKeyValueTwice()
    {
        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("Spain")
                .build();
        Assert.assertNotNull(entity);

        KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("Spain")
                .build();
    }

    /**
     * Ensure the success to register two keyables with the same key value when the key is not declared as unique.
     */
    @Test
    public void expectSuccessToRegisterNonUniqueKeyValueTwice()
    {
        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("Germany")
                .iso2("DE")
                .iso3("DEU")
                .comment("comment")
                .build();
        Assert.assertNotNull(entity);

        entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .iso3("FRA")
                .comment("comment")
                .build();
        Assert.assertNotNull(entity);
    }

    /**
     * Ensure the success to get the list of all keys of a keyable.
     */
    @Test
    public void expectSuccessToGetKeyList()
    {
        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("Germany")
                .iso2("DE")
                .iso3("DEU")
                .comment("comment")
                .build();
        Assert.assertNotNull(entity);

        List<IKey> keys = entity.getKeyList();
        Assert.assertEquals(10, keys.size());
    }

    /**
     * Ensure the success to get the list of all unique keys of a keyable.
     */
    @Test
    public void expectSuccessToGetUniqueKeyList()
    {
        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("Germany")
                .iso2("DE")
                .iso3("DEU")
                .comment("comment")
                .build();
        Assert.assertNotNull(entity);

        List<IKey> keys = entity.getUniqueKeyList();
        Assert.assertEquals(4, keys.size());
    }

    /**
     * Ensure the success to get the list of all mandatory keys of a keyable.
     */
    @Test
    public void expectSuccessToGetMandatoryKeyList()
    {
        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("Germany")
                .iso2("DE")
                .iso3("DEU")
                .comment("comment")
                .build();
        Assert.assertNotNull(entity);

        List<IKey> keys = entity.getMandatoryKeyList();
        Assert.assertEquals(2, keys.size());
    }

    /**
     * Ensure the success to get the list of all automatically generated keys of a keyable.
     */
    @Test
    public void expectSuccessToGetAutoKeyList()
    {
        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("Germany")
                .iso2("DE")
                .iso3("DEU")
                .comment("comment")
                .build();
        Assert.assertNotNull(entity);

        List<IKey> keys = entity.getAutoKeyList();
        Assert.assertEquals(1, keys.size());
    }

    /**
     * Ensure the success to get a list of keyables given the keyable class, the key name and value.
     */
    @Test
    public void expectSuccessToGetKeyableListByKeyNameAndValue()
    {
        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .iso3("FRA")
                .comment("comment")
                .build();
        Assert.assertNotNull(entity);

        entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("Germany")
                .iso2("DE")
                .iso3("DEU")
                .comment("comment")
                .build();
        Assert.assertNotNull(entity);

        List<IKeyable> countries = entity.getList(KeyableCountryWithPrimaryAndAlternateKey.class, "name", "France");
        Assert.assertTrue(countries.size() > 0);
    }

    /**
     * Ensure the success to get a list of keyables given the keyable class and a key instance.
     */
    @Test
    public void expectSuccessToGetKeyableListByKeyInstance()
    {
        KeyableCountryWithPrimaryAndAlternateKey france = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .iso3("FRA")
                .comment("comment")
                .build();
        Assert.assertNotNull(france);

        KeyableCountryWithPrimaryAndAlternateKey germany = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("Germany")
                .iso2("DE")
                .iso3("DEU")
                .comment("comment")
                .build();
        Assert.assertNotNull(germany);

        List<IKeyable> countries = germany.getList(KeyableCountryWithPrimaryAndAlternateKey.class, germany.getPrimaryKey());
        Assert.assertTrue(countries.size() > 0);
    }

    /**
     * Ensure the success to get a keyable given the keyable class, the key name and value.
     */
    @Test
    public void expectSuccessToGetKeyableByKeyNameAndValue()
    {
        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .iso3("FRA")
                .comment("comment")
                .build();
        Assert.assertNotNull(entity);

        entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("Germany")
                .iso2("DE")
                .iso3("DEU")
                .comment("comment")
                .build();
        Assert.assertNotNull(entity);

        KeyableCountryWithPrimaryAndAlternateKey country = (KeyableCountryWithPrimaryAndAlternateKey) entity.get(KeyableCountryWithPrimaryAndAlternateKey.class, "name", "France");
        Assert.assertNotNull(country);
        Assert.assertEquals("France", country.getName());
    }

    /**
     * Ensure the success to get a keyable given the keyable class and a key instance.
     */
    @Test
    public void expectSuccessToGetKeyableByKeyInstance()
    {
        KeyableCountryWithPrimaryAndAlternateKey france = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .iso3("FRA")
                .comment("comment")
                .build();
        Assert.assertNotNull(france);

        KeyableCountryWithPrimaryAndAlternateKey germany = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("Germany")
                .iso2("DE")
                .iso3("DEU")
                .comment("comment")
                .build();
        Assert.assertNotNull(germany);

        KeyableCountryWithPrimaryAndAlternateKey country = (KeyableCountryWithPrimaryAndAlternateKey) france.get(KeyableCountryWithPrimaryAndAlternateKey.class, france.getPrimaryKey());
        Assert.assertNotNull(country);
        Assert.assertEquals("France", country.getName());
    }

    /**
     * Ensure the success to retrieve a primary key field annotation by the name of the key.
     */
    @Test
    public void expectSuccessToGetPrimaryKeyAnnotation()
    {
        KeyableCountryWithPrimaryAndAlternateKey france = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .iso3("FRA")
                .comment("comment")
                .build();
        Assert.assertNotNull(france);

        Annotation annotation = france.getAnnotationKey("name");
        Assert.assertNotNull(annotation);

        Field field = france.getAnnotatedField(annotation);
        Assert.assertNotNull(field);
    }

    /**
     * Ensure the success to retrieve key field annotations.
     */
    @Test
    public void expectSuccessToGetKeyAnnotations()
    {
        KeyableCountryWithPrimaryAndAlternateKey france = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .iso3("FRA")
                .comment("comment")
                .build();
        Assert.assertNotNull(france);

        List<Annotation> annotations = france.getAnnotationKeys();
        Assert.assertTrue(annotations.size() > 0);
    }

    /**
     * Ensure the success to retrieve an alternate key field annotation by the name of the key.
     */
    @Test
    public void expectSuccessToGetAlternateKeyAnnotation()
    {
        KeyableCountryWithPrimaryAndAlternateKey france = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .iso3("FRA")
                .comment("comment")
                .build();
        Assert.assertNotNull(france);

        Annotation annotation = france.getAnnotationKey("iso3");
        Assert.assertNotNull(annotation);

        Field field = france.getAnnotatedField(annotation);
        Assert.assertNotNull(field);
    }

    /**
     * Ensure the success to create and retrieve a keyable based on a primitive long key.
     */
    @Test
    public void expectSuccessToCreateAndRetrievePrimitiveLongKey()
    {
        long value = 12378944641L;

        KeyableCountryWithPrimaryAndAlternateKey france = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso3("FRA")
                .longKey(value)
                .build();
        Assert.assertNotNull(france);

        KeyableCountryWithPrimaryAndAlternateKey country = (KeyableCountryWithPrimaryAndAlternateKey) france.get(
                KeyableCountryWithPrimaryAndAlternateKey.class,
                "longKey",
                value);

        Assert.assertNotNull(country);
        Assert.assertEquals(value, country.getLongKey());
    }

    /**
     * Ensure the success to initialize and retrieve a keyable based on a primitive integer key.
     */
    @Test
    public void expectSuccessToCreateAndRetrievePrimitiveIntegerKey()
    {
        int value = 4124589;

        KeyableCountryWithPrimaryAndAlternateKey france = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso3("FRA")
                .integerKey(value)
                .build();
        Assert.assertNotNull(france);

        KeyableCountryWithPrimaryAndAlternateKey country = (KeyableCountryWithPrimaryAndAlternateKey) france.get(
                KeyableCountryWithPrimaryAndAlternateKey.class,
                "integerKey",
                value);

        Assert.assertNotNull(country);
        Assert.assertEquals(value, country.getIntegerKey());
    }

    /**
     * Ensure the success to initialize and retrieve a keyable based on a primitive short key.
     */
    @Test
    public void expectSuccessToCreateAndRetrievePrimitiveShortKey()
    {
        short value = 85;

        KeyableCountryWithPrimaryAndAlternateKey france = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso3("FRA")
                .shortKey(value)
                .build();
        Assert.assertNotNull(france);

        KeyableCountryWithPrimaryAndAlternateKey country = (KeyableCountryWithPrimaryAndAlternateKey) france.get(
                KeyableCountryWithPrimaryAndAlternateKey.class,
                "shortKey",
                value);

        Assert.assertNotNull(country);
        Assert.assertEquals(value, country.getShortKey());
    }

    /**
     * Ensure the success to initialize and retrieve a keyable based on a primitive byte key.
     */
    @Test
    public void expectSuccessToCreateAndRetrievePrimitiveByteKey()
    {
        byte value = 0xa;

        KeyableCountryWithPrimaryAndAlternateKey france = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso3("FRA")
                .byteKey(value)
                .build();
        Assert.assertNotNull(france);

        KeyableCountryWithPrimaryAndAlternateKey country = (KeyableCountryWithPrimaryAndAlternateKey) france.get(
                KeyableCountryWithPrimaryAndAlternateKey.class,
                "byteKey",
                value);

        Assert.assertNotNull(country);
        Assert.assertEquals(value, country.getByteKey());
    }

    /**
     * Ensure the success to initialize and retrieve a keyable based on a UUID key.
     */
    @Test
    public void expectSuccessToCreateAndRetrieveUUIDKey()
    {
        UUID value = UUID.randomUUID();

        KeyableCountryWithPrimaryAndAlternateKey france = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso3("FRA")
                .uuidKey(value)
                .build();
        Assert.assertNotNull(france);

        KeyableCountryWithPrimaryAndAlternateKey country = (KeyableCountryWithPrimaryAndAlternateKey) france.get(
                KeyableCountryWithPrimaryAndAlternateKey.class,
                "uuidKey",
                value);

        Assert.assertNotNull(country);
        Assert.assertEquals(value.toString(), country.getUuidKey().toString());
    }

    /**
     * Ensure an exception is raised while trying to create a keyable with an auto key and a provided value.
     */
    @Test(expected = KeyException.class)
    public void expectFailureToCreateKeyableWhenAutoKeyWithValue()
    {
        KeyableCountryWithPrimaryAndAlternateKey france = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso3("FRA")
                .autoKey(1) // This is an auto key!
                .build();
    }

    /**
     * Ensure the success to check a key name exist for a given keyable class and a key name.
     */
    @Test
    public void expectSuccessToCheckKeyNameExist()
    {
        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso3("FRA")
                .build();
        Assert.assertNotNull(entity);

        boolean exist = KeyManager.getInstance().isKeyExist(KeyableCountryWithPrimaryAndAlternateKey.class, "name");
        Assert.assertTrue(exist);
    }

    /**
     * Ensure the success to check a key name does not exist for a given keyable class and a key name.
     */
    @Test
    public void expectSuccessToCheckKeyNameDoesNotExist()
    {
        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso3("FRA")
                .build();
        Assert.assertNotNull(entity);

        boolean exist = KeyManager.getInstance().isKeyExist(KeyableCountryWithPrimaryAndAlternateKey.class, "location");
        Assert.assertFalse(exist);
    }

    /**
     * Ensure the success to check a key value exist for a given keyable class, a key name and a key value.
     */
    @Test
    public void expectSuccessToCheckKeyValueExistence()
    {
        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso3("FRA")
                .build();
        Assert.assertNotNull(entity);

        boolean exist = KeyManager.getInstance().isKeyValueExist(KeyableCountryWithPrimaryAndAlternateKey.class, "name", "France");
        Assert.assertTrue(exist);
    }

    /**
     * Ensure the success to check a key value does not exist for a given keyable class, a key name and a key value.
     */
    @Test
    public void expectSuccessToCheckKeyValueDoesNotExist()
    {
        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso3("FRA")
                .build();
        Assert.assertNotNull(entity);

        boolean exist = KeyManager.getInstance().isKeyValueExist(KeyableCountryWithPrimaryAndAlternateKey.class, "name", "Germany");
        Assert.assertFalse(exist);
    }

    /**
     * Ensure the success to retrieve the count of registered keyables based on a key name.
     */
    @Test
    public void expectSuccessToRetrieveKeyableCountByKeyName()
    {
        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso3("FRA")
                .build();
        Assert.assertNotNull(entity);

        int count = KeyManager.getInstance().countByKeyName(KeyableCountryWithPrimaryAndAlternateKey.class, "iso3");
        Assert.assertEquals(1, count);
    }


    /**
     * Ensure the success to unregister a keyable instance only.
     */
    @Test
    public void expectSuccessToUnregisterKeyable()
    {
        KeyableCountryWithPrimaryAndAlternateKey entity = KeyableCountryWithPrimaryAndAlternateKey.builder()
                .name("France")
                .iso2("FR")
                .iso3("FRA")
                .build();
        Assert.assertNotNull(entity);

        KeyManager.getInstance().unregister(entity);
    }
}