package net.narusas.jacksonfilter;

import net.narusas.jacksonfilter.annotations.JsonFilter;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;


public class PathFilterExcludeTest extends PathFilterTestBase {

	@Test
	public void testSimple() throws JsonProcessingException {
		B b = new B();
		b.d = "abc";
		b.e = "def";
		A a = new A();
		a.b = b;
		a.c = 1;

		JacksonFilterContextHolder.updateContext(fixture("fixture1"));
		assertJson("{\"status\":\"success\",\"data\":{\"b\":{\"d\":\"abc\"},\"c\":1}}", new JSendResponse(a));

		JacksonFilterContextHolder.updateContext(fixture("fixture2"));
		assertJson("{\"status\":\"success\",\"data\":{\"c\":1}}", new JSendResponse(a));

		JacksonFilterContextHolder.updateContext(fixture("fixture3"));
		assertJson("{\"status\":\"success\",\"data\":{\"b\":{\"d\":\"abc\",\"e\":\"def\"}}}", new JSendResponse(a));
	}

	@Test
	public void testAll() {
		E1 e1 = fixtureE1();
		JacksonFilterContextHolder.updateContext(fixture("fixture4"));
		assertJson("{\"e2\":{\"b\":\"abc\"},\"e\":15}", e1);
	}

	@Test
	public void testAll2() {
		E1 e1 = fixtureE1();
		JacksonFilterContextHolder.updateContext(fixture("fixture5"));
		assertJson("{\"e2\":{\"b\":\"abc\"}}", e1);
	}

	@Test
	public void t2() {
		B b = new B();
		b.d = "abc";
		b.e = null;

		// null 은 제외 된다
		assertJson("{\"d\":\"abc\"}", b);
	}

	@JsonFilter(excludes = "b.e")
	public void fixture1() {
	}

	@JsonFilter(excludes = "b")
	public void fixture2() {
	}

	@JsonFilter(excludes = "c")
	public void fixture3() {
	}

	@JsonFilter(excludesForAll = "f")
	public void fixture4() {
	}

	@JsonFilter(excludesForAll = { "e", "f" })
	public void fixture5() {
	}
}

