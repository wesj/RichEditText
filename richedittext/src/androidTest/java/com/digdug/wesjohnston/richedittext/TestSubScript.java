package com.digdug.wesjohnston.richedittext;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.Editable;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.view.KeyEvent;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TestSubScript extends StyleTest<SubscriptSpan> {
    @Override
    boolean verifyStyle(RichEditText edit) {
        return edit.isSubscript();
    }

    @Override
    protected void verifyStyleType(SubscriptSpan style) {
        assertEquals(SubscriptSpan.class, style.getClass());
    }

    @Override
    protected Class<SubscriptSpan> getClassType() {
        return SubscriptSpan.class;
    }

    @Override
    protected void toggleStyle(RichEditText edit) {
        edit.toggleSubScript();
    }
}
