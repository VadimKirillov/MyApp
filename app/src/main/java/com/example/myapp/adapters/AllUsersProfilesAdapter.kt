package com.example.myapp.adapters

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.paging.PagedList
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.data.UserProfile
import com.example.myapp.databinding.ExerciseItemGlobalBinding
import com.example.myapp.databinding.UserProfileItemBinding
import com.example.myapp.models.ExerciseModel
import java.io.ByteArrayInputStream
import java.io.InputStream

class DiffUtilUserProfileCallBack : DiffUtil.ItemCallback<UserProfile>() {
    override fun areItemsTheSame(oldItem: UserProfile, newItem: UserProfile): Boolean {
        return oldItem.nickname == newItem.nickname
    }

    override fun areContentsTheSame(oldItem: UserProfile, newItem: UserProfile): Boolean {
        return oldItem.nickname == newItem.nickname
    }
}


class AllUsersProfilesAdapter(
    ) : PagedListAdapter<UserProfile, AllUsersProfilesAdapter.Holder>(DiffUtilUserProfileCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {

        val binding : UserProfileItemBinding = UserProfileItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if(position <= -1 || getItem(position) == null){
            return
        }
        holder.bind(getItem(position)!!)
    }

    class Holder(val binding: UserProfileItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            user: UserProfile,

        ) {
            fun decodeBase64AndSetImage(completeImageData: String, imageView: ImageView) {
                val imageDataBytes = completeImageData.substring(completeImageData.indexOf(",") + 1)
                val stream: InputStream =
                    ByteArrayInputStream(Base64.decode(imageDataBytes.toByteArray(), Base64.DEFAULT))
                val bitmap = BitmapFactory.decodeStream(stream)
                imageView.setImageBitmap(bitmap)
            }

            binding.nameUser.text = user.nickname

            user.picture?.let { decodeBase64AndSetImage(it, binding.imageCardExercise) }

        }

    }

}