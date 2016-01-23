package sudoku.fun.main;

public class Util {

	public static String valueToString(int value){	
		switch(value){
		case 16:
			return "G";
		case 15:
			return "F";
		case 14:
			return "E";
		case 13:
			return "D";
		case 12:
			return "C";
		case 11:
			return "B";
		case 10:
			return "A";
		case -1:
			return " ";
		default:
			return ""+value;
		}
	}
	
	public static int stringToValue(String s){
		switch(s){
		case "G":
			return 16;
		case "F":
			return 15;
		case "E":
			return 14;
		case "D":
			return 13;
		case "C":
			return 12;
		case "B":
			return 11;
		case "A":
			return 10;
		default:
			return Integer.parseInt(s);
		}
	}
	
}
