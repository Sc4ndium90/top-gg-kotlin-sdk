package webhook.utils

import com.sun.net.httpserver.HttpExchange
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.StandardCharsets

class RequestManager(private val exchange: HttpExchange) {
    val string: String
    val requestStream: InputStream
        get() {
            val data = ArrayList<String>()
            return exchange.requestBody
        }

    fun asJson(): JSONObject {
        return JSONObject(string)
    }

    init {
        val sb = StringBuilder()
        val isr = InputStreamReader(requestStream, StandardCharsets.UTF_8)
        val br = BufferedReader(isr)
        br.lines().forEach { str: String? -> sb.append(str) }
        string = sb.toString()
    }
}

