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
package com.ressec.hemajoo.foundation.common.test.entity.keyable;

import com.ressec.hemajoo.foundation.common.test.entity.keyable.auto.scenario.*;
import com.ressec.hemajoo.foundation.common.test.entity.keyable.model.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test suite for keyable entities.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        TestKeyableCountryWithoutKey.class,
        TestKeyableCountryWithPrimaryKey.class,
        TestKeyableCountryWithPrimaryAndAlternateKey.class,
        TestKeyableCountryWithError.class,

        TestKeyableWithOnlyAlternateAutoKey.class,
        TestKeyableWithPrimaryAutoKeyAsString.class,
        TestKeyableWithPrimaryAutoKeyAsPrimitiveByte.class,
        TestKeyableWithPrimaryAutoKeyAsPrimitiveShort.class,
        TestKeyableWithPrimaryAutoKeyAsPrimitiveInteger.class,
        TestKeyableWithPrimaryAutoKeyAsPrimitiveLong.class,

        TestKeyableWithMultiplePrimaryKey.class,
        TestKeyableWithSameAlternateKeyName.class,
        TestKeyableWithPrimaryKeyNameDifferentFromKeyName.class,
})
public class KeyableTestSuite
{
    // Empty.
}
