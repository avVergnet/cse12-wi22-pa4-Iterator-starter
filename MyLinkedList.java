/**
 * Name: Alexis Vergnet	
 * Email: avergnet@ucsd.edu
 * Sources used: None
 * 
 * A data structure similar to Java’s LinkedList with generic types. 
 * Contains the Node class which makes up the LinkedList along wiht many 
 * mehtods for Nodes and the LinkedList
 */

import java.util.AbstractList;
import java.util.ListIterator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/** 
 * Attempt at implementing a data structure similar to Java’s 
 * LinkedList with generic types. Contains the Node class which makes up
 * the LinkedList along wiht many mehtods for Nodes and the LinkedList
 */
public class MyLinkedList<E> extends AbstractList<E> {

	int size;
	Node head;
	Node tail;

	/**
	 * A Node class that holds data and references to previous and next Nodes.
	 */
	protected class Node {
		E data;
		Node next;
		Node prev;

		/** 
		 * Constructor to create singleton Node 
		 * @param element Element to add, can be null	
		 */
		public Node(E element) {
			// Initialize the instance variables
			this.data = element;
			this.next = null;
			this.prev = null;
		}

		/** 
		 * Set the parameter prev as the previous node
		 * @param prev - new previous node
		 */
		public void setPrev(Node prev) {
			this.prev = prev;		
		}

		/** 
		 * Set the parameter next as the next node
		 * @param next - new next node
		 */
		public void setNext(Node next) {
			this.next = next;
		}

		/** 
		 * Set the parameter element as the node's data
		 * @param element - new element 
		 */
		public void setElement(E element) {
			this.data = element;
		}

		/** 
		 * Accessor to get the next Node in the list 
		 * @return the next node
		 */
		public Node getNext() {
			return this.next;
		}

		/** 
		 * Accessor to get the prev Node in the list
		 * @return the previous node  
		 */
		public Node getPrev() {
			return this.prev;
		}

		/** 
		 * Accessor to get the Nodes Element 
		 * @return this node's data
		 */
		public E getElement() {
			return this.data;
		}
	}

	//  Implementation of the MyLinkedList Class
	/** Only 0-argument constructor is defined */
	public MyLinkedList() {
		size = 0;
		head = new Node(null); //dummy head node
		tail = new Node(null); //dummy tail node

		//connect the dummy head and tail nodes together
		head.setNext(tail);
		head.setPrev(null);
		tail.setPrev(head);
		tail.setNext(null);
	}

	
	/** 
	 * Helper method that returns size
	 * @return number of nodes being stored ommiting the dummy head and tail
	 */
	@Override
	public int size() {
		return this.size;
	}

	/** 
	 * Get data within the node at position index.
	 * @param index - index of which node within MyLinkedList to get data from
	 * @return the data that the indexed node contains
	 */
	@Override
	public E get(int index) {
		//test if index is in correct bounds
		if(index < 0 || index >= this.size()){
			throw new IndexOutOfBoundsException();
		}
		Node currentNode = getNth(index);
		return currentNode.getElement();
	}

	/** 
	 * Add a node into this list by index. 
	 * @param index - index to where node should be added
	 * @param data - what data the added node should carry
	 */
	@Override
	public void add(int index, E data) {
		//test if the data added is null
		if(data == null){
			throw new NullPointerException();
		}
		//test if index is in correct bounds
		if(index < 0 || index > this.size()){
			throw new IndexOutOfBoundsException();
		}
		Node addedNode = new Node(data);
		if(this.isEmpty()){
			addedNode.setNext(tail);
			addedNode.setPrev(head);
			head.setNext(addedNode);
			tail.setPrev(addedNode);
		} else if(index == this.size()){
			addedNode.setNext(tail);
			addedNode.setPrev(tail.getPrev());
			tail.getPrev().setNext(addedNode);
			tail.setPrev(addedNode);
		} else {
			Node currentNode = this.getNth(index);
			addedNode.setPrev(currentNode.getPrev());
			addedNode.setNext(currentNode);
			currentNode.getPrev().setNext(addedNode);			
			currentNode.setPrev(addedNode);
		}
		size++; //increment size
	}

	/** 
	 * Add a node at the end into this list
	 * @param data - what data the added node should carry
	 * @return true always as it is how it was defined in AbstractList
	 */
	public boolean add(E data) {
		//test if the data added is null
		if(data == null){
			throw new NullPointerException();
		}
		Node addedNode = new Node(data); //create new node
		//set its pointers
		addedNode.setNext(tail);
		addedNode.setPrev(tail.getPrev());
		//update old pointers
		tail.getPrev().setNext(addedNode);
		tail.setPrev(addedNode);

		size++; //increment size
		return true;
	}

	/** 
	 * Set the element for the node at index to data
	 * @param index - index of which node should be updated
	 * @param data - to what data it should be updated to 
	 * @return E - the element that was previously stored in this node.
	 */
	public E set(int index, E data) {
		//test if the data added is null
		if(data == null){
			throw new NullPointerException();
		}
		//test if index is in correct bounds
		if(index < 0 || index >= this.size()){
			throw new IndexOutOfBoundsException();
		}

		//find node at index given
		Node currentNode = getNth(index);
		E toReturn = currentNode.getElement();
		//update data
		currentNode.setElement(data);
		
		return toReturn;
	}

