package com.example.threeversionasproject.kotlin



/**
 * Created by Ocean on 2020/7/18.
 */
class ktTest {


    open class View{
        open fun click() {
            println("view clicked")
        }
    }

    open class Button: View() {
        override fun click(){
            println("button clicked")
        }
    }

    fun View.longClick() = println("view longClicked")
    fun Button.longClick() = println("button longClicked")

    fun main(args: Array<String>) {
        val button: View = Button()
        button.click()
        button.longClick()

    }

}

fun ktTest.goo(){}