package com.digdug.wesjohnston.richedittext.generators;

import android.content.Context;
import android.text.Editable;
import android.text.style.ForegroundColorSpan;

import com.digdug.wesjohnston.richedittext.RichEditText;

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
        ForegroundColorSpan span = (ForegroundColorSpan) other;
        return span.getForegroundColor() == color;
    }

    @Override
    public ForegroundColorSpan getSpan(Context context) {
        return new ForegroundColorSpan(color);
    }

    @Override
    public Integer getCurrentValue(RichEditText edit) {
        Editable text = edit.getText();
        ForegroundColorSpan[] spansFound = text.getSpans(edit.getSelectionStart(), edit.getSelectionEnd(), ForegroundColorSpan.class);
        if (spansFound.length > 0) {
            return spansFound[0].getForegroundColor();
        }
        return -1;
    }
}
