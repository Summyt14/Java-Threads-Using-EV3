package Buffer;

public interface IBufferCircularGUI {
	
	void setText(int pos, String text);

	void setIndexPut(int pos);

	void setIndexGet(int pos);
	
	void displayNumberOfItems(int numberOfItems);
	
	void dispose();

	boolean isVisible();
}
