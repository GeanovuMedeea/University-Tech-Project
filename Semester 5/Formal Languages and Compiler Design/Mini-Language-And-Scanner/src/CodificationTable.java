public class CodificationTable {
    private static final String[][] identifierAndConstant = {
            {"identifier","0"},
            {"constant", "1"}
    };
    private static final String[][] separatorTokens = {
            {"[", "2"},
            {"]", "3"},
            {"{", "4"},
            {"}", "5"},
            {";", "6"},
            {":", "7"},
            {",", "9"},
            {" ", "10"},
            {"  ", "11"},
            {"\n", "12"}
    };

    private static final String[][] operatorTokens = {
            {"<", "10"},
            {"<=", "11"},
            {">", "12"},
            {">=", "13"},
            {"==", "14"},
            {"!=", "15"},
            {"!", "16"},
            {"&&", "17"},
            {"&", "18"},
            {"^", "19"},
            {"=", "20"},
            {"+", "21"},
            {"-", "22"},
            {"*", "23"},
            {"%", "24"},
            {"/", "25"}
    };

    private static final String[][] reservedWords = {
            // Add reserved words with their codes (Purple Theme)
            {"is_purple_real", "26"}, // equivalent to 'abstract'
            {"purple_vibe", "27"},    // equivalent to 'assert'
            {"yellow", "28"},         // equivalent to 'block'
            {"purplestance", "29"},   // equivalent to 'boolean'
            {"purple_overload", "30"}, // equivalent to 'break'
            {"shade", "31"},          // equivalent to 'case'
            {"purple_eject", "32"},   // equivalent to 'catch'
            {"lilac", "33"},          // equivalent to 'char'
            {"purple_life", "34"},    // equivalent to 'const'
            {"purple_on_soldier", "35"}, // equivalent to 'continue'
            {"last_season", "36"},    // equivalent to 'default'
            {"purple_order", "37"},   // equivalent to 'do'
            {"orchid", "38"},         // equivalent to 'double'
            {"purple_not", "39"},     // equivalent to 'else'
            {"lavander", "40"},       // equivalent to 'float'
            {"purple_roll", "41"},    // equivalent to 'for'
            {"purple_check", "42"},   // equivalent to 'if'
            {"violet", "43"},         // equivalent to 'int'
            {"extra_purple", "44"},   // equivalent to 'long'
            {"purple_in", "45"},   // equivalent to 'read'
            {"purple_out", "46"},   // equivalent to 'print'
            {"desaturated", "47"},    // equivalent to 'return'
            {"dusk_purple", "49"},    // equivalent to 'static'
            {"purple_arsenal", "50"}, // equivalent to 'switch'
            {"their_purple_highness", "51"}, // equivalent to 'this'
            {"purple_enemy", "52"},   // equivalent to 'throw'
            {"purple_friend_check", "53"}, // equivalent to 'try'
            {"tone_friends", "54"},   // equivalent to 'tuple'
            {"outerspace", "55"},     // equivalent to 'void'
            {"perpetual_purpling", "56"} // equivalent to 'while'
    };

    String[][] getSeparatorTokens(){return separatorTokens;}
    String[][] getOperatorTokens(){return operatorTokens;}
    String[][] getReservedWords(){return reservedWords;}


    int isIdentifierOrConstant(String token){
        for (String[] predefinedToken : identifierAndConstant) {
            if (predefinedToken[0].equals(token)) {
                return Integer.parseInt(predefinedToken[1]);
            }
        }
        return -1;
    }

    int isSeparator(String token){
        for (String[] predefinedToken : separatorTokens) {
            if (predefinedToken[0].equals(token)) {
                return Integer.parseInt(predefinedToken[1]);
            }
        }
        return -1;
    }

    int isOperator(String token){
        for (String[] predefinedToken : operatorTokens) {
            if (predefinedToken[0].equals(token)) {
                return Integer.parseInt(predefinedToken[1]);
            }
        }
        return -1;
    }

    int isReservedWord(String token){
        for (String[] predefinedToken : reservedWords) {
            if (predefinedToken[0].equals(token)) {
                return Integer.parseInt(predefinedToken[1]);
            }
        }
        return -1;
    }

    public static int getTokenCode(String token) {
        for (String[] predefinedToken : identifierAndConstant) {
            if (predefinedToken[0].equals(token)) {
                return Integer.parseInt(predefinedToken[1]);
            }
        }

        for (String[] predefinedToken : separatorTokens) {
            if (predefinedToken[0].equals(token)) {
                return Integer.parseInt(predefinedToken[1]);
            }
        }

        for (String[] predefinedToken : operatorTokens) {
            if (predefinedToken[0].equals(token)) {
                return Integer.parseInt(predefinedToken[1]);
            }
        }

        for (String[] predefinedToken : reservedWords) {
            if (predefinedToken[0].equals(token)) {
                return Integer.parseInt(predefinedToken[1]);
            }
        }
        return -1; // Token not found
    }

    public static void printCodificationTable() {
        for (String[] predefinedToken : identifierAndConstant) {
            System.out.println("Token: " + predefinedToken[0] + ", Code: " + predefinedToken[1]);
        }

        for (String[] predefinedToken : separatorTokens) {
            System.out.println("Token: " + predefinedToken[0] + ", Code: " + predefinedToken[1]);
        }

        for (String[] predefinedToken : operatorTokens) {
            System.out.println("Token: " + predefinedToken[0] + ", Code: " + predefinedToken[1]);
        }

        for (String[] predefinedToken : reservedWords) {
            System.out.println("Token: " + predefinedToken[0] + ", Code: " + predefinedToken[1]);
        }
    }
}
