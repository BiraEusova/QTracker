package com.example.qtracker.profile

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.qtracker.R
import kotlinx.android.synthetic.main.profile_fragment.*


class ProfileFragment : Fragment() {

    private lateinit var navController: NavController
    private var passwordVisible = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment)

        visibilityImageButton.setOnClickListener{
            if (passwordVisible) {
                visibilityImageButton.setImageDrawable(
                    getDrawable(
                        requireContext(),
                        R.drawable.password_visibility_on
                    )
                )
                passwordTextInputEditText.inputType = InputType.TYPE_CLASS_TEXT or
                        InputType.TYPE_CLASS_TEXT
                passwordTextInputEditText.text?.let { it1 ->
                    passwordTextInputEditText.setSelection(
                        it1.length)
                }
            }
            else {
                visibilityImageButton.setImageDrawable(
                    getDrawable(
                        requireContext(),
                        R.drawable.password_visibility_off
                    )
                )
                passwordTextInputEditText.inputType = InputType.TYPE_CLASS_TEXT or
                        InputType.TYPE_TEXT_VARIATION_PASSWORD
                passwordTextInputEditText.text?.let { it1 ->
                    passwordTextInputEditText.setSelection(
                        it1.length)
                }
            }
        }

        return inflater.inflate(
            R.layout.profile_fragment,
            container, false
        )
    }
}