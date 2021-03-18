package com.sunat.coinranking

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sunat.coinranking.Adapter.CoinRankAdapter
import com.sunat.coinranking.Utils.Constants
import okhttp3.*
import org.json.JSONObject
import java.io.IOException

class MainActivity : AppCompatActivity() {

    var CoinName: ArrayList<String> = ArrayList()
    var Description: ArrayList<String> = ArrayList()
    var Icon: ArrayList<String> = ArrayList()

    private var coin_fetch = Constants.CoinRank_API

    /**
     *
     */
    private val client by lazy {
        OkHttpClient()
    }

    /**
     *
     */
    private val request by lazy {
        Request.Builder().url(coin_fetch).build()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val recyclerview = findViewById<View>(R.id.CoinRecyclerview) as RecyclerView
        val linearLayoutManager = LinearLayoutManager(applicationContext)
        recyclerview.layoutManager = linearLayoutManager

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("Error while fetching data", e.toString())
            }

            override fun onResponse(call: Call, response: Response) {

                Log.d("Response", response?.toString())
                val str_response = response.body!!.string()
                Log.d("result",str_response)
                val json_contact = JSONObject(str_response)
                val responseJson = json_contact.getJSONObject("data")
                val coins = responseJson.getJSONArray("coins")
                for (i in 0 until coins.length()) {
                    /**
                     * Now we know this API is limit to 50 (0 - 49)
                     * /data/coins/? then ? is what
                     * Next we need to map it!
                     * **/
                    val detail = coins.getJSONObject(i) // this line can print data out in obj form
                    val coin_name = detail.getString("name")
                    val description = detail.getString("description")
                    val icon = detail.getString("iconUrl")
                    CoinName.add(coin_name)
                    Description.add(description)
                    Icon.add(icon.toString())
                }
                runOnUiThread {

                    recyclerview.layoutManager = LinearLayoutManager(this@MainActivity)
                    val coinAdapter =
                        CoinRankAdapter(this@MainActivity, CoinName, Description, Icon)
                    recyclerview.adapter = coinAdapter
                }
            }
        })
    }
}


