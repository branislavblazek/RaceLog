package com.blazek10.racelog.ui.db

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreException
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class RaceLogsViewModel : ViewModel() {
    val state = mutableStateOf<List<RaceLog>>(emptyList())

    fun getData(
        athleteBib: Int?,
        controlPointId: Int?,
    ) {
        viewModelScope.launch {
            state.value = getRaceLogs(athleteBib, controlPointId)
        }
    }

    fun setData(
        athleteBib: Int,
        controlPointId: Int,
        dnf: Boolean,
        dsq: Boolean,
        dns: Boolean,
        finished: Boolean,
        time: Int,
    ) {
        viewModelScope.launch {
            createRaceLog(RaceLog(athleteBib, controlPointId, dnf, dns, dsq, finished, time))
            state.value = getRaceLogs(null, controlPointId)
        }

    }
}

suspend fun getRaceLogs(
    athleteBib: Int?,
    controlPointId: Int?,
): List<RaceLog> {
    val db = FirebaseFirestore.getInstance()
    val raceLogs = mutableListOf<RaceLog>()
    try {
        val querySnapshot = db.collection("raceLogs")
            .let { query ->
                if (athleteBib != null) {
                    query.whereEqualTo("athleteBib", athleteBib)
                } else {
                    query
                }
            }
            .let { query ->
                if (controlPointId != null) {
                    query.whereEqualTo("controlPointId", controlPointId)
                } else {
                    query
                }
            }
            .get()
            .await()
        for (document in querySnapshot.documents) {
            val raceLog = document.toObject(RaceLog::class.java)
            if (raceLog != null) {
                raceLogs.add(raceLog)
            }
        }
    } catch (e: FirebaseFirestoreException) {
        return emptyList()
    }

    return raceLogs
}

suspend fun createRaceLog(raceLog: RaceLog) {
    val db = FirebaseFirestore.getInstance()
    val querySnapshot = db.collection("raceLogs")
        .whereEqualTo("athleteBib", raceLog.athleteBib)
        .whereEqualTo("controlPointId", raceLog.controlPointId)
        .get()
        .await()
    if (!querySnapshot.isEmpty) {
        return
    }

    db.collection("raceLogs").add(raceLog).await()
}