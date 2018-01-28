package com.example.shenshihao520.wigettestfactory.kotlin

/**
 * Created by shenshihao520 on 2018/1/4.
 */
fun main(args: Array<String>){
    var list = listOf(1,2,3)
    println(list)
    joinToString(list,separator = "",postfix = "",prefix =".")

}
fun <T> joinToString(collection: Collection<T>,
        separator: String = ",",
        prefix: String = "",
        postfix: String = ""
): String {
    var result = StringBuilder(postfix)
    for ((index, element) in collection.withIndex()){
        if(index > 0) {
            result.append(separator)
        }
        result.append(element)
    }
    result.append(postfix)
    return result.toString()
}