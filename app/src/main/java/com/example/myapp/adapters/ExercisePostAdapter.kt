package com.example.myapp.adapters

import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.size
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.*
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.databinding.AllTrainItemBinding
import com.example.myapp.databinding.ExerciseItemPostBinding
import com.example.myapp.databinding.TrainItemBinding
import com.example.myapp.models.LineWithExercises
import com.example.myapp.models.TrainingModel
import com.example.myapp.viewModels.TrainingViewModel
import java.io.ByteArrayInputStream
import java.io.InputStream
import java.util.*


class ExercisePostAdapter(
                      private val trainingViewModel: TrainingViewModel) : RecyclerView.Adapter<ExercisePostAdapter.TrainingHolder>() {

    //public val trainingList = ArrayList<LineWithExercises>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrainingHolder {
        val binding : ExerciseItemPostBinding = ExerciseItemPostBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TrainingHolder(binding)
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TrainingHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    fun setList(training: List<LineWithExercises>) {
        differ.submitList(training)
    }

    class TrainingHolder(val binding: ExerciseItemPostBinding) : RecyclerView.ViewHolder(binding.root) {

        private var onItemClickListener: ((String) -> Unit)? = null

        fun setOnItemClickListener(listener: (String) -> Unit) {
            onItemClickListener = listener
        }

        fun bind(
            trainingModel: LineWithExercises,


        ) {
            binding.root.setOnClickListener {
                onItemClickListener?.let {
                    it(trainingModel.toString())
                }
            }
            fun decodeBase64AndSetImage(completeImageData: String, imageView: ImageView) {
                val imageDataBytes = completeImageData.substring(completeImageData.indexOf(",") + 1)
                val stream: InputStream =
                    ByteArrayInputStream(Base64.decode(imageDataBytes.toByteArray(), Base64.DEFAULT))
                val bitmap = BitmapFactory.decodeStream(stream)
                imageView.setImageBitmap(bitmap)
            }

            binding.idTrain.text = trainingModel.exercise.id.toString()
            binding.nameExercise.text = trainingModel.exercise.name.toString()
            binding.count.text = trainingModel.playlist.count.toString()
            decodeBase64AndSetImage(trainingModel.exercise.image, binding.imageCardExercise)
            //binding.categoryExercise.text = exercisesModel.category


        }
    }

    private val differCallBack  = object : DiffUtil.ItemCallback<LineWithExercises>()
    {

        override fun areItemsTheSame(oldItem: LineWithExercises, newItem: LineWithExercises): Boolean {
            return  oldItem.playlist.id == newItem.playlist.id
        }

        override fun areContentsTheSame(oldItem: LineWithExercises, newItem: LineWithExercises): Boolean {
            return  oldItem.playlist.id == newItem.playlist.id
        }


    }

    val differ = AsyncListDiffer(this, differCallBack)
    var saveList : List<LineWithExercises>? = null


    public val itemTouchHelper by lazy {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(UP or DOWN, 0) {

            override fun onMove(recyclerView: RecyclerView,
                                viewHolder: RecyclerView.ViewHolder,
                                target: RecyclerView.ViewHolder): Boolean {
                val adapter = recyclerView.adapter as TrainingAdapter

                //the position from where item has been moved

                val from = viewHolder.adapterPosition

                //the position where the item is moved
                val to = target.adapterPosition

                //telling the adapter to move the item
                adapter.moveItemInRecyclerViewList(from, to)




                adapter.notifyItemMoved(from, to)

                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

                //on swipe tells you when an item is swiped left or right from its position ( swipe to delete)
            }

            override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                super.onSelectedChanged(viewHolder, actionState)
                if (actionState == ACTION_STATE_DRAG) {
                    viewHolder?.itemView?.alpha = 0.5f
                }

                //when an item changes its location that is currently selected this funtion is called

            }

            override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                super.clearView(recyclerView, viewHolder)
                viewHolder.itemView.alpha = 1.0f

                //when we stop dragging , swiping or moving an item this function is called

            }
        }

        ItemTouchHelper(simpleItemTouchCallback)
    }

    fun moveItemInRecyclerViewList(from: Int, to: Int) {
        Log.d("training_adapter", "${from}:${to}")


        val list = differ.currentList.toMutableList()
        val fromLocation = list[from]

        val saveMutableList: MutableList<LineWithExercises>
        if (saveList == null){
            saveMutableList = list.toMutableList()
        }
        else{
            saveMutableList = saveList!!.toMutableList()
        }

        val fromLocationSave = saveMutableList!![from]

        if (from < to) {
            for (i in from until to) {
                Collections.swap(saveMutableList, i, i + 1)
            }
        } else {
            for (i in from downTo to + 1) {
                Collections.swap(saveMutableList, i, i - 1)
            }
        }


        fromLocation.playlist.id
        Log.d("training_adapter_exe", "${fromLocation.exercise.name}: ${fromLocation.playlist.sequence}")
        Log.d("training_new_exe", "${fromLocationSave.exercise.name}: ${fromLocationSave.playlist.sequence}")

        list.removeAt(from)
        //saveMutableList.removeAt(from)

        if (from < to) {
            for (i in from until to) {
                //          Collections.swap(saveList, i, i + 1)
            }
        } else {
            for (i in from downTo to + 1) {
                //          Collections.swap(saveList, i, i - 1)
            }
        }

        if (to < from) {
            //+1 because it start from 0 on the upside. otherwise it will not change the locations accordingly
            list.add(to + 1, fromLocation)
            fromLocation.playlist.sequence = to

        } else {
//                   //-1 because it start from length + 1 on the down side. otherwise it will not change the locations accordingly
            list.add(to - 1, fromLocation)

        }

        setList(list)
        saveList = saveMutableList
    }
}


