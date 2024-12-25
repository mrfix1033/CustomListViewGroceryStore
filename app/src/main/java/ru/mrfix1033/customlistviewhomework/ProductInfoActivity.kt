package ru.mrfix1033.customlistviewhomework

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import ru.mrfix1033.customlistviewhomework.data.Product

class ProductInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_product_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        title = "О продукте"
        setSupportActionBar(findViewById(R.id.toolbar))
        val product: Product = intent.getParcelableExtra("product")!!
        findViewById<ImageView>(R.id.imageView).setImageURI(product.image)
        findViewById<TextView>(R.id.textViewTitle).setText("Название: ${product.title}")
        findViewById<TextView>(R.id.textViewPrice).setText("Цена: ${product.price}р.")
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