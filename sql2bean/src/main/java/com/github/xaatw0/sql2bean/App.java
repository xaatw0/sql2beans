package com.github.xaatw0.sql2bean;

import java.util.Arrays;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
    	int[] target = {1,2,3};
        System.out.println( Arrays.stream(target).count() );
    }
}
