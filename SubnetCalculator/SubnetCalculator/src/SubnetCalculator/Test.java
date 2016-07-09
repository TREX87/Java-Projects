package SubnetCalculator;
import java.util.Scanner;

public class Test {

	public Test() {
		// TODO Auto-generated constructor stub
	}
	
	public static void main(String[] args) {
		System.out.print("Enter the subnet address that should be calculated: ");
		Scanner sc = new Scanner(System.in);
		String subnet = sc.nextLine();
		
		SubnetCalculator c = new SubnetCalculator(subnet);
		c.display();
	}

}
