package com.envirocleanadmin.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import com.envirocleanadmin.base.BaseBindingAdapter
import com.envirocleanadmin.base.BaseBindingViewHolder
import com.envirocleanadmin.databinding.ItemRowCommunityBinding
import com.envirocleanadmin.databinding.LoadMoreProgressBinding


/**
 * Created by imobdev on 23/3/20
 */
class CommunityAdapter : BaseBindingAdapter<String>() {
    val ITEM = 0
    val LOADING = 1
    lateinit var viewHolder: ViewDataBinding
    private var isLoadingAdded = false
    override fun bind(inflater: LayoutInflater, parent: ViewGroup, viewType: Int): ViewDataBinding {
        when (viewType) {
            ITEM ->
                viewHolder = ItemRowCommunityBinding.inflate(inflater, parent, false)
            LOADING -> {
                viewHolder = LoadMoreProgressBinding.inflate(inflater, parent, false)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder, position: Int) {

        when (getItemViewType(position)) {
            ITEM -> {
                val binding = holder.binding as ItemRowCommunityBinding
                val item = items[position]
                item?.let {
                    binding.tvCommunitiesName.text=item
                }
            }
            LOADING -> {

            }
        }

    }

    override
    fun getItemViewType(position: Int): Int {
        return if (position == items.size - 1 && isLoadingAdded) LOADING else ITEM
    }
    fun filter(string: String) {
        items = ArrayList(allItems.filter { it!!.toLowerCase().contains(string.toLowerCase()) })
        notifyDataSetChanged()
    }
}