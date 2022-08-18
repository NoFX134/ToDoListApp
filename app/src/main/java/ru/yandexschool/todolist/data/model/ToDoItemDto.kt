package ru.yandexschool.todolist.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

/**
 * Model to work with the adapter and UI
 */

@Entity(tableName = "table_todo_item")
data class ToDoItemDto(
    @PrimaryKey
    val id: UUID,
    val text: String?,
    val importance: ImportanceDto = ImportanceDto.BASIC,
    val deadline: Date?,
    val done: Boolean? = false,
    @ColumnInfo(name = "created_at")
    val createdAt: Date?,
    @ColumnInfo(name = "changed_at")
    val changedAt: Date?
): Serializable

enum class ImportanceDto {
    LOW,
    BASIC,
    IMPORTANT
}