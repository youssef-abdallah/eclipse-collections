/*
 * Copyright (c) 2022 Goldman Sachs and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompany this distribution.
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 */

package org.eclipse.collections.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.DoubleSummaryStatistics;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.Iterator;
import java.util.List;
import java.util.LongSummaryStatistics;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

import org.eclipse.collections.api.BooleanIterable;
import org.eclipse.collections.api.ByteIterable;
import org.eclipse.collections.api.CharIterable;
import org.eclipse.collections.api.DoubleIterable;
import org.eclipse.collections.api.FloatIterable;
import org.eclipse.collections.api.IntIterable;
import org.eclipse.collections.api.LongIterable;
import org.eclipse.collections.api.RichIterable;
import org.eclipse.collections.api.ShortIterable;
import org.eclipse.collections.api.bag.Bag;
import org.eclipse.collections.api.bag.ImmutableBag;
import org.eclipse.collections.api.bag.MutableBag;
import org.eclipse.collections.api.bag.primitive.BooleanBag;
import org.eclipse.collections.api.bag.primitive.ByteBag;
import org.eclipse.collections.api.bag.primitive.CharBag;
import org.eclipse.collections.api.bag.primitive.DoubleBag;
import org.eclipse.collections.api.bag.primitive.FloatBag;
import org.eclipse.collections.api.bag.primitive.IntBag;
import org.eclipse.collections.api.bag.primitive.LongBag;
import org.eclipse.collections.api.bag.primitive.MutableBooleanBag;
import org.eclipse.collections.api.bag.primitive.MutableByteBag;
import org.eclipse.collections.api.bag.primitive.MutableCharBag;
import org.eclipse.collections.api.bag.primitive.MutableDoubleBag;
import org.eclipse.collections.api.bag.primitive.MutableFloatBag;
import org.eclipse.collections.api.bag.primitive.MutableIntBag;
import org.eclipse.collections.api.bag.primitive.MutableLongBag;
import org.eclipse.collections.api.bag.primitive.MutableShortBag;
import org.eclipse.collections.api.bag.primitive.ShortBag;
import org.eclipse.collections.api.bag.sorted.ImmutableSortedBag;
import org.eclipse.collections.api.bag.sorted.MutableSortedBag;
import org.eclipse.collections.api.block.function.Function;
import org.eclipse.collections.api.block.function.Function0;
import org.eclipse.collections.api.block.procedure.Procedure;
import org.eclipse.collections.api.collection.primitive.MutableBooleanCollection;
import org.eclipse.collections.api.collection.primitive.MutableByteCollection;
import org.eclipse.collections.api.collection.primitive.MutableCharCollection;
import org.eclipse.collections.api.collection.primitive.MutableDoubleCollection;
import org.eclipse.collections.api.collection.primitive.MutableFloatCollection;
import org.eclipse.collections.api.collection.primitive.MutableIntCollection;
import org.eclipse.collections.api.collection.primitive.MutableLongCollection;
import org.eclipse.collections.api.collection.primitive.MutableShortCollection;
import org.eclipse.collections.api.factory.Maps;
import org.eclipse.collections.api.factory.SortedBags;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;
import org.eclipse.collections.api.map.ImmutableMap;
import org.eclipse.collections.api.map.MapIterable;
import org.eclipse.collections.api.map.MutableMap;
import org.eclipse.collections.api.map.primitive.ObjectDoubleMap;
import org.eclipse.collections.api.map.primitive.ObjectLongMap;
import org.eclipse.collections.api.map.sorted.MutableSortedMap;
import org.eclipse.collections.api.multimap.Multimap;
import org.eclipse.collections.api.multimap.MutableMultimap;
import org.eclipse.collections.api.partition.PartitionIterable;
import org.eclipse.collections.api.set.ImmutableSet;
import org.eclipse.collections.api.set.MutableSet;
import org.eclipse.collections.api.set.primitive.MutableBooleanSet;
import org.eclipse.collections.api.set.primitive.MutableByteSet;
import org.eclipse.collections.api.set.primitive.MutableCharSet;
import org.eclipse.collections.api.set.primitive.MutableDoubleSet;
import org.eclipse.collections.api.set.primitive.MutableFloatSet;
import org.eclipse.collections.api.set.primitive.MutableIntSet;
import org.eclipse.collections.api.set.primitive.MutableLongSet;
import org.eclipse.collections.api.set.primitive.MutableShortSet;
import org.eclipse.collections.api.set.sorted.ImmutableSortedSet;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;
import org.eclipse.collections.api.tuple.Pair;
import org.eclipse.collections.api.tuple.primitive.BooleanBooleanPair;
import org.eclipse.collections.api.tuple.primitive.ByteBytePair;
import org.eclipse.collections.api.tuple.primitive.CharCharPair;
import org.eclipse.collections.api.tuple.primitive.DoubleDoublePair;
import org.eclipse.collections.api.tuple.primitive.FloatFloatPair;
import org.eclipse.collections.api.tuple.primitive.IntIntPair;
import org.eclipse.collections.api.tuple.primitive.LongLongPair;
import org.eclipse.collections.api.tuple.primitive.ShortShortPair;
import org.eclipse.collections.impl.bag.mutable.HashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.BooleanHashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.ByteHashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.CharHashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.DoubleHashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.FloatHashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.IntHashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.LongHashBag;
import org.eclipse.collections.impl.bag.mutable.primitive.ShortHashBag;
import org.eclipse.collections.impl.bag.sorted.mutable.TreeBag;
import org.eclipse.collections.impl.block.factory.Comparators;
import org.eclipse.collections.impl.block.factory.Functions;
import org.eclipse.collections.impl.block.factory.IntegerPredicates;
import org.eclipse.collections.impl.block.factory.Predicates;
import org.eclipse.collections.impl.block.factory.Predicates2;
import org.eclipse.collections.impl.block.factory.PrimitiveFunctions;
import org.eclipse.collections.impl.block.function.AddFunction;
import org.eclipse.collections.impl.block.function.NegativeIntervalFunction;
import org.eclipse.collections.impl.block.function.PassThruFunction0;
import org.eclipse.collections.impl.block.procedure.CollectionAddProcedure;
import org.eclipse.collections.impl.factory.Bags;
import org.eclipse.collections.impl.factory.Lists;
import org.eclipse.collections.impl.factory.Sets;
import org.eclipse.collections.impl.factory.SortedSets;
import org.eclipse.collections.impl.factory.primitive.BooleanBags;
import org.eclipse.collections.impl.factory.primitive.BooleanLists;
import org.eclipse.collections.impl.factory.primitive.ByteBags;
import org.eclipse.collections.impl.factory.primitive.ByteLists;
import org.eclipse.collections.impl.factory.primitive.CharBags;
import org.eclipse.collections.impl.factory.primitive.CharLists;
import org.eclipse.collections.impl.factory.primitive.DoubleBags;
import org.eclipse.collections.impl.factory.primitive.DoubleSets;
import org.eclipse.collections.impl.factory.primitive.FloatBags;
import org.eclipse.collections.impl.factory.primitive.FloatLists;
import org.eclipse.collections.impl.factory.primitive.IntBags;
import org.eclipse.collections.impl.factory.primitive.IntLists;
import org.eclipse.collections.impl.factory.primitive.LongBags;
import org.eclipse.collections.impl.factory.primitive.LongLists;
import org.eclipse.collections.impl.factory.primitive.ShortBags;
import org.eclipse.collections.impl.factory.primitive.ShortLists;
import org.eclipse.collections.impl.list.Interval;
import org.eclipse.collections.impl.list.mutable.primitive.BooleanArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.ByteArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.CharArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.DoubleArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.FloatArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.IntArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.LongArrayList;
import org.eclipse.collections.impl.list.mutable.primitive.ShortArrayList;
import org.eclipse.collections.impl.list.primitive.IntInterval;
import org.eclipse.collections.impl.map.mutable.UnifiedMap;
import org.eclipse.collections.impl.map.sorted.mutable.TreeSortedMap;
import org.eclipse.collections.impl.set.mutable.UnifiedSet;
import org.eclipse.collections.impl.set.mutable.primitive.BooleanHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.ByteHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.CharHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.DoubleHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.FloatHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.IntHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.LongHashSet;
import org.eclipse.collections.impl.set.mutable.primitive.ShortHashSet;
import org.eclipse.collections.impl.set.sorted.mutable.TreeSortedSet;
import org.eclipse.collections.impl.test.Verify;
import org.eclipse.collections.impl.tuple.Tuples;
import org.eclipse.collections.impl.tuple.primitive.PrimitiveTuples;
import org.junit.Assert;
import org.junit.Test;

public abstract class AbstractRichIterableTestCase
{
    protected abstract <T> RichIterable<T> newWith(T... littleElements);

    @Test
    public void testNewCollection()
    {
        RichIterable<Object> collection = this.newWith();
        Verify.assertIterableEmpty(collection);
        Verify.assertIterableSize(0, collection);
    }

    @Test
    public void equalsAndHashCode()
    {
        Verify.assertEqualsAndHashCode(this.newWith(1, 2, 3), this.newWith(1, 2, 3));
        Assert.assertNotEquals(this.newWith(1, 2, 3), this.newWith(1, 2));
    }

    @Test
    public void contains()
    {
        RichIterable<Integer> collection = this.newWith(1, 2, 3, 4);
        Assert.assertTrue(collection.contains(1));
        Assert.assertTrue(collection.contains(4));
        Assert.assertFalse(collection.contains(5));
    }

    @Test
    public void containsBy()
    {
        MutableList<Pair<Integer, String>> list =
                Lists.mutable.with(
                        Tuples.pair(1, "1"),
                        Tuples.pair(2, "2"),
                        Tuples.pair(3, null));

        Assert.assertTrue(
                list.containsBy(Pair::getTwo, "2"));
        Assert.assertFalse(
                list.containsBy(Pair::getTwo, "3"));
        Assert.assertTrue(
                list.containsBy(Pair::getTwo, null));
        Assert.assertFalse(
                list.containsBy(Pair::getOne, null));
        Assert.assertFalse(
                list.newEmpty().containsBy(Pair::getOne, null));
        Assert.assertFalse(
                list.newEmpty().containsBy(Pair::getOne, "2"));
        Assert.assertThrows(NullPointerException.class, () ->
                list.newEmpty().containsBy(null, "2"));
    }

    @Test
    public void containsAllIterable()
    {
        RichIterable<Integer> collection = this.newWith(1, 2, 3, 4);
        Assert.assertTrue(collection.containsAllIterable(Lists.mutable.with(1, 2)));
        Assert.assertTrue(collection.containsAllIterable(Lists.mutable.with(1, 1, 2, 2, 2, 3)));
        Assert.assertFalse(collection.containsAllIterable(Lists.mutable.with(1, 5)));
        Assert.assertTrue(Interval.oneTo(100).containsAllIterable(collection));
        Assert.assertFalse(Interval.fromTo(2, 100).containsAllIterable(collection));
        Assert.assertTrue(this.newWith(Interval.oneTo(100).toArray()).containsAllIterable(Interval.oneTo(50)));
        Assert.assertFalse(this.newWith(Interval.fromTo(5, 100).toArray()).containsAllIterable(Interval.fromTo(200, 250)));
    }

    @Test
    public void containsAnyIterable()
    {
        RichIterable<Integer> collection = this.newWith(1, 2, 3, 4);
        Assert.assertTrue(collection.containsAnyIterable(Lists.mutable.with(0, 1)));
        Assert.assertTrue(collection.containsAnyIterable(Arrays.asList(0, 1)));
        Assert.assertFalse(collection.containsAnyIterable(Lists.mutable.with(5, 6)));
        Assert.assertFalse(collection.containsAnyIterable(Arrays.asList(5, 6)));
        Assert.assertTrue(collection.containsAnyIterable(Interval.oneTo(100)));
        Assert.assertFalse(collection.containsAnyIterable(Interval.fromTo(5, 100)));
        Assert.assertTrue(Interval.oneTo(100).containsAnyIterable(collection));
        Assert.assertFalse(Interval.fromTo(5, 100).containsAnyIterable(collection));
        Assert.assertTrue(this.newWith(Interval.oneTo(100).toArray()).containsAnyIterable(Interval.oneTo(50)));
        Assert.assertFalse(this.newWith(Interval.fromTo(5, 100).toArray()).containsAnyIterable(Interval.fromTo(200, 250)));
    }

