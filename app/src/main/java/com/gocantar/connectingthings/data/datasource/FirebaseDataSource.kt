package com.gocantar.connectingthings.data.datasource

import android.util.Log
import com.gocantar.connectingthings.data.extensions.toHumidity
import com.gocantar.connectingthings.data.extensions.toTemperature
import com.gocantar.connectingthings.data.model.HumidityFB
import com.gocantar.connectingthings.data.model.TemperatureFB
import com.google.firebase.firestore.FirebaseFirestore
import io.reactivex.subjects.PublishSubject

/**
 * Created by gocantar on 17/1/18.
 */
class FirebaseDataSource {

    private val TAG = javaClass.simpleName

    private val DATA_COLLECTION = "data"
    private val STATE_COLLECTION = "state"
    private val TEMPERATURE = "temperature"
    private val HUMIDITY = "humidity"
    private val TIMESTAMP = "timestamp"

    private val db = FirebaseFirestore.getInstance()

    fun addTemperature(address: String, temperature: TemperatureFB){
        db.collection(DATA_COLLECTION)
                .document(TEMPERATURE)
                .collection(address)
                .add(temperature)
        Log.i(TAG, "Device $address added a temperature with ${temperature.temperature} of value")
    }

    fun addHumidity(address: String, humidity: HumidityFB){
        db.collection(DATA_COLLECTION)
                .document(HUMIDITY)
                .collection(address)
                .add(humidity)
        Log.i(TAG, "Device $address added a humidity with ${humidity.humidity} % of value")

    }

    fun getTemperature(address: String, from: Long = 0): PublishSubject<TemperatureFB>{
        val observable: PublishSubject<TemperatureFB> = PublishSubject.create()
        db.collection(DATA_COLLECTION).document(TEMPERATURE).collection(address)
                .whereGreaterThanOrEqualTo(TIMESTAMP, from)
                .get()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        // Emits all received data
                        it.result.documents
                                .filterNotNull()
                                .forEach { observable.onNext(it.toTemperature()) }
                        // Emits onComplete event
                        observable.onComplete()
                    }
                    else
                        observable.onError(Throwable("Error getting temperatures"))
                }
        return observable
    }

    fun getHumidity(address: String, from: Long = 0): PublishSubject<HumidityFB>{
        val observable: PublishSubject<HumidityFB> = PublishSubject.create()
        db.collection(DATA_COLLECTION).document(HUMIDITY).collection(address)
                .whereGreaterThanOrEqualTo(TIMESTAMP, from)
                .get()
                .addOnCompleteListener { if (it.isSuccessful){
                    it.result.documents
                            .filterNotNull()
                            .forEach { observable.onNext( it.toHumidity()) }

                    observable.onComplete()
                }else
                    observable.onError(Throwable("Error getting humidity"))
                }

        return observable
    }



}