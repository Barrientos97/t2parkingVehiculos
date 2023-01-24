package com.example.matriculadevehiculo.fragment
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.matriculadevehiculo.data.AppDatabase
import com.example.matriculadevehiculo.R
import com.example.matriculadevehiculo.data.Vehicle
import com.example.matriculadevehiculo.databinding.FragmentEditdeletevehicleBinding
import com.example.matriculadevehiculo.util.UserPreferences
import kotlinx.coroutines.launch

class EditDeleteVehicleFragment(val vehicle: Vehicle) : Fragment() {

    private var _binding : FragmentEditdeletevehicleBinding? = null

    private val binding get() = _binding

    companion object{
        fun newInstance(idVehiculo: Vehicle): EditDeleteVehicleFragment {
            return EditDeleteVehicleFragment(idVehiculo)


        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditdeletevehicleBinding.inflate(inflater)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Setup vehicle type spinner
        setupVehicleTypeSpinner()

        // Setup plate info edit texts
        setupPlateInfoEditTexts()

        //get plate information
        getVehicleInformation()

        // On click listener
        onClick()

    }

    override fun onResume() {
        super.onResume()
        // On click listener
        onClick()
    }

    private fun setupVehicleTypeSpinner() {

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            requireContext(),
            R.array.vehicle_type_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->

            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            binding?.vehicleTypeSpinner?.adapter = adapter
        }

    }

    private fun setupPlateInfoEditTexts() {
        binding?.plateInfoPlateEt?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                // Set visibility to gone
                binding?.plateInfoPlateWarningTv?.visibility = View.GONE

                // Enable user edit text
                binding?.plateInfoPlateEt?.isSelected = false
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }

    private fun onClick() {

        // On exit button
        binding?.backBtn?.setOnClickListener {
            // On back pressed
            requireActivity().onBackPressed()
        }

        // On save button
        binding?.saveBtn?.setOnClickListener {

            if (binding?.plateInfoPlateEt?.text!!.isEmpty()) {

                // Set visibility to visible
                binding?.plateInfoPlateWarningTv?.visibility = View.VISIBLE

                // Disable plate edit text
                binding?.plateInfoPlateEt?.isSelected = true

            }else if (!validatePlate(binding?.plateInfoPlateEt?.text!!.toString())) {

                // Show toast message
                Toast.makeText(
                    requireContext(),
                    requireContext().getString(R.string.plate_info_wrong_plate_message),
                    Toast.LENGTH_SHORT
                ).show()

            } else {

                // On save user preferences
                onSaveUserPreferences()

                addNonDuplicatePlate()

            }

        }

        //On delete button
        binding?.deleteConst?.setOnClickListener {
            deleteVehicle()
        }
    }

    private fun addNonDuplicatePlate(){

        //inflate the layout dialog_predetermined_view
        val dialogBindingPredetermined = layoutInflater.inflate(R.layout.dialog_predetermined_view, null)
        // we declare the buttons and text
        val close_btn = dialogBindingPredetermined.findViewById<Button>(R.id.close_btn)
        val message_txt = dialogBindingPredetermined.findViewById<TextView>(R.id.message_txt)

        lifecycleScope.launch {
            // Get plate
            val plate = binding?.plateInfoPlateEt?.text.toString()

            val getPlate = AppDatabase.getDatabase(requireContext()).getVehiculoDao().getPlate(plate)

            if (getPlate != null && vehicle.id != getPlate.id ) {
                val dialog = Dialog(requireContext())
                dialog.setCancelable(true)
                dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.show()
                dialog.window!!.setGravity(Gravity.BOTTOM)

                dialog.setContentView(dialogBindingPredetermined)
                message_txt.text = getString(R.string.message_for_update_plate_text)

                close_btn.setOnClickListener{
                    dialog.dismiss()
                }

            }else {
                // Update predetermined data
                updatePredetermined()

                // Update plate data
                updateVehicle()

                // Show payment method info fragment
                viewVehicleFragment()

            }
        }
    }

    private fun getVehicleInformation(){
            //Block the switch
        if (vehicle.predetermined) binding?.defaultSettingsSwitch?.isEnabled = false

        //get plate data
        lifecycleScope.launch {
                    binding?.plateTxt?.text = vehicle.plate
                    binding?.hashtagTxt?.text = vehicle.hashtag
                    binding?.plateInfoPlateEt?.setText(vehicle.plate)
                    binding?.plateInfoTagEt?.setText(vehicle.hashtag)
                    binding?.plateInfoPlateIdEt?.setText(vehicle.plateId)
                    binding?.vehicleTypeSpinner?.setSelection(vehicle.typeVehicle)
                    binding?.defaultSettingsSwitch?.isChecked = vehicle.predetermined
        }
    }

