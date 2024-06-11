package com.blazek10.racelog.ui.db

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ControlPointNameViewModel : ViewModel() {
    val state = mutableStateOf<List<ControlPoint>>(emptyList())

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            state.value = getControlPoints()
        }
    }
}

suspend fun getControlPoints(): List<ControlPoint> {
    val db = FirebaseFirestore.getInstance()
    val controlPoints = mutableListOf<ControlPoint>()

    val querySnapshot = db.collection("controlPoints").get().await()
    for (document in querySnapshot.documents) {
        val controlPoint = document.toObject(ControlPoint::class.java)
        if (controlPoint != null) {
            controlPoints.add(controlPoint)
        }
    }

    return controlPoints
}
