package com.digdug.wesjohnston.richedittext.generators;

import android.content.Context;
import android.text.style.StrikethroughSpan;

import com.digdug.wesjohnston.richedittext.RichEditText;

public class StrikethroughGenerator implements SpanGenerator<StrikethroughSpan, Boolean> {
    @Override
    public Class getClassType() {
        return StrikethroughSpan.class;
    }

    @Override
    public boolean isSpan(StrikethroughSpan other) {
        return true;
    }

    @Override
    public StrikethroughSpan getSpan(Context context) {
        return new StrikethroughSpan();
    }

    @Override
    public Boolean getCurrentValue(RichEditText edit) {
        return null;
    }
}
