package SubnetCalculator;

public class SubnetCalculator extends ConvertBinaryToDecimalAddress implements Subnet  {

	//class variable that indicates subnet address that user inputed 
	private String subnet;
	private String formatedSubnet;
	private String binaryBroadcastAddress;
	private String binarySubnetMaskString;
	int _maskBits; //holds the value of mask bits like "/28", for instance
	String subnetID = ""; //string that will show subnetID, empty at the beginning
	
	/**
	 * default constructor
	 */
	public SubnetCalculator() {
		subnet = "";
	}
	
	
	/**
	 * constructor that initializes class variable during instantiation of class object
	 * @param subnet
	 */
	public SubnetCalculator(String subnet) {
		this.subnet = subnet;
		
		//a few while loops that loop through digits until it hits "dot", and writes them into separate strings
		int i = 0;
		String firstDecimal = "", secondDecimal = "", thirdDecimal = "", fourthDecimal = "";
		while(subnet.charAt(i) != '.') {
			firstDecimal += subnet.charAt(i);
			i++;
		}
		i++; //steps over the "dot"
		while(subnet.charAt(i) != '.') {
			secondDecimal += subnet.charAt(i);
			i++;
		}
		i++; //steps over the "dot"
		while(subnet.charAt(i) != '.') {
			thirdDecimal += subnet.charAt(i);
			i++;
		}
		i++; //steps over the "dot"
		while(subnet.charAt(i) != ' ' && subnet.charAt(i) != '/') {
			fourthDecimal += subnet.charAt(i);
			i++;
		}
		
		// extracts mask bits from the "subnet" string, and stores the value in the class variable
		String number = "";
		for(int j = i; j < subnet.length(); j++) {
			number += subnet.charAt(j);
			if(!isNum(number)) {
				number = "";
			} 
		}
		_maskBits = Integer.parseInt(number);
		
		
		//formats each separated decimal into three digits in case our decimal has only two or one
		String one = "", two = "", three = "", four = "";
		if(firstDecimal.length() < 3) {
			if(firstDecimal.length() < 2)
				one = "00" + firstDecimal;
			else
				one = "0" + firstDecimal;
		} else
			one = firstDecimal;
		
		if(secondDecimal.length() < 3) {
			if(secondDecimal.length() < 2)
				two = "00" + secondDecimal;
			else
				two = "0" + secondDecimal;
		} else
			two = secondDecimal;
		
		if(thirdDecimal.length() < 3) {
			if(thirdDecimal.length() < 2)
				three = "00" + thirdDecimal;
			else
				three = "0" + thirdDecimal;
		} else
			three = thirdDecimal;
		
		if(fourthDecimal.length() < 3) {
			if(fourthDecimal.length() < 2)
				four = "00" + fourthDecimal;
			else
				four = "0" + fourthDecimal;
		} else
			four = fourthDecimal;
		
		//initializes subnet that have format "000.000.000.000"
		formatedSubnet = one + "." + two + "." + three + "." + four;
		
	}
	
	/**
	 * standard method
	 * checks if string is number
	 */
	public boolean isNum(String strNum) {
		boolean check = true;
		try {
			Double.parseDouble(strNum);
		} catch(NumberFormatException e) {
			check = false;
		}
		
		return check;
	}
	
	
	/**
	 * calculates subnet mask and returns it as string that contains Subnet Mask address
	 * @return subnetMask
	 */
	public String calculateSubnetMask() {
		
		String maskBits = "";
		char slash;
		boolean isSlash = false;
		
		for(int i = 0; i < subnet.length(); i++) {
			slash = subnet.charAt(i);
			if(slash == '/' && isSlash == false) {
				isSlash = true;
				continue;
			} else if(isSlash) {
				maskBits += slash;
			}
		}
		
		// converts string mask bits to int mask bits, we need int for iterator
		int mBits = Integer.parseInt(maskBits);
		//variable that hold the number of max bits
		int maxBits = 32;
		
		String binarySubnetMask = "";
		for(int i = 0; i < mBits; i++) {
			//this usually locations of "dot" in IP address
			if(i == 8 || i == 17 || i == 26) {
				binarySubnetMask += ".";
				//increases number of mask bits since "dot" does not count as a binary digit
				mBits++;
				//increases number of max bits since "dot" does not count as a binary digit
				maxBits++;
			} else
				binarySubnetMask += "1";
		}
		
		//this "for" loop loops through the rest of the IP address for adding zeros to the end
		for(int i = mBits; i < maxBits; i++) {
			if(i == 8 || i == 17 || i == 26) {
				binarySubnetMask += ".";
				mBits++;
			} else
				binarySubnetMask += "0";
		}
		
		//assigns the value to the class member that will hold that value for future references
		binarySubnetMaskString = binarySubnetMask;
		
		//calls "parent's" method for converting binary to decimal
		String subnetMask = convertBinaryToDecimal(binarySubnetMask);
		
		
		return subnetMask;
	}
	
	
	/**
	 * @param String subnet
	 * @return
	 */
	public String calculateNetworkAddress() {
		
		return subnet;
	}
	
