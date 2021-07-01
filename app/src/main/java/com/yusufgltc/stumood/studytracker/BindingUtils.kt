package com.yusufgltc.stumood.studytracker

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.yusufgltc.stumood.R
import com.yusufgltc.stumood.convertDurationToFormatted
import com.yusufgltc.stumood.convertNumericQualityToString
import com.yusufgltc.stumood.database.Study

@BindingAdapter("studyDurationFormatted")
fun TextView.setStudyDurationFormatted(item: Study?) {
    item?.let {
        text = convertDurationToFormatted(item.startTime, item.endTime, context.resources)
    }

}

@BindingAdapter("studyQualityString")
fun TextView.setStudyQualityString(item: Study?) {
    item?.let {
        text = convertNumericQualityToString(item.studyQuality, context.resources)
    }

}

@BindingAdapter("studyImage")
fun ImageView.setStudyImage(item: Study?) {
    item?.let {
        setImageResource(
            when (item.studyQuality) {
                0 -> R.drawable.ic_study_0
                1 -> R.drawable.ic_study_1
                2 -> R.drawable.ic_study_2
                3 -> R.drawable.ic_study_3
                4 -> R.drawable.ic_study_4
                5 -> R.drawable.ic_study_5
                else -> R.drawable.ic_study_active
            }
        )
    }

}