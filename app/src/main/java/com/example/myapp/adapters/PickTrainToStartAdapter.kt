package com.example.myapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.data.GroupExercise
import com.example.myapp.databinding.GroupExerciseItemBinding

class PickTrainToStartAdapter(val listener: Listener): RecyclerView.Adapter<PickTrainToStartAdapter.PickTrainToStartHolder>() {
    val  pickTrainToStartList = ArrayList<GroupExercise>()
    class PickTrainToStartHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = GroupExerciseItemBinding.bind(item)
        fun bind(groupExercise: GroupExercise, listener: Listener) = with(binding) {
            imageGroupExercise.setImageResource(groupExercise.imageId)
            textGroupExercise.text = groupExercise.title
            itemView.setOnClickListener(){
                listener.onClick(groupExercise)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PickTrainToStartHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.pick_train_to_start_item, parent,false)
        return PickTrainToStartHolder(view)
    }

    override fun onBindViewHolder(holder: PickTrainToStartHolder, position: Int) {
        holder.bind(pickTrainToStartList[position], listener)
    }

    override fun getItemCount(): Int {
        return pickTrainToStartList.size
    }

    fun addGroupExercise(groupExercise: GroupExercise){
        pickTrainToStartList.add(groupExercise)
        notifyDataSetChanged()
    }

    interface Listener{
        fun onClick(groupExercise: GroupExercise){

        }
    }

}