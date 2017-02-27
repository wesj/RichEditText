package com.digdug.wesjohnston.richedittext.generators;

import android.content.Context;
import android.text.style.SubscriptSpan;

import com.digdug.wesjohnston.richedittext.RichEditText;

public class SubscriptSpanGenerator implements SpanGenerator<SubscriptSpan, Boolean> {
    @Override
    public Class<SubscriptSpan> getClassType() {
        return SubscriptSpan.class;
    }

    @Override
    public boolean isSpan(SubscriptSpan other) {
        return true;
    }

    @Override
    public SubscriptSpan getSpan(Context context) {
        return new SubscriptSpan();
    }

    @Override
    public Boolean getCurrentValue(RichEditText edit) {
        return false;
    }
}
