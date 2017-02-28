package com.digdug.wesjohnston.richedittext.generators;

import android.content.Context;
import android.text.Editable;
import android.text.style.ForegroundColorSpan;

import com.digdug.wesjohnston.richedittext.RichEditText;
import com.digdug.wesjohnston.richedittext.range.Range;
import com.digdug.wesjohnston.richedittext.range.RangeList;

public class ForegroundColorGenerator implements SpanGenerator<ForegroundColorSpan, Integer> {
    private final int color;

    public ForegroundColorGenerator(int color) {
        this.color = color;
    }

    @Override
    public Class getClassType() {
        return ForegroundColorSpan.class;
    }

    @Override
    public boolean isSpan(ForegroundColorSpan other) {
        if (color == -1) {
            return true;
        }
        return other.getForegroundColor() == color;
    }

    @Override
    public ForegroundColorSpan getSpan(Context context) {
        return new ForegroundColorSpan(color);
    }

    @Override
    public Integer getCurrentValue(RichEditText edit) {
        Editable text = edit.getText();
        Range selection = new Range(edit.getSelectionStart(), edit.getSelectionEnd());
        ForegroundColorSpan[] spansFound = text.getSpans(selection.start, selection.end, ForegroundColorSpan.class);

        int color = -1;
        RangeList list = new RangeList();
        for (ForegroundColorSpan span : spansFound) {
            if (color == -1) {
                color = span.getForegroundColor();
            }

            if (span.getForegroundColor() == color) {
                list.addRange(new Range(text.getSpanStart(span), text.getSpanEnd(span)));
            }
        }

        if (list.contains(selection)) {
            return spansFound[0].getForegroundColor();
        }

        return -1;
    }
}
