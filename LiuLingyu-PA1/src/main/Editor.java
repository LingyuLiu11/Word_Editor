package main;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;
/**
* This is the construction of editor using double linked list
* Known Bugs: <“None”>
*
* @author Lingyu Liu
* lingyuliu@brandeis.edu
* Oct 8, 2021
* COSI 21A PA1
*/
public class Editor {
	
	public int numChars; /** KEEP THIS PUBLIC : use this to store the number of characters in your Editor */
	public int curPos; /** KEEP THIS PUBLIC : use this to store the current cursor index in [0, numChars] */
	
	public Node cur; /** KEEP THIS PUBLIC : use this to reference the node that is after the visual cursor or null if curPos = numChars */
	public Node head; /** KEEP THIS PUBLIC : use this to reference the first node in the Editor's doubly linked list */
	public Node tail; /** KEEP THIS PUBLIC : use this to reference the last node in the Editor's doubly linked list */
	
//	public Editor() {
//		
//	}
	/**
	 * initial Editor
	 */
	public Editor() {
		
	}
	
	/**
	 * 
	 * @param filepath
	 * @throws FileNotFoundException
	 * initialize editor with file.
	 * run time O(n), where n is the number of chars in the file
	 */
	public Editor(String filepath) throws FileNotFoundException {
		File file = new File(filepath);
		Scanner scan = new Scanner(file);
		String s = "";
		
		while (scan.hasNextLine()) {
			
			s += scan.nextLine();
			s += String.valueOf('\n');
		}
		
		char[] chars = s.toCharArray();
		this.numChars = chars.length;
		if (chars.length == 0) {//if empty file
			this.head = null;
			this.cur = null;
			this.tail = null;
			this.curPos = 0;
		} else {
			int i = 0;//index of chars
			this.head = new Node(chars[0]);
			this.tail = head;
			i = 1;
			while (i < chars.length) {
				Node currNode = new Node(chars[i]);
				this.tail.next = currNode;
				currNode.prev = this.tail;
				this.tail = currNode;
				i++;
			}
			this.cur = this.tail.next;

			this.curPos = chars.length;
		}
	}
	
	/**
	 * 
	 * @return curPos
	 * O(1) run time
	 */
	public int getCursorPosition() {
		return this.curPos;
	}
	
	/**
	 * 
	 * @return size of editor
	 * run time O(1)
	 */
	public int size() {
		return this.numChars;
	}
	
	/**
	 * move cursor to the right
	 * O(1) run time
	 */
	public void moveRight() {
		if (this.cur == null) {
			//System.out.println("toright null");
			return;
		} else {
			this.cur = this.cur.next;
			if (this.curPos < this.numChars) {
				this.curPos += 1;
			}
			
		}
		
	}
	
	/**
	 * move cursor to left
	 * run time O(1)
	 */
	public void moveLeft() {
		
		if (this.cur == this.head || this.curPos == 0) {
			//System.out.println("toleft null");
			return;
		}
		if (this.cur == null) {
			this.cur = this.tail;
			if (this.curPos > 0) {
				this.curPos -= 1;
			}
			//System.out.println("toleft" + this.cur.data);
			return;
		}
		
		
		this.cur = this.cur.prev;
		
		if (this.curPos > 0) {//if has prev
			this.curPos -= 1;
		}
		//System.out.println("toleft" + this.cur.data);
		return;
	}
	
	/**
	 * move cursor to head
	 * O(1) time
	 */
	public void moveToHead() {
		this.curPos = 0;
		this.cur = this.head;
		//System.out.println("tohead" + this.cur.data);
	}
	
	/**
	 * move cursor to tail
	 * O(1) time
	 */
	public void moveToTail() {
		this.curPos = this.numChars;
		this.cur = this.tail.next;
//		if (this.cur != null) {
//			System.out.println("totail" + this.cur.data);
//		}
		
	}
	
