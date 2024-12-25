package ru.mrfix1033.customlistviewhomework.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import ru.mrfix1033.customlistviewhomework.R
import ru.mrfix1033.customlistviewhomework.data.Product

class ProductListAdapter(private val context: Context, private val list: MutableList<Product>) :
    ArrayAdapter<Product>(context, R.layout.list_item, list) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) view = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false)!!
        val textViewTitle = view.findViewById<TextView>(R.id.textViewTitle)
        val textViewPrice = view.findViewById<TextView>(R.id.textViewPrice)
        val imageView = view.findViewById<ImageView>(R.id.imageView)
        val person = list[position]
        textViewTitle.text = person.title
        textViewPrice.text = "${person.price}р."
        imageView.setImageURI(person.image)
        return view
    }
}