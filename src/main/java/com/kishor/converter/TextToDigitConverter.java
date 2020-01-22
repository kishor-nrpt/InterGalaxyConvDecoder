package com.kishor.converter;

import com.kishor.validator.RomanNumberFormatValidator;

import java.util.*;
import java.util.stream.Collectors;

public class TextToDigitConverter {

    private static final Map<String, Integer> CONVERSION_SCALE = createConversionScale();

    private static Map<String, Integer> createConversionScale() {
        Map<String, Integer> conversionScale = new HashMap<String, Integer>();
        conversionScale.put("I", 1);
        conversionScale.put("V", 5);
        conversionScale.put("X", 10);
        conversionScale.put("L", 50);
        conversionScale.put("C", 100);
        conversionScale.put("D", 500);
        conversionScale.put("M", 1000);
        return Collections.unmodifiableMap(conversionScale);
    }


    public Integer convertTextToDigit(String romanNumber) {

        RomanNumberFormatValidator validator = new RomanNumberFormatValidator(romanNumber);
        if (validator.validate()) {
            // Candidate for custom exceptions
            System.out.println("Invalid input");
            return 0;
        }

        Integer finalValue = 0;
        Integer previousElement = 0;

        List<Integer> listOfRomanValues = Arrays.asList(replaceCharactersWithValues(romanNumber).trim().split(" "))
            .stream()
            .map(g -> Integer.parseInt(g))
            .collect(Collectors.toList());

        ListIterator<Integer> romanValueListIterator = listOfRomanValues.listIterator();

        // fix this to have pretty logic
        while (romanValueListIterator.hasNext()) {
            Integer currentValue = romanValueListIterator.next();

            if (previousElement == 0 || previousElement >= currentValue) {
                finalValue = finalValue + previousElement;
                previousElement = currentValue;
            }
            else {
                previousElement = (currentValue - previousElement);
            }
        }

        return finalValue + previousElement;

    }

    private String replaceCharactersWithValues(String romanNumber) {
        String response = romanNumber;
        for (Map.Entry<String, Integer> entry : CONVERSION_SCALE.entrySet()) {
            response = response.replaceAll(entry.getKey(), entry.getValue() + " ");
        }
        return response;
    }

}
