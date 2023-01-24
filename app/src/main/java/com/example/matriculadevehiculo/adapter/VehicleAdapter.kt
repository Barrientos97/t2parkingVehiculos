package com.example.matriculadevehiculo.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.matriculadevehiculo.data.Vehicle
import com.example.matriculadevehiculo.databinding.ItemListBinding

class VehicleAdapter(private val onClickListener: (Vehicle)->Unit): RecyclerView.Adapter<VehicleAdapter.viewHolder>() {

    private var list = mutableListOf<Vehicle>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
        val binding = ItemListBinding .inflate(LayoutInflater.from(parent.context),parent,false)
        return viewHolder(binding)
    }

    override fun onBindViewHolder(holder: viewHolder, position: Int) {
        val item = list[position]
        holder.render(item)


    }

    override fun getItemCount(): Int  = list.size

    fun setData(data: List<Vehicle>){
        list.apply {
            clear()
            addAll(data)
        }
    }

    inner class viewHolder(val binding: ItemListBinding):RecyclerView.ViewHolder(binding.root) {

        fun render(vehicle: Vehicle) {
            binding.txtPlate.text = vehicle.plate
            binding.txtHashtag.text = vehicle.hashtag

            itemView.setOnClickListener { onClickListener(vehicle)}

            if (!vehicle.predetermined) binding.txtPredetermined.visibility = View.GONE

            else binding.txtPredetermined.visibility = View.VISIBLE

        }
    }

}