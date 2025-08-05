package com.example.note.ui.theme.feature_note.presentation.add_edit_notes

import android.util.Log
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.note.ui.theme.feature_note.domain.model.Note
import com.example.note.ui.theme.feature_note.presentation.add_edit_notes.components.TransparentTextField
import com.example.note.ui.theme.feature_note.presentation.add_edit_notes.viewModel.AddEditNoteViewModel
import com.example.note.ui.theme.feature_note.presentation.add_edit_notes.viewModel.UiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun AddEditNoteScreen(
    navController: NavController,
    noteColor: Int,
    viewModel: AddEditNoteViewModel = hiltViewModel<AddEditNoteViewModel>()
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.noteContent.value

    val snackBarHostState = remember { SnackbarHostState() }

    val noteBackgroundAnimatable = remember {
        Animatable(
            Color(if (noteColor != -1) noteColor else viewModel.noteColor.value)
        )
    }

    Log.d("colorbg", noteColor.toString())

    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = true) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                UiState.SaveNote -> {
                    navController.navigateUp()
                }

                is UiState.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(
                        message = event.message
                    )
                }
            }
        }
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                viewModel.onEvent(AddEditNoteEvents.SaveNote)
            }) {
                Icon(
                    imageVector = Icons.Default.Save,
                    contentDescription = "Save"
                )
            }
        },
        snackbarHost = {
            SnackbarHost(hostState = snackBarHostState)
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(noteBackgroundAnimatable.value)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Note.notColors.forEach { color ->
                        val colorInt = color.toArgb()
                        Box(
                            modifier = Modifier
                                .size(50.dp)
                                .shadow(25.dp, CircleShape)
                                .clip(CircleShape)
                                .background(color)
                                .border(
                                    3.dp,
                                    if (viewModel.noteColor.value == colorInt) Color.Black else Color.Transparent,
                                    CircleShape
                                )
                                .clickable {
                                    scope.launch {
                                        noteBackgroundAnimatable.animateTo(
                                            targetValue = Color(colorInt),
                                            animationSpec = tween(
                                                durationMillis = 500
                                            )
                                        )
                                    }
                                    viewModel.onEvent(AddEditNoteEvents.ChangeColor(colorInt))
                                }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                TransparentTextField(
                    text = titleState.text,
                    hint = titleState.hintText,
                    onValueChange = {
                        viewModel.onEvent(AddEditNoteEvents.EnteredTitle(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditNoteEvents.ChangeFocusTitle(it))
                    },
                    isHintVisible = titleState.isHintVisible,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.headlineLarge
                )

                Spacer(modifier = Modifier.height(16.dp))

                TransparentTextField(
                    text = contentState.text,
                    hint = contentState.hintText,
                    onValueChange = {
                        viewModel.onEvent(AddEditNoteEvents.EnteredContent(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditNoteEvents.ChangeFocusContent(it))
                    },
                    isHintVisible = contentState.isHintVisible,
                    singleLine = false,
                    textStyle = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}