package com.dani.data

fun<T> List<T>.format(): String{
    return if(size == 0) {
        "NA"
    } else if(size == 1){
        get(0).toString()
    } else{
        dropLast(1).fold(""){ a: String, b: T -> "$a, ${b.toString()}" }
            .plus(" and ${last()}")
            .removePrefix(", ")
    }
}

fun  Int?.nullableFun(): String{
    return this?.toString() ?: "null"
}
fun<T> List<T>?.formatNullable(): String{
    if(this == null) return "NA"
    return if(size == 0) {
        ""
    } else if(size == 1){
        get(0).toString()
    } else{
        dropLast(1).fold(""){ a: String, b: T -> "$a, ${b.toString()}" }
            .plus(" and ${last()}")
            .removePrefix(", ")
    }
}

fun main() {
    val nudd: List<String?> = listOf("23", null)
    val allNull: List<Int> = listOfNotNull(2, 3, 4, null)
    val hg: List<Int?> = listOfNotNull(null, null)
    val kl: List<Int>? = null

    println(nudd.formatNullable())
    println(allNull.formatNullable())
    println(hg.formatNullable())
    println(kl.formatNullable())


    println(emptyList<String>().format())
    println(listOf("1").format())
    println(listOf("1","2").format())
    println(listOf("1","2","3").format())
    println(listOf("1","2","3","4").format())


    println(emptyList<Person>().format())
    println(listOf(Person("James", 24)).format())
    println(listOf(Person("James", 24),Person("Paul", 20)).format())
    println(listOf(Person("James", 24),Person("Paul", 20), Person("Jane", 25)).format())
    println(listOf(Person("James", 24),Person("Paul", 20), Person("Jane", 25), Person("Pee", 35)).format())
}
//
data class Person(val name: String, val age: Int)