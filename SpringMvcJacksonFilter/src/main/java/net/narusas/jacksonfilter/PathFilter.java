package net.narusas.jacksonfilter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;

public class PathFilter implements BeanPropertyFilter {
	@Override
	public void serializeAsField(Object bean, JsonGenerator jgen, SerializerProvider prov, BeanPropertyWriter writer) throws Exception {
		if (bean == null) {
			return;
		}
		if (JacksonFilterContextHolder.currentContext() == null) {
			writer.serializeAsField(bean, jgen, prov);
			return;
		}

		int depth = findDepth(jgen);
		String propertyName = writer.getName();
		JacksonFilterContextHolder.currentContext().after(depth, propertyName);
		if (JacksonFilterContextHolder.currentContext().include()) {
			writer.serializeAsField(bean, jgen, prov);
		}
	}

	private int findDepth(JsonGenerator jgen) {
		int depth = 0;
		JsonStreamContext ctxt = jgen.getOutputContext();
		while (ctxt != null) {
			ctxt = ctxt.getParent();
			depth++;
		}
		return depth;
	}

	@Override
	public void depositSchemaProperty(BeanPropertyWriter writer, ObjectNode propertiesNode, SerializerProvider provider)
			throws JsonMappingException {

		writer.depositSchemaProperty(propertiesNode, provider);
	}

	@Override
	public void depositSchemaProperty(BeanPropertyWriter writer, JsonObjectFormatVisitor objectVisitor, SerializerProvider provider)
			throws JsonMappingException {
		writer.depositSchemaProperty(objectVisitor);
	}
}