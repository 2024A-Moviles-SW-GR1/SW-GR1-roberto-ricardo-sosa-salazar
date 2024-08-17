package com.example.a2024swerpnd2

import android.os.Parcel
import android.os.Parcelable

class Auto (val idAuto: Int,
            var modelo: String,
            var marca: String,
            var anio: Int,
            var precio: Double,
            ):Parcelable{
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readInt(),
        parcel.readDouble(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idAuto)
        parcel.writeString(modelo)
        parcel.writeString(marca)
        parcel.writeInt(anio)
        parcel.writeDouble(precio)

    }

    override fun describeContents(): Int {
        return 0
    }

    override fun toString(): String {
        return "$idAuto - $modelo - $marca"
    }

    companion object CREATOR : Parcelable.Creator<Auto> {
        override fun createFromParcel(parcel: Parcel): Auto {
            return Auto(parcel)
        }

        override fun newArray(size: Int): Array<Auto?> {
            return arrayOfNulls(size)
        }
    }


}