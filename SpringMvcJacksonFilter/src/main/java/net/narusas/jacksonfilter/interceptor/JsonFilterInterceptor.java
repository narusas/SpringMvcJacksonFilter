package net.narusas.jacksonfilter.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.Getter;
import lombok.Setter;
import net.narusas.jacksonfilter.JacksonFilterContextHolder;
import net.narusas.jacksonfilter.annotations.JsonFilter;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Setter
@Getter
public class JsonFilterInterceptor extends HandlerInterceptorAdapter {
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HandlerMethod method = (HandlerMethod) handler;
		JsonFilter filter = method.getMethodAnnotation(JsonFilter.class);
		if (filter == null) {
			JacksonFilterContextHolder.clearContext();
		} else {
			JacksonFilterContextHolder.updateContext(filter);
		}
		return super.preHandle(request, response, handler);
	}

}