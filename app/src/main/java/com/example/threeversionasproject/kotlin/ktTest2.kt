package com.example.threeversionasproject.kotlin

import android.view.View
import android.widget.TextView
import com.example.threeversionasproject.application.MyApplication
import com.example.threeversionasproject.kotlin.Site.name

/**
 * Created by Ocean on 2020/7/19.
 *
 * 扩展函数
 */
class MyClass {
    companion object {
        val myClassField1: Int = 1
        var myClassField2 = "this is myClassField2"
        fun companionFun1() {
            println("this is 1st companion function.")
        }

        fun companionFun2() {
            println("this is 2st companion function.")
            companionFun1()
            foo()
        }
    }

    fun MyClass.Companion.foo() {
        println("伴随对象的扩展函数（内部）")
    }

    fun test2() {
        MyClass.foo()
    }

    init {
        test2()
    }
}

val MyClass.Companion.no: Int
    get() = 10

fun MyClass.Companion.foo() {
    println("foo 伴随对象外部扩展函数")
}

fun main(args: Array<String>) {
    println("no:${MyClass.no}")
    println("field1:${MyClass.myClassField1}")
    println("field2:${MyClass.myClassField2}")
    MyClass.foo()
    MyClass.companionFun2()
}

/**
 * 密封类 eg:1
 */
sealed class Expr

data class Const(val number: Double) : Expr()
data class Sum(val e1: Expr, val e2: Expr) : Expr()
object NotANuber : Expr()

fun eval(expr: Expr): Double = when (expr) {
    is Const -> expr.number
    is Sum -> eval(expr.e1) + eval(expr.e2)
    NotANuber -> Double.NaN
}

/**
 * 密封类 eg:2
 */
sealed class UiOp {
    object Show : UiOp()
    object Hide : UiOp()
    class TranslateeeX(val px: Float) : UiOp()
    class TranslateeeY(val px: Float) : UiOp()
}

//需要携带额外信息时才定义密封类的子类使用when判断使用is  不需要携带除“显示或隐藏view之外的其它信息”时
//用 object 关键字创建单例就可以了，并且此时 when 子句不需要使用 is 关键字
fun execute(view: View, op: UiOp) = when (op) {
    UiOp.Show -> view.visibility = View.VISIBLE
    UiOp.Hide -> view.visibility = View.GONE
    is UiOp.TranslateeeX -> view.translationX = op.px // 这个 when 语句分支不仅告诉 view 要水平移动，还告诉 view 需要移动多少距离，这是枚举等 Java 传统思想不容易实现的
    is UiOp.TranslateeeY -> view.translationY = op.px
}

fun test() {
    val view: View = TextView(MyApplication.getContext());
    execute(view, UiOp.Show)
    execute(view, UiOp.TranslateeeX(3f))
    eval(NotANuber)
    eval(Const(5.00))
}

/**
 * 泛型
 */
class Box<T>(t: T) {
    val value = t
}

fun test1() {
    val box1 = Box<String>("hello")
    val box2 = Box(2)

    println("${box1.value}---${box2.value}")
}

fun <T> boxIn(value: T): Box<T> {
    return Box(value)
}
//上面的另一种写法
//fun <T> boxIn(value:T)=Box(value)


fun test2() {
    val box1 = boxIn(1)
    val box2 = boxIn<String>("你好")

    println("${box1.value}---${box2.value}")
}

fun <T : Comparable<T>> sort(list: List<T>) {
    // ……
}

fun test3() {
    sort(listOf(1, 2, 3)) // OK。Int 是 Comparable<Int> 的子类型
//    sort(listOf(HashMap<Int, String>())) // 错误：HashMap<Int, String> 不是 Comparable<HashMap<Int, String>> 的子类型
}


// 定义一个支持协变的类

//使用 out 使得一个类型参数协变，协变类型参数只能用作输出，可以作为返回值类型但是无法作为入参的类型：
class RunoobOut<out A>(val a: A) {
    fun foo(): A {
        return a
    }
}

//in 使得一个类型参数逆变，逆变类型参数只能用作输入，可以作为入参的类型但是无法作为返回值的类型：
class RunoobIn<in A>(a: A) {
    fun foo(a: A) {
    }
}

