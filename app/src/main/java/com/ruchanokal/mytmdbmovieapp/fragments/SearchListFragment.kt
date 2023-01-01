package com.ruchanokal.mytmdbmovieapp.fragments

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ruchanokal.mytmdbmovieapp.R
import com.ruchanokal.mytmdbmovieapp.adapter.SearchMovieAdapter
import com.ruchanokal.mytmdbmovieapp.databinding.FragmentSearchListBinding
import com.ruchanokal.mytmdbmovieapp.viewmodel.MainViewModel


class SearchListFragment : Fragment() {

    private val TAG = "SearchListFragment"
    private var binding : FragmentSearchListBinding? = null
    var movieName = ""
    private lateinit var viewModel : MainViewModel
    private var searchMovieAdapter  = SearchMovieAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_search_list,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = androidx.lifecycle.ViewModelProvider(this).get(MainViewModel::class.java)

        arguments?.let {

            var movieName = SearchListFragmentArgs.fromBundle(it).movieName
            viewModel.getSearchMovieDatas(movieName)

            val llm =  LinearLayoutManager(requireContext())
            llm.orientation = LinearLayoutManager.VERTICAL
            binding!!.searchRecyclerView.layoutManager = llm
            binding!!.searchRecyclerView.adapter = searchMovieAdapter

            binding!!.editTextMovieName.setText(movieName)

            binding!!.searchButton.setOnClickListener {

                movieName = binding!!.editTextMovieName.text.toString()
                viewModel.getSearchMovieDatas(movieName)

                val inputMethodManager = requireActivity().getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                inputMethodManager.hideSoftInputFromWindow(it?.windowToken, 0)

            }

            binding!!.editTextMovieName.setOnEditorActionListener { textView, i, keyEvent ->

                if (i == EditorInfo.IME_ACTION_DONE) {

                    movieName = binding!!.editTextMovieName.text.toString()

                    if (!movieName.isEmpty()) {
                        viewModel.getSearchMovieDatas(movieName)
                    } else {
                        Toast.makeText(requireContext(),"Please do not leave the movie search field blank",
                            Toast.LENGTH_SHORT).show()
                    }

                    true
                }
                false
            }


            observeLiveData()

        }

    }

    private fun observeLiveData() {

        viewModel.movieLoading.observe(viewLifecycleOwner, Observer { movieLoading ->

            if (movieLoading){

                binding!!.contentLayout.visibility = View.GONE
                binding!!.errorTryAgainLayout.visibility = View.GONE
                binding!!.progressBar.visibility = View.VISIBLE

            } else {
                binding!!.progressBar.visibility = View.GONE
            }

        })

        viewModel.movieError.observe(viewLifecycleOwner, Observer { movieError ->

            if (movieError){
                binding!!.contentLayout.visibility = View.GONE
                binding!!.errorTryAgainLayout.visibility = View.VISIBLE
                binding!!.progressBar.visibility = View.GONE

            } else {
                binding!!.errorTryAgainLayout.visibility = View.GONE
            }

        })



        viewModel.searchedMovieData.observe(viewLifecycleOwner, Observer { searchedMovieData ->

            searchedMovieData?.let {
                binding!!.contentLayout.visibility = View.VISIBLE
                val results = it.results
                searchMovieAdapter.updateMovieList(results)
            }
        })
    }

}