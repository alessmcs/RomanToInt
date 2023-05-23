// Alessandra Mancas - 22/05/2023
package com.example.testfx1;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Arrays;

public class Controller {
    @FXML
    private TextField textField;
    @FXML
    private Label resultBox;

    @FXML
    private Label yourResult;


    @FXML

    public void inputAction(ActionEvent actionEvent) throws Exception { // when enter is pressed on the textbox do an action
        // test to see user input (works)
        yourResult.setText("Your result: ");
        resultBox.setText(" ");
        String input = textField.getText();
        if(input.length() > 15){
            // TODO: fix errors like xixx
            yourResult.setText("Input is too long!");
            throw new Exception("Input is too long");
        }
        input = input.toUpperCase(); // convert everything to uppercase to avoid errors
        int result = romanToInt(input);
        resultBox.setText(String.valueOf(result));

    }

    public int romanToInt(String s) throws Exception {
        // parse the string and break it down into an array
        char[] roman = new char[s.length()];
        for (int i = 0; i < s.length(); i++) {
            roman[i] = s.charAt(i);
        }

        // parse through the array and compare characters
        int[] numbers = new int[s.length()];

        for (int i = 0; i < roman.length; i++) {
            char letter = roman[i];
            numbers[i] = convert(letter); // converts the letters to numbers
        } // when exiting the loop we get an array of ints

        int unit = 0; // unit variable keeps track of the "level" of the number
        ArrayList<Integer> subVal = new ArrayList<>(Arrays.asList(4, 9, 40, 90, 400, 900));
        int val = 0;
        Exception e = new Exception("Invalid input");

        for (int i = 0; i <= numbers.length - 1; i++) {
            if(numbers.length > 2){
                if (i != numbers.length - 1 && numbers[i] >= numbers[i + 1]) { // if it's not the last element and if the immediate next one is smaller or equal
                    int op = 0;
                    if (i + 2 <= numbers.length) {
                        // check for succession of 3 or more
                        if ( i >= 1 && numbers[i] == numbers [i+1] &&  numbers[i] == numbers[i-1] ) {
                            if ( ((numbers[i] == 5) | (numbers[i] == 50)) && (i - 2 >= 0 && numbers[i] == numbers[i-2])
                                    | (i + 2 < numbers.length && numbers[i] == numbers[i+2]) ){
                                yourResult.setText("Invalid input!");
                                throw e;                            }
                        }
                        if (i + 2 < numbers.length && numbers[i + 2] > numbers[i + 1]) {
                            // if the second next one is larger, sub the 2 and then add to the first one, then iterate
                            op = numbers[i + 2] - numbers[i + 1];
                            if (numbers.length > 3){
                                if ( (op/4)==1 | (op/9)==1 ){
                                    unit = 1;
                                } else if ( (op/4)==10 | (op/9)==10 ){
                                    unit = 10;
                                } else if ( (op/4)==100 | (op/9)==100 ){
                                    unit = 100;
                                }
                            }
                            if (!subVal.contains(op)) { throw e; }
                            // compare to the unit to make sure the croissance is followed but if unit = 0 its also ok xx
                            if(val > op && ( op < unit | unit == 0) ){  // is the next amount smaller than the one we're adding to?
                                val += numbers[i] + op;
                            } else if ( i == 0  && ( op < unit | unit == 0) && numbers[i] > op){
                                val += numbers[i] + op;
                            } else {
                                yourResult.setText("Invalid input!");
                                throw e;
                            }
                            i += 2; // avance de 2 bc you're skipping the numbers you subbed
                        } else {
                            op = numbers[i] + numbers[i + 1];
                            if ( ((numbers[i] == 5) | (numbers[i] == 50)) && ((numbers[i+1] == 5) | (numbers[i+1] == 50)) ){
                                yourResult.setText("Invalid input!");
                                throw e; // can't duplicate 5 or 50
                            }
                            if(val > op && i != 0 && ( op < unit | unit == 0)){
                                val += op;
                            } else if (i == 0) {
                                val += op;
                            } else {
                                yourResult.setText("Invalid input!");
                                throw e;
                            }
                            i += 1;
                        }
                    }
                } else if (i != numbers.length - 1 && numbers[i + 1] > numbers[i] ) { // if the next value is larger (soustraction) and not the last element
                    int op = 0;
                    op = numbers[i + 1] - numbers[i];

                    if ( (op/4)==1 | (op/9)==1 ){
                        unit = 1;
                    } else if ( (op/4)==10 | (op/9)==10 ){
                        unit = 10;
                    } else if ( (op/4)==100 | (op/9)==100 ){
                        unit = 100;
                    }

                    if (subVal.contains(op)) { //verify value
                        if(val > op && i != 0){ //  && ( op < unit | unit == 0)
                            val += op;
                        } else if (i == 0) {
                            val += op;
                        } else {
                            yourResult.setText("Invalid input!");
                            throw e;
                        }
                        i += 1;
                    } else {
                        yourResult.setText("Invalid input!");
                        throw e;                    }
                } else { // last element
                    if ( ((numbers[i] == 5) && (numbers[i-1] == 5)) | ((numbers[i] == 50) && (numbers[i-1] == 50))  ){
                        yourResult.setText("Invalid input!");
                        throw e; // can't duplicate 5 or 50
                    }
                    if(val > numbers[i] && i != 0 && ( numbers[i] < unit | unit == 0)){ // verify that the previous number isn't 5 or 50
                        val += numbers[i]; // add the last element by itself
                    } else if (i == 0 && numbers[i] < unit) {
                        val += numbers[i];
                    } else {
                        yourResult.setText("Invalid input!");
                        throw e;                    }
                }
            } else if(numbers.length == 2){ // if there are only 2 elements you also need to check the order
                if ( (numbers[i] == 5) | (numbers[i] == 50) | (numbers[i+1] == 50) | (numbers[i+1] == 50) ){
                    yourResult.setText("Invalid input!");
                    throw e; // can't duplicate 5 or 50
                }
                if (numbers[i] < numbers[i+1]){
                    val = numbers[i + 1] - numbers[i];
                } else {
                    val = numbers[i] + numbers[i + 1];
                }
                break;
            } else if (numbers.length == 1){
                val = numbers[i];
            }
        }
        return val;
    }
    public int convert(char c) throws Exception {
        int val = 0;
        switch(c){
            case 'I': val = 1; break;
            case 'V': val = 5; break;
            case 'X': val = 10; break;
            case 'L': val = 50; break;
            case 'C': val = 100; break;
            case 'D': val = 500;break;
            case 'M': val = 1000;break;
            default:
                yourResult.setText("Invalid input!");
                throw new Exception("Invalid input");
        }
        return val;
    }
}