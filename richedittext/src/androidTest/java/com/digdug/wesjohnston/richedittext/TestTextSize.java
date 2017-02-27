package com.digdug.wesjohnston.richedittext;

import android.graphics.Typeface;
import android.support.test.runner.AndroidJUnit4;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;

import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TestTextSize extends StyleTest<AbsoluteSizeSpan> {
    private int size = 30;

    @Override
    boolean verifyStyle(RichEditText edit) {
        return edit.getTextSize() == size;
    }

    @Override
    protected void verifyStyleType(AbsoluteSizeSpan style) {
        assertEquals(size, style.getSize());
    }

    @Override
    protected Class<AbsoluteSizeSpan> getClassType() {
        return AbsoluteSizeSpan.class;
    }

    @Override
    protected void toggleStyle(RichEditText edit) {
        size = size == 30 ? 10 : 30;
        edit.setTextSize(size);
    }
}
