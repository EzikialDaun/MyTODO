package com.example.mytodo

import android.app.Application

class App : Application() {

    init {
        INSTANCE = this
    }

    companion object {
        lateinit var INSTANCE: App
        var todoArray: Array<ToDo> = arrayOf()
        var projectArray: Array<Projcet> = arrayOf()

        // 테스트용 투두 uid
        var uid: Int = 0

        // 테스트용 프로젝트 uid
        var puid: Int = 0
    }
}