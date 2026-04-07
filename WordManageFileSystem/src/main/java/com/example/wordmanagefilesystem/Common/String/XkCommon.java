package com.example.wordmanagefilesystem.Common.String;

//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//


import java.io.PrintStream;

public class XkCommon {
    private static final String[] ROMAN_NUMBER = new String[]{" ", "Ⅰ", "Ⅱ", "Ⅲ", "Ⅳ", "Ⅴ", "Ⅵ", "Ⅶ", "Ⅷ", "Ⅸ"};
    private static final String[] MONEY_CHINESE_BIGCASE = new String[]{"零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"};
    private static final String[] MATCH_CHINESE_BIGCASE = new String[]{"拾", "亿", "仟", "佰", "拾", "万", "仟", "佰", "拾", "圆"};

    public XkCommon() {
    }

    public String[] separateWordMeaning(String targetWordMeaningString) {
        if (targetWordMeaningString != null && !targetWordMeaningString.trim().isEmpty()) {
            String[] getWordAllSingleMeaning = new String[getWordPunctionQuantity(wordStringTransfromCharArr(targetWordMeaningString)) + 1];
            String[] getMeaningAfterSepare = separateMeaningToArrays(targetWordMeaningString, getWordAllSingleMeaning);
            return getMeaningAfterSepare;
        } else {
            return new String[0];
        }
    }

    public boolean handleMeaningMatch(String[] wordMeaningAfterSeparation, String inputMeaning) {
        for(int i = 0; i < wordMeaningAfterSeparation.length; ++i) {
            if (wordMeaningAfterSeparation[i].equals(inputMeaning)) {
                return true;
            }
        }

        return false;
    }

    private static char[] wordStringTransfromCharArr(String targetWordMeaningString) {
        char[] getAllMeaningString = new char[targetWordMeaningString.length()];

        for(int i = 0; i < getAllMeaningString.length; ++i) {
            getAllMeaningString[i] = targetWordMeaningString.charAt(i);
        }

        return getAllMeaningString;
    }

    private static int getWordPunctionQuantity(char[] createCharCollecteMeaningStringcharAt) {
        int getWordPunctionQuantity = 0;

        for(int i = 0; i < createCharCollecteMeaningStringcharAt.length; ++i) {
            if (createCharCollecteMeaningStringcharAt[i] == '；' || createCharCollecteMeaningStringcharAt[i] == ';') {
                ++getWordPunctionQuantity;
            }
        }

        return getWordPunctionQuantity;
    }

    private static String[] separateMeaningToArrays(String targetWordMeaningString, String[] getWordAllSingleMeaning) {
        int[] meaningStringIndexCollection = getPunctionAndTweEdgePositionIndex(targetWordMeaningString);
        char[] charCollecteMeaningStringcharAt = wordStringTransfromCharArr(targetWordMeaningString);
        int index = 0;
        String[] getWordAllSingleMeaningArray = separateByPunctionPosition(getWordAllSingleMeaning, meaningStringIndexCollection, charCollecteMeaningStringcharAt, index);
        String[] getFinallyWordAllSingleMeaningArray = removeArraysNullByForeach(getWordAllSingleMeaningArray);
        return getFinallyWordAllSingleMeaningArray;
    }

    private static String[] separateByPunctionPosition(String[] getWordAllSingleMeaning, int[] meaningStringIndexCollection, char[] charCollecteMeaningStringcharAt, int index) {
        for(int i = index; i < meaningStringIndexCollection.length - 1; ++i) {
            int j;
            if (index == 0) {
                for(j = meaningStringIndexCollection[index]; j < meaningStringIndexCollection[index + 1]; ++j) {
                    getWordAllSingleMeaning[index] = getWordAllSingleMeaning[index] + charCollecteMeaningStringcharAt[j];
                }
            } else {
                for(j = meaningStringIndexCollection[index] + 1; j < meaningStringIndexCollection[index + 1]; ++j) {
                    getWordAllSingleMeaning[index] = getWordAllSingleMeaning[index] + charCollecteMeaningStringcharAt[j];
                }
            }

            ++index;
        }

        return getWordAllSingleMeaning;
    }

    private static String[] removeArraysNullByForeach(String[] getWordAllSingleMeaning) {
        String[] newGetWordAllSingleMeaning = new String[getWordAllSingleMeaning.length];

        for(int i = 0; i < getWordAllSingleMeaning.length; ++i) {
            newGetWordAllSingleMeaning[i] = removeArraysNull(getWordAllSingleMeaning[i], 3);
        }

        return newGetWordAllSingleMeaning;
    }

