package com.example.verygoodtaskplanner.di

import androidx.room.Room
import com.example.verygoodtaskplanner.data.database.TaskDatabase
import com.example.verygoodtaskplanner.data.repositories.TaskRepositoryImpl
import com.example.verygoodtaskplanner.domain.HourInteractor
import com.example.verygoodtaskplanner.presentation.repositories.TasksRepository
import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Router
import org.koin.core.component.KoinComponent
import org.koin.dsl.module

object Modules : KoinComponent {
    val router = module {
        single { Cicerone.create() }
        single { get<Cicerone<Router>>().router }
        single { get<Cicerone<Router>>().getNavigatorHolder() }
    }
    val room = module {
        single {
            Room.databaseBuilder(
                get(),
                TaskDatabase::class.java, "tasks-database"
            ).build()
        }
    }
    val repositories = module {
        factory<TasksRepository> { TaskRepositoryImpl() }
    }
    val interactors = module {
        factory { HourInteractor() }
    }
}