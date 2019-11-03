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
import com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.model.uuid.KeyableAlternateMandatoryUuidKey;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.UUID;

/**
 * Test case for the {@link KeyableAlternateMandatoryUuidKey} entity.
 * <p>
 * The underlying test class implements two keys:<ul>
 * <li>a {@link PrimaryKey#auto()} key of type {@link UUID}</li>
 * <li>an {@link AlternateKey#mandatory()} key of type {@link UUID}.</li>
 * </ul></p>
 * This test case is intended to cover tests for keyable entities implementing an alternate mandatory key of type UUID.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public class TestKeyableAlternateMandatoryUuidKey
{
    /**
     * Random number generator.
     */
    private Random random = new Random();

    @Before
    public void setUp()
    {
        KeyManager.getInstance().unregisterByKeyableClass(KeyableAlternateMandatoryUuidKey.class);
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
    private KeyableAlternateMandatoryUuidKey createKeyableWithAlternateKeyValue()
    {
        return KeyableAlternateMandatoryUuidKey.builder()
                .alternateMandatoryUuid(UUID.randomUUID())
                .build();
    }

    /**
     * Create multiple keyable instances by providing an alternate key value.
     */
    private void createMultipleKeyableWithAlternateKeyValue(final int count)
    {
        KeyableAlternateMandatoryUuidKey entity;

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
        KeyableAlternateMandatoryUuidKey entity = KeyableAlternateMandatoryUuidKey.builder()
                .alternateMandatoryUuid(UUID.randomUUID())
                .build();
        Assert.assertNotNull(entity);
    }

    /**
     * Ensure an exception is raised while trying to create a keyable when the alternate key value is not provided
     * (as mandatory is set to true).
     */
    @Test(expected = KeyException.class)
    public void expectFailureToCreateKeyableWhenAlternateKeyValueIsNotProvided()
    {
        KeyableAlternateMandatoryUuidKey entity = KeyableAlternateMandatoryUuidKey.builder().build();
    }

    /**
     * Ensure an exception is raised while creating a keyable when the primary key value is provided (as auto is set to true for the primary key).
     */
    @Test(expected = KeyException.class)
    public void expectFailureToCreateKeyableWhenPrimaryKeyValueIsProvided()
    {
        KeyableAlternateMandatoryUuidKey.builder()
                .primaryUuid(UUID.randomUUID())
                .build();
    }

    /**
     * Ensure the success to retrieve a keyable by its primary key.
     */
    @Test
    public void expectSuccessToRetrieveKeyableByPrimaryKey()
    {
        KeyableAlternateMandatoryUuidKey entity = createKeyableWithAlternateKeyValue();

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyableAlternateMandatoryUuidKey keyable = (KeyableAlternateMandatoryUuidKey) Keyable.retrieve(
                KeyableAlternateMandatoryUuidKey.class, key);
        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimaryUuid(), keyable.getPrimaryUuid());
    }

    /**
     * Ensure the success to retrieve a keyable by its primary key name.
     */
    @Test
    public void expectSuccessToRetrieveKeyableByPrimaryKeyName()
    {
        KeyableAlternateMandatoryUuidKey entity = createKeyableWithAlternateKeyValue();

        UUID keyValue = entity.getPrimaryUuid();
        Assert.assertNotNull(keyValue);

        KeyableAlternateMandatoryUuidKey keyable = (KeyableAlternateMandatoryUuidKey) Keyable.retrieve(
                KeyableAlternateMandatoryUuidKey.class, "primaryUuid", keyValue);
        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimaryUuid(), keyable.getPrimaryUuid());
    }

    /**
     * Ensure the success to retrieve a keyable instance using an alternate key.
     */
    @Test
    public void expectSuccessToRetrieveKeyableUsingAlternateKey()
    {
        KeyableAlternateMandatoryUuidKey entity = createKeyableWithAlternateKeyValue();

        IKey key = entity.getKey("alternateMandatoryUuid");
        Assert.assertNotNull(key);

        KeyableAlternateMandatoryUuidKey keyable = (KeyableAlternateMandatoryUuidKey) Keyable.retrieve(
                KeyableAlternateMandatoryUuidKey.class, key);
        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getAlternateMandatoryUuid(), keyable.getAlternateMandatoryUuid());
    }

    /**
     * Ensure the success to retrieve a keyable instance using an alternate key name and value.
     */
    @Test
    public void expectFailureToRetrieveKeyableUsingAlternateKeyNameAndValueNotProvidingKeyValue()
    {
        KeyableAlternateMandatoryUuidKey entity = createKeyableWithAlternateKeyValue();

        KeyableAlternateMandatoryUuidKey keyable = (KeyableAlternateMandatoryUuidKey) Keyable.retrieve(
                KeyableAlternateMandatoryUuidKey.class,
                "alternateMandatoryUuid",
                entity.getAlternateMandatoryUuid());
        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getAlternateMandatoryUuid(), keyable.getAlternateMandatoryUuid());
    }

    /**
     * Ensure the success to query for a keyable by its primary key name.
     */
    @Test
    public void expectSuccessToQueryKeyableByPrimaryKeyName()
    {
        KeyableAlternateMandatoryUuidKey entity = createKeyableWithAlternateKeyValue();
        Assert.assertNotNull(entity);

        UUID keyValue = entity.getPrimaryUuid();
        Assert.assertNotNull(keyValue);

        KeyableAlternateMandatoryUuidKey keyable = (KeyableAlternateMandatoryUuidKey) Keyable.query(
                KeyableAlternateMandatoryUuidKey.class,
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
        KeyableAlternateMandatoryUuidKey entity = createKeyableWithAlternateKeyValue();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyableAlternateMandatoryUuidKey keyable = (KeyableAlternateMandatoryUuidKey) Keyable.query(
                KeyableAlternateMandatoryUuidKey.class, key).get(0);

        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimaryUuid(), keyable.getPrimaryUuid());
    }

    /**
     * Ensure the success to unregister a keyable by its instance.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByInstance()
    {
        KeyableAlternateMandatoryUuidKey entity = createKeyableWithAlternateKeyValue();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyManager.getInstance().unregister(entity);

        KeyableAlternateMandatoryUuidKey keyable = (KeyableAlternateMandatoryUuidKey) Keyable.retrieve(
                KeyableAlternateMandatoryUuidKey.class, key);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyableAlternateMandatoryUuidKey.class));
    }

    /**
     * Ensure the success to unregister a keyable by its class.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByKeyableClass()
    {
        KeyableAlternateMandatoryUuidKey entity = createKeyableWithAlternateKeyValue();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyManager.getInstance().unregisterByKeyableClass(KeyableAlternateMandatoryUuidKey.class);

        KeyableAlternateMandatoryUuidKey keyable = (KeyableAlternateMandatoryUuidKey) Keyable.retrieve(
                KeyableAlternateMandatoryUuidKey.class, key);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyableAlternateMandatoryUuidKey.class));
    }

    /**
     * Ensure the success to unregister a keyable by its key type.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByKeyClass()
    {
        KeyableAlternateMandatoryUuidKey entity = createKeyableWithAlternateKeyValue();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyManager.getInstance().unregisterByKeyClass(KeyableAlternateMandatoryUuidKey.class, UUID.class);

        KeyableAlternateMandatoryUuidKey keyable = (KeyableAlternateMandatoryUuidKey) Keyable.retrieve(
                KeyableAlternateMandatoryUuidKey.class, key);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyableAlternateMandatoryUuidKey.class));
    }

    /**
     * Ensure the success to unregister a keyable by its primary key name.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByPrimaryKeyName()
    {
        KeyableAlternateMandatoryUuidKey entity = createKeyableWithAlternateKeyValue();
        Assert.assertNotNull(entity);

        // TODO Unregistering a primary key should be forbidden
        KeyManager.getInstance().unregisterByKeyName(KeyableAlternateMandatoryUuidKey.class, "primaryUuid");

        KeyableAlternateMandatoryUuidKey keyable = (KeyableAlternateMandatoryUuidKey) Keyable.retrieve(
                KeyableAlternateMandatoryUuidKey.class, entity.getPrimaryKey());
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyableAlternateMandatoryUuidKey.class));
    }

    /**
     * Ensure the success to unregister a keyable by its alternate key name.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByAlternateKeyName()
    {
        KeyableAlternateMandatoryUuidKey entity = createKeyableWithAlternateKeyValue();
        Assert.assertNotNull(entity);

        IKey alternate = entity.getKey("alternateMandatoryUuid");
        Assert.assertNotNull(alternate);

        // TODO Unregistering a primary key should be forbidden
        KeyManager.getInstance().unregisterByKeyName(KeyableAlternateMandatoryUuidKey.class, "alternateMandatoryUuid");

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyName(KeyableAlternateMandatoryUuidKey.class, alternate.getName()));
    }

    /**
     * Ensure the success to count keyable instances by keyable class.
     */
    @Test
    public void expectSuccessToCountKeyableByKeyableClass()
    {
        int value = getRandomNumber(100);

        createMultipleKeyableWithAlternateKeyValue(value);

        Assert.assertEquals(value, KeyManager.getInstance().countByKeyableClass(KeyableAlternateMandatoryUuidKey.class));
    }

    /**
     * Ensure the success to count keyable instances by primary key.
     */
    @Test
    public void expectSuccessToCountKeyableByPrimaryKey()
    {
        int value = getRandomNumber(100);

        createMultipleKeyableWithAlternateKeyValue(value);

        KeyableAlternateMandatoryUuidKey entity = createKeyableWithAlternateKeyValue();
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

        createMultipleKeyableWithAlternateKeyValue(value);

        KeyableAlternateMandatoryUuidKey entity = createKeyableWithAlternateKeyValue();
        Assert.assertNotNull(entity);

        IKey key = entity.getKey("alternateMandatoryUuid");
        Assert.assertNotNull(key);

        Assert.assertEquals(value + 1, KeyManager.getInstance().countByKey(key));
    }

    /**
     * Ensure the success to count keyable instances by its primary key name.
     */
    @Test
    public void expectSuccessToCountKeyableByPrimaryKeyName()
    {
        int value = getRandomNumber(100);

        createMultipleKeyableWithAlternateKeyValue(value);

        KeyableAlternateMandatoryUuidKey entity = createKeyableWithAlternateKeyValue();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        Assert.assertEquals(value + 1, KeyManager.getInstance().countByKeyName(key.getReferenceClass(), key.getName()));
    }
}