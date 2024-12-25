package ru.mrfix1033.customlistviewhomework.data

import android.graphics.Bitmap
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable

class Product(val title: String, val price: Float, val image: Uri?) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readFloat(),
        parcel.readParcelable(Bitmap::class.java.classLoader)
    )

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(p0: Parcel, p1: Int) {
        p0.writeString(title)
        p0.writeFloat(price)
        p0.writeParcelable(image, p1)
    }

    companion object CREATOR : Parcelable.Creator<Product> {
        override fun createFromParcel(parcel: Parcel): Product {
            return Product(parcel)
        }

        override fun newArray(size: Int): Array<Product?> {
            return arrayOfNulls(size)
        }
    }
}