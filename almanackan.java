import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;
import java.util.Random;

public class almanackan {

    public static void main(String[] args) {
        //bots();
        sortAppointments();
    }

    public static Appointment randomAppointment() {
        Random rand = new Random();

        int month = rand.nextInt((12 - 1) + 1) + 1;
        int day = rand.nextInt((31 - 1) + 1) + 1;
        int hour = rand.nextInt((24 - 0) + 1) + 0;
        int min = rand.nextInt((59 - 0) + 1) + 0;
        String text = "Hi i am a random person";
        String line = getMonthFromInt(month) + " " + day + " " + hour + ":" + min + " " + text;
        Appointment a = new Appointment(line);

        return a;
    }

    public static String getMonthFromInt(int month){
        switch (month) {
            case 1: return "jan";
            case 2: return "feb";
            case 3: return "mar";
            case 4: return "apr";
            case 5: return "maj";
            case 6: return "jun";
            case 7: return "jul";
            case 8: return "aug";
            case 9: return "sep";
            case 10: return "okt";
            case 11: return "nov";
            case 12: return "dec";
            default: return "null";
        }
    }

    public static void bots() {
        int n = 1000;
        Appointment[] appointments = new Appointment[n];
        for (int i = 0; i < n; i++) {
            System.err.println(i);
            appointments[i] = randomAppointment();
            // appointments[i].toString();
        }

        Arrays.sort(appointments);
        System.err.println("Sorted: ");
        for (Appointment appointment : appointments) {
            System.out.println(appointment.toString());
        }
    }

    public static void sortAppointments() {
        Scanner in = new Scanner(System.in);
        System.err.println("123");
        int n = Integer.parseInt(in.nextLine());
        System.err.println("this is n: " + n);
        Appointment[] appointments = new Appointment[n];
        System.err.println("A");
        for (int i = 0; i < n; i++) {
            System.err.println(i);
            appointments[i] = new Appointment(in.nextLine());
            // appointments[i].toString();
        }

        for (Appointment appointment : appointments) {
            System.err.println(appointment.toString());
        }

        Arrays.sort(appointments);
        System.err.println("Sorted: ");
        for (Appointment appointment : appointments) {
            System.out.println(appointment.toString());
        }

    }


    
}

/**
 * Appointment
 */
class Appointment implements Comparable<Appointment> {
    int month;
    String monthText;
    int day;
    int hour;
    int min;
    String text = "";

    public Appointment(String line) {
        // We take the whole line and parse it
        // parsed = new String [10000];
        String[] parsed = line.split("\\s+");

        System.err.println("Parsed version of: " + line);
        System.err.println("Parsed: ");
        for (String string : parsed) {
            System.err.print(string + " ");
        }

        System.err.println("1");
        // This also means that the description will be split up but we know the exact
        // index for each type of data
        this.month = getMonthFromText(parsed[0]);
        System.err.println("2");
        this.monthText = parsed[0];
        System.err.println("3");
        this.day = Integer.parseInt(parsed[1]);
        System.err.println("4");
        String[] time = new String[2];
        System.err.println("5");
        time = parsed[2].split(":");
        System.err.println("6");
        this.hour = Integer.parseInt(time[0]);
        System.err.println("7");
        this.min = Integer.parseInt(time[1]);
        System.err.println("8");

        // Now each remaning index is the description
        for (int i = 3; i < parsed.length; i++) {
            text += parsed[i] + " ";
        }
        System.err.println("9");
    }

    public String toString() {
        // TODO Auto-generated method stub
        int length1 = String.valueOf(this.hour).length();
        String minFix = "";
        if (this.min == 0)
            minFix = "00";
        else if (this.min > 0 && this.min < 10)
            minFix = "0" + this.min;
        else
            minFix = "" + this.min;

        return this.monthText + " " + this.day + " " + (length1 == 1 ? "0" + this.hour : this.hour) + ":" + minFix + " "
                + this.text;
    }

    

    public int getMonthFromText(String month) {
        switch (month) {
            case "jan":
                return 1;
            case "feb":
                return 2;
            case "mar":
                return 3;
            case "apr":
                return 4;
            case "maj":
                return 5;
            case "jun":
                return 6;
            case "jul":
                return 7;
            case "aug":
                return 8;
            case "sep":
                return 9;
            case "okt":
                return 10;
            case "nov":
                return 11;
            case "dec":
                return 12;
            default:
                return -1;

        }
    }

    public int getMonth() {
        return this.month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public String getMonthText() {
        return this.monthText;
    }

    public void setMonthText(String monthText) {
        this.monthText = monthText;
    }

    public int getDay() {
        return this.day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getHour() {
        return this.hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMin() {
        return this.min;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public String getText() {
        return this.text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int compareTo(Appointment o) {
        return Comparator.comparingInt(Appointment::getMonth)
                .thenComparing(Comparator.comparingInt(Appointment::getDay))
                .thenComparing(Comparator.comparingInt(Appointment::getHour))
                .thenComparing(Comparator.comparingInt(Appointment::getMin))
                .thenComparing(Comparator.comparing(Appointment::getText)).compare(this, o);
    }

}
