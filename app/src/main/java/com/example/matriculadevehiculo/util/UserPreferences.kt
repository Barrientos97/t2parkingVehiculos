package com.example.matriculadevehiculo.util

import android.content.Context
import android.preference.PreferenceManager


object UserPreferences {


    private const val USER_PREFERENCES_VEHICLE = "USER_PREFERENCES_VEHICLE"
    private const val USER_PREFERENCES_PLATE = "USER_PREFERENCES_PLATE"
    private const val USER_PREFERENCES_ALIAS = "USER_PREFERENCES_ALIAS"
    private const val USER_PREFERENCES_VEHICLE_TYPE = "USER_PREFERENCES_VEHICLE_TYPE"
    private const val USER_PREFERENCES_VEHICLE_REGISTRATION = "USER_PREFERENCES_VEHICLE_REGISTRATION"
    private const val USER_PREFERENCES_BOOLEAN_KEY = "USER_PREFERENCES_BOOLEAN_KEY"



    fun setUserPrefVehicle(context: Context, value: String) {
        setStringUserPreference(context, USER_PREFERENCES_VEHICLE, value)
    }

    fun setUserPrefPlate(context: Context, value: String) {
        setStringUserPreference(context, USER_PREFERENCES_PLATE, value)
    }

    fun setUserPrefAlias(context: Context, value: String) {
        setStringUserPreference(context, USER_PREFERENCES_ALIAS, value)
    }

    fun setUserPrefVehicleType(context: Context, value: Int) {
        setIntUserPreference(context, USER_PREFERENCES_VEHICLE_TYPE, value)
    }

    fun setUserPrefVehicleRegistration(context: Context, value: String) {
        setStringUserPreference(context, USER_PREFERENCES_VEHICLE_REGISTRATION, value)
    }
    fun setUserPrefSwitchcleRegistration(context: Context, boolean: Boolean) {
        setBooleanUserPreference(context, USER_PREFERENCES_BOOLEAN_KEY, boolean)
    }
    fun getUserPrefVehicle(context: Context): String {
        return getStringUserPreference(context, USER_PREFERENCES_VEHICLE, "no_type_vehicle")
    }
    fun getUserPrefPlate(context: Context): String {
        return getStringUserPreference(context, USER_PREFERENCES_PLATE, "no_plate")
    }
    fun getUserPrefAlias(context: Context): String {
        return getStringUserPreference(context, USER_PREFERENCES_ALIAS, "no_label")
    }
    fun getUserPrefVehicleType(context: Context): Int {
        return getIntUserPreference(context, USER_PREFERENCES_VEHICLE_TYPE, 0)
    }
    fun getUserPrefVehicleRegistration(context: Context): String {
        return getStringUserPreference(context, USER_PREFERENCES_VEHICLE_REGISTRATION, "no_registration_number")
    }
    fun getUserPrefSwitchcleRegistration(context: Context): Boolean {
        return getBooleanUserPreference(context, USER_PREFERENCES_BOOLEAN_KEY, false)
    }

    /**
     * Set and Get user preferences
     */

    private fun setStringUserPreference(context: Context, key: String, value: String) {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        with(sharedPref.edit()) {
            putString(key, value)
            apply()
        }
    }

    private fun setIntUserPreference(context: Context, key: String, value: Int) {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        with(sharedPref.edit()) {
            putInt(key, value)
            apply()
        }
    }

    private fun setBooleanUserPreference(context: Context, key: String, boolean: Boolean){
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        with(sharedPref.edit()) {
            putBoolean(key, boolean)
            apply()
        }
    }


    private fun getStringUserPreference(
        context: Context,
        key: String,
        defaultValue: String
    ): String {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPref.getString(key, defaultValue) ?: defaultValue
    }

    private fun getIntUserPreference(
        context: Context,
        key: String,
        defaultValue: Int
    ): Int {
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPref.getInt(key, defaultValue) ?: defaultValue
    }

    private fun getBooleanUserPreference(
        context: Context,
        key: String,
        boolean: Boolean
    ):Boolean{
        val sharedPref = PreferenceManager.getDefaultSharedPreferences(context)
        return sharedPref.getBoolean(key, boolean)
    }


}