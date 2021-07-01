package com.yusufgltc.stumood.studydetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.yusufgltc.stumood.R
import com.yusufgltc.stumood.database.StudyDatabase
import com.yusufgltc.stumood.databinding.FragmentStudyDetailBinding

class StudyDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding: FragmentStudyDetailBinding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_study_detail, container, false
        )

        val application = requireNotNull(this.activity).application
        val arguments = StudyDetailFragmentArgs.fromBundle(this.arguments as Bundle)

        val dataSource = StudyDatabase.getInstance(application)!!.studyDatabaseDao
        val viewModelFactory = StudyDetailViewModelFactory(arguments.studyKey, dataSource)

        val studyDetailViewModel =
            ViewModelProvider(
                this, viewModelFactory
            ).get(StudyDetailViewModel::class.java)

        binding.studyDetailViewModel = studyDetailViewModel

        binding.setLifecycleOwner(this)

        studyDetailViewModel.navigateToStudyTracker.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(
                    StudyDetailFragmentDirections.actionStudyDetailFragmentToStudyTrackerFragment()
                )
                studyDetailViewModel.doneNavigating()
            }
        })

        return binding.root
    }

}