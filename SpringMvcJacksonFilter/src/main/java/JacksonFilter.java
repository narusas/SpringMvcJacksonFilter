import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

class JacksonFilter implements BeanPropertyFilter {
	@Getter
	@Setter
	private FilterContext context;

	@Getter
	private String filterName;

	public JacksonFilter(String filterName, FilterContext context) {
		this.filterName = filterName;
		this.context = context;
	}

	public ObjectMapper initObjectMapper(ObjectMapper om) {
		om.setFilters(new SimpleFilterProvider().addFilter(filterName, this));
		om.setAnnotationIntrospector(new JacksonFilterIntrospector(filterName));
		return om;
	}

	@Override
	public void serializeAsField(Object bean, JsonGenerator jgen, SerializerProvider prov, BeanPropertyWriter writer)
			throws Exception {
		String propertyName = writer.getName();
		if (context.isExcludedProperty(propertyName)) {
			return;
		} else {
			writer.serializeAsField(bean, jgen, prov);
		}
	}

	@Override
	public void depositSchemaProperty(BeanPropertyWriter writer, ObjectNode propertiesNode, SerializerProvider provider)
			throws JsonMappingException {

	}

	@Override
	public void depositSchemaProperty(BeanPropertyWriter writer, JsonObjectFormatVisitor objectVisitor,
			SerializerProvider provider) throws JsonMappingException {

	}

	public int propertyDepth(JsonGenerator jgen) {
		int depth = 0;
		JsonStreamContext context = jgen.getOutputContext();
		while (context.getParent() != null) {
			depth++;
			context = context.getParent();
		}
		return depth;
	}

}