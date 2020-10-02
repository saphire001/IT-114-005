 import java.util.Scanner; 
public class RecurtionToLoop 
{	
	public static void main(String[] args) 
	{
		Scanner scan = new Scanner(System.in); 
		System.out.println("Enter the number you want added. 0 is exit");
		
		int sum = 0; 
		boolean Exit = false; 
		while(! Exit)
		{
			int num = scan.nextInt(); 
			
			if (num == 0)
			{
				Exit = true; 
			}
			else
				sum += num;
		}
		
		System.out.println("The sum is " + sum);
	}
}
