/*
 * (C) Copyright IBM Corp. 2019 - All Rights Reserved
 * ---------------------------------------------------------------------------
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 * ---------------------------------------------------------------------------
 */
package com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.scenario.primitive.primary;

import com.ressec.hemajoo.foundation.common.entity.keyable.IKey;
import com.ressec.hemajoo.foundation.common.entity.keyable.KeyManager;
import com.ressec.hemajoo.foundation.common.entity.keyable.Keyable;
import com.ressec.hemajoo.foundation.common.entity.keyable.PrimaryKey;
import com.ressec.hemajoo.foundation.common.entity.keyable.exception.KeyException;
import com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.model.primitive.primary.KeyablePrimaryAutoPrimitiveIntKey;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

/**
 * Test case for the {@link KeyablePrimaryAutoPrimitiveIntKey} entity.
 * <p>
 * The underlying test class implements only a primitive key:<ul>
 * - a {@link PrimaryKey#auto()} key of type primitive int</li>
 * </ul>
 * </p>
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public class TestKeyablePrimaryAutoPrimitiveIntKey
{
    /**
     * Random number generator.
     */
    private Random random = new Random();

    @Before
    public void setUp()
    {
        KeyManager.getInstance().unregisterByKeyableClass(KeyablePrimaryAutoPrimitiveIntKey.class);
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
    private KeyablePrimaryAutoPrimitiveIntKey createKeyable()
    {
        return KeyablePrimaryAutoPrimitiveIntKey.builder().build();
    }

    /**
     * Create multiple keyable instances.
     */
    private void createMultipleKeyable(final int count)
    {
        for (int i = 0; i < count; i++)
        {
            createKeyable();
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
        KeyablePrimaryAutoPrimitiveIntKey entity = createKeyable();
        Assert.assertNotNull(entity);
    }

    /**
     * Ensure an exception is raised while trying to create a keyable with a primary key with auto property when a key
     * value is provided.
     */
    @Test(expected = KeyException.class) // TODO Should throw a KeyInitializationException instead of a KeyException
    public void expectFailureToCreateKeyableWithPrimaryKeyAutoWhenKeyValueIsProvided()
    {
        KeyablePrimaryAutoPrimitiveIntKey.builder()
                .primary(158)
                .build();
    }

    /**
     * Ensure the success to retrieve a keyable entity by its primary key.
     */
    @Test
    public void expectSuccessToRetrieveKeyableByPrimaryKey()
    {
        KeyablePrimaryAutoPrimitiveIntKey entity = createKeyable();

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyablePrimaryAutoPrimitiveIntKey keyable = (KeyablePrimaryAutoPrimitiveIntKey) Keyable.retrieve(
                KeyablePrimaryAutoPrimitiveIntKey.class, key);
        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimary(), keyable.getPrimary());
    }

    /**
     * Ensure the success to retrieve a keyable entity by its primary key name.
     */
    @Test
    public void expectSuccessToRetrieveKeyableByPrimaryKeyName()
    {
        KeyablePrimaryAutoPrimitiveIntKey entity = createKeyable();

        int keyValue = entity.getPrimary();
        Assert.assertNotEquals(0, keyValue);

        KeyablePrimaryAutoPrimitiveIntKey keyable = (KeyablePrimaryAutoPrimitiveIntKey) Keyable.retrieve(
                KeyablePrimaryAutoPrimitiveIntKey.class, "primary", keyValue);
        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimary(), keyable.getPrimary());
    }

    /**
     * Ensure the success to query for a keyable entity by its primary key name.
     */
    @Test
    public void expectSuccessToQueryKeyableByPrimaryKeyName()
    {
        KeyablePrimaryAutoPrimitiveIntKey entity = createKeyable();
        Assert.assertNotNull(entity);

        int keyValue = entity.getPrimary();
        Assert.assertNotEquals(0, keyValue);

        KeyablePrimaryAutoPrimitiveIntKey keyable = (KeyablePrimaryAutoPrimitiveIntKey) Keyable.query(
                KeyablePrimaryAutoPrimitiveIntKey.class,
                "primary",
                keyValue).get(0);

        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimary(), keyable.getPrimary());
    }

    /**
     * Ensure the success to query for a keyable entity by its primary key.
     */
    @Test
    public void expectSuccessToQueryKeyableByPrimaryKey()
    {
        KeyablePrimaryAutoPrimitiveIntKey entity = createKeyable();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyablePrimaryAutoPrimitiveIntKey keyable = (KeyablePrimaryAutoPrimitiveIntKey) Keyable.query(
                KeyablePrimaryAutoPrimitiveIntKey.class, key).get(0);

        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimary(), keyable.getPrimary());
    }

    /**
     * Ensure the success to unregister a keyable entity by its instance.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByInstance()
    {
        KeyablePrimaryAutoPrimitiveIntKey entity = KeyablePrimaryAutoPrimitiveIntKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyManager.getInstance().unregister(entity);

        KeyablePrimaryAutoPrimitiveIntKey keyable = (KeyablePrimaryAutoPrimitiveIntKey) Keyable.retrieve(
                KeyablePrimaryAutoPrimitiveIntKey.class, key);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyablePrimaryAutoPrimitiveIntKey.class));
    }

    /**
     * Ensure the success to unregister a keyable by its class.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByKeyableClass()
    {
        KeyablePrimaryAutoPrimitiveIntKey entity = KeyablePrimaryAutoPrimitiveIntKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyManager.getInstance().unregisterByKeyableClass(KeyablePrimaryAutoPrimitiveIntKey.class);

        KeyablePrimaryAutoPrimitiveIntKey keyable = (KeyablePrimaryAutoPrimitiveIntKey) Keyable.retrieve(
                KeyablePrimaryAutoPrimitiveIntKey.class, key);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyablePrimaryAutoPrimitiveIntKey.class));
    }

    /**
     * Ensure the success to unregister a keyable by its key type.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByKeyClass()
    {
        KeyablePrimaryAutoPrimitiveIntKey entity = KeyablePrimaryAutoPrimitiveIntKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyManager.getInstance().unregisterByKeyClass(KeyablePrimaryAutoPrimitiveIntKey.class, int.class);

        KeyablePrimaryAutoPrimitiveIntKey keyable = (KeyablePrimaryAutoPrimitiveIntKey) Keyable.retrieve(
                KeyablePrimaryAutoPrimitiveIntKey.class, key);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyablePrimaryAutoPrimitiveIntKey.class));
    }

    /**
     * Ensure the success to unregister a key for a keyable class given the key name.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByKeyName()
    {
        KeyablePrimaryAutoPrimitiveIntKey entity = KeyablePrimaryAutoPrimitiveIntKey.builder().build();
        Assert.assertNotNull(entity);

        IKey primary = entity.getPrimaryKey();
        Assert.assertNotNull(primary);

        // TODO Unregistering a primary key should be forbidden
        KeyManager.getInstance().unregisterByKeyName(KeyablePrimaryAutoPrimitiveIntKey.class, "primary");

        KeyablePrimaryAutoPrimitiveIntKey keyable = (KeyablePrimaryAutoPrimitiveIntKey) Keyable.retrieve(
                KeyablePrimaryAutoPrimitiveIntKey.class, primary);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyablePrimaryAutoPrimitiveIntKey.class));
    }

    /**
     * Ensure the success to count the registered keyable instances given the keyable class.
     */
    @Test
    public void expectSuccessToCountKeyableByKeyableClass()
    {
        int value = getRandomNumber(100);

        createMultipleKeyable(value);

        Assert.assertEquals(value, KeyManager.getInstance().countByKeyableClass(KeyablePrimaryAutoPrimitiveIntKey.class));
    }

    /**
     * Ensure the success to count the registered keyable instances given the primary key.
     */
    @Test
    public void expectSuccessToCountKeyableByPrimaryKey()
    {
        int value = getRandomNumber(100);

        createMultipleKeyable(value);

        KeyablePrimaryAutoPrimitiveIntKey entity = KeyablePrimaryAutoPrimitiveIntKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        Assert.assertEquals(value + 1, KeyManager.getInstance().countByKey(key));
    }

    /**
     * Ensure the success to count the registered keyable instances given the primary key name.
     */
    @Test
    public void expectSuccessToCountKeyableByPrimaryKeyName()
    {
        int value = getRandomNumber(100);

        createMultipleKeyable(value);

        KeyablePrimaryAutoPrimitiveIntKey entity = KeyablePrimaryAutoPrimitiveIntKey.builder().build();
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        Assert.assertEquals(value + 1, KeyManager.getInstance().countByKeyName(key.getReferenceClass(), key.getName()));
    }
}