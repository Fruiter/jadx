package jadx.tests.internal.trycatch;

import jadx.api.InternalJadxTest;
import jadx.core.dex.nodes.ClassNode;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.not;
import static org.junit.Assert.assertThat;

public class TestTryCatch extends InternalJadxTest {

	public static class TestCls {
		private void f() {
			try {
				Thread.sleep(50);
			} catch (InterruptedException e) {
				// ignore
			}
		}
	}

	@Test
	public void test() {
		ClassNode cls = getClassNode(TestCls.class);
		String code = cls.getCode().toString();

		assertThat(code, containsString("try {"));
		assertThat(code, containsString("Thread.sleep(50);"));
		assertThat(code, containsString("} catch (InterruptedException e) {"));
		assertThat(code, not(containsString("return")));
	}
}
