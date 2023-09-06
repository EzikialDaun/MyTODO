package com.example.mytodo

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.*
import androidx.core.view.marginRight
import androidx.core.view.setPadding

class ProjectActivity : AppCompatActivity() {

    lateinit var layoutProjcet: LinearLayout
    lateinit var btnAddProject: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_project)

        // 액션 바 삭제
        supportActionBar?.hide()

        layoutProjcet = findViewById(R.id.layoutProject)
        btnAddProject = findViewById(R.id.btnAddProject)

        btnAddProject.setOnClickListener {
            val et = EditText(this)
            et.gravity = Gravity.CENTER
            val builder = AlertDialog.Builder(this).setTitle("새 프로젝트 이름").setView(et)
                .setPositiveButton("확인") { _, _ ->
                    if (et.text.toString() != "") {
                        App.projectArray =
                            App.projectArray.plus(Projcet(++App.puid, 0, et.text.toString()))
                        Toast.makeText(this, "프로젝트를 추가했습니다", Toast.LENGTH_SHORT).show()
                        render()
                    } else {
                        Toast.makeText(this, "프로젝트 이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                    }
                }
            builder.show()
        }
    }

    override fun onResume() {
        super.onResume()
        render()
    }

    private fun render() {
        layoutProjcet.removeAllViews()

        for (project in App.projectArray) {
            val layoutRow = LinearLayout(applicationContext)
            layoutRow.setPadding(5)
            // layout.orientation = LinearLayout.HORIZONTAL
            val txtName = TextView(applicationContext)
            txtName.text = project.name
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.rightMargin = 10
            params.weight = 1.0f
            val typeFace: Typeface = resources.getFont(R.font.font_base)
            txtName.typeface = typeFace
            txtName.layoutParams = params
            val btnDelete = Button(applicationContext)
            btnDelete.text = "X"
            btnDelete.setOnClickListener {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("프로젝트 삭제").setMessage("이 프로젝트를 삭제하시겠습니까?")
                    .setPositiveButton("확인") { _, _ ->
                        run {
                            // 프로젝트 삭제
                            App.projectArray =
                                App.projectArray.filter { it.id != project.id }.toTypedArray()
                            // 기존 투두들의 프로젝트 관계 삭제
                            val target =
                                App.todoArray.filter { it.projectId == project.id }.toTypedArray()
                            for (todo in target) {
                                todo.projectId = -1
                            }
                            render()
                        }
                    }.setNegativeButton("취소") { _, _ ->
                        run {}
                    }.show()
            }
            layoutRow.addView(txtName)
            layoutRow.addView(btnDelete)
            layoutProjcet.addView(layoutRow)
        }
    }
}