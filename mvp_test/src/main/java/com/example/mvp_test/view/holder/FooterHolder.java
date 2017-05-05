package com.example.mvp_test.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mvp_test.R;

/**
 * Created by Administrator on 2017/5/3.
 */

public class FooterHolder extends RecyclerView.ViewHolder {
    public TextView tvFooter;

    public FooterHolder(View itemView) {

        super(itemView);
        tvFooter = (TextView) itemView.findViewById(R.id.tvFooter);
    }
}
