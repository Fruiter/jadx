package jadx.tests.internal.inner;

import jadx.api.InternalJadxTest;
import jadx.core.dex.nodes.ClassNode;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertThat;

public class TestAnonymousClass3 extends InternalJadxTest {

	public static class TestCls {
		public static class Inner {
			private int f;
			private double d;

			public void test() {
				new Thread() {
					@Override
					public void run() {
						int a = f--;
						p(a);

						f += 2;
						f *= 2;

						a = ++f;
						p(a);

						d /= 3;
					}

					public void p(int a) {
					}
				}.start();
			}
		}
	}

	@Test
	public void test() {
		ClassNode cls = getClassNode(TestCls.class);
		String code = cls.getCode().toString();
		System.out.println(code);

		assertThat(code, containsString(indent(4) + "public void run() {"));
		assertThat(code, containsString(indent(3) + "}.start();"));

//		assertThat(code, not(containsString("synthetic")));
//		assertThat(code, not(containsString("AnonymousClass_")));

//		assertThat(code, containsString("a = f--;"));
	}
}
