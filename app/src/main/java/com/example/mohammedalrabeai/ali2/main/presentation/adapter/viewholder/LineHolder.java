package com.example.mohammedalrabeai.ali2.main.presentation.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import com.example.mohammedalrabeai.ali2.R;
//import com.orafaaraujo.rxrecyclerexample.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by rafael on 15/11/16.
 */

public class LineHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.main_line_title)
    public TextView title;

    @BindView(R.id.main_line_more)
    public ImageButton moreButton;

    @BindView(R.id.main_line_delete)
    public ImageButton deleteButton;

    public LineHolder(View itemView) {
        super(itemView);
//        title= itemView.findViewById(R.id.main_line_title);
//        moreButton= itemView.findViewById(R.id.main_line_more);
//        deleteButton= itemView.findViewById(R.id.main_line_delete);
        ButterKnife.bind(this, itemView);
    }
}
