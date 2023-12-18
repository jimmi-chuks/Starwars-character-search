package com.dani

import android.content.Context
import android.util.Log
import com.dani.domain.di.DaggerDomainComponent
import com.dani.domain.di.DomainComponent

object SpaceXApplicationComponent {
    private lateinit var domainComponent: DomainComponent

    @Synchronized
    private fun getCoreComponentInstance(context: Context): DomainComponent {
        if(!::domainComponent.isInitialized){
            val applicationContext = context.applicationContext

            domainComponent = DaggerDomainComponent.builder()
                .applicationContext(applicationContext)
                .build()
        }
        return domainComponent
    }

    fun domainComponentFromContext(context: Context) : DomainComponent =
        getCoreComponentInstance(context)
}