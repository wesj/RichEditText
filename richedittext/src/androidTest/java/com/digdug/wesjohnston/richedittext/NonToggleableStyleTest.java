package com.digdug.wesjohnston.richedittext;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.Editable;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.KeyEvent;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public abstract class NonToggleableStyleTest<T> extends StyleTest<T> {
    // Test that toggling a style works
    @Override
    @Test
    public void testStyle() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 5, 0, 5);
        assertEquals(true, verifyStyle(edit));
    }

    @Override
    @Test
    public void testExtendStart() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 5, 0, 5);
        assertEquals(true, verifyStyle(edit));
        testToggle(edit, 0, 12, 0, 12);
        assertEquals(true, verifyStyle(edit));
    }

    @Override
    @Test
    public void testExtendEnd() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 6, 12, 6, 12);
        assertEquals(true, verifyStyle(edit));
        testToggle(edit, 0, 12, 0, 12);
        assertEquals(true, verifyStyle(edit));
    }

    @Override
    @Test
    public void testTwoAreas() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 4, 0, 4);
        assertEquals(true, verifyStyle(edit));
        testToggle(edit, 7, 12, 0, 4, 7, 12);
        assertEquals(true, verifyStyle(edit));
        testToggle(edit, 3, 8, 0, 12);
        assertEquals(true, verifyStyle(edit));
    }

    @Override
    @Test
    public void testShortenStart() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 12, 0, 12);
        assertEquals(true, verifyStyle(edit));
        //testToggle(edit, 0, 5, 0, 5, 5, 12);
        //assertEquals(false, verifyStyle(edit));
    }

    @Override
    @Test
    public void testShortenEnd() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 12, 0, 12);
        assertEquals(true, verifyStyle(edit));
        //testToggle(edit, 6, 12, 0, 6, 6, 12);
        //assertEquals(false, verifyStyle(edit));
    }

    @Test
    public void testShortenMiddle() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 12, 0, 12);
        assertEquals(true, verifyStyle(edit));
        testToggle(edit, 3, 6, 0, 12);
        assertEquals(true, verifyStyle(edit));
    }

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
        assertEquals(true, verifyStyle(edit));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_1));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_1));
        verifySpans(edit, 5, 6);
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
        //assertEquals(true, verifyStyle(edit));
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
        assertEquals(true, verifyStyle(edit));

        testToggle(edit, 5, null, 0, 12);
        // assertEquals(false, verifyStyle(edit));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_1));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_1));
        verifySpans(edit, 0, 13);
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
        assertEquals(true, verifyStyle(edit));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_1));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_1));
        verifySpans(edit, 5, 8);
    }

}
