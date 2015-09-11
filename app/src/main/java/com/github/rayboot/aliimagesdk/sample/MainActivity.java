package com.github.rayboot.aliimagesdk.sample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.github.rayboot.aliimagesdk.ALiImageURL;

public class MainActivity extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.imageView);

        Glide.with(this)
                .load(new ALiImageURL("http://img.teamkn.com/i/gnFCPBrn.png")
                        .centerInside())
                .into(imageView);
    }
}
