package com.example.threeversionasproject.kotlin

/**
 * Created by Ocean on 2020/7/21.
 */
class ktTest4 {

    /**
     * 无参数的情况
     */
    fun noParams() {
        println("无参数")
    }

    val noParamsTolambda = { println("无参数") }

    fun excute() {
        noParams()
        println(noParamsTolambda)
    }

    /**
     * 有参数的情况
     */

    fun withParams(a: Int, b: Int): Int {
        return a + b
    }

    val withParmasToLambda: (Int, Int) -> Int = { a, b -> a + b }
    val withParmasToLambda2 = { a: Int, b: Int -> a + b }


    /**
     *lambda表达式作为函数中的参数的时候
     */
    //eg：1
    fun test(a: Int, b: (Int, Int) -> Int): Int {
        //invoke()函数：表示为通过函数变量调用自身，因为上面例子中的变量b是一个匿名函数。
        return a + b.invoke(3, 5)
    }

    // 调用
    fun excute2() {
        val a = test(10, { num1, num2 -> num1 + num2 })
        println(a)// 结果为：18

    }

    //eg：2
    fun test1(num1: Int, bool: (Int) -> Boolean): Int {
        return if (bool(num1)) num1 else 0
    }

    fun a() {
        println(test1(10, { it > 5 }))
        println(test1(4, { it > 5 }))
    }


    val test1 = fun(x: Int, y: Int) = x + y  // 当返回值可以自动推断出来的时候，可以省略，和函数一样
    val test2 = fun(x: Int, y: Int): Int = x + y
    val test3 = fun(x: Int, y: Int): Int {
        return x + y
    }

    val iop = fun Int.(other: Int) = this + other

    fun ab() {
        println(test1(3, 5))
        println(test2(4, 6))
        println(test3(5, 7))
        println(2.iop(3))
    }

    /**
     * 闭包，即是函数中包含函数
     */
    fun test(b: Int): () -> Int {
        var a = 3
        return fun(): Int {
            a++
            return a + b
        }
    }

    val t = test(3)

    fun excute3() {
        println(t())
        println(t())
        println(t())// 7,8,9
    }


    fun excute4() {
        var sum: Int = 0
        val arr = arrayOf(1, 3, 5, 7, 9)
        arr.filter { it < 7 }.forEach { sum += it }

        println(sum)
    }


    fun alphabetApply(): String = StringBuffer().apply {
        for (letter in 'A'..'Z') {
            append(letter)
        }
        append("\n Now I know the alphabet")
    }.toString()

    fun main(args: Array<String>) {

    }
}