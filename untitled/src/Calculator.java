import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) {
        String statement;
        Scanner input = new Scanner(System.in);
        System.out.print("Write a statement to calculate:  ");
        statement = input.nextLine();
        System.out.println("Result = " + calc(statement));
    }

    public static String calc(String input) {
        input = input.replace(" ", "");

        if (input.charAt(0) >= 48 && input.charAt(0) <= 57)
            return arabicCalc(input);
        else if (input.charAt(0) == 'I' || input.charAt(0) == 'V' || input.charAt(0) == 'X')
            return romanCalc(input);
        else
            throw new IllegalArgumentException("Invalid digit input");
    }

    public static String arabicCalc(String str) {
        int index = 0, digitsCounter = 0, signCounter = 0;
        char sign = 0;
        int len = str.length();
        boolean flag = true;

        for (int i = 0; i < len; i++) {
            if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                digitsCounter++;
            } else if (str.charAt(i) == '+' || str.charAt(i) == '-' || str.charAt(i) == '*' || str.charAt(i) == '/'){
                sign = str.charAt(i);
                index = i;
                signCounter++;
            }
        }

        if ((3 <= len && len <= 5) && (len - digitsCounter == 1)) {
            int num1 = Integer.parseInt(str.substring(0, index));
            int num2 = Integer.parseInt(str.substring(index + 1));
            if ((1 <= num1 && num1 <= 10) && (1 <= num2 && num2 <= 10)) {
                return switch (sign) {
                    case '+' -> Integer.toString(num1 + num2);
                    case '-' -> Integer.toString(num1 - num2);
                    case '*' -> Integer.toString(num1 * num2);
                    case '/' -> Integer.toString(num1 / num2);
                    default -> throw new RuntimeException("Incorrect sign input");
                };
            }
            flag = false;
        }
        if (!flag)
            throw new UnsupportedOperationException("Input number out of range");
        else if (signCounter > 1) {
            throw new UnsupportedOperationException("Too many operand inputs");
        }
        throw new IllegalArgumentException("Different numeral types input");
    }


    public static String romanCalc(String str) {
        int signCounter = 0, digitCounter = 0, index = 0;
        char sign = 0;

        for (int i = 0; i < str.length(); i++){
            if (str.charAt(i) == 'I' || str.charAt(i) == 'V' || str.charAt(i) == 'X')
                digitCounter++;

            switch (str.charAt(i)) {
                case '+' -> {
                    signCounter++;
                    sign = '+';
                    index = i;
                }
                case '-' -> {
                    signCounter++;
                    sign = '-';
                    index = i;
                }
                case '*' -> {
                    signCounter++;
                    sign = '*';
                    index = i;
                }
                case '/' -> {
                    signCounter++;
                    sign = '/';
                    index = i;
                }
            }
        }

        if (signCounter == 0)
            throw new ArithmeticException("Operator missing");
        else if (signCounter >= 2)
            throw new UnsupportedOperationException("Too many operator inputs");
        else if (str.length() - digitCounter != 1)
            throw new IllegalArgumentException("Different numeral types input");
        else {
            int num1 = romanToInteger(str.substring(0,index));
            int num2 = romanToInteger(str.substring(index + 1));

            if (num1 == 0 || num2 == 0)
                throw new UnsupportedOperationException("Input number out of range");

            return integerToRoman(num1, num2, sign);
        }
    }


    public static int romanToInteger(String numeral){
        Map<String, Integer> romanNumerals = new HashMap<String, Integer>();
        romanNumerals.put("I", 1);
        romanNumerals.put("V", 5);
        romanNumerals.put("X", 10);
        romanNumerals.put("L", 50);
        romanNumerals.put("C", 100);

        numeral = numeral + "I";
        int sum = 0, len = numeral.length();

        for (int i = len - 2; i >= 0; i--){
            if (romanNumerals.get(numeral.substring(i, i + 1)) < romanNumerals.get(numeral.substring(i + 1, i + 2))){
                sum = sum - romanNumerals.get(numeral.substring(i, i + 1));
            }
            else {
                sum = sum + romanNumerals.get(numeral.substring(i, i + 1));
            }
        }

        if (1 <= sum && sum <= 10)
            return sum;
        else
            return 0;
    }


    public static String integerToRoman(int number1, int number2, char operation){
        int outcome = 0;
        String result = "";

        switch (operation){
            case '+':
                outcome = number1 + number2;
                break;
            case '-':
                outcome = number1 - number2;
                break;
            case '*':
                outcome = number1 * number2;
                break;
            case '/':
                outcome = number1 / number2;
                break;
        }

        if (outcome < 1)
            throw new IndexOutOfBoundsException("Result in roman numerals cannot be less than one");

        while (outcome >= 100){
            result += "C";
            outcome -= 100;
        }
        while (outcome >= 90){
            result += "XC";
            outcome -= 90;
        }
        while (outcome >= 50){
            result += "L";
            outcome -= 50;
        }
        while (outcome >= 40){
            result += "XL";
            outcome -= 40;
        }
        while (outcome >= 10){
            result += "X";
            outcome -= 10;
        }
        while (outcome >= 9){
            result += "IX";
            outcome -= 9;
        }
        while (outcome >= 5){
            result += "V";
            outcome -= 5;
        }
        while (outcome >= 4){
            result += "IV";
            outcome -= 4;
        }
        while (outcome >= 1){
            result += "I";
            outcome -= 1;
        }

        return result;
    }
}