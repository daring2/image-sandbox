package com.gitlab.daring.image.config

import com.typesafe.config.Config
import com.typesafe.config.ConfigValueType

class ConfigDiffBuilder {

    fun build(c1: Config, c2: Config): Config {
        var rc = c1
        for ((p, cv) in c1.root()) {
            if (!c2.hasPath(p)) continue
            if (cv == c2.getValue(p)) {
                rc = rc.withoutPath(p)
            } else if (cv.valueType() == ConfigValueType.OBJECT) {
                val sc = build(c1.getConfig(p), c2.getConfig(p))
                rc = if (sc.isEmpty) rc.withoutPath(p) else rc.withValue(p, sc.root())
            }
        }
        return rc
    }

}