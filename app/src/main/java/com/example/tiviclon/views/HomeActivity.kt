package com.example.tiviclon.views

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.fragment.app.Fragment
import com.example.tiviclon.R
import com.example.tiviclon.databinding.ActivityHomeBinding
import com.example.tiviclon.model.application.Show
import com.example.tiviclon.views.homeFragments.FragmentCommonComunication
import com.example.tiviclon.views.homeFragments.IActionsFragment
import com.example.tiviclon.views.homeFragments.detailShow.DetailShowActivity
import com.example.tiviclon.views.homeFragments.discover.DiscoveryFragment
import com.example.tiviclon.views.homeFragments.library.LibraryFragment
import com.fondesa.kpermissions.PermissionStatus
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.anyDenied
import com.fondesa.kpermissions.anyPermanentlyDenied
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.request.PermissionRequest
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.coroutineScope
import java.util.*
import kotlin.coroutines.coroutineContext

class HomeActivity : AppCompatActivity(), PermissionRequest.Listener, FragmentCommonComunication,
    IActionsFragment {

    private lateinit var binding: ActivityHomeBinding
    private var logged = false
    private var currentCityName = "none"

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
                requestLocation()
            }
            if (it.allGranted()) {
                requestLocation()
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

        when (item.itemId) {
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
                builder.setTitle(getString(R.string.my_account))
                builder.setMessage(getString(R.string.login_alert_subtitle))
                //builder.setPositiveButton("OK", DialogInterface.OnClickListener(function = x))
                if (!logged) {
                    builder.setPositiveButton(getString(R.string.dialog_login)) { _, _ ->
                        //login
                        LoginActivity.navigateToLoginActivity(this, responseLauncher)
                    }
                    builder.setNeutralButton(getString(R.string.dialog_neutral)) { _, _ ->
                        //register
                        RegisterActivity.navigateToRegisterActivity(this, responseLauncher)
                    }
                } else {
                    builder.setPositiveButton(getString(R.string.dialog_disconect)) { _, _ ->
                        //log out
                        val icon = binding.toolbar.menu.findItem(R.id.it_login).icon
                        icon?.let {
                            DrawableCompat.setTint(
                                it,
                                ContextCompat.getColor(this, android.R.color.black)
                            )
                        }
                        logged = false
                    }
                }

                builder.setNegativeButton(getString(R.string.dialog_cancel)) { _, _ ->
                    //Exit
                }
                builder.show()
            }
        }

        return super.onOptionsItemSelected(item)

    }

    private fun setUpUI() {
        with(binding) {
            toolbar.title = getString(R.string.app_name)
            toolbar.setTitleTextColor(Color.WHITE)
            //appBar will not work without this
            setSupportActionBar(toolbar)
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_view, fragment)
        transaction.commit()
    }

    private fun setUpListeners() {
        with(binding){
            bottomNavBar.setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.action_discover -> {
                        loadFragment(DiscoveryFragment())
                        true
                    }
                    R.id.action_library -> {
                        loadFragment(LibraryFragment())
                        true
                    }
                    R.id.action_search -> {
                        loadFragment(DiscoveryFragment())
                        true
                    }
                    else -> {
                        true
                    }
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun requestLocation() {
        //FusedLocationProviderClient.lastLocation needs at least one loaction in the device. Launch G Maps app if it returns null.
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationProviderClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                setCityName(it.latitude, it.longitude)

            }
        }
    }

    private fun setCityName(lat: Double, long: Double) {
        val geoCoder = Geocoder(this, Locale("es")) //Locale.English do the trick too.
        val address = geoCoder.getFromLocation(lat, long, 3)
        val cityName = address?.get(0)?.locality ?: "city not found"
        val countryName = address?.get(0)?.countryName ?: "country not found"
        Log.d("Debug:", "Your City: $cityName ; your Country $countryName")
        currentCityName = countryName
    }

    override fun getLocation() = currentCityName

    override fun onPermissionsResult(result: List<PermissionStatus>) {
        requestLocation()
        when {
            result.anyPermanentlyDenied() -> {
                currentCityName = getString(R.string.all_permissions_denied)
            }
            result.anyDenied() -> {
                currentCityName = getString(R.string.any_permissions_denied)
                loadFragment(LibraryFragment())
            }
            result.allGranted() -> {
                //TODO
                loadFragment(LibraryFragment())
                with(binding){
                    bottomNavBar.selectedItemId = R.id.action_library
                }
            }
        }
    }

    private val responseLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            when (activityResult.resultCode) {
                RESULT_OK -> {
                    val name =
                        activityResult.data?.getStringExtra(LoginActivity.LOGIN_NAME).orEmpty()
                    Toast.makeText(
                        this,
                        buildString {
        append(getString(R.string.login_success))
        append(name)
    },
                        Toast.LENGTH_SHORT
                    ).show()
                    val icon = binding.toolbar.menu.findItem(R.id.it_login).icon
                    icon?.let {
                        DrawableCompat.setTint(
                            it,
                            ContextCompat.getColor(this, android.R.color.holo_green_dark)
                        )
                    }
                    logged = true
                }
                RegisterActivity.RESULT_OK_REGISTER -> {
                    Toast.makeText(
                        this,
                        getString(R.string.register_ok),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    Toast.makeText(this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun goShowDetail(show: Show) {
        DetailShowActivity.navigateToShowDetailActivity(this,show)
    }

    override fun getShows(): List<Show> {
        return listOf(
            Show(1,"Breaking Bad","When Walter White, a New Mexico chemistry teacher, is diagnosed with Stage III cancer and given a prognosis of only two years left to live. He becomes filled with a sense of fearlessness and an unrelenting desire to secure his family's financial future at any cost as he enters the dangerous world of drugs and crime",R.drawable.breakingbad),
            Show(2,"Spider-Man: Across the Spider-Verse","After reuniting with Gwen Stacy, Brooklyn’s full-time, friendly neighborhood Spider-Man is catapulted across the Multiverse, where he encounters the Spider Society, a team of Spider-People charged with protecting the Multiverse’s very existence. But when the heroes clash on how to handle a new threat, Miles finds himself pitted against the other Spiders and must set out on his own to save those he loves most",R.drawable.spiderman),
            Show(3,"John Wick: Chapter 4","With the price on his head ever increasing, John Wick uncovers a path to defeating The High Table. But before he can earn his freedom, Wick must face off against a new enemy with powerful alliances across the globe and forces that turn old friends into foes.",R.drawable.jhonwick),
            Show(4,"Succession","Follow the lives of the Roy family as they contemplate their future once their aging father begins to step back from the media and entertainment conglomerate they control.",R.drawable.sucession)
        )
    }

    override fun updateAppBarText(text: String) {
        with(binding) {
            toolbar.title = text
        }
    }
}