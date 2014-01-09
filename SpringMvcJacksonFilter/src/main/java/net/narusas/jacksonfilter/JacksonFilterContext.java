package net.narusas.jacksonfilter;

import net.narusas.jacksonfilter.annotations.JsonFilter;
import net.narusas.jacksonfilter.annotations.PredefinedSet;
import lombok.Data;
import lombok.Getter;

/**
 * Json Filter 로 지정된 설정값등을 담기 위한 Context 객체
 * 
 * @author narusas
 * 
 */
@Data
public class JacksonFilterContext {
	@Getter
	PropertyPath currentPath;
	String[] includes;
	String[] excludes;

	String[] includesForAll;
	String[] excludesForAll;

	Class<? extends PredefinedSet>[] includesForAllSet;
	Class<? extends PredefinedSet>[] excludesForAllSet;

	public JacksonFilterContext() {
		currentPath = new PropertyPath(0);
	}

	public JacksonFilterContext(JsonFilter filter) {
		this();
		this.includes = filter.includes();
		this.excludes = filter.excludes();

		this.includesForAll = filter.includesForAll();
		this.excludesForAll = filter.excludesForAll();

		this.includesForAllSet = filter.includesForAllSet();
		this.excludesForAllSet = filter.excludesForAllSet();
	}

	public void after(int newDepth, String newName) {
		currentPath = currentPath.after(newDepth, newName);
	}

	public boolean include() {
		if (contains(includesForAll, currentPath.getName())) {
			return true;
		}
		String path = currentPath.toPath();
		if (contains(includes, path)) {
			return true;
		}

		if (contains(excludesForAllSet, currentPath.getName()) || contains(excludesForAll, currentPath.getName())) {
			return false;
		}

		if (contains(excludes, path)) {
			return false;
		}

		return true;
	}

	private boolean contains(Class<? extends PredefinedSet>[] sets, String propertyName) {
		if (sets == null) {
			return false;
		}

		for (Class<? extends PredefinedSet> set : sets) {
			PredefinedSet predefines = PredefinedSets.find(set);
			if (predefines != null && contains(predefines.getList(), propertyName)) {
				return true;
			}
		}
		return false;
	}

	private boolean contains(String[] list, String pathOrName) {
		if (list == null) {
			return false;
		}
		for (String prop : list) {
			if (prop.equals(pathOrName)) {
				return true;
			}
		}
		return false;
	}

	public boolean containsInIncludes(String name) {
		String pathOrName = "".equals(currentPath.toPath()) ? name : currentPath.toPath() + "." + name;
		return contains(includesForAll, name) || contains(includes, pathOrName);
	}
}