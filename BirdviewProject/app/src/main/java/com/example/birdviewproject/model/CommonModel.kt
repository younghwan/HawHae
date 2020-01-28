package com.example.birdviewproject.model

interface BaseModel {
    fun getViewType(): ViewType
}

enum class SkinType(var value: String, var label: String) {
    ALL("", "모든 피부 타입"), OILY("oily", "지성 타입"),
    DRY("dry", "건성 타입"), SENSITIVE("sensitive", "민감성 타입")
}


data class Dummy(
    var index: Int
) : BaseModel {
    override fun getViewType(): ViewType {
        return ViewType.DUMMY
    }

}