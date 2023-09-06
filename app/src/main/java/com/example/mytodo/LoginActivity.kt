package com.example.mytodo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast

class LoginActivity : AppCompatActivity() {
    var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 액션 바 삭제
        supportActionBar?.hide()

        // 로그인 버튼 클릭 시, 정보가 일치하면 메인 화면으로 이동
        val btnSubmit: Button = findViewById(R.id.btnSubmit)
        btnSubmit.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        // 아이디 찾기 버튼 클릭 시, 아이디 찾기 화면으로 이동
        val btnFindID: TextView = findViewById(R.id.btnFindID)
        btnFindID.setOnClickListener {
            startActivity(Intent(this, FindIDActivity::class.java))
        }

        // 비밀번호 찾기 버튼 클릭 시, 비밀번호 찾기 화면으로 이동
        val btnFindPW: TextView = findViewById(R.id.btnFindPW)
        btnFindPW.setOnClickListener {
            startActivity(Intent(this, FindPWActivity::class.java))
        }

        // 회원가입 버튼 클릭 시, 회원가입 화면으로 이동
        val btnReg: TextView = findViewById(R.id.btnReg)
        btnReg.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }

    override fun onBackPressed() {
        val timeout = 2000
        if (System.currentTimeMillis() > backPressedTime + timeout) {
            backPressedTime = System.currentTimeMillis()
            Toast.makeText(this, getString(R.string.str_back_pressed), Toast.LENGTH_SHORT).show()
        } else if (System.currentTimeMillis() <= backPressedTime + timeout) {
            finish()
        }
    }
}