package com.example.a2024swerpnd2

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

class Parte(val idParte:Int,
            var nombre: String,
            var fechaFabricacion: String,
            var precio: Double,
            val idAuto: Int): Parcelable {
    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readDouble(),
        parcel.readInt()
    ) {
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idParte)
        parcel.writeString(nombre)
        parcel.writeString(fechaFabricacion)
        parcel.writeDouble(precio)
        parcel.writeInt(idAuto)
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "$idParte - $nombre - $fechaFabricacion - $precio"
    }

    companion object CREATOR : Parcelable.Creator<Parte> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): Parte {
            return Parte(parcel)
        }

        override fun newArray(size: Int): Array<Parte?> {
            return arrayOfNulls(size)
        }
    }

}