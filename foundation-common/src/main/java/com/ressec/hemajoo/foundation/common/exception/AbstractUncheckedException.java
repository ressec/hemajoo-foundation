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
package com.ressec.hemajoo.foundation.common.exception;

/**
 * An abstract implementation of an unchecked exception.
 * <br><br>
 * Unchecked runtime exceptions represent conditions that, generally speaking, reflect errors in program's logic and
 * cannot be reasonably recovered from at run time. Unchecked exceptions are subclasses of RuntimeException,
 * and are usually implemented using InvalidArgumentException, NullPointerException, or
 * IllegalStateException a method is not obliged to establish a policy for the unchecked exceptions thrown by
 * its implementation (and they almost always do not do so).
 *
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public abstract class AbstractUncheckedException extends RuntimeException
{
    /**
     * Default serialization identifier.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new unchecked exception based on a parent exception.
     *
     * @param exception Parent exception.
     */
    public AbstractUncheckedException(final Exception exception)
    {
        super(exception);
    }

    /**
     * Creates a new unchecked exception based on a message.
     *
     * @param message Message of the exception.
     */
    public AbstractUncheckedException(final String message)
    {
        super(message);
    }

    /**
     * Creates a new unchecked exception based on a message and a parent exception.
     *
     * @param message   Message of the exception.
     * @param exception Parent exception.
     */
    public AbstractUncheckedException(final String message, final Exception exception)
    {
        super(message, exception);
    }
}
