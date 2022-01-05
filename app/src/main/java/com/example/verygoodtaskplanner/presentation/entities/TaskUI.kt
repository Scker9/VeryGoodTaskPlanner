package com.example.verygoodtaskplanner.presentation.entities

import android.os.Parcelable
import com.example.verygoodtaskplanner.data.entities.TimeRange
import kotlinx.parcelize.Parcelize

@Parcelize
data class TaskUI(
    override var dateStart: Long,
    override var dateFinish: Long,
    var name: String,
    var description: String,
    var id: Long? = null // при создании null, при редактировании не null
) : TimeRange(), Parcelable