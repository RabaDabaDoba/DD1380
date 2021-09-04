import java.io.IOException;
import java.util.Scanner;

/*



*/

public class kommentarer2 {
    public static void main(String[] args) {

        String text = "";
        int prevChar = -1;
        int i;
        int commentIndexStart = -1;
        int commentIndexEnd = -1;
        // 0 for false, 1 for %, 2 for /**/
        int inComment = 0;
        int charCounter = 0;
        int commentLength = 0;

        try {
            while ((i = System.in.read()) != -1) {
                System.err.println("charCounter: " + charCounter + ", text: " + text);
                System.err.println("_________");
                char c = (char) i;
                // We want to dectect "%", and "/*" and "*/"
                System.err.println("Current char: " + c);
                if (inComment > 0) {
                    System.err.println("inComment > 0");
                    // Now we should detect if we're leaving a comment
                    if (i == 37 && inComment == 1) {
                        System.err.println("Ending % comment");
                        // We detected a "%" and should end the comment
                        inComment = 0;
                        commentLength = 0;
                        commentIndexEnd = charCounter;
                    } else if (i == 47 && prevChar == 42 && inComment == 2 && charCounter > 0
                            && commentIndexStart != charCounter - 1) {
                        System.err.println("Ending */ comment with charCounter: " + charCounter + ", and commentindex: "
                                + commentIndexStart);
                        inComment = 0;
                        commentLength = 0;
                        commentIndexEnd = charCounter;
                    }
                    prevChar = c;
                    commentLength++;
                } else {
                    if (i == 37) {
                        // If the character we're at are a comment started
                        inComment = 1;
                        commentIndexStart = charCounter;

                    } else if (i == 42 && prevChar == 47 && charCounter > 0 && commentIndexEnd != charCounter - 1) {
                        System.err.println("Starting comment at: " + charCounter);
                        inComment = 2;
                        text = text.substring(0, text.length() - 1);
                        commentIndexStart = charCounter;
                    } else {
                        // Just a character
                        text += c;
                        prevChar = c;

                    }

                }
                charCounter++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        // System.out.println("Here it comes!");
        // Now are done and print the fixed text:
        System.out.println(text);

    }

}
