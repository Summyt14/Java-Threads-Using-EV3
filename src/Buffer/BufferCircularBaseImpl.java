package Buffer;

import java.util.concurrent.atomic.AtomicInteger;

public abstract class BufferCircularBaseImpl<E> 
		implements IBufferCircular<E> {
	
	protected final E[] elements;
	
	protected int idxPut;
	protected int idxGet;
	
	protected final AtomicInteger numElements;
	
	protected final IBufferCircularGUI gui;
	
	@SuppressWarnings("unchecked")
	public BufferCircularBaseImpl(int size, IBufferCircularGUI gui) {
		this.gui = gui;
		
		this.elements = (E[])new Object[size];
		
		this.numElements = new AtomicInteger(0);
		this.idxPut = this.idxGet = 0;
		
		this.gui.setIndexPut( this.idxPut );
		this.gui.setIndexGet( this.idxGet );
		this.gui.displayNumberOfItems( 0 );
	}
}
