package com.yashin_vadim.catfacts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import com.yashin_vadim.catfacts.model.Response
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.internal.operators.observable.ObservableElementAt
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = di.catFactRepository

        getFactButton.setOnClickListener {
            repository.getFact()
                .doOnSubscribe {
                    loadingIndicator.isVisible = true
                    errorView.isVisible = false
                }
                .doOnError {
                    loadingIndicator.isVisible = false
                    errorView.isVisible = true
                }
                .toObservable()
                .onErrorResumeNext (Observable.empty())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {  fact ->
                    loadingIndicator.isVisible = false
                    catFactView.text = fact
                }
        }
    }
}
