package com.digdug.wesjohnston.richedittext;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Browser;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

public class UrlPickerDialog extends DialogFragment {
    private static final int Url_LOADER = 3491;
    private static final String LOGTAG = UrlPickerDialog.class.getSimpleName();
    private RichEditMenu.UrlPickerCallback callback;
    private Set<URL> data;
    private EditText edit;

    static UrlPickerDialog newInstance() {
        UrlPickerDialog dialog = new UrlPickerDialog();
        return dialog;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        edit = new EditText(getContext());
        edit.setHint("Enter a url");
        edit.setInputType(EditorInfo.TYPE_TEXT_VARIATION_URI);
        edit.setSingleLine();

        return (new AlertDialog.Builder(getContext()))
                .setCancelable(true)
                .setView(edit)
                .setNeutralButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (callback != null) {
                            //try {
                                // callback.onUrlSelected(new URL(edit.getText().toString()));
                            //} catch (MalformedURLException e) {
                                // callback.onError("Not a valid URL");
                            //}
                        }
                    }
                })
                .create();
    }

    public void setCallback(RichEditMenu.UrlPickerCallback callback) {
        this.callback = callback;
    }
}
