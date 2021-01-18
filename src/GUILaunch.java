import java.awt.EventQueue;
import java.lang.reflect.InvocationTargetException;

public class GUILaunch {

	public static void main(String[] args) {
		launch();
	}

	static class MyRunnable implements Runnable {
		private GUI window;

		public GUI getWindow() {
			return this.window;
		}

		public void run() {
			this.window = new GUI();
		}
	}

	static void launch() {
		MyRunnable r = new MyRunnable();

		try {
			EventQueue.invokeAndWait(r);
		} catch (InvocationTargetException | InterruptedException e) {
			e.printStackTrace();
		}

		r.getWindow().run();
	}

}
