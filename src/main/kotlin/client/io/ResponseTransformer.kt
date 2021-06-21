package client.io

import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class ResponseTransformer {

    inline fun <reified T> transform(inputStream: InputStream): T {
        val `in` = BufferedReader(
            InputStreamReader(inputStream))
        var inputLine: String?
        val content = StringBuffer()
        while (`in`.readLine().also { inputLine = it } != null) {
            content.append(inputLine)
        }
        `in`.close()
        return Json.decodeFromString(content.toString())
    }

}
