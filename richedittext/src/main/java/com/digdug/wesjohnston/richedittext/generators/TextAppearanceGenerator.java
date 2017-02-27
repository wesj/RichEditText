package com.digdug.wesjohnston.richedittext.generators;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.style.TextAppearanceSpan;

import com.digdug.wesjohnston.richedittext.RichEditText;

public class TextAppearanceGenerator implements SpanGenerator<TextAppearanceSpan, Integer> {
    private static final String LOGTAG = TextAppearanceGenerator.class.getSimpleName();
    private final int resourceId;
    private String family;
    private ColorStateList linkTextColor;
    private ColorStateList textColor;
    private int textSize;
    private int textStyle;

    public TextAppearanceGenerator(Context context, int resourceId) {
        TextAppearanceSpan span = new TextAppearanceSpan(context, resourceId);
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
        return new TextAppearanceSpan(family, textStyle, textSize, textColor, linkTextColor);
    }

    @Override
    public Integer getCurrentValue(RichEditText edit) {
        return -1;
    }
}
