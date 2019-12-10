package com.nibiruexocompany.whattodo.model

import io.reactivex.subjects.PublishSubject
import java.util.*

class TodoItem(content: String, startDate: Calendar?, endDate: Calendar?) {
    val dataChanged = PublishSubject.create<Unit>()

    var content: String = content
        set(value) {
            field = value
            dataChanged.onNext(Unit)
        }

    var startDate: Calendar? = startDate
        set(value) {
            field = value
            dataChanged.onNext(Unit)
        }

    var endDate: Calendar? = endDate
        set(value) {
            field = value
            dataChanged.onNext(Unit)
        }

    var isDone: Boolean = false
        set(value) {
            field = value
            dataChanged.onNext(Unit)
        }
}