package com.gitlab.daring.image.command;

import com.gitlab.daring.image.transform.TransformCommands;
import com.typesafe.config.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.gitlab.daring.image.command.CommandUtils.parseParams;
import static com.gitlab.daring.image.config.ConfigUtils.defaultConfig;
import static com.gitlab.daring.image.util.ExceptionUtils.throwArgumentException;
import static com.gitlab.daring.image.util.ExtStringUtils.splitAndTrim;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class CommandRegistry {

	static final CommandRegistry Instance = new CommandRegistry();

	public static CompositeCommand parseCmdScript(String script) {
		return Instance.parseScript(script);
	}

	final Config cmdConf = defaultConfig().getConfig("gmv.commands");
	final Map<String, Command.Factory> factories = new HashMap<>();
	final Map<String, Command> cache = new ConcurrentHashMap<>();

	public CommandRegistry() {
		EnvCommands.register(this);
		TransformCommands.register(this);
		register("show", ShowCommand::new);
	}

	public void register(String name, Command.Factory f) {
		factories.put(name, f);
	}

	public CompositeCommand parseScript(String script) {
		List<String> ss = splitAndTrim(script, ";\n");
		List<Command> cmds = ss.stream().map(this::getCommand).collect(toList());
		return new CompositeCommand(cmds);
	}

	private Command getCommand(String cmdStr) {
		Command cmd = cache.get(cmdStr);
		if (cmd == null) {
			cmd = parseCommand(cmdStr);
			if (cmd.isCacheable()) cache.put(cmdStr, cmd);
		}
		return cmd;
	}

	private Command parseCommand(String cmdStr) {
		List<String> ss = splitAndTrim(cmdStr, "()");
		String name = ss.get(0);
		String[] ps = parseParams(ss.size() > 1 ? ss.get(1) : "", getDefParams(name));
		Command.Factory cf = factories.get(name);
		if (cf == null) throwArgumentException("Invalid command " + cmdStr);
		return cf.create(ps);
	}

	private List<String> getDefParams(String name) {
		return cmdConf.hasPath(name) ? cmdConf.getStringList(name) : emptyList();
	}

}
