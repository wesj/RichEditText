package com.digdug.wesjohnston.richedittext.range;

public class Range implements Comparable<Range> {
    public int start;
    public int end;

    public Range(final int start, final int end) {
        this.start = start;
        this.end = end;
    }

    public boolean intersects(final Range range) {
        return innerIntersects(range, true);
    }

    private boolean innerIntersects(final Range range, final boolean tryReversing) {
        if (range.start >= start && range.start <= end) {
            return true;
        }

        if (range.end <= end && range.end >= start) {
            return true;
        }

        if (tryReversing) {
            return range.innerIntersects(this, false);
        }

        return false;
    }

    public boolean contains(final Range range) {
        return range.start >= start && range.end <= end;
    }

    public boolean contains(final int point) {
        return point >= start && point <= end;
    }

    public boolean equals(Object object) {
        if (object instanceof Range) {
            Range range = (Range) object;
            return range.start == start && range.end == end;
        }
        return true;
    }

    public void coalesceWith(Range range) {
        if (range.start < start) {
            start = range.start;
        }

        if (range.end > end) {
            end = range.end;
        }
    }

    public String toString() {
        return "Range [" + start + " to " + end + "]";
    }

    public Range remove(Range s) {
        // If the range to delete entirely contains this, just delete ourself
        if (s.start <= start && s.end >= end){
            start = -1;
            end = -1;
        } else if (s.start <= start && s.end < end) {
            // If the range to remove starts outside this, but ends inside it, adjust our start
            start = s.end;
        } else if (s.start > start && s.end >= end) {
            // If the range to remove starts inside this, but ends outside it, adjust our end.
            end = s.start;
        } else if (s.start > start && s.end < end) {
            // If this range is entirely inside us, adjust our end, and return a new range for the back half.
            int e = end;
            end = s.start;
            return new Range(s.end, e);
        }
        return null;
    }

    @Override
    public int compareTo(Range o) {
        if (start != o.start) {
            return o.start = start;
        }
        return o.end - end;
    }
}
