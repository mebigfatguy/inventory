package com.mebigfatguy.inventory.core;

import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class MemberDesc {

	private String clsName;
	private String memberName;
	
	public MemberDesc(String clsName, String memberName) {
		this.clsName = clsName;
		this.memberName = memberName;
	}

	public String getClsName() {
		return clsName;
	}

	public String getMemberName() {
		return memberName;
	}

	@Override
	public int hashCode() {
		return Objects.hash(clsName, memberName);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberDesc other = (MemberDesc) obj;
		return Objects.equals(clsName, other.clsName) && Objects.equals(memberName, other.memberName);
	}
	
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.SHORT_PREFIX_STYLE);
	}
}
