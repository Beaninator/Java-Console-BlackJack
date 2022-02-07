public class BlackjackCards extends RandIndexQueue<Card>
{
  public BlackjackCards(int size)
  {
    super(size);
  }
  public int getValue()
  {
    int total = 0;
		// Initially add all of the values together.  Note that the get(i)
		// method is an offset from the front of the queue, not an offset
		// from the beginning of the array.
		for (int i = 0; i < size(); i++)
		{
			total += get(i).value();
		}
		// Change aces from back to front until <= 21
		// or we run out of cards
		int loc = size()-1;
		while (loc >= 0 && total > 21)
		{
			total -= get(loc).value();
			total += get(loc).value2();
			loc--;
		}
		return total;
  }
}
