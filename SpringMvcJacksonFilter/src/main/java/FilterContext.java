import java.util.HashSet;
import java.util.Set;

class FilterContext {

	Set<String> excludeProperties = new HashSet<String>();
	Set<String> excludePaths = new HashSet<String>();

	public void excludeProperty(String... propertyNames) {
		for (String propertyName : propertyNames) {
			excludeProperties.add(propertyName);
		}
	}

	public boolean isExcludedProperty(String propertyName) {
		return excludeProperties.contains(propertyName);
	}

	public void excludePath(String... paths) {
		for (String path : paths) {
			excludePaths.add(path);
		}
	}
}