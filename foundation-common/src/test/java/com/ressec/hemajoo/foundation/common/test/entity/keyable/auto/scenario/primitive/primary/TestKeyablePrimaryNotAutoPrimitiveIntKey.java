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
import com.ressec.hemajoo.foundation.common.entity.keyable.exception.KeyException;
import com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.model.primitive.primary.KeyablePrimaryNotAutoPrimitiveIntKey;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Random;

/**
 * Test case for the {@link KeyablePrimaryNotAutoPrimitiveIntKey} entity.
 * <p>
 * The underlying test class implements only a primitive key:<ul>
 * - a primary key of type primitive int with the property auto set to false</li>
 * </ul>
 * </p>
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public class TestKeyablePrimaryNotAutoPrimitiveIntKey
{
    /**
     * Random number generator.
     */
    private Random random = new Random();

    @Before
    public void setUp()
    {
        KeyManager.getInstance().unregisterByKeyableClass(KeyablePrimaryNotAutoPrimitiveIntKey.class);
    }

    @After
    public void tearDown()
    {
        KeyManager.getInstance().shutdown();
    }

    /**
     * Creates a keyable entity.
     * @param primary Primary key value.
     * @return Created keyable entity.
     */
    private KeyablePrimaryNotAutoPrimitiveIntKey createKeyable(final int primary)
    {
        return KeyablePrimaryNotAutoPrimitiveIntKey.builder()
                .primary(primary)
                .build();
    }

    /**
     * Create multiple keyable instances.
     */
    private void createMultipleKeyable(final int count)
    {
        for (int i = 1; i < count + 1; i++)
        {
            KeyablePrimaryNotAutoPrimitiveIntKey entity = KeyablePrimaryNotAutoPrimitiveIntKey.builder()
                    .primary(i)
                    .build();
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
        KeyablePrimaryNotAutoPrimitiveIntKey entity = createKeyable(1);
        Assert.assertNotNull(entity);
    }

    /**
     * Ensure an exception is raised while trying to create a keyable not providing a primary key value.
     */
    @Test(expected = KeyException.class)
    public void expectFailureToCreateKeyable()
    {
        KeyablePrimaryNotAutoPrimitiveIntKey.builder().build();
    }

    /**
     * Ensure an exception is raised while trying to create a keyable by providing a primary key value already used.
     */
    @Test(expected = KeyException.class)
    public void expectFailureToCreateKeyableWithPrimaryKeyValueAlreadyUsed()
    {
        int value = getRandomNumber(100);

        createMultipleKeyable(value);

        createKeyable(1); // This key is already used!
    }

    /**
     * Ensure the success to retrieve a keyable entity by its primary key.
     */
    @Test
    public void expectSuccessToRetrieveKeyableByPrimaryKey()
    {
        KeyablePrimaryNotAutoPrimitiveIntKey entity = createKeyable(1);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyablePrimaryNotAutoPrimitiveIntKey keyable = (KeyablePrimaryNotAutoPrimitiveIntKey) Keyable.retrieve(
                KeyablePrimaryNotAutoPrimitiveIntKey.class, key);
        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimary(), keyable.getPrimary());
    }

    /**
     * Ensure the success to retrieve a keyable entity by its primary key name.
     */
    @Test
    public void expectSuccessToRetrieveKeyableByPrimaryKeyName()
    {
        KeyablePrimaryNotAutoPrimitiveIntKey entity = createKeyable(1);

        int keyValue = entity.getPrimary();
        Assert.assertNotEquals(0, keyValue);

        KeyablePrimaryNotAutoPrimitiveIntKey keyable = (KeyablePrimaryNotAutoPrimitiveIntKey) Keyable.retrieve(
                KeyablePrimaryNotAutoPrimitiveIntKey.class, "primary", keyValue);
        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimary(), keyable.getPrimary());
    }

    /**
     * Ensure the success to query for a keyable entity by its primary key name.
     */
    @Test
    public void expectSuccessToQueryKeyableByPrimaryKeyName()
    {
        KeyablePrimaryNotAutoPrimitiveIntKey entity = createKeyable(1);
        Assert.assertNotNull(entity);

        int keyValue = entity.getPrimary();
        Assert.assertNotEquals(0, keyValue);

        KeyablePrimaryNotAutoPrimitiveIntKey keyable = (KeyablePrimaryNotAutoPrimitiveIntKey) Keyable.query(
                KeyablePrimaryNotAutoPrimitiveIntKey.class,
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
        KeyablePrimaryNotAutoPrimitiveIntKey entity = createKeyable(1);
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyablePrimaryNotAutoPrimitiveIntKey keyable = (KeyablePrimaryNotAutoPrimitiveIntKey) Keyable.query(
                KeyablePrimaryNotAutoPrimitiveIntKey.class, key).get(0);

        Assert.assertNotNull(keyable);
        Assert.assertEquals(entity.getPrimary(), keyable.getPrimary());
    }

    /**
     * Ensure the success to unregister a keyable entity by its instance.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByInstance()
    {
        KeyablePrimaryNotAutoPrimitiveIntKey entity = createKeyable(1);
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyManager.getInstance().unregister(entity);

        KeyablePrimaryNotAutoPrimitiveIntKey keyable = (KeyablePrimaryNotAutoPrimitiveIntKey) Keyable.retrieve(
                KeyablePrimaryNotAutoPrimitiveIntKey.class, key);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyablePrimaryNotAutoPrimitiveIntKey.class));
    }

    /**
     * Ensure the success to unregister a keyable by its class.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByKeyableClass()
    {
        KeyablePrimaryNotAutoPrimitiveIntKey entity = createKeyable(1);
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyManager.getInstance().unregisterByKeyableClass(KeyablePrimaryNotAutoPrimitiveIntKey.class);

        KeyablePrimaryNotAutoPrimitiveIntKey keyable = (KeyablePrimaryNotAutoPrimitiveIntKey) Keyable.retrieve(
                KeyablePrimaryNotAutoPrimitiveIntKey.class, key);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyablePrimaryNotAutoPrimitiveIntKey.class));
    }

    /**
     * Ensure the success to unregister a keyable by its key type.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByKeyClass()
    {
        KeyablePrimaryNotAutoPrimitiveIntKey entity = createKeyable(1);
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        KeyManager.getInstance().unregisterByKeyClass(KeyablePrimaryNotAutoPrimitiveIntKey.class, int.class);

        KeyablePrimaryNotAutoPrimitiveIntKey keyable = (KeyablePrimaryNotAutoPrimitiveIntKey) Keyable.retrieve(
                KeyablePrimaryNotAutoPrimitiveIntKey.class, key);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyablePrimaryNotAutoPrimitiveIntKey.class));
    }

    /**
     * Ensure the success to unregister a key for a keyable class given the key name.
     */
    @Test
    public void expectSuccessToUnregisterKeyableByKeyName()
    {
        KeyablePrimaryNotAutoPrimitiveIntKey entity = createKeyable(1);
        Assert.assertNotNull(entity);

        IKey primary = entity.getPrimaryKey();
        Assert.assertNotNull(primary);

        // TODO Unregistering a primary key should be forbidden
        KeyManager.getInstance().unregisterByKeyName(KeyablePrimaryNotAutoPrimitiveIntKey.class, "primary");

        KeyablePrimaryNotAutoPrimitiveIntKey keyable = (KeyablePrimaryNotAutoPrimitiveIntKey) Keyable.retrieve(
                KeyablePrimaryNotAutoPrimitiveIntKey.class, primary);
        Assert.assertNull(keyable);

        Assert.assertEquals(0, KeyManager.getInstance().countByKeyableClass(KeyablePrimaryNotAutoPrimitiveIntKey.class));
    }

    /**
     * Ensure the success to count the registered keyable instances given the keyable class.
     */
    @Test
    public void expectSuccessToCountKeyableByKeyableClass()
    {
        int value = getRandomNumber(100);

        createMultipleKeyable(value);

        Assert.assertEquals(value, KeyManager.getInstance().countByKeyableClass(KeyablePrimaryNotAutoPrimitiveIntKey.class));
    }

    /**
     * Ensure the success to count the registered keyable instances given the primary key.
     */
    @Test
    public void expectSuccessToCountKeyableByPrimaryKey()
    {
        int value = getRandomNumber(100);

        createMultipleKeyable(value);

        KeyablePrimaryNotAutoPrimitiveIntKey entity = createKeyable(254);
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

        KeyablePrimaryNotAutoPrimitiveIntKey entity = createKeyable(145);
        Assert.assertNotNull(entity);

        IKey key = entity.getPrimaryKey();
        Assert.assertNotNull(key);

        Assert.assertEquals(value + 1, KeyManager.getInstance().countByKeyName(key.getReferenceClass(), key.getName()));
    }
}