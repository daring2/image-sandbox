package com.gitlab.daring.sandbox.image.commands;

import com.gitlab.daring.sandbox.image.transform.TransformCommands;
import com.typesafe.config.Config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gitlab.daring.sandbox.image.commands.CommandUtils.parseArgs;
import static com.gitlab.daring.sandbox.image.util.ConfigUtils.defaultConfig;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang3.StringUtils.split;

public class CommandRegistry {

	static final CommandRegistry Instance = new CommandRegistry();

	public static Command parseScript(String script) {
		return Instance.parse(script);
	}

	final Config cmdConf = defaultConfig().getConfig("gmv.commands");
	final Map<String, Command.Factory> registry = new HashMap<>();

	public CommandRegistry() {
		TransformCommands.register(this);
	}

	public void register(String name, Command.Factory f) {
		registry.put(name, f);
	}

	public Command parse(String script) {
		String[] ss = split(script, ";\n");
		List<Command> cmds = Arrays.stream(ss).map(this::parseCommand).collect(toList());
		return new CompositeCommand(cmds);
	}

	private Command parseCommand(String conf) {
		String[] ss = split(conf, "()");
		String name = ss[0].trim();
		String[] args = parseArgs(ss[1], getDefArgs(name));
		return registry.get(name).create(args);
	}

	private List<String> getDefArgs(String name) {
		return cmdConf.hasPath(name) ? cmdConf.getStringList(name) : emptyList();
	}

}
