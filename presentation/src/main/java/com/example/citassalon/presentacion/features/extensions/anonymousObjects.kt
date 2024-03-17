package com.example.citassalon.presentacion.features.extensions

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

fun EditText.getTextWatcher(checkMyForm: () -> Unit): TextWatcher {
    return object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            checkMyForm()
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            checkMyForm()
        }

        override fun afterTextChanged(s: Editable) {
            checkMyForm()
        }
    }
}

fun MotionLayout.setOnTransitionListener(
    onStart: () -> Unit = {},
    onChange: () -> Unit = {},
    onComplete: () -> Unit = {},
    onTrigger: () -> Unit = {}
): MotionLayout.TransitionListener {
    return object : MotionLayout.TransitionListener {
        override fun onTransitionStarted(p0: MotionLayout?, p1: Int, p2: Int) {
            onStart()
        }

        override fun onTransitionChange(p0: MotionLayout?, p1: Int, p2: Int, p3: Float) {
            onChange()
        }

        override fun onTransitionCompleted(p0: MotionLayout?, p1: Int) {
            onComplete()
        }

        override fun onTransitionTrigger(p0: MotionLayout?, p1: Int, p2: Boolean, p3: Float) {
            onTrigger()
        }

    }
}

fun RecyclerView.myOnScrolled(getNextPage: () -> Unit) {
    val layoutManager = LinearLayoutManager(this.context)
    layoutManager.orientation = LinearLayoutManager.VERTICAL
    this.layoutManager = layoutManager
    this.addOnScrollListener(object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            if (dy > 0) {
                val visibleItemCount: Int = layoutManager.childCount
                val totalItemCount: Int = layoutManager.itemCount
                val pastVisibleItems: Int = layoutManager.findFirstVisibleItemPosition()
                if (visibleItemCount + pastVisibleItems == totalItemCount) {
                    getNextPage()
                }
            }
        }
    })
}

