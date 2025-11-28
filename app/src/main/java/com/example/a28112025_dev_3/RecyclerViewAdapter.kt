package com.example.a28112025_dev_3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.a28112025_dev_3.databinding.CompanyItemBinding

class RecyclerViewAdapter : ListAdapter<Company,RecyclerViewAdapter.MyViewHolder>(DiffUtilCallback()) {

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val binding = CompanyItemBinding.bind(itemView)
        fun bind(item : Company) = with(binding){
            companyNameTV.text = item.name
            companyFoundedYearTV.text= item.foundedYear.toString()
            companySizeTV.text = item.size.toString()
        }
    }

    class DiffUtilCallback : DiffUtil.ItemCallback<Company>() {
        override fun areItemsTheSame(oldItem: Company, newItem: Company): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Company, newItem: Company): Boolean {
            return oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.company_item,parent,false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}