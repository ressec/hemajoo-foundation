/*
 * (C) Copyright IBM Corp. 2019 - All Rights Reserved
 * ---------------------------------------------------------------------------
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 * ---------------------------------------------------------------------------
 */
package com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.scenario.primitive.alternate;

import com.ressec.hemajoo.foundation.common.entity.keyable.*;
import com.ressec.hemajoo.foundation.common.entity.keyable.exception.KeyException;
import com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.model.primitive.alternate.KeyableAlternateAutoPrimitiveIntKey;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;
import java.util.UUID;

/**
 * Test case for the {@link KeyableAlternateAutoPrimitiveIntKey} entity.
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
public class TestKeyableAlternateAutoPrimitiveIntKey
{
    /**
     * Random number generator.
     */
    private Random random = new Random();

    @Before
    public void setUp()
    {
        KeyManager.getInstance().unregisterByKeyableClass(KeyableAlternateAutoPrimitiveIntKey.class);
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
    private KeyableAlternateAutoPrimitiveIntKey createKeyable()
    {
        return KeyableAlternateAutoPrimitiveIntKey.builder().build();
    }

    /**
     * Create multiple keyable instances.
     */
    private void createMultipleKeyable(final int count)
    {
        KeyableAlternateAutoPrimitiveIntKey entity;

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
     * Ensure the success to create a keyable.
     */
    @Test
    public void expectSuccessToCreateKeyable()
    {
        KeyableAlternateAutoPrimitiveIntKey entity = createKeyable();
        Assert.assertNotNull(entity);
    }

    /**
     * Ensure an exception is raised while trying to create a keyable with an auto primary UUID when a key value is provided.
     */
    @Test(expected = KeyException.class) // TODO Should throw a KeyInitializationException instead of a KeyException
    public void expectFailureToCreateKeyableWithAutoPrimaryUuidKeyWhenValueIsProvided()
    {
        KeyableAlternateAutoPrimitiveIntKey.builder()
                .primary(UUID.randomUUID())
                .build();
    }

    /**
     * Ensure an exception is raised while trying to create a keyable with an auto alternate UUID key when the key value is provided.
     */
    @Test(expected = KeyException.class) // TODO Should throw a KeyInitializationException instead of a KeyException
    public void expectFailureToCreateKeyableWithAutoAlternateUuidKeyWhenValueIsProvided()
    {
        KeyableAlternateAutoPrimitiveIntKey.builder()
                .alternate(UUID.randomUUID())
                .build();
    }

    /**
     * Ensure the success to retrieve a keyable entity by its primary key.
     */
    @Test
    public void expectSuccessToRetrieveKeyableByPrimaryKey()
    {
        KeyableAlternateAutoPrimitiveIntKey entity = createKeyableWithoutPrimaryAndAlternateKeyValue();

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyableAlternateAutoPrimitiveIntKey keyable = (KeyableAlternateAutoPrimitiveIntKey) Keyable.retrieve(
                KeyableAlternateAutoPrimitiveIntKey.class, key);
        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimaryUuid(), keyable.getPrimaryUuid());
    }

    /**
     * Ensure the success to retrieve a keyable entity by its primary key name.
     */
    @Test
    public void expectSuccessToRetrieveKeyableByPrimaryKeyName()
    {
        KeyableAlternateAutoPrimitiveIntKey entity = createKeyableWithoutPrimaryAndAlternateKeyValue();

        UUID keyValue = entity.getPrimaryUuid();
        Assert.assertNotNull(keyValue);

        KeyableAlternateAutoPrimitiveIntKey keyable = (KeyableAlternateAutoPrimitiveIntKey) Keyable.retrieve(
                KeyableAlternateAutoPrimitiveIntKey.class, "primaryUuid", keyValue);
        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimaryUuid(), keyable.getPrimaryUuid());
    }

    /**
     * Ensure the success to retrieve a keyable entity by its alternate key.
     */
    @Test
    public void expectSuccessToRetrieveKeyableByAlternateKey()
    {
        KeyableAlternateAutoPrimitiveIntKey entity = KeyableAlternateAutoPrimitiveIntKey.builder().build();

        IKey key = entity.getKey("alternateAutoUuid");
        Assert.assertNotNull(key);

        KeyableAlternateAutoPrimitiveIntKey keyable = (KeyableAlternateAutoPrimitiveIntKey) Keyable.retrieve(
                KeyableAlternateAutoPrimitiveIntKey.class, key);
        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getAlternateAutoUuid(), keyable.getAlternateAutoUuid());
    }

    /**
     * Ensure the success to retrieve a keyable entity by its alternate key name.
     */
    @Test
    public void expectSuccessToRetrieveKeyableByAlternateKeyName()
    {
        KeyableAlternateAutoPrimitiveIntKey entity = KeyableAlternateAutoPrimitiveIntKey.builder().build();

        UUID keyValue = entity.getAlternateAutoUuid();
        Assert.assertNotNull(keyValue);

        KeyableAlternateAutoPrimitiveIntKey keyable = (KeyableAlternateAutoPrimitiveIntKey) Keyable.retrieve(
                KeyableAlternateAutoPrimitiveIntKey.class, "alternateAutoUuid", keyValue);
        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getAlternateAutoUuid(), keyable.getAlternateAutoUuid());
    }

    /**
     * Ensure the success to query for a keyable entity by its primary key name.
     */
    @Test
    public void expectSuccessToQueryKeyableByPrimaryKeyName()
    {
        KeyableAlternateAutoPrimitiveIntKey entity = KeyableAlternateAutoPrimitiveIntKey.builder().build();
        Assert.assertNotNull(entity);

        UUID keyValue = entity.getPrimaryUuid();
        Assert.assertNotNull(keyValue);

        KeyableAlternateAutoPrimitiveIntKey keyable = (KeyableAlternateAutoPrimitiveIntKey) Keyable.query(
                KeyableAlternateAutoPrimitiveIntKey.class,
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
        KeyableAlternateAutoPrimitiveIntKey entity = KeyableAlternateAutoPrimitiveIntKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyableAlternateAutoPrimitiveIntKey keyable = (KeyableAlternateAutoPrimitiveIntKey) Keyable.query(
                KeyableAlternateAutoPrimitiveIntKey.class, key).get(0);

        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimaryUuid(), keyable.getPrimaryUuid());
    }

    /**
     * Ensure the success to unregister a keyable entity by its instance.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByInstance()
    {
        KeyableAlternateAutoPrimitiveIntKey entity = KeyableAlternateAutoPrimitiveIntKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyManager.getInstance().unregister(entity);

        KeyableAlternateAutoPrimitiveIntKey keyable = (KeyableAlternateAutoPrimitiveIntKey) Keyable.retrieve(
                KeyableAlternateAutoPrimitiveIntKey.class, key);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyableAlternateAutoPrimitiveIntKey.class));
    }

    /**
     * Ensure the success to unregister a keyable by its class.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByKeyableClass()
    {
        KeyableAlternateAutoPrimitiveIntKey entity = KeyableAlternateAutoPrimitiveIntKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyManager.getInstance().unregisterByKeyableClass(KeyableAlternateAutoPrimitiveIntKey.class);

        KeyableAlternateAutoPrimitiveIntKey keyable = (KeyableAlternateAutoPrimitiveIntKey) Keyable.retrieve(
                KeyableAlternateAutoPrimitiveIntKey.class, key);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyableAlternateAutoPrimitiveIntKey.class));
    }

    /**
     * Ensure the success to unregister a keyable by its key type.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByKeyClass()
    {
        KeyableAlternateAutoPrimitiveIntKey entity = KeyableAlternateAutoPrimitiveIntKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyManager.getInstance().unregisterByKeyClass(KeyableAlternateAutoPrimitiveIntKey.class, UUID.class);

        KeyableAlternateAutoPrimitiveIntKey keyable = (KeyableAlternateAutoPrimitiveIntKey) Keyable.retrieve(
                KeyableAlternateAutoPrimitiveIntKey.class, key);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyableAlternateAutoPrimitiveIntKey.class));
    }

    /**
     * Ensure the success to unregister a keyable by its key name.<br>
     * In this test, we will unregister the primary key of the entity (which should be forbidden)
     */
    @Test
    public void expectSuccessToUnregisterKeyableByKeyName()
    {
        KeyableAlternateAutoPrimitiveIntKey entity = KeyableAlternateAutoPrimitiveIntKey.builder().build();
        Assert.assertNotNull(entity);

        IKey primary = entity.getPrimaryKey();
        IKey alternate = entity.getKey("alternateAutoUuid");
        Assert.assertNotNull(primary);
        Assert.assertNotNull(alternate);

        // TODO Unregistering a primary key should be forbidden
        KeyManager.getInstance().unregisterByKeyName(KeyableAlternateAutoPrimitiveIntKey.class, "primaryUuid");

        KeyableAlternateAutoPrimitiveIntKey keyable = (KeyableAlternateAutoPrimitiveIntKey) Keyable.retrieve(
                KeyableAlternateAutoPrimitiveIntKey.class, primary);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyableAlternateAutoPrimitiveIntKey.class));

        keyable = (KeyableAlternateAutoPrimitiveIntKey) Keyable.retrieve(KeyableAlternateAutoPrimitiveIntKey.class, alternate);
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

        Assert.assertEquals(value, KeyManager.getInstance().countByKeyableClass(KeyableAlternateAutoPrimitiveIntKey.class));
    }

    /**
     * Ensure the success to count keyable instances by primary key.
     */
    @Test
    public void expectSuccessToCountKeyableByPrimaryKey()
    {
        int value = getRandomNumber(100);

        createMultipleKeyable(value);

        KeyableAlternateAutoPrimitiveIntKey entity = KeyableAlternateAutoPrimitiveIntKey.builder().build();
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

        KeyableAlternateAutoPrimitiveIntKey entity = KeyableAlternateAutoPrimitiveIntKey.builder().build();
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

        KeyableAlternateAutoPrimitiveIntKey entity = KeyableAlternateAutoPrimitiveIntKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        Assert.assertEquals(value + 1, KeyManager.getInstance().countByKeyName(key.getReferenceClass(), key.getName()));
    }
}