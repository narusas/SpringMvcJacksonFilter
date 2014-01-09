package net.narusas.jacksonfilter.annotations;

import lombok.Getter;

@Getter
public class PredefinedSet {
	String[] list;

	public PredefinedSet(String[] list) {
		this.list = list;
	}
}