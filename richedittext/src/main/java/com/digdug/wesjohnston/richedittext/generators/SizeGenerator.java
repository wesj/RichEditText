package com.digdug.wesjohnston.richedittext.generators;

import android.content.Context;
import android.text.Editable;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.TypefaceSpan;

import com.digdug.wesjohnston.richedittext.RichEditText;

public class SizeGenerator implements SpanGenerator<AbsoluteSizeSpan,Integer> {
    private int size;
    public SizeGenerator(int size) {
        this.size = size;
    }

    @Override
    public Class<AbsoluteSizeSpan> getClassType() {
        return AbsoluteSizeSpan.class;
    }

    @Override
    public boolean isSpan(AbsoluteSizeSpan other) {
        return other.getSize() == size;
    }

    @Override
    public AbsoluteSizeSpan getSpan(Context context) {
        return new AbsoluteSizeSpan(size);
    }

    @Override
    public Integer getCurrentValue(RichEditText edit) {
        Editable text = edit.getText();
        AbsoluteSizeSpan[] spansFound = text.getSpans(edit.getSelectionStart(), edit.getSelectionEnd(), AbsoluteSizeSpan.class);
        if (spansFound.length == 1) {
            return spansFound[0].getSize();
        }
        return null;
    }
}
