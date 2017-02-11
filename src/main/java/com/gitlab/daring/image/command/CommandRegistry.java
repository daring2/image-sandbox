package com.gitlab.daring.image.command;

import com.gitlab.daring.image.command.drawing.DrawCommands;
import com.gitlab.daring.image.command.feature.FeaturesCommands;
import com.gitlab.daring.image.command.transform.TransformCommands;
import com.gitlab.daring.image.common.BaseComponent;
import com.google.common.cache.Cache;
import com.typesafe.config.Config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.gitlab.daring.image.MainContext.mainContext;
import static com.gitlab.daring.image.cache.CacheUtils.buildClosableCache;
import static com.gitlab.daring.image.command.CommandUtils.parseArgs;
import static com.gitlab.daring.image.util.ExceptionUtils.throwArgumentException;
import static com.gitlab.daring.image.util.ExtStringUtils.splitAndTrim;
import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

public class CommandRegistry extends BaseComponent implements AutoCloseable {

	//TODO move to main context
	static final CommandRegistry Instance = new CommandRegistry();

	public static ScriptCommand parseCmdScript(String script) {
		return Instance.parseScript(script);
	}

	final Config cmdConfig = getConfig("commands");
	final Map<String, Command.Factory> factories = new HashMap<>();
	final Cache<String, Command> cache = buildClosableCache(config.getString("cache"));

	public CommandRegistry() {
		super("gmv.CommandRegistry");
		EnvCommands.register(this);
		TransformCommands.register(this);
		DrawCommands.register(this);
		FeaturesCommands.register(this);
		mainContext().closeEvent.onFire(this::close);
	}

	public void register(String name, Command.Factory f) {
		factories.put(name, f);
	}

	public ScriptCommand parseScript(String script) {
		List<String> ss = splitAndTrim(script, ";\n");
		List<Command> cmds = ss.stream().map(this::getCommand).collect(toList());
		return new ScriptCommand(script, cmds);
	}

	private Command getCommand(String cmdStr) {
		Command cmd = cache.getIfPresent(cmdStr);
		if (cmd == null) {
			cmd = parseCommand(cmdStr);
			if (cmd.isCacheable()) cache.put(cmdStr, cmd);
		}
		return cmd;
	}

	private Command parseCommand(String cmdStr) {
		List<String> ss = splitAndTrim(cmdStr, "()");
		String name = ss.get(0);
		String[] args = parseArgs(ss.size() > 1 ? ss.get(1) : "", getDefArgs(name));
		Command.Factory cf = factories.get(name);
		if (cf == null) throwArgumentException("Invalid command " + cmdStr);
		return cf.create(args);
	}

	private List<String> getDefArgs(String name) {
		return cmdConfig.hasPath(name) ? cmdConfig.getStringList(name) : emptyList();
	}

	@Override
	public void close() {
		cache.invalidateAll();
		cache.cleanUp();
	}

}
