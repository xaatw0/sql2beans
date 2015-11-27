package sql2bean.beans;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import org.junit.Test;

import sql2bean.sql.DataType;

public class SQLKeyValueTest {

	@Test
	public void tostring() {

		SQLKeyValue target = new SQLKeyValue();
		target.setKey("key");
		target.setValue("value");
		target.setType(DataType.Integer);
		target.addParameter(1);
		target.addParameter(3);

		assertThat(target.toString(), is("SQLKeyValue(Key:key,Value:value,Type:Integer,Param[1,3])"));
	}

	@Test
	public void equalsObject() {
		SQLKeyValue target1 = new SQLKeyValue();
		SQLKeyValue target2 = new SQLKeyValue("test");
		assertThat(target1.equals(target2), is(false));

		target1.setKey("test");
		assertThat(target1, equalTo(target2));

		target1.setType(DataType.Integer);
		assertThat(target1.equals(target2), is(false));
		target2.setType(DataType.Integer);
		assertThat(target1, equalTo(target2));

		target1.addParameter(1);
		target1.addParameter(3);
		assertThat(target1.equals(target2), is(false));
		target2.addParameter(1);
		target2.addParameter(3);
		assertThat(target1.equals(target2), is(true));
	}

}
