@file:OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)

package com.dani.starwars.di

import com.dani.data.ActivityScope
import com.dani.domain.di.DomainComponent
import com.dani.starwars.MainActivity
import dagger.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@Component(
    dependencies = [DomainComponent::class]
)
@ActivityScope
interface HomeComponent {

    @ExperimentalCoroutinesApi
    fun inject(activity: MainActivity)

    @Component.Builder
    interface Builder {
        fun build(): HomeComponent

        @BindsInstance
        fun bindActivity(activity: MainActivity): Builder

        fun domainComponent(component: DomainComponent): Builder
    }
}

