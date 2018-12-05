package com.codecool.nopainnogain.util;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

public class DragAndDropSwipeHelper extends ItemTouchHelper.SimpleCallback {
    DragAndDropSwipeHelperListener listener;

    public DragAndDropSwipeHelper(int dragDirs, int swipeDirs, DragAndDropSwipeHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder,direction,viewHolder.getAdapterPosition());
    }

    public interface DragAndDropSwipeHelperListener{
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
