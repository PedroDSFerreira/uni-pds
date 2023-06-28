package Ex1;

import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class VectorGeneric<T> {
	private T[] vec;		
	private int nElem;	      
	private final static int ALLOC = 50;   
	private int dimVec = ALLOC;     

	@SuppressWarnings("unchecked")
	public VectorGeneric() {
		vec = (T[]) new Object[dimVec];
		nElem = 0;
	}
	
	public boolean addElem(T elem) {
		if (elem == null)
			return false;
		ensureSpace();
		vec[nElem++] = elem;
		return true;
	}

	private void ensureSpace() {
		if (nElem>=dimVec) {
			dimVec += ALLOC;
			@SuppressWarnings("unchecked")
			T[] newArray = (T[]) new Object[dimVec];
			System.arraycopy(vec, 0, newArray, 0, nElem );
			vec = newArray;
		}
	}

	public boolean removeElem(T elem) {
		for (int i = 0; i < nElem; i++) {
			if (vec[i].equals(elem)) {
				if (nElem-i-1 > 0) // not last element
					System.arraycopy(vec, i+1, vec, i, nElem-i-1 );
				vec[--nElem] = null; // libertar último objecto para o GC
				return true;
			}
		}
		return false;
	}

	public int totalElem() {
		return nElem;
	}
	
	public T getElem(int i) {
		return (T) vec[i];
	}

	public Iterator<T> iterator() {
		return (this).new VectorIterator<T>();
	}

	public ListIterator<T> listIterator() {
		return (this).new VectorListIterator<T>();
	}

	public ListIterator<T> listIterator(int idx) {
		return (this).new VectorListIterator<T>(idx);
	}

	@SuppressWarnings("unchecked")
	private class VectorIterator<K> implements Iterator<K> {
		private int idx;

		VectorIterator() {
			idx = 0;
		}

		public boolean hasNext() {
			return (idx < nElem);
		}

		public K next() {
			if (hasNext())
				return (K)VectorGeneric.this.vec[idx++];
			throw new NoSuchElementException("only " + nElem + " elements");
		}

		public void remove() {
			throw new UnsupportedOperationException("Operacao nao suportada!");
		}
	}

	@SuppressWarnings("unchecked")
	private class VectorListIterator<K> implements ListIterator<K> {
		private int idx;

		VectorListIterator() {
			idx = 0;
		}

		VectorListIterator(int idx) {
			this.idx = idx;
		}

		public void add(K e) {
		}

		public boolean hasNext() {
			return (idx < nElem);
		}

		public boolean hasPrevious() {
			return (idx > 0);
		}

		public K next() {
			if (hasNext())
				return (K)vec[++idx];
			throw new NoSuchElementException("only " + nElem + " elements");
		}

		public int nextIndex() {
			return idx++;
		}

		public K previous() {
			if (hasPrevious())
				return (K)vec[--idx];
			throw new IndexOutOfBoundsException("Out of bounds");
		}

		public int previousIndex() {
			return idx--;
		}

		public void remove() {
			throw new UnsupportedOperationException("Operacao nao suportada!");
		}

		public void set(K e) {
		}
		
	}
}
