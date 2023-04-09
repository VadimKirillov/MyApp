package com.example.myapp

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.fragments.*
import com.example.myapp.utils.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener{
    private lateinit var binding: ActivityMainBinding
    lateinit var toolbar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        toolbar = binding.bottomNav
        binding.bottomNav.setOnNavigationItemSelectedListener(this)
        binding.bottomNav.selectedItemId = R.id.panelExercises
        FragmentManager.setFragment(GroupExerciseFragment.newInstance(), this)

        //val navController = Navigation.findNavController(this,R.id.nav_graph)

//        val navHostFragment = supportFragmentManager.findFragmentById() as NavHostFragment
//        val navController = navHostFragment.navController
//        val navInflater = navController.navInflater
//        val graph = navInflater.inflate(R.navigation.nav_graph)
//        navController.graph = graph
}

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.panelExercises -> FragmentManager.setFragment(GroupExerciseFragment.newInstance(), this)
            R.id.panelStartTraining -> FragmentManager.setFragment(StartTrainFragment.newInstance(), this)
            R.id.panelOptions -> FragmentManager.setFragment(OptionsFragment.newInstance(), this)
            R.id.panelCreator -> FragmentManager.setFragment(TrainCreatorFragment.newInstance(), this)
        }
        return true
    }
}