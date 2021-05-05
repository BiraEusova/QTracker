package com.example.qtracker.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.ArrayRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.qtracker.R
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.qtracker.databinding.SignUpFragmentBinding
import com.tiper.MaterialSpinner
import kotlinx.android.synthetic.main.sign_up_fragment.view.*
import java.util.*

class SignUpFragment : Fragment() {

    private var _binding: SignUpFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var signUpViewModel: SignUpViewModel
    private lateinit var navController: NavController


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment)
        _binding = DataBindingUtil.inflate(inflater,R.layout.sign_up_fragment, container,false)
        signUpViewModel = SignUpViewModel(requireContext())

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.signUpViewModel = signUpViewModel
        binding.lifecycleOwner = this

        setSpinner(binding.groupSpinner, R.array.planets_array)
        setSpinner(binding.subgroupSpinner, R.array.planets_array)

        signUpViewModel.userSignedUp.observe(viewLifecycleOwner, Observer {
            if (it){
                navController.navigate(R.id.bottomNavigation)
            }
        })

        signUpViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
        })


        binding.signUpButton.setOnClickListener {
            signUpViewModel.init(binding.groupSpinner.selectedItem.toString(),binding.subgroupSpinner.selectedItem.toString())
            signUpViewModel.signUp()
            //navController.navigate(R.id.bottomNavigation)
        }

        binding.backButton.setOnClickListener {
            navController.navigate(R.id.action_signUpFragment_to_signInFragment)
        }
    }

    private fun setSpinner(spinner: MaterialSpinner, res: Int){
        ArrayAdapter.createFromResource(
            requireContext(),
            res,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }
    }
}