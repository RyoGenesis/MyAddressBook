package com.latihan.myaddressbook_2301853962.services;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import java.io.InputStream;

public class LoadImageTask extends AsyncTask<String, Void, Bitmap> {

    private ImageView img;

    public LoadImageTask(ImageView img) {
        this.img = img;
    }

    @Override
    protected Bitmap doInBackground(String... urls) {
        String urlDisplay = urls[0];
        Bitmap imgBitmap = null;
        try {
            InputStream in = new java.net.URL(urlDisplay).openStream();
            imgBitmap = BitmapFactory.decodeStream(in);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        return imgBitmap;
    }

    @Override
    protected void onPostExecute(Bitmap result) {
        img.setImageBitmap(result);
    }
}
