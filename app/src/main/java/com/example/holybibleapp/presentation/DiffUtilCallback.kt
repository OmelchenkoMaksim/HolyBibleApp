package com.example.holybibleapp.presentation

import androidx.recyclerview.widget.DiffUtil

class DiffUtilCallback(
    private val oldList: List<BookUi>,
    private val newList: List<BookUi>,
) : DiffUtil.Callback() {
    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
    // плохо писать так
        // oldList[oldItemPosition].id == newList[newItemPosition].id
        oldList[oldItemPosition].same(newList[newItemPosition])

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition].sameContent(newList[newItemPosition])
}