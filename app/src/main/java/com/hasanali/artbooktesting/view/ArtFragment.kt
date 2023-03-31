package com.hasanali.artbooktesting.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hasanali.artbooktesting.R
import com.hasanali.artbooktesting.adapter.ArtRecyclerAdapter
import com.hasanali.artbooktesting.databinding.FragmentArtsBinding
import com.hasanali.artbooktesting.viewmodel.ArtViewModel
import javax.inject.Inject

class ArtFragment @Inject constructor(
    val artRecyclerAdapter: ArtRecyclerAdapter
): Fragment(R.layout.fragment_arts) {

    private var binding: FragmentArtsBinding? = null
    private lateinit var viewModel: ArtViewModel

    private val swipeCallBack = object : ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
        override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            // user swipe yaptığında yapacağımız işlemleri yazarız

            // RecyclerView'da Swipe edilen pozisyon
            val layoutPosition = viewHolder.layoutPosition

            // Adapter'daki Art'ları turan "arts" list'ine pozisyonu vererek seçilen Art objesini alırız.
            val selectedArt = artRecyclerAdapter.arts[layoutPosition]

            // viewModel sınıfındaki deleteArt fonksiyonuna seçilen art'ı vererek silme işlemi gerçekleştiririz.
            viewModel.deleteArt(selectedArt)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentArtsBinding.bind(view)
        viewModel = ViewModelProvider(requireActivity()).get(ArtViewModel::class.java)

        binding!!.recyclerViewArt.adapter = artRecyclerAdapter
        binding!!.recyclerViewArt.layoutManager = LinearLayoutManager(requireContext())

        ItemTouchHelper(swipeCallBack).attachToRecyclerView(binding!!.recyclerViewArt)

        subscribeToObservers()

        binding!!.fab.setOnClickListener {
            findNavController().navigate(ArtFragmentDirections.actionArtFragmentToArtDetailsFragment())
        }
    }

    private fun subscribeToObservers() {
        viewModel.artList.observe(viewLifecycleOwner, Observer {
            artRecyclerAdapter.arts = it
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}