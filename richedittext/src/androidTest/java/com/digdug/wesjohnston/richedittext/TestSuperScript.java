package com.digdug.wesjohnston.richedittext;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.Editable;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.view.KeyEvent;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TestSuperScript extends StyleTest<SuperscriptSpan> {
    @Override
    boolean verifyStyle(RichEditText edit) {
        return edit.isSuperscript();
    }

    @Override
    protected void verifyStyleType(SuperscriptSpan style) {
        assertEquals(SuperscriptSpan.class, style.getClass());
    }

    @Override
    protected Class<SuperscriptSpan> getClassType() {
        return SuperscriptSpan.class;
    }

    @Override
    protected void toggleStyle(RichEditText edit) {
        edit.toggleSuperScript();
    }
}