fun test4(args: Array<String>) {
    var strCo: RunoobOut<String> = RunoobOut("a")
    var anyCo: RunoobOut<Any> = RunoobOut<Any>("b")
    anyCo = strCo
    println(anyCo.foo())   // 输出 a


    var anyDCo = RunoobIn<Any>("b")
    var strDCo = RunoobIn("a")
    strDCo = anyDCo
}

/**
 * 星号投射
 */
class A<T>(val t: T, val t2: T, val t3: T)

class Apple(var name: String)

fun test5(args: Array<String>) {
    //使用类
    val a1: A<*> = A(12, "String", Apple("苹果"))
    val a2: A<Any?> = A(null, "String", Apple("苹果"))   //和a1是一样的
    val apple = a1.t3    //参数类型为Any
    println(apple)
    val apple2 = apple as Apple   //强转成Apple类
    println(apple2.name)
    //使用数组
    val l: ArrayList<Any?> = arrayListOf(null, 1, 1.2f, Apple("苹果"))
    for (item in l) {
        println(item)
    }
}


/**
 * 枚举
 */


enum class ProtocolState {
    WAITING {
        override fun signal() = TALKING

    },

    TALKING {
        override fun signal() = WAITING
    };

    abstract fun signal(): ProtocolState
}

enum class Color {
    RED, BLACK, BLUE, GREEN, WHITE
}

enum class Color2(val rgb: Int) {
    RED(0xFF0000),
    GREEN(0x00FF00),
    BLUE(0x0000FF)
}

fun test6(args: Array<String>) {
    var color: Color = Color.BLUE

    println(Color.values())
    println(ProtocolState.valueOf("WAITING"))
    println(color.name)
    println(color.ordinal)

}


fun test6() {
    var v: ProtocolState = ProtocolState.WAITING
    var x: ProtocolState = ProtocolState.TALKING

    println(v.name)
    println(v.ordinal)
}

/**
 * Kotlin 对象表达式和对象声明
 */
class C {
    // 私有函数，所以其返回类型是匿名对象类型
    private fun foo() = object {
        val x: String = "x"
    }

    // 公有函数，所以其返回类型是 Any
    fun publicFoo() = object {
        val x: String = "x"
    }

    fun bar() {
        val x1 = foo().x        // 没问题
//        val x2 = publicFoo().x  // 错误：未能解析的引用“x”
    }
}

fun test7(args: Array<String>) {
    val site = object {
        var name: String = "菜鸟教程"
        var url: String = "www.runoob.com"
    }
    println(site.name)//菜鸟教程
    println(site.url)//www.runoob.com
}

//当然你也可以定义一个变量来获取获取这个对象，当时当你定义两个不同的变量来获取这个对象时，
// 你会发现你并不能得到两个不同的变量。也就是说通过这种方式，我们获得一个单例。
object Site {
    var url: String = ""
    val name: String = "菜鸟教程"
}

fun test8(args: Array<String>) {
    var s1 = Site
    var s2 = Site
    s1.url = "www.runoob.com"
    println(s1.url)//www.runoob.com
    println(s2.url)//www.runoob.com
}

class Site1 {
    var name = "菜鸟教程"

    object DeskTop {
        var url = "www.runoob.com"
        fun showName() {
            print { "desk legs $name" } // 错误，不能访问到外部类的方法和变量
        }
    }
}
//类内部的对象声明可以用 companion 关键字标记，这样它就与外部类关联在一起，我们就可以直接通过外部类访问到对象的内部元素
class MyClass11 {
    companion object Factory {
        fun create(): MyClass = MyClass()
    }
}

fun test9() {
    var site = Site1()
    Site1.DeskTop.url // 正确
//    site.DeskTop.url // 错误，不能通过外部类的实例访问到该对象
    val instance = MyClass11.create()// 或者MyClass11.Factory.create() 访问到对象的内部元素
}
/**
对象表达式和对象声明之间有一个重要的语义差别：

1.对象表达式是在使用他们的地方立即执行的

2.对象声明是在第一次被访问到时延迟初始化的

3.伴生对象的初始化是在相应的类被加载（解析）时，与 Java 静态初始化器的语义相匹配
 */





