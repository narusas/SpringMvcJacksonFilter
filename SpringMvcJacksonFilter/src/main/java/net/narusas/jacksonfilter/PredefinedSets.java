package net.narusas.jacksonfilter;

import java.util.HashMap;
import java.util.Map;

public class PredefinedSets {
//	public static class AUDITABLE extends PredefinedSet {
//		public AUDITABLE() {
//			super(new String[] { "생성일시", "생성프로그램ID", "생성자", "생성자명", "변경일시", "변경프로그램ID", "변경자", "변경자명" });
//		}
//	}
//
//	public static class CREATE_TIME_ONLY extends PredefinedSet {
//		public CREATE_TIME_ONLY() {
//			super(new String[] { "생성프로그램ID", "생성자", "생성자명", "변경일시", "변경프로그램ID", "변경자", "변경자명" });
//		}
//	}
//
//	public static class MODIFIABLE extends PredefinedSet {
//		public MODIFIABLE() {
//			super(new String[] { "생성일시", "생성프로그램ID", "생성자", "생성자명", "변경일시", "변경프로그램ID", "변경자", "변경자명" });
//		}
//	}

	static Map<Class<? extends PredefinedSet>, PredefinedSet> predefines = new HashMap<Class<? extends PredefinedSet>, PredefinedSet>();
//	static {
//		predefines.put(AUDITABLE.class, new AUDITABLE());
//		predefines.put(CREATE_TIME_ONLY.class, new CREATE_TIME_ONLY());
//		predefines.put(MODIFIABLE.class, new MODIFIABLE());
//	}

	public static PredefinedSet find(Class<? extends PredefinedSet> clazz) {
		return predefines.get(clazz);
	}
}