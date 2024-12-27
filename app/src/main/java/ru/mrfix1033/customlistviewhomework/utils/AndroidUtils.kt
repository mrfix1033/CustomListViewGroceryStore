package ru.mrfix1033.customlistviewhomework.utils

import android.content.Intent
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity

fun launchActivityForResult(
    activity: AppCompatActivity,
    intent: Intent,
    func: (ActivityResult) -> Unit
) {
//    class Wrapper : Activity() {
//        override fun onCreate(savedInstanceState: Bundle?, persistableBundle: PersistableBundle?) {
//            super.onCreate(savedInstanceState)
//            activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult(), func)
//                .launch(intent)
//        }
//    }
//    Wrapper().onCreate(Bundle(), PersistableBundle())
    activity.registerForActivityResult(ActivityResultContracts.StartActivityForResult(), func).launch(intent)
}

fun pickPhoto(activity: AppCompatActivity, func: (ActivityResult) -> Unit) {
    val intent = Intent(Intent.ACTION_PICK)
    intent.type = "image/*"
    launchActivityForResult(activity, intent, func)
}