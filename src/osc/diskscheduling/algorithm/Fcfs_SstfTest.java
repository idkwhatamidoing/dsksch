package osc.diskscheduling.algorithm;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.ListIterator;

import org.junit.Test;

public class Fcfs_SstfTest {
	Integer[] arr = new Integer[] {50, 95, 180, 34, 119, 11, 123, 62, 64, 199};
	ListIterator<Integer> ls;
	DiskScheduling obj;
	
	private void init() {
		LinkedList<Integer> itReq = new LinkedList<Integer>();
		for(Integer k : arr)
			itReq.add(k);
		
		obj = new Fcfs_Sstf(itReq.iterator());
	}
	
	
	private void init2() {
		LinkedList<Integer> itReq = new LinkedList<Integer>();
		Integer[] arr2 = new Integer[] {53, 98, 183, 37, 122, 14, 124, 65, 67, 199};
		for(Integer k : arr2)
			itReq.add(k);
		
		obj = new Fcfs_Sstf(itReq.iterator());
	}
	
	@Test
	public void testFCFS() {
		init();
		System.out.println("FCFS");
		
		System.out.print("Input queue: ");
		obj.display();
		
		obj.first_scan_look();
		ls = obj.getRequestQueue().listIterator();
		assertEquals(644, ls.next().intValue());
		assertEquals(199, ls.next().intValue());
		assertEquals(50, ls.next().intValue());
				
		System.out.print("Output queue: ");
		obj.display();
	}
	
	@Test
	public void testSSTF() {
		init();
		System.out.println("SSTF");
		
		System.out.print("Input queue: ");
		obj.display();
		
		obj.shortest_cscan_clook();
		ls = obj.getRequestQueue().listIterator();
		assertEquals(236, ls.next().intValue());
		assertEquals(199, ls.next().intValue());
		assertEquals(50, ls.next().intValue());
		
		System.out.print("Output queue: ");
		obj.display();
	}
	
	@Test
	public void testFCFS2() {
		Integer[] arr2 = new Integer[] {53, 98, 183, 37, 122, 14, 124, 65, 67, 199};
		init2();
		System.out.println("FCFS");
		
		System.out.print("Input queue: ");
		obj.display();
		
		obj.first_scan_look();
		ls = obj.getRequestQueue().listIterator();
		ls.next();
		assertEquals(199, ls.next().intValue());
		
		int i = 0;
		while(ls.hasNext() && i < arr2.length - 1) {
			assertEquals(arr2[i++], ls.next());
		}
				
		System.out.print("Output queue: ");
		obj.display();
	}
	
	@Test
	public void testSSTF2() {
		Integer[] arr2 = new Integer[] {53, 65, 67, 37, 14, 98, 122, 124, 183, 199};
		init2();
		System.out.println("SSTF");
		
		System.out.print("Input queue: ");
		obj.display();
		
		obj.shortest_cscan_clook();
		ls = obj.getRequestQueue().listIterator();
		ls = obj.getRequestQueue().listIterator();
		ls.next();
		assertEquals(199, ls.next().intValue());
		
		int i = 0;
		while(ls.hasNext() && i < arr2.length - 1) {
			assertEquals(arr2[i++], ls.next());
		}
		
		System.out.print("Output queue: ");
		obj.display();
	}
}
