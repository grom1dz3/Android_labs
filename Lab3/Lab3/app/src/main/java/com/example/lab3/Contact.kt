package com.example.lab3

import android.graphics.Bitmap
import java.io.Serializable


class Contact(val name: String, val email: String, val phone: String, val photo: Bitmap) :
    Serializable