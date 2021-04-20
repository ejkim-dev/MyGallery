package com.example.mygallery

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    val REQUEST_GALLERY_TAKE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun onClick(view: View) {
        when(view.id){
            R.id.button -> loadImage()
        }
    }

    private fun loadImage(){
        // 버튼 클릭 시 이미지 불러오기(갤러리 접근)
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_GALLERY_TAKE)

        // 구글 갤러리 접근
//        intent.type = "image/*"
//        intent.action = Intent.ACTION_GET_CONTENT
//        startActivityForResult(intent, PICK_IMAGE)

        // 일반 갤러리 접근
/*        intent.type = android.provider.MediaStore.Images.Media.CONTENT_TYPE
        startActivityForResult(intent, PICK_IMAGE)*/
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val imageView = findViewById<ImageView>(R.id.image)

        // Check which request we're responding to
        /*if (requestCode == PICK_IMAGE) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                try {
                    // 선택한 이미지에서 비트맵 생성
                    val inputStream =
                        contentResolver.openInputStream(data?.data!!)
                    val img = BitmapFactory.decodeStream(inputStream);
                    inputStream?.close();
                    // 이미지뷰에 세팅
                    imageView.setImageBitmap(img);

                } catch (e: Exception) {
                    e.printStackTrace();
                }
            }
        }*/

        when(requestCode){
            1 -> {
                if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_GALLERY_TAKE){
                    imageView.setImageURI(data?.data)
                }
            }
        }
    }
}