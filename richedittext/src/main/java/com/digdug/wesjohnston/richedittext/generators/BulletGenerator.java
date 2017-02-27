package com.digdug.wesjohnston.richedittext.generators;

import android.content.Context;
import android.text.Editable;
import android.text.style.BulletSpan;

import com.digdug.wesjohnston.richedittext.RichEditText;

public class BulletGenerator implements SpanGenerator<BulletSpan, Boolean> {
    @Override
    public Class<BulletSpan> getClassType() {
        return BulletSpan.class;
    }

    @Override
    public boolean isSpan(BulletSpan other) {
        return true;
    }

    @Override
    public BulletSpan getSpan(Context context) {
        return new BulletSpan();
    }

    @Override
    public Boolean getCurrentValue(RichEditText edit) {
        Editable text = edit.getText();
        BulletSpan[] spansFound = text.getSpans(edit.getSelectionStart(), edit.getSelectionEnd(), BulletSpan.class);
        if (spansFound.length > 0) {
            return true;
        }
        return false;
    }
}
