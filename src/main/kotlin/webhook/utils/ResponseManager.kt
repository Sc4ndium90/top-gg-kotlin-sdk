package webhook.utils

import com.sun.net.httpserver.HttpExchange
import org.json.JSONObject
import java.io.IOException
import java.io.OutputStream

class ResponseManager(private val exchange: HttpExchange) {

    private var responseCode = 200
    val responseStream: OutputStream
        get() = exchange.responseBody

    fun setResponseCode(code: Int): ResponseManager {
        responseCode = code
        return this
    }

    fun writeResponse(res: String) {
        try {
            exchange.responseHeaders["null-qt"] = "true"
            exchange.sendResponseHeaders(responseCode, res.length.toLong())
            responseStream.write(res.toByteArray())
            responseStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun flush(size: Long) {
        try {
            exchange.sendResponseHeaders(responseCode, size)
            responseStream.flush()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun writeResponse(`object`: JSONObject) {
        try {
            exchange.sendResponseHeaders(responseCode, `object`.toString().length.toLong())
            responseStream.write(`object`.toString().toByteArray())
            responseStream.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}