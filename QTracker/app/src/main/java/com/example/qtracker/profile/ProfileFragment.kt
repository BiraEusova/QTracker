package com.example.qtracker.profile

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.qtracker.R
import kotlinx.android.synthetic.main.profile_fragment.*
import kotlinx.android.synthetic.main.profile_fragment.view.*


class ProfileFragment : Fragment() {

    private lateinit var navController: NavController
    private var passwordVisible = false
    private var editing = false
    private lateinit var thisView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment)

        thisView = inflater.inflate(
            R.layout.profile_fragment,
            container, false
        )


        return thisView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setEnableTextField()
        changeView()

        saveButton.setOnClickListener {
            editing = false
            setEnableTextField()
            changeView()
        }

        editButton.setOnClickListener {
            editing = true
            setEnableTextField()
            changeView()
        }

        visibilityImageButton.setOnClickListener{
            changePasswordVisibility()
        }
    }

    private fun changePasswordVisibility(){
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

            passwordVisible = false
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

            passwordVisible = true
        }
    }

    private fun setEnableTextField(){
        thisView.nameTextField.isEnabled = editing
        thisView.passwordTextField.isEnabled = editing
        thisView.groupSpinner.isEnabled = editing
        thisView.subgroupSpinner.isEnabled = editing
    }

    private fun changeView(){
        thisView.editButton.visibility = if (editing) INVISIBLE else VISIBLE
        thisView.allPositionsButton.visibility = if (editing) INVISIBLE else VISIBLE
        thisView.saveButton.visibility = if (!editing) INVISIBLE else VISIBLE
    }
}