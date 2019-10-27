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

import com.ressec.hemajoo.foundation.common.exception.AbstractUncheckedException;

/**
 * Exception thrown to indicate an error occurred when manipulating the key manager.
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public class KeyManagerException extends AbstractUncheckedException
{
    /**
     * Default serialization identifier.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Avoid creating empty exception.
     */
    private KeyManagerException()
    {
        super();
    }

    /**
     * Creates a new exception based on a parent exception.
     * @param exception Parent exception.
     */
    public KeyManagerException(final Exception exception)
    {
        super(exception);
    }

    /**
     * Creates a new exception based on a parent exception.
     * @param message Message of the exception.
     */
    public KeyManagerException(final String message)
    {
        super(message);
    }

    /**
     * Creates a new exception based on a message and a parent exception.
     * @param message   Message of the exception.
     * @param exception Parent exception.
     */
    public KeyManagerException(final String message, final Exception exception)
    {
        super(message + exception.getMessage(), exception);
    }
}

