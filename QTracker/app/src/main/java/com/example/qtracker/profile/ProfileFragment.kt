package com.example.qtracker.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.qtracker.R

class ProfileFragment : Fragment() {

    private lateinit var navController: NavController

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        //navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment)


        return inflater.inflate(R.layout.profile_fragment,
                container, false)
    }
}