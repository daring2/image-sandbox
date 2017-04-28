package com.gitlab.daring.image.command

import com.gitlab.daring.image.command.parameter.CommandParam
import com.gitlab.daring.image.command.parameter.CommandParamPanel
import com.gitlab.daring.image.command.parameter.CommandParamUtils.buildParamConfig
import com.gitlab.daring.image.config.ConfigUtils.AppConfigFile
import com.gitlab.daring.image.config.ConfigUtils.configFromMap
import com.gitlab.daring.image.config.ConfigUtils.saveDiffConfig
import com.gitlab.daring.image.event.VoidEvent
import com.gitlab.daring.image.swing.BaseAction
import com.gitlab.daring.image.swing.NotificationUtils.showErrorDialog
import net.miginfocom.swing.MigLayout
import org.slf4j.LoggerFactory.getLogger
import java.util.*
import javax.swing.*

open class CommandScriptPanel : JPanel() {

    protected var logger = getLogger(javaClass)

    val applyEvent = VoidEvent()
    val changeEvent = VoidEvent()
    val script = CommandScript()
    val staticParams = ArrayList<CommandParam<*>>()

    protected val staticParamPanel = CommandParamPanel()
    protected val paramPanel = CommandParamPanel()

    val scriptField: JTextArea
    val changeListener = { _: Void? -> changeEvent.fire() }

    init {
        layout = MigLayout("fill, wrap 1", "[fill]", "[center]")
        add(staticParamPanel)
        add(JSeparator())
        scriptField = createScriptField()
        CommandPopupMenu(scriptField)
        createButtons()
        add(JSeparator())
        add(paramPanel)
        applyEvent.onFire { this.apply() }
        script.errorEvent.addListener { this.onError(it) }
    }

    fun createScriptField(): JTextArea {
        val field = JTextArea("", 5, 20)
        add(JLabel("Скрипт"), "left")
        add(JScrollPane(field), "h 1000")
        return field
    }

    fun createButtons() {
        val act = BaseAction("Применить", { applyEvent.fire() })
        act.register(this, "control S", true)
        add(JButton(act), "left, grow 0")
    }

    fun addStaticParams(vararg ps: CommandParam<*>) {
        staticParams.addAll(ps)
    }

    fun setScript(script: String) {
        scriptField.text = script
        applyEvent.fire()
    }

    fun apply() {
        script.text = scriptField.text
        apply(staticParamPanel, staticParams)
        apply(paramPanel, script.command.params)
    }

    fun apply(p: CommandParamPanel, ps: List<CommandParam<*>>) {
        p.applyEvent.fire()
        p.params = ps
        p.addParamChangeListener(changeListener)
    }

    internal fun onError(e: Exception?) {
        logger.error("Script error", e)
        showErrorDialog(this, "Ошибка выполнения:\n" + e)
    }

    protected fun buildConfigMap(): Map<String, Any> {
        val m = buildParamConfig(staticParams).toMutableMap()
        m["script"] = script.text
        return m
    }

    fun saveConfig(path: String) {
        val m = buildConfigMap()
        val c = configFromMap(m).atPath(path)
        saveDiffConfig(c, AppConfigFile)
    }

}
