package com.example.citassalon.util

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class SwipeRecycler() {


    fun swipe(recycler: RecyclerView,listenr: SwipeRecyclerListenr) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                listenr.onSwipe(viewHolder.adapterPosition)
            }

        }).attachToRecyclerView(recycler)
    }


     interface SwipeRecyclerListenr{
         fun onMove()
         fun onSwipe(position: Int)
    }
}