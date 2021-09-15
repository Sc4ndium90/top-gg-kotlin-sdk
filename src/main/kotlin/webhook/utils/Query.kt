package webhook.utils

import java.util.*


class Query(private val query: String) {

    val queryMap: Map<String, String>
        get() {
            val result: MutableMap<String, String> = HashMap()
            Arrays.stream(query.split("&").toTypedArray()).forEach { s: String ->
                val split2 = s.split("=").toTypedArray()
                try {
                    result[split2[0]] = split2[1]
                } catch (ignored: Exception) {
                    result["none"] = "none"
                }
            }
            return result
        }

    override fun toString(): String {
        return query
    }
}