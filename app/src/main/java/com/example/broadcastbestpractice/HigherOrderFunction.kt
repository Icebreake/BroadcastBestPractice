package com.example.broadcastbestpractice

//第三个参数是一个接收两个整型参数并且返回值也是整型的函数类型参数
inline fun num1AndNum2(num1: Int, num2: Int, operation: (Int, Int) -> Int) : Int {
    //将num1和num2参数传给了第三个函数类型参数，并获取它的返回值
    val result = operation(num1, num2)
    return result
}

//给StringBuilder类定义了一个build扩展函数，这个扩展函数接收一个函数类型参数，并且返回值类型也是StringBuilder
fun StringBuilder.build(block: StringBuilder.() -> Unit) : StringBuilder {
    block()
    return this
}

//定义了一个高阶函数，用于在Lambda表达式中打印传入的字符串参数
inline fun printString(str: String, block: (String) -> Unit) {
    println("printString begin")
    block(str)
    println("printString end")
}

fun main() {
    println("main start")
    val str = ""
    printString(str) { s->
        println("lambda start")
        if (s.isEmpty()) return
        println(s)
        println("lambda end")
    }
    println("main end")
}

