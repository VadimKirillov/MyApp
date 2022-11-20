package com.example.myapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.example.CreateOrUpdateExercicesMutation
import com.example.myapp.databinding.ActivityMainBinding
import com.example.myapp.databinding.TabExercisesBinding
import com.example.myapp.fragments.CreationExerciseFragment
import com.example.myapp.fragments.ExerciseFragment
import com.example.myapp.fragments.OptionsFragment
import com.example.myapp.utils.FragmentManager
import com.example.type.ExerciseReqNameInput
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() , BottomNavigationView.OnNavigationItemSelectedListener{
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.bottomNav?.setOnNavigationItemSelectedListener(this)
        binding?.bottomNav?.selectedItemId = R.id.panelExercises
        FragmentManager.setFragment(ExerciseFragment.newInstance(), this)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.panelExercises -> FragmentManager.setFragment(ExerciseFragment.newInstance(), this)
            R.id.panelOptions -> FragmentManager.setFragment(OptionsFragment.newInstance(), this)
            R.id.panelCreator -> FragmentManager.setFragment(CreationExerciseFragment.newInstance(), this)

        }
        return true
    }
}