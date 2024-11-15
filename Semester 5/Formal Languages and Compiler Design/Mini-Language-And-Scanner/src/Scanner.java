import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

public class Scanner {
    List<String> separators;
    List<String> operators;
    List<String> reservedWords;
    SymbolTable<Object> symbolTable;
    PIF pif;

    Scanner(SymbolTable<Object> symbolTable, PIF pif) {
        this.symbolTable = symbolTable;
        this.pif = pif;
        this.separators = new ArrayList<>();
        this.operators = new ArrayList<>();
        this.reservedWords = new ArrayList<>();
        readCodificationTable();
    }

    public Boolean isWhitespace(String token) {
        if (token.compareTo(" ") == 0 || token.compareTo("\n") == 0 || token.compareTo("\t") == 0)
            return true;
        return false;
    }

    public Boolean isValidConstant(String str) {
        if((str.startsWith("-") || str.startsWith("+") )&& ( Character.isDigit(str.charAt(1)) && str.charAt(1)=='0'))
        {
            boolean check = false;
            for (int i = 1; i < str.length(); i++) {
                char ch = str.charAt(i);
                if (!Character.isDigit(ch)) {
                    if(ch == '.' && !check)
                    {
                        check = true;
                    }
                    else
                        return false;
                }
            }
            return check;

        }

        // if (str.matches("[+-]?([0-9]*[.])?[0-9]+")) {
        //     return true;
        // }

        // Check ok number
        if ((str.startsWith("0") && str.length() > 1 && Character.isDigit(str.charAt(1))) || (
                (str.startsWith("-") || str.startsWith("+")) && str.charAt(1) == '0')) {
            return false;
        }

        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException ignored) {
        }

        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException ignored) {
        }

        return str.length() == 3 && str.startsWith("'") && str.endsWith("'");

    }

    public Boolean isValidIdentifier(String str) {
        if (str.isEmpty()) {
            return false;
        }

        if (!Character.isLetter(str.charAt(0)) && str.charAt(0) != '_') {
            return false;
        }

        for (int i = 1; i < str.length(); i++) {
            char ch = str.charAt(i);
            if (!Character.isLetterOrDigit(ch) && ch != '_') {
                return false;
            }
        }
        return true;
    }

    public Boolean isSeparator(String token) {
        for (String predefinedToken : this.separators) {
            if (predefinedToken.compareTo(token) == 0) {
                return true;
            }
        }
        return false;
    }

    public Boolean isOperator(String token) {
        for (String predefinedToken : this.operators) {
            if (predefinedToken.compareTo(token) == 0) {
                return true;
            }
            else if(token.startsWith(predefinedToken))
            {
                for (String otherToken : this.operators) {
                    if (otherToken.charAt(0) == token.charAt(1)) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public Boolean isReservedWord(String token) {
        for (String predefinedToken : reservedWords) {
            if (predefinedToken.compareTo(token) == 0) {
                return true;
            }
        }
        return false;
    }

    public Boolean isMethod(String token) {
        if (token.contains(".")) {
            boolean check = false;
            for (int i = 0; i < token.length(); i++) {
                char ch = token.charAt(i);

                if (!Character.isLetter(ch) && ch != '_') {
                    if(ch == '.' && !check)
                    {
                        check = true;
                    }
                    else
                        return false;
                }
            }
            return true;
        }
        return false;
    }

    public void readCodificationTable() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader("token.in"));
            String line = reader.readLine();
            int type = 0;
            while (line != null) {
                if (line.compareTo("[Separators]") == 0)
                    type = 1;
                else if (line.compareTo("[Operators]") == 0)
                    type = 2;
                else if (line.compareTo("[ReservedWords]") == 0)
                    type = 3;
                else {
                    if (type == 1)
                        this.separators.add(line);
                    if (type == 2)
                        this.operators.add(line);
                    if (type == 3)
                        this.reservedWords.add(line);
                }
                line = reader.readLine();
            }

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void insertInPIF(String token, String type){
        int position = symbolTable.find(token);
        if(position == -1)
            position = this.symbolTable.insert(token);
        if(type.compareTo("Constant")==0)
            token = "Constant";
        if(type.compareTo("Identifier")==0)
            token = "Identifier";
        pif.genPIF(token, position);
    }

    void checkForStringConstant(String token, StringTokenizer tokenizer){
        StringBuilder stringLiteral = new StringBuilder(token);

        if(token.length()==1)
        {
            token = tokenizer.nextToken();
            stringLiteral.append(token);
        }

        while (!token.endsWith("\"") && tokenizer.hasMoreTokens()) {
            token = tokenizer.nextToken();
            stringLiteral.append(token);
        }

        token = stringLiteral.toString();

        if (token.startsWith("\"") && token.endsWith("\"")) {
            insertInPIF(token, "Constant");
        }
    }

    public void scan(String fileName) {
        try {
            int lineNumber = 1;
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                //System.out.println(line);
                StringTokenizer tokenizer = new StringTokenizer(line, String.join("", this.separators) + "\n" + " ", true);
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    if (this.isWhitespace(token))
                        continue;
                    if (this.isSeparator(token) || this.isOperator(token) || this.isReservedWord(token))
                        pif.genPIF(token, -1);
                    else if (this.isValidConstant(token))
                    {
                        this.insertInPIF(token,"Constant");
                    }
                    else if (token.startsWith("\"")) {
                        this.checkForStringConstant(token, tokenizer);
                    }
                    else if(this.isValidIdentifier(token)) {
                        this.insertInPIF(token, "Identifier");
                    } else if(token.startsWith("!")){
                        token = token.substring(1);
                        if(this.isValidIdentifier(token)) {
                            pif.genPIF("!",-1);
                            continue;
                        }
                    }
                    else if (this.isMethod(token)) {
                        StringTokenizer stringTokenizer = new StringTokenizer(token, ".");
                        while(stringTokenizer.hasMoreTokens()){
                            String part = stringTokenizer.nextToken();
                            insertInPIF(part, "Other");
                        }
                    }
                    else if(token.startsWith("-") && this.isValidIdentifier(token.substring(1))){
                        pif.genPIF("-",-1);
                    }
                    else {
                        throw new Exception("Lexical Error at line number: " + lineNumber + ", " + token + " is not lexically correct.");
                    }
                    //System.out.println(token);
                }
                line = reader.readLine();
                lineNumber++;
            }
            System.out.println("Lexically correct");
            reader.close();
        }
        catch (Exception ex){
            System.out.println(ex.getMessage());
        }
        finally {
            this.symbolTable.printSymbolTree();
            this.pif.printPIF();
        }
    }
}
