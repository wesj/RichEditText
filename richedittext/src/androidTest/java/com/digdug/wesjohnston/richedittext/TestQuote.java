package com.digdug.wesjohnston.richedittext;

import android.support.test.runner.AndroidJUnit4;
import android.text.style.QuoteSpan;

import org.junit.runner.RunWith;

import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
public class TestQuote extends LineAffectingStyleTest<QuoteSpan> {
    @Override
    boolean verifyStyle(RichEditText edit) {
        return edit.isQuote();
    }

    @Override
    protected void verifyStyleType(QuoteSpan style) {
        assertTrue(true);
    }

    @Override
    protected Class<QuoteSpan> getClassType() {
        return QuoteSpan.class;
    }

    @Override
    protected void toggleStyle(RichEditText edit) {
        edit.toggleQuotes();
    }
}
