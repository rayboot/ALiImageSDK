package com.github.rayboot.aliimagesdk;

import android.content.Context;

import com.bumptech.glide.load.model.GenericLoaderFactory;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.ModelCache;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.stream.BaseGlideUrlLoader;

import java.io.InputStream;

/**
 * author: rayboot  Created on 15/9/11.
 * email : sy0725work@gmail.com
 */
public class ALiUrlLoader extends BaseGlideUrlLoader<ALiImageURL> {

    public ALiUrlLoader(ModelLoader<GlideUrl, InputStream> concreteLoader, ModelCache<ALiImageURL, GlideUrl> modelCache) {
        super(concreteLoader, modelCache);
    }

    public static class Factory implements ModelLoaderFactory<ALiImageURL, InputStream> {
        private final ModelCache<ALiImageURL, GlideUrl> modelCache = new ModelCache<>(500);

        @Override
        public ModelLoader<ALiImageURL, InputStream> build(Context context,
                                                           GenericLoaderFactory factory) {
            return new ALiUrlLoader(factory.buildModelLoader(GlideUrl.class, InputStream.class),
                    modelCache);
        }

        @Override
        public void teardown() {
        }
    }

    @Override
    protected String getUrl(ALiImageURL model, int width, int height) {
        return model.resize(width, height).build();
    }
}
