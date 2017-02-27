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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ColorPickerDialog extends DialogFragment implements LoaderManager.LoaderCallbacks<Set<Integer>> {
    private static final int COLOR_LOADER = 3491;
    private static final String LOGTAG = ColorPickerDialog.class.getSimpleName();
    private RichEditMenu.ColorPickerCallback callback;
    private Set<Integer> data;

    static ColorPickerDialog newInstance() {
        ColorPickerDialog dialog = new ColorPickerDialog();
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(COLOR_LOADER, null, this);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return (new AlertDialog.Builder(getContext()))
                .setCancelable(true)
                .setNeutralButton(R.string.advanced, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        if (callback != null) {
                            //callback.onError("");
                        }
                    }
                })
                .setAdapter(getAdapter(), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (callback != null) {
                            Integer color = getAdapter().getItem(which);
                            //callback.onColorSelected(color);
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
    public Loader<Set<Integer>> onCreateLoader(int id, Bundle args) {
        return new ColorLoader(getContext());
    }

    @Override
    public void onLoadFinished(Loader<Set<Integer>> loader, Set<Integer> data) {
        this.data = data;
        getAdapter().addAll(data);
    }

    @Override
    public void onLoaderReset(Loader<Set<Integer>> loader) {
        getAdapter().clear();
    }

    private ColorAdapter adapter = null;
    public ColorAdapter getAdapter() {
        if (adapter == null) {
            adapter = new ColorAdapter(getContext(), android.R.layout.simple_list_item_1);
        }
        return adapter;
    }

    public void setCallback(RichEditMenu.ColorPickerCallback callback) {
        this.callback = callback;
    }

    private class ColorAdapter extends ArrayAdapter<Integer> {
        public ColorAdapter(Context context, int resource) {
            super(context, resource);
        }

        public ColorAdapter(Context context, int resource, int textViewResourceId) {
            super(context, resource, textViewResourceId);
        }

        public ColorAdapter(Context context, int resource, Integer[] objects) {
            super(context, resource, objects);
        }

        public ColorAdapter(Context context, int resource, int textViewResourceId, Integer[] objects) {
            super(context, resource, textViewResourceId, objects);
        }

        @Override
        public @NonNull
        View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            TextView view = (TextView) super.getView(position, convertView, parent);
            Integer color = getItem(position);
            view.setText("");
            view.setBackgroundColor(color);
            return view;
        }
    }

    private static class ColorLoader extends AsyncTaskLoader<Set<Integer>> {
        Set<Integer> data = null;

        public ColorLoader(Context context) {
            super(context);
        }

        @Override
        public Set<Integer> loadInBackground() {
            data = new LinkedHashSet<>();
            data.add(0xfff44336);
            data.add(0xffe91e63);
            data.add(0xff9c27b0);
            data.add(0xff673ab7);
            data.add(0xff3f51b5);
            data.add(0xff2196f3);
            data.add(0xff03a9f4);
            data.add(0xff00bcd4);
            data.add(0xff009688);
            data.add(0xff4caf50);
            data.add(0xff8bc34a);
            data.add(0xffcddc39);
            data.add(0xffffeb3b);
            data.add(0xffffc107);
            data.add(0xffff9800);
            data.add(0xffff5722);
            data.add(0xff795548);
            data.add(0xff9e9e9e);
            data.add(0xff607d8b);
            data.add(0xff000000);
            data.add(0xffffffff);
            return data;
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
