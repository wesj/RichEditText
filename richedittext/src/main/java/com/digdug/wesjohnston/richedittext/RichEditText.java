package com.digdug.wesjohnston.richedittext;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.Layout;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Menu;
import android.widget.EditText;

import com.digdug.wesjohnston.richedittext.generators.AlignmentGenerator;
import com.digdug.wesjohnston.richedittext.generators.BackgroundColorGenerator;
import com.digdug.wesjohnston.richedittext.generators.BulletGenerator;
import com.digdug.wesjohnston.richedittext.generators.FontGenerator;
import com.digdug.wesjohnston.richedittext.generators.ForegroundColorGenerator;
import com.digdug.wesjohnston.richedittext.generators.ImageGenerator;
import com.digdug.wesjohnston.richedittext.generators.QuoteGenerator;
import com.digdug.wesjohnston.richedittext.generators.SizeGenerator;
import com.digdug.wesjohnston.richedittext.generators.SpanGenerator;
import com.digdug.wesjohnston.richedittext.generators.StrikethroughGenerator;
import com.digdug.wesjohnston.richedittext.generators.StyleGenerator;
import com.digdug.wesjohnston.richedittext.generators.SubscriptSpanGenerator;
import com.digdug.wesjohnston.richedittext.generators.SuperscriptSpanGenerator;
import com.digdug.wesjohnston.richedittext.generators.TextAppearanceGenerator;
import com.digdug.wesjohnston.richedittext.generators.URLGenerator;
import com.digdug.wesjohnston.richedittext.generators.UnderlineGenerator;
import com.digdug.wesjohnston.richedittext.range.Range;
import com.digdug.wesjohnston.richedittext.range.RangeList;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class RichEditText extends EditText {
    private static final String LOGTAG = RichEditText.class.getSimpleName();
    private SpanGenerator strikethroughGenerator = new StrikethroughGenerator();
    private SpanGenerator underlineGenerator = new UnderlineGenerator();
    private SpanGenerator boldGenerator = new StyleGenerator(Typeface.BOLD);
    private SpanGenerator italicGenerator = new StyleGenerator(Typeface.ITALIC);
    private SpanGenerator subscriptGenerator = new SubscriptSpanGenerator();
    private SpanGenerator superscriptGenerator = new SuperscriptSpanGenerator();

    public RichEditText(Context context) {
        super(context);
    }

    public RichEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public RichEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RichEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void setAlignment(Layout.Alignment alignment) {
        AlignmentGenerator generator = new AlignmentGenerator(alignment);
        generator.apply(this);
        notifyStyleChange();
    }

    public Layout.Alignment getAlignment() {
        AlignmentGenerator generator = new AlignmentGenerator(Layout.Alignment.ALIGN_NORMAL);
        return generator.getCurrentValue(this);
    }

    public void setTextSize(int size) {
        SizeGenerator generator = new SizeGenerator(size);
        toggleStyle(generator, StyleMode.FORCE_SET);
    }

    @Override
    public float getTextSize() {
        SizeGenerator generator = new SizeGenerator(0);
        return generator.getCurrentValue(this);
    }

    public void addImage(File path) {
        ImageGenerator generator = new ImageGenerator(path);
        addImage(generator);
    }

    public Object getImage() {
        ImageGenerator generator = new ImageGenerator();
        return generator.getCurrentValue(this);
    }

    private enum StyleMode {
        FORCE_SET,
        FORCE_CLEAR,
        TOGGLE
    }

    private <T,V> void toggleStyle(SpanGenerator<T, V> generator, StyleMode mode) {
        Range selection = new Range(getSelectionStart(), getSelectionEnd());

        RangeList ranges = new RangeList();
        Editable text = getText();
        T startSpan = null;
        T[] styles = text.getSpans(selection.start, selection.end, generator.getClassType());

        for (T style : styles) {
            if (generator.isSpan(style)) {
                Range spanRange = new Range(text.getSpanStart(style), text.getSpanEnd(style));
                ranges.addRange(spanRange);

                if (startSpan == null || spanRange.start < ranges.getStart()) {
                    startSpan = style;
                }

                text.removeSpan(style);
            }
        }

        // If this is a zero length selection, and we have a range inside it, we need to cut off the tail of the style.
        //if (selection.start == selection.end && ranges.size() > 0) {
        //    text.setSpan(startSpan, ranges.getStart(), ranges.getEnd(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //} else
        if (ranges.contains(selection) && mode != StyleMode.FORCE_SET) {
            ranges.remove(selection);
            int i = 0;
            for (Range r : ranges) {
                if (ranges.size() == 1) {
                    text.setSpan(startSpan, r.start, r.end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
                } else {
                    if (i == 0) {
                        text.setSpan(startSpan, r.start, r.end, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
                    } else if (i == ranges.size() - 1) {
                        text.setSpan(startSpan, r.start, r.end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
                    } else {
                        text.setSpan(startSpan, r.start, r.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
                i++;
                startSpan = generator.getSpan(getContext());
            }
        } else if (startSpan != null && ranges.size() > 0) {
            text.setSpan(startSpan,
                    Math.min(ranges.getStart(), selection.start),
                    Math.max(ranges.getEnd(), selection.end),
                    Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        } else {
            Object style = generator.getSpan(getContext());
            text.setSpan(style, selection.start, selection.end, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        }
        notifyStyleChange();
    }

    private <T> boolean hasSpan(Class<? extends T> cls) {
        int start = getSelectionStart();
        int end = getSelectionEnd();
        int spanStart = Integer.MAX_VALUE;
        int spanEnd = Integer.MIN_VALUE;

        T[] styles = getText().getSpans(start, end, cls);
        Editable text = getText();
        for (T style : styles) {
            int s1 = text.getSpanStart(style);
            if (s1 < spanStart) {
                spanStart = s1;
            }

            s1 = text.getSpanEnd(style);
            if (s1 > spanEnd) {
                spanEnd = s1;
            }
        }

        return spanStart <= start && spanEnd >= end;
    }

    // BOLD
    public boolean isBold() {
        return (boolean) boldGenerator.getCurrentValue(this);
    }

    public void toggleBold() {
        toggleStyle(boldGenerator, StyleMode.TOGGLE);
    }

    // ITALICS
    public void toggleItalics() {
        toggleStyle(italicGenerator, StyleMode.TOGGLE);
    }

    public boolean isItalics() {
        return (boolean) italicGenerator.getCurrentValue(this);
    }

    public void setItalics() {
        toggleStyle(italicGenerator, StyleMode.FORCE_SET);
    }

    public void clearItalics() {
        toggleStyle(italicGenerator, StyleMode.FORCE_CLEAR);
    }

    // SUBSCRIPT
    public void toggleSubScript() {
        toggleStyle(subscriptGenerator, StyleMode.TOGGLE);
    }

    public boolean isSubscript() {
        return hasSpan(SubscriptSpan.class);
    }

    public void setSubScript() {
        toggleStyle(subscriptGenerator, StyleMode.FORCE_SET);
    }

    public void clearSubScript() {
        toggleStyle(subscriptGenerator, StyleMode.FORCE_CLEAR);
    }

    // SUPERSCRIPT
    public void toggleSuperScript() {
        toggleStyle(superscriptGenerator, StyleMode.TOGGLE);
    }

    public boolean isSuperscript() {
        return hasSpan(SuperscriptSpan.class);
    }

    public void setSuperScript() {
        toggleStyle(superscriptGenerator, StyleMode.FORCE_SET);
    }

    public void clearSuperScript() {
        toggleStyle(superscriptGenerator, StyleMode.FORCE_CLEAR);
    }

    // TextAppearance
    public void setTextAppearance(int textAppearance) {
        SpanGenerator generator = new TextAppearanceGenerator(getContext(), textAppearance);
        toggleStyle(generator, StyleMode.FORCE_SET);
    }

    // Font
    public String getFont() {
        FontGenerator generator = new FontGenerator("");
        return generator.getCurrentValue(this);
    }

    public void setFont(String family) {
        SpanGenerator generator = new FontGenerator(family);
        toggleStyle(generator, StyleMode.FORCE_SET);
    }

    // Strikethrough
    public void toggleStrikethrough() {
        toggleStyle(strikethroughGenerator, StyleMode.TOGGLE);
    }

    public boolean isStrikethrough() {
        return (boolean) italicGenerator.getCurrentValue(this);
    }

    public void setStrikethrough() {
        toggleStyle(strikethroughGenerator, StyleMode.FORCE_SET);
    }

    public void clearStrikethrough() {
        toggleStyle(strikethroughGenerator, StyleMode.FORCE_CLEAR);
    }

    // Underline
    public void toggleUnderline() {
        toggleStyle(underlineGenerator, StyleMode.TOGGLE);
    }

    public boolean isUnderline() {
        return (boolean) underlineGenerator.getCurrentValue(this);
    }

    public void setUnderline() {
        toggleStyle(underlineGenerator, StyleMode.FORCE_SET);
    }

    public void clearUnderline() {
        toggleStyle(underlineGenerator, StyleMode.FORCE_CLEAR);
    }

    // Background color
    public int getBackgroundColor() {
        BackgroundColorGenerator generator = new BackgroundColorGenerator(-1);
        return generator.getCurrentValue(this);
    }

    public void setBackgroundColor(int color) {
        SpanGenerator generator = new BackgroundColorGenerator(color);
        toggleStyle(generator, StyleMode.FORCE_SET);
    }

    // Foreground color
    public int getForegroundColor() {
        ForegroundColorGenerator generator = new ForegroundColorGenerator(-1);
        return generator.getCurrentValue(this);
    }

    public void setForegroundColor(int color) {
        SpanGenerator generator = new ForegroundColorGenerator(color);
        toggleStyle(generator, StyleMode.FORCE_SET);
    }

    // URL
    public URL getUrl() {
        URLGenerator generator = new URLGenerator(null);
        return generator.getCurrentValue(this);
    }

    public void setUrl(URL url) {
        SpanGenerator generator = new URLGenerator(url);
        toggleStyle(generator, StyleMode.FORCE_SET);
    }

    // Quotes
    public void toggleQuotes() {
        QuoteGenerator generator = new QuoteGenerator();
        if (isQuote()) {
            generator.remove(this);
        } else {
            generator.apply(this);
        }
        notifyStyleChange();
    }

    public boolean isQuote() {
        QuoteGenerator generator = new QuoteGenerator();
        return generator.getCurrentValue(this);
    }

    // Bullets
    public void toggleBullets() {
        BulletGenerator generator = new BulletGenerator();
        toggleStyle(generator, StyleMode.TOGGLE);
    }

    public boolean isBullets() {
        BulletGenerator generator = new BulletGenerator();
        return generator.getCurrentValue(this);
    }

    // Images
    public void addImage(Drawable drawable) {
        ImageGenerator generator = new ImageGenerator(drawable);
        addImage(generator);
    }

    public void addImage(int resource) {
        ImageGenerator generator = new ImageGenerator(resource);
        addImage(generator);
    }

    public void addImage(Bitmap bitmap) {
        ImageGenerator generator = new ImageGenerator(bitmap);
        addImage(generator);
    }

    private void addImage(ImageGenerator generator) {
        ImageSpan span = generator.getSpan(getContext());
        Editable text = getText();
        int start = getSelectionStart();
        int end = getSelectionEnd();
        text.replace(start, end, " ");
        text.setSpan(span, start, start + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }

    public interface SelectionChangeListener {
        public void onSelectionChanged(int start, int end);
        public void onStyleChanged();
    }

    private List<SelectionChangeListener> selectionChangeListeners = new ArrayList<SelectionChangeListener>();

    public void addSelectionChangeListener(SelectionChangeListener listener) {
        selectionChangeListeners.add(listener);
    }

    public void removeSelectionChangeListener(SelectionChangeListener listener) {
        selectionChangeListeners.remove(listener);
    }

    private void notifyStyleChange() {
        if (selectionChangeListeners == null) {
            return;
        }

        for (SelectionChangeListener listener : selectionChangeListeners) {
            listener.onStyleChanged();
        }
    }

    public void onSelectionChanged(int start, int end) {
        if (selectionChangeListeners == null) {
            return;
        }

        for (SelectionChangeListener listener : selectionChangeListeners) {
            listener.onSelectionChanged(start, end);
        }
    }
}
