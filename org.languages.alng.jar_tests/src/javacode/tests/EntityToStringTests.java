package javacode.tests;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import javacode.generated.programs.EntityWithMultipleReturn;

import org.junit.Test;
import org.languages.alng.runtime.annotations.ActionLanguageEntityField;

public class EntityToStringTests {

	@Test
	public void testToStringWithAnnotations() throws IllegalArgumentException, IllegalAccessException {
		javacode.generated.programs.EntityWithMultipleReturn.User user = new EntityWithMultipleReturn.User();
		user.set_name("MyName");
		user.set_password("MyPassword");
		Annotation[] annotations = user.getClass().getAnnotations();
		for(Annotation annotation : annotations) {
			System.out.println(annotation.annotationType());
		}
		
		for(Field f : user.getClass().getDeclaredFields()) {
			System.out.println(f.toGenericString());
			ActionLanguageEntityField ann = f.getAnnotation(ActionLanguageEntityField.class);
			String name = ann.name();
			System.out.println("FIELD ANNOTATION: " + name);
			//Object o = f.get(user); // private fields :(
			//System.out.println("O: " + o.toString());
		}
		
		for(Method m : user.getClass().getDeclaredMethods()) {
			System.out.println(m.getName());
		}
		
		Field[] fields = user.getClass().getFields();
		for(Field f : fields) {
			System.out.println("field " + f.getName());
			ActionLanguageEntityField ann = f.getAnnotation(ActionLanguageEntityField.class);
			String name = ann.name();
			System.out.println("FIELD NAME: " + name);
		}
	}
}
