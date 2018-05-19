package com.gocantar.connectingthings.data.datasource

import android.util.Log
import com.gocantar.connectingthings.data.extensions.toHumidity
import com.gocantar.connectingthings.data.extensions.toTemperature
import com.gocantar.connectingthings.data.model.HumidityFB
import com.gocantar.connectingthings.data.model.TemperatureFB
import com.google.firebase.database.*
import io.reactivex.Observable
import io.reactivex.subjects.PublishSubject
import java.util.*

/**
 * Created by gocantar on 17/1/18.
 */
class FirebaseDataSource {

    private val TAG = javaClass.simpleName

    private val TEMPERATURE = "temperature"
    private val HUMIDITY = "humidity"
    private val TIMESTAMP = "timestamp"

    private val db = FirebaseDatabase.getInstance()

    fun addTemperature(address: String, temperature: TemperatureFB){
        db.getReference(address).child(TEMPERATURE).push().setValue(temperature)
    }

    fun addHumidity(address: String, humidity: HumidityFB){
        db.getReference(address).child(HUMIDITY).push().setValue(humidity)
    }

    fun getTemperature(address: String, from: Long = 0): Observable<TemperatureFB>{
        val observable: PublishSubject<TemperatureFB> = PublishSubject.create()
        val query = db.getReference(address)
                .child(TEMPERATURE)
                .orderByChild(TIMESTAMP)
                .startAt(from.toDouble())
        query.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onCancelled(error: DatabaseError?) {
                observable.onError(Throwable())
            }
            override fun onDataChange(data: DataSnapshot?) {
                data?.toTemperature()?.forEach {
                    observable.onNext(it)
                }
                observable.onComplete()
            }
        })
        return observable
    }

    fun getHumidity(address: String, from: Long = 0): PublishSubject<HumidityFB>{
        val observable: PublishSubject<HumidityFB> = PublishSubject.create()
        val query = db.getReference(address)
                .child(HUMIDITY)
                .orderByChild(TIMESTAMP)
                .startAt(from.toDouble())
        query.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onCancelled(p0: DatabaseError?) {
                observable.onError(Throwable())
            }

            override fun onDataChange(data: DataSnapshot?) {
                data?.toHumidity()?.forEach {
                    observable.onNext(it)
                }
                observable.onComplete()
            }
        })
        return observable
    }

}