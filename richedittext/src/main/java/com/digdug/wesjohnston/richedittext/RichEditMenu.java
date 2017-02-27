package com.digdug.wesjohnston.richedittext;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import java.io.File;
import java.net.URL;

public class RichEditMenu {
    private static final String LOGTAG = RichEditMenu.class.getSimpleName();
    RichEditText edit;

    static enum MenuItem {
        BOLD(0, 0, R.string.bold, R.mipmap.bold, new RichEditMenu.OnClickListener() {
            @Override
            public void onClick(View view, RichEditText edit) {
                edit.toggleBold();
            }

            @Override
            public Object queryValue(RichEditText edit) {
                return edit.isBold();
            }
        }),
        ITALIC(1, 0, R.string.italic, R.mipmap.italics, new RichEditMenu.OnClickListener() {
            @Override
            public void onClick(View view, RichEditText edit) {
                edit.toggleItalics();
            }
            @Override
            public Object queryValue(RichEditText edit) {
                return edit.isItalics();
            }
        }),
        UNDERLINE(1, 0, R.string.underline, R.mipmap.underline, new RichEditMenu.OnClickListener() {
            @Override
            public void onClick(View view, RichEditText edit) {
                edit.toggleUnderline();
            }
            @Override
            public Object queryValue(RichEditText edit) {
                return edit.isUnderline();
            }
        }),
        STRIKETHROUGH(1, 0, R.string.strikethrough, R.mipmap.strikethrough, new RichEditMenu.OnClickListener() {
            @Override
            public void onClick(View view, RichEditText edit) {
                edit.toggleStrikethrough();
            }
            @Override
            public Object queryValue(RichEditText edit) {
                return edit.isStrikethrough();
            }
        }),
        SUPERSCRIPT(1, 0, R.string.superscript, -1, new RichEditMenu.OnClickListener() {
            @Override
            public void onClick(View view, RichEditText edit) {
                edit.toggleSuperScript();
            }
            @Override
            public Object queryValue(RichEditText edit) {
                return edit.isSuperscript();
            }
        }),
        SUBSCRIPT(1, 0, R.string.subscript, -1, new RichEditMenu.OnClickListener() {
            @Override
            public void onClick(View view, RichEditText edit) {
                edit.toggleSubScript();
            }
            @Override
            public Object queryValue(RichEditText edit) {
                return edit.isSubscript();
            }
        }),
        FONT(1, 0, R.string.font, -1, new RichEditMenu.OnClickListener() {
            @Override
            public void onClick(View view, RichEditText edit) {
                showFontPicker(edit, new FontPickerCallback() {
                    @Override
                    public void onFontSelected(RichEditText edit, String fontFamily) {
                        edit.setFont(fontFamily);
                    }

                    @Override
                    public void onError(RichEditText edit, String error) { }
                });
            }
            @Override
            public Object queryValue(RichEditText edit) {
                return edit.getFont();
            }
        }),
        FG_COLOR(1, 0, R.string.fg_color, -1, new RichEditMenu.OnClickListener() {
            @Override
            public void onClick(View view, RichEditText edit) {
                showColorPicker(edit, new ColorPickerCallback() {
                    @Override
                    public void onColorSelected(RichEditText edit, int color) {
                        edit.setForegroundColor(color);
                    }

                    @Override
                    public void onError(RichEditText edit, String error) {
                    }
                });
            }
            @Override
            public Object queryValue(RichEditText edit) {
                return edit.getForegroundColor();
            }
        }),
        BG_COLOR(1, 0, R.string.bg_color, -1, new RichEditMenu.OnClickListener() {
            @Override
            public void onClick(View view, RichEditText edit) {
                showColorPicker(edit, new ColorPickerCallback() {
                    @Override
                    public void onColorSelected(RichEditText edit, int color) {
                        edit.setBackgroundColor(color);
                    }

                    @Override
                    public void onError(RichEditText edit, String error) {
                    }
                });
            }
            @Override
            public Object queryValue(RichEditText edit) {
                return edit.getBackgroundColor();
            }
        }),
        BULLETS(1, 0, R.string.bullets, R.mipmap.bullets, new RichEditMenu.OnClickListener() {
            @Override
            public void onClick(View view, RichEditText edit) {
                edit.toggleBullets();
            }
            @Override
            public Object queryValue(RichEditText edit) {
                return edit.isBullets();
            }
        }),
        QUOTE(1, 0, R.string.quote, R.mipmap.quote, new RichEditMenu.OnClickListener() {
            @Override
            public void onClick(View view, RichEditText edit) {
                edit.toggleQuotes();
            }
            @Override
            public Object queryValue(RichEditText edit) {
                return edit.isQuote();
            }
        }),
        IMAGE(1, 0, R.string.selectImage, R.mipmap.image, new RichEditMenu.OnClickListener() {
            @Override
            public void onClick(View view, RichEditText edit) {
                showImagePicker(edit, new ImagePickerCallback() {
                    @Override
                    public void onImageSelected(RichEditText edit, Drawable image) {
                        edit.addImage(image);
                    }

                    @Override
                    public void onImageSelected(RichEditText edit, Bitmap image) {
                        Log.i(LOGTAG, "Image selected " + image);
                        edit.addImage(image);
                    }

                    @Override
                    public void onImageSelected(RichEditText edit, int imageResource) {
                        edit.addImage(imageResource);
                    }

                    @Override
                    public void onImageSelected(RichEditText edit, File path) {
                        edit.addImage(path);
                    }

                    @Override
                    public void onError(RichEditText edit, String error) {

                    }
                });
            }
            @Override
            public Object queryValue(RichEditText edit) {
                return edit.getImage();
            }
        }),
        ALIGN_CENTER(1, 0, R.string.align_center, R.mipmap.align_center, new RichEditMenu.OnClickListener() {
            @Override
            public void onClick(View view, RichEditText edit) {
                edit.setAlignment(Layout.Alignment.ALIGN_CENTER);
            }
            @Override
            public Object queryValue(RichEditText edit) {
                return edit.getAlignment() == Layout.Alignment.ALIGN_CENTER;
            }
        }),
        ALIGN_RIGHT(1, 0, R.string.align_right, R.mipmap.align_right, new RichEditMenu.OnClickListener() {
            @Override
            public void onClick(View view, RichEditText edit) {
                edit.setAlignment(Layout.Alignment.ALIGN_OPPOSITE);
            }
            @Override
            public Object queryValue(RichEditText edit) {
                return edit.getAlignment() == Layout.Alignment.ALIGN_OPPOSITE;
            }
        }),
        ALIGN_LEFT(1, 0, R.string.align_left, R.mipmap.align_left, new RichEditMenu.OnClickListener() {
            @Override
            public void onClick(View view, RichEditText edit) {
                edit.setAlignment(Layout.Alignment.ALIGN_NORMAL);
            }
            @Override
            public Object queryValue(RichEditText edit) {
                return edit.getAlignment() == Layout.Alignment.ALIGN_NORMAL;
            }
        }),
        LINK(1, 0, R.string.link, R.mipmap.link, new RichEditMenu.OnClickListener() {
            @Override
            public void onClick(View view, RichEditText edit) {
                showLinkPicker(edit, new UrlPickerCallback() {
                    @Override
                    public void onUrlSelected(RichEditText edit, URL url) {
                        edit.setUrl(url);
                    }

                    @Override
                    public void onError(RichEditText edit, String error) { }
                });
            }
            @Override
            public Object queryValue(RichEditText edit) {
                return null; // edit.getLink();
            }
        });

