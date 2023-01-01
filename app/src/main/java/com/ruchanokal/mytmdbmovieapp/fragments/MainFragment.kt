package com.ruchanokal.mytmdbmovieapp.fragments

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.ruchanokal.mytmdbmovieapp.R
import com.ruchanokal.mytmdbmovieapp.adapter.*
import com.ruchanokal.mytmdbmovieapp.databinding.FragmentMainBinding
import com.ruchanokal.mytmdbmovieapp.viewmodel.MainViewModel


class MainFragment : Fragment() {

    private val TAG = "MainFragment"

    private var binding: FragmentMainBinding? = null
    private lateinit var viewModel : MainViewModel
    var movieName = ""

    private var upcomingAdapter  = UpcomingAdapter(arrayListOf())
    private var nowPlayingAdapter  = NowPlayingAdapter(arrayListOf())
    private var popularAdapter  = PopularAdapter(arrayListOf())
    private var topRatedAdapter  = TopRatedAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_main,container,false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = androidx.lifecycle.ViewModelProvider(this).get(MainViewModel::class.java)
        viewModel.getFilmDatas()

        initializeRecyclerViews()

        binding!!.searchButton.setOnClickListener {

            movieName = binding!!.editTextMovieName.text.toString()

            if (!movieName.isEmpty()) {

                val action = MainFragmentDirections.actionMainFragmentToSearchListFragment(movieName)
                Navigation.findNavController(it).navigate(action)

            } else {

                Toast.makeText(requireContext(),"Please do not leave the movie search field blank",Toast.LENGTH_SHORT).show()
            }

        }

        binding!!.editTextMovieName.setOnEditorActionListener { textView, i, keyEvent ->

            if (i == EditorInfo.IME_ACTION_DONE) {

                movieName = binding!!.editTextMovieName.text.toString()

                if (!movieName.isEmpty()) {

                    val action = MainFragmentDirections.actionMainFragmentToSearchListFragment(movieName)
                    val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController.navigate(action)
                } else {

                    Toast.makeText(requireContext(),"Please do not leave the movie search field blank",Toast.LENGTH_SHORT).show()
                }

                true
            }
            false
        }

        binding!!.tryAgainImage.setOnClickListener {

            binding!!.editTextMovieName.setText("")
            binding!!.contentScrollView.visibility = View.VISIBLE
            binding!!.errorTryAgainLayout.visibility = View.GONE
        }

        observeLiveData()


    }

    private fun initializeRecyclerViews() {

        //RecyclerViewların Horizontal(Yatay) bir şekilde listeleneceğini belirtiyoruz.

        val llm  =  LinearLayoutManager(requireContext())
        llm.orientation = LinearLayoutManager.HORIZONTAL
        val llm2  =  LinearLayoutManager(requireContext())
        llm2.orientation = LinearLayoutManager.HORIZONTAL
        val llm3  =  LinearLayoutManager(requireContext())
        llm3.orientation = LinearLayoutManager.HORIZONTAL
        val llm4 =  LinearLayoutManager(requireContext())
        llm4.orientation = LinearLayoutManager.HORIZONTAL

        binding!!.popularRecyclerView.layoutManager = llm
        binding!!.topRatedRecyclerView.layoutManager = llm2
        binding!!.nowPlayingRecyclerView.layoutManager = llm3
        binding!!.upcomingRecyclerView.layoutManager = llm4


        //RecyclerViewlarla adapterları bağlıyoruz

        binding!!.popularRecyclerView.adapter = popularAdapter
        binding!!.topRatedRecyclerView.adapter = topRatedAdapter
        binding!!.nowPlayingRecyclerView.adapter = nowPlayingAdapter
        binding!!.upcomingRecyclerView.adapter = upcomingAdapter
    }

    private fun observeLiveData() {

        //MainViewModelda yapılan değişiklikleri burada gözlemliyoruz

        viewModel.movieLoading.observe(viewLifecycleOwner, Observer { movieLoading ->

            if (movieLoading){

                binding!!.contentScrollView.visibility = View.GONE
                binding!!.errorTryAgainLayout.visibility = View.GONE
                binding!!.progressBar.visibility = View.VISIBLE

            } else {

                binding!!.progressBar.visibility = View.GONE
            }

        })

        viewModel.movieError.observe(viewLifecycleOwner, Observer { movieError ->

            if (movieError){

                binding!!.contentScrollView.visibility = View.GONE
                binding!!.errorTryAgainLayout.visibility = View.VISIBLE
                binding!!.progressBar.visibility = View.GONE

            } else {

                binding!!.errorTryAgainLayout.visibility = View.GONE
            }

        })


        viewModel.popularMovieDetailsData.observe(viewLifecycleOwner, Observer { popularMovieData ->

                popularMovieData?.let {
                    binding!!.contentScrollView.visibility = View.VISIBLE
                    val results = it.results
                    popularAdapter.updateMovieList(results)
                }

        })

        viewModel.topRatedMovieDetailsData.observe(viewLifecycleOwner, Observer { topRatedMovieDetailsData ->

            topRatedMovieDetailsData?.let {
                binding!!.contentScrollView.visibility = View.VISIBLE
                val results = it.results
                topRatedAdapter.updateMovieList(results)
            }

        })

        viewModel.nowPlayingMovieDetailsData.observe(viewLifecycleOwner, Observer { nowPlayingMovieDetailsData ->

            nowPlayingMovieDetailsData?.let {
                binding!!.contentScrollView.visibility = View.VISIBLE
                val results = it.results
                nowPlayingAdapter.updateMovieList(results)
            }

        })

        viewModel.upcomingMovieDetailsData.observe(viewLifecycleOwner, Observer { upcomingMovieDetailsData ->

            upcomingMovieDetailsData?.let {
                binding!!.contentScrollView.visibility = View.VISIBLE
                val results = it.results
                upcomingAdapter.updateMovieList(results)
            }
        })


    }


}