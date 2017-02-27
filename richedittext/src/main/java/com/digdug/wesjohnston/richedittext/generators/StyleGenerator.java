package com.digdug.wesjohnston.richedittext.generators;

import android.content.Context;
import android.text.Editable;
import android.text.style.StyleSpan;

import com.digdug.wesjohnston.richedittext.range.Range;
import com.digdug.wesjohnston.richedittext.range.RangeList;
import com.digdug.wesjohnston.richedittext.RichEditText;

public class StyleGenerator implements SpanGenerator<StyleSpan, Boolean> {
    private int style = 0;

    public StyleGenerator(int style) {
        this.style = style;
    }

    @Override
    public Class<StyleSpan> getClassType() {
        return StyleSpan.class;
    }

    @Override
    public boolean isSpan(StyleSpan other) {
        return other.getStyle() == style;
    }

    @Override
    public StyleSpan getSpan(Context context) {
        return new StyleSpan(style);
    }

    @Override
    public Boolean getCurrentValue(RichEditText edit) {
        Range selection = new Range(edit.getSelectionStart(), edit.getSelectionEnd());

        Editable text = edit.getText();
        StyleSpan[] styles = text.getSpans(selection.start, selection.end, StyleSpan.class);

        RangeList ranges = new RangeList();
        for (StyleSpan style : styles) {
            if (style.getStyle() == this.style) {
                Range spanRange = new Range(text.getSpanStart(style), text.getSpanEnd(style));
                ranges.addRange(spanRange);
            }
        }

        return ranges.size() == 1;
    }
}
