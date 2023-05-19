package com.example.myapp.adapters

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.data.Post
import com.example.myapp.data.UserProfile
import com.example.myapp.databinding.PostItemBinding
import com.example.myapp.databinding.UserProfileItemBinding
import com.example.myapp.models.ExerciseModel
import java.io.ByteArrayInputStream
import java.io.InputStream


class DiffUtilPostsCallBack : DiffUtil.ItemCallback<Post>() {
    override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.postHead == newItem.postHead
    }

    override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean {
        return oldItem.postHead == newItem.postHead
    }
}


class PostsAdapter(
    private val openPost: (Post) -> Unit,
) : PagedListAdapter<Post, PostsAdapter.Holder>(DiffUtilPostsCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val binding : PostItemBinding = PostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if(position <= -1 || getItem(position) == null){
            return
        }
        holder.bind(getItem(position)!!, openPost)
    }

    class Holder(val binding: PostItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            post: Post,
            openPost: (Post) -> Unit,
            ) {
            fun decodeBase64AndSetImage(completeImageData: String, imageView: ImageView) {
                val imageDataBytes = completeImageData.substring(completeImageData.indexOf(",") + 1)
                val stream: InputStream =
                    ByteArrayInputStream(Base64.decode(imageDataBytes.toByteArray(), Base64.DEFAULT))
                val bitmap = BitmapFactory.decodeStream(stream)
                imageView.setImageBitmap(bitmap)
            }

            binding.nickName.text = post.author

            post.picture?.let { decodeBase64AndSetImage(it, binding.imageCardExercise) }

            binding.openPostButton.setOnClickListener(View.OnClickListener{
                openPost(post)
            })
        }

    }

}