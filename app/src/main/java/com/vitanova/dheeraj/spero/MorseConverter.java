package com.vitanova.dheeraj.spero;


/**
 * Created by dheeraj on 19/1/18.
 */

public class MorseConverter  {
    public String morseCode(char letter){
        String morseCode="";
        switch(letter){
            case 'a':
                morseCode="/./";
                break;
            case 'b':
                morseCode="/-/./././";
                break;
            case 'c':
                morseCode="/-/./-/./";
                break;
            case 'd':
                morseCode="/-/././";
                break;
            case 'e':
                morseCode="/./";
                break;
            case 'f':
                morseCode="/././-/./";
                break;
            case 'g':
                morseCode="/-/-/./";
                break;
            case 'h':
                morseCode="/././././";
                break;
            case 'i':
                morseCode="/././";
                break;
            case 'j':
                morseCode="/./-/-/-/";
                break;
            case 'k':
                morseCode="/-/./-/";
                break;
            case 'l':
                morseCode="/./-/././";
                break;
            case 'm':
                morseCode="/-/-/";
                break;
            case 'n':
                morseCode="/-/./";
                break;
            case 'o':
                morseCode="/-/-/-/";
                break;
            case 'p':
                morseCode="/./-/-/./";
                break;
            case 'q':
                morseCode="/-/-/./-/";
                break;
            case 'r':
                morseCode="/./-/./";
                break;
            case 's':
                morseCode="/./././";
                break;
            case 't':
                morseCode="/-/";
                break;
            case 'u':
                morseCode="/././-/";
                break;
            case 'v':
                morseCode="/./././-/";
                break;
            case 'w':
                morseCode="/./-/-/";
                break;
            case 'x':
                morseCode="/-/././-/";
                break;
            case 'y':
                morseCode="/-/./-/-/";
                break;
            case 'z':
                morseCode="/-/-/././";
                break;
            case '0':
                morseCode="/-/-/-/-/-/";
                break;
            case '1':
                morseCode="/./-/-/-/-/";
                break;
            case '2':
                morseCode="/././-/-/-/";
                break;
            case '3':
                morseCode="/./././-/-/";
                break;
            case '4':
                morseCode="/././././-/";
                break;
            case '5':
                morseCode="/./././././";
                break;
            case '6':
                morseCode="/-/././././";
                break;
            case '7':
                morseCode="/-/-/./././";
                break;
            case '8':
                morseCode="/-/-/-/././";
                break;
            case '9':
                morseCode="/-/-/-/-/./";
                break;
            case '.':
                morseCode="/./-/./-/./-/";
                break;
            case ',':
                morseCode="/-/-/././-/-/";
                break;
            case ':':
                morseCode="/-/-/-/./././";
                break;
            case '?':
                morseCode="/././-/-/././";
                break;
            case '-':
                morseCode="/-/././././-/";
                break;
            case '=':
                morseCode="/-/./././-/";
                break;
            case ' ':
                morseCode="/";
                break;
        }
        return morseCode;
    }

    public String stringToMorse(String str){
        String output="";
        for(int i=0;i<str.length();i++){
            output=output+morseCode(str.charAt(i));
        }
        return (output+"//");
    }

    public String morseToString(String str){
        str=str+"  ";
        String stringValue="";
        String tempStr=str;
        while(str.indexOf(' ')!=-1){
            int spaceIndex=str.indexOf(' ');
            if(str.charAt(spaceIndex+1)!=' '){
                String code=str.substring(0,spaceIndex);
                char letter=morseChar(code);
                stringValue=stringValue+String.valueOf(letter);
                str=str.substring(spaceIndex+1);
            }else{
                String code=str.substring(0,spaceIndex);
                char letter=morseChar(code);
                stringValue=stringValue+String.valueOf(letter);
                stringValue=stringValue+" ";
                str=str.substring(spaceIndex+2);
            }
        }
        return stringValue;
    }

    private char morseChar(String code) {
        char val=' ';
        if(code.equals(".-")){
            val='a';
        }else if(code.equals("-...")){
            val='b';
        }else if(code.equals("-.-.")){
            val='c';
        }else if(code.equals("-..")) {
            val = 'd';
        }else if(code.equals(".")){
            val='e';
        }else if(code.equals("..-.")){
            val='f';
        }else if(code.equals("--.")){
            val='g';
        }else if(code.equals("....")){
            val='h';
        }else if(code.equals("..")){
            val='i';
        }else if(code.equals(".---")){
            val='j';
        }else if(code.equals("-.-")){
            val='k';
        }else if(code.equals(".-..")){
            val='l';
        }else if(code.equals("--")){
            val='m';
        }else if(code.equals("-.")){
            val='n';
        }else if(code.equals("---")){
            val='o';
        }else if(code.equals(".--.")){
            val='p';
        }else if(code.equals("--.-")){
            val='q';
        }else if(code.equals(".-.")){
            val='r';
        }else if(code.equals("...")){
            val='s';
        }else if(code.equals("-")){
            val='t';
        }else if(code.equals("..-")){
            val='u';
        }else if(code.equals("...-")){
            val='v';
        }else if(code.equals(".--")){
            val='w';
        }else if(code.equals("-..-")){
            val='x';
        }else if(code.equals("-.--")){
            val='y';
        }else if(code.equals("--..")){
            val='z';
        }else if(code.equals("-----")){
            val='0';
        }else if(code.equals(".----")){
            val='1';
        }else if(code.equals("..---")){
            val='2';
        }else if(code.equals("...--")){
            val='3';
        }else if(code.equals("....-")){
            val='4';
        }else if(code.equals(".....")){
            val='5';
        }else if(code.equals("-....")){
            val='6';
        }else if(code.equals("--...")){
            val='7';
        }else if(code.equals("---..")){
            val='8';
        }else if(code.equals("----.")){
            val='9';
        }else if(code.equals(".-.-.-")){
            val='.';
        }else if(code.equals("--..--")){
            val=',';
        }else if(code.equals("---...")){
            val=':';
        }else if(code.equals("..--..")){
            val='?';
        }else if(code.equals("-....-")){
            val='-';
        }else if(code.equals("-...-")){
            val='=';
        }

        return val;
    }
}
