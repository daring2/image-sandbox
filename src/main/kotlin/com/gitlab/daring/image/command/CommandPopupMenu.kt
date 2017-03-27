package com.gitlab.daring.image.command

import com.gitlab.daring.image.swing.JScrollPopupMenu
import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent
import javax.swing.JMenuItem
import javax.swing.JTextArea

private class CommandPopupMenu(field: JTextArea) {

    val menu = JScrollPopupMenu("Available commands")

    init {
        field.addKeyListener(object: KeyAdapter() {
            override fun keyPressed(e: KeyEvent) {
                if (e.isControlDown && e.keyChar == ' ') {
                    val commands = CommandRegistry.Instance.factories.keys
                    commands.sorted().forEach { c ->
                        val item = JMenuItem(c)
                        item.addActionListener { _ -> field.insert(" $c;", field.caretPosition) }
                        menu.add(item)
                    }
                    val cp = field.caret.magicCaretPosition
                    menu.show(field, cp.x, cp.y)
                }
            }
        })
    }

}