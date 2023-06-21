package com.android.android_dagger_hilt_example.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.android.android_dagger_hilt_example.R
import com.android.android_dagger_hilt_example.databinding.FragmentListPostsBinding
import com.android.android_dagger_hilt_example.model.Post
import com.android.android_dagger_hilt_example.presentation.fragment.adapter.PostsAdapter
import com.android.android_dagger_hilt_example.presentation.viewmodel.ListPostsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListPostFragment : Fragment(), MenuProvider {

    private var _binding: FragmentListPostsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ListPostsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListPostsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().addMenuProvider(this, viewLifecycleOwner, Lifecycle.State.RESUMED)
        val postAdapter = PostsAdapter(::onItemClick)
        binding.recyclerViewPosts.adapter = postAdapter


        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.dataFlow.collect { state ->
                    when (state) {
                        is ListPostsViewModel.ListPostsState.Success -> {
                            postAdapter.update(state.list)
                            binding.progressBar.isVisible = false
                            binding.recyclerViewPosts.isVisible = true
                        }

                        is ListPostsViewModel.ListPostsState.Loading -> {
                            binding.progressBar.isVisible = true
                            binding.recyclerViewPosts.isVisible = false
                        }
                    }
                }
            }
        }

    }


    private fun onItemClick(post: Post) {
        val action =
            ListPostFragmentDirections.actionListPostFragmentToDetailPostFragment(post)

        findNavController().navigate(action)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) =
        menuInflater.inflate(R.menu.menu_item, menu)

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        return when (menuItem.itemId) {
            R.id.action_sync -> {
                viewModel.fetchData()
                true
            }

            else -> false
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}