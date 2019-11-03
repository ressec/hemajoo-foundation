/*
 * (C) Copyright IBM Corp. 2019 - All Rights Reserved
 * ---------------------------------------------------------------------------
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 * ---------------------------------------------------------------------------
 */
package com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.scenario.uuid;

import com.ressec.hemajoo.foundation.common.entity.keyable.*;
import com.ressec.hemajoo.foundation.common.entity.keyable.exception.KeyException;
import com.ressec.hemajoo.foundation.common.entity.keyable.exception.KeyManagerException;
import com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.model.uuid.KeyableAlternateUniqueUuidKey;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.UUID;

/**
 * Test case for the {@link KeyableAlternateUniqueUuidKey} entity.
 * <p>
 * The underlying test class implements two keys:<ul>
 * <li>a {@link PrimaryKey#auto()} key of type {@link UUID}</li>
 * <li>an {@link AlternateKey#unique()} key of type {@link UUID}.</li>
 * </ul></p>
 * This test case is intended to cover tests for keyable entities implementing an alternate unique key of type UUID.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public class TestKeyableAlternateUniqueUuidKey
{
    /**
     * Random number generator.
     */
    private Random random = new Random();

    @Before
    public void setUp()
    {
        KeyManager.getInstance().unregisterByKeyableClass(KeyableAlternateUniqueUuidKey.class);
    }

    @After
    public void tearDown()
    {
        KeyManager.getInstance().shutdown();
    }

    /**
     * Creates a keyable instance providing an alternate key value.
     * @return Created keyable entity.
     */
    private KeyableAlternateUniqueUuidKey createKeyableWithAlternateKeyValue()
    {
        return KeyableAlternateUniqueUuidKey.builder()
                .alternateUniqueUuid(UUID.randomUUID())
                .build();
    }

    /**
     * Creates a keyable instance without providing an alternate key value.
     * @return Created keyable entity.
     */
    private KeyableAlternateUniqueUuidKey createKeyableWithoutAlternateKeyValue()
    {
        return KeyableAlternateUniqueUuidKey.builder().build();
    }

    /**
     * Create multiple keyable instances by providing an alternate key value.
     */
    private void createMultipleKeyableWithoutAlternateKeyValue(final int count)
    {
        KeyableAlternateUniqueUuidKey entity;

        for (int i = 0; i < count; i++)
        {
            entity = createKeyableWithoutAlternateKeyValue();
            Assert.assertNotNull(entity);
        }
    }

    /**
     * Create multiple keyable instances by providing an alternate key value.
     */
    private void createMultipleKeyableWithAlternateKeyValue(final int count)
    {
        KeyableAlternateUniqueUuidKey entity;

        for (int i = 0; i < count; i++)
        {
            entity = createKeyableWithAlternateKeyValue();
            Assert.assertNotNull(entity);
        }
    }

    /**
     * Returns a random number.
     * @param bound Upper bound of the random number to generate.
     * @return Random generated number.
     */
    private int getRandomNumber(final int bound)
    {
        return random.nextInt(bound);
    }

    /**
     * Ensure the success to create a keyable when the alternate key value is provided.
     */
    @Test
    public void expectSuccessToCreateKeyableWhenAlternateKeyValueIsProvided()
    {
        KeyableAlternateUniqueUuidKey entity = KeyableAlternateUniqueUuidKey.builder()
                .alternateUniqueUuid(UUID.randomUUID())
                .build();
        Assert.assertNotNull(entity);
    }

    /**
     * Ensure the success to create a keyable when the alternate key value is not provided (as mandatory is set to false).
     */
    @Test
    public void expectSuccessToCreateKeyableWhenAlternateKeyValueIsNotProvided()
    {
        KeyableAlternateUniqueUuidKey entity = KeyableAlternateUniqueUuidKey.builder().build();
        Assert.assertNotNull(entity);
    }

    /**
     * Ensure an exception is raised while creating a keyable when the primary key value is provided (as auto is set to true for the primary key).
     */
    @Test(expected = KeyException.class)
    public void expectFailureToCreateKeyableWhenPrimaryKeyValueIsProvided()
    {
        KeyableAlternateUniqueUuidKey.builder()
                .primaryUuid(UUID.randomUUID())
                .build();
    }

    /**
     * Ensure the success to retrieve a keyable by its primary key.
     */
    @Test
    public void expectSuccessToRetrieveKeyableByPrimaryKey()
    {
        KeyableAlternateUniqueUuidKey entity = KeyableAlternateUniqueUuidKey.builder().build();

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyableAlternateUniqueUuidKey keyable = (KeyableAlternateUniqueUuidKey) Keyable.retrieve(
                KeyableAlternateUniqueUuidKey.class, key);
        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimaryUuid(), keyable.getPrimaryUuid());
    }

    /**
     * Ensure the success to retrieve a keyable by its primary key name.
     */
    @Test
    public void expectSuccessToRetrieveKeyableByPrimaryKeyName()
    {
        KeyableAlternateUniqueUuidKey entity = KeyableAlternateUniqueUuidKey.builder().build();

        UUID keyValue = entity.getPrimaryUuid();
        Assert.assertNotNull(keyValue);

        KeyableAlternateUniqueUuidKey keyable = (KeyableAlternateUniqueUuidKey) Keyable.retrieve(
                KeyableAlternateUniqueUuidKey.class, "primaryUuid", keyValue);
        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimaryUuid(), keyable.getPrimaryUuid());
    }

    /**
     * Ensure an exception is raised while trying to retrieve a keyable instance using a key not providing the key value.
     */
    @Test(expected = KeyManagerException.class)
    public void expectFailureToRetrieveKeyableUsingAlternateKeyNotProvidingKeyValue()
    {
        KeyableAlternateUniqueUuidKey entity = KeyableAlternateUniqueUuidKey.builder().build();

        IKey key = entity.getKey("alternateUniqueUuid");
        Assert.assertNotNull(key);

        KeyableAlternateUniqueUuidKey keyable = (KeyableAlternateUniqueUuidKey) Keyable.retrieve(
                KeyableAlternateUniqueUuidKey.class, key);
        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getAlternateUniqueUuid(), keyable.getAlternateUniqueUuid());
    }

    /**
     * Ensure an exception is raised while trying to retrieve a keyable instance using a key name and value not
     * providing the key value.
     */
    @Test(expected = NullPointerException.class)
    public void expectFailureToRetrieveKeyableUsingAlternateKeyNameAndValueNotProvidingKeyValue()
    {
        KeyableAlternateUniqueUuidKey entity = KeyableAlternateUniqueUuidKey.builder().build();

        Keyable.retrieve(KeyableAlternateUniqueUuidKey.class, "alternateUniqueUuid", entity.getAlternateUniqueUuid());
    }

    /**
     * Ensure the success to query for a keyable by its primary key name.
     */
    @Test
    public void expectSuccessToQueryKeyableByPrimaryKeyName()
    {
        KeyableAlternateUniqueUuidKey entity = KeyableAlternateUniqueUuidKey.builder().build();
        Assert.assertNotNull(entity);

        UUID keyValue = entity.getPrimaryUuid();
        Assert.assertNotNull(keyValue);

        KeyableAlternateUniqueUuidKey keyable = (KeyableAlternateUniqueUuidKey) Keyable.query(
                KeyableAlternateUniqueUuidKey.class,
                "primaryUuid",
                keyValue).get(0);

        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimaryUuid(), keyable.getPrimaryUuid());
    }

    /**
     * Ensure the success to query for a keyable by its primary key.
     */
    @Test
    public void expectSuccessToQueryKeyableByPrimaryKey()
    {
        KeyableAlternateUniqueUuidKey entity = KeyableAlternateUniqueUuidKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyableAlternateUniqueUuidKey keyable = (KeyableAlternateUniqueUuidKey) Keyable.query(
                KeyableAlternateUniqueUuidKey.class, key).get(0);

        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimaryUuid(), keyable.getPrimaryUuid());
    }

    /**
     * Ensure the success to unregister a keyable by its instance.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByInstance()
    {
        KeyableAlternateUniqueUuidKey entity = KeyableAlternateUniqueUuidKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyManager.getInstance().unregister(entity);

        KeyableAlternateUniqueUuidKey keyable = (KeyableAlternateUniqueUuidKey) Keyable.retrieve(
                KeyableAlternateUniqueUuidKey.class, key);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyableAlternateUniqueUuidKey.class));
    }

    /**
     * Ensure the success to unregister a keyable by its class.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByKeyableClass()
    {
        KeyableAlternateUniqueUuidKey entity = KeyableAlternateUniqueUuidKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyManager.getInstance().unregisterByKeyableClass(KeyableAlternateUniqueUuidKey.class);

        KeyableAlternateUniqueUuidKey keyable = (KeyableAlternateUniqueUuidKey) Keyable.retrieve(
                KeyableAlternateUniqueUuidKey.class, key);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyableAlternateUniqueUuidKey.class));
    }

    /**
     * Ensure the success to unregister a keyable by its key type.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByKeyClass()
    {
        KeyableAlternateUniqueUuidKey entity = KeyableAlternateUniqueUuidKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyManager.getInstance().unregisterByKeyClass(KeyableAlternateUniqueUuidKey.class, UUID.class);

        KeyableAlternateUniqueUuidKey keyable = (KeyableAlternateUniqueUuidKey) Keyable.retrieve(
                KeyableAlternateUniqueUuidKey.class, key);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyableAlternateUniqueUuidKey.class));
    }

    /**
     * Ensure the success to unregister a keyable by its primary key name.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByPrimaryKeyName()
    {
        KeyableAlternateUniqueUuidKey entity = KeyableAlternateUniqueUuidKey.builder().build();
        Assert.assertNotNull(entity);

        // TODO Unregistering a primary key should be forbidden
        KeyManager.getInstance().unregisterByKeyName(KeyableAlternateUniqueUuidKey.class, "primaryUuid");

        KeyableAlternateUniqueUuidKey keyable = (KeyableAlternateUniqueUuidKey) Keyable.retrieve(
                KeyableAlternateUniqueUuidKey.class, entity.getPrimaryKey());
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyableAlternateUniqueUuidKey.class));
    }

    /**
     * Ensure the success to unregister a keyable by its alternate key name.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByAlternateKeyName()
    {
        KeyableAlternateUniqueUuidKey entity = KeyableAlternateUniqueUuidKey.builder().build();
        Assert.assertNotNull(entity);

        IKey alternate = entity.getKey("alternateUniqueUuid");
        Assert.assertNotNull(alternate);

        // TODO Unregistering a primary key should be forbidden
        KeyManager.getInstance().unregisterByKeyName(KeyableAlternateUniqueUuidKey.class, "alternateUniqueUuid");

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyName(KeyableAlternateUniqueUuidKey.class, alternate.getName()));
    }

    /**
     * Ensure the success to count keyable instances by keyable class.
     */
    @Test
    public void expectSuccessToCountKeyableByKeyableClass()
    {
        int value = getRandomNumber(100);

        createMultipleKeyableWithAlternateKeyValue(value);

        Assert.assertEquals(value, KeyManager.getInstance().countByKeyableClass(KeyableAlternateUniqueUuidKey.class));
    }

    /**
     * Ensure the success to count keyable instances by primary key.
     */
    @Test
    public void expectSuccessToCountKeyableByPrimaryKey()
    {
        int value = getRandomNumber(100);

        createMultipleKeyableWithAlternateKeyValue(value);

        KeyableAlternateUniqueUuidKey entity = KeyableAlternateUniqueUuidKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        Assert.assertEquals(value + 1, KeyManager.getInstance().countByKey(key));
    }

    /**
     * Ensure the success to count keyables based on the alternate key.<br>
     * The expected result is 0 because the alternate key has not been provided when creating the keyable entities.
     */
    @Test
    public void expectSuccessToCountKeyableByAlternateKey()
    {
        int value = getRandomNumber(100);

        createMultipleKeyableWithoutAlternateKeyValue(value);

        KeyableAlternateUniqueUuidKey entity = KeyableAlternateUniqueUuidKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getKey("alternateUniqueUuid");
        Assert.assertNotNull(key);

        Assert.assertEquals(0, KeyManager.getInstance().countByKey(key));
    }

    /**
     * Ensure the success to count keyable instances by its primary key name.
     */
    @Test
    public void expectSuccessToCountKeyableByPrimaryKeyName()
    {
        int value = getRandomNumber(100);

        createMultipleKeyableWithAlternateKeyValue(value);

        KeyableAlternateUniqueUuidKey entity = KeyableAlternateUniqueUuidKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        Assert.assertEquals(value + 1, KeyManager.getInstance().countByKeyName(key.getReferenceClass(), key.getName()));
    }
}