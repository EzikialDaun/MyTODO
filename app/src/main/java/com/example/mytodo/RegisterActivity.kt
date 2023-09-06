package com.example.mytodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.widget.*
import java.util.*

class RegisterActivity : AppCompatActivity() {
    lateinit var spinQuestionReg: Spinner
    lateinit var editEmailReg: EditText
    lateinit var editPWReg: EditText
    lateinit var editCheckPW: EditText
    lateinit var txtSecurity: TextView
    lateinit var chkVisible: CheckBox
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        spinQuestionReg = findViewById(R.id.spinQuestionReg)
        editEmailReg = findViewById(R.id.editEmailReg)
        editPWReg = findViewById(R.id.editPWReg)
        editCheckPW = findViewById(R.id.editCheckPW)
        txtSecurity = findViewById(R.id.txtSecurity)
        chkVisible = findViewById(R.id.chkVisible)

        // 액션 바 삭제
        supportActionBar?.hide()

        val questionArray = arrayOf(
            getString(R.string.str_question_0), getString(R.string.str_question_1), getString(
                R.string.str_question_2
            ), getString(R.string.str_question_3)
        )

        val arrayAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, questionArray)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinQuestionReg.adapter = arrayAdapter

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun afterTextChanged(str: Editable?) {
                val len = str.toString().length
                var hasUpper = false
                var hasSpec = false
                val pattern = "[!@#$%^&*]".toRegex()
                if (str != null) {
                    hasUpper = str.any { it.isUpperCase() }
                    if (pattern.containsMatchIn(str)) {
                        hasSpec = true
                    }
                }
                if (len >= 8 && hasUpper && hasSpec) {
                    txtSecurity.text = "보안 수준: 높음"
                } else if (len > 6 && (hasUpper || hasSpec)) {
                    txtSecurity.text = "보안 수준: 보통"
                } else {
                    txtSecurity.text = "보안 수준: 낮음"
                }
            }
        }

        chkVisible.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                editPWReg.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                editCheckPW.inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            } else if (!isChecked) {
                editPWReg.inputType =
                    InputType.TYPE_TEXT_VARIATION_PASSWORD + InputType.TYPE_CLASS_TEXT
                editCheckPW.inputType =
                    InputType.TYPE_TEXT_VARIATION_PASSWORD + InputType.TYPE_CLASS_TEXT
            }
        }

        val editPWReg: EditText = findViewById(R.id.editPWReg)
        editPWReg.addTextChangedListener(textWatcher)

        // 회원가입 버튼 클릭 시
        val btnSubmitReg: Button = findViewById(R.id.btnSubmitReg)
        btnSubmitReg.setOnClickListener {
            // 비밀번호 최소 조건: 소문자, 숫자 포함, 5글자 이상
            val regEx = Regex("^(?=.*[a-z])(?=.*[0-9])[A-Za-z0-9!@#$%^&*]{5,}$")
            if (editPWReg.text.matches(regEx)) {
                if (editPWReg.text.toString() == editCheckPW.text.toString()) {
                    val encoder: Base64.Encoder = Base64.getEncoder()
                    val encoded: String =
                        encoder.encodeToString(editPWReg.text.toString().toByteArray())
                    Log.d("test", encoded)
                    finish()
                } else {
                    Toast.makeText(this, "두 비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "비밀번호는 소문자와 숫자를 포함, 최소 5자 입니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}