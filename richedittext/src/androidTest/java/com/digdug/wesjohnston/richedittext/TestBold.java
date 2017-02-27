package com.digdug.wesjohnston.richedittext;

import android.graphics.Typeface;
import android.support.test.runner.AndroidJUnit4;
import android.text.style.StyleSpan;

import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TestBold extends StyleTest<StyleSpan> {
    @Override
    boolean verifyStyle(RichEditText edit) {
        return edit.isBold();
    }

    @Override
    protected void verifyStyleType(StyleSpan style) {
        assertEquals(Typeface.BOLD, ((StyleSpan) style).getStyle());
    }

    @Override
    protected Class<StyleSpan> getClassType() {
        return StyleSpan.class;
    }

    @Override
    protected void toggleStyle(RichEditText edit) {
        edit.toggleBold();
    }
}