    @Test
    public void containsNoneIterable()
    {
        RichIterable<Integer> collection = this.newWith(1, 2, 3, 4);
        Assert.assertTrue(collection.containsNoneIterable(Lists.mutable.with(0, 5, 6, 7)));
        Assert.assertTrue(collection.containsNoneIterable(Arrays.asList(0, 5, 6, 7)));
        Assert.assertFalse(collection.containsNoneIterable(Lists.mutable.with(0, 1, 5, 6)));
        Assert.assertFalse(collection.containsNoneIterable(Arrays.asList(0, 1, 5, 6)));
        Assert.assertFalse(collection.containsNoneIterable(Interval.oneTo(100)));
        Assert.assertTrue(collection.containsNoneIterable(Interval.fromTo(5, 100)));
        Assert.assertFalse(Interval.oneTo(100).containsNoneIterable(collection));
        Assert.assertTrue(Interval.fromTo(5, 100).containsNoneIterable(collection));
        Assert.assertFalse(this.newWith(Interval.oneTo(100).toArray()).containsNoneIterable(Interval.oneTo(50)));
        Assert.assertTrue(this.newWith(Interval.fromTo(5, 100).toArray()).containsNoneIterable(Interval.fromTo(200, 250)));
    }

    @Test
    public void containsAllArray()
    {
        RichIterable<Integer> collection = this.newWith(1, 2, 3, 4);
        Assert.assertTrue(collection.containsAllArguments(1, 2));
        Assert.assertFalse(collection.containsAllArguments(1, 5));
    }

    @Test
    public void containsAnyCollection()
    {
        RichIterable<Integer> collection = this.newWith(1, 2, 3, 4);
        Assert.assertTrue(collection.containsAny(Lists.mutable.with(0, 1)));
        Assert.assertTrue(collection.containsAny(Arrays.asList(0, 1)));
        Assert.assertFalse(collection.containsAny(Lists.mutable.with(5, 6)));
        Assert.assertFalse(collection.containsAny(Arrays.asList(5, 6)));
        Assert.assertTrue(collection.containsAny(Interval.oneTo(100)));
        Assert.assertFalse(collection.containsAny(Interval.fromTo(5, 100)));
        Assert.assertTrue(this.newWith(Interval.oneTo(100).toArray()).containsAny(Interval.oneTo(50)));
        Assert.assertFalse(this.newWith(Interval.fromTo(5, 100).toArray()).containsAny(Interval.fromTo(200, 250)));
    }

    @Test
    public void containsNoneCollection()
    {
        RichIterable<Integer> collection = this.newWith(1, 2, 3, 4);
        Assert.assertTrue(collection.containsNone(Lists.mutable.with(0, 5, 6, 7)));
        Assert.assertTrue(collection.containsNone(Arrays.asList(0, 5, 6, 7)));
        Assert.assertFalse(collection.containsNone(Lists.mutable.with(0, 1, 5, 6)));
        Assert.assertFalse(collection.containsNone(Arrays.asList(0, 1, 5, 6)));
        Assert.assertFalse(collection.containsNone(Interval.oneTo(100)));
        Assert.assertTrue(collection.containsNone(Interval.fromTo(5, 100)));
        Assert.assertFalse(this.newWith(Interval.oneTo(100).toArray()).containsNone(Interval.oneTo(50)));
        Assert.assertTrue(this.newWith(Interval.fromTo(5, 100).toArray()).containsNone(Interval.fromTo(200, 250)));
    }

    @Test
    public void containsAllCollection()
    {
        RichIterable<Integer> collection = this.newWith(1, 2, 3, 4);
        Assert.assertTrue(collection.containsAll(Lists.mutable.with(1, 2)));
        Assert.assertFalse(collection.containsAll(Lists.mutable.with(1, 5)));
        Assert.assertFalse(collection.containsAll(Arrays.asList(1, 2, 5)));
        Assert.assertTrue(collection.containsAll(Interval.oneTo(4)));
        Assert.assertFalse(collection.containsAll(Interval.fromTo(5, 100)));
        Assert.assertTrue(this.newWith(Interval.oneTo(100).toArray()).containsAll(Interval.oneTo(50)));
        Assert.assertFalse(this.newWith(Interval.fromTo(5, 100).toArray()).containsAll(Interval.fromTo(50, 150)));
    }

    @Test
    public void tap()
    {
        MutableList<Integer> tapResult = Lists.mutable.of();
        RichIterable<Integer> collection = this.newWith(1, 2, 3, 4);
        Assert.assertSame(collection, collection.tap(tapResult::add));
        Assert.assertEquals(collection.toList(), tapResult);
    }

    private void forEach(Function<Collection<Integer>, Consumer<Integer>> adderProvider)
    {
        MutableList<Integer> result = Lists.mutable.of();
        RichIterable<Integer> template = this.newWith(1, 2, 3, 4);
        Consumer<Integer> adder = adderProvider.apply(result);
        if (adder instanceof Procedure<?>)
        {
            template.forEach((Procedure<Integer>) adder);
        }
        else
        {
            template.forEach(adder);
        }
        Verify.assertSize(4, result);
        Verify.assertContainsAll(result, 1, 2, 3, 4);
    }

    private void forEachProcedure()
    {
        this.forEach(CollectionAddProcedure::on);
    }

    private void forEachConsumer()
    {
        this.forEach(collection -> collection::add);
    }

    @Test
    public void forEach()
    {
        this.forEachProcedure();
        this.forEachConsumer();
    }

    @Test
    public void forEachWith()
    {
        MutableList<Integer> result = Lists.mutable.of();
        RichIterable<Integer> collection = this.newWith(1, 2, 3, 4);
        collection.forEachWith((argument1, argument2) -> result.add(argument1 + argument2), 0);
        Verify.assertSize(4, result);
        Verify.assertContainsAll(result, 1, 2, 3, 4);
    }

    @Test
    public void forEachWithIndex()
    {
        MutableBag<Integer> elements = Bags.mutable.of();
        MutableBag<Integer> indexes = Bags.mutable.of();
        RichIterable<Integer> collection = this.newWith(1, 2, 3, 4);
        collection.forEachWithIndex((object, index) -> {
            elements.add(object);
            indexes.add(index);
        });
        Assert.assertEquals(Bags.mutable.of(1, 2, 3, 4), elements);
        Assert.assertEquals(Bags.mutable.of(0, 1, 2, 3), indexes);
    }

