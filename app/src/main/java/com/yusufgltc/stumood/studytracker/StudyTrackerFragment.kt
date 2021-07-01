package com.yusufgltc.stumood.studytracker

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.yusufgltc.stumood.R
import com.yusufgltc.stumood.database.StudyDatabase
import com.yusufgltc.stumood.databinding.FragmentStudyTrackerBinding

class StudyTrackerFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentStudyTrackerBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_study_tracker, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = StudyDatabase.getInstance(application)!!.studyDatabaseDao
        val viewModelFactory = StudyTrackerViewModelFactory(dataSource, application)

        val studyTrackerViewModel =
            ViewModelProvider(this, viewModelFactory).get(StudyTrackerViewModel::class.java)

        binding.studyTrackerViewModel = studyTrackerViewModel
        val adapter = StudyAdapter(StudyListener { studyId ->
            //Toast.makeText(context, "${studyId}", Toast.LENGTH_LONG).show()
            studyTrackerViewModel.onStudyClicked(studyId)
        })
        binding.studyList.adapter = adapter

        studyTrackerViewModel.studies.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.addHeaderAndSubmitList(it)
            }
        })

        binding.setLifecycleOwner(this)

        studyTrackerViewModel.showSnackBarEvent.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                Snackbar.make(
                    requireActivity().findViewById(android.R.id.content),
                    getString(R.string.cleared_message),
                    Snackbar.LENGTH_SHORT
                ).show()
                studyTrackerViewModel.doneShowingSnackbar()
            }
        })


        studyTrackerViewModel.navigateToStudyQuality.observe(viewLifecycleOwner, Observer { study ->
            study?.let {
                this.findNavController().navigate(
                    StudyTrackerFragmentDirections
                        .actionStudyTrackerFragmentToStudyQualityFragment(study.studyId)
                )
                studyTrackerViewModel.doneNavigating()
            }
        })

        studyTrackerViewModel.navigateToStudyDetail.observe(viewLifecycleOwner, Observer { study ->
            study?.let {
                this.findNavController().navigate(
                    StudyTrackerFragmentDirections
                        .actionStudyTrackerFragmentToStudyDetailFragment(study)
                )
                studyTrackerViewModel.onStudyDetailNavigated()
            }
        })

//        val manager = GridLayoutManager(activity, 3)
//        binding.studyList.layoutManager = manager
        return binding.root
    }
}