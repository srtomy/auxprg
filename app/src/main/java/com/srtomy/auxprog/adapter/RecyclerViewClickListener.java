package com.srtomy.auxprog.adapter;

import android.view.View;

public interface RecyclerViewClickListener {
    void recyclerViewListClicked(View v, int position);
    void recyclerViewListLongClicked(View v, int position);
}