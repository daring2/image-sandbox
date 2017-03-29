package com.gitlab.daring.image.swing

import java.awt.event.ActionEvent
import javax.swing.AbstractAction
import javax.swing.JComponent
import javax.swing.JComponent.WHEN_FOCUSED
import javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW
import javax.swing.KeyStroke.getKeyStroke

class BaseAction(name: String, val act: () -> Unit) : AbstractAction(name) {

    constructor(name: String, act: Runnable) : this(name, act::run)

    val name get() = getValue(NAME) as String

    override fun actionPerformed(e: ActionEvent) {
        act.invoke()
    }

    fun register(c: JComponent, keyStr: String, inWindow: Boolean = false) {
        c.actionMap.put(name, this)
        if (!keyStr.isEmpty()) {
            val ks = getKeyStroke(keyStr)
            val cond = if (inWindow) WHEN_IN_FOCUSED_WINDOW else WHEN_FOCUSED
            c.getInputMap(cond).put(ks, name)
        }
    }

}