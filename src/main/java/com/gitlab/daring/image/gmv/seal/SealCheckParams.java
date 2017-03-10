package com.gitlab.daring.image.gmv.seal;

import com.gitlab.daring.image.command.parameter.CommandParam;
import com.gitlab.daring.image.command.parameter.EnumParam;
import com.gitlab.daring.image.command.parameter.FileParam;
import com.gitlab.daring.image.command.parameter.IntParam;
import com.gitlab.daring.image.common.BaseComponent;
import com.typesafe.config.Config;

import java.util.List;

import static java.util.Arrays.asList;

class SealCheckParams extends BaseComponent {

    final Config c = config;
    final FileParam sampleFile = newFileParam("sampleFile", "Образец");
    final FileParam shotFile = newFileParam("shotFile", "Снимок");
    final FileParam markerFile = newFileParam("markerFile", "Маркер");
    final IntParam objSize = new IntParam("0:Размер объекта:0-100").bind(c, "objSize");
    final EnumParam<FindMethod> findMethod = new EnumParam<>(FindMethod.class, "simple:Метод").bind(c, "findMethod");
    final boolean fullAffine = c.getBoolean("fullAffine");
    final IntParam winOffset = new IntParam("0:Смещение:0-10").bind(c, "winOffset");

    public SealCheckParams(Config c) {
        super(c);
    }

    private FileParam newFileParam(String path, String name) {
        return new FileParam(":" + name).bind(c, path);
    }

    List<CommandParam<?>> getParams() {
        return asList(sampleFile, shotFile, markerFile, objSize, findMethod, winOffset);
    }

}
