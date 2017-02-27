package com.digdug.wesjohnston.richedittext.generators;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Layout;
import android.text.style.AlignmentSpan;
import android.text.style.ImageSpan;
import android.util.Log;

import com.digdug.wesjohnston.richedittext.RichEditText;

import java.io.File;

public class ImageGenerator implements SpanGenerator<ImageSpan, Object> {
    private static final String LOGTAG = ImageGenerator.class.getSimpleName();
    private final Bitmap bitmap;
    private final int resourceId;
    private final Drawable drawable;
    private final File path;

    public ImageGenerator(Bitmap bitmap) {
        this.bitmap = bitmap;
        resourceId = -1;
        drawable = null;
        path = null;
    }

    public ImageGenerator(int resId) {
        this.resourceId = resId;
        bitmap = null;
        drawable = null;
        path = null;
    }

    public ImageGenerator(Drawable drawable) {
        this.drawable = drawable;
        bitmap = null;
        this.resourceId = -1;
        path = null;
    }

    public ImageGenerator(File path) {
        this.path = path;
        bitmap = null;
        resourceId = -1;
        drawable = null;
    }

    public ImageGenerator() {
        this.path = null;
        this.bitmap = null;
        this.resourceId = -1;
        this.drawable = null;
    }

    @Override
    public Class<ImageSpan> getClassType() {
        return ImageSpan.class;
    }

    @Override
    public boolean isSpan(ImageSpan other) {
        return true;
    }

    @Override
    public ImageSpan getSpan(Context context) {
        if (bitmap != null) {
            ImageSpan span = new ImageSpan(context, bitmap);
            return span;
        } else if (drawable != null) {
            return new ImageSpan(drawable);
        } else if (path != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(path.toString());
            return new ImageSpan(context, bitmap);
        }
        return new ImageSpan(context, resourceId);
    }

    @Override
    public Object getCurrentValue(RichEditText edit) {
        Editable text = edit.getText();
        ImageSpan[] spansFound = text.getSpans(edit.getSelectionStart(), edit.getSelectionEnd(), ImageSpan.class);
        if (spansFound.length > 0) {
            ImageSpan span = spansFound[0];
            Drawable drawable = span.getDrawable();
            if (drawable != null) {
                return drawable;
            }
            return spansFound[0].getSource();
        }
        return null;
    }
}
