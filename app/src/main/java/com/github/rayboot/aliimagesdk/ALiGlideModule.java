package com.github.rayboot.aliimagesdk;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.module.GlideModule;

import java.io.InputStream;

/**
 * author: rayboot  Created on 15/9/11.
 * email : sy0725work@gmail.com
 */
public class ALiGlideModule implements GlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDecodeFormat(DecodeFormat.PREFER_ARGB_8888);
    }

    @Override
    public void registerComponents(Context context, Glide glide) {
        glide.register(ALiImageURL.class, InputStream.class,
                new ALiUrlLoader.Factory());
    }
}
