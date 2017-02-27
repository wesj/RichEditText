package com.digdug.wesjohnston.richedittext;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.Editable;
import android.text.style.StyleSpan;
import android.view.KeyEvent;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TestItalics extends StyleTest<StyleSpan> {
    @Override
    boolean verifyStyle(RichEditText edit) {
        return edit.isItalics();
    }

    @Override
    protected void verifyStyleType(StyleSpan style) {
        assertEquals(Typeface.ITALIC, ((StyleSpan) style).getStyle());
    }

    @Override
    protected Class<StyleSpan> getClassType() {
        return StyleSpan.class;
    }

    @Override
    protected void toggleStyle(RichEditText edit) {
        edit.toggleItalics();
    }
}
