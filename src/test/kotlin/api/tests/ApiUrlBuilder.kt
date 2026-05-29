package api.tests

object ApiUrlBuilder {

    fun endpoint(baseUrl: String, path: String): String {
        val normalizedBase = baseUrl.trimEnd('/')
        val normalizedPath = if (path.startsWith("/")) path else "/$path"
        return normalizedBase + normalizedPath
    }

    fun withPathParam(path: String, paramName: String, value: String): String =
        path.replace("{$paramName}", value)
}
