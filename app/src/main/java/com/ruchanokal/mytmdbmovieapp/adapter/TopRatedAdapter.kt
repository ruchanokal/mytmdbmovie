package com.ruchanokal.mytmdbmovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ruchanokal.mytmdbmovieapp.R
import com.ruchanokal.mytmdbmovieapp.databinding.TopratedMoviesRowBinding
import com.ruchanokal.mytmdbmovieapp.fragments.MainFragmentDirections
import com.ruchanokal.mytmdbmovieapp.model.MovieModel
import com.ruchanokal.mytmdbmovieapp.model.TopRated

class TopRatedAdapter(val topRatedList : ArrayList<com.ruchanokal.mytmdbmovieapp.model.Result>) : RecyclerView.Adapter<TopRatedAdapter.TopRatedHolder>() {

    class TopRatedHolder(val binding : TopratedMoviesRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopRatedHolder {
        val view = DataBindingUtil.inflate<TopratedMoviesRowBinding>(LayoutInflater.from(parent.context), R.layout.toprated_movies_row,parent,false)
        return TopRatedHolder(view)
    }

    override fun onBindViewHolder(holder: TopRatedHolder, position: Int) {

        //Data Binding kütüphanesi sayesinde listenin itemini sadece bir değişkene atıyoruz ve bu sayede
        //imageview ve textviewlara ayrı ayrı değer atamamıza gerek kalmıyor.
        holder.binding.topratedmovie = topRatedList.get(position)

        holder.itemView.setOnClickListener {

            val action = MainFragmentDirections.actionMainFragmentToMovieDetailsFragment(topRatedList.get(position))
            Navigation.findNavController(it).navigate(action)

        }
    }

    override fun getItemCount(): Int {
        return topRatedList.size
    }

    //adapterı güncellemek için yazılan fonksiyon
    fun updateMovieList(myNewList : List<com.ruchanokal.mytmdbmovieapp.model.Result>){

        topRatedList.clear()
        topRatedList.addAll(myNewList)
        notifyDataSetChanged()

    }
}