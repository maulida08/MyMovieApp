package com.ida.mymovie.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.ida.mymovie.adapter.PopularAdapter
import com.ida.mymovie.databinding.FragmentHomeBinding
import com.ida.mymovie.model.GetAllMoviePopular
import com.ida.mymovie.service.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import com.ida.mymovie.model.Result

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var preferences: SharedPreferences

    companion object {
        const val ID = "id"
        const val SHARED_FILE = "KotlinSharedPreference"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = requireContext().getSharedPreferences(LoginFragment.LOGINUSER, Context.MODE_PRIVATE)
        binding.tvName.text = "${preferences.getString(LoginFragment.USERNAME,null)}"

        fetchAllMoviePopular()
    }

    private fun fetchAllMoviePopular(){
        ApiClient.instance.getAllMoviePopular()
            .enqueue(object : Callback<GetAllMoviePopular> {

                override fun onResponse(
                    call: Call<GetAllMoviePopular>,
                    response: Response<GetAllMoviePopular>
                ) {
                    val body = response.body()
                    val code = response.code()
                    if (code == 200){
                        body?.let { showListMoviePopular(it.results)
                        }

                    }

                }

                override fun onFailure(call: Call<GetAllMoviePopular>, t: Throwable) {
                    Toast.makeText(requireContext(), "${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun showListMoviePopular(data: List<Result>) {
        val adapter = PopularAdapter(object : PopularAdapter.OnClickListener{
            override fun onClickItem(data: Result) {
                val id = data.id
                val detail = HomeFragmentDirections.actionHomeFragmentToDetailMovieFragment(id)
                findNavController().navigate(detail)
            }
        })
        adapter.submitData(data)
        binding.rvListPopular.adapter = adapter
    }

}