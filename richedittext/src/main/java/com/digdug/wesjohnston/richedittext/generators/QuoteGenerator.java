package com.digdug.wesjohnston.richedittext.generators;

import android.content.Context;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import android.text.style.QuoteSpan;
import android.util.Log;
import android.widget.EditText;

import com.digdug.wesjohnston.richedittext.RichEditText;
import com.digdug.wesjohnston.richedittext.range.Range;

/**
 * Created by Wes Johnston on 2/23/2017.
 */

public class QuoteGenerator implements SpanGenerator<QuoteSpan, Boolean> {
    @Override
    public Class<QuoteSpan> getClassType() {
        return QuoteSpan.class;
    }

    @Override
    public boolean isSpan(QuoteSpan other) {
        return true;
    }

    @Override
    public QuoteSpan getSpan(Context context) {
        return new QuoteSpan();
    }

    @Override
    public Boolean getCurrentValue(RichEditText edit) {
        Range range = getCurrentCursorLine(edit);
        Editable text = edit.getText();
        QuoteSpan[] spansFound = text.getSpans(range.start, range.end, QuoteSpan.class);
        return spansFound.length > 0;
    }

    public Range getCurrentCursorLine(EditText editText) {
        Layout layout = editText.getLayout();
        Range range = new Range(0, editText.length());
        if (layout == null) {
            return range;
        }

        int start = Selection.getSelectionStart(editText.getText());
        if (start != -1) {
            int line = layout.getLineForOffset(start);
            range.start = layout.getLineStart(line);
        }

        int end = Selection.getSelectionStart(editText.getText());
        if (end != -1) {
            int line = layout.getLineForOffset(end);
            range.end = layout.getLineEnd(line);
        }

        return range;
    }

    public void apply(RichEditText edit) {
        Range r = getCurrentCursorLine(edit);
        if (r.start == -1 || r.end == -1) {
            return;
        }

        Editable text = edit.getText();
        AlignmentSpan[] spans = text.getSpans(r.start, r.end, AlignmentSpan.class);
        for (AlignmentSpan span : spans) {
            text.removeSpan(span);
        }
        edit.getText().setSpan(getSpan(edit.getContext()), r.start, r.end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    }

    public void remove(RichEditText edit) {
        Range r = getCurrentCursorLine(edit);
        Editable text = edit.getText();
        AlignmentSpan[] spans = text.getSpans(r.start, r.end, AlignmentSpan.class);
        for (AlignmentSpan span : spans) {
            text.removeSpan(span);
        }
    }
}
