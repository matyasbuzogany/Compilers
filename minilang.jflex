import java.util.*;
%%
%standalone
%line
%{
    int numberOfTokens 0;
    int numberOfIdentifiers = 0;
    int numberOfConstants = 0;
    int numberOfKeywords = 0;
    int numberOfOperators = 0;

    List<String> identifierList = new ArrayList<>();
    List<String> literalList = new ArrayList<>();
    List<String> keywordList = new ArrayList<>();
%}

%eof{
    System.out.println(SUMMARY: )
    System.out.println("\t" Tokens: " + numberOfTokens)
    System.out.println("\t" Identifiers: " + numberOfIdentifiers)
    System.out.println("\t" Constants: " + numberOfConstants)
    System.out.println("\t" Keywords: " + numberOfKeywords)
    System.out.println("\t" Operators: " + numberOfOperators)

    System.out.println("IDENTIFIERS: ")
    for (int i = 0; i < identifierList.size(); i++) {
        System.out.println(identifierList.get(i));
    }
    System.out.println("LITERALS: ")
    for (int i = 0; i < literalList.size(); i++) {
        System.out.println(literalList.get(i));
    }
    System.out.println("KEYWORDS: ")
    for (int i = 0; i < keywordList.size(); i++) {
        System.out.println(keywordList.get(i));
    }
%eof}

LETTER=[a-zA-Z]
DIGIT=[0-9]+
STRING=("\""|[a-zA-Z0-9]+ (" "[a-zA-Z0-9])* "\"")
IDENTIFIER={LETTER}({LETTER}|{DIGIT}|_)*
KEYWORD=("class"|"public"|"private"|"this"|"float"|"return"|"static"|"void"|"new"|"for"|"if")
OPERATOR=("="|"<")
%%
{KEYWORD} { numberOfTokens++; numberOfKeywords++; keywordList.add(yytext()); }
{IDENTIFIER} { numberOfTokens++; numberOfIdentifiers++; identifierList.add(yytext()); }
{OPERATOR} { numberOfTokens++; numberOfOperators++; }
. {} /*ignore */