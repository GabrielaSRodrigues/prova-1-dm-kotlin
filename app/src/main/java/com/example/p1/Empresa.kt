import android.os.Parcel
import android.os.Parcelable

open class Empresa(var nome: String, var CNPJ: String, var caixa: Float) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readFloat()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nome)
        parcel.writeString(CNPJ)
        parcel.writeFloat(caixa)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Empresa> {
        override fun createFromParcel(parcel: Parcel): Empresa {
            return Empresa(parcel)
        }

        override fun newArray(size: Int): Array<Empresa?> {
            return arrayOfNulls(size)
        }
    }
}