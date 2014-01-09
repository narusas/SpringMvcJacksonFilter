package net.narusas.jacksonfilter;

import java.util.Arrays;

import net.narusas.jacksonfilter.annotations.JsonFilter;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

public class PathFilterSkipClassesTest extends PathFilterTestBase {

	@Test
	public void testSkipClasses_PageResponse() throws JsonProcessingException {
//		E1 e1 = fixtureE1();
//		Page<E1> p = new PageImpl<E1>(Arrays.asList(e1, e1));
//		PageResponse<E1> res = new PageResponse<E1>(p);
//		JacksonFilterContextHolder.updateContext(fixture("fixture1"));
//		// 2depth 에 해당하는 pagedata 도 무시 처리되어야 함. fixtures1 에 기술된 exclude 는 e1
//		// 데이터를 기준으로 나옴
//		// @formatter:off
//		assertJson("{\"status\":\"success\",\"data\":{\"content\":[" + "{\"e2\":{\"b\":\"abc\"},\"e\":15},"
//				+ "{\"e2\":{\"b\":\"abc\"},\"e\":15}"
//				+ "],\"size\":0,\"page\":0,\"numberOfElements\":2,\"totalPages\":1,\"totalElements\":2}}", res);
//		// @formatter:on
	}

	// 2depth 에 해당하는 pagedata 도 무시 처리되어야 함. fixtures1 에 기술된 exclude 는 e1 데이터를
	// 기준으로 나옴
	@Test
	public void testSkipClasses_PageResponse_1() throws JsonProcessingException {
//		E1 e1 = fixtureE1();
//		Page<E1> p = new PageImpl<E1>(Arrays.asList(e1));
//		PageResponse<E1> res = new PageResponse<E1>(p);
//		JacksonFilterContextHolder.updateContext(fixture("fixture1"));
//		// @formatter:off
//		String expected = "{\"status\":\"success\",\"data\":{\"content\":[{\"e2\":{\"b\":\"abc\"},\"e\":15}],\"size\":0,\"page\":0,\"numberOfElements\":1,\"totalPages\":1,\"totalElements\":1}}";
//		assertJson(expected, new PageResponse<E1>(p));
//		assertJson(expected, new GridResponse(p));
//
//		// @formatter:on
	}

	@Test
	public void testSkipClasses_JSendResponse() throws JsonProcessingException {
		E1 e1 = fixtureE1();
		JacksonFilterContextHolder.updateContext(fixture("fixture1"));
		assertJson("{\"status\":\"success\",\"data\":{\"e2\":{\"b\":\"abc\"},\"e\":15}}", new JSendResponse(e1));
	}

	@Test
	public void testSkipClasses_Response() throws JsonProcessingException {
//		E1 e1 = fixtureE1();
//		JacksonFilterContextHolder.updateContext(fixture("fixture1"));
//		assertJson("{\"status\":\"success\",\"data\":{\"e2\":{\"b\":\"abc\"},\"e\":15}}", new Response<E1>(e1));
	}

	@JsonFilter(excludes = "e2.f")
	public void fixture1() {
	}

}