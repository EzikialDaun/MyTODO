package com.example.mytodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

class FindPWActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_find_pw)

        val spinQuestion: Spinner = findViewById(R.id.spinQuestion)

        // 액션 바 삭제
        supportActionBar?.hide()

        val questionArray = arrayOf(
            getString(R.string.str_question_0), getString(R.string.str_question_1), getString(
                R.string.str_question_2
            ), getString(R.string.str_question_3)
        )

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, questionArray)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinQuestion.adapter = arrayAdapter

        // 재설정 버튼 클릭 시
        val btnResetPW: Button = findViewById(R.id.btnResetPW)
        btnResetPW.setOnClickListener {
            finish()
        }
    }
}