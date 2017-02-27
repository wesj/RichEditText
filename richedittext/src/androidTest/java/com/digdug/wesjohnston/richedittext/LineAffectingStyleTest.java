package com.digdug.wesjohnston.richedittext;

import android.content.Context;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.text.Layout;
import android.text.style.AlignmentSpan;
import android.view.KeyEvent;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public abstract class LineAffectingStyleTest<T> extends StyleTest<T> {
    @Override
    @Test
    public void testStyle() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 5, 0, 12);
        assertEquals(true, verifyStyle(edit));

        testToggle(edit, null, null, 0, 12);
        assertEquals(true, verifyStyle(edit));
    }

    @Override
    @Test
    public void testTwoAreas() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 4, 0, 12);
        assertEquals(true, verifyStyle(edit));

        testToggle(edit, 7, 12, 0, 12);
        assertEquals(true, verifyStyle(edit));

        testToggle(edit, 3, 8, 0, 12);
        assertEquals(true, verifyStyle(edit));
    }

    @Override
    @Test
    public void testExtendStart() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 5, 0, 12);
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

        testToggle(edit, 6, 12, 0, 12);
        assertEquals(true, verifyStyle(edit));

        testToggle(edit, 0, 12, 0, 12);
        assertEquals(true, verifyStyle(edit));
    }

    @Override
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
        verifySpans(edit, 0, 11);
        assertEquals(true, verifyStyle(edit));

        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_1));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_1));
        verifySpans(edit, 0, 12);

        toggleStyle(edit);
        //assertEquals(false, verifyStyle(edit));
        //edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_2));
        //edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_2));
        verifySpans(edit, 0, 12);
    }

    @Test
    @Override
    public void testShortenStart() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 12, 0, 12);
        assertEquals(true, verifyStyle(edit));
        testToggle(edit, 0, 5, 0, 12);
        assertEquals(true, verifyStyle(edit));
    }

    @Override
    @Test
    public void testShortenEnd() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 12, 0, 12);
        assertEquals(true, verifyStyle(edit));
        testToggle(edit, 6, 12, 0, 12);
        assertEquals(true, verifyStyle(edit));
    }

    @Override
    @Test
    public void testShortenMiddle() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 12, 0, 12);
        assertEquals(true, verifyStyle(edit));
        testToggle(edit, 3, 6, 0, 12);
        assertEquals(true, verifyStyle(edit));
    }

    @Override
    @Test
    public void testTypeFront() {
        if (!looperPrepared) {
            Looper.prepare();
            looperPrepared = true;
        }

        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello world");

        testToggle(edit, 5, 7, 0, 11);
        edit.setSelection(5);
        //assertEquals(true, verifyStyle(edit));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_1));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_1));
        //assertEquals("", Html.toHtml(edit.getText()));
        verifySpans(edit, 0, 12);
    }

    @Override
    @Test
    public void testTypeBack() {
        if (!looperPrepared) {
            Looper.prepare();
            looperPrepared = true;
        }

        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello world");

        testToggle(edit, 5, 7, 0, 11);
        edit.setSelection(7);
        assertEquals(true, verifyStyle(edit));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_1));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_1));
        verifySpans(edit, 0, 12);
    }

}
