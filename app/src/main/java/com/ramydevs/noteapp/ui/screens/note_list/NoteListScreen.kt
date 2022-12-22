package com.ramydevs.noteapp.ui.screens.note_list

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.ExperimentalLifecycleComposeApi
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramydevs.noteapp.R
import com.ramydevs.noteapp.data.source.note.Note
import com.ramydevs.noteapp.ui.common.CustomRoundedIcon
import com.ramydevs.noteapp.ui.common.CustomTopAppBar
import com.ramydevs.noteapp.ui.common.LoadingContent
import com.ramydevs.noteapp.ui.screens.note_list.components.NoteItem
import com.ramydevs.noteapp.ui.screens.note_list.components.SearchNoteDialog


@OptIn(ExperimentalLifecycleComposeApi::class, ExperimentalAnimationApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NoteListScreen(
    userMessage: String? = null,
    toAddEditNoteScreen: (Int?) -> Unit,
    noteListViewModel: NoteListViewModel = hiltViewModel()
) {
    val uiState = noteListViewModel.uiState.collectAsStateWithLifecycle()
    val lazyListState = rememberLazyListState()
    val scaffoldState = rememberScaffoldState()
    var showSearchDialog by remember {
        mutableStateOf(false)
    }
    val isScrolling by remember {
        derivedStateOf { lazyListState.isScrollInProgress }
    }
    LaunchedEffect(key1 = userMessage){
       userMessage?.let {
           scaffoldState.snackbarHostState.showSnackbar(
               message = it
           )
       }
    }
    if (showSearchDialog) {
        SearchNoteDialog(
            openDialog = showSearchDialog,

            onDismissDialog = {
                showSearchDialog = false
            }
        )
    }
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colors.primary,
        topBar = {
            CustomTopAppBar(
                modifier = Modifier.padding(10.dp),
                title = "Notes",
                actions = {
                    CustomRoundedIcon(
                        painter = painterResource(R.drawable.ic_outline_search_24),
                        onClick = {
                            showSearchDialog = true
                        },
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    CustomRoundedIcon(painter = painterResource(R.drawable.ic_outline_info_24))

                }
            )
        },
        floatingActionButton = {
            AnimatedVisibility(
                visible = !isScrolling,
                enter = scaleIn(),
                exit = scaleOut()
            ) {
                FloatingActionButton(onClick = { toAddEditNoteScreen(null) }) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        tint = Color.White,
                        contentDescription = ""
                    )
                }
            }
        }
    ) {
        NoteListContent(
            noteList = uiState.value.items,
            isLoading = uiState.value.isLoading,
            lazyListState = lazyListState,
            onNoteClick = toAddEditNoteScreen,
        )
    }
}

@Composable
fun NoteListContent(
    noteList: List<Note>,
    isLoading: Boolean = false,
    lazyListState: LazyListState,
    onNoteClick: (Int) -> Unit
) {
    LoadingContent(
        isLoading = isLoading,
        isEmpty = noteList.isEmpty(),
        loadingContent = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LinearProgressIndicator(
                    color = Color.White,
                )
                Text(text = "Loading notes")
            }
        },
        emptyContent = {

            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    modifier = Modifier.size(300.dp),
                    painter = painterResource(R.drawable.img_empty),
                    contentDescription = ""
                )
                Text(text = "Create your first note!")
            }
        }

    ) {
        LazyColumn(
            modifier = Modifier.padding(10.dp),
            state = lazyListState
        ) {

            items(noteList) {
                NoteItem(
                    note = it,
                    onClick = {
                        onNoteClick(it.id)
                    }
                )
            }
        }
    }
}