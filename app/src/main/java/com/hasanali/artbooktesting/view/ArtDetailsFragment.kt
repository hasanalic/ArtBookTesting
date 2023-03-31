package com.hasanali.artbooktesting.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.RequestManager
import com.hasanali.artbooktesting.R
import com.hasanali.artbooktesting.databinding.FragmentArtDetailsBinding
import com.hasanali.artbooktesting.databinding.FragmentArtsBinding
import com.hasanali.artbooktesting.util.Status
import com.hasanali.artbooktesting.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtDetailsFragment @Inject constructor(
    val glide: RequestManager
): Fragment(R.layout.fragment_art_details){

    private var binding: FragmentArtDetailsBinding? = null
    private lateinit var viewModel: ArtViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArtDetailsBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        binding!!.artImageView.setOnClickListener {
            findNavController().navigate(ArtDetailsFragmentDirections.actionArtDetailsFragmentToImageApiFragment())
        }

        val callBack = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().popBackStack()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(callBack)

        binding!!.saveButton.setOnClickListener {
            viewModel.makeArt(binding!!.nameText.text.toString(), binding!!.artistName.text.toString(), binding!!.yearText.text.toString())
        }

        susbscribeToObservers()
    }

    private fun susbscribeToObservers() {
        viewModel.selectedImageUrl.observe(viewLifecycleOwner, Observer {
            glide.load(it).into(binding!!.artImageView)
        })

        viewModel.insertArtMessage.observe(viewLifecycleOwner, Observer {
            when(it.status) {
                Status.SUCCESS -> {
                    Toast.makeText(requireContext(), "Success!", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                    viewModel.resetInsertArtMsg()
                }
                Status.ERROR -> {
                    Toast.makeText(requireContext(), it.message, Toast.LENGTH_SHORT).show()
                }
                Status.LOADING -> {}
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}