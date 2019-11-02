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
 * An abstract implementation of a checked exception.
 * <br><br>
 * A checked exception represents invalid conditions in areas outside the immediate control
 * of the program (invalid user input, database problems, network outages, absent files) are
 * subclasses of {@link Exception}.
 * <br><br>
 * A method is obliged to establish a policy for all checked exceptions thrown by its
 * implementation (either pass the checked exception further up the stack, or handle it somehow).
 * <br><br>
 * It is somewhat confusing, but note as well that {@link RuntimeException} (unchecked) is itself
 * a subclass of {@link Exception} (checked).
 *
 * @author <a href="mailto:christophe.resse@gmail.com">Christophe Resse</a>
 * @version 1.0.0
 */
public abstract class AbstractCheckedException extends Exception
{
    /**
     * Default serialization identifier.
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new checked exception based on a parent exception.
     *
     * @param exception Parent exception.
     */
    public AbstractCheckedException(final Exception exception)
    {
        super(exception);
    }

    /**
     * Creates a new checked exception based on a message.
     *
     * @param message Message of the exception.
     */
    public AbstractCheckedException(final String message)
    {
        super(message);
    }

    /**
     * Creates a new checked exception based on a message and a parent exception.
     *
     * @param message   Message of the exception.
     * @param exception Parent exception.
     */
    public AbstractCheckedException(final String message, final Exception exception)
    {
        super(message + exception.getMessage(), exception);
    }
}

