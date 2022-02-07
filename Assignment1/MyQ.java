// CS 0445 Spring 2022
// Assignment 1 MyQ<T> interface
// Carefully read the specifications for each of the operations and
// implement them correctly in your RandIndexQueue<T> class.

// The MyQ<T> interface extends the author's QueueInterface<T> and
// adds the setMoves() and getMoves() methods that were in a separate
// interface in Recitation Exercise 2.  Thus, MyQ<T> is incorporating
// the functionalities of both QueueInterface<T> and Moves.  It also
// has a few extra methods, as explained below.

// For the details of the queue operations, see QueueInterface<T>.  Note
// that you will need QueueInterface.java in order to use this interface.

// For this assignment, you MUST implement the interface with the
// following requirements: 
// 1) The underlying data must be a simple Java array
// 2) No Java collection classes may be used in this implementation.  All of
//    your methods must act directly on the underlying array
// 3) Your enqueue() and dequeue() methods must require only a single data assignment
//    each.  In other words, there should be no shifting to create or to fill
//    a space in your array.  To see how to implement your queue in this way,
//    see the Assignment 1 document and also read Section 11.7-11.16 of your text. 
//    You are not required to use this exact implementation but it should be similar.
// 4) The enqueue() method must always succeed (barring some extreme event).  This
//    means that your implementation must have a way to dynamically resize your
//    underlying array when necessary.  Be careful about resizing -- it should not
//    affect the logical ordering of the MyQ.
//
//    Note: Do not count the moves necessary for resizing in your totals.  We
//    will discuss the overhead of resizing the array later on in lecture.

public interface MyQ<T> extends QueueInterface<T>
{
	
	// Return the number of items currently in the MyQ.  Determining the
	// length of a queue can sometimes very useful.
	public int size();
	
	// Return the length of the underlying data structure which is storing the
	// data.  In an array implementation, this will be the length of the array.
	// This method would not normally be part of a queue but is included here to
	// enable testing of your resizing operation.
	public int capacity();

	// Methods to get and set the value for the moves variable.  The idea for
	// this is the same as shown in Recitation Exercise 2 -- but now instead
	// of a separate interface these are incorporated into the MyQ<T>
	// interface.  The value of your moves variable should be updated during
	// an enqueue() or dequeue() method call.  However, any data movement required
	// to resize the underlying array should not be counted in the moves.
	public int getMoves();
	public void setMoves(int moves);
}