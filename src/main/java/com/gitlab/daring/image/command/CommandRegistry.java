package com.gitlab.daring.image.command;

import com.gitlab.daring.image.transform.TransformCommands;
import com.typesafe.config.Config;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.gitlab.daring.image.command.CommandUtils.parseParams;
import static com.gitlab.daring.image.config.ConfigUtils.defaultConfig;
import static com.gitlab.daring.image.util.ExtStringUtils.splitAndTrim;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class CommandRegistry {

	static final CommandRegistry Instance = new CommandRegistry();

	public static Command parseScript(String script) {
		return Instance.parse(script);
	}

	final Config cmdConf = defaultConfig().getConfig("gmv.commands");
	final Map<String, Command.Factory> registry = new HashMap<>();

	public CommandRegistry() {
		TransformCommands.register(this);
		register("show", ShowCommand::new);
	}

	public void register(String name, Command.Factory f) {
		registry.put(name, f);
	}

	public Command parse(String script) {
		List<String> ss = splitAndTrim(script, ";\n");
		List<Command> cmds = ss.stream().map(this::parseCommand).collect(toList());
		return new CompositeCommand(cmds);
	}

	private Command parseCommand(String conf) {
		List<String> ss = splitAndTrim(conf, "()");
		String name = ss.get(0);
		String[] ps = parseParams(ss.size() > 1 ? ss.get(1) : "", getDefParams(name));
		return registry.get(name).create(ps);
	}

	private List<String> getDefParams(String name) {
		return cmdConf.hasPath(name) ? cmdConf.getStringList(name) : emptyList();
	}

}
