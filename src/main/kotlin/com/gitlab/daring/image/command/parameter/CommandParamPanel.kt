package com.gitlab.daring.image.command.parameter

import com.gitlab.daring.image.event.VoidEvent
import net.miginfocom.swing.MigLayout
import java.util.Collections.emptyList
import javax.swing.*

class CommandParamPanel : JPanel() {

    @JvmField
    val applyEvent = VoidEvent()

    var params = emptyList<CommandParam<*>>()
        set(ps) {
            if (params == ps) return
            field = ps
            applyEvent.removeListeners()
            removeAll()
            ps.forEach { this.addParam(it) }
            revalidate()
        }

    init {
        layout = MigLayout("fill, wrap 2", "[right][grow,fill]", "[center]")
    }


    fun addParamChangeListener(l: (Void?) -> Unit) {
        params.forEach { it.changeEvent.addListener(l) }
    }

    fun addParam(p: CommandParam<*>) {
        if (p.name.isEmpty()) return
        when (p) {
            is ParamGroup -> addComponent(p.name, JSeparator(), "")
            is NumberParam<*> -> addNumberParam(p)
            is EnumParam<*> -> addEnumParam(p)
            is BooleanParam -> addBooleanParam(p)
            is FileParam -> addFileParam(p)
            is StringParam -> addStringParam(p)
        }
    }

    fun addNumberParam(p: NumberParam<*>) {
        val sl = JSlider(p.minValue.toInt(), p.maxValue.toInt(), p.value.toInt())
        val range = sl.maximum - sl.minimum
        sl.majorTickSpacing = if (range > 15) range / 10 else 1
        sl.paintLabels = true
        sl.addChangeListener { p.setNumValue(sl.value) }
        val f = newValueField(p, false)
        f.columns = 5
        addComponent(p.name, f, "split 2, growx 0")
        add(sl)
    }

    fun <T : EnumParam<*>> addEnumParam(p: T) {
        val vs = p.enumValues.map { "$it" }.toTypedArray()
        val cb = JComboBox(vs)
        cb.selectedItem = p.stringValue
        cb.addActionListener { p.stringValue = vs[cb.selectedIndex] }
        addComponent(p.name, cb, "")
    }

    fun addBooleanParam(p: BooleanParam) {
        val b = JCheckBox("", p.value)
        b.addItemListener { p.value = b.isSelected }
        addComponent(p.name, b, "")
    }

    fun addFileParam(p: FileParam) {
        val f = newValueField(p, true)
        val fc = ParamFileChooser(p, f)
        addComponent(p.name, f, "split 2")
        add(fc.openButton, "growx 0")
    }

    fun addStringParam(p: StringParam) {
        val f = newValueField(p, true)
        addComponent(p.name, f, "")
    }

    fun <T: Any> newValueField(p: CommandParam<T>, editable: Boolean): JTextField {
        val f = JTextField("" + p.value)
        if (editable) {
            f.addActionListener { p.stringValue = f.text }
            applyEvent.onFire { p.vr = p.parseValue(f.text) }
        } else {
            f.isEditable = false
            p.changeEvent.onFire { f.text = "" + p.value }
        }
        return f
    }

    fun addComponent(label: String, comp: JComponent, spec: String) {
        add(JLabel(label))
        add(comp, spec)
    }

}