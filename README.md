# Overview 
Hi

I like Jackson JSON Processor. It is simple and powerful tool.

But It is lack of dynamic filtering, especially bi-directional references(or infinite loop).

After 2.0, Jackson support @JsonIdentityInfo, but generated json is not usable with a web browser. It is only usable when  Jackson-to-jackson situation.

Jackson support also @JsonIgnore. Simple case, @JsonIgnore is OK. But when a java object is render to many forms , @JsonIgnore is not usable.

Jackson support @JsonView. But in my thought, presentation layer  take responsibility for render json of object, not Model. @JsonView is  intrusive.      

So I make a jackson filter plugin to dynamic filter, and integration with spring mvc(presentation layer).
  

# Simple Usage

<code>
import net.narusas.jacksonfilter.*;


FilterContext context = new SimpleFilterContext();

JacksonFilter filter = new JacksonFilter("pathFilter", context);

ObjectMapper om = new ObjectMapper();

filter.initObjectMapper(om);

Employee employee = new Employee();

employee.setAge(15);

employee.setName("john");

context.excludeProperty("name");

String jsonString = om.writeValueAsString(obj);

</code>



## Support filter context methods
### Exclude property

<code> 
excludeProperty(String... propertyNames)
</code>

Exclude target property from _ALL_ bean that is filtered by jackson.


### Exclude path
<code>
excludePath(String... paths)
</code>

Exclude target property path from _ROOT_ bean. 

Ex)

<code>
Employee e =..;<br/>
Department d = ..;<br/>
d.addEmployee(e);<br/>
...<br/>
context.excludePath("employee.age");<br/>
</code>

Rendered Json)

<code>
{"name":"depart1",employees:[{"name":"John"}]} // age is excluded
</code>


### Include property
<code>
includeProperty(String... propertyNames)
</code>

Include target property from _ALL_ bean even that property is annonated by @JsonIgnore .

And include target property from _ALL_ bean even that property is contained in exclude properties. (by excludeProperty or predefinedSet method). In other words, include has higher priority than exclude. 


### Include path
<code>
includePath(String... path)
</code>

Include target property path from _ROOT_ bean even that path is contained in exclude path(by excludePath or predefinedSet method). In other words, include has higer priority than exclude. 


### Apply predefined sets 
<code>
predefinedSet(Class<? extends PredefinedSet>... sets)
</code>

PredefinedSet has 3 methods(excludeProperties, excludePath, includeProperty).  This is use for defining reusable sets. 



# Integrate with Spring mvc

## Config
<code>
	@Configuration
	@EnableWebMvc

	public class SpringMvcConfig extends WebMvcConfigurerAdapter {


		@Override
		public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
			converters.add(mappingJacksonHttpMessageConverter());
		}
	
		/**
		 * Ajax 지원을 위한 Jackson 컨버터를 등록한다
		 * 
		 * @return
		 */
		@Bean
		public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
			MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
			converter.setObjectMapper(objectMapper());
			return converter;
		}
	
		@Bean
		public ObjectMapper objectMapper() {
			ObjectMapper om = new ObjectMapper();
			om.setFilters(new SimpleFilterProvider().addFilter("pathFilter", jacksonFilter()));
			om.setAnnotationIntrospector(new JacksonFilterIntrospector("pathFilter", filterContext()));
	
			return om;
		}
	
		@Bean
		public JacksonFilter jacksonFilter() {
			return new JacksonFilter("pathFilter", filterContext());
		}
	
		@Bean
		public FilterContext filterContext() {
			return new ThreadLocalFilterContext();
		}
	
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(jacksonFilterContextInterceptor());
		}
	
		@Bean
		public JacksonFilterContextInterceptor jacksonFilterContextInterceptor() {
			return new JacksonFilterContextInterceptor();
		}
	
		@Bean
		public FixtureController testController() {
			return new FixtureController();
		}
	
	}


</code>


## Usage
### Filter by declaration
Add @JsonFilter annotation on controller method. 

Ex)

<code>
	@RequestMapping("/do3")
	@JsonFilter(excludePath = { "employees.age" })
	public @ResponseBody
	Department do3() {
		Employee employee = new Employee();
		employee.setAge(15);
		employee.setName("john");

		Department dept = new Department();
		HashSet<Employee> employees = new HashSet<Employee>();
		employees.add(employee);
		dept.setEmployees(employees);
		dept.setName("dept1");
		return dept;
	}
</code>

### Filter by dynamic
Use  CurrentRequestFilterContext. It is handle current request based on ThreadLocal. 

<code>

	@RequestMapping("/do6")
	@JsonFilter(excludeProperty = { "age" })
	public @ResponseBody
	Employee do6(@RequestParam(value = "include", required = false) String includeOption) {
		if ("age".equals(includeOption)) {
			CurrentRequestFilterContext.getInstance().includeProperty("age");
		}

		return e;
	}
	
</code>
