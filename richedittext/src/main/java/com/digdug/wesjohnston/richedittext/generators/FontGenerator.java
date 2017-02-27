package com.digdug.wesjohnston.richedittext.generators;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Parcel;
import android.text.Editable;
import android.text.TextPaint;
import android.text.style.TypefaceSpan;

import com.digdug.wesjohnston.richedittext.RichEditText;

public class FontGenerator implements SpanGenerator<TypefaceSpan, String> {

    private static final String LOGTAG = FontGenerator.class.getSimpleName();
    private final String family;

    public FontGenerator(String family) {
        this.family = family;
    }

    @Override
    public Class<TypefaceSpan> getClassType() {
        return TypefaceSpan.class;
    }

    @Override
    public boolean isSpan(TypefaceSpan other) {
        return other.getFamily().equals(family);
    }

    @SuppressLint("ParcelCreator")
    public static class FontSpan extends TypefaceSpan {
        public FontSpan(String family) {
            super(family);
        }

        public FontSpan(Parcel src) {
            super(src);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            apply(ds, getFamily());
        }

        @Override
        public void updateMeasureState(TextPaint paint) {
            apply(paint, getFamily());
        }

        private static void apply(Paint paint, String family) {
            int oldStyle;

            Typeface old = paint.getTypeface();
            if (old == null) {
                oldStyle = 0;
            } else {
                oldStyle = old.getStyle();
            }

            Typeface tf = Typeface.createFromFile(family);
            int fake = oldStyle & ~tf.getStyle();

            if ((fake & Typeface.BOLD) != 0) {
                paint.setFakeBoldText(true);
            }

            if ((fake & Typeface.ITALIC) != 0) {
                paint.setTextSkewX(-0.25f);
            }

            paint.setTypeface(tf);
        }
    }

    @Override
    public TypefaceSpan getSpan(Context context) {
        if (family.startsWith("/")) {
            return new FontSpan(family);
        }
        return new TypefaceSpan(family);
    }

    public String getCurrentValue(RichEditText edit) {
        Editable text = edit.getText();
        TypefaceSpan[] spansFound = text.getSpans(edit.getSelectionStart(), edit.getSelectionEnd(), TypefaceSpan.class);
        if (spansFound.length == 1) {
            return spansFound[0].getFamily();
        }
        return null;
    }
}