	/** 
	 * Remove the node from the position index in this list 
	 * @param index - index of which node should be removed
	 * @return E - the data within the removed node.
	 */
	public E remove(int index) {
		//test if index is in correct bounds
		if(index < 0 || index >= this.size()){
			throw new IndexOutOfBoundsException();
		} 
		
		//find node to remove based on index
		Node currentNode = getNth(index);
		E toReturn = currentNode.data;

		//update the pointers of the rest of the list
		currentNode.getPrev().next = currentNode.getNext();
		currentNode.getNext().prev = currentNode.getPrev(); 
		//set the pointers of the removed node to zero
		currentNode.setNext(null);
		currentNode.setPrev(null);
		//reduce size by 1;
		size--;

		return toReturn;
	}

	/** 
	 * Remove all nodes from the list
	 */
	public void clear() {
		if(!this.isEmpty()){
			//update the pointers of the inside of the list to null
			head.getNext().setPrev(null);
			tail.getPrev().setNext(null);
			//set the pointers of the dummy head and tail nodes to each other
			head.setNext(tail);
			tail.setPrev(head);		
		} 
		size = 0;
	}

	/**
	 * Determine if the list is empty
	 * @return ture if list is empty and false if not
	 */
	public boolean isEmpty() {
		if(this.size() == 0){
			return true;
		} return false;
	}

	/** 
	 * Helper method that returns the Node at a specified index not the content 
	 * @param index - index of which node to return
	 * @return node at a specified index not the content
	 */
	protected Node getNth(int index) {		
		//test if index is in correct bounds
		if(index < 0 || index >= this.size()){
			throw new IndexOutOfBoundsException();
		}
	
		//find node at given index
		Node currentNode = this.head;
		for(int i = 0; i < index + 1; i++){
			currentNode = currentNode.getNext();
		}
		
		return currentNode;
	}

	protected class MyListIterator implements ListIterator<E> {
		//throws IllegalStateException NoSuchElementException and NullPointerException 

        //Instance variables
		Node left, right;
		int idx;
        boolean forward;
		boolean canRemoveOrSet;

        // MyListIterator methods

		//Constructor that is used to initialize the iterator. 
		public MyListIterator(){
			left = head;
			right = head.getNext();
			idx = 0;
			forward = true;
			canRemoveOrSet = false;
		}

		//Return true if there is an element node when going in the forward (head to tail) direction from the current iterator position. Sentinel (dummy) nodes do not count as element nodes.
        public boolean hasNext() {
			if(right == tail){
				return false;
			} return true;
        }

		/* Moves the iterator forward by one node.
		 * @return the next element in the list when going forward
		 */
		public E next(){
			if(this.hasNext() == false){
				throw new NoSuchElementException();
			}
			//update pointers
			left = right;
			right = right.getNext();
			//update idx
			idx++;
			//update instance variables
			canRemoveOrSet = true;
			forward = true;
			return left.getElement();
		}

		//Return true if there is an element node when going in the backward (tail to head) direction from the current iterator position. Sentinel (dummy) nodes do not count as element nodes.
		public boolean hasPrevious(){
			if(left == head){
				return false;
			} return true;
		}

		//Return the next element in the list when going backward, and move the iterator backward by one node.
		public E previous(){
			if(this.hasPrevious() == false){
				throw new NoSuchElementException();
			}
			//update pointers
			right = left;
			left = left.getPrev();
			//update idx
			idx--;
			//update instance variables
			canRemoveOrSet = true;
			forward = false;
			return right.getElement();
		}

		//Return the index of the element that would be returned by a call to next().
		//Return the list size if at the end of the list.
		public int nextIndex(){
			if(right == tail){
				return size;
			} return idx;
		}

		//Return the index of the element that would be returned by a call to previous().
		//Return -1 if at the start of the list.
		public int previousIndex(){
			if(left == head){
				return -1;
			} return idx-1;
		}

		//Insert the given item into the list immediately before the element that would be returned by next()
		//If we call previous() immediately following add, the newly added item would be returned.
		//The value of the current index of the list iterator is increased by one.
		public void add(E element){
			if(element == null){
				throw new NullPointerException();
			}
			Node addedNode = new Node(element);
			//upadate pointers
			left.setNext(addedNode);
			addedNode.setPrev(left);
			right.setPrev(addedNode);
			addedNode.setNext(right);
			//update instance variables
			left = addedNode;
			canRemoveOrSet = false;
			idx++;
			size++; 
		}

		//For the node returned by the most recent next/previous call, replace its value with the new value element.
		public void set(E element){
			if(element == null){
				throw new NullPointerException();
			}
			if(canRemoveOrSet == false){
				throw new IllegalStateException();
			}
			if(forward == true){
				left.setElement(element);
			} else {
				right.setElement(element);
			}
		}

		//Remove the last element node returned by the most recent next/previous call.
		public void remove(){
			if(canRemoveOrSet == false){
				throw new IllegalStateException();
			}
			if(forward == true){
				left.getPrev().setNext(right);
				right.setPrev(left.getPrev());
				left.setNext(null);
				left.setPrev(null);
				left = right.getPrev();
				idx--;
			} else {
				right.getNext().setPrev(left);
				left.setNext(right.getNext());
				right.setNext(null);
				right.setPrev(null);
				right = left.getNext();
			}
			size--;
			canRemoveOrSet = false;
		}
	}
}