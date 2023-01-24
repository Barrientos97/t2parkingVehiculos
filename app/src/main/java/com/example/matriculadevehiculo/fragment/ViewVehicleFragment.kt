package com.example.matriculadevehiculo.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.matriculadevehiculo.data.AppDatabase
import com.example.matriculadevehiculo.data.Vehicle
import com.example.matriculadevehiculo.R
import com.example.matriculadevehiculo.adapter.VehicleAdapter
import com.example.matriculadevehiculo.databinding.FragmentViewvehicleBinding
import kotlinx.coroutines.launch


class ViewVehicleFragment : Fragment() {

        private var _binding : FragmentViewvehicleBinding? = null
    private val binding get() = _binding

    companion object{
        fun newInstance(): ViewVehicleFragment {
            return ViewVehicleFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = FragmentViewvehicleBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //show plate list

            initRecyclerVie()

        // On click listener
        onClick()
    }

    override fun onResume() {
        super.onResume()
        Handler(Looper.getMainLooper()).postDelayed({
            initRecyclerVie()

        },500)
    }

    private fun initRecyclerVie(){
        binding?.viewAddVehicleRecycler?.visibility = View.VISIBLE
        binding?.shimmerViewContainer?.visibility = View.GONE
        //show plate list
        lifecycleScope.launch {
            val vehicleList = AppDatabase.getDatabase(requireContext()).getVehiculoDao().getAllVehicle()
            binding?.viewAddVehicleRecycler?.apply {
                layoutManager = LinearLayoutManager(requireContext())
                adapter = VehicleAdapter{t -> editDeleteVehicleFragment(t)} .apply {
                    lifecycleScope.launch {
                        setData(vehicleList)
                    }
                }
            }
        }
    }

    private fun onClick(){
        binding?.nextBtn?.setOnClickListener {

            addVehicleFragment()
        }
    }

    private fun addVehicleFragment() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container_view,
                AddVehicleFragment.newInstance(),
                "agregar"
            )
            .addToBackStack("Main")
            .commit()
    }

    private fun editDeleteVehicleFragment(id:Vehicle) {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container_view,
                EditDeleteVehicleFragment.newInstance(id),
                "modificar"
            )
            .addToBackStack("Main")
            .commit()
    }

}