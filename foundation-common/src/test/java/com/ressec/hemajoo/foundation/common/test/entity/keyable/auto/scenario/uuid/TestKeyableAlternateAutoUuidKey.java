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
import com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.model.uuid.KeyableAlternateAutoUuidKey;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.UUID;

/**
 * Test case for the {@link KeyableAlternateAutoUuidKey} entity.
 * <p>
 * The underlying test class implements two keys:<ul>
 * <li>a {@link PrimaryKey#auto()} key of type {@link UUID}</li>
 * <li>an {@link AlternateKey#auto()} key of type {@link UUID}.</li>
 * </ul></p>
 * This test case is intended to cover tests for keyable entities implementing an alternate auto key of type UUID.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public class TestKeyableAlternateAutoUuidKey
{
    /**
     * Random number generator.
     */
    private Random random = new Random();

    @Before
    public void setUp()
    {
        KeyManager.getInstance().unregisterByKeyableClass(KeyableAlternateAutoUuidKey.class);
    }

    @After
    public void tearDown()
    {
        KeyManager.getInstance().shutdown();
    }

    /**
     * Creates a keyable instance without providing a primary nor an alternate key value.
     * @return Created keyable entity.
     */
    private KeyableAlternateAutoUuidKey createKeyableWithoutPrimaryAndAlternateKeyValue()
    {
        return KeyableAlternateAutoUuidKey.builder().build();
    }

    /**
     * Create multiple keyable instances.
     */
    private void createMultipleKeyable(final int count)
    {
        for (int i = 0; i < count; i++)
        {
            createKeyableWithoutPrimaryAndAlternateKeyValue();
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
     * Ensure the success to create a keyable.
     */
    @Test
    public void expectSuccessToCreateKeyable()
    {
        KeyableAlternateAutoUuidKey entity = createKeyableWithoutPrimaryAndAlternateKeyValue();
        Assert.assertNotNull(entity);
    }

    /**
     * Ensure an exception is raised while trying to create a keyable with an auto primary UUID when a key value is provided.
     */
    @Test(expected = KeyException.class) // TODO Should throw a KeyInitializationException instead of a KeyException
    public void expectFailureToCreateKeyableWithAutoPrimaryUuidKeyWhenValueIsProvided()
    {
        KeyableAlternateAutoUuidKey.builder()
                .primaryUuid(UUID.randomUUID())
                .build();
    }

    /**
     * Ensure an exception is raised while trying to create a keyable with an auto alternate UUID key when the key value is provided.
     */
    @Test(expected = KeyException.class) // TODO Should throw a KeyInitializationException instead of a KeyException
    public void expectFailureToCreateKeyableWithAutoAlternateUuidKeyWhenValueIsProvided()
    {
        KeyableAlternateAutoUuidKey.builder()
                .alternateAutoUuid(UUID.randomUUID())
                .build();
    }

    /**
     * Ensure the success to retrieve a keyable entity by its primary key.
     */
    @Test
    public void expectSuccessToRetrieveKeyableByPrimaryKey()
    {
        KeyableAlternateAutoUuidKey entity = createKeyableWithoutPrimaryAndAlternateKeyValue();

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyableAlternateAutoUuidKey keyable = (KeyableAlternateAutoUuidKey) Keyable.retrieve(
                KeyableAlternateAutoUuidKey.class, key);
        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimaryUuid(), keyable.getPrimaryUuid());
    }

    /**
     * Ensure the success to retrieve a keyable entity by its primary key name.
     */
    @Test
    public void expectSuccessToRetrieveKeyableByPrimaryKeyName()
    {
        KeyableAlternateAutoUuidKey entity = createKeyableWithoutPrimaryAndAlternateKeyValue();

        UUID keyValue = entity.getPrimaryUuid();
        Assert.assertNotNull(keyValue);

        KeyableAlternateAutoUuidKey keyable = (KeyableAlternateAutoUuidKey) Keyable.retrieve(
                KeyableAlternateAutoUuidKey.class, "primaryUuid", keyValue);
        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimaryUuid(), keyable.getPrimaryUuid());
    }

    /**
     * Ensure the success to retrieve a keyable entity by its alternate key.
     */
    @Test
    public void expectSuccessToRetrieveKeyableByAlternateKey()
    {
        KeyableAlternateAutoUuidKey entity = KeyableAlternateAutoUuidKey.builder().build();

        IKey key = entity.getKey("alternateAutoUuid");
        Assert.assertNotNull(key);

        KeyableAlternateAutoUuidKey keyable = (KeyableAlternateAutoUuidKey) Keyable.retrieve(
                KeyableAlternateAutoUuidKey.class, key);
        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getAlternateAutoUuid(), keyable.getAlternateAutoUuid());
    }

    /**
     * Ensure the success to retrieve a keyable entity by its alternate key name.
     */
    @Test
    public void expectSuccessToRetrieveKeyableByAlternateKeyName()
    {
        KeyableAlternateAutoUuidKey entity = KeyableAlternateAutoUuidKey.builder().build();

        UUID keyValue = entity.getAlternateAutoUuid();
        Assert.assertNotNull(keyValue);

        KeyableAlternateAutoUuidKey keyable = (KeyableAlternateAutoUuidKey) Keyable.retrieve(
                KeyableAlternateAutoUuidKey.class, "alternateAutoUuid", keyValue);
        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getAlternateAutoUuid(), keyable.getAlternateAutoUuid());
    }

    /**
     * Ensure the success to query for a keyable entity by its primary key name.
     */
    @Test
    public void expectSuccessToQueryKeyableByPrimaryKeyName()
    {
        KeyableAlternateAutoUuidKey entity = KeyableAlternateAutoUuidKey.builder().build();
        Assert.assertNotNull(entity);

        UUID keyValue = entity.getPrimaryUuid();
        Assert.assertNotNull(keyValue);

        KeyableAlternateAutoUuidKey keyable = (KeyableAlternateAutoUuidKey) Keyable.query(
                KeyableAlternateAutoUuidKey.class,
                "primaryUuid",
                keyValue).get(0);

        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimaryUuid(), keyable.getPrimaryUuid());
    }

    /**
     * Ensure the success to query for a keyable entity by its primary key.
     */
    @Test
    public void expectSuccessToQueryKeyableByPrimaryKey()
    {
        KeyableAlternateAutoUuidKey entity = KeyableAlternateAutoUuidKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyableAlternateAutoUuidKey keyable = (KeyableAlternateAutoUuidKey) Keyable.query(
                KeyableAlternateAutoUuidKey.class, key).get(0);

        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimaryUuid(), keyable.getPrimaryUuid());
    }

    /**
     * Ensure the success to unregister a keyable entity by its instance.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByInstance()
    {
        KeyableAlternateAutoUuidKey entity = KeyableAlternateAutoUuidKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyManager.getInstance().unregister(entity);

        KeyableAlternateAutoUuidKey keyable = (KeyableAlternateAutoUuidKey) Keyable.retrieve(
                KeyableAlternateAutoUuidKey.class, key);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyableAlternateAutoUuidKey.class));
    }

    /**
     * Ensure the success to unregister a keyable by its class.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByKeyableClass()
    {
        KeyableAlternateAutoUuidKey entity = KeyableAlternateAutoUuidKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyManager.getInstance().unregisterByKeyableClass(KeyableAlternateAutoUuidKey.class);

        KeyableAlternateAutoUuidKey keyable = (KeyableAlternateAutoUuidKey) Keyable.retrieve(
                KeyableAlternateAutoUuidKey.class, key);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyableAlternateAutoUuidKey.class));
    }

    /**
     * Ensure the success to unregister a keyable by its key type.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByKeyClass()
    {
        KeyableAlternateAutoUuidKey entity = KeyableAlternateAutoUuidKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyManager.getInstance().unregisterByKeyClass(KeyableAlternateAutoUuidKey.class, UUID.class);

        KeyableAlternateAutoUuidKey keyable = (KeyableAlternateAutoUuidKey) Keyable.retrieve(
                KeyableAlternateAutoUuidKey.class, key);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyableAlternateAutoUuidKey.class));
    }

    /**
     * Ensure the success to unregister a keyable by its key name.<br>
     * In this test, we will unregister the primary key of the entity (which should be forbidden)
     */
    @Test
    public void expectSuccessToUnregisterKeyableByKeyName()
    {
        KeyableAlternateAutoUuidKey entity = KeyableAlternateAutoUuidKey.builder().build();
        Assert.assertNotNull(entity);

        IKey primary = entity.getPrimaryKey();
        IKey alternate = entity.getKey("alternateAutoUuid");
        Assert.assertNotNull(primary);
        Assert.assertNotNull(alternate);

        // TODO Unregistering a primary key should be forbidden
        KeyManager.getInstance().unregisterByKeyName(KeyableAlternateAutoUuidKey.class, "primaryUuid");

        KeyableAlternateAutoUuidKey keyable = (KeyableAlternateAutoUuidKey) Keyable.retrieve(
                KeyableAlternateAutoUuidKey.class, primary);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyableAlternateAutoUuidKey.class));

        keyable = (KeyableAlternateAutoUuidKey) Keyable.retrieve(KeyableAlternateAutoUuidKey.class, alternate);
        Assert.assertNotNull(keyable);
    }

    /**
     * Ensure the success to count keyable instances by keyable class.
     */
    @Test
    public void expectSuccessToCountKeyableByKeyableClass()
    {
        int value = getRandomNumber(100);

        createMultipleKeyable(value);

        Assert.assertEquals(value, KeyManager.getInstance().countByKeyableClass(KeyableAlternateAutoUuidKey.class));
    }

    /**
     * Ensure the success to count keyable instances by primary key.
     */
    @Test
    public void expectSuccessToCountKeyableByPrimaryKey()
    {
        int value = getRandomNumber(100);

        createMultipleKeyable(value);

        KeyableAlternateAutoUuidKey entity = KeyableAlternateAutoUuidKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        Assert.assertEquals(value + 1, KeyManager.getInstance().countByKey(key));
    }

    /**
     * Ensure the success to count keyable instances by alternate key.
     */
    @Test
    public void expectSuccessToCountKeyableByAlternateKey()
    {
        int value = getRandomNumber(100);

        createMultipleKeyable(value);

        KeyableAlternateAutoUuidKey entity = KeyableAlternateAutoUuidKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getKey("alternateAutoUuid");
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

        KeyableAlternateAutoUuidKey entity = KeyableAlternateAutoUuidKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        Assert.assertEquals(value + 1, KeyManager.getInstance().countByKeyName(key.getReferenceClass(), key.getName()));
    }
}