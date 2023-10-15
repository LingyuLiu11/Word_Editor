package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import main.Editor;
import main.Node;
/**
* This is the edge case tests I wrote
* Known Bugs: <“None”>
*
* @author Lingyu Liu
* lingyuliu@brandeis.edu
* Oct 8, 2021
* COSI 21A PA1
*/
public class StudentEditorTests {

	Editor e;

	/**
	 * Before each test, the Editor is re-initialized
	 */
	@Before
	public void reset() {
		e = new Editor();
	}

	/**
	 * Building on example movement no. 1 on page 4 on the assignment PDF, tests if
	 * the cursor can be properly moved from before 'b' to before 'l' to before 'u'.
	 */
	@Test
	public void testMoveRightEdge() {
		// create editor storing "blue"
		loadEditorBLUE(e);

		// point cur at head
		e.cur = e.head;
		e.curPos = 0;

		// move cur right many times
		e.moveRight();
		assertEquals(1, e.getCursorPosition());
		assertEquals('l', e.cur.data);
		e.moveRight();
		assertEquals(2, e.getCursorPosition());
		assertEquals('u', e.cur.data);
		e.moveRight();
		e.moveRight();
		e.moveRight();
		e.moveRight();
		e.moveRight();
		assertEquals(4, e.getCursorPosition());
		assertEquals(null, e.cur);
		
	}

	/**
	 * Building on example movement no. 2 on page 4 on the assignment PDF, tests if
	 * the cursor can be properly moved from before 'e' to before 'u' to before 'l'.
	 */
	@Test
	public void testMoveLeftEdge() {
		// create editor storing "blue"
		loadEditorBLUE(e);

		// point cur at tail
		e.cur = e.tail;
		e.curPos = 3;

		// move cur left many times
		e.moveLeft();
		assertEquals(2, e.getCursorPosition());
		assertEquals('u', e.cur.data);
		e.moveLeft();
		assertEquals(1, e.getCursorPosition());
		assertEquals('l', e.cur.data);
		e.moveLeft();
		e.moveLeft();
		e.moveLeft();
		e.moveLeft();
		e.moveLeft();
		e.moveLeft();
		assertEquals(0, e.getCursorPosition());
		assertEquals('b', e.cur.data);
	}

	/**
	 * Using example movement no. 3 on page 4 on the assignment PDF, tests if the
	 * cursor can be properly moved from before 'u' to before 'b'.
	 */
	@Test
	public void testMoveToHead() {
		// create editor storing "blue"
		loadEditorBLUE(e);

		// put cur at 'u'
		e.cur = e.head.next.next;
		e.curPos = 2;

		// move to head
		e.moveToHead();

		// cur should now be at 'b'
		assertEquals(0, e.getCursorPosition());
		assertEquals('b', e.cur.data);
	}

	/**
	 * Using example movement no. 4 on page 4 on the assignment PDF, tests if the
	 * cursor can be properly moved from before 'u' to after 'e'.
	 */
	@Test
	public void testMoveToTail() {
		// create editor storing "blue"
		loadEditorBLUE(e);

		// put cur at 'u'
		e.cur = e.head.next.next;
		e.curPos = 2;

		// move to tail
		e.moveToTail();

		// cur should now be after 'e'
		assertEquals(4, e.getCursorPosition());
		assertNull(e.cur);
	}

	/**
	 * Builds on example insertions discussed on p. 5 and 6 of the assignment PDF,
	 * tests if the Editor properly inserts '$', '@', '%' after 'b'
	 */
	@Test
	public void testMultipleInsertEdge() {
		// create editor storing "blue"
		loadEditorBLUE(e);
		//insert to the end
		e.cur = null;
		e.curPos = 4;
		e.insert('$');
		e.insert('@');
		e.insert('%');
		assertEquals(7, e.getCursorPosition());
		assertEquals(null, e.cur);
		assertEquals("blue$@%", e.toString());
		
		//insert to head
		e.cur = e.head;
		e.curPos = 0;
		e.insert('$');
		e.insert('@');
		e.insert('%');
		assertEquals(3, e.getCursorPosition());
		assertEquals('b', e.cur.data);
		assertEquals("$@%blue$@%", e.toString());
	}

