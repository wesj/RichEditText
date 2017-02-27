package com.digdug.wesjohnston.richedittext;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.test.espresso.core.deps.guava.base.Preconditions;
import android.support.v4.app.FragmentActivity;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

import java.net.URL;

@RequiresApi(api = Build.VERSION_CODES.M)
public class RichEditActionModeCallback extends ActionMode.Callback2 {
    private ActionMode.Callback customCallback;
    RichEditMenu menu;

    public ActionMode.Callback getCustomCallback() {
        return customCallback;
    }

    public void setCustomCallback(ActionMode.Callback customCallback) {
        this.customCallback = customCallback;
    }

    private final RichEditText edit;
    private boolean mHasSelection;

    public RichEditActionModeCallback(RichEditText view) {
        edit = Preconditions.checkNotNull(view);
        menu = new RichEditMenu(edit);
    }

    @Override
    public boolean onCreateActionMode(ActionMode mode, Menu menu) {
        mode.setTitle(null);
        mode.setSubtitle(null);
        mode.setTitleOptionalHint(true);
        this.menu.populateMenuWithItems(menu);

       if (menu.hasVisibleItems() || mode.getCustomView() != null) {
            if (mHasSelection && !edit.hasTransientState()) {
                edit.setHasTransientState(true);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
        ActionMode.Callback customCallback = getCustomCallback();
        if (customCallback != null) {
            return customCallback.onPrepareActionMode(mode, menu);
        }
        return true;
    }

    @Override
    public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
        ActionMode.Callback customCallback = getCustomCallback();
        if (customCallback != null && customCallback.onActionItemClicked(mode, item)) {
            return true;
        }
        return menu.onTextContextMenuItem(mode.getMenu(), item.getItemId());
    }

    @Override
    public void onDestroyActionMode(ActionMode mode) {
        // Clear mTextActionMode not to recursively destroy action mode by clearing selection.
        ActionMode.Callback customCallback = getCustomCallback();
        if (customCallback != null) {
            customCallback.onDestroyActionMode(mode);
        }

        //Selection.setSelection((Spannable) edit.getText(),
        //        edit.getSelectionEnd());
    }
}
