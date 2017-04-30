package com.gitlab.daring.image.sandbox

import com.gitlab.daring.image.MainContext
import com.gitlab.daring.image.config.ConfigUtils.defaultConfig
import com.gitlab.daring.image.swing.BaseFrame

class ImageSandbox : AutoCloseable {

    val ConfigPath = "isb.ImageSandbox"
    val config = defaultConfig.getConfig(ConfigPath)

    internal val mp = MainPanel(this)
    internal val scriptExecutor = ScriptExecutor(this)

    init {
        mp.applyEvent.onFire(this::apply)
        mp.changeEvent.onFire(this::executeScript)
        executeScript()
    }

    fun showFrame() {
        val frame = BaseFrame("ImageSandbox", mp)
        frame.addCloseListener(this::close)
        frame.show(800, 600)
    }

    fun apply() {
        executeScript()
        mp.saveConfig(ConfigPath)
    }

    fun executeScript() {
        scriptExecutor.executeAsync()
    }

    override fun close() {
        scriptExecutor.close()
        MainContext.close()
    }

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            ImageSandbox().showFrame()
        }
    }

}
