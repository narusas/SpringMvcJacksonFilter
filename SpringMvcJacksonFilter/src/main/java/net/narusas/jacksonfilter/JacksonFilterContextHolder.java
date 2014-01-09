package net.narusas.jacksonfilter;

import net.narusas.jacksonfilter.annotations.JsonFilter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JacksonFilterContextHolder {
	static ThreadLocal<JacksonFilterContext> holder = new ThreadLocal<JacksonFilterContext>();

	public static JacksonFilterContext currentContext() {
		return holder.get();
	}

	public static void setContext(JacksonFilterContext context) {
		holder.set(context);
	}

	public static void clearContext() {
		holder.remove();
	}

	public static void updateContext(JsonFilter filter) {
		setContext(new JacksonFilterContext(filter));
	}
}