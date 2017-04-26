package tut.flightbookingsystem.adapter;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class GlideAdapter {

    // For a simple image list:
    public static void setImage(Context context,
                                int url,
                                final ImageView imageView) {
        Glide.with(context).load(url)
//                .thumbnail(Glide.with(context).load(R.drawable.loading_spinner))
                .crossFade()
                .into(imageView);
    }

    // For a simple image list:
    public static void setImage(Context context,
                                String url,
                                final ImageView imageView) {
        Glide.with(context).load(url)
//                .thumbnail(Glide.with(context).load(R.drawable.loading_spinner))
                .crossFade()
                .into(imageView);
    }

}
