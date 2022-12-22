package com.ramydevs.noteapp.ui.screens.add_note

import androidx.compose.foundation.LocalIndication
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramydevs.noteapp.R
import com.ramydevs.noteapp.ui.common.ConfirmAlertDialog
import com.ramydevs.noteapp.ui.common.CustomRoundedIcon
import com.ramydevs.noteapp.ui.common.CustomTextField
import com.ramydevs.noteapp.ui.common.CustomTopAppBar
import kotlinx.coroutines.flow.collectLatest

data class DialogPayload(
    val show: Boolean = false,
    val message: String = "",
    val confirmText: String = "",
    val onConfirm: () -> Unit = {},
    val onDismiss: () -> Unit = {},
)

@OptIn(ExperimentalLifecycleComposeApi::class)
@Composable
fun AddEditNoteScreen(
    addEditNoteViewModel: AddEditNoteViewModel = hiltViewModel(),
    onBack: (String?) -> Unit = {}
) {
    val localContext = LocalContext.current
    var showConfirmDialog by remember(localContext) {
        mutableStateOf(DialogPayload())
    }
    val isEditing = addEditNoteViewModel.isEditing
    var isInViewMode by remember {
        mutableStateOf(isEditing)
    }
    val uiState = addEditNoteViewModel.uiState.collectAsStateWithLifecycle()
    val scaffoldState = rememberScaffoldState()

    LaunchedEffect(key1 = Unit) {
        addEditNoteViewModel.uiEvent.collectLatest {
            when (it) {

                UiEvent.SavedNote -> {
                    onBack("note saved successfully")
                }
                UiEvent.DeletedNote -> {
                    onBack("note deleted successfully")
                }
                is UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        it.message
                    )
                }
            }
        }
    }
    if (showConfirmDialog.show) {
        ConfirmAlertDialog(
            show = showConfirmDialog.show,
            message = showConfirmDialog.message,
            confirmText = showConfirmDialog.confirmText,
            onConfirm = showConfirmDialog.onConfirm,
            onDismiss = {
                showConfirmDialog.onDismiss()
                showConfirmDialog = showConfirmDialog.copy(
                    show = false,
                )
            }
        )
    }

    Scaffold(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
            .padding(10.dp),
        backgroundColor = MaterialTheme.colors.primary,
        scaffoldState = scaffoldState,
        topBar = {

            CustomTopAppBar(
                showNavButton = true,
                onNavIconClick = {
                    if (addEditNoteViewModel.hasUserEditedNote) {
                        showConfirmDialog = showConfirmDialog.copy(
                            show = true,
                            message = "Are your sure you want discard your changes ?",
                            confirmText = "Keep",
                            onDismiss = { onBack(null) },
                            onConfirm = {
                                showConfirmDialog = showConfirmDialog.copy(
                                    show = false
                                )
                            }
                        )
                    } else {
                        onBack(null)
                    }
                },
                actions = {
                    if (isInViewMode) {
                        CustomRoundedIcon(painter = painterResource(R.drawable.ic_outline_edit_24),
                            onClick = { isInViewMode = false })
                    } else {
                        CustomRoundedIcon(painter = painterResource(R.drawable.ic_outline_eye_24),
                            onClick = { isInViewMode = true })
                        Spacer(modifier = Modifier.width(10.dp))
                        if (isEditing) {
                            CustomRoundedIcon(painter = painterResource(R.drawable.ic_outline_delete_24),
                                onClick = {
                                    showConfirmDialog = showConfirmDialog.copy(
                                        show = true,
                                        message = "Delete note?",
                                        confirmText = "Delete",
                                        onConfirm = addEditNoteViewModel::onDeleteNote
                                    )
                                })
                            Spacer(modifier = Modifier.width(10.dp))
                        }

                        CustomRoundedIcon(painter = painterResource(R.drawable.ic_outline_save_24),
                            onClick = {
                                if (isEditing) {
                                    showConfirmDialog = showConfirmDialog.copy(
                                        show = true,
                                        message = "Save changes?",
                                        confirmText = "Save",
                                        onConfirm = addEditNoteViewModel::onSaveNote
                                    )
                                } else {
                                    addEditNoteViewModel.onSaveNote()
                                }
                            })
                    }

                }
            )
        }
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    enabled = !isInViewMode,
                    indication = if (!isInViewMode) LocalIndication.current else null,
                ) {}
                .padding(10.dp),
            ) {

            CustomTextField(
                modifier = Modifier.fillMaxWidth(),
                value = uiState.value.note.title,
                onValueChanged = addEditNoteViewModel::onTitleChanged,
                hint = "Title",
                textStyle = MaterialTheme.typography.h5
            )
            Spacer(modifier = Modifier.height(10.dp))
            CustomTextField(
                modifier = Modifier
                    .fillMaxHeight(),
                value = uiState.value.note.content,
                onValueChanged = addEditNoteViewModel::onContentChanged,
                hint = "Type something...",
                textStyle = MaterialTheme.typography.body1
            )


        }
    }
}
