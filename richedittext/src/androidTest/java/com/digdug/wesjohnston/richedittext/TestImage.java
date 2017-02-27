package com.digdug.wesjohnston.richedittext;

import android.content.Context;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.text.style.ImageSpan;
import android.view.KeyEvent;

import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertEquals;

/**
 * Created by Wes Johnston on 2/24/2017.
 */

public class TestImage extends StyleTest<ImageSpan> {
    @Override
    boolean verifyStyle(RichEditText edit) {
        return edit.getImage() != null;
    }

    @Override
    protected void verifyStyleType(ImageSpan style) {
        assertTrue(true);
    }

    @Override
    protected Class<ImageSpan> getClassType() {
        return ImageSpan.class;
    }

    @Override
    protected void toggleStyle(RichEditText edit) {
        edit.addImage(android.R.mipmap.sym_def_app_icon);
    }

    // Test that toggling a style works
    @Test
    @Override
    public void testStyle() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 5, 0, 1);
        assertEquals(true, verifyStyle(edit));
        //testToggle(edit, null, null);
        //assertEquals(false, verifyStyle(edit));
    }

    @Test
    @Override
    public void testTwoAreas() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 4, 0, 1);
        assertEquals(true, verifyStyle(edit));
        testToggle(edit, 6, 8, 0, 1, 6, 7);
        assertEquals(true, verifyStyle(edit));
        testToggle(edit, 2, 5, 0, 1, 4, 5, 2, 3);
        assertEquals(true, verifyStyle(edit));
    }

    @Test
    @Override
    public void testShortenStart() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 12, 0, 1);
        assertEquals(true, verifyStyle(edit));
        // testToggle(edit, 0, 5, 6, 12);
        // assertEquals(false, verifyStyle(edit));
    }

    @Test
    @Override
    public void testTypeFront() {
        if (!looperPrepared) {
            Looper.prepare();
            looperPrepared = true;
        }

        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello world");

        testToggle(edit, 5, 7, 5, 6);
        edit.setSelection(5);
        //assertEquals(true, verifyStyle(edit));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_1));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_1));
        //assertEquals("", Html.toHtml(edit.getText()));
        verifySpans(edit, 6, 7);
    }

    @Test
    @Override
    public void testShortenMiddle() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 12, 0, 1);
        assertEquals(true, verifyStyle(edit));
        //testToggle(edit, 3, 6, 0, 2, 7, 12);
        //assertEquals(false, verifyStyle(edit));
    }

    @Test
    @Override
    public void testShortenEnd() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 12, 0, 1);
        assertEquals(true, verifyStyle(edit));
        //testToggle(edit, 6, 12, 0, 5);
        //assertEquals(false, verifyStyle(edit));
    }

    @Test
    @Override
    public void testTypeBack() {
        if (!looperPrepared) {
            Looper.prepare();
            looperPrepared = true;
        }

        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello world");

        testToggle(edit, 5, 7, 5, 6);
        edit.setSelection(6);
        assertEquals(true, verifyStyle(edit));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_1));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_1));
        verifySpans(edit, 5, 6);
    }

    @Override
    @Test
    public void testToggleMiddle() {
        if (!looperPrepared) {
            Looper.prepare();
            looperPrepared = true;
        }

        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 12, 0, 1);
        assertEquals(true, verifyStyle(edit));

        testToggle(edit, 1, null, 0, 1, 1, 2);
        // assertEquals(false, verifyStyle(edit));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_1));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_1));
        verifySpans(edit, 0, 1, 1, 2);
    }

    @Test
    @Override
    public void testExtendStart() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 0, 5, 0, 1);
        assertEquals(true, verifyStyle(edit));
        testToggle(edit, 0, 8, 0, 1);
        assertEquals(true, verifyStyle(edit));
    }

    @Test
    @Override
    public void testExtendEnd() {
        Context appContext = InstrumentationRegistry.getTargetContext();
        RichEditText edit = new RichEditText(appContext);
        edit.setText("Hello World!");

        testToggle(edit, 6, 12, 6, 7);
        assertEquals(true, verifyStyle(edit));
        testToggle(edit, 0, 7, 0, 1);
        assertEquals(true, verifyStyle(edit));
    }

    @Test
    @Override
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
        verifySpans(edit, 5, 6);
        assertEquals(true, verifyStyle(edit));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_1));
        edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_1));
        verifySpans(edit, 5, 6);

        toggleStyle(edit);
        //assertEquals(false, verifyStyle(edit));
        //edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_2));
        //edit.dispatchKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_2));
        verifySpans(edit, 5, 6, 7, 8);
    }
}
