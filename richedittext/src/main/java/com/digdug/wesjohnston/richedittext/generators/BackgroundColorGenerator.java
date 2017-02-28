package com.digdug.wesjohnston.richedittext.generators;

import android.content.Context;
import android.text.Editable;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;

import com.digdug.wesjohnston.richedittext.RichEditText;
import com.digdug.wesjohnston.richedittext.range.Range;
import com.digdug.wesjohnston.richedittext.range.RangeList;

public class BackgroundColorGenerator implements SpanGenerator<BackgroundColorSpan, Integer> {

    private final int color;

    public BackgroundColorGenerator(int color) {
        this.color = color;
    }

    @Override
    public Class<BackgroundColorSpan> getClassType() {
        return BackgroundColorSpan.class;
    }

    @Override
    public boolean isSpan(BackgroundColorSpan other) {
        if (color == -1) {
            return true;
        }
        return other.getBackgroundColor() == color;
    }

    @Override
    public BackgroundColorSpan getSpan(Context context) {
        return new BackgroundColorSpan(color);
    }

    @Override
    public Integer getCurrentValue(RichEditText edit) {
        Editable text = edit.getText();
        Range selection = new Range(edit.getSelectionStart(), edit.getSelectionEnd());
        BackgroundColorSpan[] spansFound = text.getSpans(selection.start, selection.end, BackgroundColorSpan.class);

        int color = -1;
        RangeList list = new RangeList();
        for (BackgroundColorSpan span : spansFound) {
            if (color == -1) {
                color = span.getBackgroundColor();
            }

            if (span.getBackgroundColor() == color) {
                list.addRange(new Range(text.getSpanStart(span), text.getSpanEnd(span)));
            }
        }

        if (list.contains(selection)) {
            return spansFound[0].getBackgroundColor();
        }

        return -1;
    }
}
