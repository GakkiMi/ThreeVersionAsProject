package com.example.threeversionasproject.kotlin

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.threeversionasproject.R

class KotlinActivity : AppCompatActivity() {

    var age: String? = "23"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlin)
        println("---------" + sum(3, 4))
        println("---------" + sum1(6, 4))
        avage(4, 2, 6, 46, 8, 5)
        //lambda表达式
        val sumLambda: (Int, Int) -> Int = { x, y -> x + y }
        println(sumLambda(2, 3))
        var age: String? = null           //可以为空的 age
//        val age1 = age!!.toInt()    //为空则抛出异常
        val age2 = age?.toInt()     //为空则返回null
//        var age3 = age?.toInt() ?: -1   //为空则返回 -1
        println("---------" + age2)


        val array: IntArray = intArrayOf(11, 22, 33)

        for (s in array) {
            println(s)
        }

        fun hasPrefix(x: Any) = when (x) {
            is String -> x.startsWith("prefix")
            else -> false
        }

        val allByDefault: Int? = 6


        val s = "runoob"

        val str = "$s.length is ${s.length}" // 求值结果为 "runoob.length is 6"
        println(str)

        val b = Array(3, { i -> (i * 2) })
        b[1] = 6;
        for (index in b.indices) {
            println(b[index])
        }


        for (i in 1..10) {
            if (i == 3) continue  // i 为 3 时跳过当前循环，继续下一次循环
            println(i)
            if (i > 5) break   // i 为 6 时 跳出循环
        }
        var person: Person = Person()
        person.no = 5

        val items = listOf("apple", "banana", "kiwi")
        for (item in items) {
            println(item)
        }


        val kt: ktTest = ktTest()
        kt.goo()


    }


    fun strMode() {
        var a = 1;
        val s1 = "a is " + a
        val s2 = "${s1.replace("is", "was")},but now is $a"
        println(s1)
        println(s2)

    }


    fun sum(a: Int, b: Int): Int {
        print("========" + a + b);
        return a + b;
    }

    public fun sum1(a: Int, b: Int): Int = a + b;

    fun avage(vararg v: Int) {
        for (i in v) {
            println(v[i])
        }
        for (i in v.indices) {
            println(v[i])
        }
    }


    fun getStringLength(obj: Any): Int? {

        obj.hashCode()
        if (obj !is String) {
            return null
        }
        if (obj !is Int) {
            return null
        }
        // 在这个分支中, `obj` 的类型会被自动转换为 `String`
        return obj.length;
    }


    fun hasPrefix(x: Any) = when (x) {
        is String -> x.startsWith("prefix")
        else -> false


    }

    fun hasPrssefix(x: Any): Boolean = if (x is String) x.startsWith("prefix") else false


    fun test() {
        Runoob("")

    }

    //有了主构造器，次构造器必须调用主构造器

    class Runoob(var name: String) {  // 类名为 Runoob
        // 大括号内是类体构成
        var url: String = "http://www.runoob.com"
        var country: String = "CN"
        var siteName = ""


        init {
            println("初始化网站名: ${name}")
        }

        fun printTest() {
            println("我是类的函数")
        }

        private constructor(ddd: Int) : this("gadg") {
            println("我是类的次构造函数")

        }

        constructor(ddd: Int, d: String) : this("hahha") {

        }

        constructor(name: String, sdsdf: String) : this(6) {
            println("我是类的次构造函数两个参数")

        }
    }


    class Person {


        init {
            println("你好")
        }


        var lastName: String = "zhang"
            get() = if (field == "") "" else field.toUpperCase()   // 将变量赋值后转换为大写
            set(va) {

            }

        var no: Int = 100
            get() = field                // 后端变量
            set(value) {
                if (value < 10) {       // 如果传入的值小于 10 返回该值
                    field = value
                } else {
                    field = -1         // 如果传入的值大于等于 10 返回 -1
                }
            }


        var heiht: Float = 145.4f
            private set


    }

    /**
     * 抽象类
     */
    open class Base {
        open fun f() {
            println("歧视")
        }
    }

    class Derived : Base() {
        override fun f() {
            super.f()
            println("寿司")

        }
    }

    /**
     * 嵌套类
     */
    class Outer {                  // 外部类
        private val bar: Int = 1

        class Nested {             // 嵌套类
            fun foo() = 2
        }
    }

    fun c() {
        val demo = Outer.Nested().foo()// 调用格式：外部类.嵌套类.嵌套类方法/属性
        println(demo)    // == 2
    }

    /**
     * 内部类嵌套和嵌套类的区别  嵌套类Outer.Nested()...  内部类嵌套Outer2().Inner()...
     */

    class Outer2 {
        private val bar: Int = 1
        var v = "成员属性"

        /**嵌套内部类**/
        inner class Inner {
            fun foo() = bar  // 访问外部类成员
            fun innerTest() {
                var o = this@Outer2 //获取外部类的成员变量
                //var o =Outer2() //获取外部类的成员变量
                println("内部类可以引用外部类的成员，例如：" + o.v)
            }
        }
    }

    fun b() {
        val d = Outer2().Inner().foo()
        println(d)
        val e = Outer2().Inner().innerTest()
        println("===" + e)
    }


    /**
     * 接口
     */
    interface TestInterface {
        fun test()
    }

    class Test {
        var a = 1;

        fun test(testInterface: TestInterface) {
            testInterface.test()
        }
    }

    fun a() {
        val test = Test()
        test.test(object : TestInterface {
            override fun test() {
                println("hahah")
            }
        })
    }


    /**
     * 继承
     */
    //基类
    open class person(var name: String, var age: Int) {// 基类


        constructor(m_age: Int, m_name: String) : this(m_name, m_age) {

        }
    }

    //子类有主构造函数，基类必须再主构造函数中初始化  这个调的是基类的主构造函数
    class student(name: String, age: Int, var sex: String) : person(name, age) {

    }

    //子类无主构造函数，则再次构造函数中用super关键字初始化基类  这个调的是基类的次构造函数
    class student1 : person {

        constructor(name: String, age: Int) : super(age, name) {

        }

    }

    fun main(args: Array<String>) {
        val s = student("Runoob", 18, "S12346")
        println("学生名： ${s.name}")
        println("年龄： ${s.age}")
        println("性别： ${s.sex}")

        val s1 = student1("张三", 23)
        println("学生名： ${s1.name}")


    }


    /**
     * 重写
     */
    open class A {
        open fun f() {
            print("A")
        }

        fun a() {
            print("a")
        }
    }

    interface B {
        fun f() {
            print("B")
        } //接口的成员变量默认是 open 的

        fun b() {
            print("b")
        }
    }

    class C() : A(), B {
        override fun f() {
            super<A>.f()
            super<B>.f()
        }
    }

    fun kkk() {
        val c = C()
        c.f();

    }

    /**
     * 接口
     */

    interface MyInterface {
        fun a()  //未实现  必选
        fun b() {
            //已实现  可选
            println("方法b")
        }

        var name: String //name 属性, 抽象的
    }

    class inter : MyInterface {
        override var name: String = "runoob"

        override fun a() {
            println("方法a的实现")
        }

    }

    fun d() {
        var inter = inter()
        inter.a()
        inter.b()
        println(inter.name)
    }

    /**
     * 接口函数重写
     */

    interface interA {
        fun foo() {
            print("A")
        }   // 已实现

        fun bar()                  // 未实现，没有方法体，是抽象的
    }

    interface interB {
        fun foo() {
            print("B")
        }   // 已实现

        fun bar() {
            print("bar")
        } // 已实现
    }

    class interC() : interA {
        override fun bar() {
            println("interA==bar")
        }
    }

    class interD() : interA, interB {
        override fun foo() {
            super<interA>.foo()
            super<interB>.foo()
        }

        override fun bar() {
            println("实现interA的bar（）")
        }

    }

    fun interE() {
        val d = interD();
        d.foo()
        d.bar()
    }

    /**
     * 扩展函数  MutableList的扩展
     */
    //eg:1
    fun MutableList<Int>.swap(index1: Int, index2: Int) {
        val tmp = this[index1]     //  this 对应该列表
        this[index1] = this[index2]
        this[index2] = tmp
    }

    fun swapTest() {
        val l = mutableListOf<Int>(1, 2, 3)
        l.swap(0, 2)
        println(l.toString())//输出结果 [3, 2, 1]
    }

    //eg:2
    open class expandC

    class expandD : expandC()

    fun expandC.Foo() = "expandC"

    fun expandD.Foo() = "expandD"

    fun printFoo(c: expandC) {
        println(c.Foo())
    }

    fun expandTest() {
        printFoo(expandD())//输出结果  c
    }


    //若扩展函数和成员函数一致，则使用该函数时，会优先使用成员函数。

    //扩展一个空对象
    fun Any?.toString(): String {
        if (this == null) return "null"
        // 空检测之后，“this”会自动转换为非空类型，所以下面的 toString()
        // 解析为 Any 类的成员函数
        return toString()
    }

    fun te() {
        var t = null
        println(t.toString())
    }

    /**
     * 伴身对象扩展函数
     */
    class MyClass {
        companion object {}  // 将被称为 "Companion"
    }

    fun MyClass.Companion.foo() {
        println("伴随对象的扩展函数")
    }

    val MyClass.Companion.no: Int
        get() = 10

    fun maain(args: Array<String>) {
        println("no:${MyClass.no}")
        MyClass.foo()
    }

    //eg
    fun <T> Collection<T>.joinToString(
            separator: String = ",",
            prefix: String = "",
            postfix: String = ""
    ): String {
        val result = StringBuilder(prefix)
        for ((index, value) in this.withIndex()) {
            if (index > 0) {
                result.append(separator)
            }
            result.append(value)
        }
        result.append(postfix)
        return result.toString()
    }

    fun maisssn(args: Array<String>) {
        println(listOf("a", "b", "c").joinToString(prefix = "[", postfix = "]"))
    }

}

fun ktTest.hahah(){

}



