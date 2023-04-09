package com.example.myapp.fragments

import com.example.myapp.R
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapp.adapters.GroupExerciseAdapter
import com.example.myapp.data.GroupExercise
import com.example.myapp.databinding.FragmentGroupExerciseBinding

class GroupExerciseFragment :Fragment(), GroupExerciseAdapter.Listener {
    private lateinit var binding: FragmentGroupExerciseBinding
    private var adapter = GroupExerciseAdapter(this)

    private val imageGroupExerciseIdList = listOf(
        R.drawable.abs,
        R.drawable.quads,
        R.drawable.glutes,
        R.drawable.triceps,
        R.drawable.biceps,
        R.drawable.back,
        R.drawable.chest,
    )
    private val titleGroupExerciseIdList = listOf(
        "Пресс",
        "Квадрицепсы",
        "Ягодицы",
        "Трицепсы",
        "Бицепсы",
        "Спина",
        "Грудь",
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentGroupExerciseBinding.inflate(inflater,container, false )
        init()

        return binding.root
    }

    override fun onClick(groupExercise: GroupExercise) {
        super.onClick(groupExercise)
        Toast.makeText(context, groupExercise.title, Toast.LENGTH_LONG).show()
        val transaction  = activity?.supportFragmentManager?.beginTransaction()
        transaction?.replace(R.id.content, ExerciseFragment())
        transaction?.commit()
    }

    private fun init(){
        binding.apply {
            rcViewGroups.layoutManager = GridLayoutManager(context, 2 )
            rcViewGroups.adapter = adapter

            for (i in 0..imageGroupExerciseIdList.size-1){
                val groupExercise = GroupExercise(imageGroupExerciseIdList[i], titleGroupExerciseIdList[i])
                adapter.addGroupExercise(groupExercise)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = GroupExerciseFragment()
    }


}