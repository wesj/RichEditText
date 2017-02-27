package com.digdug.wesjohnston.richedittext.generators;

import android.content.Context;

import com.digdug.wesjohnston.richedittext.RichEditText;

public abstract interface SpanGenerator<T,V> {
    Class<T> getClassType();

    boolean isSpan(T other);

    T getSpan(Context context);

    V getCurrentValue(RichEditText edit);
}
