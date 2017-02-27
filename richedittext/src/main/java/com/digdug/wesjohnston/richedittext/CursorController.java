package com.digdug.wesjohnston.richedittext;

import android.view.ViewTreeObserver;

interface CursorController extends ViewTreeObserver.OnTouchModeChangeListener {
    /**
     * Makes the cursor controller visible on screen.
     * See also {@link #hide()}.
     */
    public void show();

    /**
     * Hide the cursor controller from screen.
     * See also {@link #show()}.
     */
    public void hide();

    /**
     * Called when the view is detached from window. Perform house keeping task, such as
     * stopping Runnable thread that would otherwise keep a reference on the context, thus
     * preventing the activity from being recycled.
     */
    public void onDetached();

    public boolean isCursorBeingModified();

    public boolean isActive();
}
