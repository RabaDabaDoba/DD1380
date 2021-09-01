import java.io.IOException;
import java.util.Scanner;

public class kommentarer {
    public static void main(String[] args) throws IOException {

        readComments2();

    }

    private static void readComments2() throws IOException {

        Scanner in = new Scanner(System.in, "utf-8");
        //char[] textArray = new char[10000];

        // We read all that we can and then we split it in an array, then we try to
        // locate the citation markers and figure it out from there

        String text = "";
        int counter = 1;
        int i = 0;
        int c1 = -1; // %
        int c2 = -1; // /*
        boolean comment = false;
        char prevChar = (char) System.in.read();
        //textArray[0] = prevChar;
        text += prevChar;
        //System.err.println("prevchar: " + prevChar);
        try {
            while ((i = System.in.read()) != -1) {
                char c = (char) i;
                System.err.println("char: " + c + ", int of char: " + i);

                if ((i == 37 && comment) || (i == 47 && comment && prevChar == 42 )) {
                    // Now we know that is the end of a comment
                    System.err.println("COMMENT END");
                    comment = false;
                    continue;
                }
                else if ((i == 37 && !comment)|| (i == 42 && !comment && prevChar == 47 )) {
                    // Now we know that is the beginning of a comment and we do not want to read
                    System.err.println("COMMENT START");
                    comment = true;
                    //text = text.substring(0, text.length()-1);
                } 
                
                

                if (!comment) {
                    // Now we know we are not in a comment want want to save the input
                    //textArray[counter] = c;
                    //prevChar = c;
                    counter++;
                    text+= c;
                } else {
                    System.err.println("Not writing, in comment");
                }

                prevChar = c;
                

                // Din kod här
                System.err.println("TEXT: " + text);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Now we split
        
        System.err.println("Now the string version");
        System.out.print(text);

    }


    private static void readComments() throws IOException {

        Scanner in = new Scanner(System.in, "utf-8");
        //char[] textArray = new char[10000];

        // We read all that we can and then we split it in an array, then we try to
        // locate the citation markers and figure it out from there

        String text = "";
        int counter = 1;
        int i = 0;
        int c1 = -1; // %
        int c2 = -1; // /*
        char prevChar = (char) System.in.read();
        //textArray[0] = prevChar;
        text += prevChar;
        //System.err.println("prevchar: " + prevChar);
        try {
            while ((i = System.in.read()) != -1) {
                char c = (char) i;
                System.err.println("char: " + c + ", int of char: " + i);

                if (i == 37 && c1 == 1 && prevChar == 32) {
                    // Now we know that is the end of a comment
                    System.err.println("COMMENT END");
                    c1 = -1;
                    continue;
                }
                else if (i == 37 && c1 == -1) {
                    // Now we know that is the beginning of a comment and we do not want to read
                    System.err.println("COMMENT START");
                    c1 = 1;
                    text = text.substring(0, text.length()-1);
                } 
                else if(i == 47 && c2 == 1 && prevChar == 42 ){
                    // Now we know that is the end of a comment
                    System.err.println("COMMENT END");
                    c2 = -1;
                    continue;
                }else if(i == 42 && c2 == -1 && prevChar == 47 ){
                    // Now we know that is the beginning of a comment and we do not want to read
                    System.err.println("COMMENT START");
                    c2 = 1;
                    text = text.substring(0, text.length()-1);
                }
                

                if (c1 == -1 && c2 == -1) {
                    // Now we know we are not in a comment want want to save the input
                    //textArray[counter] = c;
                    //prevChar = c;
                    counter++;
                    text+= c;
                } else {
                    System.err.println("Not writing, in comment");
                }

                prevChar = c;
                

                // Din kod här
                System.err.println("TEXT: " + text);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Now we split
        
        System.err.println("Now the string version");
        System.out.print(text);

    }

    private static void printChars(char[] textArray) {
        
        for(char c : textArray){
            System.out.print(c);
        }
    }
        




}
