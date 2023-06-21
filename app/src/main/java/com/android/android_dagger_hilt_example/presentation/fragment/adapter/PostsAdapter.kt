package com.android.android_dagger_hilt_example.presentation.fragment.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.android_dagger_hilt_example.databinding.ItemPostListBinding
import com.android.android_dagger_hilt_example.model.Post

class PostsAdapter(
    private val onItemClicked: (post: Post) -> Unit,
) : RecyclerView.Adapter<PostsAdapter.PostViewHolder>() {

    private var list = emptyList<Post>()

    fun update(list: List<Post>) {
        this.list = list
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): PostViewHolder {
        val binding =
            ItemPostListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PostViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(list[position])
    }

    inner class PostViewHolder(
        private val binding: ItemPostListBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(post: Post) {
            binding.apply {
                textviewPostTitle.text = post.title
                cardviewPost.setOnClickListener {
                    onItemClicked(post)
                }
            }
        }
    }
}