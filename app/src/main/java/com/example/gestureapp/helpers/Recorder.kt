package com.example.gestureapp.helpers

import android.os.Environment
import android.util.Log
import java.io.File
import java.io.FileOutputStream

abstract class Recorder {
    companion object{

        private const val parentDir = "GestureApp"
        private const val sensorsFile = "sensorsData.csv"
        private const val swipeFile = "swipeData.csv"
        private const val keyboardFile = "keyboardData.csv"
        private const val buttonFile = "buttonData.csv"

        private fun getParent(): File{

            val downloadsDir = Environment
                .getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            val parentDir = File(downloadsDir, parentDir)

            if(!parentDir.exists()){
                parentDir.mkdir()
            }
            return parentDir
        }
        private fun getFile(fileName: String): File{
            return File(getParent(), fileName)
        }
        fun sensorsData(data: String){
            append(getFile(sensorsFile), data)
        }
        fun swipeData(data: String){
            append(getFile(swipeFile), data)
        }
        fun keyboardData(data: String){
            append(getFile(keyboardFile), data)
        }
        fun buttonData(data: String){
            append(getFile(buttonFile), data)
        }
        private fun append(file: File, data: String){
            try {
                FileOutputStream(file, true)
                    .bufferedWriter().use {
                        it.appendLine(data)
                    }
            } catch (e: Exception) {
                Log.e("RECORDER", e.toString())
            }
        }
    }
}