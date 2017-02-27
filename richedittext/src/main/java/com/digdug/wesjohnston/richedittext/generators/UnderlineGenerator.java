package com.digdug.wesjohnston.richedittext.generators;

import android.content.Context;
import android.text.style.UnderlineSpan;

import com.digdug.wesjohnston.richedittext.RichEditText;

public class UnderlineGenerator implements SpanGenerator<UnderlineSpan, Boolean> {

    @Override
    public Class<UnderlineSpan> getClassType() {
        return UnderlineSpan.class;
    }

    @Override
    public boolean isSpan(UnderlineSpan other) {
        return true;
    }

    @Override
    public UnderlineSpan getSpan(Context context) {
        return new UnderlineSpan();
    }

    @Override
    public Boolean getCurrentValue(RichEditText edit) {
        return false;
    }
}
