package com.example.matriculadevehiculo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.matriculadevehiculo.databinding.ItemListZonaBinding
import com.example.matriculadevehiculo.modelos.Country

class ZoneAdapter(private val country:List<String>) : RecyclerView.Adapter<ZoneAdapter.viewHolder>()  {

    private var mSelectedItem = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = ItemListZonaBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
//        val item = country[position]
//        holder.render(item)

        holder.render(country[position], position, mSelectedItem)
        holder.binding.checkSelecc.isChecked = position == mSelectedItem;
    }

    override fun getItemCount(): Int = country.size


    inner class viewHolder(val binding: ItemListZonaBinding):RecyclerView.ViewHolder(binding.root) {
            fun render(country:String, position: Int, selectedPosition: Int){
                binding.departmentTxt.text = country

                binding.checkSelecc.setOnClickListener {
                if ((selectedPosition == -1 && position == 0))
                    binding.checkSelecc.isChecked = true
                else
                    binding.checkSelecc.isChecked = selectedPosition == position

                mSelectedItem=getAdapterPosition()
                notifyDataSetChanged()

            }
            }
    }
}