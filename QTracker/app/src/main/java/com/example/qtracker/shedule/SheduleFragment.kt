package com.example.qtracker.shedule

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.qtracker.R
import com.example.qtracker.recyclerView.RecyclerAdapter
import kotlinx.android.synthetic.main.shedule_fragment.view.*

class SheduleFragment : Fragment() {

    private lateinit var navController: NavController
    private lateinit var mRecyclerView : RecyclerView
    private val mAdapter : RecyclerAdapter = RecyclerAdapter()

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        //navController = Navigation.findNavController(requireActivity(), R.id.navHostFragment)

        var view = inflater.inflate(R.layout.shedule_fragment,
            container, false)

        mRecyclerView = view!!.sheduleRecyclerView
        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter.RecyclerAdapter(/*getNotes(),*/ requireContext())
        mAdapter.getView(view)
        mRecyclerView.adapter = mAdapter


        return inflater.inflate(R.layout.shedule_fragment,
                container, false)
    }
}