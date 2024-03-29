/*
 * Igor Pieters
 * UCID 30061116
 * Assignment 2 - CPSC 501
 * Fall 2019
 * 
 * Class reflective inspector that retrieves information of classes it is reflecting on. 
 * 
 * */
package src;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Inspector {

	public void inspect(Object obj, boolean recursive) {
		Class c = obj.getClass();
		inspectClass(c, obj, recursive, 0);
	}

	public void inspectClass(Class c, Object obj, boolean recursive, int depth) {
		inspectClassName(c);
		inspectSuperClass(c, obj, recursive, depth);
		inspectInterface(c, obj, recursive, depth);
		inspectConstructor(c, obj, depth);
		inspectMethods(c, depth);
		inspectFields(c, obj, recursive, depth);
	}

	public void inspectClassName(Class c) {
		printHelper("Class Name [" + c.getName() + "]", 0);
	}
	public void inspectSuperClass(Class c, Object obj, boolean recursive, int depth) {

		if (c.equals(Object.class))
			return;

		Class superClass = c.getSuperclass();

		if (superClass != null) {
			printHelper("SuperClass [" + superClass.getName() + "]", depth);
			inspectClass(superClass, obj, recursive, depth + 1);
		} else
			printHelper("There is no super class", depth);

	}

	public void inspectInterface(Class c, Object obj, boolean recursive, int depth) {

		Class[] interfaces = c.getInterfaces();

		for (Class inter : interfaces) {
			printHelper("Interface [" + inter.getName() + "]", depth);
			inspectClass(inter, obj, recursive, depth + 1);
		}
	}

	public void inspectConstructor(Class c, Object obj, int depth) {

		Constructor[] constructors = c.getDeclaredConstructors();

		for (Constructor constructor : constructors) {
			printHelper("Constructor [" + constructor.getName() + "]", depth);

			Class[] parameters = constructor.getParameterTypes();
			for (Class parameter : parameters)
				printHelper("Parameter [" + parameter.getName() + "]", depth + 1);

			printHelper("Modifier [" + Modifier.toString(constructor.getModifiers()) + "]", depth + 1);
		}
	}

	public void inspectMethods(Class c, int depth) {

		Method[] methods = c.getDeclaredMethods();

		for (Method method : methods) {
			printHelper("Method [" + method.getName() + "]", depth);

			Class[] exceptions = method.getExceptionTypes();
			for (Class exception : exceptions)
				printHelper("Exceptions [" + exception.getName() + "]", depth + 1);

			Class[] parameters = method.getParameterTypes();
			for (Class parameter : parameters)
				printHelper("Parameter [" + parameter.getName() + "]", depth + 1);

			printHelper("Return [" + method.getReturnType().getName() + "]", depth + 1);
			printHelper("Modifier [" + Modifier.toString(method.getModifiers()) + "]", depth + 1);
		}
	}

	public void inspectFields(Class c, Object obj, boolean recursive, int depth) {

		Field[] fields = c.getDeclaredFields();

		for (Field field : fields) {
			printHelper("Field Name [" + field.getName() + "]", depth);
			printHelper("Type [" + field.getType().getName() + "]", depth + 1);
			printHelper("Modifier [" + Modifier.toString(field.getModifiers()) + "]", depth + 1);

			field.setAccessible(true);

			Object value = null;
			try {
				value = field.get(obj);
			} catch (IllegalAccessException e) {
				System.out.println("Unable to access field");
			}

			if (value == null)
				printHelper("Value [" + null + "]", depth + 1);
			else if (field.getType().isArray())
				inspectArray(field.getType(), value, recursive, depth);
			else if (field.getType().isPrimitive())
				printHelper("Value [" + value + "]", depth + 1);
			else
				inspectReferenceValue(recursive, depth, value);
		}
	}

	public void inspectArray(Class c, Object obj, boolean recursive, int depth) {

		Class componentType = c.getComponentType();
		printHelper("Component Type [" + componentType.getName() + "]", depth);
		printHelper("Array Length [" + Array.getLength(obj) + "]", depth);

		for (int i = 0; i < Array.getLength(obj); i++) {
			Object object = Array.get(obj, i);

			if (object == null)
				printHelper("Value [" + i + "] = null", depth + 1);
			else if (componentType.isPrimitive())
				printHelper("Value [" + i + "] = " + object.getClass().getName(), depth + 1);
			else if (componentType.isArray())
				inspectArray(object.getClass(), object, recursive, 1);
			else
				inspectReferenceValue(recursive, depth, object);

		}
		System.out.println();
	}

	private void inspectReferenceValue(boolean recursive, int depth, Object object) {
		if (recursive)
			inspectClass(object.getClass(), object, recursive, depth + 1);
		else
			printHelper("Reference Value [" + object.getClass().getName() + "@" + object.hashCode() + "]", depth);

	}

	public void printHelper(String message, int indent) {
		for (int i = 0; i < indent; i++)
			System.out.print("\t");

		System.out.println(message);
	}
}