	/**
	 * insert char c to the editor befor the cursor
	 * @param c
	 * run time O(1)
	 */
	public void insert(char c) { 
		
		if (this.numChars == 0) {//if insert to empty file
			Node insertNode = new Node(c);
			this.head = insertNode;
			this.tail = insertNode;
			this.cur = this.tail.next;
			this.numChars = 1;
			
			this.curPos = 1;
			
			return;
		}
		
		if (this.curPos == 0) {//if add to the head
			Node insertNode = new Node(c);
			insertNode.next = this.head;
			insertNode.prev = null;
			if (this.head != null) {
				this.head.prev = insertNode;
			}
			this.head = insertNode;
			this.curPos = 1;
		} else if (this.cur != null){//if not add to the end
			Node prevNode = this.cur.prev;
			Node insertNode = new Node(c);
			insertNode.next = this.cur;
			prevNode.next = insertNode;
			insertNode.prev = prevNode;
			
			
			this.cur.prev = insertNode;
			this.curPos += 1;
		} else {//if add to the end
			Node insertNode = new Node(c);
			this.tail.next = insertNode;
			insertNode.prev = this.tail;
			insertNode.next = this.cur;
			this.tail = insertNode;
			
			this.curPos += 1;
			
		}
		this.numChars += 1;
		//System.out.println("num: " + this.numChars);
		//System.out.println("position: " + this.curPos);
		
		
		
	}
	
	/**
	 * delete node cur
	 * O(1) time
	 */
	public void delete() {
		if (this.cur == null) {//if cur is at the end
			//System.out.println("null");
			return;
		}
		Node prevN = null;//record prev node of cur
		if (this.cur.prev != null) {//cur not head
			this.cur.prev.next = this.cur.next;
			prevN = this.cur.prev;
		} else {//cur is head
			this.head = this.cur.next;
			this.cur.prev = null;
		}
		
		if (this.cur.next != null) {//cur not tail
			this.cur.next.prev = this.cur.prev;
		}
		
		this.cur = this.cur.next;
		
		//renew tail if needed
		if (this.cur == null && this.curPos > 0) {
			this.tail = prevN;
		}
		//renew numChars
		if (this.numChars > 0) {
			this.numChars -= 1;
		}
	}
	
	/**
	 * delete char previous to the cursor
	 * O(1) time
	 */
	public void backspace() {
		if (this.cur == this.head) {//if cursor is at head
			return;
		}
		this.numChars -= 1;//renew numChars
		if (this.cur == null) {//if cur is at last
			if (this.tail.prev != null) {//if editor has more than one char
				this.tail.prev.next = null;
				this.curPos -= 1;
				this.tail = this.tail.prev;
				
				return;
			} else {//only one char and delete
				this.clear();
				return;
			}
		}
		if (this.cur.prev.prev == null) {//delete first
			this.cur.prev = null;
			this.head = this.cur;
			this.curPos -= 1;
		} else {//delete in middle
			this.cur.prev.prev.next = this.cur;
			this.cur.prev = this.cur.prev.prev;
			this.curPos -= 1;
		}
	}
	
	/**
	 * return content of editor to string
	 * run time O(n), where n is number of chars in file
	 */
	public String toString() {
		String s = "";
		Node dummy = this.head;
		while (dummy != null) {
			s += String.valueOf(dummy.data);
			dummy = dummy.next;
		}
		return s;
	}
	
	/**
	 * clear the file to empty
	 * run time O(1)
	 */
	public void clear() {
		this.numChars = 0;
		this.cur = null;
		this.head = null;
		this.curPos = 0;
		this.tail = null;	
	}
	
	/**
	 * save the file
	 * @param savepath
	 * @throws FileNotFoundException
	 * run time O(n), where n is number of chars in file
	 */
	public void save(String savepath) throws FileNotFoundException {
		PrintStream out = new PrintStream(new PrintStream(savepath));
		out.println(this.toString());
		out.flush();
		out.close();
	}
	
	
}
