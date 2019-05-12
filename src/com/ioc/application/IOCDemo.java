package com.ioc.application;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/*
 * IOC容器实现类
 */
public class IOCDemo {
	private Map<String, Object> beanmap = new HashMap<String, Object>();

	public IOCDemo(String location) throws Exception {
		loadBeans(location);
	}

	/*
	 * 加载配置文件方法
	 */
	private void loadBeans(String location) throws Exception {
		// 读取配置文件
		InputStream is = new FileInputStream(location);
		// 读入.xml文档需要一个DocumentBuilder对象
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = factory.newDocumentBuilder();
		// 通过java IO 流的读取文件
		Document document = docBuilder.parse(is);
		Element root = document.getDocumentElement();
		NodeList nodes = root.getChildNodes();

		// 遍历bean标签
		for (int i = 0; i < nodes.getLength(); i++) {
			Node node = nodes.item(i);
			if (node instanceof Element) {
				Element e = (Element) node;
				String id = e.getAttribute("id");
				String classname = e.getAttribute("class");

				// 加载beanClass
				Class beanClass = null;
				beanClass = Class.forName(classname);

				// 创建bean
				Object bean = beanClass.newInstance();

				// 遍历 <property> 标签
				NodeList propertiesList = e.getElementsByTagName("property");
				for (int j = 0; j < propertiesList.getLength(); j++) {
					Node propertyNode = propertiesList.item(j);
					if (propertyNode instanceof Element) {
						Element proElement = (Element) propertyNode;
						String name = proElement.getAttribute("name");
						String value = proElement.getAttribute("value");
						// 利用反射将bean相关字段f访问权限设为可访问
						Field declaredField = bean.getClass().getDeclaredField(name);
						declaredField.setAccessible(true);

						if (value != null && value.length() > 0) {
							// 把属性值填充到相关字段
							declaredField.set(bean, value);
						} else {
							String ref = proElement.getAttribute("ref");
							if (ref == null || ref.length() == 0) {
								throw new IllegalArgumentException("ref config error");
							}
							declaredField.set(bean, getBean(ref));
						}
						// 把bean注册到容器当中
						registerBean(id, bean);
					}
				}
			}
		}
	}

	public Object getBean(String name) {
		// TODO Auto-generated method stub
		Object bean = beanmap.get(name);
		if (bean == null) {
			throw new IllegalArgumentException("there is no bean with name " + name);
		}
		return bean;
	}

	public void registerBean(String id, Object bean) {
		beanmap.put(id, bean);
	}

}
