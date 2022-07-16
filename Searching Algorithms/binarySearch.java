import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class binarySearch {
    private static final List<String> filePaths = new ArrayList<>();

    static {
        filePaths.add("resources/1Kints.txt");
        filePaths.add("resources/2Kints.txt");
        filePaths.add("resources/4Kints.txt");
        filePaths.add("resources/8Kints.txt");
        filePaths.add("resources/16Kints.txt");
    }


    /**
     * @param array
     * @param elem
     * @param comparator
     * @param <T>
     * @return
     *
     *  @Time_Complexity: O(log n)
     *  @Best_Case: O(1)
     *
     * @Implentation
     * @Iterative:
     * 2 pointers low and high are initiated starting at 0 and the length -1,
     * these pointers change dpeending on the comparison of the the middle value
     * and the target element
     *
     * this process is looped while low <= high after that the element is not present in the array
     * therefore returning -1
     *
     * @Recursive:
     *     Compare x with the middle element.
     *     If x matches with the middle element, we return the mid index.
     *     Else If x is greater than the mid element, then x can only lie in the right half subarray after the mid element. So we recur for the right half.
     *     Else (x is smaller) recur for the left half.
     */
    public static <T extends Comparable<T>> int binarySearch(final T[] array, final T elem, final Comparator<T> comparator){
        int low = 0;
        int high = array.length - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            T midVal = array[mid];
            int result = comparator.compare(elem,midVal);

            if (result < 0) {
                high = mid - 1;
            }

            else if (result > 0) {
                low = mid + 1;
            }
            else {
                return mid;
            }
        }
        return -1;
    }

    public static <T extends Comparable<T>> int binarySearchRecursive(final T[] array, final T elem, final Comparator<T> comparator){
    int low = 0;
    int high = array.length-1;
    return bSearchRecursive(array,elem,comparator,low,high);
    }

    public static <T extends Comparable<T>> int  bSearchRecursive(final T[] array, final T elem, final Comparator<T> comparator, int low, int high){
        int middle = low  + ((high - low) / 2);

        if (high < low) {
            return -1;
        }
        if (comparator.compare(elem,array[middle])==0) {
            return middle;
        } else if (comparator.compare(elem,array[middle])==-1){
            return bSearchRecursive(array, elem,comparator, low, middle - 1);
        } else {
            return bSearchRecursive(array, elem,comparator, middle + 1, high);
        }
    }
    public static <T extends Comparable<T>> int binarySearch(final List<T> list, final T elem, final Comparator<T> comparator){
        int low = 0;
        int high = list.size() - 1;

        while (low <= high) {
            int mid = (low + high) / 2;
            T midVal = list.get(mid);
            int result = comparator.compare(elem,midVal);
            if (result < 0) {
                high = mid - 1;
            }
            else if (result > 0) {
                low = mid + 1;
            }
            else {
                return mid;
            }
        }
        return -1;
    }
    private static int[] loadFileAsIntArray(final String filePath) throws IOException {
        return Files.readAllLines(Paths.get(filePath)).stream().mapToInt(Integer::parseInt).toArray();
    }

    public static void main(String[] args) throws IOException {

        int a = 1;
        long startTime = 0;
        long endTime = 0;
        long totalTime = 0;


        Comparator compare = Comparator.comparing(Integer::intValue);
        System.out.println("----------------------Binary Search Analysis---------------------");
        System.out.println("-------------------Binary Search Non Recursive-------------------");

        for (final String path : filePaths) {
            int[] array = loadFileAsIntArray(path);
            Integer[] numbers = Arrays.stream( array ).boxed().toArray( Integer[]::new );
            startTime = System.nanoTime();
            binarySearch(numbers,1000,compare);
            endTime = System.nanoTime();
            System.out.println("Time Taken for File " + a + " = " + (endTime - startTime) + " ns");
            totalTime += endTime - startTime;
            a++;
        }
        System.out.println("-------------------Binary Search Recursive----------------------");
        for (final String path : filePaths) {
            int[] array = loadFileAsIntArray(path);
            Integer[] numbers = Arrays.stream( array ).boxed().toArray( Integer[]::new );
            startTime = System.nanoTime();
            binarySearchRecursive(numbers,1000,compare);
            endTime = System.nanoTime();
            System.out.println("Time Taken for File " + a + " = " + (endTime - startTime) + " ns");
            totalTime += endTime - startTime;
            a++;
        }

    }
}
