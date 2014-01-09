package net.narusas.jacksonfilter;

import java.util.HashMap;
import java.util.Map;

import lombok.Data;

@Data
public class PropertyPath {
	PropertyPath parent;
	String name;
	int depth;// Response, Data, Property 해서 3 가 기본이 됨

	public PropertyPath(int depth) {
		this.depth = depth;
	}

	public PropertyPath after(int newDepth, String newName) {
		PropertyPath next = null;
		if (depth == newDepth) {
			this.name = newName;
			next = this;
		} else if (depth > newDepth) {
			next = parent.after(newDepth, newName);
			next.name = newName;
		} else { // depth < newDepth
			// if (depth + 1 != newDepth && depth != 0) {
			// throw new
			// IllegalArgumentException("Invalid new depth. skipped depth from "
			// +
			// depth + " to " + newDepth);
			// }
			next = new PropertyPath(newDepth);
			next.parent = this;
		}
		next.name = newName;
		return next;
	}

	public boolean isSkip(String skipPath) {
		return toPath().equals(skipPath);
	}

	public String toPath() {
		return toPath(new HashMap<PropertyPath, String>());
	}

	private String toPath(Map<PropertyPath, String> map) {
		if (map.containsKey(parent)) {
			return name;
		} else {
			if (parent != null) {
				map.put(parent, parent.name);
			}
			return parent == null ? "" + (name == null ? "" : name) : parent.toPath(map) + ("".equals(parent.toPath(map)) ? "" : ".")
					+ name;
		}
	}
}