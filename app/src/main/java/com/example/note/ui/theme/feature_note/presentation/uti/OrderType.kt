package com.example.note.ui.theme.feature_note.presentation.uti

import androidx.room.Index

sealed class OrderType {
    object Ascending: OrderType()
    object Descending: OrderType()
}