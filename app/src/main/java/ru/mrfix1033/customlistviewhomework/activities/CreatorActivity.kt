package ru.mrfix1033.customlistviewhomework.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.mrfix1033.customlistviewhomework.R
import ru.mrfix1033.customlistviewhomework.data.Product
import ru.mrfix1033.customlistviewhomework.menuhandlers.OptionsMenu
import ru.mrfix1033.customlistviewhomework.ui.ProductListAdapter

class CreatorActivity : OptionsMenu(R.menu.main_exit) {

    private lateinit var toolbar: Toolbar
    private lateinit var imageView: ImageView
    private lateinit var editTextTitle: EditText
    private lateinit var editTextPrice: EditText
    private lateinit var buttonAdd: Button
    private lateinit var listView: ListView

    private val productList = mutableListOf<Product>()
    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_creator)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        toolbar = findViewById(R.id.toolbar)
        imageView = findViewById(R.id.imageView)
        editTextTitle = findViewById(R.id.editTextTitle)
        editTextPrice = findViewById(R.id.editTextPrice)
        buttonAdd = findViewById(R.id.buttonAdd)
        listView = findViewById(R.id.listView)

        title = "Магазин продуктов"
        setSupportActionBar(toolbar)

        imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            launchActivityPickPhoto.launch(intent)
        }

        buttonAdd.setOnClickListener {
            if (!createProduct()) return@setOnClickListener
            setOrUpdateListViewAdapter()
            clearEditFields()
        }

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val intent = Intent(this, ProductDetailsActivity::class.java)
                intent.putExtra("index", position)
                intent.putExtra("product", productList[position])
                launchActivityProductDetails.launch(intent)
            }
    }

    private fun createProduct(): Boolean {
        val productTitle = editTextTitle.text.toString()
        val productPrice = editTextPrice.text.toString().toFloatOrNull()
        if (productPrice == null) {
            Toast.makeText(this, getString(R.string.incorrectPriceField), Toast.LENGTH_SHORT).show()
            return false
        }
        val productImage = uri
        val product = Product(productTitle, productPrice, productImage)
        productList.add(product)
        return true
    }

    private fun setOrUpdateListViewAdapter() {
        val adapter = ProductListAdapter(this, productList)
        listView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    private fun clearEditFields() {
        editTextTitle.text.clear()
        editTextPrice.text.clear()
        imageView.setImageBitmap(null)
        uri = null
    }

    val launchActivityProductDetails =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            productList[it.data!!.getIntExtra("index", -1)] =
                it.data!!.getParcelableExtra("product")!!
            setOrUpdateListViewAdapter()
        }

    val launchActivityPickPhoto =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            uri = it.data!!.data
            imageView.setImageURI(uri)
        }
}