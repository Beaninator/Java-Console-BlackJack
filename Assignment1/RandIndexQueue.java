import java.util.Random;

public class RandIndexQueue<T> implements MyQ<T>, Shufflable, Indexable<T>
{
  protected T[] data;
  private int back = 0;
  private int front = 0;
  private int lSize = 0;
  private int moves = 0;

  public RandIndexQueue(int capacity)
  {
    @SuppressWarnings("unchecked")
    T [] temp = (T []) new Object[capacity];
    data = temp;
  }
  /*
  copy constructor, which should make a new RandIndexQueue<T> that is logically equivalent to the argument.
  The copy constructor should not be shallow – the arrays should not be shared.
  However, it does not have to be completely deep either
  (you don't need to make new copies of the individual items in the queue).
  */
  public RandIndexQueue(RandIndexQueue<T> old)
  {
    data = (T []) new Object[old.capacity()];
    for(int i = 0; i < old.size(); i ++)
    {
        data[i] = old.get(i);
    }
    lSize = old.size();
    back = old.size() - 1;
  }

  /** Adds a new entry to the back of this queue.
      @param newEntry  An object to be added. */
  public void enqueue(T newEntry)
  {
    if(lSize == data.length)
    {
      //System.out.println("Resizing\n current " + toString());
      T [] temp = (T []) new Object[data.length * 2];
      for(int i = 0; i <= lSize; i ++)
      {
        temp[i] = get(i);
      }
      front = 0;

      //copy temp array over//
      moves ++;
      data = temp;

      //tracking the logical last taken index//
      back = lSize;
      data[back] = newEntry;
      lSize ++;
    }
    //we don't have to resize//
    else
    {
      //we must wrap//
      if(back + 1 > data.length - 1)
      {
        //System.out.println("Wrapping...\n current " + toString());

        back = 0;
      }
      else if (lSize == 0)
      {
        //System.out.println("First entry...\n current " + toString());
        back = 0;
      }
      //we don't need to wrap//
      else
      {
        //System.out.println("Adding item normally...\n current " + toString());
        back ++;
      }
      moves ++;
      lSize ++;
      data[back] = newEntry;
    }
  }

  /** Removes and returns the entry at the front of this queue.
      @return  The object at the front of the queue.
      @throws  EmptyQueueException if the queue is empty before the operation. */
  public T dequeue()
  {
    T old;

    if(lSize == 0)
    {
      throw new EmptyQueueException();
    }

    //wrapped//
    else if (front + 1 == data.length)
    {
      old = data[front];
      data[front] = null;
      front = 0;
    }

    else if (lSize == 1)
    {
      old = getFront();
      front = 0;
      data[front] = null;
    }
    //not wrapped//
    else
    {
      old = data[front];
      data[front] = null;
      front ++;
    }

    moves ++;
    lSize --;
    return old;
  }

  /**  Retrieves the entry at the front of this queue.
      @return  The object at the front of the queue.
      @throws  EmptyQueueException if the queue is empty. */
  public T getFront()
  {
    if(lSize == 0)
    {
      throw new EmptyQueueException();
    }
    else
    {
      return data[front];
    }
  }

  /** Detects whether this queue is empty.
      @return  True if the queue is empty, or false otherwise. */
  public boolean isEmpty()
  {
    if(lSize == 0)
    {
      return true;
    }
    else
    {
      return false;
    }
  }

  /** Removes all entries from this queue. */
  public void clear()
  {
    for(int i = 0; i < lSize; i ++)
    {
      dequeue();
    }
    lSize = 0;
    back = 0;
    front = 0;
  }

  // Return the number of items currently in the MyQ.  Determining the
  // length of a queue can sometimes very useful.

  // Return the length of the underlying data structure which is storing the
  // data.  In an array implementation, this will be the length of the array.
  // This method would not normally be part of a queue but is included here to
  // enable testing of your resizing operation.
  public int capacity()
  {
    return data.length;
  }

  // Methods to get and set the value for the moves variable.  The idea for
  // this is the same as shown in Recitation Exercise 2 -- but now instead
  // of a separate interface these are incorporated into the MyQ<T>
  // interface.  The value of your moves variable should be updated during
  // an enqueue() or dequeue() method call.  However, any data movement required
  // to resize the underlying array should not be counted in the moves.
  public int getMoves()
  {
    return moves;
  }

  public void setMoves(int m)
  {
    moves = m;
  }

  public void shuffle()
  {
    Random rand = new Random();

    for(int index = 0; index < lSize; index ++)
    {
      int i = rand.nextInt(lSize);
      T item1 = get(i);

      int j = rand.nextInt(lSize);
      T item2 = get(j);

      set(i, item2);
      set(j, item1);
    }

    return;
  }

  // Get and return the value located at logical location i in the implementing
  // collection, where location 0 is the logical beginning of the collection.
  // If the collection has fewer than (i+1) items, throw an IndexOutOfBoundsException
  public T get(int i)
  {
    if(lSize < i)
    {
      throw new IndexOutOfBoundsException();
    }

    //must wrap//
    else if(front + i >= data.length)
    {
      return data[Math.abs(data.length - (front + i))];
    }

    //no wrap needed//
    else
    {
      return data[front + i];
    }
  }

  // Assign item to logical location i in the implementing collection, where location
  // 0 is the logical beginning of the collection.  If the collection has fewer than
  // (i+1) items, throw an IndexOutOfBoundsException
  public void set(int i, T item)
  {
    if(i >= lSize)
    {
      throw new IndexOutOfBoundsException();
    }
    else
    {
      if(front + i >= data.length)
      {
        data[Math.abs(data.length - (front + i))] = item;
      }
      else
      {
        data[front + i] = item;
      }
    }
    moves ++;
  }

  // Return the number of items currently in the Indexable. Note that this is the
  // same method specified in the MyQ<T> interface.  It is fine for a single method
  // to be part of more than one interface
  public int size()
  {
    return lSize;
  }

/*
The second method is an equals method that returns true if the queues are logically equivalent.
In this case logically equivalent means that the individual items in both queues are equal() and in the same relative positions
within the queues (based on front and back)
*/
  public boolean equals(RandIndexQueue<T> rhs)
  {
    if(lSize == rhs.size())
    {
      for(int i = 0; i < lSize; i ++)
      {
        if(get(i) != rhs.get(i))
        {
          //System.out.println("Found that at index " + i + " " + get(i) + " doesn't equal " + rhs.get(i));
          return false;
        }
      }
    }

    else
    {
      //System.out.println("Found that the queues don't have the same logical size");
      return false;
    }

    return true;
  }

  public String toString()
  {
    String result = "Contents : ";
    /*
    for(T items : data)
    {
      result = result + items + " ";
    }
    */

    for(int i = 0; i < lSize; i ++)
    {
      result = result + get(i) + " ";
    }

    return result;
  }
}
