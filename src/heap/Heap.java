package heap;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * This is a basic implementation of a binary priority heap.
 * 
 * A binary priority heap is a complete tree with the heap property
 * where the children of the parent are either exclusively less than
 * the parent (max) or exclusively greater than the parent (min).
 */
public class Heap<P> {
	// underlying heap implementation
	private ArrayList<P> heap;
	// defines underlying ordering
	private Comparator<P> comp;
	// initialize an empty heap
	public Heap(Comparator<P> comp) {
		heap = new ArrayList<P>();
		this.comp = comp;
	}
	
	/**
	 * Insert the element into the heap.
	 * 
	 * Ex:
	 * 
	 * [] ins 2 -> [2]
	 * [2] ins 0 -> [0, 2]
	 * [0, 2] ins -10 -> [-10, 0, 2]
	 * [-10, 0, 2] ins 1 -> [-10, 0, 2, 1]
	 * 
	 * @param p
	 * 		The element to insert.
	 */
	public void insert(P p) {
		// the element is put at the end of the list
		heap.add(p);
		// heapify up
		int i = heap.size() - 1;
		while (i > 0) {
			P elem = heap.get(i);
			// right child, even indexed
			if (i % 2 == 0) {
				P parent = heap.get(i/2 - 1);
				// swap if elem is smaller than parent
				if (comp.compare(elem, parent) < 0) {
					swap(i, i/2 - 1);
					i = i/2 - 1;
				}
			}
			// left child, odd indexed
			else {
				P parent = heap.get(i/2);
				// swap if elem is smaller than parent
				if (comp.compare(elem, parent) < 0) {
					swap(i, i/2);
					i = i/2;
				}
			}
		}
	}
	
	/**
	 * Swaps the elements at the respective indices.
	 * @param i1
	 * 		The index of the first element to swap.
	 * @param i2
	 * 		The index of the second element to swap.
	 * 
	 */
	private void swap(int i1, int i2) throws IndexOutOfBoundsException {
		P temp = heap.get(i1);
		heap.set(i1, heap.get(i2));
		heap.set(i2, temp);
	}
	
	/**
	 * Remove element at index i.
	 * 
	 * Ex:
	 * 
	 * [-11, -10, 0, 1, 0, 2, 1] remove(1) -> [-11, 0, 0, 1, 1, 2]
	 * 
	 * @param i
	 * 		Index of the element to remove. 
	 * @throws Exception
	 * 		If the index is less than 0 or greater than index of the last 
	 * 		element.
	 */
	public void remove(int i) throws Exception {
		int size = heap.size();
		if (i < 0 || i > size - 1) {
			throw new Exception("Invalid index.");
		}
		// swap the element to end of the list
		swap(i, --size);
		// remove the element (O(1))
		heap.remove(i);
		// heapify down
		while (i < size) {
			// the index of the left child
			int cl = (2 * i) + 1;
			// the index of the right child
			int cr = (2 * i) + 2;
			// the value of the parent
			P p = heap.get(i);
			// check that left child exists
			if (cl < size) {
				// value of left child
				P l = heap.get(cl);
				// check that the right child exists
				if (cr < size) {
					// value of right child 
					P r = heap.get(cr);
					// right is lower valued than the left
					// and right is lower valued than the parent
					// swap and continue to heapify down
					if (comp.compare(r, l) < 0 && comp.compare(r, p) < 0) {
						swap(cr, i);
						i = cr;
						continue;
					}
				}
				// left is lower valued than the right, if it existed
				// and left is lower valued than the parent
				// swap and continue to heapify down
				if (comp.compare(l, p) < 0) {
					swap(cl, i);
					i = cl;
					continue;
				}
				// left child and right child are not higher priority
				// than the parent
				else {
					break;
				}
			}
			// left child doesn't exist, end loop
			else {
				break;
			}
		}
	}
	
	/**
	 * Gets the highest priority value from the heap.
	 * Runs in O(1).
	 * @return
	 * 		The minimum value of the heap.
	 * @throws Exception
	 * 		If the heap is empty.
	 * 		TODO more specific exception
	 */
	public P getPriority() throws Exception {
		// Ensure that heap has an element
		if (!(heap.size() > 0)) {
			throw new Exception("Heap is empty. No min value.");
		}
		return heap.get(0);
	}
	
	/**
	 * Fully sorts the heap, in-place, but in reverse priority.
	 * Runs in O(n * log(n))
	 */
	public void sort() {
		
	}
}
