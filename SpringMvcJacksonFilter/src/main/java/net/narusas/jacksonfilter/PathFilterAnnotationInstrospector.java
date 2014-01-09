package net.narusas.jacksonfilter;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.AnnotatedMember;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;

/**
 * Jackson JSON 라이브러리는 JSON 으로 컨버팅하는 중간에 특정 클래스에 대해 어떤 필터를 사용할것인가를 후킹하는 메커니즘을
 * 제공한다. 여기서는 Skip 대상이 아닌 모든 클래스에 대해 pathFilter 필터를 사용하라는 명령을 내리게 된다.
 * 
 * @author narusas
 * 
 */
@Setter
@Getter
public class PathFilterAnnotationInstrospector extends JacksonAnnotationIntrospector {
	private static final long serialVersionUID = -8886708264656227253L;

	Class<?>[] skipClassList;

	@Override
	public Version version() {
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object findFilterId(AnnotatedClass ac) {
		if (skipClassList == null) {
			return "pathFilter";
		}
		// JSendResponse 등 Path 필터가 필요하지 않은 클래스들은 무시한다.
		for (Class clazz : skipClassList) {
			if (clazz.isAssignableFrom(ac.getRawType())) {
				// skip 목록에 포함된 것만 skip 한다
				return null;
			}
		}
		return "pathFilter";
	}

	/**
	 * <code>@JsonIgore</code> 로 지정된 속성중 @JsonFilter 의 include에 포함되면 Json
	 * Rendering 에 포함시킨다.
	 */
	@Override
	public boolean hasIgnoreMarker(AnnotatedMember m) {
		if (m != null && JacksonFilterContextHolder.currentContext() != null
				&& JacksonFilterContextHolder.currentContext().containsInIncludes(m.getName())) {
			return false;
		}
		return super.hasIgnoreMarker(m);
	}
}