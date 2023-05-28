package com.example.tiviclon.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.*
import android.graphics.Color
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import androidx.core.view.get
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
    private var logged = false

    private val request by lazy {
        permissionsBuilder(
            Manifest.permission.ACCESS_FINE_LOCATION, //aproximate granted
            // Manifest.permission.ACCESS_COARSE_LOCATION //aproximate denied
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
                startActivity(Intent().apply {
                    action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
                    data = Uri.fromParts("package", packageName, null)
                })
                startActivity(intent)
            }
            if (it.anyDenied()) {
                //this is useful if you request more than one permission
                showLocation()
            }
            if (it.allGranted()) {
                showLocation()
            }
        }

        setUpUI()
        setUpListeners()
        request.send()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.itemId

        when (id) {
            R.id.it_location -> {
                request.send()
                return true
            }
            R.id.it_exit -> {
                finish()
                return true
            }
            R.id.it_about -> {
                //web intent
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://trakt.tv/"))
                startActivity(webIntent)
                return true
            }
            R.id.it_login -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Mi Cuenta")
                builder.setMessage("What would you like to do")
                //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))
                if (!logged) {
                    builder.setPositiveButton("Entrar") { _, _ ->
                        //login
                        LoginActivity.navigateToLoginActivity(this, responseLauncher)
                    }
                    builder.setNeutralButton("Registrarse") { _, _ ->
                        //register
                    }
                } else {
                    builder.setPositiveButton("Desconectar") { _, _ ->
                        //log out
                        val icon = binding.toolbar.menu.findItem(R.id.it_login).icon
                        icon?.let {
                            DrawableCompat.setTint(it,ContextCompat.getColor(this,android.R.color.black))
                        }
                        logged = false
                    }
                }

                builder.setNegativeButton("Cancelar") { _, _ ->
                    //Exit
                }
                builder.show()
            }
        }

        return super.onOptionsItemSelected(item)

    }

    private fun setUpUI() {
        with(binding) {
            toolbar.title = getString(R.string.Appname)
            toolbar.setTitleTextColor(Color.WHITE)
            //appBar will not work without this
            setSupportActionBar(toolbar)
        }
    }

    private fun setUpListeners() {
        //nothing to do
    }

    @SuppressLint("MissingPermission")
    private fun showLocation() {
        //FusedLocationProviderClient.lastLocation needs at least one loaction in the device. Launch G Maps app if it returns null.
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                binding.tvGreeting.text =
                    buildString {
                        append("your location is: ")
                        append(getCityName(it.latitude, it.longitude))
                    }
            }
        }

    }

    private fun getCityName(lat: Double, long: Double): String {
        val geoCoder = Geocoder(this, Locale("es")) //Locale.English do the trick too.
        val address = geoCoder.getFromLocation(lat, long, 3)
        val cityName = address?.get(0)?.locality ?: "city not found"
        val countryName = address?.get(0)?.countryName ?: "country not found"
        Log.d("Debug:", "Your City: $cityName ; your Country $countryName")
        return countryName
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

    private val responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            if (activityResult.resultCode == RESULT_OK) {
                val name =
                    activityResult.data?.getStringExtra(LoginActivity.LOGIN_NAME).orEmpty()
                Toast.makeText(
                    this,
                    "logeado con exito: user $name",
                    Toast.LENGTH_SHORT
                ).show()
                val icon = binding.toolbar.menu.findItem(R.id.it_login).icon
                icon?.let {
                    DrawableCompat.setTint(it,ContextCompat.getColor(this,android.R.color.holo_green_dark))
                }
                logged = true
            } else {
                Toast.makeText(this, "fallo en login", Toast.LENGTH_SHORT).show()
            }
        }
}