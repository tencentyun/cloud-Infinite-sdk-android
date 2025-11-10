package com.tencent.qcloud.ci.hdr.sample

import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var hdrCheckbox: CheckBox
    private lateinit var loadImageButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // 初始化视图组件
        imageView = findViewById(R.id.image_view)
        hdrCheckbox = findViewById(R.id.hdr_checkbox)
        loadImageButton = findViewById(R.id.load_image_button)

        // 设置按钮点击事件
        loadImageButton.setOnClickListener {
            loadImage()
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun loadImage() {
        val isHdrEnabled = hdrCheckbox.isChecked
        val inputStream = assets.open("湖面HDR.jpg")
        val bitmap = BitmapFactory.decodeStream(inputStream)
        imageView.setImageBitmap(bitmap)
        inputStream.close()

        // Set color mode of the activity to the correct color mode
        // https://developer.android.com/media/grow/ultra-hdr/display?hl=zh-cn#kotlin
        window.colorMode =
            if (isHdrEnabled && bitmap.hasGainmap()) ActivityInfo.COLOR_MODE_HDR else ActivityInfo.COLOR_MODE_DEFAULT
    }
}