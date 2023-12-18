package com.dani.data

import javax.inject.Scope


@Scope
@kotlin.annotation.Retention
annotation class ActivityScope

@Scope
@kotlin.annotation.Retention
annotation class FragmentScope

@Scope
@kotlin.annotation.Retention
annotation class DomainScope

@Scope
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class FeatureScope