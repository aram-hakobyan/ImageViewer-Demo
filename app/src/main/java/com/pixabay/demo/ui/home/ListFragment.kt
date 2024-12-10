package com.pixabay.demo.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pixabay.demo.R
import com.pixabay.demo.databinding.FragmentListBinding
import com.pixabay.demo.domain.model.Photo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListFragment : Fragment(), ImageListAdapter.OnItemClickListener {

    private val viewModel: PhotosViewModel by activityViewModels()
    private lateinit var binding: FragmentListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list, container, false
        )
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        initUI()
        setupScrollListener()

        return binding.root
    }

    private fun initUI() {
        val adapter = ImageListAdapter(this)
        binding.rvImages.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.imageList.collect {
                    adapter.submitList(it)
                }
            }
        }
    }

    override fun onItemClick(image: Photo) {
        viewModel.image = image

        findNavController().navigate(
            R.id.action_listFragment_to_detailsFragment
        )
    }

    private fun setupScrollListener() {
        binding.rvImages.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == viewModel.imageList.value.size - 1) {
                    viewModel.loadImages()
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }
}