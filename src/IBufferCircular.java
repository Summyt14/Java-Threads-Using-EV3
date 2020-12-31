public interface IBufferCircular<E> {

	public void put(E e) throws InterruptedException;
	
	public E get() throws InterruptedException;
}
