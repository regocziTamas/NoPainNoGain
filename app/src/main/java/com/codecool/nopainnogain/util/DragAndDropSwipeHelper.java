package com.codecool.nopainnogain.util;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import java.util.Collections;

public class DragAndDropSwipeHelper extends ItemTouchHelper.SimpleCallback {
    DragAndDropSwipeHelperListener listener;

    public DragAndDropSwipeHelper(int dragDirs, int swipeDirs, DragAndDropSwipeHelperListener listener) {
        super(dragDirs,swipeDirs);
        this.listener = listener;
    }



    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.DOWN | ItemTouchHelper.UP | ItemTouchHelper.START | ItemTouchHelper.END;
        int swipeFlags = ItemTouchHelper.RIGHT;
        return makeMovementFlags(dragFlags,swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        listener.onDragAndDropped(viewHolder,target);
        return false;
    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder,direction,viewHolder.getAdapterPosition());
    }

    public interface DragAndDropSwipeHelperListener{
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
        void onDragAndDropped(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target);
    }
}
