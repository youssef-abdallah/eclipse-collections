/*
 * Copyright (c) 2021 Goldman Sachs.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.api.set;

import java.util.Collection;

import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.collection.FixedSizeCollection;

/**
 * A FixedSizeSet is a set that may be mutated, but cannot grow or shrink in size.
 */
public interface FixedSizeSet<T>
        extends MutableSet<T>, FixedSizeCollection<T>
{
    @Override
    MutableSet<T> with(T element);

    @Override
    MutableSet<T> without(T element);

    @Override
    MutableSet<T> withAll(Iterable<? extends T> elements);

    @Override
    MutableSet<T> withoutAll(Iterable<? extends T> elements);

    @Override
    FixedSizeSet<T> tap(Procedure<? super T> procedure);

    @Override
    default boolean containsAll(Collection<?> source)
    {
        return FixedSizeCollection.super.containsAll(source);
    }
}
