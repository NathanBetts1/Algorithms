import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Collectors;

public class stringSearching {


    /**
     * @param pat
     * @param txt
     * @return
     *
     *
     * @Time_Complexity:
     * The time complexity of brute force is O(mn)
     *  So, if we were to search for a string of "n" characters in a string of "m" characters using brute force, it would take us n * m tries.
     */
    public static int bruteForce(String pat, String txt) {
        int M = pat.length();
        int N = txt.length();
        for (int i = 0; i <= N - M; i++) {
            int j;
            for (j = 0; j < M; j++)
                if (txt.charAt(i + j) != pat.charAt(j)) {
                    break;
                }
            if (j == M) return i;
        }
            return N;
        }


        public static void main(String[] args) throws FileNotFoundException {
            BufferedReader in = new BufferedReader(new FileReader("/Users/nathanbetts/IdeaProjects/Algorithms/src/the-full-bee-movie-script.txt"));

            String a = new String();
            String b = new String();
          a =   in.lines().collect(Collectors.joining("\n"));

            long start = System.currentTimeMillis();
                System.out.println(bruteForce("rehearsal", a));
                long end = System.currentTimeMillis();
                System.out.println("Time taken for Brute Force = " + (end - start) + "ms");


    }


}


