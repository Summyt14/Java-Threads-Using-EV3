public interface IBufferCircularGUI {
	
	public void setText(int pos, String text);

	public void setIndexPut(int pos);

	public void setIndexGet(int pos);
	
	public void displayNumberOfItems(int numberOfItems);
	
	public void dispose();
}
