package com.digdug.wesjohnston.richedittext;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.Editable;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.KeyEvent;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TestTextSize extends NonToggleableStyleTest<AbsoluteSizeSpan> {
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
        // size = size == 30 ? 10 : 30;
        edit.setTextSize(size);
    }
}
