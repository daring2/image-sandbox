package com.gitlab.daring.image.command.parameter

import com.gitlab.daring.image.swing.SwingUtils.newButton
import org.apache.commons.lang3.StringUtils.defaultIfBlank
import java.io.File
import java.nio.file.Paths
import javax.swing.JButton
import javax.swing.JFileChooser
import javax.swing.JFileChooser.APPROVE_OPTION
import javax.swing.JTextField
import javax.swing.filechooser.FileFilter
import javax.swing.filechooser.FileNameExtensionFilter

class ParamFileChooser(val param: FileParam, val valueField: JTextField) {

    val openButton: JButton = newButton("...", this::open)

    val fc by lazy { createFileChooser() }

    fun createFileChooser(): JFileChooser {
        val fc = JFileChooser()
        val vf = File(defaultIfBlank(param.v, "."))
        fc.currentDirectory = if (vf.isFile) vf.parentFile else vf
        fc.isMultiSelectionEnabled = param.maxCount != 1
        fc.fileFilter = createFileFilter()
        return fc
    }

    fun createFileFilter(): FileFilter {
        val exts = arrayOf("bmp", "png", "jpg", "jpeg", "gif")
        return FileNameExtensionFilter("Images", *exts)
    }

    fun open() {
        val r = fc.showOpenDialog(valueField)
        if (r == APPROVE_OPTION) {
            valueField.text = buildFileList()
            valueField.postActionEvent()
        }
    }

    fun buildFileList(): String {
        val d = Paths.get("").toAbsolutePath()
        val fs = if (fc.isMultiSelectionEnabled) fc.selectedFiles else arrayOf(fc.selectedFile)
        return fs.map(File::toPath).map { p ->
            try { d.relativize(p) } catch (e: Exception) { p }
        }.joinToString(",");
    }

}