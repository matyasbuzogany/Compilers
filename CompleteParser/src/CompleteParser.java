import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

public class CompleteParser {

    public enum Enumeration {
        keyword1("false",0,"keyword","K"),
        keyword2("true",1,"keyword","K"),
        keyword3("class",2,"keyword","K"),
        keyword4("public",3,"keyword","K"),
        keyword5("static",4,"keyword","K"),
        keyword6("this",5,"keyword","K"),
        keyword7("if",6,"keyword","K"),
        keyword8("for",7,"keyword","K"),
        keyword9("new",8,"keyword","K"),
        keyword10("else",9,"keyword","K"),
        keyword11("void",90,"keyword","K"),
        keyword12("boolean",91,"keyword","K"),
        keyword13("float",92,"keyword","K"),
        keyword14("private", 93, "keyword", "K"),
        keyword15("return", 94, "keyword", "K"),
        separator1("{",50,"separator","P"),
        separator2("}",51,"separator","P"),
        separator3("[",52,"separator","P"),
        separator4("]",53,"separator","P"),
        separator5("(",54,"separator","P"),
        separator6(")",55,"separator","P"),
        separator7(".",60,"separator","P"),
        separator8(",",61,"separator","P"),
        separator9(":",62,"separator","P"),
        separator10(";",63,"separator","P"),
        operator1("!",70,"operator","O"),
        operator2("&",71,"operator","O"),
        operator3("<",72,"operator","O"),
        operator4("=",73,"operator","O"),
        operator5("+",80,"operator","O"),
        operator6("-",81,"operator","O"),
        operator7("*",82,"operator","O"),
        operator8("/",83,"operator","O"),
        operator9(">",93,"operator","O");

        private String token;
        private int id;
        private String type;
        private String typeCode;

        Enumeration(String token,int id,String type,String typeCode){
            this.token=token;
            this.id=id;
            this.type=type;
            this.typeCode=typeCode;
        }

        public String getToken(){
            return this.token;
        }

        public String getTypeCode(){
            return this.typeCode;
        }

        public int getId(){
            return this.id;
        }
    }


    public static class Identifier {
        String name;
        String kind;
        String type;
        int lineOfDeclaration;
        String accessModifier;
        String scope;


        public Identifier(String name, String kind, String type, int lineOfDeclaration, String accessModifier, String scope) {
            this.name = name;
            this.kind = kind;
            this.type = type;
            this.lineOfDeclaration = lineOfDeclaration;
            this.accessModifier = accessModifier;
            this.scope = scope;
        }


        public String getName() {
            return name;
        }

        public String getKind() {
            return kind;
        }

        public String getType() {
            return type;
        }

        public int getLineOfDeclaration() {
            return lineOfDeclaration;
        }

        public String getAccessModifier() {
            return accessModifier;
        }

        public String getScope() {
            return scope;
        }
    }


    public static class TableRow {
        String token;
        String tokenID;
        String typeCode;
        int lineNumber;

        public TableRow(String token, String tokenID, String typeCode, int lineNumber) {
            this.token = token;
            this.tokenID = tokenID;
            this.typeCode = typeCode;
            this.lineNumber = lineNumber;
        }

        public String getToken() {
            return token;
        }

        public String getTokenID() {
            return tokenID;
        }

        public int getLineNumber() {
            return lineNumber;
        }
    }

