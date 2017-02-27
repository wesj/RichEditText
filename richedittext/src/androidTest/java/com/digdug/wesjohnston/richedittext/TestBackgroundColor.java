package com.digdug.wesjohnston.richedittext;

import android.support.test.runner.AndroidJUnit4;
import android.text.style.BackgroundColorSpan;

import org.junit.runner.RunWith;

import static android.graphics.Color.RED;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TestBackgroundColor extends StyleTest<BackgroundColorSpan> {
    @Override
    boolean verifyStyle(RichEditText edit) {
        return edit.getBackgroundColor() == RED;
    }

    @Override
    protected void verifyStyleType(BackgroundColorSpan style) {
        assertTrue(true);
    }

    @Override
    protected Class<BackgroundColorSpan> getClassType() {
        return BackgroundColorSpan.class;
    }

    @Override
    protected void toggleStyle(RichEditText edit) {
        edit.setBackgroundColor(RED);
    }
}
