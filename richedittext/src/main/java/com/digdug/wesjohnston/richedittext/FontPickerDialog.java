package com.digdug.wesjohnston.richedittext;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

public class FontPickerDialog extends DialogFragment implements LoaderManager.LoaderCallbacks<Map<String, String>> {
    private static final int FONT_LOADER = 5623;
    private static final String LOGTAG = FontPickerDialog.class.getSimpleName();
    private RichEditMenu.FontPickerCallback callback;
    private Map<String, String> data;

    static FontPickerDialog newInstance() {
        FontPickerDialog dialog = new FontPickerDialog();
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(FONT_LOADER, null, this);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return (new AlertDialog.Builder(getContext()))
                .setCancelable(true)
                .setAdapter(getAdapter(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (callback != null) {
                    String name = getAdapter().getItem(which);
                    //callback.onFontSelected(data.get(name));
                }
            }
        }).setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (callback != null) {
                    //callback.onError("");
                }
            }
        })
        .create();
    }

    @Override
    public Loader<Map<String, String>> onCreateLoader(int id, Bundle args) {
        return new FontLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<Map<String, String>> loader, Map<String, String> data) {
        this.data = data;
        getAdapter().addAll(data.keySet());
    }

    @Override
    public void onLoaderReset(Loader<Map<String, String>> loader) {
        getAdapter().clear();
    }

    private FontAdapter adapter = null;
    public FontAdapter getAdapter() {
        if (adapter == null) {
            adapter = new FontAdapter(getContext(), android.R.layout.simple_list_item_1);
        }
        return adapter;
    }

    public void setCallback(RichEditMenu.FontPickerCallback callback) {
        this.callback = callback;
    }

    private class FontAdapter extends ArrayAdapter<String> {
        public FontAdapter(Context context, int resource) {
            super(context, resource);
        }

        public FontAdapter(Context context, int resource, int textViewResourceId) {
            super(context, resource, textViewResourceId);
        }

        public FontAdapter(Context context, int resource, String[] objects) {
            super(context, resource, objects);
        }

        public FontAdapter(Context context, int resource, int textViewResourceId, String[] objects) {
            super(context, resource, textViewResourceId, objects);
        }

        public FontAdapter(Context context, int resource, List<String> objects) {
            super(context, resource, objects);
        }

        public FontAdapter(Context context, int resource, int textViewResourceId, List<String> objects) {
            super(context, resource, textViewResourceId, objects);
        }

        @Override
        public @NonNull
        View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            String font = getItem(position);
            Typeface type = Typeface.createFromFile(data.get(font));
            view.setTypeface(type);
            return view;
        }
    }

    private static class FontLoader extends AsyncTaskLoader<Map<String, String>> {
        Map<String, String> data = null;

        public FontLoader(Context context) {
            super(context);
        }

        @Override
        public Map<String, String> loadInBackground() {
            return FontManager.enumerateFonts();
        }

        @Override
        protected void onStartLoading() {
            if (data != null) {
                deliverResult(data);
            }

            if (takeContentChanged() || data == null) {
                forceLoad();
            }
        }
    }
}
