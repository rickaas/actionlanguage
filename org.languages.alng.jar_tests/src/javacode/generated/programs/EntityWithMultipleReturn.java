package javacode.generated.programs;

import org.languages.alng.runtime.*;
import org.languages.alng.runtime.annotations.ActionLanguageEntity;
import org.languages.alng.runtime.annotations.ActionLanguageEntityField;
import org.languages.alng.runtime.annotations.ActionLanguageEntityFunction;

// Main entry point
public class EntityWithMultipleReturn {
	public static void main(String[] args) {
		// System.out.println(42); // the answer to the universe...
		ApplicationEnvironment.init();
		GlobalFunctions.create();

		// start the main function
		GlobalFunctions.instance.bar();

		// System.exit(0);
	}

	// entity definitions

	// Entity User
	@ActionLanguageEntity
	public static class User {
		// entity properties

		@ActionLanguageEntityField(name = "name")
		private String name;

		public String get_name() {
			return name;
		}

		public void set_name(String name) {
			this.name = name;
		}

		@ActionLanguageEntityField(name = "password")
		private String password;

		public String get_password() {
			return password;
		}

		public void set_password(String password) {
			this.password = password;
		}

		// entity functions
		// function foo
		@ActionLanguageEntityFunction(name = "foo")
		public void foo(String arg1, Integer arg2, User arg3) {
			DebugFunctions.enter("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
							"9,4,18,4", "foo");
			DebugFunctions
					.var("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
							"9,4,18,4", "arg1", arg1);
			DebugFunctions
					.var("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
							"9,4,18,4", "arg2", arg2);
			DebugFunctions
					.var("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
							"9,4,18,4", "arg3", arg3);
			try {
				DebugFunctions
						.step("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
								"10,8,10,28");
				// declaration statement
				Integer integer = 3;
				DebugFunctions
						.var("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
								"10,8,10,28", "integer", integer);
				DebugFunctions
						.step("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
								"11,8,11,37");
				// declaration statement
				Integer result = (integer * 5);
				DebugFunctions
						.var("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
								"11,8,11,37", "result", result);
				DebugFunctions
						.step("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
								"12,8,12,26");
				// declaration statement
				Boolean b = false;
				DebugFunctions
						.var("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
								"12,8,12,26", "b", b);
				DebugFunctions
						.step("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
								"13,8,17,8");
				// if-statement
				if (b) {
					DebugFunctions
							.step("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
									"14,12,14,18");
					return;

				} else {
					DebugFunctions
							.step("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
									"16,12,16,18");
					return;

				}

			} finally {
				DebugFunctions
						.exit("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
								"9,4,18,4", "foo");

			}

		}

		@ActionLanguageEntityFunction(name = "baz")
		// function baz
		public String baz() {
			DebugFunctions
					.enter("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
							"20,4,23,4", "baz");
			try {
				DebugFunctions
						.step("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
								"21,8,21,29");
				SystemFunctions.print("User:");
				DebugFunctions
						.step("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
								"22,8,22,20");
				return "BAZ";

			} finally {
				DebugFunctions
						.exit("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
								"20,4,23,4", "baz");

			}

		}

	}

	// global funtion definitions
	static class GlobalFunctions extends SystemFunctions {
		// extending SystemFunctions, which is available in the
		// org.languages.alng.runtime package
		public static GlobalFunctions instance = null;

		public static void create() {
			instance = new GlobalFunctions();
		}

		// START: global functions:
		// function bar
		public void bar() {
			DebugFunctions
					.enter("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
							"26,0,32,0", "bar");
			try {
				DebugFunctions
						.step("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
								"28,4,28,22");
				// declaration statement
				User u = new User();
				DebugFunctions
						.var("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
								"28,4,28,22", "u", u);
				DebugFunctions
						.step("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
								"30,4,30,20");
				u.foo("a", 1, u);
				DebugFunctions
						.step("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
								"31,4,31,10");
				return;

			} finally {
				DebugFunctions
						.exit("alng_scripts/testcases/programs/test004_entitywithmultiplereturn.alng",
								"26,0,32,0", "bar");

			}

		}

		// END: global functions
	}
}
