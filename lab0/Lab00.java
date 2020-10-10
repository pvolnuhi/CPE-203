public class Lab00
{
   public static void main(String[] args)
   {
   	//declaring and initializing some variables
   		int x = 5;
   		String y = "hello";
   		double z = 9.8;

   		//printing the variables
   		System.out.println("x: "+ x + " y: " + y + " z: " + z);

   		//a list (make an array in java)
   		int[] nums = {3, 6, -1, 2};
   		for (int num : nums)
   			System.out.println(num);

   		//call a function
   		int numFound = char_count(y, 'l'); // //=string ''=char
   		System.out.println("Found: " + numFound); //need the println to place numFound on newline after +, otherwise it'll merge on the same line 

   		//a counting for loop
   		for (int i = 1; i < 11; i++)
   			System.out.print(i + " ");
   			System.out.println();
   	}

   	public static int char_count(String s, char c) //void usually returns nothing 
	{
		int count = 0; // int count
		char[] chars = s.toCharArray(); 
		for (char ch : chars)
		{
			if (ch == c)
			{
				count += 1;
			}
		}
	return count;
	}

}