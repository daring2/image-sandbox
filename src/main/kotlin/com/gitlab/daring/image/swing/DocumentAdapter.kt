package com.gitlab.daring.image.swing

import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class DocumentAdapter(val func: (DocumentEvent) -> Unit): DocumentListener {

    override fun changedUpdate(e: DocumentEvent) = func(e)
    override fun insertUpdate(e: DocumentEvent) = func(e)
    override fun removeUpdate(e: DocumentEvent) = func(e)

}