package com.example.employeeapp

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import coil.load
import coil.transform.CircleCropTransformation
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import com.loopj.android.http.RequestParams
import cz.msebera.android.httpclient.Header
import cz.msebera.android.httpclient.entity.StringEntity
import org.json.JSONObject
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Base64

class Upload2 : AppCompatActivity() {

    // Constants for request codes
    private val CAMERA_REQUEST_CODE = 1
    private val GALLERY_REQUEST_CODE = 2

    // Variables for image file path
    private lateinit var imagepath: String
    private lateinit var imagefile: File

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload2)

        // Adjust window insets for edge-to-edge display
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val imageView: ImageView = findViewById(R.id.imageView)
        imageView.setOnClickListener {
            showImagePickerDialog()
        }
    }

    // Method to show a dialog to choose between Camera and Gallery
    private fun showImagePickerDialog() {
        val pictureDialog = AlertDialog.Builder(this)
        pictureDialog.setTitle("Select Action")
        val pictureDialogItems = arrayOf("Select photo from Gallery", "Capture photo using Camera")
        pictureDialog.setItems(pictureDialogItems) { _, which ->
            when (which) {
                0 -> openGallery()
                1 -> openCamera()
            }
        }
        pictureDialog.show()
    }

    // Method to open the gallery
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, GALLERY_REQUEST_CODE)
    }

    // Method to open the camera
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, CAMERA_REQUEST_CODE)
    }

    // Handling the result of image selection or capture
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                CAMERA_REQUEST_CODE -> {
                    val bitmap = data?.extras?.get("data") as Bitmap
                    handleCapturedImage(bitmap)
                }
                GALLERY_REQUEST_CODE -> {
                    val uri: Uri? = data?.data
                    val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                    handleSelectedImage(bitmap)
                }
            }
        }
    }

    // Method to handle captured image
    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleCapturedImage(bitmap: Bitmap) {
        val imageView: ImageView = findViewById(R.id.imageView)
        val btnImage: Button = findViewById(R.id.btnImage)

        // Load image into ImageView with Coil
        imageView.load(bitmap) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }

        // Set click listener to upload the image
        btnImage.setOnClickListener {
            postImageToApi(bitmap)
            imagepath = saveImageToFile(bitmap)
            Log.d("ImagePath", imagepath)
        }
    }

    // Method to handle selected image from gallery
    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleSelectedImage(bitmap: Bitmap) {
        val imageView: ImageView = findViewById(R.id.imageView)
        val btnImage: Button = findViewById(R.id.btnImage)

        // Load image into ImageView with Coil
        imageView.load(bitmap) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }

        // Set click listener to upload the image
        btnImage.setOnClickListener {
            postImageToApi(bitmap)
            imagepath = saveImageToFile(bitmap)
            Log.d("ImagePath", imagepath)
        }
    }

    // Method to save bitmap as a file and return the file path
    private fun saveImageToFile(bitmap: Bitmap): String {
        return try {
            val imageFile = createImageFile(bitmap)
            imageFile.absolutePath
        } catch (e: IOException) {
            e.printStackTrace()
            ""
        }
    }

    // Method to create a temporary image file
    @Throws(IOException::class)
    private fun createImageFile(bitmap: Bitmap): File {
        val cacheDir = applicationContext.cacheDir
        val imageFile = File.createTempFile("image", ".jpg", cacheDir)
        FileOutputStream(imageFile).use { outputStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
        }
        return imageFile
    }

    // Method to encode image file to Base64
    @RequiresApi(Build.VERSION_CODES.O)
    private fun encodeImageToBase64(file: File): String {
        val bytes = file.readBytes()
        return Base64.getEncoder().encodeToString(bytes)
    }

    // Method to upload image to the API
    @RequiresApi(Build.VERSION_CODES.O)
    private fun postImageToApi(bitmap: Bitmap) {
        val progressBar: ProgressBar = findViewById(R.id.progressbar)
        progressBar.visibility = View.VISIBLE
        val client = AsyncHttpClient(true, 80, 443)
        val api = "https://kimaniben.pythonanywhere.com/products"
        val body = JSONObject()

        try {
            val imageFile = createImageFile(bitmap)
            val encodedString = encodeImageToBase64(imageFile)
            val prodName: EditText = findViewById(R.id.name)
            val prodCost: EditText = findViewById(R.id.cost)
            val prodDesc: EditText = findViewById(R.id.desc)
            body.put("prod_name", prodName.text.toString())
            body.put("prod_cost", prodCost.text.toString())
            body.put("prod_desc", prodDesc.text.toString())
            body.put("image", encodedString)
            imagefile = imageFile
        } catch (e: IOException) {
            e.printStackTrace()
        }

        val conBody = StringEntity(body.toString())
        client.post(this, api, conBody, "application/json", object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?) {
                progressBar.visibility = View.GONE
                Toast.makeText(applicationContext, "Product uploaded successfully", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray?, error: Throwable?) {
                progressBar.visibility = View.GONE
                Toast.makeText(applicationContext, "Upload failed: $error", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
