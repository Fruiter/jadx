package jadx.tests.internal.loops;

import jadx.api.InternalJadxTest;
import jadx.core.dex.nodes.ClassNode;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class TestLoopDetection3 extends InternalJadxTest {

	public static class TestCls {

		private void test(TestCls parent, int pos) {
			Object item;
			while (--pos >= 0) {
				item = parent.get(pos);
				if (item instanceof String) {
					func((String) item);
					return;
				}
			}
		}

		private Object get(int pos) {
			return null;
		}

		private void func(String item) {
		}
	}

	@Test
	public void test() {
		ClassNode cls = getClassNode(TestCls.class);
		String code = cls.getCode().toString();
		System.out.println(code);

		assertThat(code, containsString("while"));
		// TODO
		// assertThat(code, containsString("while (--pos >= 0) {"));
	}
}
