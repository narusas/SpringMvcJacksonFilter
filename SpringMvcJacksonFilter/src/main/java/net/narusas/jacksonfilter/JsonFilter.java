package net.narusas.jacksonfilter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Spring MVC + Jackson2 JSON에서 Controller 에서 JSON 생성을 필터링하기 위한 어노테이션. <br>
 * <br>
 * 1. excludes - 특정 경로 제외<br>
 * Json 변환시 별다른 옵션이 없을때는 모든 속성이 JSON 으로 변경된다. 그중에서 특정 경로의 속성을 제외 시키고 싶을때는
 * excludes 에 속성경로를 지정해 주면 JSON 생성에서 제외된다.<br>
 * <code>@JsonFilter(excludes={"팔로우태그목록.팔로우해쉬태그.해쉬태그그룹매핑목록"})</code><br>
 * <br>
 * 2. excludesForAll - 특정 속성 제외 <br>
 * 모든 객체에 대해서 특정 이름의 속성을 JSON 에서 제외한다.<br>
 * <code>@JsonFilter(excludesForAll={"생성일시", "생성프로그램ID", "생성자", "생성자명", "변경일시", "변경프로그램ID", "변경자", "변경자명"})></code>
 * <br>
 * <br>
 * 3. excludeSet - 특정 경로 집합 제외<br>
 * 자주 사용되는 제외 경로를 클래스로 지정해서 한번에 제외 시킬수 있다.<br>
 * <code>@JsonFilter(excludesSet={PredefinedSets.AUDITABLE})></code><br>
 * <br>
 * 4. excludeForAllSet - 특정 속성 집합 제외<br>
 * 자주 사용되는 제외 속성를 클래스로 지정해서 한번에 제외 시킬수 있다.<br>
 * <code>@JsonFilter(excludesSet={PredefinedSets.AUDITABLE})></code><br>
 * <br>
 * 5. includes/includeSet - excludeSet 을 통해 제외된 경로중 일부를 다시 JSON 생성에 포함시킨다.<br>
 * <code>@JsonFilter(excludesSet={PredefinedSets.AUDITABLE},  includes={""})></code>
 * <br>
 * <br>
 * 6. includesForAll/includeForAllSet - excludeForAllSet 을 통해 제외된 속성중 일부를 다시
 * JSON 생성에 포함시킨다.<br>
 * <code>@JsonFilter(excludesForAllSet={PredefinedSets.AUDITABLE},  includesForAll={"변경일시","변경자명"})></code>
 * <br>
 * <br>
 * 
 * 적용 우선순위는 다음과 같다. 6,5,4,3,2,1 <br>
 * <br>
 * 우선순위가 의미하는 바는 include 는 exclude 를 보조하는 수단으로 사용해야 한다는 것이다.
 * 
 * @author narusas
 * 
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface JsonFilter {
	String[] includes() default {};

	String[] excludes() default {};

	String[] includesForAll() default {};

	String[] excludesForAll() default {};

	Class<? extends PredefinedSet>[] includesSet() default {};

	Class<? extends PredefinedSet>[] excludesSet() default {};

	Class<? extends PredefinedSet>[] includesForAllSet() default {};

	Class<? extends PredefinedSet>[] excludesForAllSet() default {};
}