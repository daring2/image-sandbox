package com.gitlab.daring.image.command

import com.gitlab.daring.image.command.CommandRegistry.Companion.commandRegistry
import com.gitlab.daring.image.swing.BaseAction
import javax.swing.JComboBox
import javax.swing.JTextArea
import javax.swing.plaf.basic.BasicComboPopup

internal class CommandPopupMenu(val field: JTextArea) {

    val commands = commandRegistry().commands.sorted()

    val box = JComboBox<String>()
    val popup = box.ui.getAccessibleChild(null, 0) as BasicComboPopup
    val list = popup.list

    private var lastWord = ""

    init {
        commands.forEach(box::addItem)
        list.isFocusable = true
        BaseAction("open", this::open).register(field, "control SPACE")
        BaseAction("apply", this::apply).register(list, "ENTER")
        BaseAction("hide", this::hide).register(list, "ESCAPE")
//        field.addDocumentListener { if (popup.isVisible) refresh() }
    }

    fun open() {
        refresh()
        val cp = field.caret.magicCaretPosition
        popup.show(field, cp.x, cp.y)
        list.requestFocus()
    }

    fun refresh() {
        val text = field.getText(0, field.caretPosition)
        val w = text.takeLastWhile(Char::isLetter)
        if (w == lastWord) return
        lastWord = w
        box.removeAllItems()
        commands.filter { it.startsWith(w) }.forEach(box::addItem)
    }

    fun apply() {
        val w = lastWord
        field.document.remove(field.caretPosition - w.length, w.length)
        field.insert("" + list.selectedValue, field.caretPosition)
        hide()
    }

    fun hide() {
        popup.hide()
        field.requestFocus()
    }

}