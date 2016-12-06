package osc.diskscheduling.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Superclass for the disk scheduling algorithm.
 * Contains all globally required methods for child class.
 * @author Boey Jian Wen (014545) & Chia Kim Kim (014616)
 *
 */
public abstract class DiskScheduling {
	
	protected ArrayList<Requests> requestQueue = new ArrayList<Requests>();
	
	protected int head;
	protected int tail;
	
	/**
	 * Gets an Iterator over the input queue.
	 * The Iterator must have the head and tail within the input queue of {@code LinkedList<Integer>}.
	 * Automatically converts Integer to int and insert into working {@code LinkedList<Requests>}.
	 * Head and tail is removed before processing the working list.
	 * @param itQueue The Iterator reference.
	 */
	public DiskScheduling(Iterator<Integer> itQueue) {
		while(itQueue.hasNext()) {
			requestQueue.add(new Requests(itQueue.next().intValue()));
		}
		head = requestQueue.remove(0).cylinder;
		tail = requestQueue.remove(requestQueue.size() - 1).cylinder;
	}
	
	/**
	 * Polymorphism for 3 primitive algorithm.
	 * First Come First Serve, Scan and Look.
	 */
	public abstract void first_scan_look();
	
	/**
	 * Polymorphism for 3 enhanced algorithm from {@link osc.diskscheduling.algorithm.DiskScheduling#first_scan_look()}.
	 * Shortest Seek Time First, CScan, CLook
	 */
	public abstract void shortest_cscan_clook();
	
	/**
	 * Shows the request queue. For debug purposes only
	 */
	public void display() {
		System.out.print("[ ");
		for(int i = 0; i < requestQueue.size(); i++)
			System.out.print(requestQueue.get(i).cylinder + " ");
		System.out.println("]");
	}
	
	/**
	 * Shows the seek difference of each request in the queue. For debug purposes only.
	 */
	public void displaySeek() {
		System.out.print("[ ");
		for(int i = 0; i < requestQueue.size(); i++)
			System.out.print(requestQueue.get(i).getSeekDiff() + " ");
		System.out.println("]");
	}
	
	/**
	 * Set seek time sequentially.
	 * All seek time are unsigned.
	 */
	protected void absoluteSetSeek() {
		requestQueue.get(0).setSeekDiff(Math.abs(head - requestQueue.get(0).cylinder));
		for(int i = 1; i < requestQueue.size(); i++) {
			requestQueue.get(i).setSeekDiff(Math.abs(requestQueue.get(i).cylinder - requestQueue.get(i - 1).cylinder));
		}
	}
	
	/**
	 * Calculates the total seek time for the entire request queue.
	 * This method is directly used for FCFS algorithm.
	 * @return The total seek time
	 */
	private int totalSeek() {
		int seekSum = 0;
		
		for(int i = 0; i < requestQueue.size(); i++) {
			seekSum += requestQueue.get(i).getSeekDiff();
		}
		
		return seekSum;
	}
	
	/**
	 * Gets the modified working list.
	 * The head is the first element of the list.
	 * The total seek and tail are pushed into the end of the list.
	 * @return {@code LinkedList<Integer>} with cylinder values only.
	 */
	public LinkedList<Integer> getRequestQueue() {
		LinkedList<Integer> cylinderOnly = new LinkedList<Integer>();
		
		for(int i = 0; i < requestQueue.size(); i++) {
			cylinderOnly.add(new Integer(requestQueue.get(i).cylinder));
		}
		
		cylinderOnly.push(new Integer(head));
		cylinderOnly.push(new Integer(tail));
		cylinderOnly.push(new Integer(totalSeek()));
				
		return cylinderOnly;
	}
	

	/**
	 * MergeSort algorithm for disk scheduling algorithm which has ascending or descending pattern
	 * @param l Lower bound
	 * @param m Middle reference
	 * @param r Upper bound
	 * @param isAsc Flag for ascending or descending sort
	 */
	private void merge(int l, int m, int r, boolean isAsc) {
		int n1 = l;
		int n2 = m + 1;
		int i = l;
		Requests[] arr = new Requests[requestQueue.size()];
		
		if(isAsc) { //Sort in ascending order?
			while(n1 <= m && n2 <= r) {
				arr[i++] = (requestQueue.get(n1).getSeekDiff() <= requestQueue.get(n2).getSeekDiff()) ? //Sort in ascending
						requestQueue.get(n1++) : requestQueue.get(n2++);
			}
		}
		else {
			while(n1 <= m && n2 <= r) {
				arr[i++] = (requestQueue.get(n1).getSeekDiff() >= requestQueue.get(n2).getSeekDiff()) ? //Sort in descending
						requestQueue.get(n1++) : requestQueue.get(n2++);
			}
		}
		
		while(n1 <= m)
			arr[i++] = requestQueue.get(n1++);
		
		while(n2 <= r)
			arr[i++] = requestQueue.get(n2++);
		
		for(int z = l; z <= r; z++)
			requestQueue.set(z, arr[z]);
	}
	
	/**
	 * MergeSort algorithm for sorting. This method is used to execute MergeSort.
	 * This MergeSort is for partial list sorting.
	 * Use {@code list.sort(new Comparator<Requests>())} instead for full list sort.
	 * @param l Lower bound
	 * @param r Upper bound
	 * @param isAsc Flag for ascending or descending sort
	 */
	protected void mergeSort(int l, int r, boolean isAsc) {
		if(l < r) {
			int mid = (l + r) / 2;
			mergeSort(l, mid, isAsc);
			mergeSort(mid + 1, r, isAsc);
			merge(l, mid, r, isAsc);
		}
		else
			return;
	}
}
