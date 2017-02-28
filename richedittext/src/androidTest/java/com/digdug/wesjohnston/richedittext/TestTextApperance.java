package com.digdug.wesjohnston.richedittext;

import android.text.Editable;
import android.text.style.TextAppearanceSpan;

import com.digdug.wesjohnston.richedittext.generators.TextAppearanceGenerator;

import org.w3c.dom.Text;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

public class TestTextApperance extends StyleTest<TextAppearanceSpan> {
    int resource = android.R.style.TextAppearance_DeviceDefault_Large;

    @Override
    boolean verifyStyle(RichEditText edit) {
        return edit.getTextAppearance() == resource;
    }

    @Override
    protected void verifyStyleType(TextAppearanceSpan style) {
        // XXX - We should just check that this interhits from a TextAppearanceSpan
        assertTrue(style instanceof TextAppearanceSpan);
    }

    @Override
    protected Class<TextAppearanceSpan> getClassType() {
        return TextAppearanceSpan.class;
    }

    @Override
    protected void toggleStyle(RichEditText edit) {
        if (verifyStyle(edit)) {
            edit.clearTextAppearance();
        } else {
            edit.setTextAppearance(resource);
        }
    }
}
