package jadx.tests.internal.variables;

import jadx.api.InternalJadxTest;
import jadx.core.dex.nodes.ClassNode;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class TestVariables3 extends InternalJadxTest {

	public static class TestCls {
		String test(Object s) {
			int i;
			if (s == null) {
				i = 2;
			} else {
				i = 3;
				s = null;
			}
			return s + " " + i;
		}
	}

	@Test
	public void test() {
		ClassNode cls = getClassNode(TestCls.class);
		String code = cls.getCode().toString();
		System.out.println(code);

		assertThat(code, containsString("int i;"));
		assertThat(code, containsString("i = 2;"));
		assertThat(code, containsString("i = 3;"));
		assertThat(code, containsString("s = null;"));
		assertThat(code, containsString("return s + \" \" + i;"));
	}
}
