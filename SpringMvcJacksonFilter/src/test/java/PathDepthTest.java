import static org.junit.Assert.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Data;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsonFormatVisitors.JsonObjectFormatVisitor;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

/**
 * Survey: How jackson handle nested property?
 * 
 * @author narusas
 * 
 */
public class PathDepthTest {
	private ObjectMapper om;
	private JacksonFilter filter;

	@Before
	public void setup() {
		om = new ObjectMapper();
		
		filter = new JacksonFilter(null, null);
	}

	@Test
	public void basicDepthTest() throws JsonProcessingException {
		A a = new A();
		B b = new B();
		C c = new C();
		D d = new D();

		c.setName("john");
		c.list.add(d);
		a.setB(b);
		b.setC(c);
		
		om.setAnnotationIntrospector(new JacksonFilterIntrospector("test"));
		om.setFilters(new SimpleFilterProvider().addFilter("test", new BasicPropertyFilterTest()));

		assertEquals("{\"age\":null,\"b\":{\"sex\":true,\"c\":{\"name\":\"john\",\"list\":[{\"dept\":null}]}}}",
				om.writeValueAsString(a));
	}

	class BasicPropertyFilterTest extends PropertyFilterAdopter {
		@Override
		public void serializeAsField(Object bean, JsonGenerator jgen, SerializerProvider prov, BeanPropertyWriter writer)
				throws Exception {
			String propertName = writer.getName();
			System.out.println(propertName);
			int depth = filter.propertyDepth(jgen);

			if ("age".equals(propertName)) { // A's property
				assertEquals(1, depth);
			}

			if ("b".equals(propertName)) { // A's property
				assertEquals(1, depth);
			}

			if ("sex".equals(propertName)) { // B's property
				assertEquals(2, depth);
			}

			if ("c".equals(propertName)) { // B's property
				assertEquals(2, depth);
			}
			if ("name".equals(propertName)) { // C's property
				assertEquals(3, depth);
			}
			if ("list".equals(propertName)) { // C's property
				assertEquals(3, depth);
			}

			if ("dept".equals(propertName)) { // D's property
				assertEquals("Collection is also count as depth, but I can't handle via serializeAsField method", 5,
						depth);
			}

			writer.serializeAsField(bean, jgen, prov);
		}
	}

	@Data
	public static class A {
		Integer age;
		B b;
	}

	@Data
	public static class B {
		Boolean sex = true;
		C c;
	}

	@Data
	public static class C {
		String name;
		List<D> list = new ArrayList<D>();
	}

	@Data
	public static class D {
		String dept;
	}
	
	@Test
	public void crossReferenceDepthTest() throws JsonProcessingException {
		Cross1 c1 = new Cross1();
		Cross2 c2 = new Cross2();
		c1.setC2(c2);;
		c2.setC1(c1);
		
		
		System.out.println(om.writeValueAsString(c1));		
		
	}
	
	@Test
	public void parseBiDirectionalReference() throws JsonParseException, JsonMappingException, IOException {
		String src = "{\"@id\":1,\"name\":\"cross1\",\"c2\":{\"@id\":2,\"age\":15,\"c1\":1}}";
		Cross1 c1 = om.readValue(src, Cross1.class);
		assertEquals("cross1",c1.getName());
		assertNotNull(c1.getC2());
		assertEquals(Integer.valueOf(15), c1.getC2().getAge());
		assertEquals(c1, c1.getC2().getC1());
		
	}
	
	@Data
	@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
	public static class Cross1 {
		String name = "cross1";
		Cross2 c2;
	}
	
	@Data
	@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
	public static class Cross2 {
		Integer age = 15;
		Cross1 c1;
	}

}