    @Test
    public void select()
    {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4, 5).select(Predicates.lessThan(3)), 1, 2);
        RichIterable<Integer> result = this.newWith(-1, 2, 3, 4, 5).select(Predicates.lessThan(3));
        Verify.assertNotContains(3, result);
        Verify.assertNotContains(4, result);
        Verify.assertNotContains(5, result);
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4, 5).select(Predicates.lessThan(3), UnifiedSet.newSet()), 1, 2);
    }

    @Test
    public void selectWith()
    {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4, 5).selectWith(Predicates2.lessThan(), 3), 1, 2);
        RichIterable<Integer> result = this.newWith(-1, 2, 3, 4, 5).selectWith(Predicates2.lessThan(), 3);
        Verify.assertNotContains(3, result);
        Verify.assertNotContains(4, result);
        Verify.assertNotContains(5, result);
        Verify.assertContainsAll(
                this.newWith(1, 2, 3, 4, 5).selectWith(
                        Predicates2.lessThan(),
                        3),
                1, 2);
    }

    @Test
    public void selectWith_target()
    {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4, 5).selectWith(Predicates2.lessThan(), 3, HashBag.newBag()), 1, 2);
        Verify.denyContainsAny(this.newWith(-1, 2, 3, 4, 5).selectWith(Predicates2.lessThan(), 3, HashBag.newBag()), 3, 4, 5);
        Verify.assertContainsAll(
                this.newWith(1, 2, 3, 4, 5).selectWith(Predicates2.lessThan(), 3, HashBag.newBag()),
                1, 2);
    }

    @Test
    public void reject()
    {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4).reject(Predicates.lessThan(3)), 3, 4);
        Verify.assertContainsAll(
                this.newWith(1, 2, 3, 4).reject(Predicates.lessThan(3), UnifiedSet.newSet()), 3, 4);
    }

    @Test
    public void rejectWith()
    {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4).rejectWith(Predicates2.lessThan(), 3), 3, 4);
        Verify.assertContainsAll(
                this.newWith(1, 2, 3, 4).rejectWith(Predicates2.lessThan(), 3, UnifiedSet.newSet()),
                3, 4);
    }

    @Test
    public void rejectWith_target()
    {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4).rejectWith(Predicates2.lessThan(), 3, HashBag.newBag()), 3, 4);
        Verify.assertContainsAll(
                this.newWith(1, 2, 3, 4).rejectWith(Predicates2.lessThan(), 3, UnifiedSet.newSet()),
                3, 4);
    }

    @Test
    public void selectInstancesOf()
    {
        RichIterable<Number> numbers = this.newWith(1, 2.0, 3, 4.0, 5);
        Assert.assertEquals(HashBag.newBagWith(1, 3, 5), numbers.selectInstancesOf(Integer.class).toBag());
        Assert.assertEquals(HashBag.newBagWith(1, 2.0, 3, 4.0, 5), numbers.selectInstancesOf(Number.class).toBag());
    }

    @Test
    public void collect()
    {
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4).collect(String::valueOf), "1", "2", "3", "4");
        Verify.assertContainsAll(this.newWith(1, 2, 3, 4).collect(String::valueOf, UnifiedSet.newSet()), "1", "2", "3", "4");
    }

    @Test
    public void collectTarget()
    {
        Assert.assertEquals(
                Bags.mutable.of(2, 3, 4),
                this.newWith(1, 2, 3).collect(each -> each + 1, Lists.mutable.empty()).toBag());
        Assert.assertEquals(
                Bags.mutable.of(2, 3, 4),
                Bags.mutable.withAll(this.newWith(1, 2, 3).collect(each -> each + 1, new ArrayList<>())));
        Assert.assertEquals(
                Bags.mutable.of(2, 3, 4),
                Bags.mutable.withAll(this.newWith(1, 2, 3).collect(each -> each + 1, new CopyOnWriteArrayList<>())));
        Assert.assertEquals(
                Bags.mutable.of(2, 3, 4),
                this.newWith(1, 2, 3).collect(each -> each + 1, Bags.mutable.empty()));
        Assert.assertEquals(
                Sets.mutable.of(2, 3, 4),
                this.newWith(1, 2, 3).collect(each -> each + 1, Sets.mutable.empty()));
        Assert.assertEquals(
                Sets.mutable.of(2, 3, 4),
                this.newWith(1, 2, 3).collect(each -> each + 1, new HashSet<>()));
        Assert.assertEquals(
                Sets.mutable.of(2, 3, 4),
                this.newWith(1, 2, 3).collect(each -> each + 1, new CopyOnWriteArraySet<>()));
    }

    @Test
    public void collectBoolean()
    {
        BooleanIterable result = this.newWith(1, 0).collectBoolean(PrimitiveFunctions.integerIsPositive());
        Assert.assertEquals(BooleanBags.mutable.of(true, false), result.toBag());
        Assert.assertEquals(BooleanBags.mutable.of(true, false), BooleanBags.mutable.ofAll(result));
    }

    @Test
    public void collectBooleanWithTarget()
    {
        MutableBooleanCollection target = new BooleanArrayList();
        BooleanIterable result = this.newWith(1, 0).collectBoolean(PrimitiveFunctions.integerIsPositive(), target);
        Assert.assertSame("Target list sent as parameter not returned", target, result);
        Assert.assertEquals(BooleanBags.mutable.of(true, false), result.toBag());
    }

    @Test
    public void collectBooleanWithBagTarget()
    {
        BooleanHashBag target = new BooleanHashBag();
        BooleanHashBag result = this.newWith(1, 0).collectBoolean(PrimitiveFunctions.integerIsPositive(), target);
        Assert.assertSame("Target list sent as parameter not returned", target, result);
        Assert.assertEquals(BooleanHashBag.newBagWith(true, false), result);
    }

    @Test
    public void collectByte()
    {
        ByteIterable result = this.newWith(1, 2, 3, 4).collectByte(PrimitiveFunctions.unboxIntegerToByte());
        Assert.assertEquals(ByteBags.mutable.of((byte) 1, (byte) 2, (byte) 3, (byte) 4), result.toBag());
        Assert.assertEquals(ByteBags.mutable.of((byte) 1, (byte) 2, (byte) 3, (byte) 4), ByteBags.mutable.ofAll(result));
    }

    @Test
    public void collectByteWithTarget()
    {
        MutableByteCollection target = new ByteArrayList();
        ByteIterable result = this.newWith(1, 2, 3, 4).collectByte(PrimitiveFunctions.unboxIntegerToByte(), target);
        Assert.assertSame("Target list sent as parameter not returned", target, result);
        Assert.assertEquals(ByteHashBag.newBagWith((byte) 1, (byte) 2, (byte) 3, (byte) 4), result.toBag());
    }

    @Test
    public void collectByteWithBagTarget()
    {
        ByteHashBag target = new ByteHashBag();
        ByteHashBag result = this.newWith(1, 2, 3, 4).collectByte(PrimitiveFunctions.unboxIntegerToByte(), target);
        Assert.assertSame("Target list sent as parameter not returned", target, result);
        Assert.assertEquals(ByteHashBag.newBagWith((byte) 1, (byte) 2, (byte) 3, (byte) 4), result);
    }

    @Test
    public void collectChar()
    {
        CharIterable result = this.newWith(1, 2, 3, 4).collectChar(PrimitiveFunctions.unboxIntegerToChar());
        Assert.assertEquals(CharBags.mutable.of((char) 1, (char) 2, (char) 3, (char) 4), result.toBag());
        Assert.assertEquals(CharBags.mutable.of((char) 1, (char) 2, (char) 3, (char) 4), CharBags.mutable.ofAll(result));
    }

    @Test
    public void collectCharWithTarget()
    {
        MutableCharCollection target = new CharArrayList();
        CharIterable result = this.newWith(1, 2, 3, 4).collectChar(PrimitiveFunctions.unboxIntegerToChar(), target);
        Assert.assertSame("Target list sent as parameter not returned", target, result);
        Assert.assertEquals(CharHashBag.newBagWith((char) 1, (char) 2, (char) 3, (char) 4), result.toBag());
    }

    @Test
    public void collectCharWithBagTarget()
    {
        CharHashBag target = new CharHashBag();
        CharHashBag result = this.newWith(1, 2, 3, 4).collectChar(PrimitiveFunctions.unboxIntegerToChar(), target);
        Assert.assertSame("Target list sent as parameter not returned", target, result);
        Assert.assertEquals(CharHashBag.newBagWith((char) 1, (char) 2, (char) 3, (char) 4), result);
    }

    @Test
    public void collectDouble()
    {
        DoubleIterable result = this.newWith(1, 2, 3, 4).collectDouble(PrimitiveFunctions.unboxIntegerToDouble());
        Assert.assertEquals(DoubleBags.mutable.of(1.0d, 2.0d, 3.0d, 4.0d), result.toBag());
        Assert.assertEquals(DoubleBags.mutable.of(1.0d, 2.0d, 3.0d, 4.0d), DoubleBags.mutable.ofAll(result));
    }

    @Test
    public void collectDoubleWithTarget()
    {
        MutableDoubleCollection target = new DoubleArrayList();
        DoubleIterable result = this.newWith(1, 2, 3, 4).collectDouble(PrimitiveFunctions.unboxIntegerToDouble(), target);
        Assert.assertSame("Target list sent as parameter not returned", target, result);
        Assert.assertEquals(DoubleHashBag.newBagWith(1.0d, 2.0d, 3.0d, 4.0d), result.toBag());
    }

    @Test
    public void collectDoubleWithBagTarget()
    {
        DoubleHashBag target = new DoubleHashBag();
        DoubleHashBag result = this.newWith(1, 2, 3, 4).collectDouble(PrimitiveFunctions.unboxIntegerToDouble(), target);
        Assert.assertSame("Target list sent as parameter not returned", target, result);
        Assert.assertEquals(DoubleHashBag.newBagWith(1.0d, 2.0d, 3.0d, 4.0d), result);
    }

    @Test
    public void collectFloat()
    {
        FloatIterable result = this.newWith(1, 2, 3, 4).collectFloat(PrimitiveFunctions.unboxIntegerToFloat());
        Assert.assertEquals(FloatBags.mutable.of(1.0f, 2.0f, 3.0f, 4.0f), result.toBag());
        Assert.assertEquals(FloatBags.mutable.of(1.0f, 2.0f, 3.0f, 4.0f), FloatBags.mutable.ofAll(result));
    }

    @Test
    public void collectFloatWithTarget()
    {
        MutableFloatCollection target = new FloatArrayList();
        FloatIterable result = this.newWith(1, 2, 3, 4).collectFloat(PrimitiveFunctions.unboxIntegerToFloat(), target);
        Assert.assertSame("Target list sent as parameter not returned", target, result);
        Assert.assertEquals(FloatHashBag.newBagWith(1.0f, 2.0f, 3.0f, 4.0f), result.toBag());
    }

    @Test
    public void collectFloatWithBagTarget()
    {
        FloatHashBag target = new FloatHashBag();
        FloatHashBag result = this.newWith(1, 2, 3, 4).collectFloat(PrimitiveFunctions.unboxIntegerToFloat(), target);
        Assert.assertSame("Target list sent as parameter not returned", target, result);
        Assert.assertEquals(FloatHashBag.newBagWith(1.0f, 2.0f, 3.0f, 4.0f), result);
    }

    @Test
    public void collectInt()
    {
        IntIterable result = this.newWith(1, 2, 3, 4).collectInt(PrimitiveFunctions.unboxIntegerToInt());
        Assert.assertEquals(IntBags.mutable.of(1, 2, 3, 4), result.toBag());
        Assert.assertEquals(IntBags.mutable.of(1, 2, 3, 4), IntBags.mutable.ofAll(result));
    }

    @Test
    public void collectIntWithTarget()
    {
        MutableIntCollection target = new IntArrayList();
        IntIterable result = this.newWith(1, 2, 3, 4).collectInt(PrimitiveFunctions.unboxIntegerToInt(), target);
        Assert.assertSame("Target list sent as parameter not returned", target, result);
        Assert.assertEquals(IntHashBag.newBagWith(1, 2, 3, 4), result.toBag());
    }

    @Test
    public void collectIntWithBagTarget()
    {
        IntHashBag target = new IntHashBag();
        IntHashBag result = this.newWith(1, 2, 3, 4).collectInt(PrimitiveFunctions.unboxIntegerToInt(), target);
        Assert.assertSame("Target list sent as parameter not returned", target, result);
        Assert.assertEquals(IntHashBag.newBagWith(1, 2, 3, 4), result);
    }

    @Test
    public void collectLong()
    {
        LongIterable result = this.newWith(1, 2, 3, 4).collectLong(PrimitiveFunctions.unboxIntegerToLong());
        Assert.assertEquals(LongBags.mutable.of(1, 2, 3, 4), result.toBag());
        Assert.assertEquals(LongBags.mutable.of(1, 2, 3, 4), LongBags.mutable.ofAll(result));
    }

    @Test
    public void collectLongWithTarget()
    {
        MutableLongCollection target = new LongArrayList();
        LongIterable result = this.newWith(1, 2, 3, 4).collectLong(PrimitiveFunctions.unboxIntegerToLong(), target);
        Assert.assertSame("Target list sent as parameter not returned", target, result);
        Assert.assertEquals(LongHashBag.newBagWith(1, 2, 3, 4), result.toBag());
    }

    @Test
    public void collectLongWithBagTarget()
    {
        LongHashBag target = new LongHashBag();
        LongHashBag result = this.newWith(1, 2, 3, 4).collectLong(PrimitiveFunctions.unboxIntegerToLong(), target);
        Assert.assertSame("Target list sent as parameter not returned", target, result);
        Assert.assertEquals(LongHashBag.newBagWith(1, 2, 3, 4), result);
    }

    @Test
    public void collectShort()
    {
        ShortIterable result = this.newWith(1, 2, 3, 4).collectShort(PrimitiveFunctions.unboxIntegerToShort());
        Assert.assertEquals(ShortBags.mutable.of((short) 1, (short) 2, (short) 3, (short) 4), result.toBag());
        Assert.assertEquals(ShortBags.mutable.of((short) 1, (short) 2, (short) 3, (short) 4), ShortBags.mutable.ofAll(result));
    }

    @Test
    public void collectShortWithTarget()
    {
        MutableShortCollection target = new ShortArrayList();
        ShortIterable result = this.newWith(1, 2, 3, 4).collectShort(PrimitiveFunctions.unboxIntegerToShort(), target);
        Assert.assertSame("Target list sent as parameter not returned", target, result);
        Assert.assertEquals(ShortHashBag.newBagWith((short) 1, (short) 2, (short) 3, (short) 4), result.toBag());
    }

    @Test
    public void collectShortWithBagTarget()
    {
        ShortHashBag target = new ShortHashBag();
        ShortHashBag result = this.newWith(1, 2, 3, 4).collectShort(PrimitiveFunctions.unboxIntegerToShort(), target);
        Assert.assertSame("Target list sent as parameter not returned", target, result);
        Assert.assertEquals(ShortHashBag.newBagWith((short) 1, (short) 2, (short) 3, (short) 4), result);
    }

    @Test
    public void flatCollect()
    {
        RichIterable<Integer> collection = this.newWith(1, 2, 3, 4);
        Function<Integer, MutableList<String>> function = object -> Lists.mutable.with(String.valueOf(object));

        Verify.assertListsEqual(
                Lists.mutable.with("1", "2", "3", "4"),
                collection.flatCollect(function).toSortedList());

        Verify.assertSetsEqual(
                UnifiedSet.newSetWith("1", "2", "3", "4"),
                collection.flatCollect(function, UnifiedSet.newSet()));
    }

    @Test
    public void flatCollectWith()
    {
        RichIterable<Integer> collection = this.newWith(4, 5, 6, 7);

        Verify.assertSetsEqual(
                Sets.mutable.with(1, 2, 3, 4, 5, 6, 7),
                collection.flatCollectWith(Interval::fromTo, 1).toSet());

        Verify.assertBagsEqual(
                Bags.mutable.with(4, 3, 2, 1, 5, 4, 3, 2, 1, 6, 5, 4, 3, 2, 1, 7, 6, 5, 4, 3, 2, 1),
                collection.flatCollectWith(Interval::fromTo, 1, Bags.mutable.empty()));
    }

    @Test
    public void flatCollectBoolean()
    {
        RichIterable<BooleanBooleanPair> iterable = this.newWith(
                PrimitiveTuples.pair(true, false),
                PrimitiveTuples.pair(false, true));

        Function<BooleanBooleanPair, BooleanIterable> function =
                pair -> BooleanLists.mutable.with(pair.getOne(), pair.getTwo());

        MutableBooleanBag bag = iterable.flatCollectBoolean(function, new BooleanHashBag());
        MutableBooleanSet set = iterable.flatCollectBoolean(function, new BooleanHashSet());

        BooleanBag expected = BooleanBags.mutable.with(true, false, false, true);
        Assert.assertEquals(expected, bag);
        Assert.assertEquals(expected.toSet(), set);
    }

    @Test
    public void flatCollectByte()
    {
        RichIterable<ByteBytePair> iterable =
                this.newWith(
                        PrimitiveTuples.pair((byte) 1, (byte) 2),
                        PrimitiveTuples.pair((byte) 3, (byte) 4));

        Function<ByteBytePair, ByteIterable> function =
                pair -> ByteLists.mutable.with(pair.getOne(), pair.getTwo());

        MutableByteBag bag = iterable.flatCollectByte(function, new ByteHashBag());
        MutableByteSet set = iterable.flatCollectByte(function, new ByteHashSet());

        ByteBag expected = ByteBags.mutable.with((byte) 1, (byte) 2, (byte) 3, (byte) 4);
        Assert.assertEquals(expected, bag);
        Assert.assertEquals(expected.toSet(), set);
    }

    @Test
    public void flatCollectShort()
    {
        RichIterable<ShortShortPair> iterable =
                this.newWith(
                        PrimitiveTuples.pair((short) 1, (short) 2),
                        PrimitiveTuples.pair((short) 3, (short) 4));

        Function<ShortShortPair, ShortIterable> function =
                pair -> ShortLists.mutable.with(pair.getOne(), pair.getTwo());

        MutableShortBag bag = iterable.flatCollectShort(function, new ShortHashBag());
        MutableShortSet set = iterable.flatCollectShort(function, new ShortHashSet());

        ShortBag expected = ShortBags.mutable.with((short) 1, (short) 2, (short) 3, (short) 4);
        Assert.assertEquals(expected, bag);
        Assert.assertEquals(expected.toSet(), set);
    }

    @Test
    public void flatCollectInt()
    {
        RichIterable<IntIntPair> iterable =
                this.newWith(
                        PrimitiveTuples.pair(1, 2),
                        PrimitiveTuples.pair(3, 4));

        Function<IntIntPair, IntIterable> function =
                pair -> IntLists.mutable.with(pair.getOne(), pair.getTwo());

        MutableIntBag bag = iterable.flatCollectInt(function, new IntHashBag());
        MutableIntSet set = iterable.flatCollectInt(function, new IntHashSet());

        IntBag expected = IntBags.mutable.with(1, 2, 3, 4);
        Assert.assertEquals(expected, bag);
        Assert.assertEquals(expected.toSet(), set);
    }

    @Test
    public void flatCollectChar()
    {
        RichIterable<CharCharPair> iterable =
                this.newWith(
                        PrimitiveTuples.pair('a', 'b'),
                        PrimitiveTuples.pair('c', 'd'));

        Function<CharCharPair, CharIterable> function =
                pair -> CharLists.mutable.with(pair.getOne(), pair.getTwo());

        MutableCharBag bag = iterable.flatCollectChar(function, new CharHashBag());
        MutableCharSet set = iterable.flatCollectChar(function, new CharHashSet());

        CharBag expected = CharBags.mutable.with('a', 'b', 'c', 'd');
        Assert.assertEquals(expected, bag);
        Assert.assertEquals(expected.toSet(), set);
    }

    @Test
    public void flatCollectLong()
    {
        RichIterable<LongLongPair> iterable =
                this.newWith(
                        PrimitiveTuples.pair(1L, 2L),
                        PrimitiveTuples.pair(3L, 4L));

        Function<LongLongPair, LongIterable> function =
                pair -> LongLists.mutable.with(pair.getOne(), pair.getTwo());

        MutableLongBag bag = iterable.flatCollectLong(function, new LongHashBag());
        MutableLongSet set = iterable.flatCollectLong(function, new LongHashSet());

        LongBag expected = LongBags.mutable.with(1L, 2L, 3L, 4L);
        Assert.assertEquals(expected, bag);
        Assert.assertEquals(expected.toSet(), set);
    }

    @Test
    public void flatCollectDouble()
    {
        RichIterable<DoubleDoublePair> iterable =
                this.newWith(
                        PrimitiveTuples.pair(1.0, 2.0),
                        PrimitiveTuples.pair(3.0, 4.0));

        Function<DoubleDoublePair, DoubleIterable> function =
                pair -> DoubleSets.mutable.with(pair.getOne(), pair.getTwo());

        MutableDoubleBag bag = iterable.flatCollectDouble(function, new DoubleHashBag());
        MutableDoubleSet set = iterable.flatCollectDouble(function, new DoubleHashSet());

        DoubleBag expected = DoubleBags.mutable.with(1.0, 2.0, 3.0, 4.0);
        Assert.assertEquals(expected, bag);
        Assert.assertEquals(expected.toSet(), set);
    }

    @Test
    public void flatCollectFloat()
    {
        RichIterable<FloatFloatPair> iterable =
                this.newWith(
                        PrimitiveTuples.pair((float) 1, (float) 2),
                        PrimitiveTuples.pair((float) 3, (float) 4));

        Function<FloatFloatPair, FloatIterable> function =
                pair -> FloatLists.mutable.with(pair.getOne(), pair.getTwo());

        MutableFloatBag bag = iterable.flatCollectFloat(function, new FloatHashBag());
        MutableFloatSet set = iterable.flatCollectFloat(function, new FloatHashSet());

        FloatBag expected = FloatBags.mutable.with(1, 2, 3, 4);
        Assert.assertEquals(expected, bag);
        Assert.assertEquals(expected.toSet(), set);
    }

    @Test
    public void detect()
    {
        Assert.assertEquals(Integer.valueOf(3), this.newWith(1, 2, 3, 4, 5).detect(Integer.valueOf(3)::equals));
        Assert.assertNull(this.newWith(1, 2, 3, 4, 5).detect(Integer.valueOf(6)::equals));
    }

    @Test
    public void detectOptional()
    {
        Assert.assertEquals(Integer.valueOf(3), this.newWith(1, 2, 3, 4, 5).detectOptional(Integer.valueOf(3)::equals).get());
        Assert.assertNotNull(this.newWith(1, 2, 3, 4, 5).detectOptional(Integer.valueOf(6)::equals));
        Assert.assertThrows(NoSuchElementException.class, () -> this.newWith(1, 2, 3, 4, 5).detectOptional(Integer.valueOf(6)::equals).get());
    }

    @Test(expected = NoSuchElementException.class)
    public void min_empty_throws()
    {
        this.<Integer>newWith().min(Integer::compareTo);
    }

    @Test(expected = NoSuchElementException.class)
    public void max_empty_throws()
    {
        this.<Integer>newWith().max(Integer::compareTo);
    }

    @Test(expected = NullPointerException.class)
    public void min_null_throws()
    {
        this.newWith(1, null, 2).min(Integer::compareTo);
    }

    @Test(expected = NullPointerException.class)
    public void max_null_throws()
    {
        this.newWith(1, null, 2).max(Integer::compareTo);
    }

    @Test
    public void min()
    {
        Assert.assertEquals(Integer.valueOf(1), this.newWith(1, 3, 2).min(Integer::compareTo));
    }

    @Test
    public void minOptional()
    {
        Assert.assertEquals(
                Integer.valueOf(1),
                this.newWith(1, 3, 2).minOptional().get());
        Assert.assertEquals(
                Integer.valueOf(1),
                this.newWith(1, 3, 2).minOptional(Integer::compareTo).get());
        Assert.assertFalse(
                this.<Integer>newWith().minOptional().isPresent());
        Assert.assertFalse(
                this.<Integer>newWith().minOptional(Integer::compareTo).isPresent());
    }

    @Test
    public void max()
    {
        Assert.assertEquals(Integer.valueOf(3), this.newWith(1, 3, 2).max(Integer::compareTo));
    }

    @Test
    public void maxOptional()
    {
        Assert.assertEquals(
                Integer.valueOf(3),
                this.newWith(1, 3, 2).maxOptional().get());
        Assert.assertEquals(
                Integer.valueOf(3),
                this.newWith(1, 3, 2).maxOptional(Integer::compareTo).get());
        Assert.assertFalse(
                this.<Integer>newWith().maxOptional().isPresent());
        Assert.assertFalse(
                this.<Integer>newWith().maxOptional(Integer::compareTo).isPresent());
    }

    @Test(expected = NullPointerException.class)
    public void min_null_throws_without_comparator()
    {
        this.newWith(1, null, 2).min();
    }

    @Test(expected = NullPointerException.class)
    public void max_null_throws_without_comparator()
    {
        this.newWith(1, null, 2).max();
    }

    @Test
    public void min_without_comparator()
    {
        Assert.assertEquals(Integer.valueOf(1), this.newWith(3, 1, 2).min());
    }

    @Test
    public void max_without_comparator()
    {
        Assert.assertEquals(Integer.valueOf(3), this.newWith(1, 3, 2).max());
    }

    @Test
    public void min_null_safe()
    {
        RichIterable<Integer> integers = this.newWith(1, 3, 2, null);
        Assert.assertEquals(Integer.valueOf(1), integers.min(Comparators.safeNullsHigh(Integer::compareTo)));
        Assert.assertNull(integers.min(Comparators.safeNullsLow(Integer::compareTo)));
    }

    @Test
    public void max_null_safe()
    {
        RichIterable<Integer> integers = this.newWith(1, 3, 2, null);
        Assert.assertEquals(Integer.valueOf(3), integers.max(Comparators.safeNullsLow(Integer::compareTo)));
        Assert.assertNull(integers.max(Comparators.safeNullsHigh(Integer::compareTo)));
    }

    @Test
    public void minBy()
    {
        Assert.assertEquals(Integer.valueOf(1), this.newWith(1, 3, 2).minBy(String::valueOf));
    }

    @Test
    public void minByOptional()
    {
        Assert.assertEquals(Integer.valueOf(1), this.newWith(1, 3, 2).minByOptional(String::valueOf).get());
        Assert.assertFalse(this.newWith().minByOptional(String::valueOf).isPresent());
    }

    @Test
    public void maxBy()
    {
        Assert.assertEquals(Integer.valueOf(3), this.newWith(1, 3, 2).maxBy(String::valueOf));
    }

    @Test
    public void maxByOptional()
    {
        Assert.assertEquals(Integer.valueOf(3), this.newWith(1, 3, 2).maxByOptional(String::valueOf).get());
        Assert.assertFalse(this.newWith().maxByOptional(String::valueOf).isPresent());
    }

    @Test(expected = NullPointerException.class)
    public void minBy_null_throws()
    {
        this.newWith(1, null, 2).minBy(Integer::valueOf);
    }

    @Test(expected = NullPointerException.class)
    public void maxBy_null_throws()
    {
        this.newWith(1, null, 2).maxBy(Integer::valueOf);
    }

    @Test
    public void detectWith()
    {
        Assert.assertEquals(Integer.valueOf(3), this.newWith(1, 2, 3, 4, 5).detectWith(Object::equals, 3));
        Assert.assertNull(this.newWith(1, 2, 3, 4, 5).detectWith(Object::equals, 6));
    }

    @Test
    public void detectWithOptional()
    {
        Assert.assertEquals(Integer.valueOf(3), this.newWith(1, 2, 3, 4, 5).detectWithOptional(Object::equals, 3).get());
        Assert.assertNotNull(this.newWith(1, 2, 3, 4, 5).detectWithOptional(Object::equals, 6));
        Assert.assertThrows(NoSuchElementException.class, () -> this.newWith(1, 2, 3, 4, 5).detectWithOptional(Object::equals, 6).get());
    }

    @Test
    public void detectIfNone()
    {
        Assert.assertEquals(Integer.valueOf(3), this.newWith(1, 2, 3, 4, 5).detectIfNone(Integer.valueOf(3)::equals, () -> 6));
        Assert.assertEquals(Integer.valueOf(6), this.newWith(1, 2, 3, 4, 5).detectIfNone(Integer.valueOf(6)::equals, () -> 6));
    }

    @Test
    public void detectWithIfNoneBlock()
    {
        Function0<Integer> function = new PassThruFunction0<>(-42);
        Assert.assertEquals(
                Integer.valueOf(5),
                this.newWith(1, 2, 3, 4, 5).detectWithIfNone(
                        Predicates2.greaterThan(),
                        4,
                        function));
        Assert.assertEquals(
                Integer.valueOf(-42),
                this.newWith(1, 2, 3, 4, 5).detectWithIfNone(
                        Predicates2.lessThan(),
                        0,
                        function));
    }

    @Test
    public void allSatisfy()
    {
        Assert.assertTrue(this.newWith(1, 2, 3).allSatisfy(Integer.class::isInstance));
        Assert.assertFalse(this.newWith(1, 2, 3).allSatisfy(Integer.valueOf(1)::equals));
    }

    @Test
    public void allSatisfyWith()
    {
        Assert.assertTrue(this.newWith(1, 2, 3).allSatisfyWith(Predicates2.instanceOf(), Integer.class));
        Assert.assertFalse(this.newWith(1, 2, 3).allSatisfyWith(Object::equals, 1));
    }

    @Test
    public void noneSatisfy()
    {
        Assert.assertTrue(this.newWith(1, 2, 3).noneSatisfy(Boolean.class::isInstance));
        Assert.assertFalse(this.newWith(1, 1, 3).noneSatisfy(Integer.valueOf(1)::equals));
        Assert.assertTrue(this.newWith(1, 2, 3).noneSatisfy(Integer.valueOf(4)::equals));
    }

    @Test
    public void noneSatisfyWith()
    {
        Assert.assertTrue(this.newWith(1, 2, 3).noneSatisfyWith(Predicates2.instanceOf(), Boolean.class));
        Assert.assertFalse(this.newWith(1, 2, 3).noneSatisfyWith(Object::equals, 1));
    }

    @Test
    public void anySatisfy()
    {
        Assert.assertFalse(this.newWith(1, 2, 3).anySatisfy(String.class::isInstance));
        Assert.assertTrue(this.newWith(1, 2, 3).anySatisfy(Integer.class::isInstance));
    }

    @Test
    public void anySatisfyWith()
    {
        Assert.assertFalse(this.newWith(1, 2, 3).anySatisfyWith(Predicates2.instanceOf(), String.class));
        Assert.assertTrue(this.newWith(1, 2, 3).anySatisfyWith(Predicates2.instanceOf(), Integer.class));
    }

    @Test
    public void count()
    {
        Assert.assertEquals(3, this.newWith(1, 2, 3).count(Integer.class::isInstance));
    }

    @Test
    public void countWith()
    {
        Assert.assertEquals(3, this.newWith(1, 2, 3).countWith(Predicates2.instanceOf(), Integer.class));
    }

    @Test
    public void collectIf()
    {
        Verify.assertContainsAll(
                this.newWith(1, 2, 3).collectIf(
                        Integer.class::isInstance,
                        Object::toString),
                "1", "2", "3");
        Verify.assertContainsAll(
                this.newWith(1, 2, 3).collectIf(
                        Integer.class::isInstance,
                        Object::toString,
                        UnifiedSet.newSet()),
                "1", "2", "3");
    }

    @Test
    public void collectWith()
    {
        Assert.assertEquals(
                Bags.mutable.of(2, 3, 4),
                this.newWith(1, 2, 3).collectWith(AddFunction.INTEGER, 1).toBag());
    }

    @Test
    public void collectWith_target()
    {
        Assert.assertEquals(
                Bags.mutable.of(2, 3, 4),
                this.newWith(1, 2, 3).collectWith(AddFunction.INTEGER, 1, Lists.mutable.empty()).toBag());
        Assert.assertEquals(
                Bags.mutable.of(2, 3, 4),
                Bags.mutable.withAll(this.newWith(1, 2, 3).collectWith(AddFunction.INTEGER, 1, new ArrayList<>())));
        Assert.assertEquals(
                Bags.mutable.of(2, 3, 4),
                Bags.mutable.withAll(this.newWith(1, 2, 3).collectWith(AddFunction.INTEGER, 1, new CopyOnWriteArrayList<>())));
        Assert.assertEquals(
                Bags.mutable.of(2, 3, 4),
                this.newWith(1, 2, 3).collectWith(AddFunction.INTEGER, 1, Bags.mutable.empty()));
        Assert.assertEquals(
                Sets.mutable.of(2, 3, 4),
                this.newWith(1, 2, 3).collectWith(AddFunction.INTEGER, 1, Sets.mutable.empty()));
        Assert.assertEquals(
                Sets.mutable.of(2, 3, 4),
                this.newWith(1, 2, 3).collectWith(AddFunction.INTEGER, 1, new HashSet<>()));
        Assert.assertEquals(
                Sets.mutable.of(2, 3, 4),
                this.newWith(1, 2, 3).collectWith(AddFunction.INTEGER, 1, new CopyOnWriteArraySet<>()));
    }

    @Test
    public void getAny()
    {
        RichIterable<Integer> distinctElements = this.newWith(1, 2, 3);
        Assert.assertTrue(distinctElements.contains(distinctElements.getAny()));
        RichIterable<String> duplicateElements = this.newWith("a", "a", "b");
        Assert.assertTrue(duplicateElements.contains(duplicateElements.getAny()));
    }

    @Test
    public void getFirst()
    {
        Assert.assertEquals(Integer.valueOf(1), this.newWith(1, 2, 3).getFirst());
        Assert.assertNotEquals(Integer.valueOf(3), this.newWith(1, 2, 3).getFirst());
    }

    @Test
    public void getLast()
    {
        Assert.assertNotEquals(Integer.valueOf(1), this.newWith(1, 2, 3).getLast());
        Assert.assertEquals(Integer.valueOf(3), this.newWith(1, 2, 3).getLast());
    }

    @Test
    public void getOnly()
    {
        Assert.assertEquals(Integer.valueOf(2), this.newWith(2).getOnly());
        Assert.assertNotEquals(Integer.valueOf(2), this.newWith(1).getOnly());
    }

    @Test(expected = IllegalStateException.class)
    public void getOnly_not_only_one_throws()
    {
        this.newWith(1, 2).getOnly();
    }

    @Test(expected = IllegalStateException.class)
    public void getOnly_empty_throws()
    {
        this.newWith().getOnly();
    }

    @Test
    public void isEmpty()
    {
        Verify.assertIterableEmpty(this.newWith());
        Verify.assertIterableNotEmpty(this.newWith(1, 2));
        Assert.assertTrue(this.newWith(1, 2).notEmpty());
    }

    @Test
    public void iterator()
    {
        RichIterable<Integer> objects = this.newWith(1, 2, 3);
        Iterator<Integer> iterator = objects.iterator();
        for (int i = objects.size(); i-- > 0; )
        {
            Assert.assertTrue(iterator.hasNext());
            Integer integer = iterator.next();
            Assert.assertEquals(3, integer.intValue() + i);
        }
        Assert.assertFalse(iterator.hasNext());
    }

    @Test(expected = NoSuchElementException.class)
    public void iterator_throws()
    {
        RichIterable<Integer> objects = this.newWith(1, 2, 3);
        Iterator<Integer> iterator = objects.iterator();
        for (int i = objects.size(); i-- > 0; )
        {
            Assert.assertTrue(iterator.hasNext());
            iterator.next();
        }
        Assert.assertFalse(iterator.hasNext());
        iterator.next();
    }

    @Test
    public void injectInto()
    {
        RichIterable<Integer> objects = this.newWith(1, 2, 3);
        Integer result = objects.injectInto(1, AddFunction.INTEGER);
        Assert.assertEquals(Integer.valueOf(7), result);
        int sum = objects.injectInto(0, AddFunction.INTEGER_TO_INT);
        Assert.assertEquals(6, sum);
    }

    @Test
    public void injectIntoInt()
    {
        RichIterable<Integer> objects = this.newWith(1, 2, 3);
        int result = objects.injectIntoInt(1, AddFunction.INTEGER_TO_INT);
        Assert.assertEquals(7, result);
        int sum = objects.injectIntoInt(0, AddFunction.INTEGER_TO_INT);
        Assert.assertEquals(6, sum);
    }

    @Test
    public void injectIntoLong()
    {
        RichIterable<Integer> objects = this.newWith(1, 2, 3);
        long result = objects.injectIntoLong(1, AddFunction.INTEGER_TO_LONG);
        Assert.assertEquals(7, result);
        long sum = objects.injectIntoLong(0, AddFunction.INTEGER_TO_LONG);
        Assert.assertEquals(6, sum);
    }

    @Test
    public void injectIntoDouble()
    {
        RichIterable<Integer> objects = this.newWith(1, 2, 3);
        double result = objects.injectIntoDouble(1, AddFunction.INTEGER_TO_DOUBLE);
        Assert.assertEquals(7.0d, result, 0.001);
        double sum = objects.injectIntoDouble(0, AddFunction.INTEGER_TO_DOUBLE);
        Assert.assertEquals(6.0d, sum, 0.001);
    }

    @Test
    public void injectIntoFloat()
    {
        RichIterable<Integer> objects = this.newWith(1, 2, 3);
        float result = objects.injectIntoFloat(1, AddFunction.INTEGER_TO_FLOAT);
        Assert.assertEquals(7.0f, result, 0.001f);
        float sum = objects.injectIntoFloat(0, AddFunction.INTEGER_TO_FLOAT);
        Assert.assertEquals(6.0f, sum, 0.001f);
    }

    @Test
    public void sumFloat()
    {
        RichIterable<Integer> objects = this.newWith(1, 2, 3);
        float expected = objects.injectInto(0, AddFunction.INTEGER_TO_FLOAT);
        double actual = objects.sumOfFloat(Integer::floatValue);
        Assert.assertEquals(expected, actual, 0.001);
    }

    @Test
    public void summarizeFloat()
    {
        RichIterable<Integer> objects = this.newWith(1, 2, 3);
        DoubleSummaryStatistics expected = objects.summarizeFloat(Integer::floatValue);
        Assert.assertEquals(6.0d, expected.getSum(), 0.0);
        Assert.assertEquals(3, expected.getCount());
    }

    @Test
    public void sumFloatConsistentRounding1()
    {
        MutableList<Integer> list = Interval.oneTo(100_000).toList().shuffleThis();

        // The test only ensures the consistency/stability of rounding. This is not meant to test the "correctness" of the float calculation result.
        // Indeed, the lower bits of this calculation result are always incorrect due to the information loss of original float values.
        Assert.assertEquals(
                1.082323233761663,
                this.newWith(list.toArray(new Integer[]{})).sumOfFloat(i -> 1.0f / (i.floatValue() * i.floatValue() * i.floatValue() * i.floatValue())),
                1.0e-15);
    }

    @Test
    public void sumFloatConsistentRounding2()
    {
        MutableList<Integer> list = Interval.oneTo(99_999).toList().shuffleThis();

        // The test only ensures the consistency/stability of rounding. This is not meant to test the "correctness" of the float calculation result.
        // Indeed, the lower bits of this calculation result are always incorrect due to the information loss of original float values.
        Assert.assertEquals(
                33333.00099340081,
                this.newWith(list.toArray(new Integer[]{})).sumOfFloat(i -> 1.0f / 3.0f),
                0.0);
    }

    @Test
    public void sumDouble()
    {
        RichIterable<Integer> objects = this.newWith(1, 2, 3);
        double expected = objects.injectInto(0, AddFunction.INTEGER_TO_DOUBLE);
        double actual = objects.sumOfDouble(Integer::doubleValue);
        Assert.assertEquals(expected, actual, 0.001);
    }

    @Test
    public void summarizeDouble()
    {
        RichIterable<Integer> objects = this.newWith(1, 2, 3);
        DoubleSummaryStatistics expected = objects.summarizeDouble(Integer::doubleValue);
        Assert.assertEquals(6.0d, expected.getSum(), 0.0);
        Assert.assertEquals(3, expected.getCount());
    }

    @Test
    public void sumDoubleConsistentRounding1()
    {
        MutableList<Integer> list = Interval.oneTo(100_000).toList().shuffleThis();

        Assert.assertEquals(
                1.082323233711138,
                this.newWith(list.toArray(new Integer[]{})).sumOfDouble(i -> 1.0d / (i.doubleValue() * i.doubleValue() * i.doubleValue() * i.doubleValue())),
                1.0e-15);
    }

    @Test
    public void sumDoubleConsistentRounding2()
    {
        MutableList<Integer> list = Interval.oneTo(99_999).toList().shuffleThis();

        Assert.assertEquals(
                33333.0,
                this.newWith(list.toArray(new Integer[]{})).sumOfDouble(i -> 1.0d / 3.0d),
                0.0);
    }

    @Test
    public void sumInteger()
    {
        RichIterable<Integer> objects = this.newWith(1, 2, 3);
        long expected = objects.injectInto(0L, AddFunction.INTEGER_TO_LONG);
        long actual = objects.sumOfInt(integer -> integer);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void summarizeInt()
    {
        RichIterable<Integer> objects = this.newWith(1, 2, 3);
        IntSummaryStatistics expected = objects.summarizeInt(Integer::intValue);
        Assert.assertEquals(6, expected.getSum());
        Assert.assertEquals(3, expected.getCount());
    }

    @Test
    public void sumLong()
    {
        RichIterable<Integer> objects = this.newWith(1, 2, 3);
        long expected = objects.injectInto(0L, AddFunction.INTEGER_TO_LONG);
        long actual = objects.sumOfLong(Integer::longValue);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void summarizeLong()
    {
        RichIterable<Integer> objects = this.newWith(1, 2, 3);
        LongSummaryStatistics expected = objects.summarizeLong(Integer::longValue);
        Assert.assertEquals(6, expected.getSum());
        Assert.assertEquals(3, expected.getCount());
    }

    @Test
    public void sumByInt()
    {
        RichIterable<Integer> values = this.newWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        ObjectLongMap<Integer> result = values.sumByInt(i -> i % 2, e -> e);
        Assert.assertEquals(25, result.get(1));
        Assert.assertEquals(30, result.get(0));
    }

    @Test
    public void sumByFloat()
    {
        RichIterable<Integer> values = this.newWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        ObjectDoubleMap<Integer> result = values.sumByFloat(f -> f % 2, e -> e);
        Assert.assertEquals(25.0f, result.get(1), 0.0);
        Assert.assertEquals(30.0f, result.get(0), 0.0);
    }

    @Test
    public void sumByFloatConsistentRounding()
    {
        MutableList<Integer> group1 = Interval.oneTo(100_000).toList().shuffleThis();
        MutableList<Integer> group2 = Interval.fromTo(100_001, 200_000).toList().shuffleThis();
        MutableList<Integer> integers = Lists.mutable.withAll(group1);
        integers.addAll(group2);
        ObjectDoubleMap<Integer> result = integers.sumByFloat(
                integer -> integer > 100_000 ? 2 : 1,
                integer -> {
                    Integer i = integer > 100_000 ? integer - 100_000 : integer;
                    return 1.0f / (i.floatValue() * i.floatValue() * i.floatValue() * i.floatValue());
                });

        // The test only ensures the consistency/stability of rounding. This is not meant to test the "correctness" of the float calculation result.
        // Indeed, the lower bits of this calculation result are always incorrect due to the information loss of original float values.
        Assert.assertEquals(
                1.082323233761663,
                result.get(1),
                1.0e-15);
        Assert.assertEquals(
                1.082323233761663,
                result.get(2),
                1.0e-15);
    }

    @Test
    public void sumByLong()
    {
        RichIterable<Integer> values = this.newWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        ObjectLongMap<Integer> result = values.sumByLong(l -> l % 2, e -> e);
        Assert.assertEquals(25, result.get(1));
        Assert.assertEquals(30, result.get(0));
    }

    @Test
    public void sumByDouble()
    {
        RichIterable<Integer> values = this.newWith(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        ObjectDoubleMap<Integer> result = values.sumByDouble(d -> d % 2, e -> e);
        Assert.assertEquals(25.0d, result.get(1), 0.0);
        Assert.assertEquals(30.0d, result.get(0), 0.0);
    }

    @Test
    public void sumByDoubleConsistentRounding()
    {
        MutableList<Integer> group1 = Interval.oneTo(100_000).toList().shuffleThis();
        MutableList<Integer> group2 = Interval.fromTo(100_001, 200_000).toList().shuffleThis();
        MutableList<Integer> integers = Lists.mutable.withAll(group1);
        integers.addAll(group2);
        ObjectDoubleMap<Integer> result = integers.sumByDouble(
                integer -> integer > 100_000 ? 2 : 1,
                integer -> {
                    Integer i = integer > 100_000 ? integer - 100_000 : integer;
                    return 1.0d / (i.doubleValue() * i.doubleValue() * i.doubleValue() * i.doubleValue());
                });

        Assert.assertEquals(
                1.082323233711138,
                result.get(1),
                1.0e-15);
        Assert.assertEquals(
                1.082323233711138,
                result.get(2),
                1.0e-15);
    }

    @Test
    public void toArray()
    {
        RichIterable<Integer> objects = this.newWith(1, 2, 3);
        Object[] array = objects.toArray();
        Verify.assertSize(3, array);
        Integer[] array2 = objects.toArray(new Integer[3]);
        Verify.assertSize(3, array2);
    }

    @Test
    public void partition()
    {
        RichIterable<Integer> integers = this.newWith(-3, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        PartitionIterable<Integer> result = integers.partition(IntegerPredicates.isEven());
        Assert.assertEquals(this.newWith(-2, 0, 2, 4, 6, 8), result.getSelected());
        Assert.assertEquals(this.newWith(-3, -1, 1, 3, 5, 7, 9), result.getRejected());
    }

    @Test
    public void partitionWith()
    {
        RichIterable<Integer> integers = this.newWith(-3, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
        PartitionIterable<Integer> result = integers.partitionWith(Predicates2.in(), Lists.mutable.with(-2, 0, 2, 4, 6, 8));
        Assert.assertEquals(this.newWith(-2, 0, 2, 4, 6, 8), result.getSelected());
        Assert.assertEquals(this.newWith(-3, -1, 1, 3, 5, 7, 9), result.getRejected());
    }

    @Test
    public void toList()
    {
        MutableList<Integer> list = this.newWith(1, 2, 3, 4).toList();
        Assert.assertEquals(Lists.immutable.with(1, 2, 3, 4), list);
    }

    @Test
    public void toImmutableList()
    {
        ImmutableList<Integer> list = this.newWith(1, 2, 3, 4).toImmutableList();
        Verify.assertContainsAll(list, 1, 2, 3, 4);
        ImmutableList<Integer> singletonList = this.newWith(1).toImmutableList();
        Assert.assertEquals(Lists.mutable.with(1), singletonList);
        ImmutableList<Integer> emptyList = this.<Integer>newWith().toImmutableList();
        Assert.assertEquals(Lists.immutable.empty(), emptyList);
    }

    @Test
    public void toCollection()
    {
        MutableList<Integer> list = this.newWith(1, 2, 3, 4).into(Lists.mutable.with(0));
        Verify.assertContainsAll(list, 0, 1, 2, 3, 4);
    }

    @Test
    public void toBag()
    {
        MutableBag<Integer> bag = this.newWith(1, 2, 3, 4).toBag();
        Assert.assertEquals(Bags.immutable.with(1, 2, 3, 4), bag);
    }

    @Test
    public void toImmutableBag()
    {
        ImmutableBag<Integer> bag = this.newWith(1, 2, 3, 4).toImmutableBag();
        Assert.assertEquals(Bags.mutable.with(1, 2, 3, 4), bag);
        ImmutableBag<Integer> singletonBag = this.newWith(1).toImmutableBag();
        Assert.assertEquals(Bags.mutable.with(1), singletonBag);
        ImmutableBag<Integer> emptyBag = this.<Integer>newWith().toImmutableBag();
        Assert.assertEquals(Bags.immutable.empty(), emptyBag);
    }

    @Test
    public void toSortedList_natural_ordering()
    {
        RichIterable<Integer> integers = this.newWith(2, 1, 5, 3, 4);
        MutableList<Integer> list = integers.toSortedList();
        Verify.assertStartsWith(list, 1, 2, 3, 4, 5);
    }

    @Test
    public void toImmutableSortedList_natural_ordering()
    {
        ImmutableList<Integer> list = this.newWith(4, 2, 1, 3).toImmutableSortedList();
        Assert.assertEquals(Lists.mutable.with(1, 2, 3, 4), list);
        ImmutableList<Integer> singletonList = this.newWith(1).toImmutableSortedList();
        Assert.assertEquals(Lists.mutable.with(1), singletonList);
        ImmutableList<Integer> emptyList = this.<Integer>newWith().toImmutableSortedList();
        Assert.assertEquals(Lists.immutable.empty(), emptyList);
    }

    @Test
    public void toSortedList_with_comparator()
    {
        RichIterable<Integer> integers = this.newWith(2, 4, 1, 3);
        MutableList<Integer> list = integers.toSortedList(Collections.reverseOrder());
        Assert.assertEquals(Lists.mutable.with(4, 3, 2, 1), list);
    }

    @Test
    public void toImmutableSortedList_with_comparator()
    {
        RichIterable<Integer> integers = this.newWith(2, 4, 1, 3);
        ImmutableList<Integer> list = integers.toImmutableSortedList(Collections.reverseOrder());
        Assert.assertEquals(Lists.mutable.with(4, 3, 2, 1), list);
    }

    @Test(expected = NullPointerException.class)
    public void toSortedList_with_null()
    {
        this.newWith(2, 4, null, 1, 3).toSortedList();
    }

    @Test
    public void toSortedBag_natural_ordering()
    {
        RichIterable<Integer> integers = this.newWith(2, 2, 5, 3, 4);
        MutableSortedBag<Integer> bag = integers.toSortedBag();
        Verify.assertSortedBagsEqual(TreeBag.newBagWith(2, 2, 3, 4, 5), bag);
    }

    @Test
    public void toImmutableSortedBag_natural_ordering()
    {
        ImmutableSortedBag<Integer> bag = this.newWith(4, 1, 2, 3).toImmutableSortedBag();
        Assert.assertEquals(SortedBags.mutable.with(1, 2, 3, 4), bag);
        ImmutableSortedBag<Integer> singletonBag = this.newWith(1).toImmutableSortedBag();
        Assert.assertEquals(SortedBags.mutable.with(1), singletonBag);
        ImmutableSortedBag<Integer> emptyBag = this.<Integer>newWith().toImmutableSortedBag();
        Assert.assertEquals(SortedBags.immutable.empty(), emptyBag);
    }

    @Test
    public void toSortedBag_with_comparator()
    {
        RichIterable<Integer> integers = this.newWith(2, 4, 2, 3);
        MutableSortedBag<Integer> bag = integers.toSortedBag(Collections.reverseOrder());
        Verify.assertSortedBagsEqual(TreeBag.newBagWith(Collections.reverseOrder(), 4, 3, 2, 2), bag);
    }

    @Test
    public void toImmutableSortedBag_with_comparator()
    {
        RichIterable<Integer> integers = this.newWith(2, 4, 2, 3);
        ImmutableSortedBag<Integer> bag = integers.toImmutableSortedBag(Collections.reverseOrder());
        Verify.assertSortedBagsEqual(TreeBag.newBagWith(Collections.reverseOrder(), 4, 3, 2, 2), bag);
    }

    @Test(expected = NullPointerException.class)
    public void toSortedBag_with_null()
    {
        this.newWith(2, 4, null, 1, 2).toSortedBag();
    }

    @Test
    public void toSortedBagBy()
    {
        RichIterable<Integer> integers = this.newWith(2, 4, 1, 3);
        MutableSortedBag<Integer> bag = integers.toSortedBagBy(String::valueOf);
        Verify.assertSortedBagsEqual(TreeBag.newBagWith(1, 2, 3, 4), bag);
    }

    @Test
    public void toImmutableSortedBagBy()
    {
        RichIterable<Integer> integers = this.newWith(2, 4, 1, 3);
        ImmutableSortedBag<Integer> bag = integers.toImmutableSortedBagBy(String::valueOf);
        Verify.assertSortedBagsEqual(TreeBag.newBagWith(1, 2, 3, 4), bag);
    }

    @Test
    public void toSortedListBy()
    {
        RichIterable<Integer> integers = this.newWith(2, 4, 1, 3);
        MutableList<Integer> list = integers.toSortedListBy(String::valueOf);
        Assert.assertEquals(Lists.mutable.with(1, 2, 3, 4), list);
    }

    @Test
    public void toImmutableSortedListBy()
    {
        RichIterable<Integer> integers = this.newWith(2, 4, 1, 3);
        ImmutableList<Integer> list = integers.toImmutableSortedListBy(String::valueOf);
        Assert.assertEquals(Lists.mutable.with(1, 2, 3, 4), list);
    }

    @Test
    public void toSortedSet_natural_ordering()
    {
        RichIterable<Integer> integers = this.newWith(2, 4, 1, 3, 2, 1, 3, 4);
        MutableSortedSet<Integer> set = integers.toSortedSet();
        Verify.assertSortedSetsEqual(TreeSortedSet.newSetWith(1, 2, 3, 4), set);
    }

    @Test
    public void toImmutableSortSet_natural_ordering()
    {
        ImmutableSortedSet<Integer> set = this.newWith(2, 1, 4, 3).toImmutableSortedSet();
        Assert.assertEquals(SortedSets.mutable.with(1, 2, 3, 4), set);
        ImmutableSortedSet<Integer> singletonSet = this.newWith(1).toImmutableSortedSet();
        Assert.assertEquals(SortedSets.mutable.with(1), singletonSet);
        ImmutableSortedSet<Integer> emptySet = this.<Integer>newWith().toImmutableSortedSet();
        Assert.assertEquals(SortedSets.immutable.empty(), emptySet);
    }

    @Test
    public void toSortedSet_with_comparator()
    {
        RichIterable<Integer> integers = this.newWith(2, 4, 4, 2, 1, 4, 1, 3);
        MutableSortedSet<Integer> set = integers.toSortedSet(Collections.reverseOrder());
        Verify.assertSortedSetsEqual(TreeSortedSet.newSetWith(Collections.reverseOrder(), 1, 2, 3, 4), set);
    }

    @Test
    public void toImmutableSortedSet_with_comparator()
    {
        RichIterable<Integer> integers = this.newWith(2, 4, 4, 2, 1, 4, 1, 3);
        ImmutableSortedSet<Integer> set = integers.toImmutableSortedSet(Collections.reverseOrder());
        Verify.assertSortedSetsEqual(TreeSortedSet.newSetWith(Collections.reverseOrder(), 1, 2, 3, 4), set.castToSortedSet());
    }

    @Test
    public void toSortedSetBy()
    {
        RichIterable<Integer> integers = this.newWith(2, 4, 1, 3);
        MutableSortedSet<Integer> set = integers.toSortedSetBy(String::valueOf);
        Verify.assertSortedSetsEqual(TreeSortedSet.newSetWith(1, 2, 3, 4), set);
    }

    @Test
    public void toImmutableSortedSetBy()
    {
        RichIterable<Integer> integers = this.newWith(2, 4, 1, 3);
        ImmutableSortedSet<Integer> set = integers.toImmutableSortedSetBy(String::valueOf);
        Verify.assertSortedSetsEqual(TreeSortedSet.newSetWith(1, 2, 3, 4), set.castToSortedSet());
    }

    @Test(expected = NullPointerException.class)
    public void toSortedListBy_with_null()
    {
        this.newWith(2, 4, null, 1, 3).toSortedListBy(Functions.getIntegerPassThru());
    }

    @Test
    public void toSet()
    {
        RichIterable<Integer> integers = this.newWith(1, 2, 3, 4);
        MutableSet<Integer> set = integers.toSet();
        Assert.assertEquals(Sets.immutable.with(1, 2, 3, 4), set);
    }

    @Test
    public void toImmutableSet()
    {
        ImmutableSet<Integer> set = this.newWith(1, 2, 3, 4).toImmutableSet();
        Assert.assertEquals(Sets.mutable.with(1, 2, 3, 4), set);
        ImmutableSet<Integer> singletonSet = this.newWith(1).toImmutableSet();
        Assert.assertEquals(Sets.mutable.with(1), singletonSet);
        ImmutableSet<Integer> emptySet = this.<Integer>newWith().toImmutableSet();
        Assert.assertEquals(Sets.immutable.empty(), emptySet);
    }

    @Test
    public void toMap()
    {
        RichIterable<Integer> integers = this.newWith(1, 2, 3, 4);
        MutableMap<String, String> map =
                integers.toMap(Object::toString, Object::toString);
        Assert.assertEquals(UnifiedMap.newWithKeysValues("1", "1", "2", "2", "3", "3", "4", "4"), map);
    }

    @Test
    public void toImmutableMap()
    {
        RichIterable<Integer> integers = this.newWith(1, 2, 3, 4);
        ImmutableMap<String, String> map =
                integers.toImmutableMap(Object::toString, Object::toString);
        Assert.assertEquals(UnifiedMap.newWithKeysValues("1", "1", "2", "2", "3", "3", "4", "4"), map);
        RichIterable<Integer> empty = this.newWith();
        ImmutableMap<String, String> emptyMap =
                empty.toImmutableMap(Object::toString, Object::toString);
        Assert.assertSame(Maps.immutable.empty(), emptyMap);
    }

    @Test
    public void toMapTarget()
    {
        RichIterable<Integer> integers = this.newWith(1, 2, 3, 4);
        Map<String, String> jdkMap = new HashMap<>();
        jdkMap.put("1", "1");
        jdkMap.put("2", "2");
        jdkMap.put("3", "3");
        jdkMap.put("4", "4");
        Map<String, String> targetMap =
                integers.toMap(Object::toString, Object::toString, new HashMap<>());
        Assert.assertEquals(jdkMap, targetMap);
        Assert.assertTrue(targetMap instanceof HashMap);
    }

    @Test
    public void toSortedMap()
    {
        RichIterable<Integer> integers = this.newWith(1, 2, 3);
        MutableSortedMap<Integer, String> map = integers.toSortedMap(Functions.getIntegerPassThru(), Object::toString);
        Verify.assertMapsEqual(TreeSortedMap.newMapWith(1, "1", 2, "2", 3, "3"), map);
        Verify.assertListsEqual(Lists.mutable.with(1, 2, 3), map.keySet().toList());
    }

    @Test
    public void toSortedMap_with_comparator()
    {
        RichIterable<Integer> integers = this.newWith(1, 2, 3);
        MutableSortedMap<Integer, String> map = integers.toSortedMap(Comparators.reverseNaturalOrder(),
                Functions.getIntegerPassThru(), Object::toString);
        Verify.assertMapsEqual(TreeSortedMap.newMapWith(Comparators.reverseNaturalOrder(), 1, "1", 2, "2", 3, "3"), map);
        Verify.assertListsEqual(Lists.mutable.with(3, 2, 1), map.keySet().toList());
    }

    @Test
    public void toSortedMapBy()
    {
        RichIterable<Integer> integers = this.newWith(1, 2, 3);
        MutableSortedMap<Integer, String> map = integers.toSortedMapBy(key -> -key,
                Functions.getIntegerPassThru(), Object::toString);
        Verify.assertMapsEqual(TreeSortedMap.newMapWith(Comparators.reverseNaturalOrder(), 1, "1", 2, "2", 3, "3"), map);
        Verify.assertListsEqual(Lists.mutable.with(3, 2, 1), map.keySet().toList());
    }

    @Test
    public void toBiMap()
    {
        RichIterable<Integer> integers = this.newWith(1, 2, 3);

        Assert.assertEquals(
                Maps.mutable.with("1", "1", "2", "2", "3", "3"),
                integers.toBiMap(Object::toString, Object::toString));

        Assert.assertThrows(
                IllegalArgumentException.class,
                () -> integers.toBiMap(i -> "Constant Key", Objects::toString));
        Assert.assertThrows(
                IllegalArgumentException.class,
                () -> integers.toBiMap(Object::toString, i -> "Constant Value"));
        Assert.assertThrows(
                IllegalArgumentException.class,
                () -> integers.toBiMap(i -> "Constant Key", i -> "Constant Value"));
    }

    @Test
    public void toImmutableBiMap()
    {
        RichIterable<Integer> integers = this.newWith(1, 2, 3);

        Assert.assertEquals(
                Maps.mutable.with("1", "1", "2", "2", "3", "3"),
                integers.toImmutableBiMap(Object::toString, Object::toString));

        Assert.assertThrows(
                IllegalArgumentException.class,
                () -> integers.toImmutableBiMap(i -> "Constant Key", Objects::toString));
        Assert.assertThrows(
                IllegalArgumentException.class,
                () -> integers.toImmutableBiMap(Object::toString, i -> "Constant Value"));
        Assert.assertThrows(
                IllegalArgumentException.class,
                () -> integers.toImmutableBiMap(i -> "Constant Key", i -> "Constant Value"));
    }

    @Test
    public void testToString()
    {
        RichIterable<Object> collection = this.newWith(1, 2, 3);
        Assert.assertEquals("[1, 2, 3]", collection.toString());
    }

    @Test
    public void makeString()
    {
        RichIterable<Object> collection = this.newWith(1, 2, 3);
        Assert.assertEquals(collection.toString(), '[' + collection.makeString() + ']');
    }

    @Test
    public void makeStringWithSeparator()
    {
        RichIterable<Object> collection = this.newWith(1, 2, 3);
        Assert.assertEquals(collection.toString(), '[' + collection.makeString(", ") + ']');
    }

    @Test
    public void makeStringWithSeparatorAndStartAndEnd()
    {
        RichIterable<Object> collection = this.newWith(1, 2, 3);
        Assert.assertEquals(collection.toString(), collection.makeString("[", ", ", "]"));
    }

    @Test
    public void fusedCollectMakeString()
    {
        RichIterable<Integer> collection = this.newWith(1, 2, 3);
        Assert.assertEquals(
                collection.asLazy().collect(Integer::toUnsignedString).makeString("[", ", ", "]"),
                collection.makeString(Integer::toUnsignedString, "[", ", ", "]"));
    }

    @Test
    public void appendString()
    {
        RichIterable<Object> collection = this.newWith(1, 2, 3);
        Appendable builder = new StringBuilder();
        collection.appendString(builder);
        Assert.assertEquals(collection.toString(), '[' + builder.toString() + ']');
    }

    @Test
    public void appendStringWithSeparator()
    {
        RichIterable<Object> collection = this.newWith(1, 2, 3);
        Appendable builder = new StringBuilder();
        collection.appendString(builder, ", ");
        Assert.assertEquals(collection.toString(), '[' + builder.toString() + ']');
    }

    @Test
    public void appendStringWithSeparatorAndStartAndEnd()
    {
        RichIterable<Object> collection = this.newWith(1, 2, 3);
        Appendable builder = new StringBuilder();
        collection.appendString(builder, "[", ", ", "]");
        Assert.assertEquals(collection.toString(), builder.toString());
    }

    @Test
    public void appendStringThrows()
    {
        Verify.assertThrows(
                RuntimeException.class,
                () -> this.newWith(1, 2, 3)
                        .appendString(new ThrowingAppendable()));
        Verify.assertThrows(
                RuntimeException.class,
                () -> this.newWith(1, 2, 3)
                        .appendString(new ThrowingAppendable(), ", "));
        Verify.assertThrows(
                RuntimeException.class,
                () -> this.newWith(1, 2, 3)
                        .appendString(new ThrowingAppendable(), "[", ", ", "]"));
    }

    /**
     * @since 9.0
     */
    @Test
    public void countBy()
    {
        RichIterable<Integer> integers = this.newWith(1, 2, 3, 4, 5, 6);
        Bag<Integer> evensAndOdds = integers.countBy(each -> Integer.valueOf(each % 2));
        Assert.assertEquals(3, evensAndOdds.occurrencesOf(1));
        Assert.assertEquals(3, evensAndOdds.occurrencesOf(0));
        Bag<Integer> evensAndOdds2 = integers.countBy(each -> Integer.valueOf(each % 2), Bags.mutable.empty());
        Assert.assertEquals(3, evensAndOdds2.occurrencesOf(1));
        Assert.assertEquals(3, evensAndOdds2.occurrencesOf(0));
    }

    /**
     * @since 9.0
     */
    @Test
    public void countByWith()
    {
        RichIterable<Integer> integers = this.newWith(1, 2, 3, 4, 5, 6);
        Bag<Integer> evensAndOdds = integers.countByWith((each, parm) -> Integer.valueOf(each % parm), 2);
        Assert.assertEquals(3, evensAndOdds.occurrencesOf(1));
        Assert.assertEquals(3, evensAndOdds.occurrencesOf(0));
        Bag<Integer> evensAndOdds2 = integers.countByWith((each, parm) -> Integer.valueOf(each % parm), 2, Bags.mutable.empty());
        Assert.assertEquals(3, evensAndOdds2.occurrencesOf(1));
        Assert.assertEquals(3, evensAndOdds2.occurrencesOf(0));
    }

    /**
     * @since 10.0.0
     */
    @Test
    public void countByEach()
    {
        RichIterable<Integer> integerList = this.newWith(1, 2, 4);
        Bag<Integer> integerBag1 = integerList.countByEach(each -> IntInterval.oneTo(5).collect(i -> each * i));
        Assert.assertEquals(1, integerBag1.occurrencesOf(1));
        Assert.assertEquals(2, integerBag1.occurrencesOf(2));
        Assert.assertEquals(3, integerBag1.occurrencesOf(4));
        Assert.assertEquals(2, integerBag1.occurrencesOf(8));
        Assert.assertEquals(1, integerBag1.occurrencesOf(12));
        Bag<Integer> integerBag2 = integerList.countByEach(each -> IntInterval.oneTo(5).collect(i -> each * i), Bags.mutable.empty());
        Assert.assertEquals(1, integerBag2.occurrencesOf(1));
        Assert.assertEquals(2, integerBag2.occurrencesOf(2));
        Assert.assertEquals(3, integerBag2.occurrencesOf(4));
        Assert.assertEquals(2, integerBag2.occurrencesOf(8));
        Assert.assertEquals(1, integerBag2.occurrencesOf(12));
    }

    @Test
    public void groupBy()
    {
        RichIterable<Integer> collection = this.newWith(1, 2, 3, 4, 5, 6, 7);
        Function<Integer, Boolean> isOddFunction = object -> IntegerPredicates.isOdd().accept(object);

        MutableMap<Boolean, RichIterable<Integer>> expected =
                UnifiedMap.newWithKeysValues(
                        Boolean.TRUE, this.newWith(1, 3, 5, 7),
                        Boolean.FALSE, this.newWith(2, 4, 6));

        Multimap<Boolean, Integer> multimap = collection.groupBy(isOddFunction);
        Assert.assertEquals(expected, multimap.toMap());

        Function<Integer, Boolean> function = (Integer object) -> true;
        MutableMultimap<Boolean, Integer> multimap2 = collection.groupBy(
                isOddFunction,
                this.<Integer>newWith().groupBy(function).toMutable());
        Assert.assertEquals(expected, multimap2.toMap());
    }

    @Test
    public void groupByEach()
    {
        RichIterable<Integer> collection = this.newWith(1, 2, 3, 4, 5, 6, 7);

        NegativeIntervalFunction function = new NegativeIntervalFunction();
        MutableMultimap<Integer, Integer> expected = this.<Integer>newWith().groupByEach(function).toMutable();
        for (int i = 1; i < 8; i++)
        {
            expected.putAll(-i, Interval.fromTo(i, 7));
        }

        Multimap<Integer, Integer> actual =
                collection.groupByEach(function);
        Assert.assertEquals(expected, actual);

        Multimap<Integer, Integer> actualWithTarget =
                collection.groupByEach(function, this.<Integer>newWith().groupByEach(function).toMutable());
        Assert.assertEquals(expected, actualWithTarget);
    }

    @Test
    public void groupByUniqueKey()
    {
        RichIterable<Integer> collection = this.newWith(1, 2, 3);
        Assert.assertEquals(UnifiedMap.newWithKeysValues(1, 1, 2, 2, 3, 3), collection.groupByUniqueKey(id -> id));
    }

    @Test(expected = IllegalStateException.class)
    public void groupByUniqueKey_throws_for_duplicate()
    {
        RichIterable<Integer> collection = this.newWith(1, 2, 3);
        collection.groupByUniqueKey(id -> 2);
    }

    @Test
    public void groupByUniqueKey_target()
    {
        RichIterable<Integer> collection = this.newWith(1, 2, 3);
        Assert.assertEquals(
                UnifiedMap.newWithKeysValues(0, 0, 1, 1, 2, 2, 3, 3),
                collection.groupByUniqueKey(id -> id, UnifiedMap.newWithKeysValues(0, 0)));
    }

    @Test(expected = IllegalStateException.class)
    public void groupByUniqueKey_target_throws_for_duplicate()
    {
        RichIterable<Integer> collection = this.newWith(1, 2, 3);
        Assert.assertEquals(
                UnifiedMap.newWithKeysValues(0, 0, 1, 1, 2, 2, 3, 3),
                collection.groupByUniqueKey(id -> id, UnifiedMap.newWithKeysValues(2, 2)));
    }

    @Test
    public void zip()
    {
        RichIterable<String> collection = this.newWith("1", "2", "3", "4", "5", "6", "7");
        List<Object> nulls = Collections.nCopies(collection.size(), null);
        List<Object> nullsPlusOne = Collections.nCopies(collection.size() + 1, null);
        List<Object> nullsMinusOne = Collections.nCopies(collection.size() - 1, null);

        RichIterable<Pair<String, Object>> pairs = collection.zip(nulls);
        Assert.assertEquals(
                collection.toSet(),
                pairs.collect((Function<Pair<String, ?>, String>) Pair::getOne).toSet());
        Assert.assertEquals(
                nulls,
                pairs.collect((Function<Pair<?, Object>, Object>) Pair::getTwo, Lists.mutable.of()));

        RichIterable<Pair<String, Object>> pairsPlusOne = collection.zip(nullsPlusOne);
        Assert.assertEquals(
                collection.toSet(),
                pairsPlusOne.collect((Function<Pair<String, ?>, String>) Pair::getOne).toSet());
        Assert.assertEquals(nulls, pairsPlusOne.collect((Function<Pair<?, Object>, Object>) Pair::getTwo, Lists.mutable.of()));

        RichIterable<Pair<String, Object>> pairsMinusOne = collection.zip(nullsMinusOne);
        Assert.assertEquals(collection.size() - 1, pairsMinusOne.size());
        Assert.assertTrue(collection.containsAllIterable(pairsMinusOne.collect((Function<Pair<String, ?>, String>) Pair::getOne)));

        Assert.assertEquals(
                collection.zip(nulls).toSet(),
                collection.zip(nulls, UnifiedSet.newSet()));
    }

    @Test
    public void zipWithIndex()
    {
        RichIterable<String> collection = this.newWith("1", "2", "3", "4", "5", "6", "7");
        RichIterable<Pair<String, Integer>> pairs = collection.zipWithIndex();

        Assert.assertEquals(
                collection.toSet(),
                pairs.collect((Function<Pair<String, ?>, String>) Pair::getOne).toSet());
        Assert.assertEquals(
                Interval.zeroTo(collection.size() - 1).toSet(),
                pairs.collect((Function<Pair<?, Integer>, Integer>) Pair::getTwo, UnifiedSet.newSet()));

        Assert.assertEquals(
                collection.zipWithIndex().toSet(),
                collection.zipWithIndex(UnifiedSet.newSet()));
    }

    @Test
    public void chunk()
    {
        RichIterable<String> collection = this.newWith("1", "2", "3", "4", "5", "6", "7");
        RichIterable<RichIterable<String>> groups = collection.chunk(2);
        RichIterable<Integer> sizes = groups.collect(RichIterable::size);
        Assert.assertEquals(Lists.mutable.with(2, 2, 2, 1), sizes);
    }

    @Test
    public void chunk_empty()
    {
        RichIterable<String> collection = this.newWith();
        RichIterable<RichIterable<String>> groups = collection.chunk(2);
        Assert.assertEquals(groups.size(), 0);
    }

    @Test
    public void chunk_single()
    {
        RichIterable<String> collection = this.newWith("1");
        RichIterable<RichIterable<String>> groups = collection.chunk(2);
        Assert.assertEquals(Lists.mutable.with(1), groups.collect(RichIterable::size));
    }

    @Test(expected = IllegalArgumentException.class)
    public void chunk_zero_throws()
    {
        RichIterable<String> collection = this.newWith("1", "2", "3", "4", "5", "6", "7");
        collection.chunk(0);
    }

    @Test
    public void chunk_large_size()
    {
        RichIterable<String> collection = this.newWith("1", "2", "3", "4", "5", "6", "7");
        Assert.assertEquals(collection, collection.chunk(10).getOnly());
    }

    @Test
    public void empty()
    {
        Verify.assertIterableEmpty(this.newWith());
        Assert.assertTrue(this.newWith().isEmpty());
        Assert.assertFalse(this.newWith().notEmpty());
    }

    @Test
    public void notEmpty()
    {
        RichIterable<Integer> notEmpty = this.newWith(1);
        Verify.assertIterableNotEmpty(notEmpty);
    }

    @Test
    public void aggregateByMutating()
    {
        RichIterable<Integer> collection = this.newWith(1, 1, 1, 2, 2, 3);
        MapIterable<String, AtomicInteger> aggregation = collection.aggregateInPlaceBy(String::valueOf, AtomicInteger::new, AtomicInteger::addAndGet);
        if (collection instanceof Set)
        {
            Assert.assertEquals(1, aggregation.get("1").intValue());
            Assert.assertEquals(2, aggregation.get("2").intValue());
            Assert.assertEquals(3, aggregation.get("3").intValue());
        }
        else
        {
            Assert.assertEquals(3, aggregation.get("1").intValue());
            Assert.assertEquals(4, aggregation.get("2").intValue());
            Assert.assertEquals(3, aggregation.get("3").intValue());
        }
    }

    @Test
    public void aggregateByNonMutating()
    {
        MapIterable<String, Integer> aggregation =
                this.newWith(1, 1, 1, 2, 2, 3).aggregateBy(
                        Object::toString,
                        () -> 0,
                        (integer1, integer2) -> integer1 + integer2);

        if (this.newWith(1, 1, 1, 2, 2, 3) instanceof Set)
        {
            Assert.assertEquals(1, aggregation.get("1").intValue());
            Assert.assertEquals(2, aggregation.get("2").intValue());
            Assert.assertEquals(3, aggregation.get("3").intValue());
        }
        else
        {
            Assert.assertEquals(3, aggregation.get("1").intValue());
            Assert.assertEquals(4, aggregation.get("2").intValue());
            Assert.assertEquals(3, aggregation.get("3").intValue());
        }
    }

    @Test
    public void reduceOptional()
    {
        RichIterable<Integer> littleIterable = this.newWith(1, 2, 3);
        Optional<Integer> result =
                littleIterable.reduce(Integer::sum);
        Assert.assertEquals(6, result.get().intValue());

        RichIterable<Integer> bigIterable = this.newWith(Interval.oneTo(20).toArray());
        Optional<Integer> bigResult =
                bigIterable.reduce(Integer::max);
        Assert.assertEquals(20, bigResult.get().intValue());

        Optional<Integer> max =
                littleIterable.reduce(Integer::max);
        Assert.assertEquals(3, max.get().intValue());

        Optional<Integer> min =
                littleIterable.reduce(Integer::min);
        Assert.assertEquals(1, min.get().intValue());

        RichIterable<Integer> iterableEmpty = this.newWith();
        Optional<Integer> resultEmpty =
                iterableEmpty.reduce(Integer::sum);
        Assert.assertFalse(resultEmpty.isPresent());
    }
}
