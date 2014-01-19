import com.fasterxml.jackson.databind.introspect.AnnotatedClass;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;


class JacksonFilterIntrospector extends JacksonAnnotationIntrospector {
	private static final long serialVersionUID = 6820892987460288289L;
	private String filterName;

	public JacksonFilterIntrospector(String filterName) {
		this.filterName = filterName;
	}

	@Override
	public Object findFilterId(AnnotatedClass ac) {
		return filterName;
	}
}