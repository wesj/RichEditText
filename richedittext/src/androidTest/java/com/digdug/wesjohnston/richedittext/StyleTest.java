package com.digdug.wesjohnston.richedittext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.Editable;
import android.view.KeyEvent;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@SuppressLint("SetTextI18n")
@RunWith(AndroidJUnit4.class)
public abstract class StyleTest<T> {
    abstract boolean verifyStyle(RichEditText edit);

    void testToggle(RichEditText edit, Integer start, Integer end, Integer... spans) {
        if (start != null) {
            if (end != null) {
                edit.setSelection(start, end);
            } else {
                edit.setSelection(start);
            }
        }

        toggleStyle(edit);

        verifySpans(edit, spans);
    }

    protected abstract void verifyStyleType(T style);

    protected abstract Class<T> getClassType();

    protected abstract void toggleStyle(RichEditText edit);

    // Test that toggling a style works
    @Test
    public void testStyle() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 5, 0, 5);
        assertTrue(verifyStyle(edit));
        testToggle(edit, null, null);
        assertFalse(verifyStyle(edit));
    }

    @Test
    public void testExtendStart() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 5, 0, 5);
        assertTrue(verifyStyle(edit));
        testToggle(edit, 0, 12, 0, 12);
        assertTrue(verifyStyle(edit));
    }

    @Test
    public void testExtendEnd() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 6, 12, 6, 12);
        assertTrue(verifyStyle(edit));
        testToggle(edit, 0, 12, 0, 12);
        assertTrue(verifyStyle(edit));
    }

    @Test
    public void testTwoAreas() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 4, 0, 4);
        assertTrue(verifyStyle(edit));
        testToggle(edit, 7, 12, 0, 4, 7, 12);
        assertTrue(verifyStyle(edit));
        testToggle(edit, 3, 8, 0, 12);
        assertTrue(verifyStyle(edit));
    }

    @Test
    public void testShortenStart() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 12, 0, 12);
        assertTrue(verifyStyle(edit));
        testToggle(edit, 0, 5, 5, 12);
        assertFalse(verifyStyle(edit));
    }

    @Test
    public void testShortenEnd() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 12, 0, 12);
        assertTrue(verifyStyle(edit));
        testToggle(edit, 6, 12, 0, 6);
        assertFalse(verifyStyle(edit));
    }

    @Test
    public void testShortenMiddle() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 12, 0, 12);
        assertTrue(verifyStyle(edit));
        testToggle(edit, 3, 6, 0, 3, 6, 12);
        assertFalse(verifyStyle(edit));
    }

    static boolean looperPrepared = false;

    @Test
    public void testNoSelection() {
        if (!looperPrepared) {
            Looper.prepare();
            looperPrepared = true;
        }

        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello world");

        edit.setSelection(5);
        toggleStyle(edit);
        verifySpans(edit, 5, 5);
        assertTrue(verifyStyle(edit));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_1));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_1));
        verifySpans(edit, 5, 6);

        toggleStyle(edit);
        //assertFalse(verifyStyle(edit));
        //edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_2));
        //edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_2));
        verifySpans(edit, 5, 6);
    }

    void verifySpans(RichEditText edit, Integer... spans) {
        Editable text = edit.getText();
        T[] spansFound = text.getSpans(0, text.length(), getClassType());
        assertEquals(spans.length / 2, spansFound.length);

        int i = 0;
        for (T span : spansFound) {
            int start = text.getSpanStart(span);
            verifyStyleType(span);
            assertEquals(spans[i * 2].intValue(), text.getSpanStart(span));
            assertEquals(spans[i * 2 + 1].intValue(), text.getSpanEnd(span));
            i++;
        }
    }

    @Test
    public void testTypeFront() {
        if (!looperPrepared) {
            Looper.prepare();
            looperPrepared = true;
        }

        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello world");

        testToggle(edit, 5, 7, 5, 7);
        edit.setSelection(5);
        //assertTrue(verifyStyle(edit));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_1));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_1));
        //assertEquals("", Html.toHtml(edit.getText()));
        verifySpans(edit, 5, 8);
    }

    @Test
    public void testToggleMiddle() {
        if (!looperPrepared) {
            Looper.prepare();
            looperPrepared = true;
        }

        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 12, 0, 12);
        assertTrue(verifyStyle(edit));

        testToggle(edit, 5, null, 0, 5, 5, 12);
        // assertFalse(verifyStyle(edit));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_1));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_1));
        verifySpans(edit, 0, 5, 6, 13);
    }

    @Test
    public void testTypeBack() {
        if (!looperPrepared) {
            Looper.prepare();
            looperPrepared = true;
        }

        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello world");

        testToggle(edit, 5, 7, 5, 7);
        edit.setSelection(7);
        assertTrue(verifyStyle(edit));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_1));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_1));
        verifySpans(edit, 5, 8);
    }
}
