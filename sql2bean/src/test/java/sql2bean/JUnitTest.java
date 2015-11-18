package sql2bean;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class JUnitTest {

	@Test
	public void junit4() {

		String test = "JUnit4";
		assertThat( test, is("JUnit4"));
	}

	@Test
	public void java8(){

		int[] target = {1, 3, 6};
		assertThat(Arrays.stream(target).count(), is(3L));
	}
}
