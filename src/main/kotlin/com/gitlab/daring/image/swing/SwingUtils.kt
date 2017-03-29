package com.gitlab.daring.image.swing

import com.gitlab.daring.image.util.CommonUtils.tryRun
import javax.swing.JButton
import javax.swing.SwingUtilities.invokeAndWait
import javax.swing.SwingUtilities.isEventDispatchThread
import javax.swing.event.DocumentEvent
import javax.swing.text.JTextComponent

object SwingUtils {

    fun isEdt() = isEventDispatchThread()

    @JvmStatic
    fun runInEdt(r: Runnable) {
        tryRun { if (isEdt()) r.run() else invokeAndWait(r) }
    }

    @JvmStatic
    fun newButton(label: String, act: () -> Unit): JButton {
        val b = JButton(label)
        b.addActionListener { act.invoke() }
        return b
    }

    fun JTextComponent.addDocumentListener(l: (DocumentEvent) -> Unit) {
        this.document.addDocumentListener(DocumentAdapter(l))
    }

}