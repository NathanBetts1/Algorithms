import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.stream.Collectors;

class KMPsearch {
    /**
     * @param pat
     * @param txt
     *
     * @Time_Complexity
     *  The time complexity of KMP algorithm is O(n) in the worst case.
     *   it also has a space complexity of O(m)
     *
     * @Implementation:
     * @PreProcessing
     * KMP algorithm preprocesses pat[] and constructs an auxiliary lps[] of size m (same as size of pattern) which is used to skip characters while matching.
     * We search for lps in sub-patterns. More clearly we focus on sub-strings of patterns that are either prefix and suffix.
     *
     * @Searching
     * We start comparison of pat[j] with j = 0 with characters of current window of text.
     * We keep matching characters txt[i] and pat[j] and keep incrementing i and j while pat[j] and txt[i] keep matching.
     * When we see a mismatch
     * We know that characters pat[0..j-1] match with txt[i-j…i-1]
     * We also know  that lps[j-1] is count of characters of pat[0…j-1] that are both proper prefix and suffix.
     * From above two points, we can conclude that we do not need to match these lps[j-1] characters with txt[i-j…i-1]
     * because we know that these characters will anyway match
     *
     *
     */



    void KMPSearch(final String pat, final String txt) {
        int M = pat.length();
        int N = txt.length();

        int lps[] = new int[M];
        int j = 0;

        computeLPSArray(pat, M, lps);
        int i = 0;
        while (i < N) {
            if (pat.charAt(j) == txt.charAt(i)) {
                j++;
                i++;
            }
            if (j == M) {
                System.out.println("Found pattern "
                        + "at index " + (i - j));
                j = lps[j - 1];
            }
            else if (i < N && pat.charAt(j) != txt.charAt(i)) {
                if (j != 0)
                    j = lps[j - 1];
                else
                    i = i + 1;
            }
        }
    }
    void computeLPSArray(final String pat, final int M, final int lps[]) {
        // length of the previous longest prefix suffix
        int len = 0;
        int i = 1;
        lps[0] = 0; // lps[0] is always 0
        // the loop calculates lps[i] for i = 1 to M-1
        while (i < M) {
            if (pat.charAt(i) == pat.charAt(len)) {

                        len++;
                lps[i] = len;
                i++;
            } else // (pat[i] != pat[len])
            {
                // This is tricky. Consider the example.
                // AAACAAAA and i = 7. The idea is similar
                // to search step.
                if (len != 0) {
                    len = lps[len - 1];
                    // Also, note that we do not increment
                    // i here
                } else // if (len == 0)
                {
                    lps[i] = len;
                    i++; }
            } }
    }
    public static void main(String args[]) throws FileNotFoundException {
        BufferedReader in = new BufferedReader(new FileReader("/Users/nathanbetts/IdeaProjects/Algorithms/src/the-full-bee-movie-script.txt"));

        String a = new String();
        a =   in.lines().collect(Collectors.joining("\n"));
        for(int i=0;i<10;i++) {
            long start = System.currentTimeMillis();
            new KMPsearch().KMPSearch("rehearsal", a);
            long end = System.currentTimeMillis();
            System.out.println("Time taken for Brute Force= " + (end - start) + "ms");
        }

    } }