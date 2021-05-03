package com.example.qtracker.signin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.qtracker.R
import kotlinx.android.synthetic.main.sign_in_fragment.view.*

class SignInFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var thisView: View

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        thisView = inflater.inflate(
            R.layout.sign_in_fragment,
            container, false)

        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment)

        thisView.signInButton.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_bottomNavigation)
        }

        thisView.signUpButton.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_signUpFragment)
        }

        return thisView
    }
}