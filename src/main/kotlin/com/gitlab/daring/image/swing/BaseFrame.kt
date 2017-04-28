package com.gitlab.daring.image.swing

import java.awt.Container
import java.awt.event.WindowAdapter
import java.awt.event.WindowEvent
import javax.swing.JFrame

class BaseFrame(title: String, content: Container) : JFrame(title) {

    init {
        contentPane = content
        pack()
        defaultCloseOperation = DISPOSE_ON_CLOSE
        isLocationByPlatform = true
    }

    fun addCloseListener(l: () -> Unit) {
        this.addWindowListener(object : WindowAdapter() {
            override fun windowClosed(e: WindowEvent) = l()
        })
    }

    fun show(width: Int, height: Int) {
        setSize(width, height)
        isVisible = true
    }

}