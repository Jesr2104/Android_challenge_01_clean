package com.justjump.android_challenge_01_clean

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.justjump.android_challenge_01_clean.databinding.FragmentBrowserTweetsBinding
import com.justjump.android_challenge_01_clean.dialog.DialogLiveSpanSetup
import com.justjump.android_challenge_01_clean.viewmodel.BrowserTweetsViewModel
import com.justjump.domain.tweets.TweetsDataModel

class BrowserTweets : Fragment(), DialogLiveSpanSetup.SetupValue, OnMapReadyCallback {

    private lateinit var binding: FragmentBrowserTweetsBinding
    private lateinit var browserTweetsViewModel: BrowserTweetsViewModel
    private lateinit var navController: NavController
    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        binding = FragmentBrowserTweetsBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        browserTweetsViewModel = ViewModelProvider(this).get(BrowserTweetsViewModel::class.java)
        navController = view.findNavController()

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = childFragmentManager
            .findFragmentById(R.id.main_map_fragment) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Observer definitions
        //==================================
        val myObserverLiveSpan = Observer<Int> {
            binding.liveSpanCurrentValue.text = "${browserTweetsViewModel.liveSpanValue.value} s"
        }

        val myObserverUpdateUI = Observer<TweetsDataModel> {
            val newLocation = LatLng(it.location_latitude,it.location_longitude)
            val marker = MarkerOptions().position(newLocation).title(it.user)
            val valueMarker = mMap.addMarker(marker)

            browserTweetsViewModel.liveSpanValue.value?.let { time ->
                countDownTimer(valueMarker,
                browserTweetsViewModel.secondToMillisecond(time))
            }
            browserTweetsViewModel.addNewTweets(it)
        }
        //==================================

        browserTweetsViewModel.liveSpanValue.observe(viewLifecycleOwner, myObserverLiveSpan)
        browserTweetsViewModel.newPin.observe(viewLifecycleOwner,myObserverUpdateUI)

        // event for search tweets
        binding.buttonSearch.setOnClickListener {
            if(binding.textSearch.text!!.isNotEmpty()){
                browserTweetsViewModel.searchTweets(binding.textSearch.text.toString())
            } else {
                Toast.makeText(requireContext(), getString(R.string.messege_field_empty), Toast.LENGTH_SHORT).show()
            }
        }

        // event to change the live span time
        binding.setLifeSpan.setOnClickListener {
            val newDialog = DialogLiveSpanSetup(this)
            newDialog.show(requireActivity().supportFragmentManager, "")
        }
    }

    private fun countDownTimer(marker: Marker, interval: Long) =
        object : CountDownTimer(interval, interval) {
            override fun onTick(millisUntilFinished: Long) = Unit
            override fun onFinish() { marker.remove() }
        }.start()

    override fun getValue(value: Int) { browserTweetsViewModel.liveSpanValue.value = value }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnMarkerClickListener { marker ->
            val itTweet = browserTweetsViewModel.getTweetForTitle(marker.title)
            val action = BrowserTweetsDirections.actionBrowserTweetsToShowDetails(itTweet)
            navController.navigate(action)

            false
        }
    }
}