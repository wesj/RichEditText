package com.digdug.wesjohnston.richedittext;

import android.support.test.runner.AndroidJUnit4;
import android.text.style.BackgroundColorSpan;
import android.text.style.URLSpan;

import org.junit.runner.RunWith;

import java.net.MalformedURLException;
import java.net.URL;

import static android.graphics.Color.RED;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TestURL extends StyleTest<URLSpan> {
    URL url = new URL("http://www.google.com");

    public TestURL() throws MalformedURLException {
    }

    @Override
    boolean verifyStyle(RichEditText edit) {
        URL url = edit.getUrl();
        return this.url.equals(url);
    }

    @Override
    protected void verifyStyleType(URLSpan style) {
        assertTrue(true);
    }

    @Override
    protected Class<URLSpan> getClassType() {
        return URLSpan.class;
    }

    @Override
    protected void toggleStyle(RichEditText edit) {
        if (edit.getUrl() != null) {
            edit.clearUrl();
        } else {
            edit.setUrl(url);
        }
    }
}
