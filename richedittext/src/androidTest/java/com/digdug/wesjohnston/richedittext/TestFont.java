package com.digdug.wesjohnston.richedittext;

import android.text.style.TypefaceSpan;

import static org.junit.Assert.assertEquals;

public class TestFont extends StyleTest<TypefaceSpan> {
    @Override
    boolean verifyStyle(RichEditText edit) {
        String font = edit.getFont();
        if (font != null) {
            return font.equals("serif");
        }
        return false;
    }

    @Override
    protected void verifyStyleType(TypefaceSpan style) {
        assertEquals(TypefaceSpan.class, style.getClass());
    }

    @Override
    protected Class<TypefaceSpan> getClassType() {
        return TypefaceSpan.class;
    }

    @Override
    protected void toggleStyle(RichEditText edit) {
        if (verifyStyle(edit)) {
            edit.clearFont();
        } else {
            edit.setFont("serif");
        }
    }
}
