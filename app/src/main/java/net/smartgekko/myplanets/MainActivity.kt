package net.smartgekko.myplanets

import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.transition.TransitionManager
import net.smartgekko.myplanets.databinding.ActivityMainBinding
import net.smartgekko.myplanets.utils.ROTATE_LEFT
import net.smartgekko.myplanets.utils.ROTATE_RIGHT
import net.smartgekko.myplanets.utils.WIKI_BASE_URL

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var planetsArray: ArrayList<LinearLayout>

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
            rotateConstraints(ROTATE_RIGHT)
            TransitionManager.beginDelayedTransition(binding.planetsLayout)
        }

        binding.rightStep.setOnClickListener {
            rotateConstraints(ROTATE_LEFT)
            TransitionManager.beginDelayedTransition(binding.planetsLayout)
        }

        binding.sun.setOnClickListener {
            letsShowWeb(WIKI_BASE_URL + getString(R.string.sun))
        }

        letsShowWeb(WIKI_BASE_URL + getString(R.string.jupiter) + " (планета)")
    }

    private fun rotateConstraints(direction: Int) {
        for (item: LinearLayout in planetsArray) {
            val params = item.getLayoutParams() as ConstraintLayout.LayoutParams
            var currentAngle = params.circleAngle

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
                letsShowWeb(WIKI_BASE_URL + item.tag + " (планета)")
            }
        }
    }

    private fun letsShowWeb(url: String) {
        binding.searchWebView.loadUrl(url)
    }
}