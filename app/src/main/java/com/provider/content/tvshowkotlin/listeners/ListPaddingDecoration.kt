package com.provider.content.tvshowkotlin.listeners

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import com.provider.content.tvshowkotlin.R

/**
 * Created by Nand Kishor Patidar on 06,August,2018
 * Email nandkishor.patidar@ratufa.com.
 *
 */
class ListPaddingDecoration(context: Context, val paddingLeft: Int, val paddingRight: Int) : RecyclerView.ItemDecoration(){
    private var mDivider: Drawable? = null

    init {
        mDivider = ContextCompat.getDrawable(context, R.drawable.divider_medium)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        //super.onDrawOver(c, parent, state)
        val left = parent.paddingLeft + paddingLeft
        val right = parent.width - parent.paddingRight + paddingRight

        val childCount = parent.childCount

        for (i in 0 until childCount) {
            val child = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top = child.bottom + params.bottomMargin + 10
            val bottom = top + (mDivider?.intrinsicHeight ?: 0) + 10

            mDivider?.let {
                it.setBounds(left, top, right, bottom)
                it.draw(c)
            }

        }
    }
}