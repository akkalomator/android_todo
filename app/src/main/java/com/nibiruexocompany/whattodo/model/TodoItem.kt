package com.nibiruexocompany.whattodo.model

import io.reactivex.subjects.PublishSubject
import java.util.*

class TodoItem(content: String, date: Date, isDone: Boolean) {
    val dataChanged = PublishSubject.create<Unit>()

    var content: String = content
        set(value) {
            field = value
            dataChanged.onNext(Unit)
        }

    var date: Date = date
        set(value) {
            field = value
            dataChanged.onNext(Unit)
        }

    var isDone: Boolean = isDone
    set(value) {
        field = value
        dataChanged.onNext(Unit)
    }
}