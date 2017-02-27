package com.digdug.wesjohnston.richedittext;

import android.support.test.runner.AndroidJUnit4;
import android.text.style.BulletSpan;

import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TestBullets extends StyleTest<BulletSpan> {
    @Override
    boolean verifyStyle(RichEditText edit) {
        return edit.isBullets();
    }

    @Override
    protected void verifyStyleType(BulletSpan style) {
        assertTrue(true);
    }

    @Override
    protected Class<BulletSpan> getClassType() {
        return BulletSpan.class;
    }

    @Override
    protected void toggleStyle(RichEditText edit) {
        edit.toggleBullets();
    }
}
