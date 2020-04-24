package com.example.coronavirusapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.github.kittinunf.fuel.httpGet
import com.github.kittinunf.result.Result;
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import android.text.method.ScrollingMovementMethod
import androidx.core.app.ComponentActivity
import androidx.core.app.ComponentActivity.ExtraData
import androidx.core.content.ContextCompat.getSystemService
import android.icu.lang.UCharacter.GraphemeClusterBreak.T



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView.text = ""
        textView.movementMethod = ScrollingMovementMethod()


        button.setOnClickListener {
            textView.text = "fetching new data..."
            val httpAsync = "https://corona.lmao.ninja/v2/jhucsse"
                .httpGet()
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
                        }
                        is Result.Success -> {
                            val data = result.get()
                            textView.text = data
                        }
                    }
                }

            httpAsync.join()
        }
    }
}
