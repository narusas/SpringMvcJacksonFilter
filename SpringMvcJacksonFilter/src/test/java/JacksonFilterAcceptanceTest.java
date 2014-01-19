import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonFilterAcceptanceTest {

	private FilterContext context;
	private ObjectMapper om;

	@Before
	public void setup() {
		context = new FilterContext();
		JacksonFilter filter = new JacksonFilter("pathFilter", context);

		om = new ObjectMapper();
		filter.initObjectMapper(om);
	}

	@Test
	public void filterPropertyNone() throws JsonProcessingException {
		Employee employee = new Employee();
		employee.setAge(15);
		employee.setName("john");

		assertJson("{\"name\":\"john\",\"age\":15}", employee);
	}

	@Test
	public void filterProperty_name() throws JsonProcessingException {
		Employee employee = new Employee();
		employee.setAge(15);
		employee.setName("john");
		context.excludeProperty("name");
		assertJson("{\"age\":15}", employee);
	}

	@Test
	public void filterProperty_age() throws JsonProcessingException {
		Employee employee = new Employee();
		employee.setAge(15);
		employee.setName("john");
		context.excludeProperty("age");
		assertJson("{\"name\":\"john\"}", employee);
	}

	@Test
	public void filterProperties_both() throws JsonProcessingException {
		Employee employee = new Employee();
		employee.setAge(15);
		employee.setName("john");
		context.excludeProperty("age", "name");

		assertJson("{}", employee);
	}
	
	@Test
	public void filterCollection_array() throws JsonProcessingException {
		Job job = new Job();
		job.setAlias(new String[]{"job1", "job2"});
		job.setPays(Arrays.asList(15,20));
		
		assertJson("{\"alias\":[\"job1\",\"job2\"],\"pays\":[15,20]}", job);
		
		context.excludeProperty("alias");
		assertJson("{\"pays\":[15,20]}", job);
	}
	
	@Test
	public void filterCollection_collection() throws JsonProcessingException {
		Job job = new Job();
		job.setAlias(new String[]{"job1", "job2"});
		job.setPays(Arrays.asList(15,20));
		
		assertJson("{\"alias\":[\"job1\",\"job2\"],\"pays\":[15,20]}", job);
		
		context.excludeProperty("pays");
		assertJson("{\"alias\":[\"job1\",\"job2\"]}", job);
	}
	
	@Test
	public void filterPath() throws JsonProcessingException {
		final Employee employee = new Employee();
		employee.setAge(15);
		employee.setName("john");
		
		Department dept = new Department();
		dept.setEmployees(new HashSet<Employee>(){
			{
				add(employee);
			}
		});
		dept.setName("dept1");
		
		assertJson("{\"name\":\"dept1\",\"employees\":[{\"name\":\"john\",\"age\":15}]}", dept);
		

		context.excludePath("employees.age");
		assertJson("{\"name\":\"dept1\",\"employees\":[{\"name\":\"john\"}]}", dept);
		
	}
	
	

	void assertJson(String expectedJsonString, Object obj) throws JsonProcessingException {
		String jsonString = om.writeValueAsString(obj);
		assertEquals(expectedJsonString, jsonString);
	}

}

@Data
class Employee {
	String name;
	int age;
}

@Data
class Job {
	String[] alias;
	List<Integer> pays;
}

@Data
class Department {
	String name;
	Set<Employee> employees;
}
