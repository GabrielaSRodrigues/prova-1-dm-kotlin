package com.example.p1

import Empresa
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

class Cinema(nome: String, CNPJ: String, caixa: Float, var assentos: Int) : Empresa(nome, CNPJ, caixa), Parcelable {
    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readFloat(),
        parcel.readInt()
    )

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeInt(assentos)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Cinema> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): Cinema {
            return Cinema(parcel)
        }

        override fun newArray(size: Int): Array<Cinema?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Cinema: ${this.nome}, cnpj: ${this.CNPJ}, caixa: ${this.caixa}, assento: ${this.assentos} \n"
    }
}