        final int id;
        final int order;
        final int label;
        final int icon;
        final OnClickListener listener;

        MenuItem(int id, int order, int label, int icon, OnClickListener listener) {
            this.id = id;
            this.order = order;
            this.label = label;
            this.icon = icon;
            this.listener = listener;
        }
    }

    public interface FontPickerCallback {
        public void onFontSelected(RichEditText edit, String fontFamily);
        public void onError(RichEditText edit, String error);
    }

    public interface ImagePickerCallback {
        public void onImageSelected(RichEditText edit, Drawable image);
        public void onImageSelected(RichEditText edit, Bitmap image);
        public void onImageSelected(RichEditText edit, int imageResource);
        public void onImageSelected(RichEditText edit, File path);
        public void onError(RichEditText edit, String error);
    }

    public interface ColorPickerCallback {
        public void onColorSelected(RichEditText edit, int color);
        public void onError(RichEditText edit, String error);
    }

    public interface UrlPickerCallback {
        public void onUrlSelected(RichEditText edit, URL url);
        public void onError(RichEditText edit, String error);
    }

    public static void showFontPicker(RichEditText edit, FontPickerCallback callback) {
        try {
            final FragmentActivity activity = (FragmentActivity) edit.getContext();
            FontPickerDialog newFragment = FontPickerDialog.newInstance();
            newFragment.setCallback(callback);
            newFragment.show(activity.getSupportFragmentManager(), "dialog");
        } catch(ClassCastException ex) {
            callback.onError(edit, "No valid context");
        }
    }

