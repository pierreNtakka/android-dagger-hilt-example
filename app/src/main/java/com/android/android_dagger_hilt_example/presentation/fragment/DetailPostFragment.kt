package com.android.android_dagger_hilt_example.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.android.android_dagger_hilt_example.databinding.FragmentDetailPostsBinding
import com.android.android_dagger_hilt_example.model.Post

class DetailPostFragment : Fragment() {

    private var _binding: FragmentDetailPostsBinding? = null
    private val binding get() = _binding!!

    private val args: DetailPostFragmentArgs by navArgs()

    private lateinit var post: Post

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        post = args.post
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailPostsBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvPostTitle.text = post.title
        binding.tvPostBody.text = post.body
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}