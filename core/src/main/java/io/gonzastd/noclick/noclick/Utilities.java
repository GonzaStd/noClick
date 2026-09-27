package io.gonzastd.noclick.noclick;
import java.util.Random;

public class Utilities {
    private final Random r;

    public Utilities(){
        this.r = new Random();
    }
    public char genRandomLetter() {
        char[] letters = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};

        return letters[this.r.nextInt(letters.length)];
    }

    public int genRandomDigits(int numberOfDigits){
        double n = r.nextDouble();
        return (int) (
            n * Math.pow(
                (double)10,
                (double) numberOfDigits)
        );
    }
}
