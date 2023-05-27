package com.example.tiviclon.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tiviclon.R
import com.example.tiviclon.databinding.ActivityHomeBinding
import com.example.tiviclon.model.application.User
import com.fondesa.kpermissions.PermissionStatus
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.anyDenied
import com.fondesa.kpermissions.anyPermanentlyDenied
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.request.PermissionRequest
import com.google.android.gms.location.LocationServices
import java.util.*

class HomeActivity : AppCompatActivity(), PermissionRequest.Listener {

    companion object {
        const val USER_INFO = "USER_INFO"
        fun navigateToHomeActivity(
            context: Context,
            user: User,
        ) {
            val intent = Intent(context, HomeActivity::class.java).apply {
                putExtra(USER_INFO, user)
            }
            context.startActivity(intent)
        }

    }

    private lateinit var binding: ActivityHomeBinding

    private val request by lazy {
        permissionsBuilder(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        request.addListener(this)
        request.addListener {
            if (it.anyPermanentlyDenied()) {
                Toast.makeText(this, R.string.additional_listener_msg, Toast.LENGTH_SHORT).show()
            }
            if (it.anyDenied()) {
                binding.button.setOnClickListener {
                    request.send()
                }
            }
            if (it.allGranted()) {
                showLocation()
            }
        }

        setUpUI()
        setUpListeners()
        request.send()
    }


    private fun setUpUI() {
    }

    fun setUpListeners() {
        //nothing to do
    }

    @SuppressLint("MissingPermission")
    private fun showLocation() {
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location ->
            binding.tvGreeting.text =
                buildString {
                    append("your location is: ")
                    append(getCityName(location.latitude, location.longitude))
                }
        }

    }

    private fun getCityName(lat: Double, long: Double): String {
        val geoCoder = Geocoder(this, Locale.getDefault())
        val address = geoCoder.getFromLocation(lat, long, 3)
        val cityName = address?.get(0)?.locality ?: "city not found"
        val countryName = address?.get(0)?.countryName ?: "country not found"
        Log.d("Debug:", "Your City: $cityName ; your Country $countryName")
        return cityName
    }

    override fun onPermissionsResult(result: List<PermissionStatus>) {
        when {
            result.anyPermanentlyDenied() -> {
                binding.tvGreeting.text = getString(R.string.permissions_denied)
            }
            result.anyDenied() -> {
                binding.tvGreeting.text = getString(R.string.any_permission_denied)
            }
            result.allGranted() -> {
                binding.tvGreeting.text = getString(R.string.permissions_granted)
            }
        }

    }
}