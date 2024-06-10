package com.blazek10.racelog.ui.db

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AthletesViewModel : ViewModel() {
    val state = mutableStateOf<List<Athlete>>(emptyList())

    init {
        getData()
    }

    private fun getData() {
        viewModelScope.launch {
            state.value = getAthletes()
        }
    }
}

suspend fun getAthletes(): List<Athlete> {
    val db = FirebaseFirestore.getInstance()
    val athletes = mutableListOf<Athlete>()

    try {
        val querySnapshot = db.collection("athletes").get().await()
        for (document in querySnapshot.documents) {
            val athlete = document.toObject(Athlete::class.java)
            if (athlete != null) {
                athletes.add(athlete)
            }
        }
    } catch (e: FirebaseFirestoreException) {
        return emptyList()
    }

    return athletes
}

suspend fun createAthlete(athlete: Athlete) {
    val db = FirebaseFirestore.getInstance()
    val querySnapshot = db.collection("athletes")
        .whereEqualTo("bib", athlete.bib)
        .get()
        .await()

    if (!querySnapshot.isEmpty) {
        return
    }
    db.collection("athletes").add(athlete).await()
}