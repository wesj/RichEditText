package com.digdug.wesjohnston.richedittext.generators;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Parcel;
import android.text.Editable;
import android.text.TextPaint;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;

import com.digdug.wesjohnston.richedittext.RichEditText;
import com.digdug.wesjohnston.richedittext.range.Range;
import com.digdug.wesjohnston.richedittext.range.RangeList;

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
        if (family == null) {
            return true;
        }
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
        if (family == null) {
            return new TypefaceSpan("");
        }

        if (family.startsWith("/")) {
            return new FontSpan(family);
        }
        return new TypefaceSpan(family);
    }

    @Override
    public String getCurrentValue(RichEditText edit) {
        Editable text = edit.getText();
        Range selection = new Range(edit.getSelectionStart(), edit.getSelectionEnd());
        TypefaceSpan[] spansFound = text.getSpans(selection.start, selection.end, TypefaceSpan.class);

        String family = null;
        RangeList list = new RangeList();
        for (TypefaceSpan span : spansFound) {
            if (family == null) {
                family = span.getFamily();
            }

            if (span.getFamily().equals(family)) {
                list.addRange(new Range(text.getSpanStart(span), text.getSpanEnd(span)));
            }
        }

        if (list.contains(selection)) {
            return spansFound[0].getFamily();
        }

        return null;
    }
}
