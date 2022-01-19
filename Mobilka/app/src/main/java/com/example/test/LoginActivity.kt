package com.example.test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException

class LoginActivity : AppCompatActivity() {

    val client = OkHttpClient()
    val FORM = "application/x-www-form-urlencoded".toMediaTypeOrNull()

    fun httpPost(url: String, body: RequestBody, success: (response: Response) -> Unit, failure: () -> Unit) {
        val request = Request.Builder()
                .url(url)
                .post(body)
                .addHeader("Accept", "application/json")
                .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                failure()
            }

            override fun onResponse(call: Call, response: Response) {
                success(response)
            }

        })
    }

    fun login(login: String, password: String) {
        Toast.makeText(this, "Logowanie (" + login + ":" + password +")", Toast.LENGTH_SHORT).show()
        val url = "http://192.168.0.6:3000/login"
        val body = ("session[index]=" + login + "&session[password]=" + password).toRequestBody(FORM)

        httpPost(url, body,
        fun (response: Response) {
            Log.v("INFO", "Succeeded")
            val response_string = response.body?.string()
            val json = JSONObject(response_string)
            if(json.has("message")) {
                this.runOnUiThread {
                    Toast.makeText(this, json["message"] as String, Toast.LENGTH_SHORT).show()
                }
            }
            else if (json.has("token")) {
                this.runOnUiThread() {
                    Toast.makeText(this, json["token"] as String, Toast.LENGTH_SHORT).show()
                    val intent = android.content.Intent(this, HomeActivity::class.java)
                    startActivity(intent);
                }
            }
        },
        fun () {
            Log.v("INFO", "Failed")
        })
    }
    fun LoginToShort() {
        Toast.makeText(this, "Numer indeksu powinien składać się z 6 znaków", Toast.LENGTH_SHORT).show()
    }

    fun PasswordIsNull() {
        Toast.makeText(this, "Hasło nie może być puste", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        if(login_field.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        }

        login_button.setOnClickListener {
            val login = login_field.text.toString()
            val password = password_field.text.toString()

            if(login_field.length()!=6)
                LoginToShort()
            else if(password_field.length()<1)
                PasswordIsNull()
            else
                login(login, password)

        }
    }
}
