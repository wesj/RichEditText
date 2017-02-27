package com.digdug.wesjohnston.richedittext;

import android.text.Editable;
import android.text.style.TextAppearanceSpan;

import com.digdug.wesjohnston.richedittext.generators.TextAppearanceGenerator;

import org.w3c.dom.Text;

import static org.junit.Assert.assertEquals;

public class TestTextApperance extends StyleTest<TextAppearanceSpan> {
    @Override
    boolean verifyStyle(RichEditText edit) {
        Editable text = edit.getText();
        TextAppearanceSpan[] spansFound = text.getSpans(edit.getSelectionStart(), edit.getSelectionEnd(), TextAppearanceSpan.class);

        if (spansFound.length > 0) {
            TextAppearanceGenerator generator = new TextAppearanceGenerator(edit.getContext(), android.R.style.TextAppearance_DeviceDefault_Large);
            return generator.isSpan(spansFound[0]);
        }
        return false;
    }

    @Override
    protected void verifyStyleType(TextAppearanceSpan style) {
        assertEquals(TextAppearanceSpan.class, style.getClass());
    }

    @Override
    protected Class<TextAppearanceSpan> getClassType() {
        return TextAppearanceSpan.class;
    }

    @Override
    protected void toggleStyle(RichEditText edit) {
        edit.setTextAppearance(android.R.style.TextAppearance_DeviceDefault_Large);
    }
}
