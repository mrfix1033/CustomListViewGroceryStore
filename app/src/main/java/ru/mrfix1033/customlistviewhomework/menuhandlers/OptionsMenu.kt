package ru.mrfix1033.customlistviewhomework.menuhandlers

import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity

open class OptionsMenu(private val menuId: Int) : AppCompatActivity() {
    lateinit var idToAction: Map<Int, () -> Any>
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(menuId, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        idToAction[item.itemId]?.invoke()
        return true
    }
}