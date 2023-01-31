package com.example.matriculadevehiculo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.matriculadevehiculo.databinding.ItemListZonaBinding
import com.example.matriculadevehiculo.modelos.Country

class ZoneAdapter(private val country:List<Country>) : RecyclerView.Adapter<ZoneAdapter.viewHolder>()  {

    private var mSelectedItem = -1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = ItemListZonaBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
//        val item = country[position]
//        holder.render(item)

        holder.render(country[position], position, mSelectedItem)
        holder.binding.checkSelec.isChecked = position == mSelectedItem;
    }

    override fun getItemCount(): Int = country.size


    inner class viewHolder(val binding: ItemListZonaBinding):RecyclerView.ViewHolder(binding.root) {
            fun render(country:Country, position: Int, selectedPosition: Int){
                binding.departmentTxt.text = country.departamento

                    binding.checkSelec.tag = country
                    binding.checkSelec.setOnClickListener {
                    if ((selectedPosition == -1 && position == 0)){
                        binding.checkSelec.isChecked = true
                    }
                    else
                        binding.checkSelec.isChecked = selectedPosition == position

                    mSelectedItem=getAdapterPosition()
                    notifyDataSetChanged()

                }


            }
    }
}