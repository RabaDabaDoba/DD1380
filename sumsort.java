import java.util.Arrays;
import java.util.Scanner;

public class sumsort {

    public static void main(String[] args) {


        System.out.println(sumItUp());
    }

    private static int sumItUp() {
        Scanner in = new Scanner(System.in);
        int n = Integer.parseInt(in.nextLine());
        int [] array = new int [n];
        
        for (int i = 0; i < n; i++) {
            array[i] = Integer.parseInt(in.next());
        }

        Arrays.parallelSort(array);
        int sum = 0;
        if(n%2 == 0){//Even
            for (int j = (n/2); j < n; j++) {
                sum += array[j];
            }
        }else{
            for (int j = ((n+1)/2)-1; j < n; j++) {
                sum += array[j];
            }
        } 

        return sum;

    }

}