import java.util.Scanner;

/**
 * boff
 */
public class boff {

    public static void main(final String[] args) {
        //Creating the scanner that will read the input
        final Scanner in = new Scanner(System.in);

        //Get the variables and convert to integers
        final int n = Integer.parseInt(in.next());
        final int a = Integer.parseInt(in.next());
        final int b = Integer.parseInt(in.next());
        
        int boffCounter = 0;

        for (int i = a; i <= b; i++) {
            //Here we control if i ends with n, or if it is divisible by n:
            if(i%n == 0){
                boffCounter++;
            }
            else if(n>=10){
                if(i%100 == n){
                    boffCounter++;
                    
                }
            }else{
                if(i%10 == n){
                    boffCounter++;
                    
                }
            }
           
            
        }

        System.out.println(boffCounter);

        
    }
}