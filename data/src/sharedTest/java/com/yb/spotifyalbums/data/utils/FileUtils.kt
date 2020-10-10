package com.yb.spotifyalbums.data.utils

import android.content.Context
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class FileUtils {
    fun readJsonFile(context: Context, filename: String): String {
        var inputStream: InputStream? = null
        try {
            inputStream = javaClass.classLoader?.getResourceAsStream(filename)
            val reader = BufferedReader(InputStreamReader(inputStream))
            val sb = StringBuilder()
            var str: String? = ""
            while (str != null) {
                sb.append(str)
                str = reader.readLine()
            }
            return sb.toString()

        } finally {
            inputStream?.close()
        }
    }

}
