package com.ioc.entry;

public class Computer {
	private String name;
	private String weight;
	private String size;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "Computer [name=" + name + ", weight=" + weight + ", size=" + size + "]";
	}

}
