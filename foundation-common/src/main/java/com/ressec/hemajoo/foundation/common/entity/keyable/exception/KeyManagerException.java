/*
 * (C) Copyright IBM Corp. 2019 - All Rights Reserved
 * ---------------------------------------------------------------------------
 * The source code for this program is not published or otherwise
 * divested of its trade secrets, irrespective of what has
 * been deposited with the U.S. Copyright Office.
 * ---------------------------------------------------------------------------
 */
package com.ressec.hemajoo.foundation.common.entity.keyable.exception;

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

