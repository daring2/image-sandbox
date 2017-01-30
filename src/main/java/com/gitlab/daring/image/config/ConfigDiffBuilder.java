package com.gitlab.daring.image.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;
import com.typesafe.config.ConfigValueType;
import java.util.Map;

public class ConfigDiffBuilder {

	public Config build(Config c1, Config c2) {
		Config rc = c1;
		for (Map.Entry<String, ConfigValue> me : c1.root().entrySet()) {
			String p = me.getKey();
			ConfigValue cv = me.getValue();
			if (!c2.hasPath(p)) continue;
			if (cv.equals(c2.getValue(p))) {
				rc = rc.withoutPath(p);
			} else if (cv.valueType() == ConfigValueType.OBJECT) {
				Config sc = build(c1.getConfig(p), c2.getConfig(p));
				rc = rc.withValue(p, sc.root());
			}
		}
		return rc;
	}

}
