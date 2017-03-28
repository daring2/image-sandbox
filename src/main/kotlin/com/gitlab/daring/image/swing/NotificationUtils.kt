package com.gitlab.daring.image.swing

import java.awt.Component
import javax.swing.JOptionPane.ERROR_MESSAGE
import javax.swing.JOptionPane.showMessageDialog
import javax.swing.JScrollPane
import javax.swing.JTextArea

object NotificationUtils {

    @JvmStatic
    fun showErrorDialog(c: Component, msg: String) {
        val field = JTextArea(10, 40)
        field.lineWrap = true
        field.isEditable = false
        field.text = msg
        showMessageDialog(c, JScrollPane(field), "Ошибка", ERROR_MESSAGE)
    }

}