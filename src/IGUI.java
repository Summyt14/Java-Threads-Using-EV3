import java.util.concurrent.Semaphore;

public interface IGUI {
	public void acabeiDesenho();

	public boolean acabouDesenho();

	public Semaphore getMutex();

	public int getDist();

}