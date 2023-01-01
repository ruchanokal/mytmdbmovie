package com.ruchanokal.movieomdb.util

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.ruchanokal.mytmdbmovieapp.R
import com.ruchanokal.mytmdbmovieapp.viewmodel.genresHashMap

fun ImageView.downloadFromUrl(url: String?, progressDrawable: CircularProgressDrawable){

    Log.i("Util","downloadUrl: "+ url)

    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.ic_baseline_error_outline_24)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(url)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                Log.e("Util", "onLoadFailed")
                return false
            }

            override fun onResourceReady(
                resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {
                Log.d("Util", "OnResourceReady")
                return false

            }
        })
        .into(this)

}

fun placeholderProgressBar(context: Context) : CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 8f
        centerRadius = 40f
        start()
    }
}

@BindingAdapter("android:downloadUrl")
fun downloadImage(view: ImageView, url:String?) {

    //Example Link : https://image.tmdb.org/t/p/w500/kqjL17yufvn9OVLyXYpvtyrFfak.jpg
    val realURL = "https://image.tmdb.org/t/p/w500" + url
    view.downloadFromUrl(realURL, placeholderProgressBar(view.context))
}

@BindingAdapter("android:changegenresfromids")
fun changeGenresText(textView: TextView, ids:List<Int>) {

    if (genresHashMap != null && genresHashMap.size >0) {

        val sb = StringBuilder()

        var counter = 0

        for(a in genresHashMap){
            if (ids.contains(a.key)) {
                counter++
                sb.append(a.value)
                if (ids.size > counter){
                    sb.append(", ")
                }
            }
        }

        textView.text = sb.toString()

    }
}