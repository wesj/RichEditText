package com.digdug.wesjohnston.richtexter;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.digdug.wesjohnston.richedittext.RichEditActionModeCallback;
import com.digdug.wesjohnston.richedittext.RichEditMenu;
import com.digdug.wesjohnston.richedittext.RichEditText;
import com.digdug.wesjohnston.richedittext.RichEditToolbar;

public class MainActivity extends AppCompatActivity {

    private static final String LOGTAG = MainActivity.class.getSimpleName();
    private RichEditText edit;
    private RichEditToolbar toolbar;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (RichEditToolbar) findViewById(R.id.toolbar);
        edit = (RichEditText) findViewById(R.id.edit);
        edit.setCustomSelectionActionModeCallback(new RichEditActionModeCallback(edit));
        toolbar.bindRichEdit(edit);

        final TextView html = (TextView) findViewById(R.id.html);
        edit.addSelectionChangeListener(new RichEditText.SelectionChangeListener() {
            @Override
            public void onSelectionChanged(int start, int end) {
                html.setText(Html.toHtml(edit.getText()));
            }

            @Override
            public void onStyleChanged() {
                html.setText(Html.toHtml(edit.getText()));
            }
        });
        edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                html.setText(Html.toHtml(edit.getText()));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        RichEditMenu helper = new RichEditMenu(edit);
        helper.populateMenuWithItems(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        RichEditMenu helper = new RichEditMenu(edit);
        return helper.onTextContextMenuItem(null, item.getItemId());
    }
}
