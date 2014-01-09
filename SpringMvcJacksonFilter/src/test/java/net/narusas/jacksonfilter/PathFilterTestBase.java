package net.narusas.jacksonfilter;

import static org.junit.Assert.*;

import java.lang.reflect.Method;
import java.util.Collection;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import org.junit.Before;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.BeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

public class PathFilterTestBase {
	@Before
	public void setUp() {
		om = new ObjectMapper();
		// om.setSerializationInclusion(Include.NON_NULL);

		FilterProvider fp = new SimpleFilterProvider().addFilter("pathFilter", pathFilter());
		om.setFilters(fp);
		om.setAnnotationIntrospector(pathFilterAnnotationInstrospector());
	}

	public void assertJson(String expectedJson, Object data) {
		try {
			String jsonString = convert(data);
			assertEquals(expectedJson, jsonString);
		} catch (JsonProcessingException e) {
			throw new IllegalArgumentException(e);
		}
	}

	protected String convert(Object data) throws JsonProcessingException {
		String jsonString = om.writeValueAsString(data);
		System.out.println(jsonString);
		return jsonString;
	}

	public JsonFilter fixture(String name) {
		try {
			Method m = getClass().getMethod(name, new Class[] {});
			JsonFilter filter = m.getAnnotation(JsonFilter.class);
			assertNotNull(filter);
			return filter;
		} catch (Exception e) {
			throw new IllegalArgumentException(e);
		}
	}

	// 필터링 과정에서 무시할 클래스 목록.
	Class<?>[] skipClasses = new Class[] { Collection.class, };
	protected ObjectMapper om;

	@Bean
	public PathFilterAnnotationInstrospector pathFilterAnnotationInstrospector() {
		PathFilterAnnotationInstrospector ins = new PathFilterAnnotationInstrospector();
		ins.setSkipClassList(skipClasses);
		return ins;
	}

	public BeanPropertyFilter pathFilter() {
		return new PathFilter();
	}

	@Getter
	@Setter
	public static class A {
		B b;
		int c;
	}

	@Getter
	@Setter
	public static class B {
		String d;
		String e;
	}

	@Getter
	@Setter
	public static class C {
		String b;

		@JsonIgnore
		int d;
	}

	@Getter
	@Setter
	public static class D1 {
		@JsonIgnore
		D2 d2;
		int e;
	}

	@Getter
	@Setter
	public static class D2 {
		String b;

		@JsonIgnore
		int f;
	}

	public D1 fixtureD1() {
		D1 d1 = new D1();
		D2 d2 = new D2();
		d1.e = 15;
		d2.b = "abc";
		d2.f = 10;
		d1.d2 = d2;
		return d1;
	}

	@Getter
	@Setter
	public static class E1 {
		E2 e2;
		int e;
	}

	@Getter
	@Setter
	public static class E2 {
		String b;
		int f;
	}

	public E1 fixtureE1() {
		E1 e1 = new E1();
		E2 e2 = new E2();
		e1.e = 15;
		e2.b = "abc";
		e2.f = 10;
		e1.e2 = e2;
		return e1;
	}

	@Data
	public static class JSendResponse {
		private Object content;

		public JSendResponse(Object content) {
			this.content = content;
		}

	}
}