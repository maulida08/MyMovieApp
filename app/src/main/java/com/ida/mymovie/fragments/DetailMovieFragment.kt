package com.ida.mymovie.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.ida.mymovie.R
import com.ida.mymovie.databinding.FragmentDetailMovieBinding
import com.ida.mymovie.viewmodel.Detail

class DetailMovieFragment : Fragment() {

    private var _binding : FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!

    private val IMAGE_BASE ="https://image.tmdb.org/t/p/w500/"

    private val args : DetailMovieFragmentArgs by navArgs()

    private lateinit var detailViewModel: Detail

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detailViewModel = ViewModelProvider(requireActivity()).get(Detail::class.java)
        val id = args.id
        detailViewModel.getDetail(id)
        detailViewModel.dataDetail.observe(viewLifecycleOwner){
            Glide.with(binding.root).load(IMAGE_BASE+it.posterPath).into(binding.ivImage)
            binding.tvTittle.text = it.originalTitle
            binding.tvDesc.text = it.overview
        }
    }

}