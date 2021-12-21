package com.example.verygoodtaskplanner.data.entities

//можно потом перевести в json....
data class TaskModel(
    val dateStart: Long,
    val dateFinish: Long,
    val name: String,
    val description: String
) {
    private var id: Long = 0
}

