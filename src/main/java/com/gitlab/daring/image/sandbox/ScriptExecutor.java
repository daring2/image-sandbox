package com.gitlab.daring.image.sandbox;

import com.gitlab.daring.image.command.CommandScript;
import com.gitlab.daring.image.concurrent.TaskExecutor;

import java.util.List;

import static com.gitlab.daring.image.util.ExtStringUtils.splitAndTrim;
import static java.util.Collections.emptyList;
import static org.apache.commons.lang3.StringUtils.replace;

class ScriptExecutor implements AutoCloseable {

	final ImageSandbox sb;
	final CommandScript cmdScript;
	final TaskExecutor executor = new TaskExecutor();

	String script = "";
	List<String> files = emptyList();

	ScriptExecutor(ImageSandbox sb) {
		this.sb = sb;
		cmdScript = sb.mp.script;
	}

	void executeAsync() {
		executor.executeAsync(this::execute);
	}

	void execute() {
		script = cmdScript.getText();
		files = splitAndTrim(sb.mp.filesParam.v, ",").toList();
		if (files.isEmpty()) files.add("");
		for (int i = 0; i < files.size(); i++) {
			cmdScript.execute(buildScript(i));
		}
		cmdScript.setText(script);
	}

	String buildScript(int i) {
		String file = files.get(i);
		String rc = script;
		if (!file.isEmpty()) rc = "read(" + file + ")\n" + rc;
		rc = replace(rc, "$file", file);
		rc = replace(rc, "$fileIndex", "" + i);
		return rc;
	}

	@Override
	public void close() {
		executor.close();
	}

}