package com.ioc.test;

import com.ioc.application.IOCDemo;
import com.ioc.entry.Car;
import com.ioc.entry.Computer;
import com.ioc.entry.Wheel;

public class IOCTest {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String location = IOCDemo.class.getClassLoader().getResource("resource/test.xml").getFile();
//		System.out.println(location);
		IOCDemo demo = new IOCDemo(location);
		Wheel wheel = (Wheel) demo.getBean("wheel");
		System.out.println(wheel);
		Car car = (Car) demo.getBean("car");
		System.out.println(car);
		Computer computer = (Computer) demo.getBean("computer");
		System.out.println(computer);
	}

}

/**
 * result : Wheel [brand=Michelin, specification=265/60 R18] Car [name=Mercedes
 * Benz G 500, wheel=Wheel [brand=Michelin, specification=265/60 R18],
 * length=4717mm, width=1855mm, height=1949mm] Computer [name=拯救者 Y7000,
 * weight=2.3kg, size=15.6英寸]
 **/