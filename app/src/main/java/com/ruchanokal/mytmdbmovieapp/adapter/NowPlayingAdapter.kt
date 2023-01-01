package com.ruchanokal.mytmdbmovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ruchanokal.mytmdbmovieapp.R
import com.ruchanokal.mytmdbmovieapp.databinding.NowplayingMoviesRowBinding
import com.ruchanokal.mytmdbmovieapp.fragments.MainFragmentDirections
import com.ruchanokal.mytmdbmovieapp.model.Latest
import com.ruchanokal.mytmdbmovieapp.model.NowPlaying

class NowPlayingAdapter(val nowplayingList : ArrayList<com.ruchanokal.mytmdbmovieapp.model.Result>) : RecyclerView.Adapter<NowPlayingAdapter.NowPlayingHolder>() {

    class NowPlayingHolder(val binding : NowplayingMoviesRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NowPlayingHolder {
        val view = DataBindingUtil.inflate<NowplayingMoviesRowBinding>(LayoutInflater.from(parent.context), R.layout.nowplaying_movies_row,parent,false)
        return NowPlayingHolder(view)
    }

    override fun onBindViewHolder(holder: NowPlayingHolder, position: Int) {

        //Data Binding kütüphanesi sayesinde listenin itemini sadece bir değişkene atıyoruz ve bu sayede
        //imageview ve textviewlara ayrı ayrı değer atamamıza gerek kalmıyor.
        holder.binding.nowplayingmovies = nowplayingList.get(position)

        holder.itemView.setOnClickListener {

            val action = MainFragmentDirections.actionMainFragmentToMovieDetailsFragment(nowplayingList.get(position))
            Navigation.findNavController(it).navigate(action)

        }

    }

    override fun getItemCount(): Int {
        return nowplayingList.size
    }

    //adapterı güncellemek için yazılan fonksiyon
    fun updateMovieList(myNewList : List<com.ruchanokal.mytmdbmovieapp.model.Result>){

        nowplayingList.clear()
        nowplayingList.addAll(myNewList)
        notifyDataSetChanged()

    }
}