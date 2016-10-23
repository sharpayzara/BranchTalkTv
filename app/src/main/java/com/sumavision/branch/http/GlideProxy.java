package com.sumavision.branch.http;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.sumavision.branch.R;

public class GlideProxy {

    public static final String ANDROID_RESOURCE = "android.resource://";
    public static final String FOREWARD_SLASH = "/";

    private GlideProxy() {
    }

    private static class GlideControlHolder {
        private static GlideProxy instance = new GlideProxy();
    }

    public static GlideProxy getInstance() {
        return GlideControlHolder.instance;
    }

    // 将资源ID转为Uri
    public Uri resourceIdToUri(Context context, int resourceId) {
        return Uri.parse(ANDROID_RESOURCE + context.getPackageName() + FOREWARD_SLASH + resourceId);
    }

    // 加载drawable图片
    public void loadResImage(Context context, int resId, ImageView imageView) {
        Glide.with(context)
                .load(resourceIdToUri(context, resId))
                .placeholder(R.mipmap.default_img_bg)
                .error(R.mipmap.default_img_bg)
                .crossFade()
                .into(imageView);
    }

    public void loadResImage2(Context context, int resId, ImageView imageView) {
        Glide.with(context)
                .load(resourceIdToUri(context, resId))
                .crossFade()
                .into(imageView);
    }

    // 加载drawable图片
    public void loadResImage(Context context, int resId, ImageView imageView, int defaultId) {
        Glide.with(context)
                .load(resourceIdToUri(context, resId))
                .placeholder(defaultId)
                .error(defaultId)
                .crossFade()
                .into(imageView);
    }

    // 加载本地图片
    public void loadLocalImage(Context context, String path, ImageView imageView) {
        Glide.with(context)
                .load("file://" + path)
                .placeholder(R.mipmap.default_img_bg)
                .error(R.mipmap.default_img_bg)
                .crossFade()
                .into(imageView);
    }

    // 加载网络图片
    public void loadVImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .animate(R.anim.image_load)
                .placeholder(R.mipmap.default_img_v)
                .error(R.mipmap.error_vertical)
                .crossFade()
                .into(imageView);
    }
    // 加载网络图片
    public void loadHImage(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .animate(R.anim.image_load)
                .placeholder(R.mipmap.default_img_bg)
                .error(R.mipmap.error_horizontal)
                .crossFade()
                .into(imageView);
    }
}
