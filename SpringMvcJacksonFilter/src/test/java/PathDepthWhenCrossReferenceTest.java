import static org.junit.Assert.*;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;


public class PathDepthWhenCrossReferenceTest implements BeanPropertyFilter {

	@Test
	public void test() {
		fail("Not yet implemented");
	}

	@Override
	public void serializeAsField(Object bean, JsonGenerator jgen, SerializerProvider prov, BeanPropertyWriter writer)
			throws Exception {
		
	}

	@Override
	public void depositSchemaProperty(BeanPropertyWriter writer, ObjectNode propertiesNode, SerializerProvider provider)
			throws JsonMappingException {
		
	}

	@Override
	public void depositSchemaProperty(BeanPropertyWriter writer, JsonObjectFormatVisitor objectVisitor,
			SerializerProvider provider) throws JsonMappingException {
		// TODO Auto-generated method stub
		
	}

}
