package com.digdug.wesjohnston.richedittext;

import com.digdug.wesjohnston.richedittext.range.Range;
import com.digdug.wesjohnston.richedittext.range.RangeList;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class RangeListTest {
    @Test
    public void testConstructor() {
        RangeList list = new RangeList();
        Range r = new Range(1,2);
        list.addRange(r);
        assertEquals(1, list.size());
        assertEquals(1, list.getStart());
        assertEquals(2, list.getEnd());

        for (Range range : list) {
            assertEquals(range, r);
        }
    }

    @Test
    public void testAdding() {
        RangeList list = new RangeList();
        Range r = new Range(1,2);
        Range r2 = new Range(3,4);
        list.addRange(r);
        list.addRange(r2);
        assertEquals(2, list.size());
        assertEquals(1, list.getStart());
        assertEquals(4, list.getEnd());
    }

    @Test
    public void testAddingOverlap() {
        RangeList list = new RangeList();
        Range r = new Range(1,3);
        Range r2 = new Range(3,4);
        list.addRange(r);
        list.addRange(r2);
        assertEquals(1, list.size());
        assertEquals(1, list.getStart());
        assertEquals(4, list.getEnd());
        for (Range range : list) {
            assertEquals(new Range(1, 4), range);
        }
    }

    @Test
    public void testContains() {
        RangeList list = new RangeList();
        Range r = new Range(1,3);
        Range r2 = new Range(8,10);
        list.addRange(r);
        list.addRange(r2);
        assertTrue(list.contains(new Range(2, 3)));
        assertFalse(list.contains(new Range(2, 9)));
        assertFalse(list.contains(new Range(5, 6)));
    }

    @Test
    public void testIntersects() {
        RangeList list = new RangeList();
        Range r = new Range(1,3);
        Range r2 = new Range(8,10);
        list.addRange(r);
        list.addRange(r2);
        assertTrue(list.intersects(new Range(2, 3)));
        assertTrue(list.intersects(new Range(2, 9)));
        assertFalse(list.intersects(new Range(5, 6)));
    }

    @Test
    public void testRemove() {
        RangeList list = new RangeList();
        Range r = new Range(1,10);
        list.addRange(r);

        list.remove(new Range(1,1));
        assertEquals(1, list.size());
        assertEquals(2, list.getStart());
        assertEquals(10, list.getEnd());

        list.remove(new Range(10,10));
        assertEquals(1, list.size());
        assertEquals(2, list.getStart());
        assertEquals(9, list.getEnd());

        list.remove(new Range(10,15));
        assertEquals(1, list.size());
        assertEquals(2, list.getStart());
        assertEquals(9, list.getEnd());

        list.remove(new Range(5,6));
        assertEquals(2, list.size());
        assertEquals(2, list.getStart());
        assertEquals(9, list.getEnd());
    }

}
