package com.digdug.wesjohnston.richedittext.generators;

import android.content.Context;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.Spanned;
import android.text.style.AlignmentSpan;
import android.util.Log;
import android.widget.EditText;

import com.digdug.wesjohnston.richedittext.RichEditText;
import com.digdug.wesjohnston.richedittext.range.Range;

/**
 * Created by Wes Johnston on 2/23/2017.
 */

public class AlignmentGenerator implements SpanGenerator<AlignmentSpan,Layout.Alignment> {
    private static final String LOGTAG = AlignmentGenerator.class.getSimpleName();
    private final Layout.Alignment alignment;

    public AlignmentGenerator(Layout.Alignment alignment) {
        this.alignment = alignment;
    }
    @Override
    public Class<AlignmentSpan> getClassType() {
        return AlignmentSpan.class;
    }

    @Override
    public boolean isSpan(AlignmentSpan other) {
        return other.getAlignment() == alignment;
    }

    @Override
    public AlignmentSpan getSpan(Context context) {
        return new AlignmentSpan.Standard(alignment);
    }

    @Override
    public Layout.Alignment getCurrentValue(RichEditText edit) {
        Editable text = edit.getText();
        AlignmentSpan[] spansFound = text.getSpans(edit.getSelectionStart(), edit.getSelectionEnd(), AlignmentSpan.class);
        if (spansFound.length > 0) {
            return spansFound[0].getAlignment();
        }
        return Layout.Alignment.ALIGN_NORMAL;
    }

    public Range getCurrentCursorLine(EditText editText) {
        Range range = new Range(0, editText.length());
        Layout layout = editText.getLayout();
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
        Log.i(LOGTAG, "Apply " + r);
        edit.getText().setSpan(getSpan(edit.getContext()), r.start, r.end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
    }
}
