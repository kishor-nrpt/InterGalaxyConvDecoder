package com.kishor.validator;

import java.util.regex.Pattern;

public class RomanNumberFormatValidator {
    private static final String repeatMoreThanThrice = "[I]{4}|[X]{4}|[C]{4}|[M]{4}";
    private static final String noRepetition = "[D]{2}|[L]{2}|[V]{2}";
    private String romanNumber;

    public RomanNumberFormatValidator(String romanNumber) {
        this.romanNumber = romanNumber;
    }

    public Boolean validate() {
        return (!Pattern.matches(repeatMoreThanThrice, romanNumber) || !Pattern.matches(noRepetition, romanNumber));
    }

    ;
}
