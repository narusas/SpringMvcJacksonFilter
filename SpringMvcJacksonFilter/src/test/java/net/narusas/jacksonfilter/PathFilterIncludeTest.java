package net.narusas.jacksonfilter;

import net.narusas.jacksonfilter.annotations.JsonFilter;

import org.junit.Test;

public class PathFilterIncludeTest extends PathFilterTestBase {

	@Test
	public void handleJsongIgnore() {
		C c = new C();
		c.b = "abc";
		c.d = 10;
		JacksonFilterContextHolder.clearContext();
		// "@JsonIgnore 가 있기 때문에  render 에서 제외되어야함",
		assertJson("{\"b\":\"abc\"}", c);

	}

	@Test
	public void include_JsongIgnore() {
		C c = new C();
		c.b = "abc";
		c.d = 10;
		JacksonFilterContextHolder.updateContext(fixture("fixture4"));
		// JsonFilter 에 include 되었기 때문에 Redner 에 포함되어야 함
		assertJson("{\"b\":\"abc\",\"d\":10}", c);
	}

	@Test
	public void handle_child_ignore() {
		D1 d1 = fixtureD1();
		JacksonFilterContextHolder.clearContext();
		assertJson("{\"e\":15}", d1);
	}

	@Test
	public void include_child_ignore() {
		D1 d1 = fixtureD1();
		JacksonFilterContextHolder.updateContext(fixture("fixture5"));
		assertJson("{\"d2\":{\"b\":\"abc\"},\"e\":15}", d1);
	}

	@Test
	public void include_child_ignore_child() {
		D1 d1 = fixtureD1();
		JacksonFilterContextHolder.updateContext(fixture("fixture6"));
		assertJson("{\"d2\":{\"b\":\"abc\",\"f\":10},\"e\":15}", d1);
	}

	@Test
	public void include_child_ignore_child_and_all() {
		D1 d1 = fixtureD1();
		JacksonFilterContextHolder.updateContext(fixture("fixture7"));
		assertJson("{\"d2\":{\"b\":\"abc\",\"f\":10},\"e\":15}", d1);
	}

	@JsonFilter(includes = "d")
	public void fixture4() {
	}

	@JsonFilter(includes = "d2")
	public void fixture5() {
	}

	@JsonFilter(includes = { "d2", "d2.f" })
	public void fixture6() {
	}

	@JsonFilter(includes = { "d2" }, includesForAll = { "f" })
	public void fixture7() {
	}

}