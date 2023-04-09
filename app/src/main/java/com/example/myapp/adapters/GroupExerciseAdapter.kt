package com.example.myapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.data.GroupExercise
import com.example.myapp.databinding.GroupExerciseItemBinding

class GroupExerciseAdapter(val listener: Listener): RecyclerView.Adapter<GroupExerciseAdapter.GroupExerciseHolder>() {
    val  groupExerciseList = ArrayList<GroupExercise>()
    class GroupExerciseHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = GroupExerciseItemBinding.bind(item)
        fun bind(groupExercise: GroupExercise, listener: Listener) = with(binding) {
            imageGroupExercise.setImageResource(groupExercise.imageId)
            textGroupExercise.text = groupExercise.title
            itemView.setOnClickListener(){
                listener.onClick(groupExercise)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupExerciseHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.group_exercise_item, parent,false)
        return GroupExerciseHolder(view)
    }

    override fun onBindViewHolder(holder: GroupExerciseHolder, position: Int) {
        holder.bind(groupExerciseList[position], listener)
    }

    override fun getItemCount(): Int {
        return groupExerciseList.size
    }

    fun addGroupExercise(groupExercise: GroupExercise){
        groupExerciseList.add(groupExercise)
        notifyDataSetChanged()
    }

    interface Listener{
        fun onClick(groupExercise: GroupExercise){

        }
    }
}