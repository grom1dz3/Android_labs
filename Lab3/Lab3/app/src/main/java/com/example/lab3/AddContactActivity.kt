package com.example.lab3
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.ActivityResultLauncher
import android.view.View
import android.widget.ImageView

class AddContactActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private var photoUri: Uri? = null
    private val takePhotoLauncher = registerForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
        bitmap?.let {
            imageView.setImageBitmap(it)
            photoUri = saveBitmapToUri(it)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_contact)

        imageView = findViewById(R.id.contactImage)

        findViewById<Button>(R.id.btnTakePhoto).setOnClickListener {
            takePhotoLauncher.launch(null)
        }

        findViewById<Button>(R.id.btnAddContact).setOnClickListener {
            val name = findViewById<EditText>(R.id.etName).text.toString()
            val email = findViewById<EditText>(R.id.etEmail).text.toString()
            val phone = findViewById<EditText>(R.id.etPhone).text.toString()

            if (name.isNotBlank() && email.isNotBlank() && phone.isNotBlank() && photoUri != null) {
                val contact = Contact(name, email, phone, photoUri!!)
                val resultIntent = Intent().putExtra("contact", contact)
                setResult(RESULT_OK, resultIntent)
                finish()
            } else {
                Toast.makeText(this, "Заповніть усі поля та зробіть фото", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveBitmapToUri(bitmap: Bitmap): Uri {
        val path = MediaStore.Images.Media.insertImage(contentResolver, bitmap, "Contact Photo", null)
        return Uri.parse(path)
    }
}