    private static String removeArraysNull(String stringBeforeSeparate, int separateIndex) {
        StringBuilder newString = new StringBuilder();

        for(int i = 0; i < stringBeforeSeparate.length(); ++i) {
            if (i > separateIndex) {
                newString.append(stringBeforeSeparate.charAt(i));
            }
        }

        return newString.toString();
    }

    private static int[] getPunctionAndTweEdgePositionIndex(String targetWordMeaningString) {
        int[] getDotIndex = new int[getWordPunctionQuantity(wordStringTransfromCharArr(targetWordMeaningString)) + 2];
        getDotIndex[0] = 0;
        int index = 1;

        for(int i = 0; i < targetWordMeaningString.length(); ++i) {
            if (targetWordMeaningString.charAt(i) == '；' || targetWordMeaningString.charAt(i) == ';') {
                getDotIndex[index] = i;
                ++index;
            }
        }

        getDotIndex[getDotIndex.length - 1] = targetWordMeaningString.length();
        return getDotIndex;
    }

    public String removeContinuousRepeation(String string) {
        if (string != null && !string.trim().isEmpty()) {
            String[] strings = stringTransformStringArrays(string);
            int[] repeationStringIndex = getRepeationStringIndex(strings);
            int repeationtotal = getRepeationtotal(repeationStringIndex);
            String getRemoveRepeation = removeRepeationStringByIndex(strings, repeationStringIndex, repeationtotal);
            return getRemoveRepeation;
        } else {
            return "";
        }
    }

    public String remainUniqueString(String string) {
        if (string != null && !string.trim().isEmpty()) {
            String[] strings = stringTransformStringArrays(string);
            String[] getUniqueStringToArrays = getUniqueStringToArrays(strings);
            String finallString = stringArraysTransformStringAndRemoveNull(getUniqueStringToArrays);
            return finallString;
        } else {
            return "";
        }
    }

    private static String[] stringTransformStringArrays(String string) {
        String[] stringArr = new String[string.length()];

        for(int i = 0; i < stringArr.length; ++i) {
            stringArr[i] = String.valueOf(string.charAt(i));
        }

        return stringArr;
    }

    private static int[] getRepeationStringIndex(String[] stringArr) {
        int getRepeationPositionIndex = 0;
        int[] getRepeationPosition = new int[stringArr.length];

        for(int j = 0; j < stringArr.length - 1; ++j) {
            if (stringArr[j].equals(stringArr[j + 1])) {
                getRepeationPosition[getRepeationPositionIndex] = j + 1;
                ++getRepeationPositionIndex;
            }
        }

        return getRepeationPosition;
    }

    private static int getRepeationtotal(int[] getRepeationStringIndex) {
        int total = 0;

        for(int i = 0; i < getRepeationStringIndex.length; ++i) {
            if (getRepeationStringIndex[i] != 0) {
                ++total;
            }
        }

        return total;
    }

    private static String removeRepeationStringByIndex(String[] stringArr, int[] getRepeationStringIndex, int repeationTotal) {
        String[] newStringArr = new String[stringArr.length - repeationTotal - 1];
        int index = 0;
        int newStringArrIndex = 0;

        for(int i = 0; i < stringArr.length; ++i) {
            if (iIsMatchArraysElement(i, getRepeationStringIndex)) {
                ++index;
            } else {
                newStringArr[newStringArrIndex] = stringArr[i];
                ++newStringArrIndex;
            }
        }

        String string = stringArraysTransfromString(newStringArr);
        return string;
    }

