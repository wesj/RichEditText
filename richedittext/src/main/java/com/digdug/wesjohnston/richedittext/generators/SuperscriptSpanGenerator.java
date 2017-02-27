package com.digdug.wesjohnston.richedittext.generators;

import android.content.Context;
import android.text.style.SuperscriptSpan;

import com.digdug.wesjohnston.richedittext.RichEditText;

public class SuperscriptSpanGenerator implements SpanGenerator<SuperscriptSpan, Boolean> {

    @Override
    public Class<SuperscriptSpan> getClassType() {
        return SuperscriptSpan.class;
    }

    @Override
    public boolean isSpan(SuperscriptSpan other) {
        return true;
    }

    @Override
    public SuperscriptSpan getSpan(Context context) {
        return new SuperscriptSpan();
    }

    @Override
    public Boolean getCurrentValue(RichEditText edit) {
        return false;
    }
}
