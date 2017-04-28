package com.gitlab.daring.image.command

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Arrays.asList

class CommandRegistryTest {

    val r = CommandRegistry()

    @Test
    @Throws(Exception::class)
    fun testParseScript() {
        r.register("cmd1", ::TestCommand1)
        r.register("cmd2", ::TestCommand2)

        checkParse("cmd1", TestCommand1())
        checkParse("cmd1()", TestCommand1())

        val cmd1 = TestCommand1("p1")
        val cmd2 = TestCommand2("p2")
        checkParse("cmd1(p1)", cmd1)
        checkParse("\n cmd1(p1) ;\n ", cmd1)
        checkParse("cmd1(p1); cmd2(p2)", cmd1, cmd2)
        checkParse("cmd1(p1)  \n cmd2(p2) ; ", cmd1, cmd2)
    }

    fun checkParse(script: String, vararg cmds: Command) {
        val cmd = r.parseScript(script)
        assertEquals(cmd.commands, asList(*cmds))
    }

    class TestCommand1(vararg ps: String) : KBaseCommand(arrayOf(*ps)) {
        override fun execute(env: CommandEnv) {}
    }

    class TestCommand2(vararg ps: String) : KBaseCommand(arrayOf(*ps)) {
        override fun execute(env: CommandEnv) {}
    }

}