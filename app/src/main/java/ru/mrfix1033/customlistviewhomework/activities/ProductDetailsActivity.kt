package ru.mrfix1033.customlistviewhomework.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.mrfix1033.customlistviewhomework.R
import ru.mrfix1033.customlistviewhomework.data.Product
import ru.mrfix1033.customlistviewhomework.menuhandlers.OptionsMenu

class ProductDetailsActivity : OptionsMenu(R.menu.main_exit_back) {
    private var index: Int = -1
    private var imageViewUri: Uri? = null
    private lateinit var editTextTitle: EditText
    private lateinit var editTextPrice: EditText
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        idToAction =
            mapOf(Pair(R.id.itemExit) { finishAffinity() }, Pair(R.id.itemBack) { finish() })
        title = "О продукте"
        setSupportActionBar(findViewById(R.id.toolbar))
//        index = intent.getIntExtra("index", -1)
        val product: Product = intent.getParcelableExtra("product")!!
        imageView = findViewById(R.id.imageView)
        imageView.setImageURI(product.image)
        imageView.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            launchActivityPickPhoto.launch(intent)
        }
        editTextTitle = findViewById(R.id.editTextTitle)
        editTextTitle.setText(product.title)
        editTextPrice = findViewById(R.id.editTextPrice)
        editTextPrice.setText(product.price.toString())
    }

    override fun finish() {
//        val intent = Intent()
//        intent.putExtra("index", index)
        intent.putExtra(
            "product", Product(
                editTextTitle.text.toString(),
                editTextPrice.text.toString().toFloat(),
                imageViewUri
            )
        )
        setResult(RESULT_OK, intent)
        super.finish()
    }

    val launchActivityPickPhoto =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            imageViewUri = it.data!!.data
            imageView.setImageURI(imageViewUri)
        }
}