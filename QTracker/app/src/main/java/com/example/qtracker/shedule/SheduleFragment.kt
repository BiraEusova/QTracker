package com.example.qtracker.shedule

import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qtracker.R
import com.example.qtracker.databinding.SignUpFragmentBinding
import com.example.qtracker.dataclasses.Lesson
import com.example.qtracker.recyclerView.RecyclerAdapter
import com.example.qtracker.signup.SignUpViewModel
import com.tiper.MaterialSpinner
import kotlinx.android.synthetic.main.shedule_fragment.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SheduleFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var sheduleViewModel: SheduleViewModel
    //private var _binding: SignUpFragmentBinding? = null
    //private val binding get() = _binding!!
    private lateinit var adapter: RecyclerAdapter
    private lateinit var mRecyclerView : RecyclerView
    private lateinit var thisView: View

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        //navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment)
        sheduleViewModel = SheduleViewModel(requireContext())

        thisView = inflater.inflate(R.layout.shedule_fragment,
            container, false)

        return thisView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setRecyclerView()
    }

    fun setRecyclerView(){

        adapter = RecyclerAdapter()
        val llm = LinearLayoutManager(requireContext())

        thisView.sheduleRecyclerView.layoutManager = llm
        thisView.sheduleRecyclerView.adapter = adapter

        sheduleViewModel.getData()

        if (sheduleViewModel.lessons.isEmpty()){
            thisView.emptyDayImageView.visibility = VISIBLE;
        }
        else thisView.emptyDayImageView.visibility = INVISIBLE;

        sheduleViewModel.lessons.add(Lesson("1212", "sth", "00:00", "00:00", "smb", "971900", "Red"))

        adapter.set(sheduleViewModel.lessons, requireContext(), thisView)

    }
}