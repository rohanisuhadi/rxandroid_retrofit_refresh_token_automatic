package com.example.kamtiz.rxandroid2andjava

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.kamtiz.rxandroid2andjava.local.DataManagerAuth
import com.example.kamtiz.rxandroid2andjava.local.DataManagerCustomer
import com.example.kamtiz.rxandroid2andjava.local.PreferencesHelper
import com.example.kamtiz.rxandroid2andjava.model.AccessToken
import com.example.kamtiz.rxandroid2andjava.model.AudioBook
import com.example.kamtiz.rxandroid2andjava.model.User
import com.example.kamtiz.rxandroid2andjava.network.APIAuthentication
import com.example.kamtiz.rxandroid2andjava.utils.network.TokenAccount
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = "MainActivity"
    }

    private var compositeDisposable: CompositeDisposable? = null

    private val params = HashMap<String, String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getStarredRepos()

        val refresh = findViewById<Button>(R.id.refresh)
        val book = findViewById<Button>(R.id.book)

        compositeDisposable = CompositeDisposable()

        refresh.setOnClickListener {
            refreshToken()
        }

        book.setOnClickListener {
            getBookApi()
        }


    }

    private fun getBookApi() {

        val params = HashMap<String, String>()
        params.put("offset", 0.toString())
        params.put("limit", 20.toString())
        params.put("purchased", "true")
        val apiAuthentication = APIAuthentication(this@MainActivity)
        val tokenAccount = TokenAccount(this@MainActivity)

        val dataManagerCustomer = DataManagerCustomer(apiAuthentication, tokenAccount,
            DataManagerAuth(
                apiAuthentication,
                tokenAccount
            )
        )

        compositeDisposable?.add(dataManagerCustomer.fetchCustomers(params).subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<AudioBook>>() {

                override fun onStart() {

                }

                override fun onNext(audioBooks: List<AudioBook>) {
                    //Handle logic
                    Log.i(TAG, "dataManagerCustomer")
                }

                override fun onError(e: Throwable) {
                    /*if (e is HttpException) {
                        if(e.code() == 401){
                            refreshToken()
                        }
                    }*/
                }

                override fun onComplete() {

                }
            }))

        /*
        val apiAccess = APIAuthentication(TokenAccount(this@MainActivity).getTokenAccount())
        compositeDisposable?.add(apiAccess
            .getAudioBook(params)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<List<AudioBook>>() {

                override fun onStart() {

                }

                override fun onNext(audioBooks: List<AudioBook>) {
                    //Handle logic
                    Log.i(TAG, "SUCCESS, HTTP RESPONSE: ")
                }

                override fun onError(e: Throwable) {
                    if (e is HttpException) {
                        if(e.code() == 401){
                            refreshToken()
                        }
                    }
                }

                override fun onComplete() {

                }
            }))*/
    }

    private fun refreshToken() {

        val apiAccess = APIAuthentication(this@MainActivity)

        apiAccess
            .refreshToken()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<AccessToken>() {

                override fun onStart() {

                }

                override fun onNext(accessToken: AccessToken) {
                    //Handle logic
                    Log.i(TAG, "TOKEN JWT " + accessToken.accessToken.toString())
                    TokenAccount(this@MainActivity).setTokenAccount(accessToken.accessToken.toString())
                    PreferencesHelper(this@MainActivity).putSignInUser(accessToken)
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }
            })
        Log.i(TAG, "disposable" + compositeDisposable.toString())
    }

    override fun onDestroy() {
        if (compositeDisposable != null && !compositeDisposable!!.isDisposed()) {
            compositeDisposable!!.dispose()
        }

        super.onDestroy()
    }

    private fun getStarredRepos() {

        val apiAccess = APIAuthentication(this@MainActivity)
        val params = HashMap<String, String>()

        val user = User()
        user.email = "rohanisuhadi52@gmail.com"
        user.password = "audiobuku1234~!@#"
        apiAccess
            .loginEmail(user)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<User>() {

                override fun onStart() {

                }

                override fun onNext(user: User) {
                    //Handle logic
                    Log.i(TAG, "SUCCESS, HTTP RESPONSE: " + user.token.toString())

                    TokenAccount(this@MainActivity).setTokenAccount(user.token!!)
                }

                override fun onError(e: Throwable) {

                    if (e is HttpException) {
                        Log.i(TAG, "HttpException, HTTP RESPONSE: " + e.response())
                        Log.i(TAG, "HttpException, HTTP BODY: " + e.response())
                        val body = e.response().errorBody()?.string()
                        Log.i(TAG, "HttpException, HTTP BODY: " + body)

                        val myjson = JSONObject(body)
                        val the_json_array = myjson.get("message")
                        Log.e("TAG", "response 33: " + the_json_array)

                        val `object` = JSONObject(myjson.get("message").toString())
                        val Jarray = `object`.getJSONArray("password")

                        for (i in 0 until Jarray.length()) {
                            Log.i(TAG, "HttpException, HTTP BODY: " + Jarray.getString(i))
                        }
                    }

                    if (e is IOException) {
                        Log.i(TAG, "IOException, HTTP Error: " + e.message)
                    }
                    //Handle error
                    Log.i(TAG, "RxJava2, HTTP Error: " + e.message)
                    showMessage(e.message.toString())

                }

                override fun onComplete() {

                }
            })
    }

    fun showMessage(message:String){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }
}
