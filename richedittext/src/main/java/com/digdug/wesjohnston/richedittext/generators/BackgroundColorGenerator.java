package com.digdug.wesjohnston.richedittext.generators;

import android.content.Context;
import android.text.Editable;
import android.text.style.BackgroundColorSpan;

import com.digdug.wesjohnston.richedittext.RichEditText;

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
        return other.getBackgroundColor() == color;
    }

    @Override
    public BackgroundColorSpan getSpan(Context context) {
        return new BackgroundColorSpan(color);
    }

    @Override
    public Integer getCurrentValue(RichEditText edit) {
        Editable text = edit.getText();
        BackgroundColorSpan[] spansFound = text.getSpans(edit.getSelectionStart(), edit.getSelectionEnd(), BackgroundColorSpan.class);
        if (spansFound.length > 0) {
            return spansFound[0].getBackgroundColor();
        }
        return -1;
    }
}
