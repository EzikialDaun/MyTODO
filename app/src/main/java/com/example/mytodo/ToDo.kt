package com.example.mytodo

import java.time.LocalDateTime

class ToDo(
    val id: Int,
    val userId: Int,
    var title: String,
    var contents: String?,
    val isCompleted: Boolean,
    var targetDate: LocalDateTime,
    var projectId: Int?,
    val categoryId: Int?,
    var priority: Int?
) : java.io.Serializable {
    private val _id: Int = id
    private val _userId: Int = userId
    private val _title: String = title
    private val _contents: String? = contents
    private val _isCompleted: Boolean = isCompleted
    private val _targetDate: LocalDateTime = targetDate
    private val _projectId: Int? = projectId
    private val _categoryId: Int? = categoryId
    private val _priority: Int? = priority
}