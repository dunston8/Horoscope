package com.example.horoscope

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.lang.IndexOutOfBoundsException

class HoroskopActivity : AppCompatActivity() {

    //Lager en global variabel for request-kø
    var requestQueue: RequestQueue? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horoskop)

        val getURL = intent.getStringExtra("url")
        val url = getURL.toString()

        APIKall(url)
    }

    private fun APIKall(url: String) {
        requestQueue = Volley.newRequestQueue(this)
        //Lager en request
        val request = StringRequest(
            Request.Method.POST, url,
            { response ->
                //Gjør noe med svaret
                formatResponse(response)
            },
            { error ->
                //Håndterer eventuelle feil
                findViewById<TextView>(R.id.currentDate).text = "Kunne ikke laste horoskop"

            }
        )
        //Gir requesten en tag
        request.tag = "horoskop"
        //Legger til requesten i køen
        requestQueue?.add(request)
    }

    override fun onStop() {
        super.onStop()
        requestQueue?.cancelAll("horoskop")
    }

    private fun formatResponse(resp: String) {
        try {
            val list = resp.split("\"")


            findViewById<TextView>(R.id.currentDate).text = list[7]

            findViewById<TextView>(R.id.compatiblity).text = list[11]

            findViewById<TextView>(R.id.mood).text = list[19]

            findViewById<TextView>(R.id.color).text = list[23]

            findViewById<TextView>(R.id.luckynumber).text = list[27]

            findViewById<TextView>(R.id.luckytime).text = list[31]





        } catch (e: IndexOutOfBoundsException){
            val error = findViewById<TextView>(R.id.description)
            error.text = "Kunne ikke laste Horoskop"

        }
    }
}