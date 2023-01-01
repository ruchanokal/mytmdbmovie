package com.ruchanokal.mytmdbmovieapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.ruchanokal.mytmdbmovieapp.R
import com.ruchanokal.mytmdbmovieapp.databinding.SearchMoviesRowBinding
import com.ruchanokal.mytmdbmovieapp.fragments.MainFragmentDirections
import com.ruchanokal.mytmdbmovieapp.fragments.SearchListFragmentArgs
import com.ruchanokal.mytmdbmovieapp.fragments.SearchListFragmentDirections
import com.ruchanokal.mytmdbmovieapp.model.MovieModel
import com.ruchanokal.mytmdbmovieapp.model.Popular

class SearchMovieAdapter(val searchList : ArrayList<com.ruchanokal.mytmdbmovieapp.model.Result>) : RecyclerView.Adapter<SearchMovieAdapter.SearchHolder>() {

    class SearchHolder(val binding : SearchMoviesRowBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val view = DataBindingUtil.inflate<SearchMoviesRowBinding>(LayoutInflater.from(parent.context), R.layout.search_movies_row,parent,false)
        return SearchHolder(view)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {

        //Data Binding kütüphanesi sayesinde listenin itemini sadece bir değişkene atıyoruz ve bu sayede
        //imageview ve textviewlara ayrı ayrı değer atamamıza gerek kalmıyor.
        holder.binding.movie = searchList.get(position)

        holder.itemView.setOnClickListener {

            val action = SearchListFragmentDirections.actionSearchListFragmentToMovieDetailsFragment(searchList.get(position))
            Navigation.findNavController(it).navigate(action)

        }
    }

    override fun getItemCount(): Int {
        return searchList.size
    }

    //adapterı güncellemek için yazılan fonksiyon
    fun updateMovieList(myNewList : List<com.ruchanokal.mytmdbmovieapp.model.Result>){

        searchList.clear()
        searchList.addAll(myNewList)
        notifyDataSetChanged()

    }
}