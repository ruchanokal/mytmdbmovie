package com.ruchanokal.mytmdbmovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ruchanokal.mytmdbmovieapp.R
import com.ruchanokal.mytmdbmovieapp.databinding.PopularMoviesRowBinding
import com.ruchanokal.mytmdbmovieapp.fragments.MainFragmentDirections
import com.ruchanokal.mytmdbmovieapp.model.NowPlaying
import com.ruchanokal.mytmdbmovieapp.model.Popular

class PopularAdapter(val popularList : ArrayList<com.ruchanokal.mytmdbmovieapp.model.Result>) : RecyclerView.Adapter<PopularAdapter.PopularHolder>() {

    class PopularHolder(val binding : PopularMoviesRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PopularHolder {
        val view = DataBindingUtil.inflate<PopularMoviesRowBinding>(LayoutInflater.from(parent.context), R.layout.popular_movies_row,parent,false)
        return PopularHolder(view)
    }

    override fun onBindViewHolder(holder: PopularHolder, position: Int) {

        //Data Binding kütüphanesi sayesinde listenin itemini sadece bir değişkene atıyoruz ve bu sayede
        //imageview ve textviewlara ayrı ayrı değer atamamıza gerek kalmıyor.
        holder.binding.popularmovie = popularList.get(position)

        holder.itemView.setOnClickListener {

            val action = MainFragmentDirections.actionMainFragmentToMovieDetailsFragment(popularList.get(position))
            Navigation.findNavController(it).navigate(action)

        }
    }

    override fun getItemCount(): Int {
        return popularList.size
    }

    //adapterı güncellemek için yazılan fonksiyon
    fun updateMovieList(myNewList : List<com.ruchanokal.mytmdbmovieapp.model.Result>){

        popularList.clear()
        popularList.addAll(myNewList)
        notifyDataSetChanged()

    }
}