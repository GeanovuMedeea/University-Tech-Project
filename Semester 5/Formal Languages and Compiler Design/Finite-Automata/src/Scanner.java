import java.io.BufferedReader;
import java.io.FileReader;
import java.util.*;

public class Scanner {
    List<String> separators;
    List<String> operators;
    List<String> reservedWords;
    SymbolTable<Object> symbolTable;
    PIF pif;
    private FA identifierFA;
    private FA integerConstantFA;


    Scanner(SymbolTable<Object> symbolTable, PIF pif) {
        this.symbolTable = symbolTable;
        this.pif = pif;
        this.separators = new ArrayList<>();
        this.operators = new ArrayList<>();
        this.reservedWords = new ArrayList<>();
        readCodificationTable();

        this.identifierFA = new FA();
        this.integerConstantFA = new FA();
        try {
            identifierFA.loadFAFromFile("IdentifierFA.in");
            integerConstantFA.loadFAFromFile("IntegerConstantFA.in");
        } catch (Exception e) {
            System.out.println("Error loading FAs: " + e.getMessage());
        }
    }

    public Boolean isWhitespace(String token) {
        return token.equals(" ") || token.equals("\n") || token.equals("\t");
    }

    public Boolean isValidConstant(String str) {
        boolean isCharConstant = (str.length() == 3 && str.startsWith("'") && str.endsWith("'"));
        return (integerConstantFA.isDFA() && integerConstantFA.acceptsSequence(str)) || isCharConstant;

    }

    public Boolean isValidIdentifier(String str) {
        return identifierFA.isDFA() && identifierFA.acceptsSequence(str);
    }

    public Boolean isSeparator(String token) {
        return this.separators.contains(token);
    }

    public Boolean isOperator(String token) {
        for (String predefinedToken : this.operators) {
            if (predefinedToken.equals(token)) {
                return true;
            } else if (token.startsWith(predefinedToken)) {
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
        return reservedWords.contains(token);
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
            if(isValidConstant(token))
                insertInPIF(token, "Constant");
        }
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
                if (line.equals("[Separators]")) type = 1;
                else if (line.equals("[Operators]")) type = 2;
                else if (line.equals("[ReservedWords]")) type = 3;
                else {
                    if (type == 1) this.separators.add(line);
                    if (type == 2) this.operators.add(line);
                    if (type == 3) this.reservedWords.add(line);
                }
                line = reader.readLine();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void insertInPIF(String token, String type) {
        int position = symbolTable.find(token);
        if (position == -1)
            position = this.symbolTable.insert(token);
        if (type.equals("Constant"))
            token = "Constant";
        if (type.equals("Identifier"))
            token = "Identifier";
        pif.genPIF(token, position);
    }

    public void scan(String fileName) {
        try {
            int lineNumber = 1;
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, String.join("", this.separators) + "\n" + " ", true);
                while (tokenizer.hasMoreTokens()) {
                    String token = tokenizer.nextToken();
                    if (this.isWhitespace(token))
                        continue;
                    if (this.isSeparator(token) || this.isOperator(token) || this.isReservedWord(token))
                        pif.genPIF(token, -1);
                    else if (this.isValidConstant(token)) {
                        this.insertInPIF(token, "Constant");
                    } else if (this.isValidIdentifier(token)) {
                        this.insertInPIF(token, "Identifier");
                    }
                    else if (token.startsWith("\"")) {
                        this.checkForStringConstant(token, tokenizer);
                    }else if(token.startsWith("!")){
                        token = token.substring(1);
                        if(this.isValidIdentifier(token)) {
                            pif.genPIF("!",-1);
                        }
                    }
                    else if (this.isMethod(token)) {
                        StringTokenizer stringTokenizer = new StringTokenizer(token, ".");
                        while(stringTokenizer.hasMoreTokens()){
                            String part = stringTokenizer.nextToken();
                            if(isValidIdentifier(part))
                                insertInPIF(part, "Other");
                        }
                    }else {
                        throw new Exception("Lexical Error at line number: " + lineNumber + ", " + token + " is not lexically correct.");
                    }
                }
                line = reader.readLine();
                lineNumber++;
            }
            System.out.println("Lexically correct");
            reader.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            this.symbolTable.printSymbolTree();
            this.pif.printPIF();
        }
    }
}
