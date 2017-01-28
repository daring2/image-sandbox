package com.gitlab.daring.sandbox.image.command;

import com.gitlab.daring.sandbox.image.transform.ConvertCommand;
import org.junit.Test;

import static com.gitlab.daring.sandbox.image.command.CommandRegistry.parseScript;
import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class CommandRegistryTest {

	@Test
	public void testParseScript() throws Exception {
        Command cmd1 = new ConvertCommand("grey");
        Command cmd2 = new ShowCommand("i1");
        checkParse("convert(grey)", cmd1);
        checkParse("\n convert(grey) ;\n ", cmd1);
        checkParse("convert(grey); show(i1)", cmd1, cmd2);
        checkParse("convert(grey)  \n show(i1) ; ", cmd1, cmd2);
	}

	void checkParse(String script, Command... cmds) {
        CompositeCommand cmd = (CompositeCommand) parseScript(script);
        assertEquals(cmd.commands, asList(cmds));
    }

}