package com.example.mytodo

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDateTime

class AddToDoActivity : AppCompatActivity() {
    var priorityIndex: Int = 0
    var projectIndex: Int = 0
    var isEdit: Boolean = false

    lateinit var todo: ToDo
    lateinit var editTitle: EditText
    lateinit var editDetails: EditText
    lateinit var spinPriority: Spinner
    lateinit var spinProject: Spinner
    lateinit var btnConfirm: Button
    lateinit var btnDelete: Button
    lateinit var datePicker: DatePicker
    lateinit var timePicker: TimePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_to_do)

        editTitle = findViewById(R.id.editTitle)
        editDetails = findViewById(R.id.editDetails)
        spinPriority = findViewById(R.id.spinPriority)
        spinProject = findViewById(R.id.spinProject)
        btnConfirm = findViewById(R.id.btnConfirm)
        btnDelete = findViewById(R.id.btnDelete)
        datePicker = findViewById(R.id.datePicker)
        timePicker = findViewById(R.id.timePicker)

        // 액션 바 삭제
        supportActionBar?.hide()

        val priorityArray = arrayOf("우선순위 낮음", "우선순위 보통", "우선순위 높음")

        val priorityAdapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_item, priorityArray)
        priorityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinPriority.adapter = priorityAdapter
        spinPriority.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, index: Int, id: Long
            ) {
                priorityIndex = index
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                priorityIndex = 0
            }
        }

        val projectArray = App.projectArray.map { it.name }
        val projectAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, projectArray)
        projectAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)
        spinProject.adapter = projectAdapter
        spinProject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?, view: View?, index: Int, id: Long
            ) {
                projectIndex = index
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                projectIndex = 0
            }
        }

        btnConfirm.setOnClickListener {
            if (editTitle.length() != 0) {
                Log.d("test", datePicker.dayOfMonth.toString())
                val dateTime: LocalDateTime =
                    LocalDateTime.now().withYear(datePicker.year).withMonth(datePicker.month + 1)
                        .withDayOfMonth(datePicker.dayOfMonth).withHour(timePicker.hour)
                        .withMinute(timePicker.minute).withSecond(0)
                if (isEdit) {
                    val findResult = App.todoArray.find { it.id == todo.id }
                    if (findResult != null) {
                        findResult.title = editTitle.text.toString()
                        findResult.contents = editDetails.text.toString()
                        findResult.targetDate = dateTime
                        findResult.priority = priorityIndex
                        findResult.projectId = App.projectArray[projectIndex].id
                    }
                } else {
                    // 테스트용 설정
                    App.todoArray = App.todoArray.plus(
                        ToDo(
                            App.uid++,
                            0,
                            editTitle.text.toString(),
                            editDetails.text.toString(),
                            false,
                            dateTime,
                            App.projectArray[projectIndex].id,
                            0,
                            priorityIndex
                        )
                    )
                }
                // 수정이나 추가를 마치고 본 액티비티 종료
                finish()
            } else {
                Toast.makeText(this, "제목을 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
        }

        btnDelete.setOnClickListener {
            if (isEdit) {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("TODO 삭제").setMessage("이 TODO를 삭제하시겠습니까?")
                    .setPositiveButton("확인") { _, _ ->
                        run {
                            App.todoArray = App.todoArray.filter { it.id != todo.id }.toTypedArray()
                            finish()
                        }
                    }.setNegativeButton("취소") { _, _ ->
                        run {}
                    }.show()
            } else {
                btnDelete.visibility = View.GONE
            }
        }

        if (App.projectArray.isEmpty()) {
            spinProject.visibility = View.GONE
        }

        isEdit = intent.getBooleanExtra("isEdit", false)
        if (isEdit) {
            btnDelete.visibility = View.VISIBLE
            todo = intent.getSerializableExtra("todo") as ToDo
            editTitle.setText(todo.title)
            editDetails.setText(todo.contents)
            datePicker.updateDate(
                todo.targetDate.year, todo.targetDate.monthValue - 1, todo.targetDate.dayOfMonth
            )
            timePicker.hour = todo.targetDate.hour
            timePicker.minute = todo.targetDate.minute
            if (todo.priority != null) {
                spinPriority.setSelection(todo.priority!!)
                priorityIndex = todo.priority!!
            } else {
                priorityIndex = 0
                spinPriority.setSelection(0)
            }
            if (todo.projectId != null) {
                if (todo.projectId == -1) {
                    projectIndex = 0
                    spinProject.setSelection(0)
                } else {
                    projectIndex = App.projectArray.indexOfFirst { it.id == todo.projectId }
                    spinProject.setSelection(projectIndex)
                }
            } else {
                projectIndex = 0
                spinProject.setSelection(0)
            }
        } else {
            btnDelete.visibility = View.GONE
            // 기본 우선순위 낮음
            spinPriority.setSelection(0)
            spinProject.setSelection(0)
        }
    }
}