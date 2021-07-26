package net.smartgekko.myplanets.views

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import android.webkit.WebSettings
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.Group
import androidx.transition.Transition
import androidx.transition.TransitionInflater
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


        binding.sun.setOnClickListener {
            letsShowInfo(getString(R.string.sun),"")
        }
        binding.appbar.addOnOffsetChangedListener(object: AppBarStateChangeListener() {
            override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                when(state) {
                    State.COLLAPSED -> { binding.smallTumb.visibility = View.VISIBLE }
                    State.EXPANDED -> { binding.smallTumb.visibility = View.INVISIBLE }
                    State.IDLE -> { binding.smallTumb.visibility = View.INVISIBLE }
                }
            }
        }
        )

        binding.planetsGroup.setAllOnClickListener(View.OnClickListener {
            val nameText: TextView = it.findViewWithTag("planetName")
            letsShowOnePlanet(nameText.text.toString())
        })
        binding.sun.setOnClickListener {
            letsShowOnePlanet(resources.getString(R.string.sun))
        }

        binding.onePlanetImage.setOnClickListener {
            binding.onePlanet.visibility = View.INVISIBLE
            binding.planetsLayout.visibility = View.VISIBLE
        }

        letsShowInfo(getString(R.string.jupiter)," (планета)")
    }

    private fun Group.setAllOnClickListener(listener: View.OnClickListener?) {
        referencedIds.forEach { id ->
            rootView.findViewById<View>(id).setOnClickListener(listener)
        }
    }


    private fun letsShowInfo(currentPlanet: String, extendedString: String) {
        binding.searchWebView.loadUrl(WIKI_BASE_URL + currentPlanet + extendedString)
        binding.textPlanet.text = currentPlanet
        binding.textPlanet.text =currentPlanet
    }
    private fun letsShowOnePlanet(onePlanetName:String){

        binding.planetsLayout.visibility = View.INVISIBLE
       when(onePlanetName){
           resources.getString(R.string.sun) ->{
               binding.onePlanetImage.setImageResource(R.drawable.sun)
           }
           resources.getString(R.string.mercury) ->{
               binding.onePlanetImage.setImageResource(R.drawable.mercury)
           }
           resources.getString(R.string.venus) ->{
               binding.onePlanetImage.setImageResource(R.drawable.venus)
           }
           resources.getString(R.string.earth) ->{
               binding.onePlanetImage.setImageResource(R.drawable.earth)
           }
           resources.getString(R.string.mars) ->{
               binding.onePlanetImage.setImageResource(R.drawable.mars)
           }
           resources.getString(R.string.jupiter) ->{
               binding.onePlanetImage.setImageResource(R.drawable.jupiter)
           }
           resources.getString(R.string.saturn) ->{
               binding.onePlanetImage.setImageResource(R.drawable.saturn)
           }
           resources.getString(R.string.uranus) ->{
               binding.onePlanetImage.setImageResource(R.drawable.uranus)
           }
           resources.getString(R.string.neptune) ->{
               binding.onePlanetImage.setImageResource(R.drawable.neptune)
           }
       }
       // binding.onePlanetText.text = onePlanetName
        binding.textPlanet.text = onePlanetName
        binding.onePlanet.visibility = View.VISIBLE
    }

}


