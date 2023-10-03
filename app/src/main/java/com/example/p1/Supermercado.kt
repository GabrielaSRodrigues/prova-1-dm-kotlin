package com.example.p1

import Empresa
import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi

class Supermercado(nome: String, CNPJ: String, caixa: Float, var ar: Boolean) : Empresa(nome, CNPJ, caixa), Parcelable {
    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readFloat(),
        parcel.readBoolean()
    )

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        super.writeToParcel(parcel, flags)
        parcel.writeBoolean(ar)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Supermercado> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): Supermercado {
            return Supermercado(parcel)
        }

        override fun newArray(size: Int): Array<Supermercado?> {
            return arrayOfNulls(size)
        }
    }

    override fun toString(): String {
        return "Supermercado: ${this.nome}, cnpj: ${this.CNPJ}, caixa: ${this.caixa}, ar: ${this.ar} \n"
    }
}
