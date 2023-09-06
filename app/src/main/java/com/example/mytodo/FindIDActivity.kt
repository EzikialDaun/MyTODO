package com.example.mytodo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class FindIDActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_id)

        // 액션 바 삭제
        supportActionBar?.hide()

        val editCode: EditText = findViewById(R.id.editCode)
        // 인증번호 요청 버튼 클릭 시
        val btnReq: Button = findViewById(R.id.btnReq)
        btnReq.setOnClickListener {
            editCode.isEnabled = true
            Toast.makeText(this, "인증번호가 발송되었습니다.", Toast.LENGTH_SHORT).show()
        }
        // 제출 버튼 클릭 시
        val btnSubmitCode: Button = findViewById(R.id.btnSubmitCode)
        btnSubmitCode.setOnClickListener {
            finish()
        }
    }
}