package com.example.myapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.data.GroupExercise
import com.example.myapp.databinding.BottomMuscleListItemBinding
import com.example.myapp.databinding.GroupExerciseItemBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomMuscleAdapter(val listener: BottomMuscleAdapter.Listener,
) : RecyclerView.Adapter<BottomMuscleAdapter.BottomGroupExerciseHolder>()
{
    val  mList = ArrayList<GroupExercise>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BottomGroupExerciseHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.bottom_muscle_list_item, parent,false)
        return BottomGroupExerciseHolder(view)
    }

    override fun onBindViewHolder(holder: BottomGroupExerciseHolder, position: Int) {
        holder.bind(mList[position], listener)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class BottomGroupExerciseHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = BottomMuscleListItemBinding.bind(item)
        fun bind(groupExercise: GroupExercise, listener: BottomMuscleAdapter.Listener) = with(binding) {
            imageGroupExercise.setImageResource(groupExercise.imageId)
            textGroupExercise.text = groupExercise.title
            itemView.setOnClickListener(){
                listener.onClick(groupExercise)
            }
        }
    }

    fun addBottomMuscleGroup(groupExercise: GroupExercise){
        mList.add(groupExercise)
        notifyDataSetChanged()
    }

    interface Listener{
        fun onClick(groupExercise: GroupExercise){

        }
    }
}