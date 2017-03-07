package com.gitlab.daring.image.command.parameter;

import static java.lang.Integer.parseInt;

public class IntParam extends NumberParam<Integer> {

    public IntParam(String sv, String sp) {
        super(sv, sp);
    }

    public IntParam(String sv) {
        this(sv, "");
    }

    public int posVal(int dv) {
        return v > 0 ? v : dv;
    }

    @Override
    public Integer parseValue(String sv) {
        return parseInt(sv);
    }

    @Override
    public void setNumValue(Number v) {
        setValue(v.intValue());
    }

}
