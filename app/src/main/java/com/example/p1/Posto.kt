package com.example.p1

import Empresa
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

class Posto(nome: String, CNPJ: String, caixa: Float, var capacidade: Float) : Empresa(nome, CNPJ, caixa), Parcelable {

    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readFloat(),
        parcel.readFloat()
    )

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeFloat(capacidade)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Posto> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): Posto {
            return Posto(parcel)
        }

        override fun newArray(size: Int): Array<Posto?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Posto: ${this.nome}, cnpj: ${this.CNPJ}, caixa: ${this.caixa}, capacidade: ${this.capacidade} \n"
    }


}