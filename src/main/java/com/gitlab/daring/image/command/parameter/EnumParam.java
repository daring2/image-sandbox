package com.gitlab.daring.image.command.parameter;

import static com.gitlab.daring.image.util.EnumUtils.findEnum;

public class EnumParam<T extends Enum<T>>  extends CommandParam<T> {

	public Class<T> enumClass;

	public EnumParam(Class<T> cl, String sv) {
		super(sv, "");
	}

	@Override
	public T parseValue(String sv) {
		return findEnum(enumClass, sv);
	}

}