	/**
	 * @param String subnet
	 * @return
	 */
	public String calculateBroadcastAddress() {
		
		int numberOfBitsToBeChanged = 32 - _maskBits; //calculates them by subtracting _maskBits from 32 (maximum bits)
		String temp = ""; //temporary string that used to store binary representation of broadcast address
		
		// converts decimal formated subnet to binary subnet
		String binaryFormatedSubnet = convertDecimalToBinary(formatedSubnet);
		//stores it in "temp" string except of explicit number of last bits, because they should be changed to ones
		for(int i = 0; i < (binaryFormatedSubnet.length() - numberOfBitsToBeChanged); i++) {
			temp += binaryFormatedSubnet.charAt(i);
		}
		for(int i = 0; i < numberOfBitsToBeChanged; i++) {
			temp += '1';
		}
		
		//stores value of "temp" in class variable for future references
		binaryBroadcastAddress = temp;
		
		//converts binary broadcast address to decimal
		binaryFormatedSubnet = convertBinaryToDecimal(temp);
		
		return binaryFormatedSubnet + " /" + _maskBits;
	}
	
	/**
	 * @param String subnet
	 * @return
	 */
	public String calculateHostAddresses() {
		
		String network = calculateNetworkAddress();
		String broadcast = calculateBroadcastAddress();
		String networkLastNumber = "";
		String broadcastLastNumber = "";
		int dotCounter = 0;
		
		//with "for" loop extracts the last number of address for network address
		for(int i = 0; i < network.length(); i++) {
			if(network.charAt(i) == '.' && dotCounter < 3) {
				dotCounter++;
				continue; //does not continue with the rest of the instructions in this iteration, and goes straight to the next one
			}
			
			if(dotCounter == 3 && network.charAt(i) != ' ' && network.charAt(i) != '/')
				networkLastNumber += network.charAt(i);
			else if(network.charAt(i) == ' ' || network.charAt(i) == '/')
				break; //break loop when it hits one of those characters
		}
		
		//resets the "dotCounter" to zero
		dotCounter = 0;
		
		//with "for" loop extracts the last number of address for broadcast address
				for(int i = 0; i < broadcast.length(); i++) {
					if(broadcast.charAt(i) == '.' && dotCounter < 3) {
						dotCounter++;
						continue; 
					}
					
					if(dotCounter == 3 && broadcast.charAt(i) != ' ' && broadcast.charAt(i) != '/')
						broadcastLastNumber += broadcast.charAt(i);
					else if(broadcast.charAt(i) == ' ' || broadcast.charAt(i) == '/')
						break; 
				}
		
		//for calculation of hosts, function needs integer representation of last numbers of network and broadcast addresses
		int net = Integer.parseInt(networkLastNumber);
		int broad = Integer.parseInt(broadcastLastNumber);
		
		//for calculating subnetID we need to deduct from the "net" int one
		int lastNumberInSubnetID = net - 1;
		
		//resets the "dotCounter" to zero
		dotCounter = 0;
				
		//and places new string in subnetID class member
		for(int i = 0; i < network.length(); i++) {
			if(broadcast.charAt(i) == '.' && dotCounter < 3) {
				dotCounter++;
			}
			
			if(dotCounter != 3)
				subnetID += network.charAt(i);
			else if(dotCounter == 3)
				break; //break loop when it hits one of those characters
		}
		//after the loop, adds to subnetID the "lastNumberInSubnetID"
		subnetID += "." + lastNumberInSubnetID;
		
		//number of hosts gets calculated by subtracting the last number of net from broadcast
		int hosts = broad - net;
		
		return Integer.toString(hosts);
	}
	
	public void display() {
		System.out.println("Network address: " + calculateNetworkAddress());
		System.out.println("Subnet Mask: " + calculateSubnetMask());
		System.out.println("Broadcast address: " + calculateBroadcastAddress());
		System.out.println("Number of hosts: " + calculateHostAddresses());
	}

	public int getMaskBits() {
		return _maskBits;
	}
	
	public String getFormatedSubnet() {
		return formatedSubnet;
	}
	
	public String getBinaryBroadcastAddress() {
		return binaryBroadcastAddress;
	}
	
	public String getBinarySubnetMaskString() {
		return binarySubnetMaskString;
	}
}