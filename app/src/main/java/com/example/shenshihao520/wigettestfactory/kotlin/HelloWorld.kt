package com.example.shenshihao520.wigettestfactory.kotlin

/**
 * Created by shenshihao520 on 2017/10/30.
 */

interface Expr
class Num(val value: Int) : Expr
class Sum(val left: Expr,val right: Expr) : Expr

//fun eval(e: Expr) : Int{
//    if(e is Num){
//        val n = e as Num
//        return n.value
//    }
//    if(e is Sum){
//        return eval(e.right) + eval(e.left)
//    }
//    throw IllegalArgumentException("Unknow")
//    return 0
//}
//上面的if改when
fun eval (e: Expr): Int =
        when (e) {
            is Num ->
                    e.value
            is Sum ->
                    eval(e.right) + eval(e.left)
            else ->
                    throw IllegalArgumentException("Unknow")
        }


fun fizzBuzz(i: Int) = when{
    i % 15 == 0 -> "FizzBuzz"
    i % 5 == 0 -> "Fizz"
    else -> "Buzz"
}


fun main(args: Array<String>){
//    println(eval(Sum(Sum(Num(1),Num(2)),Num(4))))
    for(i in 1..100)
    {
        print(fizzBuzz(20))
    }
    for (i in 100 downTo 1 step 2){
        print(fizzBuzz(i))
    }
}