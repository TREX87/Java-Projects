package SubnetCalculator;

public class ConvertBinaryToDecimalAddress {
	
	public ConvertBinaryToDecimalAddress() {}
	
	/**
	 * convert binary address to decimal address
	 * @param args
	 */
	public String convertBinaryToDecimal(String binary) {
		
		//integers that will hold four decimal numbers in IP address  by dots, like "|255|.|255|.|1|.|0|" 
		int decimal1 = 0, decimal2 = 0, decimal3 = 0, decimal4 = 0;
		
		for(int i = 0; i < binary.length(); i++) {
			//indicates location of "dots" in binary address, and skips it
			if(i != 8 && i != 17 && i != 26) {
				
				//first decimal number in IP address
				if(i < 8) {
					
					decimal1 += (i == 0 && binary.charAt(i) == '1') ? 128 : 0;
					decimal1 += (i == 1 && binary.charAt(i) == '1') ? 64 : 0;
					decimal1 += (i == 2 && binary.charAt(i) == '1') ? 32 : 0;
					decimal1 += (i == 3 && binary.charAt(i) == '1') ? 16 : 0;
					decimal1 += (i == 4 && binary.charAt(i) == '1') ? 8 : 0;
					decimal1 += (i == 5 && binary.charAt(i) == '1') ? 4 : 0;
					decimal1 += (i == 6 && binary.charAt(i) == '1') ? 2 : 0;
					decimal1 += (i == 7 && binary.charAt(i) == '1') ? 1 : 0;
					
				//second decimal number in IP address
				} else if(i > 8 && i < 17) {
					
					decimal2 += (i == 9 && binary.charAt(i) == '1') ? 128 : 0;
					decimal2 += (i == 10 && binary.charAt(i) == '1') ? 64 : 0;
					decimal2 += (i == 11 && binary.charAt(i) == '1') ? 32 : 0;
					decimal2 += (i == 12 && binary.charAt(i) == '1') ? 16 : 0;
					decimal2 += (i == 13 && binary.charAt(i) == '1') ? 8 : 0;
					decimal2 += (i == 14 && binary.charAt(i) == '1') ? 4 : 0;
					decimal2 += (i == 15 && binary.charAt(i) == '1') ? 2 : 0;
					decimal2 += (i == 16 && binary.charAt(i) == '1') ? 1 : 0;
					
				//third decimal number in IP address
				} else if(i > 17 && i < 26) {
					
					decimal3 += (i == 18 && binary.charAt(i) == '1') ? 128 : 0;
					decimal3 += (i == 19 && binary.charAt(i) == '1') ? 64 : 0;
					decimal3 += (i == 20 && binary.charAt(i) == '1') ? 32 : 0;
					decimal3 += (i == 21 && binary.charAt(i) == '1') ? 16 : 0;
					decimal3 += (i == 22 && binary.charAt(i) == '1') ? 8 : 0;
					decimal3 += (i == 23 && binary.charAt(i) == '1') ? 4 : 0;
					decimal3 += (i == 24 && binary.charAt(i) == '1') ? 2 : 0;
					decimal3 += (i == 25 && binary.charAt(i) == '1') ? 1 : 0;
					
				//fourth decimal number in IP address
				} else if(i > 26) {
					
					decimal4 += (i == 27 && binary.charAt(i) == '1') ? 128 : 0;
					decimal4 += (i == 28 && binary.charAt(i) == '1') ? 64 : 0;
					decimal4 += (i == 29 && binary.charAt(i) == '1') ? 32 : 0;
					decimal4 += (i == 30 && binary.charAt(i) == '1') ? 16 : 0;
					decimal4 += (i == 31 && binary.charAt(i) == '1') ? 8 : 0;
					decimal4 += (i == 32 && binary.charAt(i) == '1') ? 4 : 0;
					decimal4 += (i == 33 && binary.charAt(i) == '1') ? 2 : 0;
					decimal4 += (i == 34 && binary.charAt(i) == '1') ? 1 : 0;
					
				}
			}
		}
		
		//creates string for storing the whole IP address
		String decimal = "";
		String temp = Integer.toString(decimal1);
		decimal += temp;
		decimal += '.';
		temp = Integer.toString(decimal2);
		decimal += temp;
		decimal += '.';
		temp = Integer.toString(decimal3);
		decimal += temp;
		decimal += '.';
		temp = Integer.toString(decimal4);
		decimal += temp;
		
		//returns decimal string with IP address
		return decimal;
	}
	
	/**
	 * convert decimal address to binary address
	 * @param decimal
	 * @return string
	 */
	public String convertDecimalToBinary(String decimal) {
		//local variables for each eight-bit part of decimal IP address
		int decimal1 = 0, decimal2 = 0, decimal3 = 0, decimal4 = 0;
		String temp1 = "", temp2 = "", temp3 = "", temp4 = "";
		
		
		for(int i = 0; i < decimal.length(); i++) {
			if(decimal.charAt(i) != '.') {
				if(i < 3) 
					temp1 += decimal.charAt(i);
				else if(i > 3 && i < 7)
					temp2 += decimal.charAt(i);
				else if(i > 7 && i < 11)
					temp3 += decimal.charAt(i);
				else if(i > 11)
					temp4 += decimal.charAt(i);				
			}  
		}
		
		//parses separated decimal eight-bit parts into separate integers
		decimal1 = Integer.parseInt(temp1);
		decimal2 = Integer.parseInt(temp2);
		decimal3 = Integer.parseInt(temp3);
		decimal4 = Integer.parseInt(temp4);
		
		//clears all temps strings
		temp1 = ""; temp2 = ""; temp3 = ""; temp4 = "";
		
		//calls the method for representing each eight-bit decimal part as a binary digits
		temp1 = makeEightBitBinary(decimal1);
		temp2 = makeEightBitBinary(decimal2);
		temp3 = makeEightBitBinary(decimal3);
		temp4 = makeEightBitBinary(decimal4);
		
		//returns the full binary represented IP address
		return temp1 + "." + temp2 + "." + temp3 + "." + temp4;
	}
	
	
	/**
	 * receives decimal and makes from it eight-bit binary representation 
	 * @param decimal
	 * @return temp
	 */
	public String makeEightBitBinary(int decimal) {
		//local string that function operates on
		String temp = "";
		//variable that used for iteration through binary values
		int i = 128;
		
		//loops through eight-bit decimal part
		while(i >= 1) {
			if(decimal != i && decimal > i) {
				temp += "1";
				decimal -= i;
			} else if(decimal == i) {
				temp += "1";
				decimal -= i;
			} else
				temp += "0";
			
			i = i / 2; //at every iteration divides iterator by two
		}
		
		return temp;
	}
}
