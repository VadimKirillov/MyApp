package com.example.myapp.fragments

import android.R
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.fragment.app.DialogFragment
import com.example.myapp.databinding.FragmentMuscleGroupPickerBinding


class MuscleGroupPickerFragment : DialogFragment(), View.OnClickListener {

    private var binding: FragmentMuscleGroupPickerBinding? = null

    private var idExercise:Int? = null
    private var lvMain: ListView? = null
    private var names: Array<String?>? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentMuscleGroupPickerBinding.inflate(inflater, container, false)

        //idExercise = arguments?.getString("idExercise")?.toInt()
        binding?.btnChecked?.setText(arguments?.getString("Muscle").toString())

        val itemsBodyPart = listOf("Ноги", "Руки", "Спина", "Плечи", "Грудь")
        lvMain = binding!!.lvMain
        // устанавливаем режим выбора пунктов списка
        // устанавливаем режим выбора пунктов списка
        lvMain!!.choiceMode = ListView.CHOICE_MODE_SINGLE
        // Создаем адаптер, используя массив из файла ресурсов
        // Создаем адаптер, используя массив из файла ресурсов
        val adapter = ArrayAdapter(context?.applicationContext!!, R.layout.simple_list_item_single_choice, itemsBodyPart)

        lvMain!!.adapter = adapter

        lvMain!!.setItemChecked(0,true)
        // получаем массив из файла ресурсов

        // получаем массив из файла ресурсов
        names = resources.getStringArray(com.example.myapp.R.array.names)

        binding!!.btnChecked.setOnClickListener(this)

        return binding?.root

    }

    override fun onClick(view: View) {
        Log.e("ww", names!![lvMain!!.checkedItemPosition]!!)
        dismiss()
        val panelEditMuscleGroup = CreationExerciseFragment()
        val parameters = Bundle()
        parameters.putString("Muscle", names!![lvMain!!.checkedItemPosition]!!)
        panelEditMuscleGroup.arguments = parameters
        //EditCountTrainFragment().show((context as FragmentActivity).supportFragmentManager, "editTrain")
        dismiss()

        //(context as FragmentActivity).supportFragmentManager.beginTransaction().addToBackStack(null);
        //(context as FragmentActivity).supportFragmentManager.beginTransaction().replace(R.id.content, panelEditMuscleGroup).commit()


    }

}