package com.busra.moviedemo.util

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class SpaceItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val position = parent.getChildLayoutPosition(view)
        with(outRect) {
            if (position == 0) {
                top = space
            }
            bottom = space
            left = space
            right = space
        }

        super.getItemOffsets(outRect, view, parent, state)
    }

}