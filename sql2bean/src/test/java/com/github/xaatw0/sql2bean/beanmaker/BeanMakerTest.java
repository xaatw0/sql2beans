package com.github.xaatw0.sql2bean.beanmaker;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.github.xaatw0.sql2bean.sql.ColumnInfo;
import com.github.xaatw0.sql2bean.sql.DataType;

public class BeanMakerTest {

	@Test
	public void write() {

		ColumnInfo info1 = new ColumnInfo("NAME", DataType.String);
		ColumnInfo info2 = new ColumnInfo("SCHOOL_AGE", DataType.Integer);
		List<ColumnInfo> data = Arrays.asList(info1, info2);

		BeanMaker target = new BeanMaker(null);
		target.init();

		String expected =
		"package com.github.xaatw0.sql2bean.beanmaker;" + System.lineSeparator() +
		"" + System.lineSeparator() +
		"public class BeanMakerTest_Target{" + System.lineSeparator() +
		"" + System.lineSeparator() +
		"	private String name;" + System.lineSeparator() +
		"" + System.lineSeparator() +
		"	public void setName(String name){" + System.lineSeparator() +
		"		this.name = name;" + System.lineSeparator() +
		"	}" + System.lineSeparator() +
		"" + System.lineSeparator() +
		"	public String getName(){" + System.lineSeparator() +
		"		return name;" + System.lineSeparator() +
		"	}" + System.lineSeparator() +
		"" + System.lineSeparator() +
		"	private Integer schoolAge;" + System.lineSeparator() +
		"" + System.lineSeparator() +
		"	public void setSchoolAge(Integer schoolAge){" + System.lineSeparator() +
		"		this.schoolAge = schoolAge;" + System.lineSeparator() +
		"	}" + System.lineSeparator() +
		"" + System.lineSeparator() +
		"	public Integer getSchoolAge(){" + System.lineSeparator() +
		"		return schoolAge;" + System.lineSeparator() +
		"	}" + System.lineSeparator() +
		"}";


		assertThat(target.write(data, getClass().getPackage().getName(), "BeanMakerTest_Target"),is(expected));
	}

	@Test
	public void compile(){
		ColumnInfo info1 = new ColumnInfo("NAME", DataType.String);
		ColumnInfo info2 = new ColumnInfo("SCHOOL_AGE", DataType.Integer);
		List<ColumnInfo> data = Arrays.asList(info1, info2);

		BeanMaker target = new BeanMaker("target\\test-classes");
		target.init();
		target.compile(data, "com.github.xaatw0.sql2bean.beanmaker", "BeanMakerTest_Target");

		Object obj = null;

        try {
        	// インスタンスの生成
			obj = Class.forName("com.github.xaatw0.sql2bean.beanmaker.BeanMakerTest_Target").newInstance();
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}

        try {
        	// setName("テスト")で値を設定
			Method setMethod =  obj.getClass().getMethod("setName", String.class);
			setMethod.invoke(obj, "テスト");
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

        Object result = null;

        try {
        	// getName()で値を取得
			Method getMethod =  obj.getClass().getMethod("getName");
			result = getMethod.invoke(obj);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
			e.printStackTrace();
		}

        assertThat(result, is("テスト"));

	}

}
