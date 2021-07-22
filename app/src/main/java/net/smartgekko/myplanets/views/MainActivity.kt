package net.smartgekko.myplanets.views

import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.transition.TransitionManager
import com.google.android.material.appbar.AppBarLayout
import net.smartgekko.myplanets.R
import net.smartgekko.myplanets.databinding.ActivityMainBinding
import net.smartgekko.myplanets.utils.ROTATE_LEFT
import net.smartgekko.myplanets.utils.ROTATE_RIGHT
import net.smartgekko.myplanets.utils.WIKI_BASE_URL

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var planetsArray: ArrayList<LinearLayout>
    private lateinit var currentPlanetName: String

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

        binding.leftStep.setOnClickListener {
            rotateLeft()
        }
        binding.rightStep.setOnClickListener {
            rotateRight()
        }

        binding.sun.setOnClickListener {
            letsShowInfo(getString(R.string.sun),"")
        }
        binding.appbar.addOnOffsetChangedListener(object: AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                when(state) {
                    State.COLLAPSED -> { binding.textPlanet.visibility = View.VISIBLE }
                    State.EXPANDED -> { binding.textPlanet.visibility = View.INVISIBLE }
                    State.IDLE -> { binding.textPlanet.visibility = View.INVISIBLE }
                }
            }
        }
        )

        letsShowInfo(getString(R.string.jupiter)," (планета)")
    }

    private fun rotateRight() {
        rotateConstraints(ROTATE_RIGHT)
        TransitionManager.beginDelayedTransition(binding.planetsLayout)
    }

    private fun rotateLeft() {
        rotateConstraints(ROTATE_LEFT)
        TransitionManager.beginDelayedTransition(binding.planetsLayout)
    }

    private fun rotateConstraints(direction: Int) {
        var currentPlanet = ""
        for (item: LinearLayout in planetsArray) {
            val params = item.getLayoutParams() as ConstraintLayout.LayoutParams
            var currentAngle = params.circleAngle
            val nameText: TextView = item.findViewWithTag("planetName")

            when (direction) {
                ROTATE_LEFT -> {
                    currentAngle += 45
                    if (currentAngle > 360) {
                        params.circleAngle = 45f
                    } else {
                        params.circleAngle = currentAngle

                    }
                }
                ROTATE_RIGHT -> {
                    currentAngle -= 45
                    if (currentAngle < 0) {
                        params.circleAngle = 315f
                    } else {
                        params.circleAngle = currentAngle
                    }
                }
            }

            item.layoutParams = params
            if (currentAngle == 180f) {
                currentPlanet = item.tag.toString()
                nameText.textSize = 30f
            } else {
                nameText.textSize = 12f
            }
        }
        letsShowInfo(currentPlanet," (планета)")

    }

    private fun letsShowInfo(currentPlanet: String, extendedString: String) {
        binding.searchWebView.loadUrl(WIKI_BASE_URL + currentPlanet + extendedString)
        binding.textPlanet.text = currentPlanet
        binding.textPlanet.text =currentPlanet
    }
}