    static ArrayList<TableRow> tableRows = new ArrayList<>();
    static ArrayList<Character> charList = new ArrayList<>(Arrays.asList('a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z','A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'R', 'S', 'T', 'U', 'Q', 'V', 'W', 'X', 'Y', 'Z'));
    static ArrayList<Character> digitList = new ArrayList<>(Arrays.asList('0', '1', '2', '3', '4', '5', '6', '7', '8', '9'));
    static ArrayList<String> keywordList = new ArrayList<>(Arrays.asList("false","true","class","public","static","this","if","for","new","else","int","void", "private", "float", "return"));
    static ArrayList<Character> operatorList = new ArrayList<>(Arrays.asList('!','&','=','+','-','*','/', '<', '>'));
    static ArrayList<Character> separatorList = new ArrayList<>(Arrays.asList('{','}','[',']','(',')','.',';',':',','));
    static ArrayList<String> accessModifierList = new ArrayList<>(Arrays.asList("public","private","protected"));
    static ArrayList<String> variableTypeList = new ArrayList<>(Arrays.asList("String", "float", "Auto", "ArrayList"));
    static ArrayList<String> functionNameList = new ArrayList<>(Arrays.asList("getFuelUsage", "nextFloat"));
    static ArrayList<String> conditionList = new ArrayList<>(Arrays.asList("<"));
    static ArrayList<Character> invalidCharacterList = new ArrayList<>(Arrays.asList('@','#','$','%','^'));

    static ArrayList<String> identifiers = new ArrayList<>();
    static ArrayList<String> numbers = new ArrayList<>();

    static String token;
    static LinkedList<String> tokenList = new LinkedList<>();

    static boolean isEmpty;
    static boolean isScanning = true;

    static int invalidCharacterLine;

    static ArrayList<Identifier> parseTable = new ArrayList<>();

    static String currentName;
    static String currentKind;
    static String currentType;
    static int currentLine;
    static String currentAccess;
    static String currentScope;
//    static int lineNR = 1;


    static void scan(char str[]) {

        int currentLineScanner = 0;
        int len = str.length;
        String word = "";
        String operator = "";
        boolean isString = false;
        int number = 0;
        int tokenID = 0;

        for (int i = 0; i < len; i++) {

            if (charList.contains(str[i])) {
                word += str[i];
            }

            else if (str[i] == '\"') {
                word += str[i];
                i += 1;
                while (str[i] != '\"') {
                    word += str[i];
                    i += 1;
                    isString = true;
                }
                word = word + "\"";
            }

            else if (digitList.contains(str[i])) {
                int j = Character.getNumericValue(str[i]);
                number = number * 10 + j;
            }

            else if (operatorList.contains(str[i])) {
                operator = operator + str[i];
            }

            else if (str[i] == '\n') {
                currentLineScanner = currentLineScanner + 1;
            }

            else if (invalidCharacterList.contains(str[i])) {
                isScanning = false;
                invalidCharacterLine = currentLineScanner;
                break;
            }

            else if ( (separatorList.contains(str[i])) || str[i] == ' ') {

                if ( !isString && (!word.equals("")) && (!keywordList.contains(word))) {
                    boolean inlist = false;
                    for (TableRow row: tableRows) {
                        if (row.getToken().equals(word)) {
                            inlist = true;
                            TableRow row2 = new TableRow ("I", word, "#", currentLineScanner);
                            tokenList.add(word);
                            tableRows.add(row2);
                            identifiers.add(word);
                            break;
                        }
                    }

                    if (!inlist) {
                        tokenList.add(word);
                        identifiers.add(word);
                        TableRow row1 = new TableRow("I", word, "#", currentLineScanner);
                        tableRows.add(row1);
                    }
                }

                else if ( !isString && word != "" && keywordList.contains(word)) {
                    for (Enumeration enumeration: Enumeration.values()) {
                        if (enumeration.getToken().equals(word)) {
                            String id = String.valueOf(enumeration.getId());
                            TableRow tableRow = new TableRow(enumeration.getTypeCode(), enumeration.getToken(), id, currentLineScanner);
                            tokenList.add(word);
                            tableRows.add(tableRow);
                        }
                    }
                }

                else if (isString && word != "") {
                    boolean inlist = false;
                    for (TableRow row: tableRows) {
                        if (row.getToken().equals(word)) {
                            inlist = true;
                            tokenID++;
                            TableRow row2 = new TableRow("S", word, "#", currentLineScanner);
                            tableRows.add(row2);
                            identifiers.add(word);
                            tokenList.add(word);
                            break;
                        }
                    }

                    if (!inlist) {
                        tokenID++;
                        TableRow r2 = new TableRow("S", word, "#", currentLineScanner);
                        tableRows.add(r2);
                        identifiers.add(word);
                        tokenList.add(word);
                    }
                }

                else if (number != 0) {
                    boolean inlist = false;
                    String k = String.valueOf(number);
                    for (TableRow row: tableRows) {
                        if (row.getToken().equals(k)) {
                            inlist = true;
                            tokenID++;
                            TableRow row3 = new TableRow("N", k, "#", currentLineScanner);
                            tableRows.add(row3);
                            numbers.add(k);
                            tokenList.add(k);
                            break;
                        }
                    }

                    if (!inlist) {
                        tokenID++;
                        TableRow r3 = new TableRow("N", k, "#", currentLineScanner);
                        tableRows.add(r3);
                        numbers.add(k);
                        tokenList.add(k);
                    }
                }

                else if (operator != "") {
                    for (Enumeration enumeration: Enumeration.values()) {
                        if (enumeration.getToken().equals(operator)) {

                            String id = String.valueOf(enumeration.getId());
                            TableRow row4 = new TableRow(enumeration.getTypeCode(), enumeration.getToken(), id, currentLineScanner);
                            tableRows.add(row4);
                            tokenList.add(operator);
                        }
                    }
                }

                if (separatorList.contains(str[i])) {
                    String s = String.valueOf(str[i]);
                    for (Enumeration enumeration: Enumeration.values()) {
                        if (enumeration.getToken().equals(s)) {
                            String id = String.valueOf(enumeration.getId());
                            TableRow row5 = new TableRow(enumeration.getTypeCode(), enumeration.getToken(), id, currentLineScanner);
                            tableRows.add(row5);
                            tokenList.add(s);
                        }
                    }
                }

                isString = false;
                word = "";
                number = 0;
                operator = "";
            }
        }
    }






    public static void parse() {
        classDeclaration();
    }


    public static void classDeclaration() {

        if (token.equals("class")) {
            currentKind = "Class";
            currentAccess = "Public";
            currentType = "-";

            get();

            if (identifiers.contains(token)) {
                currentName = token;

                get();

                if (token.equals("{")) {
                    System.out.println("Class declaration found!");
                    currentScope = "Global";
                    boolean found = false;

                    for (TableRow row: tableRows) {
                        if (row.getTokenID().equals(currentName) && !found) {
                            currentLine = row.getLineNumber();
                            found = true;
                        }
                    }
                    Identifier identifier = new Identifier(currentName, currentKind, currentType, currentLine, currentAccess, currentScope);
                    parseTable.add(identifier);
                    currentScope = currentName;
                    get();
                    statement();
                }
            }
        }
    }


    public static void declare1() {

        //assigning value
        if (token.equals("=")) {
            currentKind = "Variable";
            boolean found = false;
            for (TableRow row : tableRows) {
                if ((row.getTokenID().equals(currentName)) && !found) {
                    currentLine = row.getLineNumber();
                    found = true;
                }
            }
            Identifier identifier = new Identifier(currentName, currentKind, currentType, currentLine, currentAccess, currentScope);
            parseTable.add(identifier);
            get();
            valueAssignment();
        }

        else if (token.equals(";")) {
            currentKind = "Variable";
            boolean found = false;

            for (TableRow row : tableRows) {
                if ((row.getTokenID().equals(currentName)) && !found) {
                    System.out.println("found variable");
                    currentLine = row.getLineNumber();
                    found = true;
                }
            }
            Identifier identifier = new Identifier(currentName, currentKind, currentType, currentLine, currentAccess, currentScope);
            parseTable.add(identifier);
            System.out.println("Variable Declaration found!");
            get();
        }

        //for getter functions
        else if (token.equals("(")) {
            get();
            if (token.equals(")")) {
                get();
                if (token.equals("{")) {
                    get();
                    if (token.equals("return")) {
                        get();
                        if (identifiers.contains(token)) {
                            get();
                            if (token.equals(";")) {
                                get();
                                if (token.equals("}")) {
                                    System.out.println("Getter function found!");
                                    currentKind = "Method";
                                    boolean found = false;

                                    for (TableRow row : tableRows) {
                                        if ((row.getTokenID().equals(currentName)) && !found) {
                                            currentLine = row.getLineNumber();
                                            found = true;
                                        }
                                    }
                                    Identifier identifier = new Identifier(currentName, currentKind, currentType, currentLine, currentAccess, currentScope);
                                    parseTable.add(identifier);
                                    get();
                                } else {
                                    System.out.println("Parsing Error at getter method!");
                                    System.exit(1);
                                }
                            } else {
                                System.out.println("Parsing Error at getter method!");
                                System.exit(1);
                            }
                        } else {
                            System.out.println("Parsing Error at getter method!");
                            System.exit(1);
                        }
                    } else {
                        System.out.println("Parsing Error at getter method!");
                        System.exit(1);
                    }
                } else {
                    System.out.println("Parsing Error at getter method!");
                    System.exit(1);
                }
            } else {
                System.out.println("Parsing Error at getter method!");
                System.exit(1);
            }
        }
        else {
            System.out.println("Parsing Error at getter method!");
            System.exit(1);
        }
    }


    public static void valueAssignment() {

        if (identifiers.contains(token)) {
//            System.out.println("keyboard kene legyen: " + token);
            get();
            valueAssignmentStrich();
        }

        else if (token.equals("new")) {
            get();
            if (variableTypeList.contains(token)) {
                get();
                valueAssingmentUserDefined();
            } else {
                System.out.println("Parsing Error at Value Assignment!");
                System.exit(1);
            }
        }
    }


    public static void valueAssignmentStrich() {
        //calling method
        if (token.equals(".")) {
//            System.out.println(". kene legyen: " + token);
            get();
            if (functionNameList.contains(token)) {
                get();
                if (token.equals("(")) {
                    get();
                    if (token.equals(")")) {
                        get();
                        if (token.equals(";")) {
                            get();
                            System.out.println("Value assigment with a Method found!");
                        } else {
                            System.out.println("Parsing Error at Value Assigment!");
                            System.exit(1);
                        }
                    } else {
                        System.out.println("Parsing Error at Value Assigment!");
                        System.exit(1);
                    }
                } else {
                    System.out.println("Parsing Error at Value Assigment!");
                    System.exit(1);
                }
            } else {
                System.out.println("Parsing Error at Value Assigment!");
                System.exit(1);
            }
        } else {
            System.out.println("Parsing error at Value Assigment!");
            System.exit(1);
        }
    }


    public static void valueAssingmentUserDefined() {
        //List initialization
        if (token.equals("<")) {
            get();
            if (token.equals(">")){
                get();
                if (token.equals("(")) {
                    get();
                    if (token.equals(")")) {
                        get();
                        if (token.equals(";")) {
                            get();
                            System.out.println("List initialization found!");
                        } else {
                            System.out.println("Parsing Error at List initialization!");
                            System.exit(1);
                        }
                    } else {
                        System.out.println("Parsing Error at List initialization!");
                        System.exit(1);
                    }
                } else {
                    System.out.println("Parsing Error at List initialization!");
                    System.exit(1);
                }
            } else {
                System.out.println("Parsing Error at List initialization!");
                System.exit(1);
            }
        }

        else if (token.equals("(")) {
            get();
            if (identifiers.contains(token)) {
                currentName = token;
                currentType = "String";
                currentKind = "String-literal";
                currentAccess = "-";
                boolean found = false;

                for (TableRow row : tableRows) {
                    if ((row.getTokenID().equals(currentName)) && !found) {
                        currentLine = row.getLineNumber();
                        found = true;
                    }
                }
                Identifier identifier = new Identifier(currentName, currentKind, currentType, currentLine, currentAccess, currentScope);
                parseTable.add(identifier);
                get();

                if (token.equals(",")) {
                    get();

                    if (numbers.contains(token)) {
                        currentName = token;
                        currentType = "float";
                        currentKind = "Float-literal";
                        currentAccess = "-";
                        boolean foundd = false;

                        for (TableRow row : tableRows) {
                            if ((row.getTokenID().equals(currentName)) && !foundd) {
                                currentLine = row.getLineNumber();
                                foundd = true;
                            }
                        }
                        Identifier identifierr = new Identifier(currentName, currentKind, currentType, currentLine, currentAccess, currentScope);
                        parseTable.add(identifierr);
                        get();

                        if (token.equals(")")) {
                            get();
                            if (token.equals(";")) {
                                get();
                                System.out.println("User Defined Value assigment found!");
                            } else {
                                System.out.println("Parsing Error at valueAssingmentUserDefined!");
                                System.exit(1);
                            }
                        } else {
                            System.out.println("Parsing Error at valueAssingmentUserDefined!");
                            System.exit(1);
                        }
                    } else {
                        System.out.println("Parsing Error at valueAssingmentUserDefined!");
                        System.exit(1);
                    }
                } else {
                    System.out.println("Parsing Error at valueAssingmentUserDefined!");
                    System.exit(1);
                }
            } else {
                System.out.println("Parsing Error at valueAssingmentUserDefined!");
                System.exit(1);
            }
        }
    }


    public static int statement() {
        //declaration when there is an access modifier present
        if (accessModifierList.contains(token)) {
            currentAccess = token;
            get();

            if (variableTypeList.contains(token)) {
                currentType = token;
                get();

                if (identifiers.contains(token)) {
                    currentName = token;
                    get();
                    declare1();
                }

                //constructor
                else if (token.equals("(")) {
                    get();
                    if (variableTypeList.contains(token)) { //1
                        get();
                        if (identifiers.contains(token)) { //2
                            get();
                            if (token.equals(",")) { //3
                                get();
                                if(variableTypeList.contains(token)) { //4
                                    get();
                                    if (identifiers.contains(token)) { //5
                                        get();
                                        if (token.equals(")")) { //6
                                            get();
                                            if (token.equals("{")) { //7
                                                get();
                                                if (token.equals("this")) { //8
                                                    get();
                                                    if (token.equals(".")) { //9
                                                        get();
                                                        if (identifiers.contains(token)) { //10
                                                            get();
                                                            if (token.equals("=")) { //11
                                                                get();
                                                                if (identifiers.contains(token)) { //12
                                                                    get();
                                                                    if (token.equals(";")) { //13
                                                                        get();
                                                                        if (token.equals("this")) { //14
                                                                            get();
                                                                            if (token.equals(".")) { //15
                                                                                get();
                                                                                if (identifiers.contains(token)) { //16
                                                                                    get();
                                                                                    if (token.equals("=")) { //17
                                                                                        get();
                                                                                        if (identifiers.contains(token)) { //18
                                                                                            get();
                                                                                            if (token.equals(";")) { //19
                                                                                                get();
                                                                                                System.out.println("Found Constructor with Access Modifier!");
                                                                                            } else {
                                                                                                System.out.println("Parsing Error!"); //19
                                                                                                System.exit(1);
                                                                                            }
                                                                                        } else {
                                                                                            System.out.println("Parsing Error!"); //18
                                                                                            System.exit(1);
                                                                                        }
                                                                                    } else {
                                                                                        System.out.println("Parsing Error!"); //17
                                                                                        System.exit(1);
                                                                                    }
                                                                                } else {
                                                                                    System.out.println("Parsing Error!"); //16
                                                                                    System.exit(1);
                                                                                }
                                                                            } else {
                                                                                System.out.println("Parsing Error!"); //15
                                                                                System.exit(1);
                                                                            }
                                                                        } else {
                                                                            System.out.println("Parsing Error!"); //14
                                                                            System.exit(1);
                                                                        }
                                                                    } else {
                                                                        System.out.println("Parsing Error!"); //13
                                                                        System.exit(1);
                                                                    }
                                                                } else {
                                                                    System.out.println("Parsing Error!"); //12
                                                                    System.exit(1);
                                                                }
                                                            } else {
                                                                System.out.println("Parsing Error!"); //11
                                                                System.exit(1);
                                                            }
                                                        } else {
                                                            System.out.println("Parsing Error!");  //10
                                                            System.exit(1);
                                                        }
                                                    } else {
                                                        System.out.println("Parsing Error!"); //9
                                                        System.exit(1);
                                                    }
                                                } else {
                                                    System.out.println("Parsing Error!"); //8
                                                    System.exit(1);
                                                }
                                            } else {
                                                System.out.println("Parsing Error!"); //7
                                                System.exit(1);
                                            }
                                        } else {
                                            System.out.println("Parsing Error!"); //6
                                            System.exit(1);
                                        }
                                    } else {
                                        System.out.println("Parsing Error!"); //5
                                        System.exit(1);
                                    }
                                } else {
                                    System.out.println("Parsing Error!"); //4
                                    System.exit(1);
                                }
                            } else {
                                System.out.println("Parsing Error!"); //3
                                System.exit(1);
                            }
                        } else {
                            System.out.println("Parsing Error!"); //2
                            System.exit(1);
                        }
                    } else {
                        System.out.println("Parsing Error!"); //1
                        System.exit(1);
                    }
                }
            }
            //main function
            else if (token.equals("static")) { //1
                get();
                if (token.equals("void")) { //2
                    get();
                    if (token.equals("main")) { //3
                        boolean found = false;
                        for (TableRow row : tableRows) {
                            if((row.getTokenID().equals(currentName)) && !found) {
                                currentLine = row.getLineNumber();
                                found = true;
                            }
                        }
                        Identifier i = new Identifier("main", "Method", "void", currentLine, "public", "Auto");
                        parseTable.add(i);
                        get();
                        if (token.equals("(")) { //4
                            get();
                            if (token.equals("String")) { //5
                                get();
                                if (token.equals("[")) { //6
                                    get();
                                    if (token.equals("]")) { //7
                                        get();
                                        if (token.equals("args")) { //8
                                            boolean foundd = false;
                                            for (TableRow row : tableRows) {
                                                if((row.getTokenID().equals(currentName)) && !foundd) {
                                                    currentLine = row.getLineNumber();
                                                    foundd = true;
                                                }
                                            }
                                            Identifier ii = new Identifier("args", "Variable", "String[]", currentLine, "private", "Auto.main");
                                            parseTable.add(ii);
                                            get();
                                            if (token.equals(")")) { //9
                                                get();
                                                if (token.equals("{")) { //10
                                                    currentScope = currentScope + ".main";
                                                    get();
                                                    System.out.println("Main program found!");
                                                    statement();
                                                } else {
                                                    System.out.println("Parsing Error at Main function!"); //10
                                                    System.exit(1);
                                                }
                                            } else {
                                                System.out.println("Parsing Error at Main function!"); //9
                                                System.exit(1);
                                            }
                                        } else {
                                            System.out.println("Parsing Error at Main function!"); //8
                                            System.exit(1);
                                        }
                                    } else {
                                        System.out.println("Parsing Error at Main function!"); //7
                                        System.exit(1);
                                    }
                                } else {
                                    System.out.println("Parsing Error at Main function!"); //6
                                    System.exit(1);
                                }
                            } else {
                                System.out.println("Parsing Error at Main function!"); //5
                                System.exit(1);
                            }
                        } else {
                            System.out.println("Parsing Error at Main function!"); //4
                            System.exit(1);
                        }
                    } else {
                        System.out.println("Parsing Error at Main function!"); //3
                        System.exit(1);
                    }
                } else {
                    System.out.println("Parsing Error at Main function!"); //2
                    System.exit(1);
                }
            }
        }

        //declaration without access modifier
        else if (variableTypeList.contains(token)) {
//            System.out.println("Float kene legyen aztan: " + token);
            currentAccess = "-";
            currentType = token;
            get();
            if (identifiers.contains(token)) {
//                System.out.println("desiredEconomy kene legyen: " + token);
                currentName = token;
                get();
                declare1();
            }

            //constructor
            else if (token.equals("(")) {
                get();
                if (variableTypeList.contains(token)) { //1
                    get();
                    if (identifiers.contains(token)) { //2
                        get();
                        if (token.equals(",")) { //3
                            get();
                            if(variableTypeList.contains(token)) { //4
                                get();
                                if (identifiers.contains(token)) { //5
                                    get();
                                    if (token.equals(")")) { //6
                                        get();
                                        if (token.equals("{")) { //7
                                            get();
                                            if (token.equals("this")) { //8
                                                get();
                                                if (token.equals(".")) { //9
                                                    get();
                                                    if (identifiers.contains(token)) { //10
                                                        get();
                                                        if (token.equals("=")) { //11
                                                            get();
                                                            if (identifiers.contains(token)) { //12
                                                                get();
                                                                if (token.equals(";")) { //13
                                                                    get();
                                                                    if (token.equals("this")) { //14
                                                                        get();
                                                                        if (token.equals(".")) { //15
                                                                            get();
                                                                            if (identifiers.contains(token)) { //16
                                                                                get();
                                                                                if (token.equals("=")) { //17
                                                                                    get();
                                                                                    if (identifiers.contains(token)) { //18
                                                                                        get();
                                                                                        if (token.equals(";")) { //19
                                                                                            get();
                                                                                            if (token.equals("}")) {
                                                                                                get();
                                                                                                System.out.println("Constructor found without Access Modifier!");
                                                                                            } else {
                                                                                                System.out.println("Parsing Error at Constructor without Access Modifier!");
                                                                                                System.exit(1);
                                                                                            }
                                                                                        } else {
                                                                                            System.out.println("Parsing Error at Constructor without Access Modifier!"); //19
                                                                                            System.exit(1);
                                                                                        }
                                                                                    } else {
                                                                                        System.out.println("Parsing Error at Constructor without Access Modifier!"); //18
                                                                                        System.exit(1);
                                                                                    }
                                                                                } else {
                                                                                    System.out.println("Parsing Error at Constructor without Access Modifier!"); //17
                                                                                    System.exit(1);
                                                                                }
                                                                            } else {
                                                                                System.out.println("Parsing Error at Constructor without Access Modifier!"); //16
                                                                                System.exit(1);
                                                                            }
                                                                        } else {
                                                                            System.out.println("Parsing Error at Constructor without Access Modifier!"); //15
                                                                            System.exit(1);
                                                                        }
                                                                    } else {
                                                                        System.out.println("Parsing Error at Constructor without Access Modifier!"); //14
                                                                        System.exit(1);
                                                                    }
                                                                } else {
                                                                    System.out.println("Parsing Error at Constructor without Access Modifier!"); //13
                                                                    System.exit(1);
                                                                }
                                                            } else {
                                                                System.out.println("Parsing Error at Constructor without Access Modifier!"); //12
                                                                System.exit(1);
                                                            }
                                                        } else {
                                                            System.out.println("Parsing Error at Constructor without Access Modifier!"); //11
                                                            System.exit(1);
                                                        }
                                                    } else {
                                                        System.out.println("Parsing Error at Constructor without Access Modifier!");  //10
                                                        System.exit(1);
                                                    }
                                                } else {
                                                    System.out.println("Parsing Error at Constructor without Access Modifier!"); //9
                                                    System.exit(1);
                                                }
                                            } else {
                                                System.out.println("Parsing Error at Constructor without Access Modifier!"); //8
                                                System.exit(1);
                                            }
                                        } else {
                                            System.out.println("Parsing Error at Constructor without Access Modifier!"); //7
                                            System.exit(1);
                                        }
                                    } else {
                                        System.out.println("Parsing Error at Constructor without Access Modifier!"); //6
                                        System.exit(1);
                                    }
                                } else {
                                    System.out.println("Parsing Error at Constructor without Access Modifier!"); //5
                                    System.exit(1);
                                }
                            } else {
                                System.out.println("Parsing Error at Constructor without Access Modifier!"); //4
                                System.exit(1);
                            }
                        } else {
                            System.out.println("Parsing Error at Constructor without Access Modifier!"); //3
                            System.exit(1);
                        }
                    } else {
                        System.out.println("Parsing Error at Constructor without Access Modifier!"); //2
                        System.exit(1);
                    }
                } else {
                    System.out.println("Parsing Error at Constructor without Access Modifier!"); //1
                    System.exit(1);
                }
            }
        }

        //List declaration
        else if (token.equals("List")) {
//            System.out.println("List kene legyen " + token);
            get();
            currentAccess = "-";
            if(token.equals("<")) {
                get();
                if (variableTypeList.contains(token)) {
                    currentType = "List<" + token + ">";
                    get();
                    if (token.equals(">")) {
                        get();
                        if (identifiers.contains(token)) {
                            currentName = token;
                            currentKind = "Variable";
                            get();
                            declare1();
                        } else {
                            System.out.println("Parsing Error! Statement List declaration");
                            System.exit(1);
                        }
                    } else {
                        System.out.println("Parsing Error! Statement List declaration");
                        System.exit(1);
                    }
                } else {
                    System.out.println("Parsing Error! Statement List declaration");
                    System.exit(1);
                }
            } else {
                System.out.println("Parsing Error! Statement List declaration");
                System.exit(1);
            }
        }

        //add elements to list
        else if (token.equals("autos")) { //1
            get();
            if (token.equals(".")) { //2
                get();
                if (token.equals("add")) { //3
                    get();
                    if (token.equals("(")) { //4
                        get();
                        if (identifiers.contains(token)) { //5
                            get();
                            if (token.equals(")")) { //6
                                get();
                                if (token.equals(";")) { //7
                                    get();
                                    System.out.println("Adding Element to List!");
                                } else {
                                    System.out.println("Parsing Error at Adding Element to a List!"); //7
                                    System.exit(1);
                                }
                            } else {
                                System.out.println("Parsing Error at Adding Element to a List!"); //6
                                System.exit(1);
                            }
                        } else {
                            System.out.println("Parsing Error at Adding Element to a List!"); //5
                            System.exit(1);
                        }
                    } else {
                        System.out.println("Parsing Error at Adding Element to a List!"); //4
                        System.exit(1);
                    }
                } else {
                    System.out.println("Parsing Error at Adding Element to a List!"); //3
                    System.exit(1);
                }
            } else {
                System.out.println("Parsing Error at Adding Element to a List!"); //2
                System.exit(1);
            }
        }

        //Scanner declaration
        else if (token.equals("Scanner")) {
            currentType = "Scanner";
            currentKind = "Variable";
            currentAccess = "-";
            get();

            if (identifiers.contains(token)) { //1
                currentName = token;
                get();
                if (token.equals("=")) { //2
                    get();
                    if (token.equals("new")) { //3
                        get();
                        if (token.equals("Scanner")) { //4
                            get();
                            if (token.equals("(")) { //5
                                get();
                                if (token.equals("System")) { //6
                                    get();
                                    if (token.equals(".")) { //7
                                        get();
                                        if (token.equals("in")) { //8
                                            get();
                                            if (token.equals(")")) { //9
                                                get();
                                                if (token.equals(";")) { //10
                                                    boolean found = false;

                                                    for (TableRow row : tableRows) {
                                                        if ((row.getTokenID().equals(currentName)) && !found) {
                                                            currentLine = row.getLineNumber();
                                                            found = true;
                                                        }
                                                    }
                                                    Identifier identifier = new Identifier(currentName, currentKind, currentType, currentLine, currentAccess, currentScope);
                                                    parseTable.add(identifier);
                                                    get();
                                                    System.out.println("Scanner declaration found!");
                                                } else {
                                                    System.out.println("Parsing Error at Scanner declaration!");
                                                    System.exit(1);
                                                }
                                            } else {
                                                System.out.println("Parsing Error at Scanner declaration!");
                                                System.exit(1);
                                            }
                                        } else {
                                            System.out.println("Parsing Error at Scanner declaration!");
                                            System.exit(1);
                                        }
                                    } else {
                                        System.out.println("Parsing Error at Scanner declaration!");
                                        System.exit(1);
                                    }
                                } else {
                                    System.out.println("Parsing Error at Scanner declaration!");
                                    System.exit(1);
                                }
                            } else {
                                System.out.println("Parsing Error at Scanner declaration!");
                                System.exit(1);
                            }
                        } else {
                            System.out.println("Parsing Error at Scanner declaration!");
                            System.exit(1);
                        }
                    } else {
                        System.out.println("Parsing Error at Scanner declaration!");
                        System.exit(1);
                    }
                } else {
                    System.out.println("Parsing Error at Scanner declaration!");
                    System.exit(1);
                }
            } else {
                System.out.println("Parsing Error at Scanner declaration!");
                System.exit(1);
            }
        }

        //printline
        else if (token.equals("System")) {
            get();
            if (token.equals(".")) {
                get();
                if (token.equals("out")) {
                    get();
                    if (token.equals(".")) {
                        get();
                        if (token.equals("println")) {
                            get();
                            if (token.equals("(")) {
                                get();
                                if (identifiers.contains(token)) {
                                    get();
                                    if (token.equals(")")) {
                                        get();
                                        if (token.equals(";")) {
                                            get();
                                            System.out.println("Printline found!");
                                        } else {
                                            System.out.println("Parsing Error at Printline!");
                                            System.exit(1);
                                        }
                                    } else {
                                        System.out.println("Parsing Error at Printline!");
                                        System.exit(1);
                                    }
                                } else {
                                    System.out.println("Parsing Error at Printline!");
                                    System.exit(1);
                                }
                            } else {
                                System.out.println("Parsing Error at Printline!");
                                System.exit(1);
                            }
                        } else {
                            System.out.println("Parsing Error at Printline!");
                            System.exit(1);
                        }
                    } else {
                        System.out.println("Parsing Error at Printline!");
                        System.exit(1);
                    }
                } else {
                    System.out.println("Parsing Error at Printline!");
                    System.exit(1);
                }
            } else {
                System.out.println("Parsing Error at Printline!");
                System.exit(1);
            }
        }

        //for loop
        else if (token.equals("for")) {
            get();
            if (token.equals("(")) {
                get();
                if (variableTypeList.contains(token)) {
                    get();
                    if (identifiers.contains(token)) {

                        get();
                        if (token.equals(":")) {
                            get();
                            if (identifiers.contains(token)) {
                                get();
                                if (token.equals(")")) {
                                    get();
                                    if (token.equals("{")) {
                                        get();
                                        System.out.println("FOR Loop found!");
                                        statement();
                                    } else {
                                        System.out.println("Parsing Error at FOR Loop!");
                                        System.exit(1);
                                    }
                                } else {
                                    System.out.println("Parsing Error at FOR Loop!");
                                    System.exit(1);
                                }
                            } else {
                                System.out.println("Parsing Error at FOR Loop!");
                                System.exit(1);
                            }
                        } else {
                            System.out.println("Parsing Error at FOR Loop!");
                            System.exit(1);
                        }
                    } else {
                        System.out.println("Parsing Error at FOR Loop!");
                        System.exit(1);
                    }
                } else {
                    System.out.println("Parsing Error at FOR Loop!");
                    System.exit(1);
                }
            } else {
                System.out.println("Parsing Error at FOR Loop!");
                System.exit(1);
            }
        }

        //if statement
        else if (token.equals("if")) {
            get();
            if (token.equals("(")) {
                get();
                if (identifiers.contains(token)) {
                    get();
                    if (conditionList.contains(token)) {
                        get();
                        if (identifiers.contains(token)) {
                            get();
                            if (token.equals(")")) {
                                get();
                                if (token.equals("{")) {
                                    get();
                                    System.out.println("If statement found!");
                                    statement();
                                } else {
                                    System.out.println("Parsing Error at IF Statement!");
                                    System.exit(1);
                                }
                            } else {
                                System.out.println("Parsing Error at IF Statement!");
                                System.exit(1);
                            }
                        } else {
                            System.out.println("Parsing Error at IF Statement!");
                            System.exit(1);
                        }
                    } else {
                        System.out.println("Parsing Error at IF Statement!");
                        System.exit(1);
                    }
                } else {
                    System.out.println("Parsing Error at IF Statement!");
                    System.exit(1);
                }
            } else {
                System.out.println("Parsing Error at IF Statement!");
                System.exit(1);
            }
        }

        else if(token.equals("}")) {
            System.out.println("End of loop/method/class/program detected!");
            get();
            return 1;
        }
        statement();
        return 0;
    }



    public static void main(String[] args) {

        String sourceCode = "";

        if (args.length == 0) {
            System.out.println("Please provide a file for input!");
        }

        try {
            File file = new File(args[0]);
            Scanner input = new Scanner(file);
            while (input.hasNextLine()) {
                sourceCode = sourceCode + "\n" + input.nextLine();
            }
        } catch (IOException ioException) {
            System.out.println("Cannot open file");
        }


        char sourceCodeChar[] = sourceCode.toCharArray();
        scan(sourceCodeChar);

        //Output for the scanner
//        for (TableRow row: tableRows) {
//            System.out.println(row.getLineNumber() + " " + row.getTokenID() + " " + row.getToken());
//        }

        token = tokenList.remove();
        if (isScanning) {
            parse();
        } else {
            System.out.println("Invalid character at line: " + invalidCharacterLine);
        }


        try(PrintWriter writer = new PrintWriter(new File("symboltable.csv"))) {
            StringBuilder sb1 = new StringBuilder();
            sb1.append("Name");
            sb1.append(", ");
            sb1.append("Kind");
            sb1.append(", ");
            sb1.append("Type");
            sb1.append(", ");
            sb1.append("Line");
            sb1.append(", ");
            sb1.append("Modifier");
            sb1.append(", ");
            sb1.append("Scope");
            sb1.append("\n");
            writer.write(sb1.toString());

            for(Identifier identifier : parseTable){
                StringBuilder sb2 = new StringBuilder();
                sb2.append(identifier.getName());
                sb2.append(", ");

                sb2.append(identifier.getKind());
                sb2.append(", ");
                sb2.append(identifier.getType());
                sb2.append(", ");

                String lineNumber = String.valueOf(identifier.getLineOfDeclaration());
                sb2.append(lineNumber);
                sb2.append(", ");
                sb2.append(identifier.getAccessModifier());
                sb2.append(", ");
                sb2.append(identifier.getScope());

                sb2.append("\n");
                writer.write(sb2.toString());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    static void get() {
        if (!tokenList.isEmpty()) {
            token = tokenList.remove();
        } else {
            isEmpty = true;
        }
    }
}
