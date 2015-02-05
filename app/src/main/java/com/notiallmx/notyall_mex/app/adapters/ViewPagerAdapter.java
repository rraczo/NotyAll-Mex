package com.notiallmx.notyall_mex.app.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.notiallmx.notyall_mex.app.R;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by lord on 03/02/2015.
 */
public class ViewPagerAdapter extends PagerAdapter {
    // Declare Variables
    Context context;
    DisplayImageOptions defaultOptions;
    ImageLoader imageLoader;
    String[] images;
    LayoutInflater inflater;

    public ViewPagerAdapter(Context context, String[] images) {
        this.context = context;
        this.images = images;
        defaultOptions = new DisplayImageOptions.Builder()
                .cacheOnDisc(true).cacheInMemory(true)
                .imageScaleType(ImageScaleType.EXACTLY)
                .displayer(new FadeInBitmapDisplayer(300)).build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
                context)
                .defaultDisplayImageOptions(defaultOptions)
                .memoryCache(new WeakMemoryCache())
                .discCacheSize(100 * 1024 * 1024).build();

        ImageLoader.getInstance().init(config);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((RelativeLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

// Declare Variables

        ImageView imgflag;
        BitmapDrawable ob = null;
        Bitmap imagen;

        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View itemView = inflater.inflate(R.layout.view_pager_item, container,false);

        // Locate the ImageView in viewpager_item.xml
        imgflag = (ImageView) itemView.findViewById(R.id.flag);
        // Capture position and set to the ImageView
        Log.i("imagen", images[position]);
        URL imageUrl = null;
        HttpURLConnection conn = null;

        try {

            imageUrl = new URL(images[position]);
            try {
                conn = (HttpURLConnection) imageUrl.openConnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
            conn.connect();
            imagen= BitmapFactory.decodeStream(conn.getInputStream());
            ob = new BitmapDrawable(imagen);
        } catch (IOException e) {
            e.printStackTrace();
        }

        imgflag.setBackgroundDrawable(ob);
        //imageLoader.displayImage(String.valueOf(Uri.parse(images[position])), imgflag, defaultOptions);
        ((ViewPager) container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
// Remove viewpager_item.xml from ViewPager
        ((ViewPager) container).removeView((RelativeLayout) object);

    }
}