package com.tencent.qcloud.infinite.sample.tpg;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.tencent.qcloud.infinite.sample.R;
import com.tencent.qcloud.infinite.sample.base.DecoderMemoryGlideListAdapter;
import com.tencent.qcloud.infinite.sample.base.ImageBean;

import java.util.List;

/**
 * <p>
 * Created by jordanqin on 2023/5/31 10:39.
 * Copyright 2010-2020 Tencent Cloud. All Rights Reserved.
 */
public class TpgGlideSmallImageListAdapter extends DecoderMemoryGlideListAdapter {

    public TpgGlideSmallImageListAdapter(List<ImageBean> imageList) {
        super(imageList);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_small_image, parent, false);
        return new MyViewHolder(view);
    }
}
