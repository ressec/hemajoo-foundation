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

import com.ressec.hemajoo.foundation.common.entity.keyable.KeyManager;
import com.ressec.hemajoo.foundation.common.entity.keyable.exception.KeyException;
import lombok.extern.log4j.Log4j2;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * Test case for the {@link KeyableCountryWithoutKey} entity.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@Log4j2
public class TestKeyableCountryWithoutKey
{
    @Before
    public void setUp() throws Exception
    {
        KeyManager.getInstance().unregisterByKeyableClass(KeyableCountryWithoutKey.class);
    }

    @After
    public void tearDown() throws Exception
    {
        KeyManager.getInstance().shutdown();
    }

    /**
     * Ensure an exception is raised while trying to create a keyable not having a primary key defined.
     */
    @Test(expected = KeyException.class)
    public void expectFailureToCreateKeyableWhenKeyableHasNoPrimaryKey()
    {
        KeyableCountryWithoutKey entity = KeyableCountryWithoutKey.builder()
                .numeric(250)
                .build();
    }
}