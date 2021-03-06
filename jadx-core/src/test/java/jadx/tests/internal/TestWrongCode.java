package jadx.tests.internal;

import jadx.api.InternalJadxTest;
import jadx.core.dex.nodes.ClassNode;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class TestWrongCode extends InternalJadxTest {

	public static class TestCls {
		private int test() {
			int[] a = null;
			return a.length;
		}

		@SuppressWarnings("empty")
		private int test2(int a) {
			if (a == 0);
			return a;
		}
	}

	@Test
	public void test() {
		ClassNode cls = getClassNode(TestCls.class);
		String code = cls.getCode().toString();
		System.out.println(code);

		assertThat(code, not(containsString("return false.length;")));
		assertThat(code, containsString("return null.length;"));

		assertThat(code, containsString("return a == 0 ? a : a;"));
	}
}
