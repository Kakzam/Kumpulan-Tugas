package com.example.myapp

import com.android.volley.NetworkResponse
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.VolleyLog
import com.android.volley.toolbox.HttpHeaderParser
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.io.IOException

class MultipartRequest(
    method: Int,
    private val url: String,
    private val params: Map<String, String>?,
    private val files: Map<String, DataPart>?,
    private val listener: Response.Listener<NetworkResponse>,
    errorListener: Response.ErrorListener
) : Request<NetworkResponse>(method, url, errorListener) {

    private val boundary = "volley-multipart-boundary"
    private val twoHyphens = "--"
    private val lineEnd = "\r\n"

    override fun getHeaders(): MutableMap<String, String> {
        return HashMap() // Jika diperlukan, Anda dapat menambahkan header ke permintaan di sini
    }

    override fun getBodyContentType(): String {
        return "multipart/form-data;boundary=$boundary"
    }

    @Throws(IOException::class)
    override fun getBody(): ByteArray {
        val byteArrayOutputStream = ByteArrayOutputStream()
        val dataOutputStream = DataOutputStream(byteArrayOutputStream)

        params?.let { params ->
            for ((key, value) in params) {
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd)
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"$key\"$lineEnd")
                dataOutputStream.writeBytes("Content-Type: text/plain$lineEnd")
                dataOutputStream.writeBytes(lineEnd)
                dataOutputStream.writeBytes(value)
                dataOutputStream.writeBytes(lineEnd)
            }
        }

        files?.let { files ->
            for ((key, dataPart) in files) {
                dataOutputStream.writeBytes(twoHyphens + boundary + lineEnd)
                dataOutputStream.writeBytes("Content-Disposition: form-data; name=\"$key\"; filename=\"${dataPart.fileName}\"$lineEnd")
                if (dataPart.type != null && !dataPart.type.trim().isEmpty()) {
                    dataOutputStream.writeBytes("Content-Type: ${dataPart.type}$lineEnd")
                }
                dataOutputStream.writeBytes(lineEnd)
                dataOutputStream.write(dataPart.data)
                dataOutputStream.writeBytes(lineEnd)
            }
        }

        dataOutputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd)

        return byteArrayOutputStream.toByteArray()
    }

    override fun parseNetworkResponse(response: NetworkResponse): Response<NetworkResponse> {
        return try {
            Response.success(response, HttpHeaderParser.parseCacheHeaders(response))
        } catch (e: Exception) {
            Response.error(e as VolleyError?)
        }
    }

    override fun deliverResponse(response: NetworkResponse) {
        listener.onResponse(response)
    }

    override fun deliverError(error: VolleyError) {
        super.deliverError(error)
        VolleyLog.e("Error in multipart request: ${error.message}")
    }
}

data class DataPart(
    val fileName: String,
    val data: ByteArray,
    val type: String? = null
)
