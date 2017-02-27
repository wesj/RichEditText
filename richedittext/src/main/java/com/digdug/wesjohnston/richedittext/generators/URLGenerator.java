package com.digdug.wesjohnston.richedittext.generators;

import android.content.Context;
import android.text.Editable;
import android.text.style.URLSpan;

import com.digdug.wesjohnston.richedittext.RichEditText;

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
        URLSpan span = (URLSpan) other;
        return span.getURL().equals(url.toString());
    }

    @Override
    public URLSpan getSpan(Context context) {
        return new URLSpan(url.toString());
    }

    @Override
    public URL getCurrentValue(RichEditText edit) {
        Editable text = edit.getText();
        URLSpan[] spansFound = text.getSpans(edit.getSelectionStart(), edit.getSelectionEnd(), URLSpan.class);
        if (spansFound.length > 0) {
            try {
                return new URL(spansFound[0].getURL());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
