package com.imageviewer.demo.presentation.home.fragment

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
import com.imageviewer.demo.R
import com.imageviewer.demo.databinding.FragmentListBinding
import com.imageviewer.demo.domain.model.Image
import com.imageviewer.demo.presentation.home.HomeViewModel
import com.imageviewer.demo.presentation.home.adapter.ImageListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ImageListFragment : Fragment(), ImageListAdapter.OnItemClickListener {

    private val viewModel: HomeViewModel by activityViewModels()
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
                viewModel.images.collect {
                    adapter.submitList(it)
                }
            }
        }
    }

    override fun onItemClick(image: Image) {
        viewModel.selectedImage = image

        findNavController().navigate(
            R.id.action_listFragment_to_detailsFragment
        )
    }

    private fun setupScrollListener() {
        binding.rvImages.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == viewModel.images.value.size - 1) {
                    viewModel.loadImages()
                }
            }
        })
    }

    companion object {
        @JvmStatic
        fun newInstance() = ImageListFragment()
    }
}