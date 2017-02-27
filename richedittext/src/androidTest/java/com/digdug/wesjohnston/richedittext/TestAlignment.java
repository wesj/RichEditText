package com.digdug.wesjohnston.richedittext;

import android.support.test.runner.AndroidJUnit4;
import android.text.Layout;
import android.text.style.AlignmentSpan;

import org.junit.Before;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class TestAlignment extends LineAffectingStyleTest<AlignmentSpan> {
    Layout.Alignment expected = Layout.Alignment.ALIGN_CENTER;
    @Override
    boolean verifyStyle(RichEditText edit) {
        return edit.getAlignment() == expected;
    }

    @Override
    protected void verifyStyleType(AlignmentSpan style) {
        assertEquals(expected, style.getAlignment());
    }

    @Override
    protected Class<AlignmentSpan> getClassType() {
        return AlignmentSpan.class;
    }

    @Override
    protected void toggleStyle(RichEditText edit) {
        if (edit.getAlignment() == Layout.Alignment.ALIGN_NORMAL) {
            edit.setAlignment(Layout.Alignment.ALIGN_CENTER);
            expected = Layout.Alignment.ALIGN_CENTER;
        } else {
            edit.setAlignment(Layout.Alignment.ALIGN_NORMAL);
            expected = Layout.Alignment.ALIGN_NORMAL;
        }
    }

    @Before
    public void resetExpected() {
        expected = Layout.Alignment.ALIGN_CENTER;
    }
}
