package com.digdug.wesjohnston.richedittext;

import android.support.test.runner.AndroidJUnit4;
import android.text.style.ForegroundColorSpan;

import org.junit.runner.RunWith;

import static android.graphics.Color.GREEN;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TestForegroundColor extends StyleTest<ForegroundColorSpan> {
    @Override
    boolean verifyStyle(RichEditText edit) {
        return edit.getForegroundColor() == GREEN;
    }

    @Override
    protected void verifyStyleType(ForegroundColorSpan style) {
        assertTrue(true);
    }

    @Override
    protected Class<ForegroundColorSpan> getClassType() {
        return ForegroundColorSpan.class;
    }

    @Override
    protected void toggleStyle(RichEditText edit) {
        edit.setForegroundColor(GREEN);
    }
}
