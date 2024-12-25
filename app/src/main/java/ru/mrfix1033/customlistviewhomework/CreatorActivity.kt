package ru.mrfix1033.customlistviewhomework

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.AdapterView
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.mrfix1033.customlistviewhomework.data.Product
import ru.mrfix1033.customlistviewhomework.enumerations.RequestCode
import ru.mrfix1033.customlistviewhomework.ui.ProductListAdapter

class CreatorActivity : AppCompatActivity() {

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
            val selectImageIntent = Intent(Intent.ACTION_PICK)
            selectImageIntent.type = "image/*"
            startActivityForResult(selectImageIntent, RequestCode.PICK_PHOTO)
        }

        buttonAdd.setOnClickListener {
            if (!createProduct()) return@setOnClickListener
            setOrUpdateListViewAdapter()
            clearEditFields()
        }

        listView.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->
                val intent = Intent(this, ProductInfoActivity::class.java)
                intent.putExtra("product", productList[position])
                startActivity(intent)
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            RequestCode.PICK_PHOTO -> {
                if (resultCode == RESULT_OK) {
                    uri = data!!.data
                    imageView.setImageURI(uri)
                }
            }
        }
    }

    fun createProduct(): Boolean {
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

    fun setOrUpdateListViewAdapter() {
        val adapter = ProductListAdapter(this, productList)
        listView.adapter = adapter
        adapter.notifyDataSetChanged()
    }

    fun clearEditFields() {
        editTextTitle.text.clear()
        editTextPrice.text.clear()
        imageView.setImageBitmap(null)
        uri = null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.itemExit -> finishAffinity()
        }
        return true
    }
}