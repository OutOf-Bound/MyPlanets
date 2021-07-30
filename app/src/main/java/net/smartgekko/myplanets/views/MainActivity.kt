package net.smartgekko.myplanets.views

import android.graphics.Rect
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.transition.*
import com.google.android.material.appbar.AppBarLayout
import net.smartgekko.myplanets.R
import net.smartgekko.myplanets.databinding.ActivityMainBinding
import net.smartgekko.myplanets.utils.*


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var planetsArray: ArrayList<LinearLayout>
    private var onePlanetView: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        planetsArray = arrayListOf(
            binding.the1,
            binding.the2,
            binding.the3,
            binding.the4,
            binding.the5,
            binding.the6,
            binding.the7,
            binding.the8
        )

        binding.searchWebView.getSettings().setJavaScriptEnabled(true)
        binding.searchWebView.getSettings().setPluginState(WebSettings.PluginState.ON)
        binding.searchWebView.webViewClient = WebViewClient()


        binding.sun.setOnClickListener {
            letsShowInfo(getString(R.string.sun), "")
        }

        binding.appbar.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                when (state) {
                    State.COLLAPSED -> {
                        binding.smallTumb.visibility = View.VISIBLE
                    }
                    State.EXPANDED -> {
                        checkAndSetCurrentState()
                    }

                    State.IDLE -> {
                        checkAndSetCurrentState()
                    }
                }
            }
        })

        binding.planetsGroup.setAllOnClickListener(View.OnClickListener {
            val nameText: TextView = it.findViewWithTag("planetName")
            letsShowOnePlanet(nameText.text.toString())

        })

        binding.sun.setOnClickListener {
            letsShowOnePlanet(resources.getString(R.string.sun))
        }

        binding.onePlanetImage.setOnClickListener {
            letsShowAllPlanets()
        }

        binding.smallTumb.setOnClickListener {
            letsShowAllPlanets()
            binding.appbar.setExpanded(true, true)
        }

        letsShowInfo(getString(R.string.jupiter), " (планета)")

    }

    private fun Group.setAllOnClickListener(listener: View.OnClickListener?) {
        referencedIds.forEach { id ->
            rootView.findViewById<View>(id).setOnClickListener(listener)
        }
    }

    private fun letsShowInfo(currentPlanet: String, extendedString: String) {
        binding.searchWebView.loadUrl(WIKI_BASE_URL + currentPlanet + extendedString)
        val slide = Slide()
        slide.slideEdge = Gravity.BOTTOM
        slide.setDuration(300)
        binding.textPlanet.visibility = View.INVISIBLE
        TransitionManager.beginDelayedTransition(binding.titleLayout,slide)
        binding.textPlanet.text = currentPlanet
        binding.textPlanet.visibility = View.VISIBLE
    }

    private fun letsShowOnePlanet(onePlanetName: String) {
        var extendString = ""
        when (onePlanetName) {
            resources.getString(R.string.sun) -> {
                binding.onePlanetImage.setImageResource(R.drawable.sun)
            }
            resources.getString(R.string.mercury) -> {
                binding.onePlanetImage.setImageResource(R.drawable.mercury)
            }
            resources.getString(R.string.venus) -> {
                binding.onePlanetImage.setImageResource(R.drawable.venus)
            }
            resources.getString(R.string.earth) -> {
                binding.onePlanetImage.setImageResource(R.drawable.earth)
            }
            resources.getString(R.string.mars) -> {
                binding.onePlanetImage.setImageResource(R.drawable.mars)
            }
            resources.getString(R.string.jupiter) -> {
                binding.onePlanetImage.setImageResource(R.drawable.jupiter)
            }
            resources.getString(R.string.saturn) -> {
                binding.onePlanetImage.setImageResource(R.drawable.saturn)
            }
            resources.getString(R.string.uranus) -> {
                binding.onePlanetImage.setImageResource(R.drawable.uranus)
                extendString = " (планета)"
            }
            resources.getString(R.string.neptune) -> {
                binding.onePlanetImage.setImageResource(R.drawable.neptune)
            }
        }

        binding.textPlanet.text = onePlanetName

        TransitionManager.beginDelayedTransition(binding.planetsLayout,Fade().setDuration(300))
        TransitionManager.beginDelayedTransition(binding.onePlanet,Fade().setDuration(300))
        hideSolarSystem()
        showOnePlanet()
        letsShowInfo(onePlanetName, extendString)
        setCurrentState(ONE_PLANET_STATE)
    }

    private fun letsShowAllPlanets() {
        TransitionManager.beginDelayedTransition(binding.onePlanet,Fade().setDuration(300))
        TransitionManager.beginDelayedTransition(binding.planetsLayout,Fade().setDuration(300))
        hideOnePlanet()
        showSolarSystem()
        letsShowInfo(getString(R.string.solar_system), "")
        setCurrentState(MULTI_PLANET_STATE)
    }

    private fun setCurrentState(state: Int) {
        when (state) {
            ONE_PLANET_STATE -> {
                binding.smallTumb.visibility = View.VISIBLE
                onePlanetView = true
            }
            else -> {
                binding.smallTumb.visibility = View.INVISIBLE
                onePlanetView = false
            }
        }
    }

    private fun checkAndSetCurrentState() {
        if (onePlanetView) {
            setCurrentState(ONE_PLANET_STATE)
        } else {
            setCurrentState(MULTI_PLANET_STATE)
        }
    }

    private fun showSolarSystem(){
        with(binding){
            the1.visibility = View.VISIBLE
            the2.visibility = View.VISIBLE
            the3.visibility = View.VISIBLE
            the4.visibility = View.VISIBLE
            the5.visibility = View.VISIBLE
            the6.visibility = View.VISIBLE
            the7.visibility = View.VISIBLE
            the8.visibility = View.VISIBLE
            sun.visibility = View.VISIBLE
        }
    }

    private fun hideSolarSystem(){
        with(binding){
            the1.visibility = View.INVISIBLE
            the2.visibility = View.INVISIBLE
            the3.visibility = View.INVISIBLE
            the4.visibility = View.INVISIBLE
            the5.visibility = View.INVISIBLE
            the6.visibility = View.INVISIBLE
            the7.visibility = View.INVISIBLE
            the8.visibility = View.INVISIBLE
            sun.visibility = View.INVISIBLE
        }
    }

    private fun showOnePlanet(){
        with(binding){
            onePlanet.visibility = View.VISIBLE
        }
    }

    private fun hideOnePlanet(){
        with(binding){
            onePlanet.visibility = View.INVISIBLE
        }
    }




}


