package com.android.android_dagger_hilt_example.presentation.list.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
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
import com.android.android_dagger_hilt_example.presentation.list.fragment.adapter.PostsAdapter
import com.android.android_dagger_hilt_example.presentation.list.viewmodel.ListPostsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


/**
 *
 * ActivityResultContracts.StartActivityForResult: Used to start an activity and receive a result back.
 * ActivityResultContracts.RequestPermission: Used to request a single permission.
 * ActivityResultContracts.RequestMultiplePermissions: Used to request multiple permissions at once.
 * ActivityResultContracts.TakePicture: Used to capture a photo and save it to the given Uri.
 * ActivityResultContracts.TakePicturePreview: Used to capture a photo and return it as a Bitmap.
 * ActivityResultContracts.TakeVideo: Used to capture a video and save it to the given Uri.
 * ActivityResultContracts.PickContact: Used to pick a contact from the contacts app.
 * ActivityResultContracts.GetContent: Used to open a document picker to select a piece of content.
 * ActivityResultContracts.OpenDocument: Used to open a document picker to select a document.
 * ActivityResultContracts.OpenMultipleDocuments: Used to open a document picker to select multiple documents.
 * ActivityResultContracts.CreateDocument: Used to create a new document.
 *
 * **/


@AndroidEntryPoint
class ListPostFragment : Fragment(), MenuProvider {

    private var _binding: FragmentListPostsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<ListPostsViewModel>()

    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                viewModel.onPictureSelected()
            }
        }

    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            uri?.let {
                viewModel.onPictureSelected(it)
            }
        }

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

                        is ListPostsViewModel.ListPostsState.Error -> {
                            binding.progressBar.isVisible = false
                            binding.recyclerViewPosts.isVisible = false

                            Toast.makeText(
                                this@ListPostFragment.context,
                                "An Error was occurrend",
                                Toast.LENGTH_SHORT
                            ).show()

                        }
                    }
                }
            }
        }

        binding.fab.setOnClickListener {
            viewModel.createPost()
        }

        binding.fabCamera.setOnClickListener {
            viewModel.captureImage(this.cameraLauncher)
        }

        binding.fabGallery.setOnClickListener {
            this.galleryLauncher.launch("image/*")
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