    private static String stringArraysTransfromString(String[] stringArrays) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < stringArrays.length; ++i) {
            stringBuilder.append(stringArrays[i]);
        }

        return stringBuilder.toString();
    }

    private static boolean iIsMatchArraysElement(int currentInedx, int[] repeationPositions) {
        int[] var2 = repeationPositions;
        int var3 = repeationPositions.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            int repeationPosition = var2[var4];
            if (currentInedx == repeationPosition) {
                return true;
            }
        }

        return false;
    }

    private static String[] getUniqueStringToArrays(String[] strings) {
        int newStringArrIndex = 0;
        String[] newStringArr = new String[strings.length];

        for(int i = 0; i < strings.length; ++i) {
            if (!stringsIsContainString(strings[i], newStringArr)) {
                newStringArr[newStringArrIndex] = strings[i];
                ++newStringArrIndex;
            }
        }

        return newStringArr;
    }

    private static String stringArraysTransformStringAndRemoveNull(String[] strings) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < strings.length; ++i) {
            if (strings[i] != null) {
                stringBuilder.append(strings[i]);
            }
        }

        return stringBuilder.toString();
    }

    private static boolean stringsIsContainString(String string, String[] strings) {
        String[] var2 = strings;
        int var3 = strings.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String stringsElement = var2[var4];
            if (string.equals(stringsElement)) {
                return true;
            }
        }

        return false;
    }

    public String reverseMainMethod(String string) {
        if (string != null && !string.trim().isEmpty()) {
            char[] chars = stringTransformCharArrays(string);
            String reverseResult = reverseCharArraysToString(chars);
            return reverseResult;
        } else {
            return "";
        }
    }

    private static char[] stringTransformCharArrays(String string) {
        char[] charArr = new char[string.length()];

        for(int i = 0; i < string.length(); ++i) {
            charArr[i] = string.charAt(i);
        }

        return charArr;
    }

    private static String reverseCharArraysToString(char[] charArr) {
        StringBuilder getReverseString = new StringBuilder();

        for(int i = charArr.length - 1; i >= 0; --i) {
            getReverseString.append(charArr[i]);
        }

        return getReverseString.toString();
    }

    public String fullWritingFormOfChineseStandardCapitalAmountNumerals(String number) {
        if (number != null && !number.trim().isEmpty()) {
            String money = chineseBigCasePolicyFormat_Dynamic(number).toString();
            return combineChineseBigZero(money);
        } else {
            return "";
        }
    }

    public String AbbreviatedWritingFormOfChineseStandardCapitalAmountNumerals(String number) {
        return number != null && !number.trim().isEmpty() ? chineseBigCasePolicyFormat_Dynamic(number).toString() : "";
    }

    private static String[] splitAndPrintCorresponding_ChineseBigCase(String inputMoney) {
        int[] MoneyArray = new int[inputMoney.length()];
        String[] getEndingMoney = new String[inputMoney.length()];

        for(int i = 0; i < inputMoney.length(); ++i) {
            MoneyArray[i] = Integer.parseInt(String.valueOf(inputMoney.charAt(i)));

            for(int j = MoneyArray[i]; j <= MoneyArray[i]; ++j) {
                getEndingMoney[i] = MONEY_CHINESE_BIGCASE[j];
            }
        }

        return getEndingMoney;
    }

    private static String[] addZero(String[] splitAndPrintCorresponding_ChineseBigCase) {
        int lengthDifference = 10 - splitAndPrintCorresponding_ChineseBigCase.length;
        String[] addZero = new String[splitAndPrintCorresponding_ChineseBigCase.length + lengthDifference];
        if (splitAndPrintCorresponding_ChineseBigCase.length < 10) {
            int i;
            for(i = 0; i < lengthDifference; ++i) {
                addZero[i] = "零";
            }

            for(i = 0; i < splitAndPrintCorresponding_ChineseBigCase.length; ++i) {
                addZero[i + lengthDifference] = splitAndPrintCorresponding_ChineseBigCase[i];
            }
        }

        return addZero;
    }

    private static String[] flexibleBigcaseString_Pro(String[] splitAndPrintCorresponding_ChineseBigCase) {
        String[] reverse_MATCH_CHINESE_BIGCASE = new String[splitAndPrintCorresponding_ChineseBigCase.length];
        int reverse_Index = 0;

        for(int i = MATCH_CHINESE_BIGCASE.length - 1; i >= MATCH_CHINESE_BIGCASE.length - splitAndPrintCorresponding_ChineseBigCase.length; --i) {
            reverse_MATCH_CHINESE_BIGCASE[reverse_Index] = MATCH_CHINESE_BIGCASE[i];
            ++reverse_Index;
        }

        return attach_After_Reverse(splitAndPrintCorresponding_ChineseBigCase, reverse_MATCH_CHINESE_BIGCASE);
    }

    private static String[] attach_After_Reverse(String[] splitAndPrintCorresponding_ChineseBigCase, String[] reverse_MATCH_CHINESE_BIGCASE) {
        String[] endAttachStringArrary = new String[splitAndPrintCorresponding_ChineseBigCase.length];
        int splitAndPrintCorresponding_ChineseBigCase_index = 0;

        for(int i = reverse_MATCH_CHINESE_BIGCASE.length - 1; i >= 0; --i) {
            endAttachStringArrary[splitAndPrintCorresponding_ChineseBigCase_index] = splitAndPrintCorresponding_ChineseBigCase[splitAndPrintCorresponding_ChineseBigCase_index] + reverse_MATCH_CHINESE_BIGCASE[i];
            ++splitAndPrintCorresponding_ChineseBigCase_index;
        }

        return endAttachStringArrary;
    }

    private static StringBuilder chineseBigCasePolicyFormat_Dynamic(String money) {
        String[] getChineseBigCase_Arrary = splitAndPrintCorresponding_ChineseBigCase(money);
        String[] getDoubleArrary = flexibleBigcaseString_Pro(getChineseBigCase_Arrary);
        StringBuilder getchineseBigCasePolicyFormat_Dynamic_String = new StringBuilder();

        for(int i = 0; i < getDoubleArrary.length; ++i) {
            getchineseBigCasePolicyFormat_Dynamic_String.append(getDoubleArrary[i]);
        }

        return getchineseBigCasePolicyFormat_Dynamic_String;
    }

    private static String chineseBigCasePolicyFormat_Dynamic_Main(String money) {
        if (money != null && !money.trim().isEmpty()) {
            return chineseBigCasePolicyFormat_Dynamic(money).toString();
        } else {
            throw new IllegalArgumentException("Input number can't empty!!");
        }
    }

    private static String chineseBigCasePolicyFormat_Billion(String money) {
        String stringMoney = String.valueOf(money);
        String[] splitandprintcorresponding_chinesebigcase = splitAndPrintCorresponding_ChineseBigCase(stringMoney);
        String[] completeMoney = addZero(splitandprintcorresponding_chinesebigcase);
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < completeMoney.length; ++i) {
            stringBuilder.append(completeMoney[i]).append(MATCH_CHINESE_BIGCASE[i]);
        }

        return stringBuilder.toString();
    }

    private static String combineChineseBigZero(String NumberToChineseNumMain_Dynamic) {
        String result = NumberToChineseNumMain_Dynamic;

        for(int i = result.length() - 1; i >= 0; --i) {
            int[] getZeroIndex;
            if (result.charAt(i) == '零' && result.charAt(i - 2) == '零' && result.charAt(i - 4) == '零') {
                getZeroIndex = new int[]{i, i + 1, i - 2, i - 2 + 1, i - 4, i - 4 + 1};
                result = getTweZeroAndProcessDeleteZero(result, getZeroIndex);
                i = result.length() - 1;
            } else if (result.charAt(i) == '零' && result.charAt(i - 2) == '零') {
                getZeroIndex = new int[]{i, i + 1, i - 2, i - 2 + 1};
                result = getTweZeroAndProcessDeleteZero(result, getZeroIndex);
                i = result.length() - 1;
            } else if (result.charAt(i) == 20336 && result.charAt(i - 1) != '零' && result.charAt(i - 3) == '零' && result.charAt(i + 2) == '零') {
                getZeroIndex = new int[0];
            }
        }

        return result;
    }

    private static String getTweZeroAndProcessDeleteZero(String NumberToChineseNumMain_Dynamic, int[] getZeroIndex) {
        StringBuilder stringBuilder = new StringBuilder();
        PrintStream var10000;
        char var10001;
        int i;
        if (getZeroIndex.length == 4) {
            System.out.println(4);

            for(i = 0; i < NumberToChineseNumMain_Dynamic.length(); ++i) {
                if (i != getZeroIndex[0] && i != getZeroIndex[1] && i != getZeroIndex[2]) {
                    if (i == getZeroIndex[3]) {
                        stringBuilder.append("零");
                    } else {
                        stringBuilder.append(NumberToChineseNumMain_Dynamic.charAt(i));
                        var10000 = System.out;
                        var10001 = NumberToChineseNumMain_Dynamic.charAt(i);
                        var10000.println("遍历的char为" + var10001);
                    }
                } else {
                    stringBuilder.append("");
                }
            }

            return stringBuilder.toString();
        } else if (getZeroIndex.length != 6) {
            return NumberToChineseNumMain_Dynamic;
        } else {
            System.out.println(6);

            for(i = 0; i < NumberToChineseNumMain_Dynamic.length(); ++i) {
                if (i != getZeroIndex[0] && i != getZeroIndex[1] && i != getZeroIndex[2] && i != getZeroIndex[3] && i != getZeroIndex[4]) {
                    if (i == getZeroIndex[5]) {
                        stringBuilder.append("零");
                    } else {
                        stringBuilder.append(NumberToChineseNumMain_Dynamic.charAt(i));
                        var10000 = System.out;
                        var10001 = NumberToChineseNumMain_Dynamic.charAt(i);
                        var10000.println("遍历的char为" + var10001);
                    }
                } else {
                    stringBuilder.append("");
                }
            }

            return stringBuilder.toString();
        }
    }

    public String substring(String originString, String substringTargetString) {
        if (originString != null && !originString.trim().isEmpty()) {
            String string = matchForeachAndTargetString(originString, substringTargetString);
            return string;
        } else {
            return "";
        }
    }

    public String substring(String originString, String substringTargetString, int substringLength) {
        if (originString != null && !originString.trim().isEmpty()) {
            String string = matchForeachAndTargetString(originString, substringTargetString, substringLength);
            return string;
        } else {
            return "";
        }
    }

    public String substringIgnoreSpace(String originString, String substringTargetString, int substringLength) {
        if (originString != null && !originString.trim().isEmpty()) {
            String removeSpaceAfterRemoveSpace = this.removeSpaceHandle(originString);
            String string = matchForeachAndTargetStringIgnoreSpace(removeSpaceAfterRemoveSpace, substringTargetString, substringLength);
            return string;
        } else {
            return "";
        }
    }

    private static String matchForeachAndTargetString(String originString, String target) {
        int difference = target.length() - 1;

        for(int i = 0; i < originString.length(); ++i) {
            if (i + difference < originString.length() && originString.charAt(i) == target.charAt(0) && originString.charAt(i + difference) == target.charAt(difference)) {
                String substringOriginString = substringHandle(originString, i, i + difference);
                if (isEqualsStringAndStringEqualLength(substringOriginString, target)) {
                    return substringHandle(originString, i + difference);
                }
            }
        }

        return originString;
    }

    private static String matchForeachAndTargetStringIgnoreSpace(String originString, String target, int substringLength) {
        int difference = target.length() - 1;

        for(int i = 0; i < originString.length(); ++i) {
            if (i + difference < originString.length() && originString.charAt(i) == target.charAt(0) && originString.charAt(i + difference) == target.charAt(difference)) {
                String substringOriginString = substringHandle(originString, i, i + difference);
                if (isEqualsStringAndStringEqualLength(substringOriginString, target)) {
                    return substringHandle(originString, i + target.length(), i + difference + substringLength);
                }
            }
        }

        return originString;
    }

    private static String matchForeachAndTargetString(String originString, String target, int substringLength) {
        int difference = target.length() - 1;

        for(int i = 0; i < originString.length(); ++i) {
            if (i + difference < originString.length() && originString.charAt(i) == target.charAt(0) && originString.charAt(i + difference) == target.charAt(difference)) {
                String substringOriginString = substringHandle(originString, i, i + difference);
                if (isEqualsStringAndStringEqualLength(substringOriginString, target)) {
                    return substringHandle(originString, i + target.length(), i + difference + substringLength);
                }
            }
        }

        return originString;
    }

    private static String substringHandle(String originString, int substringStart) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < originString.length(); ++i) {
            if (substringStart < i) {
                stringBuilder.append(originString.charAt(i));
            }
        }

        return stringBuilder.toString();
    }

    public String substring(String originString, int substringStart) {
        return originString != null && !originString.trim().isEmpty() ? substringHandle(originString, substringStart) : "";
    }

    private static String substringHandle(String originString, int substringStart, int substringEnd) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < originString.length(); ++i) {
            if (i >= substringStart && i <= substringEnd) {
                stringBuilder.append(originString.charAt(i));
            }
        }

        return stringBuilder.toString();
    }

    public String substring(String originString, int substringStart, int substringEnd) {
        return originString != null && !originString.trim().isEmpty() ? substringHandle(originString, substringStart, substringEnd) : "";
    }

    private static boolean isEqualsStringAndStringEqualLength(String string1, String string2) {
        return string1.equals(string2);
    }

    public String removeSpaceHandle(String string) {
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0; i < string.length(); ++i) {
            if (string.charAt(i) != ' ') {
                stringBuilder.append(string.charAt(i));
            }
        }

        return stringBuilder.toString();
    }

    public String removeSpace(String string) {
        return this.removeSpaceHandle(string);
    }
}

