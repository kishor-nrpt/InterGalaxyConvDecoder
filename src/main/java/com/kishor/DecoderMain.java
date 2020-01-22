package com.kishor;

import com.kishor.parsers.InputParser;

import java.util.ArrayList;
import java.util.Scanner;

public class DecoderMain {
    public static void main(String[] args) {
        System.out.println("Application is running");
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> arrayLines = new ArrayList<>();
        String line;
        while(true){
            line = scanner.nextLine();
            if(line.equals("")){
                break;
            }
            else {
                arrayLines.add(line);
            }
        }

        InputParser ip  = new InputParser();
        ip.parse(arrayLines).forEach(System.out::println);
    }

}