	/**
	 * Builds on example deletions discussed on p. 6 of the assignment PDF, tests if
	 * the Editor properly deletes 'l', then 'u', then 'e'.
	 */
	@Test
	public void testDeleteMultipleEdge() {
		// create editor storing "blue"
		loadEditorBLUE(e);

		// point cur at 'b'
		e.cur = e.head;
		e.curPos = 0;

		
		e.delete();

		assertEquals('l', e.head.data);
		assertEquals('e', e.tail.data);
		assertEquals(0, e.getCursorPosition());
		assertEquals(3, e.size());
		assertEquals('l', e.cur.data);
		
		e.delete();
		e.delete();
		e.delete();
		e.delete();
		assertEquals(0, e.size());
		
		loadEditorBLUE(e);
		e.cur = null;
		e.curPos = 4;
		e.delete();
		assertEquals(4, e.size());
		
		//insert and delete mixed
		e.insert('$');//blue$
		e.insert('$');//blue$$
		e.moveLeft();
		e.moveLeft();
		e.delete();//blue
		e.delete();//blue
		assertEquals("blue", e.toString());
		e.insert('$');//blue$
		assertEquals("blue$", e.toString());
		
		assertEquals('b', e.head.data);
		assertEquals('$', e.tail.data);
		assertEquals(5, e.getCursorPosition());
		assertEquals(5, e.size());
		assertEquals(null, e.cur);
	}

	/**
	 * Builds on example backspaces discussed on p. 6 - 7 of the assignment PDF,
	 * tests if the Editor properly backspaces 'u', then 'l', then 'b'.
	 */
	@Test
	public void testBackspaceMultipleEdge() {
		// create editor storing "blue"
		loadEditorBLUE(e);

		// point cur 'b'
		e.cur = e.head;
		e.curPos = 0;
		e.backspace();
		e.backspace();
		e.backspace();
		e.backspace();
		assertEquals('b', e.head.data);
		assertEquals('e', e.tail.data);
		assertEquals(0, e.getCursorPosition());
		assertEquals(4, e.size());
		assertEquals('b', e.cur.data);
		
		//put at end
		e.cur = null;
		e.curPos = 4;
		e.backspace();
		e.backspace();
		e.backspace();
		e.backspace();
		e.backspace();
		e.backspace();
		e.backspace();
		e.backspace();
		assertEquals(null, e.head);
		assertEquals(null, e.tail);
		assertEquals(0, e.getCursorPosition());
		assertEquals(0, e.size());
		assertEquals(null, e.cur);
		
		//insert, delete, backspace mixed
		loadEditorBLUE(e);
		e.cur = null;
		e.curPos = 4;
		e.backspace();//blu
		e.insert('$');//blu$
		e.insert('5');//blu$5
		e.moveLeft();//blu$5
		e.delete();//blu$
		assertEquals('b', e.head.data);
		assertEquals('$', e.tail.data);
		assertEquals(4, e.getCursorPosition());
		assertEquals(4, e.size());
		assertEquals(null, e.cur);
	}

	/**
	 * Tests the toString() method described on page 7.
	 */
	@Test
	public void testToStringMultiLines() {
		// create editor storing "blue"
		loadEditorBLUE(e);
		e.cur = null;
		e.curPos = 4;
		e.insert(' ');
		e.insert('\n');
		e.insert('a');
		// check toString
		assertEquals("blue " + '\n' + "a", e.toString());
	}
	public void testClear() {
		// create editor storing "blue"
		loadEditorBLUE(e);
		e.clear();
		// check toString
		assertEquals("", e.toString());
	}
	
	public static void loadEditorBLUE(Editor editor) {
		Node b = new Node('b');
		Node l = new Node('l');
		Node u = new Node('u');
		Node e = new Node('e');

		b.next = l;
		l.prev = b;
		l.next = u;
		u.prev = l;
		u.next = e;
		e.prev = u;

		editor.head = b;
		editor.tail = e;

		editor.numChars = 4;
	}

}
