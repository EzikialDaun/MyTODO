package com.example.mytodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ReviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)

        // 액션 바 삭제
        supportActionBar?.hide()
    }
}