package com.yashin_vadim.catfacts

import android.app.Application
import android.content.Context
import com.yashin_vadim.catfacts.di.DependencyInjection
import com.yashin_vadim.catfacts.di.DependencyInjectionIml

class App : Application() {

    val di: DependencyInjection by lazy {
        DependencyInjectionIml()
    }

}

val Context.di : DependencyInjection
get() {
    return (this.applicationContext as App).di
}