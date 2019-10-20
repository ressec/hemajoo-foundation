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
package com.ressec.hemajoo.foundation.common.entity.keyable;

/**
 * Interface providing the behavior of a key.
 *
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public interface IKey
{
    /**
     * Returns the name of the key.
     *
     * @return Key name.
     */
    String getName();

    /**
     * Returns the type of the key.
     *
     * @return Key type.
     */
    Class<?> getType();

    /**
     * Returns the value of the key.
     *
     * @return Key value.
     */
    Object getValue();

    /**
     * Returns if the key is the primary key ?
     *
     * @return True if the key is the primary key, false otherwise.
     */
    boolean isPrimary();

    /**
     * Returns if the key value is mandatory ?
     *
     * @return True if the key value is mandatory, false otherwise.
     */
    boolean isMandatory();

    /**
     * Returns if the key value must be unique ?
     *
     * @return True if the key value must be unique, false otherwise.
     */
    boolean isUnique();
}
