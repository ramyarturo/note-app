package com.ramydevs.noteapp.utils

sealed class NoteDestinations(val route: String) {
    object NoteList : NoteDestinations("note_list_screen")
    object AddEditNote : NoteDestinations("add_edit_note_screen")
    fun withArgs(vararg args: String): String {
        return buildString {
            append(route)
            args.forEachIndexed { index, arg ->
                if (index == 0) {
                    append("?")
                }
                append("$arg={$arg}")
                if (args.size > 1 && index < args.lastIndex) {
                    append("&")
                }
            }
        }
    }
    fun setArgs(vararg args: Pair<String, Any?>): String {
        val mRoute = withArgs(*args.map { it.first }.toTypedArray())
        var newRoute = mRoute
        args.forEach {
            val arg = "{${(it).first}}"
            if (it.second == null) {
                newRoute = newRoute.replace("${it.first}=$arg", "")
            }
            newRoute = newRoute.replace(arg, it.second?.toString() ?: "")
        }
        return newRoute
    }
}

object NoteDestinationArgs {
    const val NOTE_ID_ARG = "note_id_arg"
}