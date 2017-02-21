package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.command.CommandScript;
import com.gitlab.daring.image.command.parameter.CommandParam;
import com.gitlab.daring.image.command.parameter.IntParam;
import com.gitlab.daring.image.command.parameter.StringParam;
import com.gitlab.daring.image.common.BaseComponent;
import com.gitlab.daring.image.template.TemplateMatcher;
import com.typesafe.config.Config;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.List;

import static java.util.Arrays.asList;

@NotThreadSafe
class SealCheckService extends BaseComponent {

	final StringParam sampleFile = newStringParam("sampleFile", "Образец");
	final StringParam targetFile = newStringParam("targetFile", "Снимок");
	final IntParam objSize = new IntParam("0:Размер объекта:0-100").bind(config, "objSize");
	final IntParam winOffset = new IntParam("0:Смещение:0-10").bind(config, "winOffset");

	final TemplateMatcher matcher = new TemplateMatcher(getConfig("matcher"));

	CommandScript script;

	public SealCheckService(Config c) {
		super(c);
	}

	private StringParam newStringParam(String path, String name) {
		return new StringParam(":" + name).bind(config, path);
	}

	List<CommandParam<?>> getParams() {
		return asList(sampleFile, targetFile, objSize, winOffset);
	}

	public void setScript(CommandScript script) {
		this.script = script;
	}

	public void check() {
		new CheckTask(this).run();
	}

}