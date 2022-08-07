package ru.yandexschool.todolist.data.model

import com.google.gson.annotations.SerializedName

data class ResponseToDo(

	@field:SerializedName("list")
	val list: List<ListItem>,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("revision")
	val revision: Int? = null
)

data class ListItem(

	val id: String,
	val text: String,
	val importance: String? = null,
	val color: String? = null,
	val deadline: Long? = null,
	val done: Boolean = false,

	@field:SerializedName("created_at")

	val createdAt: Long,
	@field:SerializedName("changed_at")
	val changedAt: Long? = null,

	@field:SerializedName("last_updated_by")
	val lastUpdatedBy: Long? = null,



)