    private fun updateVehicle(){
        // Get plate
        val plate = binding?.plateInfoPlateEt?.text!!.toString()
        // Get hashtag
        val hashtag = binding?.plateInfoTagEt?.text.toString()
        // Get plateId
        val plateId = binding?.plateInfoPlateIdEt?.text.toString()
        // Get vehicle
        val typeVehicle = binding?.vehicleTypeSpinner?.selectedItemPosition!!
        // Get predetermined
        val predetermined = binding?.defaultSettingsSwitch?.isChecked as Boolean

        //Update plate data
        lifecycleScope.launch{
            AppDatabase.getDatabase(requireContext()).getVehiculoDao()
                .updateIdVehicule(plate,hashtag,plateId,typeVehicle,predetermined,vehicle.id)
        }
    }

    private fun updatePredetermined(){
        // Get predetermined
        val predetermined = binding?.defaultSettingsSwitch?.isChecked as Boolean

        lifecycleScope.launch{

            AppDatabase.getDatabase(requireContext()).getVehiculoDao()
                .updatePredetemined(predetermined,vehicle.id)

        }
    }

    private fun deleteVehicle(){
        //inflate the layout dialog_view
        val dialogBinding = layoutInflater.inflate(R.layout.dialog_delete_view, null)
        // we declare the buttons and text
        val confirm_btn = dialogBinding.findViewById<Button>(R.id.confirm_btn)
        val cancel_txt = dialogBinding.findViewById<TextView>(R.id.cancel_txt)

        //inflate the layout dialog_predetermined_view
        val dialogBindingPredetermined = layoutInflater.inflate(R.layout.dialog_predetermined_view, null)
        // we declare the buttons and text
        val close_btn = dialogBindingPredetermined.findViewById<Button>(R.id.close_btn)
        val textView = dialogBindingPredetermined.findViewById<TextView>(R.id.message_txt)

        val dialog = Dialog(requireContext())
        dialog.setCancelable(true)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()
        dialog.window!!.setGravity(Gravity.BOTTOM)

        if (!vehicle.predetermined) {
            dialog.setContentView(dialogBinding)
            confirm_btn.setOnClickListener{
                lifecycleScope.launch {
                    //delete plate data
                  AppDatabase.getDatabase(requireContext()).getVehiculoDao().deleteIdVehicle(vehicle.id)
                    viewVehicleFragment()
                }
                dialog.dismiss()
            }
            cancel_txt.setOnClickListener{
                dialog.dismiss()
            }

        }
        else {
            dialog.setContentView(dialogBindingPredetermined)
            textView.text = getString(R.string.message_for_text_predetermined)
            close_btn.setOnClickListener{
                dialog.dismiss()
            }
        }

    }

    private fun validatePlate(plate: String): Boolean {
        return plate.matches("(^[A-Z0-9]{2,8}$)".toRegex())
    }

    private fun onSaveUserPreferences() {

        // Get plate
        val plate = binding?.plateInfoPlateEt?.text.toString()

        // Get vehicle
        val vehicle = (binding?.vehicleTypeSpinner?.selectedItem as String).let {
            if (it == requireContext().getString(R.string.vehicle_type_title))
                requireContext().getString(R.string.vehicle_type_car)
            else it
        }

        // Get vehicle type
        val vehicleType= (binding?.vehicleTypeSpinner?.selectedItem as String).let {
            if (it == requireContext().getString(R.string.vehicle_type_title))
                0
            else binding?.vehicleTypeSpinner?.selectedItemPosition
        }

        //Get Switch
        val Switch = binding?.defaultSettingsSwitch?.isChecked as Boolean

        // Save preferences
        UserPreferences.setUserPrefPlate(requireContext(), plate)
        UserPreferences.setUserPrefVehicle(requireContext(), vehicle)
        UserPreferences.setUserPrefVehicleType(requireContext(), vehicleType!!)
        UserPreferences.setUserPrefSwitchcleRegistration(requireContext(),Switch)


        // Tag
        binding?.plateInfoTagEt?.text.let {

            if (it!!.isNotEmpty()) {
                // Save preferences
                UserPreferences.setUserPrefAlias(requireContext(), it.toString())
            }
        }

        // Plate ID
        binding?.plateInfoPlateIdEt?.text.let {

            if (it!!.isNotEmpty()) {
                // Save preferences
                UserPreferences.setUserPrefVehicleRegistration(requireContext(), it.toString())
            }
        }
    }

    private fun viewVehicleFragment() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragment_container_view,
                ViewVehicleFragment.newInstance(),
                "guardar"
            )
            .addToBackStack("Main")
            .commit()
    }
}

