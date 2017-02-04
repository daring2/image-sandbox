package com.gitlab.daring.image.command;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

public class CommandRegistryTest {

	final CommandRegistry r = new CommandRegistry();

	@Test
	public void testParseScript() throws Exception {
		r.register("cmd1", TestCommand1::new);
		r.register("cmd2", TestCommand2::new);

		checkParse("cmd1", new TestCommand1());
		checkParse("cmd1()", new TestCommand1());

        Command cmd1 = new TestCommand1("p1");
        Command cmd2 = new TestCommand2("p2");
        checkParse("cmd1(p1)", cmd1);
        checkParse("\n cmd1(p1) ;\n ", cmd1);
        checkParse("cmd1(p1); cmd2(p2)", cmd1, cmd2);
        checkParse("cmd1(p1)  \n cmd2(p2) ; ", cmd1, cmd2);
	}

	void checkParse(String script, Command... cmds) {
		ScriptCommand cmd = r.parseScript(script);
		assertEquals(cmd.commands, asList(cmds));
	}

    static class TestCommand1 extends BaseCommand {
	    public TestCommand1(String... params) {
		    super(params);
	    }
	    @Override
	    public void execute(CommandEnv env) {}
    }

	static class TestCommand2 extends BaseCommand {
		public TestCommand2(String... params) {
			super(params);
		}
		@Override
		public void execute(CommandEnv env) {}
	}

}