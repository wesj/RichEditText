package com.digdug.wesjohnston.richedittext;

import android.app.Dialog;
import android.content.ContentUris;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Wes Johnston on 2/24/2017.
 */

public class ImagePickerDialog extends Fragment {
    private static final int PICK_FROM_CAMERA = 1;
    private static final int PICK_FROM_FILE = 2;
    private static final String LOGTAG = ImagePickerDialog.class.getSimpleName();
    private Uri imageCaptureUri;
    private RichEditMenu.ImagePickerCallback callback;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        Resources res = getContext().getResources();
        final String [] items           = new String [] {
                res.getString(R.string.fromCamera),
                res.getString(R.string.fromFile)
        };
        ArrayAdapter<String> adapter  = new ArrayAdapter<String> (getContext(), android.R.layout.select_dialog_item,items);
        AlertDialog.Builder builder     = new AlertDialog.Builder(getContext());

        builder.setTitle(R.string.selectImage);
        builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int item ) {
                if (item == 0) {
                    selectFromCamera();
                } else {
                    selectFromFile();
                }
            }
        });
        builder.create();
        */
        selectFromFile();
    }

    private void selectFromFile() {
        Intent intent = new Intent();

        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_FILE);
    }

    private void selectFromCamera() {
        Intent intent    = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file        = new File(Environment.getExternalStorageDirectory(),
                "tmp_avatar_" + String.valueOf(System.currentTimeMillis()) + ".jpg");
        imageCaptureUri = Uri.fromFile(file);

        try {
            intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageCaptureUri);
            intent.putExtra("return-data", true);

            startActivityForResult(intent, PICK_FROM_CAMERA);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (callback == null) {
            getFragmentManager().popBackStack();
            return;
        }

        if (resultCode != RESULT_OK) {
            getFragmentManager().popBackStack();
            return;
        }

        String path     = "";
        if (requestCode == PICK_FROM_FILE) {
            imageCaptureUri = data.getData();
            (new AsyncTask<Void, Void, Bitmap>() {

                @Override
                protected Bitmap doInBackground(Void... params) {
                    ParcelFileDescriptor parcelFD = null;
                    try {
                        parcelFD = getContext().getContentResolver().openFileDescriptor(imageCaptureUri, "r");
                        FileDescriptor imageSource = parcelFD.getFileDescriptor();
                        return BitmapFactory.decodeFileDescriptor(imageSource);
                    } catch (FileNotFoundException e) {
                        Log.i(LOGTAG, "File ", e);
                    }
                    return null;
                }

                @Override
                protected void onPostExecute(Bitmap bitmap) {
                    if (bitmap != null) {
                        // callback.onImageSelected(bitmap);
                    } else {
                        // callback.onError("No bitmap");
                    }
                    getFragmentManager().popBackStack();
                }
            }).execute();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String handleDocumentUri() {
        if (isExternalStorageDocument(imageCaptureUri)) {
            final String docId = DocumentsContract.getDocumentId(imageCaptureUri);
            final String[] split = docId.split(":");
            final String type = split[0];

            if ("primary".equalsIgnoreCase(type)) {
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            }
        } else if (isDownloadsDocument(imageCaptureUri)) {
            final String id = DocumentsContract.getDocumentId(imageCaptureUri);
            final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            return getDataColumn(contentUri, null, null);
        } else if (isMediaDocument(imageCaptureUri)) {
            final String docId = DocumentsContract.getDocumentId(imageCaptureUri);
            final String[] split = docId.split(":");
            final String type = split[0];

            Uri contentUri = null;
            if ("image".equals(type)) {
                contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
            } else if ("video".equals(type)) {
                contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
            } else if ("audio".equals(type)) {
                contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            }

            final String selection = "_id=?";
            final String[] selectionArgs = new String[] {
                    split[1]
            };

            return getDataColumn(contentUri, selection, selectionArgs);
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public String getDataColumn(Uri contentUri, String selection, String[] selectionArgs) {
        String [] proj      = {MediaStore.Images.Media.DATA};
        Cursor cursor       = getContext().getContentResolver().query( contentUri, proj, selection, selectionArgs, null);

        if (cursor == null) {
            return null;
        }

        int column_index    = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public void setCallback(RichEditMenu.ImagePickerCallback callback) {
        this.callback = callback;
    }

    public static ImagePickerDialog newInstance() {
        return new ImagePickerDialog();
    }
}
