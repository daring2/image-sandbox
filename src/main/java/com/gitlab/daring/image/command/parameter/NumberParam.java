package com.gitlab.daring.image.command.parameter;

import static org.apache.commons.lang3.StringUtils.split;

public abstract class NumberParam<T extends Number> extends CommandParam<T> {

    public T minValue;
    public T maxValue;

    public NumberParam(String sv, String sp) {
        super(sv, sp);
        parseSpec();
    }

    protected void parseSpec() {
        String[] ss = split(spec, "-");
        minValue = parseValue(ss[0]);
        maxValue = parseValue(ss[1]);
    }

    public abstract void setNumValue(Number v);

    public double pv() {
        return v.doubleValue() * 0.01;
    }

}
