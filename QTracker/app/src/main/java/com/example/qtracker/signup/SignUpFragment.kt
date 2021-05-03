package com.example.qtracker.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.qtracker.R
import com.example.qtracker.spinner.SpinnerAdapter
import com.tiper.MaterialSpinner
import kotlinx.android.synthetic.main.sign_up_fragment.*
import kotlinx.android.synthetic.main.sign_up_fragment.view.*

class SignUpFragment : Fragment() {

    private lateinit var navController: NavController
    //private lateinit var spinnerAdapter: SpinnerAdapter
    private lateinit var thisView: View

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        thisView = inflater.inflate(
                R.layout.sign_up_fragment,
                container, false)

        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment)

        thisView.backButton.setOnClickListener {
            navController.navigate(R.id.action_signUpFragment_to_signInFragment)
        }

        thisView.signUpButton.setOnClickListener {
            navController.navigate(R.id.bottomNavigation)
        }

        /*val exampleGroupList = listOf("971901", "971902", "972001", "972002");
        val groupSpinner = thisView.groupSpinner as MaterialSpinner
        spinnerAdapter = SpinnerAdapter(this.requireContext(), exampleGroupList)
        groupSpinner.adapter = spinnerAdapter*/

        return thisView

    }

}