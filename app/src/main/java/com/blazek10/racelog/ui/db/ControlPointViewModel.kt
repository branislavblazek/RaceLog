package com.blazek10.racelog.ui.db

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ControlPointViewModel : ViewModel() {
    val state = mutableStateOf<ControlPoint?>(null)

    fun login(id: String, password: String) {
        viewModelScope.launch {
            state.value = getControlPoint(id.toInt(), password)
        }
    }

    fun reset() {
        state.value = null
    }
}

suspend fun getControlPoint(id: Int, password: String): ControlPoint {
    val db = FirebaseFirestore.getInstance()
    var controlPointResult = ControlPoint()

    try {
        val querySnapshot = db.collection("controlPoints")
            .whereEqualTo("id", id)
            .whereEqualTo("password", password)
            .get()
            .await()
        for (document in querySnapshot.documents) {
            val controlPoint = document.toObject(ControlPoint::class.java)
            if (controlPoint != null) {
                controlPointResult = controlPoint
            }
        }
    } catch (e: FirebaseFirestoreException) {
        // log into console
    }

    return controlPointResult
}