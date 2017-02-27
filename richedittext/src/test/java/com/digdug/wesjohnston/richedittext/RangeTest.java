package com.digdug.wesjohnston.richedittext;

import com.digdug.wesjohnston.richedittext.range.Range;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RangeTest {
    @Test
    public void testCreate() throws Exception {
        Range r = new Range(1, 2);
        assertEquals(1, r.start);
        assertEquals(2, r.end);
    }

    @Test
    public void testIntersects() throws Exception {
        Range r = new Range(3,5);
        Range r2 = new Range(3, 3);
        Range r3 = new Range(3, 10);
        Range r4 = new Range(1, 3);
        Range r5 = new Range(1, 2);
        Range r6 = new Range(1, 10);
        assertTrue(r.intersects(r2));
        assertTrue(r.intersects(r3));
        assertTrue(r.intersects(r4));
        assertFalse(r.intersects(r5));
        assertTrue(r.intersects(r6));
    }

    @Test
    public void testContains() {
        Range r = new Range(3,5);
        assertTrue(r.contains(3));
        assertFalse(r.contains(10));

        Range r1 = new Range(3,4);
        assertTrue(r.contains(r1));

        Range r2 = new Range(6,7);
        assertFalse(r.contains(r2));

        Range r3 = new Range(1,4);
        assertFalse(r.contains(r3));

        Range r4 = new Range(3,7);
        assertFalse(r.contains(r4));
    }

    @Test
    public void testRemove() {
        Range r = new Range(1,10);
        r.remove(new Range(-5,1));
        assertEquals(2, r.start);
        assertEquals(10, r.end);

        r.remove(new Range(10,15));
        assertEquals(2, r.start);
        assertEquals(9, r.end);

        Range removed = r.remove(new Range(5,6));
        assertEquals(2, r.start);
        assertEquals(4, r.end);
        assertEquals(7, removed.start);
        assertEquals(9, removed.end);

        r.remove(new Range(0,10));
        assertEquals(-1, r.start);
        assertEquals(-1, r.end);
    }

    @Test
    public void testEquals() {
        Range r = new Range(3,5);
        Range r2 = new Range(3,5);
        Range r3 = new Range(4, 4);
        assertTrue(r.equals(r2));
        assertFalse(r.equals(r3));
    }

    @Test
    public void testCoalesece() {
        Range r = new Range(3,5);
        Range r2 = new Range(5,10);
        r.coalesceWith(r2);

        assertEquals(3, r.start);
        assertEquals(10, r.end);
    }

    @Test
    public void testCoaleseceOutside() {
        Range r = new Range(3,5);
        Range r2 = new Range(7,10);
        r.coalesceWith(r2);

        assertEquals(3, r.start);
        assertEquals(10, r.end);
    }
}