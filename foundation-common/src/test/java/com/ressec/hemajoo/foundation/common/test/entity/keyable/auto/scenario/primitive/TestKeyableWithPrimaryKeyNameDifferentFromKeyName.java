/*
 * (C) Copyright IBM Corp. 2019 - All Rights Reserved
 * ---------------------------------------------------------------------------
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 * ---------------------------------------------------------------------------
 */
package com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.scenario.primitive;

import com.ressec.hemajoo.foundation.common.entity.keyable.KeyManager;
import com.ressec.hemajoo.foundation.common.entity.keyable.exception.KeyException;
import com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.model.primitive.KeyableWithDifferentKeyAndFieldName;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for the {@link KeyableWithDifferentKeyAndFieldName} entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public class TestKeyableWithPrimaryKeyNameDifferentFromKeyName
{
    @Before
    public void setUp() throws Exception
    {
        KeyManager.getInstance().unregisterKeysByKeyableType(KeyableWithDifferentKeyAndFieldName.class);
    }

    @After
    public void tearDown() throws Exception
    {
        KeyManager.getInstance().shutdown();
    }

    /**
     * Ensure the failure to create a keyable with a primary key without specifying a key value.
     */
    @Test(expected = KeyException.class)
    public void expectFailureToCreateKeyableWithMandatoryPrimaryKeyWithoutKeyValue()
    {
        KeyableWithDifferentKeyAndFieldName entity = KeyableWithDifferentKeyAndFieldName.builder()
                .build();
    }

    /**
     * Ensure the success to create a keyable and to retrieve the count of registered keyables based on the key name.
     */
    @Test
    public void expectSuccessToCreateKeyableAndRetrieveRegisteredKeyableCountBasedOnKeyName()
    {
        KeyableWithDifferentKeyAndFieldName entity = KeyableWithDifferentKeyAndFieldName.builder()
                .name("Alexandre")
                .build();

        Assert.assertNotNull(entity);

        int count = KeyManager.getInstance().countByKeyName(KeyableWithDifferentKeyAndFieldName.class, "official"); // Key name
        Assert.assertEquals(1, count);
    }

    /**
     * Context: Create a keyable entity and have it registered by the key manager.<p></p>
     * Query: Get the count of registered keyables based on the primary key field name (not the key name).<p></p>
     * Result (expected): Returns 0 entities.
     */
    @Test
    public void expectSuccessToCreateKeyableAndDoNotRetrieveRegisteredKeyableCountBasedOnFieldName()
    {
        KeyableWithDifferentKeyAndFieldName entity = KeyableWithDifferentKeyAndFieldName.builder()
                .name("Pierre")
                .build();

        Assert.assertNotNull(entity);

        int count = KeyManager.getInstance().countByKeyName(KeyableWithDifferentKeyAndFieldName.class, "name"); // Field name
        Assert.assertEquals(0, count);
    }
}