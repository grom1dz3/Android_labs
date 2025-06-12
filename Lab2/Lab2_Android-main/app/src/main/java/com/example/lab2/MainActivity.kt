package com.example.lab2

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var photoUri: Uri
    private lateinit var photoFile: File

    private val CAMERA_REQUEST_CODE = 100
    private val PERMISSION_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.imageView)
        val btnTakePhoto = findViewById<Button>(R.id.btnTakePhoto)
        val btnSendEmail = findViewById<Button>(R.id.btnSendEmail)

        // Разрешения
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA),
                PERMISSION_CODE
            )
        }

        // Кнопка "Сделать селфі"
        btnTakePhoto.setOnClickListener {
            takePhoto()
        }

        // Кнопка "Відправити селфі"
        btnSendEmail.setOnClickListener {
            sendEmailWithPhoto()
        }
    }

    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        photoFile = File.createTempFile("selfie_", ".jpg", cacheDir)
        photoUri = FileProvider.getUriForFile(
            this,
            "${applicationContext.packageName}.fileprovider",
            photoFile
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            imageView.setImageURI(photoUri)
        }
    }

    private fun sendEmailWithPhoto() {
        val emailIntent = Intent(Intent.ACTION_SEND).apply {
            type = "application/image"
            putExtra(Intent.EXTRA_EMAIL, arrayOf("hodovychenko@op.edu.ua"))
            putExtra(Intent.EXTRA_SUBJECT, "ANDROID Мамаєнко Дар’я")
            putExtra(Intent.EXTRA_TEXT, "Фото у вкладенні. Репозиторій: https://github.com/dashamamaienko/Lab2_Android")
            putExtra(Intent.EXTRA_STREAM, photoUri)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }
        startActivity(Intent.createChooser(emailIntent, "Надіслати селфі через:"))
    }
}
