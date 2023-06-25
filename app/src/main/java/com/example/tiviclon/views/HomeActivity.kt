package com.example.tiviclon.views

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.*
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import com.example.tiviclon.R
import com.example.tiviclon.TiviClon
import com.example.tiviclon.container.AppContainer
import com.example.tiviclon.databinding.ActivityHomeBinding
import com.example.tiviclon.model.application.DetailShow
import com.example.tiviclon.model.application.Show
import com.example.tiviclon.views.detailShow.DetailShowActivity
import com.example.tiviclon.views.homeFragments.FragmentCommonComunication
import com.example.tiviclon.views.homeFragments.IActionsFragment
import com.example.tiviclon.views.homeFragments.discover.DiscoveryFragment
import com.example.tiviclon.views.homeFragments.library.LibraryFragment
import com.example.tiviclon.views.homeFragments.search.SearchFragment
import com.fondesa.kpermissions.PermissionStatus
import com.fondesa.kpermissions.allGranted
import com.fondesa.kpermissions.anyDenied
import com.fondesa.kpermissions.anyPermanentlyDenied
import com.fondesa.kpermissions.extension.permissionsBuilder
import com.fondesa.kpermissions.request.PermissionRequest
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.*
import java.io.IOException
import java.util.*

class HomeActivity : AppCompatActivity(), PermissionRequest.Listener, FragmentCommonComunication,
    IActionsFragment {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var appContainer: AppContainer
    private var logged = false
    private var loggedUser = ""
    private var currentCityName = "please, check your enable your gps"
    private val shows: MutableList<Show> = mutableListOf()
    private val scope =
        CoroutineScope(Dispatchers.Main + SupervisorJob() + CoroutineExceptionHandler { _, throwable ->
            throwable.printStackTrace()
            loadShowsFromBD()
            loadFragment(LibraryFragment())
            hideProgressBar()
        })

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

        appContainer = TiviClon.appContainer

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
        setUpState()
        setUpUI()
        setUpListeners()
        val liveDataShows = MutableLiveData<List<Show>>()
        liveDataShows.observe(this) {
            scope.launch(Dispatchers.IO) {
                showProgressBar()
                shows.clear()
                shows.addAll(it)
                loadFragment(LibraryFragment())
                hideProgressBar()
            }
        }
        scope.launch {
            val appShows = appContainer.repository.getShows()
            liveDataShows.postValue(appShows)
        }
    }

    private fun loadShowsFromBD() {
        shows.clear()
        scope.launch(Dispatchers.IO) {
            val bdShows = appContainer.repository.getShows()
            shows.clear()
            shows.addAll(bdShows)
        }
    }

    override fun hideProgressBar() {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                binding.progressBar.visibility = View.GONE
            }
        }
    }

    override fun showProgressBar() {
        GlobalScope.launch {
            withContext(Dispatchers.Main) {
                binding.progressBar.visibility = View.VISIBLE
                request.send()
            }
        }
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
                val webIntent = Intent(ACTION_VIEW, Uri.parse("https://trakt.tv/"))
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
                        setLoggedState(false, "")
                        loadFragment(LibraryFragment())

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

    private fun setUpState() {
        logged = appContainer.repository.getLoginState()
        appContainer.repository.getLoggedUser()?.let {
            loggedUser = it
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container_view, fragment)
        transaction.commit()
    }

    private fun setUpListeners() {
        with(binding) {
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
                        loadFragment(SearchFragment())
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
        var address: List<Address>? = null
        try {
            address = geoCoder.getFromLocation(lat, long, 3)
        } catch (e: IOException) {
            Log.i("Exception", "Geocoder is not working")
            Toast.makeText(this, getString(R.string.gps_error), Toast.LENGTH_SHORT).show()
        }

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
                with(binding) {
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
                    setLoggedState(true, name)
                    loadFragment(LibraryFragment())
                }
                RegisterActivity.RESULT_OK_REGISTER -> {
                    Toast.makeText(
                        this,
                        getString(R.string.register_ok),
                        Toast.LENGTH_SHORT
                    ).show()
                    scope.launch {
                        appContainer.repository.getAllUsers().forEach {
                            Log.i("BD_USERS", it.toString())
                        }
                    }
                }
                else -> {
                    Toast.makeText(this, getString(R.string.login_fail), Toast.LENGTH_SHORT).show()
                }
            }
        }

    override fun isUserLogged() = logged
    private fun setLoggedState(isLogged: Boolean, name: String) {
        appContainer.repository.saveLoginState(isLogged)
        appContainer.repository.saveLoggedUser(name)
        logged = isLogged
    }

    override fun goShowDetail(id: Int) {
        DetailShowActivity.navigateToShowDetailActivity(this, id)
    }

    override fun getShows(onShowsRetrieved: (List<Show>) -> Unit) {
        onShowsRetrieved(shows)
    }

    override fun setPrefShow(idShow: String) {
        // nothing to do
    }

    override fun deletePrefShow(idShow: String) {
        //nothing to do
    }

    override fun getPrefsShows(onShowsRetrieved: (List<Int>) -> Unit) {
        val showIds = mutableListOf<Int>()
        scope.launch {
            appContainer.repository.getLoggedUser()?.let { usernameId ->
                Log.i("USERNAME", usernameId)
                Log.i("USERNAME", loggedUser)
                val retreivedShows = appContainer.repository.getFavShows(usernameId)
                showIds.addAll(retreivedShows.map {
                    it.toInt()
                })
            }
            withContext(Dispatchers.Main) {
                onShowsRetrieved(showIds)
            }
        }
    }

    override fun getDetailShows(
        id: Int,
        scope: CoroutineScope,
        onShowRetrieved: (DetailShow) -> Unit
    ) {
        //nothing to do
    }

    override fun updateAppBarText(text: String) {
        with(binding) {
            toolbar.title = text
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        scope.cancel()
    }
}