package com.example.myapp

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.databinding.FragmentTrainCreatorBinding
import com.example.myapp.fragments.CreationExerciseFragment
import com.example.myapp.fragments.ExerciseFragment
import com.example.myapp.fragments.OptionsFragment
import com.example.myapp.utils.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener{
    private lateinit var binding: ActivityMainBinding
    var toolbar: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        toolbar = binding.bottomNav
        binding?.bottomNav?.setOnNavigationItemSelectedListener(this)
        binding?.bottomNav?.selectedItemId = R.id.panelExercises
        FragmentManager.setFragment(ExerciseFragment.newInstance(), this)

        //FragmentManager.setFragment(LoginFragment.newInstance(), this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.panelExercises -> FragmentManager.setFragment(ExerciseFragment.newInstance(), this)
            R.id.panelStartTraining -> FragmentManager.setFragment(StartTrainFragment.newInstance(), this)
            R.id.panelOptions -> FragmentManager.setFragment(OptionsFragment.newInstance(), this)
            R.id.panelCreator -> FragmentManager.setFragment(TrainCreatorFragment.newInstance(), this)


        }
        return true
    }
    fun getNav(): BottomNavigationView? {
        return toolbar
    }
}