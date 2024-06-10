package com.blazek10.racelog.ui.db

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AthleteViewModel : ViewModel() {
    val state = mutableStateOf<Athlete?>(null)

    fun getData(athleteId: Int) {
        viewModelScope.launch {
            state.value = getAthlete(athleteId)
        }
    }
}

suspend fun getAthlete(id: Int): Athlete {
    val db = FirebaseFirestore.getInstance()
    var athleteResult = Athlete()

    try {
        val querySnapshot = db.collection("athletes")
            .whereEqualTo("bib", id)
            .get()
            .await()
        for (document in querySnapshot.documents) {
            val athlete = document.toObject(Athlete::class.java)
            if (athlete != null) {
                athleteResult = athlete
            }
        }
    } catch (e: FirebaseFirestoreException) {
        // log into console
    }

    return athleteResult
}