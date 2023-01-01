package com.ruchanokal.mytmdbmovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ruchanokal.mytmdbmovieapp.R
import com.ruchanokal.mytmdbmovieapp.databinding.UpcomingMoviesRowBinding
import com.ruchanokal.mytmdbmovieapp.fragments.MainFragmentDirections
import com.ruchanokal.mytmdbmovieapp.model.TopRated
import com.ruchanokal.mytmdbmovieapp.model.UpcomingMovies

class UpcomingAdapter(val upcomingList : ArrayList<com.ruchanokal.mytmdbmovieapp.model.Result>) : RecyclerView.Adapter<UpcomingAdapter.UpcomingHolder>() {

    class UpcomingHolder(val binding : UpcomingMoviesRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UpcomingHolder {
        val view = DataBindingUtil.inflate<UpcomingMoviesRowBinding>(LayoutInflater.from(parent.context), R.layout.upcoming_movies_row,parent,false)
        return UpcomingHolder(view)
    }

    override fun onBindViewHolder(holder: UpcomingHolder, position: Int) {

        //Data Binding kütüphanesi sayesinde listenin itemini sadece bir değişkene atıyoruz ve bu sayede
        //imageview ve textviewlara ayrı ayrı değer atamamıza gerek kalmıyor.
        holder.binding.upcomingmovie = upcomingList.get(position)

        holder.itemView.setOnClickListener {

            val action = MainFragmentDirections.actionMainFragmentToMovieDetailsFragment(upcomingList.get(position))
            Navigation.findNavController(it).navigate(action)

        }
    }

    override fun getItemCount(): Int {
        return upcomingList.size
    }

    //adapterı güncellemek için yazılan fonksiyon
    fun updateMovieList(myNewList : List<com.ruchanokal.mytmdbmovieapp.model.Result>){

        upcomingList.clear()
        upcomingList.addAll(myNewList)
        notifyDataSetChanged()

    }


}