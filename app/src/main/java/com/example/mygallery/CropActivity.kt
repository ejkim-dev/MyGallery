package com.example.mygallery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.yalantis.ucrop.UCrop
import java.io.File

class CropActivity : AppCompatActivity() {
    private lateinit var imageView: ImageView
    private lateinit var loadImageButton: Button
    private lateinit var indicateButton: Button
    private lateinit var sendButton: Button
    private lateinit var imageUri: Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crop)
        initView()
        initListener()
    }

    private fun initListener() {
        loadImageButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = MediaStore.Images.Media.CONTENT_TYPE
            intent.type = "image/*"
            startActivityForResult(
                intent,
                PICK_IMAGE
            )
        }
    }

    private fun initView() {
        imageView = findViewById(R.id.gallery_iv_image)
        loadImageButton = findViewById(R.id.gallery_btn_load)
        indicateButton = findViewById(R.id.gallery_btn_indicate)
        sendButton = findViewById(R.id.gallery_btn_send)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == PICK_IMAGE) {
                val sourceUri = data!!.data
                if (sourceUri != null) {
                    val destinationUri = Uri.fromFile(File(cacheDir, "cropped"))
                    openCropActivity(sourceUri, destinationUri)
                } else {
                    Toast.makeText(this, getString(R.string.get_img_error_msg), Toast.LENGTH_SHORT).show()
                }
            } else if (requestCode == UCrop.REQUEST_CROP) {
                val resultUri = UCrop.getOutput(data!!)
                if (resultUri != null) {
                    Log.d("AAA","AAa")
                    //?????????
                    imageView.setImageDrawable(null)
                    //??????????????? ??????
                    imageUri = resultUri
                    imageView.setImageURI(imageUri)
//                    Glide.with(this).load(imageUri).fitCenter().into(imageView)
                } else {
                    Toast.makeText(this, getString(R.string.get_img_error_msg), Toast.LENGTH_SHORT).show()
                }
            }
        } else if (resultCode == UCrop.RESULT_ERROR) {
            Toast.makeText(this, getString(R.string.get_img_error_msg), Toast.LENGTH_SHORT).show()
        }
    }

    private fun openCropActivity(
        sourceUri: Uri,
        destinationUri: Uri
    ) {
        UCrop.of(sourceUri, destinationUri)
            .start(this)
    }

    companion object {
        const val PICK_IMAGE = 1
    }
}