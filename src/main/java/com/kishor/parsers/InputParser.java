package com.kishor.parsers;

import com.kishor.converter.TextToDigitConverter;
import javafx.util.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class InputParser {
    public List<String> parse(List<String> linesOfInputText) {
//        System.out.println("Input is: " + linesOfInputText);

        Map<String, List<String>> inputGroups = linesOfInputText.stream()
            .collect(Collectors.groupingBy(fullOneLineOfInput -> getInputType(fullOneLineOfInput)));

        Map<String, String> mp = parseSeed(inputGroups.get("seeds"));

        Map<String, Integer> hp = parseHints(inputGroups.get("hints"), mp);

        return parseQuestion(inputGroups.get("questions"), mp, hp);

    }

    private String getInputType(String str) {
        return str.split(" ").length == 3 ? "seeds" : (str.endsWith("?") ? "questions" : "hints");
    }

    private List<String> parseQuestion(List<String> ls, Map<String, String> seedMap, Map<String, Integer> hintMap) {
        return ls.stream().map(t -> decodeQuestion(t, seedMap, hintMap)).collect(Collectors.toList());

    }

    private String decodeQuestion(String str, Map<String, String> seedMap, Map<String, Integer> hintMap) {
        String tempStr = "";
        if (str.startsWith("how much is")) {
            tempStr = str.substring(str.indexOf("how much is") + 11, str.indexOf("?")).trim();
            return tempStr + " is " + decodeSeedInfo(tempStr, seedMap);

        }
        else if (
            str.startsWith("how many Credits is")) {
            tempStr = str.substring(str.indexOf("how many Credits is") + 19, str.indexOf("?")).trim();
            return tempStr + " is " + handleHintQuestions(tempStr, seedMap, hintMap) + " Credits";
        }
        else {
            return "I have no idea what you are talking about";
        }
    }

    private Integer handleHintQuestions(String str, Map<String, String> seedMap, Map<String, Integer> hintMap) {
        return Stream.of(str.substring(0, str.lastIndexOf(" "))).map(t->  decodeSeedInfo(t, seedMap))
            .mapToInt(seedValue -> hintMap.get( str.substring(str.lastIndexOf(" ") + 1, str.length())) * seedValue).sum();

    }

    private Map<String, String> parseSeed(List<String> ls) {
        return ls.stream().map(t -> t.split(" "))
            .map(seed -> new Pair<String, String>(seed[0].trim(), seed[2].trim()))
            .collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }

    private Map<String, Integer> parseHints(List<String> ls, Map<String, String> seedMap) {
        return ls.stream().map(str -> extractHint(str, seedMap)).collect(Collectors.toMap(Pair::getKey, Pair::getValue));
    }


    private Pair<String, Integer> extractHint(String str, Map seedMap) {

        List<String> hintPrefix = Arrays.asList(str.substring(0, str.indexOf("is") - 1).trim().split(" "));
        String hintText = hintPrefix.get(hintPrefix.size() - 1);

        List<String> seedData = hintPrefix.subList(0, hintPrefix.size() - 1);

        Integer seedsToDecode = decodeSeedInfo(String.join(" ", seedData), seedMap);

        String[] hintSuffix = str.substring(str.indexOf("is") + 2, str.length()).trim().split(" ");
        Integer hintValue = Integer.parseInt(hintSuffix[0]) / seedsToDecode;

        return new Pair<String, Integer>(hintText, hintValue);
    }

    private Integer decodeSeedInfo(String seed, Map<String, String> seedMap) {

        String seedText = seed;
        TextToDigitConverter decode = new TextToDigitConverter();

        for (Map.Entry<String, String> entry : seedMap.entrySet()) {

            seedText = seedText.replaceAll(entry.getKey(), entry.getValue());
        }

        return decode.convertTextToDigit(seedText.replaceAll(" ", ""));
    }


}
