package com.gitlab.daring.image.command.parameter;

public class ParamGroup extends StringParam {

    public ParamGroup(String name) {
        super(":" + name);
    }

}