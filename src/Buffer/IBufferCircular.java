package Buffer;

import java.util.concurrent.atomic.AtomicInteger;

public interface IBufferCircular<E> {

	void put(E e) throws InterruptedException;
	
	E get() throws InterruptedException;

	int getIdMsg();

	AtomicInteger getNumElements();
}
