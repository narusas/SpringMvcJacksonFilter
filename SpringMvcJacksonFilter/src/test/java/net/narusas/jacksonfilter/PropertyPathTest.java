package net.narusas.jacksonfilter;

import static org.junit.Assert.*;

import org.junit.Test;

public class PropertyPathTest {

	@Test
	public void readPathName() {
		PropertyPath path = new PropertyPath(0);
		assertEquals("", path.toPath());

		path = path.after(1, "abc");
		assertEquals("abc", path.toPath());

		path = path.after(1, "def");
		assertEquals("def", path.toPath());

		path = path.after(2, "ddd");
		assertEquals("def.ddd", path.toPath());

		path = path.after(3, "eee");
		assertEquals("def.ddd.eee", path.toPath());

		path = path.after(2, "fff");
		assertEquals("def.fff", path.toPath());

		path = path.after(3, "ggg");
		assertEquals("def.fff.ggg", path.toPath());

		// 중간 뎁스 점프 3 -> 1
		// 줄어드는 뎁스 점프는 허용된다
		path = path.after(1, "aaa");
		assertEquals("aaa", path.toPath());
	}

	// 늘어나는 뎁스 점프는 중간에 사용될 프로퍼티명을 알수 없으므로 에러
	@Test(expected = IllegalArgumentException.class)
	public void testIllegalDepth() {
		PropertyPath path = new PropertyPath(0);
		assertEquals("", path.toPath());
		path = path.after(1, "aaa");
		assertEquals("aaa", path.toPath());
		path = path.after(3, "bbb");
	}

	@Test
	public void zeroToUp() {
		PropertyPath path = new PropertyPath(0);
		assertEquals("", path.toPath());
		path = path.after(3, "aaa");
		assertEquals("0에서 부터 늘어나는  path 는 허용", "aaa", path.toPath());
	}

}