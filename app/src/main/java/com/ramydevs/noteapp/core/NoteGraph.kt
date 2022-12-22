package com.ramydevs.noteapp.core

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ramydevs.noteapp.ui.screens.add_note.AddEditNoteScreen
import com.ramydevs.noteapp.ui.screens.note_list.NoteListScreen
import com.ramydevs.noteapp.utils.NoteDestinationArgs
import com.ramydevs.noteapp.utils.NoteDestinations

@Composable
fun NoteGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = navController,
        startDestination = NoteDestinations.NoteList.route
    ) {
        composable(NoteDestinations.NoteList.route) {
            val userMessage: String? = it.savedStateHandle[MESSAGE_KEY]
            NoteListScreen(
                userMessage = userMessage,
                toAddEditNoteScreen = { noteId: Int? ->
                    navController.currentBackStackEntry!!
                        .savedStateHandle.remove<String>(MESSAGE_KEY)

                    navController.navigate(
                        NoteDestinations.AddEditNote
                            .setArgs(
                                NoteDestinationArgs.NOTE_ID_ARG to noteId,
                            )
                    )
                },
            )
        }
        composable(
            route = NoteDestinations.AddEditNote.withArgs(
                NoteDestinationArgs.NOTE_ID_ARG
            ),
            arguments = listOf(
                navArgument(NoteDestinationArgs.NOTE_ID_ARG) {
                    type = NavType.IntType
                    defaultValue = -1
                }
            )
        ) {
            AddEditNoteScreen(
                onBack = {
                    it?.let {
                        navController.previousBackStackEntry!!
                            .savedStateHandle[MESSAGE_KEY] = it
                    }
                    navController.popBackStack()
                }
            )

        }

    }

}
private const val MESSAGE_KEY = "message"
