package com.digdug.wesjohnston.richedittext.range;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RangeList implements Iterable<Range> {
    private List<Range> ranges = new ArrayList<>();
    private int start = Integer.MAX_VALUE;
    private int end = Integer.MIN_VALUE;

    public void addRange(Range range) {
        if (range.start < start) {
            start = range.start;
        }

        if (range.end > end) {
            end = range.end;
        }

        for (Range r : ranges) {
            if (r.equals(range)) {
                continue;
            }

            if (r.intersects(range)) {
                r.coalesceWith(range);
                range = r;
            }
        }

        ranges.add(range);
        Collections.sort(ranges);
    }

    public int size() {
        return ranges.size();
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    public boolean contains(Range selection) {
        for (Range r : ranges) {
            if (r.contains(selection)) {
                return true;
            }
        }
        return false;
    }

    public boolean intersects(Range selection) {
        for (Range r : ranges) {
            if (r.intersects(selection)) {
                return true;
            }
        }
        return false;
    }

    public void remove(Range s) {
        for (Range r : ranges) {
            if (r.intersects(s)) {
                boolean updateStart = false;
                boolean updateEnd = false;
                if (r.start == start) {
                    updateStart = true;
                }
                if (r.end == end) {
                    updateEnd = true;
                }
                Range newRange = r.remove(s);
                if (newRange != null) {
                    // Avoid using addRange here, since we don't want to coalesce.
                    ranges.add(newRange);
                    if (newRange.end > end) {
                        end = newRange.end;
                    }
                    Collections.sort(ranges);
                } else {
                    if (r.start == -1 && r.end == -1) {
                        ranges.remove(r);
                    }

                    if (updateStart) {
                        start = r.start;
                    }

                    if (updateEnd) {
                        end = r.end;
                    }
                }
            }
        }
    }

    public String toString() {
        return ranges.toString();
    }

    @Override
    public Iterator<Range> iterator() {
        return ranges.iterator();
    }
}
