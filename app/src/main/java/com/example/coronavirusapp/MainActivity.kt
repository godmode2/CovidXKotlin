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
import com.google.gson.Gson


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        textView2.text = ""
        //textView.movementMethod = ScrollingMovementMethod()


        button.setOnClickListener {
            textView3.text = "fetching new data..."
            val url = "https://corona.lmao.ninja/v2/jhucsse/counties/Champaign"
                .httpGet()
                .responseString { request, response, result ->
                    when (result) {
                        is Result.Failure -> {
                            val ex = result.getException()
                            println(ex)
                        }
                        is Result.Success -> {
                            val json = result.get()
                            var gson = Gson()
                            var list: Array<Chunk> = gson.fromJson(json, Array<Chunk>::class.java)
                            val confirmedCases = list.get(0).stats.confirmed
                            textView2.text = confirmedCases.toString()
                        }
                    }
                }

            url.join()
        }
    }
}
class Chunk(val country: String, val province: String, val county: String, val updatedAt: String, val stats: Stats)
class Stats(val confirmed: Int, val deaths: Int, val recovered: Int)
