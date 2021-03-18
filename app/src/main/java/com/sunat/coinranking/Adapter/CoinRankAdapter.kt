package com.sunat.coinranking.Adapter

import android.content.Context
import android.net.Uri
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.sunat.coinranking.R
import java.util.*

class CoinRankAdapter(
    private var context: Context,
    private var CoinName: ArrayList<String>,
    private var Description: ArrayList<String>,
    private var Icon: ArrayList<String>
) : RecyclerView.Adapter<CoinRankAdapter.CoinRankViewHolder>() {

    class CoinRankViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        /**
         * ViewHolder from coin_adapter_layout.xml
         **/
        var coinName = view.findViewById(R.id.coinName) as TextView
        var coinDescription = view.findViewById(R.id.coinDescription) as TextView
        var coinIcon = view.findViewById(R.id.coinIcon) as ImageView
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoinRankViewHolder {

        val v = LayoutInflater.from(parent.context).inflate(R.layout.coin_adapter_layout,parent,false)
        println(v)
        return CoinRankViewHolder(v)
    }

    override fun onBindViewHolder(holder: CoinRankViewHolder, position: Int) {
        /** Bind CoinAdapter layout to this class **/
        holder.apply {
            coinName.text = CoinName[position]
            coinDescription.text = Description[position]
            // what we got is https://cdn.coinranking.com/bOabBYkcX/bitcoin_btc.svg

            val uri = Uri.parse(Icon[position])
            Log.d("uri Tag", uri.toString())
            coinIcon.loadSvg(Icon[position])
        }
    }

    override fun getItemCount(): Int {
        return CoinName.size
    }
}

/**
 * Create New function to load SVG icon from CoinRanking API base on [url]
 */
private fun ImageView.loadSvg(url: String) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(500)
        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}
