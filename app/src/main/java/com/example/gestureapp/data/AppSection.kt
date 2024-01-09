package com.example.gestureapp.data

import java.util.UUID
abstract class AppSection {

    companion object {
        var sectionId: Int = 0
        fun newSection(newSessionId: Int) {
            sectionId = newSessionId
        }
    }
}

