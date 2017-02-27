package com.digdug.wesjohnston.richedittext;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.Layout;
import android.text.SpannableStringBuilder;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import com.digdug.wesjohnston.richedittext.RichEditMenu.MenuItem;

import java.util.HashMap;
import java.util.Map;

public class RichEditToolbar extends HorizontalScrollView {
    private static final String LOGTAG = RichEditToolbar.class.getSimpleName();
    Map<MenuItem, View> items = new HashMap<>(MenuItem.values().length);
    private LinearLayout layout;
    private RichEditText edit;

    private int prevSelectionStart = -1;
    private int prevSelectionEnd = -1;

    public RichEditToolbar(Context context) {
        super(context);
        initUI();
    }

    public RichEditToolbar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initUI();
    }

    public RichEditToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initUI();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RichEditToolbar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initUI();
    }

    public void bindRichEdit(RichEditText edit) {
        this.edit = edit;

        edit.addSelectionChangeListener(new RichEditText.SelectionChangeListener() {
            @Override
            public void onSelectionChanged(int start, int end) {
                updateUI();
            }

            @Override
            public void onStyleChanged() {
                updateUI();
            }
        });
    }

    private void updateUI() {
        for (MenuItem key : items.keySet()) {
            Object value = key.listener.queryValue(edit);
            if (value instanceof Boolean) {
                ImageView view = (ImageView) items.get(key);
                view.setImageState(new int[]{(Boolean) value ? android.R.attr.state_checked : -android.R.attr.state_checked}, false);
            }
        }
    }

    private void initUI() {
        layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);
        layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT));
        addView(layout);

        for (MenuItem item : MenuItem.values()) {
            if (item.icon > -1) {
                items.put(item, addMenuItem(item));
            }
        }

        Button text = new Button(getContext());
        text.setLayoutParams(new LinearLayout.LayoutParams(100, 100));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            text.setTextAlignment(TEXT_ALIGNMENT_CENTER);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            text.setBackgroundTintList(new ColorStateList(new int[][] {
                    new int[] { android.R.attr.state_enabled },
                    new int[] { -android.R.attr.state_enabled }
            }, new int[] {
                    Color.LTGRAY,
                    Color.LTGRAY
            }));
        }
        text.setTextColor(Color.BLACK);
        text.setGravity(Gravity.CENTER);
        SpannableStringBuilder builder = new SpannableStringBuilder(" T ");
        //builder.setSpan(new BackgroundColorSpan(Color.BLUE), 0, builder.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        //builder.setSpan(new ForegroundColorSpan(Color.RED), 0, builder.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        //builder.setSpan(new UnderlineSpan(), 0, builder.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        text.setText(builder);
        layout.addView(text);
    }

    private ImageButton addMenuItem(final RichEditMenu.MenuItem item) {
        ImageButton button = new ImageButton(getContext());
        button.setScaleType(ImageView.ScaleType.FIT_CENTER);
        button.setLayoutParams(new LinearLayout.LayoutParams(100, 100));
        button.setContentDescription(getContext().getResources().getString(item.label));

        if (item.icon > -1) {
            button.setImageResource(item.icon);
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            button.setBackgroundTintList(new ColorStateList(new int[][] {
                    new int[] { android.R.attr.state_checked },
                    new int[] { -android.R.attr.state_checked }
            }, new int[] {
                    Color.RED,
                    Color.BLUE
            }));
        }

        button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                item.listener.onClick(v, edit);
            }
        });

        layout.addView(button);
        return button;
    }
}
