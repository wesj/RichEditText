package com.digdug.wesjohnston.richedittext.generators;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.text.Editable;
import android.text.style.ForegroundColorSpan;
import android.text.style.TextAppearanceSpan;

import com.digdug.wesjohnston.richedittext.RichEditText;
import com.digdug.wesjohnston.richedittext.range.Range;
import com.digdug.wesjohnston.richedittext.range.RangeList;

public class TextAppearanceGenerator implements SpanGenerator<TextAppearanceSpan, Integer> {
    private static final String LOGTAG = TextAppearanceGenerator.class.getSimpleName();
    private final int resourceId;
    private String family;
    private ColorStateList linkTextColor;
    private ColorStateList textColor;
    private int textSize;
    private int textStyle;

    public TextAppearanceGenerator(Context context, int resourceId) {
        TextAppearanceSpan span = new TextApperanceResourceSpan(context, resourceId);
        this.resourceId = resourceId;
        family = span.getFamily();
        linkTextColor = span.getLinkTextColor();
        textColor = span.getTextColor();
        textSize = span.getTextSize();
        textStyle = span.getTextStyle();
    }

    @Override
    public Class getClassType() {
        return TextAppearanceSpan.class;
    }

    @Override
    public boolean isSpan(TextAppearanceSpan span) {
        if (resourceId == -1) {
            return true;
        }

        String spanFam = span.getFamily();
        if (spanFam != null) {
            return spanFam.equals(family) &&
                span.getLinkTextColor().equals(linkTextColor) &&
                span.getTextColor().equals(textColor) &&
                span.getTextSize() == textSize &&
                span.getTextStyle() == textStyle;
        }
        return false;
    }

    @Override
    public TextAppearanceSpan getSpan(Context context) {
        if (this.resourceId > -1) {
            return new TextApperanceResourceSpan(context, this.resourceId);
        }
        return new TextAppearanceSpan(family, textStyle, textSize, textColor, linkTextColor);
    }

    @Override
    public Integer getCurrentValue(RichEditText edit) {
        Editable text = edit.getText();
        Range selection = new Range(edit.getSelectionStart(), edit.getSelectionEnd());
        TextAppearanceSpan[] spansFound = text.getSpans(selection.start, selection.end, TextAppearanceSpan.class);

        TextAppearanceSpan defaultSpan = null;
        RangeList list = new RangeList();
        for (TextAppearanceSpan span : spansFound) {
            if (defaultSpan == null) {
                defaultSpan = span;
            }

            if (span.equals(defaultSpan)) {
                list.addRange(new Range(text.getSpanStart(span), text.getSpanEnd(span)));
            }
        }

        if (list.contains(selection)) {
            for (TextAppearanceSpan span : spansFound) {
                if (span instanceof TextApperanceResourceSpan) {
                    return ((TextApperanceResourceSpan) span).getResource();
                }
            }

            // XXX - I don't know how to get the resource out of this :(
            // For now we just return 1 to say "Yes, the entire selection has the same textApperance"
            return 1;
        }

        // There is no, or multiple appearances in this span.
        return -1;
    }

    @SuppressLint("ParcelCreator")
    public static class TextApperanceResourceSpan extends TextAppearanceSpan {
        private int resource = -1;

        public TextApperanceResourceSpan(Context context, int appearance) {
            super(context, appearance);
            resource = appearance;
        }

        public TextApperanceResourceSpan(Context context, int appearance, int colorList) {
            super(context, appearance, colorList);
            resource = appearance;
        }

        public int getResource() {
            return resource;
        }
    }
}
