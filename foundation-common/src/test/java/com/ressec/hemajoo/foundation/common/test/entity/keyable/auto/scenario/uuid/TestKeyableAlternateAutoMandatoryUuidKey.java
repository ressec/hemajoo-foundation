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
import com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.model.uuid.KeyableAlternateAutoMandatoryUuidKey;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.UUID;

/**
 * Test case for the {@link KeyableAlternateAutoMandatoryUuidKey} entity.
 * <p>
 * The underlying test class implements two keys:<ul>
 * <li>a {@link PrimaryKey#auto()} key of type {@link UUID}</li>
 * <li>an {@link AlternateKey#auto()} and {@link AlternateKey#mandatory()} key of type {@link UUID}.</li>
 * </ul></p>
 * This test case is intended to cover tests for keyable entities implementing an alternate unique key of type UUID.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public class TestKeyableAlternateAutoMandatoryUuidKey
{
    /**
     * Random number generator.
     */
    private Random random = new Random();

    @Before
    public void setUp()
    {
        KeyManager.getInstance().unregisterByKeyableClass(KeyableAlternateAutoMandatoryUuidKey.class);
    }

    @After
    public void tearDown()
    {
        KeyManager.getInstance().shutdown();
    }

    /**
     * Creates a keyable instance.
     * @return Created keyable entity.
     */
    private KeyableAlternateAutoMandatoryUuidKey createKeyable()
    {
        return KeyableAlternateAutoMandatoryUuidKey.builder().build();
    }

    /**
     * Create multiple keyable instances.
     */
    private void createMultipleKeyable(final int count)
    {
        KeyableAlternateAutoMandatoryUuidKey entity;

        for (int i = 0; i < count; i++)
        {
            entity = createKeyable();
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
     * Ensure the success to create a keyable when the primary key value is not provided (as mandatory is set to false).
     */
    @Test
    public void expectSuccessToCreateKeyableWhenPrimaryKeyValueIsNotProvided()
    {
        KeyableAlternateAutoMandatoryUuidKey entity = KeyableAlternateAutoMandatoryUuidKey.builder().build();
        Assert.assertNotNull(entity);
    }

    /**
     * Ensure the success to create a keyable when the alternate key value is not provided (as mandatory is set to false).
     */
    @Test
    public void expectSuccessToCreateKeyableWhenAlternateKeyValueIsNotProvided()
    {
        KeyableAlternateAutoMandatoryUuidKey entity = KeyableAlternateAutoMandatoryUuidKey.builder().build();
        Assert.assertNotNull(entity);
    }

    /**
     * Ensure an exception is raised while creating a keyable when the primary key value is provided (as auto is set to true for the primary key).
     */
    @Test(expected = KeyException.class)
    public void expectFailureToCreateKeyableWhenPrimaryKeyValueIsProvided()
    {
        KeyableAlternateAutoMandatoryUuidKey.builder()
                .primaryUuid(UUID.randomUUID())
                .build();
    }

    /**
     * Ensure an exception is raised while creating a keyable when the primary key value is provided (as auto is set to true for the primary key).
     */
    @Test(expected = KeyException.class)
    public void expectFailureToCreateKeyableWhenAlternateKeyValueIsProvided()
    {
        KeyableAlternateAutoMandatoryUuidKey.builder()
                .alternateAutoMandatoryUuid(UUID.randomUUID())
                .build();
    }

    /**
     * Ensure the success to retrieve a keyable by its primary key.
     */
    @Test
    public void expectSuccessToRetrieveKeyableByPrimaryKey()
    {
        KeyableAlternateAutoMandatoryUuidKey entity = createKeyable();

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyableAlternateAutoMandatoryUuidKey keyable = (KeyableAlternateAutoMandatoryUuidKey) Keyable.retrieve(
                KeyableAlternateAutoMandatoryUuidKey.class, key);
        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimaryUuid(), keyable.getPrimaryUuid());
    }

    /**
     * Ensure the success to retrieve a keyable by its primary key name.
     */
    @Test
    public void expectSuccessToRetrieveKeyableByPrimaryKeyName()
    {
        KeyableAlternateAutoMandatoryUuidKey entity = createKeyable();

        UUID keyValue = entity.getPrimaryUuid();
        Assert.assertNotNull(keyValue);

        KeyableAlternateAutoMandatoryUuidKey keyable = (KeyableAlternateAutoMandatoryUuidKey) Keyable.retrieve(
                KeyableAlternateAutoMandatoryUuidKey.class, "primaryUuid", keyValue);
        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimaryUuid(), keyable.getPrimaryUuid());
    }

    /**
     * Ensure the success to retrieve a keyable instance using the alternate key.
     */
    @Test
    public void expectSuccessToRetrieveKeyableUsingAlternateKey()
    {
        KeyableAlternateAutoMandatoryUuidKey entity = createKeyable();

        KeyableAlternateAutoMandatoryUuidKey keyable = (KeyableAlternateAutoMandatoryUuidKey) Keyable.retrieve(
                KeyableAlternateAutoMandatoryUuidKey.class,
                "alternateAutoMandatoryUuid",
                entity.getAlternateAutoMandatoryUuid());
        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getAlternateAutoMandatoryUuid(), keyable.getAlternateAutoMandatoryUuid());
    }

    /**
     * Ensure the success to query for a keyable by its primary key name.
     */
    @Test
    public void expectSuccessToQueryKeyableByPrimaryKeyName()
    {
        KeyableAlternateAutoMandatoryUuidKey entity = createKeyable();
        Assert.assertNotNull(entity);

        UUID keyValue = entity.getPrimaryUuid();
        Assert.assertNotNull(keyValue);

        KeyableAlternateAutoMandatoryUuidKey keyable = (KeyableAlternateAutoMandatoryUuidKey) Keyable.query(
                KeyableAlternateAutoMandatoryUuidKey.class,
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
        KeyableAlternateAutoMandatoryUuidKey entity = createKeyable();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyableAlternateAutoMandatoryUuidKey keyable = (KeyableAlternateAutoMandatoryUuidKey) Keyable.query(
                KeyableAlternateAutoMandatoryUuidKey.class, key).get(0);

        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimaryUuid(), keyable.getPrimaryUuid());
    }

    /**
     * Ensure the success to unregister a keyable by its instance.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByInstance()
    {
        KeyableAlternateAutoMandatoryUuidKey entity = createKeyable();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyManager.getInstance().unregister(entity);

        KeyableAlternateAutoMandatoryUuidKey keyable = (KeyableAlternateAutoMandatoryUuidKey) Keyable.retrieve(
                KeyableAlternateAutoMandatoryUuidKey.class, key);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyableAlternateAutoMandatoryUuidKey.class));
    }

    /**
     * Ensure the success to unregister a keyable by its class.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByKeyableClass()
    {
        KeyableAlternateAutoMandatoryUuidKey entity = createKeyable();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyManager.getInstance().unregisterByKeyableClass(KeyableAlternateAutoMandatoryUuidKey.class);

        KeyableAlternateAutoMandatoryUuidKey keyable = (KeyableAlternateAutoMandatoryUuidKey) Keyable.retrieve(
                KeyableAlternateAutoMandatoryUuidKey.class, key);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyableAlternateAutoMandatoryUuidKey.class));
    }

    /**
     * Ensure the success to unregister a keyable by its key type.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByKeyClass()
    {
        KeyableAlternateAutoMandatoryUuidKey entity = createKeyable();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyManager.getInstance().unregisterByKeyClass(KeyableAlternateAutoMandatoryUuidKey.class, UUID.class);

        KeyableAlternateAutoMandatoryUuidKey keyable = (KeyableAlternateAutoMandatoryUuidKey) Keyable.retrieve(
                KeyableAlternateAutoMandatoryUuidKey.class, key);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyableAlternateAutoMandatoryUuidKey.class));
    }

    /**
     * Ensure the success to unregister a keyable by its primary key name.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByPrimaryKeyName()
    {
        KeyableAlternateAutoMandatoryUuidKey entity = createKeyable();
        Assert.assertNotNull(entity);

        // TODO Unregistering a primary key should be forbidden
        KeyManager.getInstance().unregisterByKeyName(KeyableAlternateAutoMandatoryUuidKey.class, "primaryUuid");

        KeyableAlternateAutoMandatoryUuidKey keyable = (KeyableAlternateAutoMandatoryUuidKey) Keyable.retrieve(
                KeyableAlternateAutoMandatoryUuidKey.class, entity.getPrimaryKey());
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyableAlternateAutoMandatoryUuidKey.class));
    }

    /**
     * Ensure the success to unregister a keyable by its alternate key name.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByAlternateKeyName()
    {
        KeyableAlternateAutoMandatoryUuidKey entity = createKeyable();
        Assert.assertNotNull(entity);

        IKey alternate = entity.getKey("alternateAutoMandatoryUuid");
        Assert.assertNotNull(alternate);

        // TODO Unregistering a primary key should be forbidden
        KeyManager.getInstance().unregisterByKeyName(KeyableAlternateAutoMandatoryUuidKey.class, "alternateAutoMandatoryUuid");

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyName(KeyableAlternateAutoMandatoryUuidKey.class, alternate.getName()));
    }

    /**
     * Ensure the success to count keyable instances by keyable class.
     */
    @Test
    public void expectSuccessToCountKeyableByKeyableClass()
    {
        int value = getRandomNumber(100);

        createMultipleKeyable(value);

        Assert.assertEquals(value, KeyManager.getInstance().countByKeyableClass(KeyableAlternateAutoMandatoryUuidKey.class));
    }

    /**
     * Ensure the success to count keyable instances by primary key.
     */
    @Test
    public void expectSuccessToCountKeyableByPrimaryKey()
    {
        int value = getRandomNumber(100);

        createMultipleKeyable(value);

        KeyableAlternateAutoMandatoryUuidKey entity = createKeyable();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        Assert.assertEquals(value + 1, KeyManager.getInstance().countByKey(key));
    }

    /**
     * Ensure the success to count keyables based on the alternate key.
     */
    @Test
    public void expectSuccessToCountKeyableByAlternateKey()
    {
        int value = getRandomNumber(100);

        createMultipleKeyable(value);

        KeyableAlternateAutoMandatoryUuidKey entity = createKeyable();
        Assert.assertNotNull(entity);

        IKey key = entity.getKey("alternateAutoMandatoryUuid");
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

        createMultipleKeyable(value);

        KeyableAlternateAutoMandatoryUuidKey entity = createKeyable();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        Assert.assertEquals(value + 1, KeyManager.getInstance().countByKeyName(key.getReferenceClass(), key.getName()));
    }
}