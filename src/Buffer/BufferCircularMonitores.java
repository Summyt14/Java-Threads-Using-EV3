package Buffer;

import java.util.concurrent.atomic.AtomicInteger;

public class BufferCircularMonitores<E> extends BufferCircularBaseImpl<E> {

	private int idMsg = 0;

	public BufferCircularMonitores(int size, IBufferCircularGUI gui) {
		super(size, gui);
	}

	@Override
	public synchronized void put(E e) throws InterruptedException {
		// Thread.sleep(1000);

		// Colocar o novo elemento na posi��o correspondente
		this.elements[this.idxPut] = e;

		// Atualizar a interface gr�fica (mostrar o elemento inserido)
		this.gui.setText(this.idxPut, e.toString());

		// Atualizar o pr�ximo �ndice de put (posi��o onde se coloca o pr�ximo elemento)
		++this.idxPut;
		this.idxPut %= this.elements.length;
		++this.idMsg;

		// Atualizar a interface gr�fica (posi��o onde se coloca o pr�ximo elemento)
		this.gui.setIndexPut(this.idxPut);

		if (this.numElements.get() < this.elements.length) {
			// Atualizar o n�mero de elementos no buffer
			this.gui.displayNumberOfItems(this.numElements.incrementAndGet());
		}

		this.notifyAll();
	}

	@Override
	public synchronized E get() throws InterruptedException {
		E result;

		// Enquanto o contentor estiver vazio temos de esperar
		while (this.numElements.get() == 0) {
			this.wait();
		}

		// Neste ponto existe um elemento para consumir

		// Obter o elemento mais antigo
		result = this.elements[this.idxGet];

		// Atualizar o pr�ximo �ndice de get (posi��o de onde se obtem o pr�ximo
		// elemento)
		++this.idxGet;
		this.idxGet %= this.elements.length;

		// Atualizar a interface gr�fica (posi��o de onde se obtem o pr�ximo elemento)
		this.gui.setIndexGet(this.idxGet);

		// Atualizar o n�mero de elementos no buffer
		this.gui.displayNumberOfItems(this.numElements.decrementAndGet());

		// Verificar se existe algum produtor bloqueado

		this.notifyAll();

		return result;
	}

	public int getIdMsg() {
		return this.idMsg;
	}

	public AtomicInteger getNumElements() {
		return this.numElements;
	}
}