    public static void showColorPicker(RichEditText edit, ColorPickerCallback callback) {
        try {
            final FragmentActivity activity = (FragmentActivity) edit.getContext();
            ColorPickerDialog newFragment = ColorPickerDialog.newInstance();
            newFragment.setCallback(callback);
            newFragment.show(activity.getSupportFragmentManager(), "dialog");
        } catch(ClassCastException ex) {
            callback.onError(edit, "No valid context");
        }
    }

    public static void showLinkPicker(RichEditText edit, UrlPickerCallback callback) {
        try {
            final FragmentActivity activity = (FragmentActivity) edit.getContext();
            UrlPickerDialog newFragment = UrlPickerDialog.newInstance();
            newFragment.setCallback(callback);
            newFragment.show(activity.getSupportFragmentManager(), "dialog");
        } catch(ClassCastException ex) {
            callback.onError(edit, "No valid context");
        }
    }

    public RichEditMenu(RichEditText edit) {
        this.edit = edit;
    }

    private android.view.MenuItem addMenuItem(Menu menu, MenuItem id) {
        final android.view.MenuItem item = menu.add(Menu.NONE, id.id, id.order, id.label);
        item.setShowAsAction(android.view.MenuItem.SHOW_AS_ACTION_IF_ROOM);
        if (id.icon > -1) {
            item.setIcon(id.icon);
        }
        //item.setCheckable(true);
        //item.setChecked(checked);
        return item;
    }

    public void populateMenuWithItems(Menu menu) {
        for (MenuItem item : MenuItem.values()) {
            addMenuItem(menu, item);
        }
    }

    public boolean onTextContextMenuItem(Menu menu, int itemId) {
        if (itemId < MenuItem.values().length) {
            final MenuItem id = MenuItem.values()[itemId];
            id.listener.onClick(null, edit);
            return true;
        }
        return false;
    }

    private static void showImagePicker(RichEditText edit, ImagePickerCallback callback) {
        try {
            final FragmentActivity activity = (FragmentActivity) edit.getContext();
            ImagePickerDialog newFragment = ImagePickerDialog.newInstance();
            newFragment.setCallback(callback);

            FragmentManager manager = activity.getSupportFragmentManager();
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.add(newFragment, "image picker");
            transaction.addToBackStack("dialog");
            transaction.commit();
        } catch(ClassCastException ex) {
            callback.onError(edit, "No valid context");
        }
    }

    interface OnClickListener {
        public void onClick(View view, RichEditText edit);
        public Object queryValue(RichEditText edit);
    }
}
