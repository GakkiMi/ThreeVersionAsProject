package com.example.threeversionasproject.kotlin

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

/**
 * Created by Ocean on 2020/7/20.
 */
class ktTest3 {

    /**
     * 委托
     */
    interface Base {
        fun print()
    }

    class BaseImp(val x: Int) : Base {
        override fun print() {
            println(x)
        }
    }

    class Derived(b: Base) : Base by b {

    }

    fun test() {
        val derived = Derived(BaseImp(5))
        val derived1 = BaseImp(5)
        derived.print()
    }


    //属性委托
    class Example {
        var p: String by Delegate()
    }

    // 委托的类
    class Delegate {
        operator fun getValue(thisRef: Any?, property: KProperty<*>): String {
            return "$thisRef, 这里委托了 ${property.name} 属性"
        }

        operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
            println("$thisRef 的 ${property.name} 属性赋值为 $value")
        }
    }

    fun test2() {
        val e = Example()
        println(e.p)     // 访问该属性，调用 getValue() 函数

        e.p = "Runoob"   // 调用 setValue() 函数
        println(e.p)

//    Example@ 433 c675d, 这里委托了 p 属性
//    Example@ 433 c675d 的 p 属性赋值为 Runoob
//    Example@ 433 c675d, 这里委托了 p 属性
    }

    /**
     * 标准委托
     */
    //延迟属性 Lazy
    val lazyValue: String by lazy {
        println("computed!")     // 第一次调用输出，第二次调用不执行
        "Hello"
    }

    fun test3() {
        println(lazyValue)   // 第一次执行，执行两次输出表达式
        println(lazyValue)   // 第二次执行，只输出返回值
//        computed!
//        Hello
//        Hello
    }


    //可观察属性 Observable
    class User {
        var name: String by Delegates.observable("初始值") { prop, old, new ->
            println("旧值：$old -> 新值：$new")
        }
    }

    fun test4() {
        val user = User()
        user.name = "第一次赋值"
        user.name = "第二次赋值"
//执行结果
//        旧值：初始值 -> 新值：第一次赋值
//        旧值：第一次赋值 -> 新值：第二次赋值
    }


    //把属性储存在映射中
    class Site(val map: Map<String, Any?>) {
        val name: String by map
        val url: String  by map
    }

    fun test5() {
        // 构造函数接受一个映射参数
        val site = Site(mapOf(
                "name" to "菜鸟教程",
                "url" to "www.runoob.com"
        ))

        // 读取映射值
        println(site.name)
        println(site.url)

        //菜鸟教程
//        www.runoob.com
    }

    class Foo {
        var notNullBar: String by Delegates.notNull<String>()
    }

    fun test6() {
//        var v = Foo()
//        v.notNullBar="hello"
//        println(v.notNullBar)

        Foo().notNullBar = "bar"
        println(Foo().notNullBar)
    }


}

