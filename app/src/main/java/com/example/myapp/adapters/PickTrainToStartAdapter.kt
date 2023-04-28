package com.example.myapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.data.GroupExercise
import com.example.myapp.databinding.AllTrainItemBinding
import com.example.myapp.databinding.GroupExerciseItemBinding
import com.example.myapp.databinding.PickTrainToStartItemBinding
import com.example.myapp.models.TrainingModel

class PickTrainToStartAdapter(val listener: Listener): RecyclerView.Adapter<PickTrainToStartAdapter.PickTrainToStartHolder>() {
    val  pickTrainToStartList = ArrayList<TrainingModel>()

    class PickTrainToStartHolder(item: View): RecyclerView.ViewHolder(item) {
        val binding = PickTrainToStartItemBinding.bind(item)
        fun bind(trainingModel: TrainingModel, listener: Listener) = with(binding) {
            idTrain.text = trainingModel.id.toString()
            nameTrain.text = trainingModel.name
            itemView.setOnClickListener(){
                listener.onClick(trainingModel)
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



    fun setList(trainings: List<TrainingModel>) {
        pickTrainToStartList.clear()
        pickTrainToStartList.addAll(trainings)
    }

//    fun addGroupExercise(groupExercise: TrainingModel){
//        pickTrainToStartList.add(groupExercise)
//        notifyDataSetChanged()
//    }

    interface Listener{
        fun onClick(trainingModel: TrainingModel){

        }
    }

}