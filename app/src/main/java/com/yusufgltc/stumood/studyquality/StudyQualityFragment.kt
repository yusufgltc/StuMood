package com.yusufgltc.stumood.studyquality

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.yusufgltc.stumood.R
import com.yusufgltc.stumood.database.StudyDatabase
import com.yusufgltc.stumood.databinding.FragmentStudyQualityBinding

class StudyQualityFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentStudyQualityBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_study_quality, container, false)
        val application = requireNotNull(this.activity).application

        val arguments = StudyQualityFragmentArgs.fromBundle(requireArguments())

        val dataSource = StudyDatabase.getInstance(application)!!.studyDatabaseDao
        val viewModelFactory = StudyQualityViewModelFactory(arguments.studyKey, dataSource)

        val studyQualityViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(StudyQualityViewModel::class.java)

        binding.studyQualityViewModel = studyQualityViewModel

        studyQualityViewModel.navigateToStudyTracker.observe(viewLifecycleOwner, Observer {
            if (it == true) { // Observed state is true.
                this.findNavController().navigate(
                    StudyQualityFragmentDirections.actionStudyQualityFragmentToStudyTrackerFragment()
                )
                studyQualityViewModel.doneNavigating()
            }
        })

        return binding.root
    }

}