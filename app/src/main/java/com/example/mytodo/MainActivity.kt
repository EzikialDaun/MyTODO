package com.example.mytodo

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    lateinit var tableToDo: TableLayout
    var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 액션 바 삭제
        supportActionBar?.hide()

        tableToDo = findViewById(R.id.tableToDo)

        // 추가 버튼 클릭 시, 추가 화면으로 이동
        val btnAdd: Button = findViewById(R.id.btnAdd)
        btnAdd.setOnClickListener {
            val intent = Intent(this, AddToDoActivity::class.java);
            intent.putExtra("isEdit", false)
            startActivity(intent)
        }

        val btnOption: Button = findViewById(R.id.btnOption)
        btnOption.setOnClickListener {
            startActivity(Intent(this, OptionActivity::class.java))
        }

        val btnProject: Button = findViewById(R.id.btnProject)
        btnProject.setOnClickListener {
            startActivity(Intent(this, ProjectActivity::class.java))
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

    override fun onResume() {
        super.onResume()
        render()
    }

    private fun render() {
        tableToDo.removeAllViews()

        val params: TableRow.LayoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT
        )
        params.gravity = Gravity.CENTER
        params.rightMargin = 10

        val typeFace: Typeface = resources.getFont(R.font.font_base)

        for (todo in App.todoArray) {
            val chkCompleted = CheckBox(applicationContext)
            chkCompleted.isChecked = todo.isCompleted

            val txtTitle = TextView(applicationContext)
            txtTitle.text = todo.title
            txtTitle.typeface = typeFace
            txtTitle.setTextSize(2, 12f)
            txtTitle.maxLines = 2
            txtTitle.width = 350
            txtTitle.ellipsize = TextUtils.TruncateAt.END

            val txtProject = TextView(applicationContext)
            if (todo.projectId == -1) {
                txtProject.text = ""
            } else {
                val target = App.projectArray.find { it.id == todo.projectId }
                if (target == null) {
                    txtProject.text = ""
                } else {
                    txtProject.text = target.name
                }

            }
            txtProject.typeface = typeFace
            txtProject.setTextSize(2, 12f)
            txtProject.maxLines = 1
            txtProject.width = 200
            txtProject.ellipsize = TextUtils.TruncateAt.END
            txtProject.gravity = Gravity.CENTER

            val txtDate = TextView(applicationContext)
            txtDate.text = todo.targetDate.format(DateTimeFormatter.ofPattern("M월 dd일 a h:mm"))
                .replace("AM", "오전").replace("PM", "오후")
            txtDate.typeface = typeFace
            txtDate.setTextSize(2, 12f)
            txtDate.maxLines = 1
            txtDate.width = 250
            txtDate.ellipsize = TextUtils.TruncateAt.END
            txtDate.gravity = Gravity.CENTER

            val tableRow = TableRow(applicationContext)
            tableRow.addView(chkCompleted, params)
            tableRow.addView(txtTitle, params)
            tableRow.addView(txtProject, params)
            tableRow.addView(txtDate, params)
            tableRow.tag = todo.id.toString()
            tableRow.setOnClickListener {
                val intent = Intent(this, AddToDoActivity::class.java);
                intent.putExtra("isEdit", true)
                intent.putExtra("todo", todo)
                startActivity(intent)
            }

            tableToDo.addView(tableRow)
        }
    }
}