package edu.temple.coroutineconversion

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

class MainActivity : AppCompatActivity() {

    //TODO (Refactor to replace Thread code with coroutines)

    private val cakeImageView: ImageView by lazy {
        findViewById(R.id.imageView)
    }

    private val currentTextView: TextView by lazy {
        findViewById(R.id.currentTextView)
    }

    val handler = Handler(Looper.getMainLooper(), Handler.Callback {

        currentTextView.text = String.format(Locale.getDefault(), "Current opacity: %d", it.what)
        cakeImageView.alpha = it.what / 100f
        true
    })

    val scope = CoroutineScope(Job() + Dispatchers.Default)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.revealButton).setOnClickListener{
            scope.launch {
                revealCake()
            }.start()
        }
    }

    private suspend fun revealCake(){
        repeat(100) {
            withContext(Dispatchers.Main) {
                currentTextView.text = ("Current opacity: " + it.toString())
                cakeImageView.alpha = it / 100f
            }
            delay(40)
        }
    }
}