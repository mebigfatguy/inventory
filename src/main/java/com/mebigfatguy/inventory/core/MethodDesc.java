package com.mebigfatguy.inventory.core;

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MethodDesc {

	private String clsName;
	private String methodName;
	private String description;
	
	public MethodDesc(String clsName, String methodName, String description) {
		this.clsName = clsName;
		this.methodName = methodName;
		this.description = description;
	}

	public String getClsName() {
		return clsName;
	}

	public String getMethodName() {
		return methodName;
	}

	public String getDescription() {
		return description;
	}

	@Override
	public int hashCode() {
		return Objects.hash(clsName, description, methodName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MethodDesc other = (MethodDesc) obj;
		return Objects.equals(clsName, other.clsName) && Objects.equals(description, other.description)
				&& Objects.equals(methodName, other.methodName);
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}

}
