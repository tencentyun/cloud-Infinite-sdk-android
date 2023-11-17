/*
 * Copyright (c) 2010-2020 Tencent Cloud. All rights reserved.
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all
 *  copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  SOFTWARE.
 */

package com.tencent.qcloud.infinite.sample.avif;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.qcloud.image.avif.Avif;
import com.tencent.qcloud.image.decoder.utils.ResourcesUtil;
import com.tencent.qcloud.infinite.sample.R;
import com.tencent.qcloud.infinite.sample.base.BaseActivity;

import java.io.IOException;

public class AvifDecodeActivity extends BaseActivity {
    private ImageView imageView;
    private RadioButton rb_avif;
    private RadioButton rb_avis;

    private SeekBar sb_width;
    private TextView tv_width;
    private SeekBar sb_height;
    private TextView tv_height;
    private SeekBar sb_x;
    private TextView tv_x;
    private SeekBar sb_y;
    private TextView tv_y;
    private SeekBar sb_insample;
    private TextView tv_insample;

    private byte[] imageData = null;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decode_avif);

        imageView = findViewById(R.id.imageView);
        sb_width = findViewById(R.id.sb_width);
        tv_width = findViewById(R.id.tv_width);
        sb_height = findViewById(R.id.sb_height);
        tv_height = findViewById(R.id.tv_height);
        sb_x = findViewById(R.id.sb_x);
        tv_x = findViewById(R.id.tv_x);
        sb_y = findViewById(R.id.sb_y);
        tv_y = findViewById(R.id.tv_y);
        sb_insample = findViewById(R.id.sb_insample);
        tv_insample = findViewById(R.id.tv_insample);

        rb_avif = findViewById(R.id.rb_avif);
        rb_avis = findViewById(R.id.rb_avis);
        initByType("avif");

        rb_avif.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initByType("avif");
            }
        });
        rb_avis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initByType("avis");
            }
        });

        findViewById(R.id.btn_load).setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View v) {
                if(sb_width.getProgress()<=0 || sb_height.getProgress()<=0){
                    Toast.makeText(AvifDecodeActivity.this, "宽高必须大于0", Toast.LENGTH_SHORT).show();
                    return;
                }

                Bitmap bitmap = null;
                if(rb_avif.isChecked()){
                    bitmap = Avif.decode(imageData,
                            sb_x.getProgress(), sb_y.getProgress(), sb_width.getProgress(), sb_height.getProgress(), sb_insample.getProgress());
                } else if(rb_avis.isChecked()){
                    Toast.makeText(AvifDecodeActivity.this, "avis暂时不支持区域解码", Toast.LENGTH_SHORT).show();
                }
                imageView.setImageBitmap(null);
                imageView.setImageBitmap(bitmap);
            }
        });

        sb_width.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_width.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb_height.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_height.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb_x.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_x.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb_y.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_y.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        sb_insample.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                tv_insample.setText(Integer.toString(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void initByType(String type){
        String fileName = "";
        if("avif".equals(type)){
            fileName = "1.avif";
        } else if("avis".equals(type)){
            fileName = "a8_3.avifs";
        }

        try {
            imageData = ResourcesUtil.readFileFromAssets(AvifDecodeActivity.this, fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Bitmap bitmap = null;
        if("avif".equals(type)){
            bitmap = Avif.decode(imageData);
        } else if("avis".equals(type)){
            bitmap = Avif.decode(imageData);
        }

        imageView.setImageBitmap(null);
        imageView.setImageBitmap(bitmap);
        tv_x.setText("0");
        sb_x.setMax(bitmap.getWidth());
        sb_x.setProgress(0);
        tv_y.setText("0");
        sb_y.setMax(bitmap.getHeight());
        sb_y.setProgress(0);
        tv_width.setText("0");
        sb_width.setMax(bitmap.getWidth());
        sb_width.setProgress(0);
        tv_height.setText("0");
        sb_height.setMax(bitmap.getHeight());
        sb_height.setProgress(0);
        tv_insample.setText("1");
        sb_insample.setProgress(1);
    }
}