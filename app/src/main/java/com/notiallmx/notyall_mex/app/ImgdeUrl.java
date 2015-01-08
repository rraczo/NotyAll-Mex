package com.notiallmx.notyall_mex.app;

/**
 * Created by oscar on 02/06/2014.
 */
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

public class ImgdeUrl extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference imageViewReference;

    public ImgdeUrl(ImageView imageView) {
        imageViewReference = new WeakReference(imageView);
        }
    @Override
    // Actual download method, run in the task thread
    protected Bitmap doInBackground(String... params) {
        // params comes from the execute() call: params[0] is the url.
        return procesosjsoup.dameBitmapdeURL(params[0]);
        }
    @Override
    // Once the image is downloaded, associates it to the imageView
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
            }
        if (imageViewReference != null) {
            final ImageView imageView = (ImageView) imageViewReference.get();
            if (imageView != null) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                    } else {
                    //ponemos imagen temporal para antes de cargar imagen de url
                    // imageView.setImageDrawable(imageView.getContext().getResources().getDrawable(R.drawable.ic_launcher));
                   }

                    //}
                }
            }
        }
    }

