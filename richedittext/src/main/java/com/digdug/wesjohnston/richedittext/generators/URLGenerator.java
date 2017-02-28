package com.digdug.wesjohnston.richedittext.generators;

import android.content.Context;
import android.text.Editable;
import android.text.style.ForegroundColorSpan;
import android.text.style.URLSpan;

import com.digdug.wesjohnston.richedittext.RichEditText;
import com.digdug.wesjohnston.richedittext.range.Range;
import com.digdug.wesjohnston.richedittext.range.RangeList;

import java.net.MalformedURLException;
import java.net.URL;

public class URLGenerator implements SpanGenerator<URLSpan, URL> {
    private final URL url;

    public URLGenerator(URL url) {
        this.url = url;
    }

    @Override
    public Class getClassType() {
        return URLSpan.class;
    }

    @Override
    public boolean isSpan(URLSpan other) {
        if (url == null) {
            return true;
        }

        URLSpan span = (URLSpan) other;
        return span.getURL().equals(url.toString());
    }

    @Override
    public URLSpan getSpan(Context context) {
        if (url == null) {
            return new URLSpan("");
        }
        return new URLSpan(url.toString());
    }

    @Override
    public URL getCurrentValue(RichEditText edit) {
        Editable text = edit.getText();
        Range selection = new Range(edit.getSelectionStart(), edit.getSelectionEnd());
        URLSpan[] spansFound = text.getSpans(selection.start, selection.end, URLSpan.class);

        String url = null;
        RangeList list = new RangeList();
        for (URLSpan span : spansFound) {
            if (url == null) {
                url = span.getURL();
            }

            if (url.equals(span.getURL())) {
                list.addRange(new Range(text.getSpanStart(span), text.getSpanEnd(span)));
            }
        }

        if (list.contains(selection)) {
            try {
                return new URL(spansFound[0].getURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
