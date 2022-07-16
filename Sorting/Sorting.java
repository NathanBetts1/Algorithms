import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.CollationElementIterator;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.*;
import static org.junit.Assert.*;


public class Sorting {
    private static final List<String> filePaths = new ArrayList<>();

    static {
        filePaths.add("resources/1Kints.txt");
        filePaths.add("resources/2Kints.txt");
        filePaths.add("resources/4Kints.txt");
        filePaths.add("resources/8Kints.txt");
        filePaths.add("resources/16Kints.txt");
    }


    //------------------------------------------ 20358446 - Nathan Betts ---------------------------------------------------------

    //----------------------------------------------- Bogo Sort ---------------------------------------------------------

    public static <T extends Comparable<T>> void bogoSort(final List<T> list, final Comparator<T> comparator) {
        while (isSortedList(list, comparator) != true) {
            for (int i = 0; i < list.size(); i++)
                Collections.swap(list, i, (int) (Math.random() * i));
        }
    }

    public static <T extends Comparable<T>> void bogoSort(final T[] array, final Comparator<T> comparator) {
        while (isSorted(array, comparator) == false) {
            shuffle(array);
        }
    }

    static <T extends Comparable<T>> void swap(final T[] array, int i, int j) {
        T temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    static <T extends Comparable<T>> boolean isSorted(final T[] array, final Comparator<T> comparator) {
        for (int i = 1; i < array.length; i++)
            if (comparator.compare(array[i], array[i - 1]) == -1) {
                return false;
            }
        return true;
    }

    static <T extends Comparable<T>> boolean isSortedList(final List<T> list, final Comparator<T> comparator) {
        for (int i = 1; i < list.size(); i++)
            if (comparator.compare(list.get(i), list.get(i - 1)) == -1) {
                return false;
            }
        return true;
    }

    static <T extends Comparable<T>> void shuffle(final T[] a) {
        for (int i = 0; i < a.length; i++)
            swap(a, i, (int) (Math.random() * i));
    }

    //----------------------------------------------- Bubble Sort ---------------------------------------------------------

    /**
     * @param list
     * @param comparator
     * @param <T>
     * @Complexity: Worst and Average Case Time Complexity: O(n2). Worst case occurs when array is reverse sorted.
     * Best Case Time Complexity: O(n). Best case occurs when array is already sorted.
     * Auxiliary Space: O(1)
     * Boundary Cases: Bubble sort takes minimum time (Order of n) when elements are already sorted.
     * @Implentation Nested Loops iterate thorough the List/Array comparing the highlighted element to the
     * next element and if the element is greater than then the one in front of it, bubbleSort swaps them
     * , in turn sorting the data structure in place.
     */

    public static <T extends Comparable<T>> void bubbleSort(final List<T> list, final Comparator<T> comparator) {
        int n = list.size();

        for (int i = n; i > 1; i--) {
            for (int j = 0; j < i - 1; j++) {
                if (comparator.compare(list.get(j), list.get(j + 1)) > -1) {
                    Collections.swap(list, j, j + 1);
                }
            }
        }
    }

    public static <T extends Comparable<T>> void bubbleSort(final T[] array, final Comparator<T> comparator) {

        for (int i = array.length; i > 1; i--) {
            for (int j = 0; j < i - 1; j++) {
                if (comparator.compare(array[j], array[j + 1]) == 1) {
                    T temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    //----------------------------------------------- Selection Sort ---------------------------------------------------------

    /**
     * @param list
     * @param comparator
     * @param <T>
     * @Complexity: Time Complexity: O(n^2) as there are two nested loops.
     * Auxiliary Space: O(1)
     * @Implentation Selection Sort sorts an array by repeatedly identifying the smallest member in the unsorted segment and placing it first.
     * In a given array, the algorithm keeps two subarrays.
     * The minimum element from the unsorted subarray is chosen and moved to the sorted subarray
     * in each iteration of selection sort.
     */

    public static <T extends Comparable<T>> void selectionSort(final List<T> list, final Comparator<T> comparator) {

        for (int i = 0; i < list.size() - 1; i++) {
            int smallestIndex = i;

            for (int j = i + 1; j < list.size(); j++) {
                if (comparator.compare(list.get(smallestIndex), list.get(j)) > 0) {
                    smallestIndex = j;
                }
            }
            Collections.swap(list, smallestIndex, i);
        }
    }

    public static <T extends Comparable<T>> void selectionSort(final T[] array, final Comparator<T> comparator) {

        for (int i = 0; i < array.length - 1; ++i) {
            int minIndex = i;
            for (int j = i + 1; j < array.length; ++j) {
                if (comparator.compare(array[j], array[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            T temp = array[i];
            array[i] = array[minIndex];
            array[minIndex] = temp;
        }
    }

    //----------------------------------------------- Merge Sort ---------------------------------------------------------

    /**
     * @param list
     * @param comparator
     * @param <T>
     * @Complexity: Worst-case time complexity: O(N*logN)
     * Average-case time complexity: O(N*logN)
     * @Implentation For the List:
     * I implemented a Divide method which recursively divides the input list into two halves till the size becomes 1.
     * Once the size becomes 1, the merge processes come into action and start merging
     * arrays back till the complete array is merged.
     * Merge makes uses of an arrayList which is of type T, which handles the ordering of the array
     * keeping the outputs of the comparisons in order, then sets the input lists elements
     * to its own therefore, in place sorting the list.
     */

    public static <T extends Comparable<T>> void mergeSort(final List<T> list, final Comparator<T> comparator) {
        divide(0, list.size() - 1, list, comparator);
    }

    public static <T extends Comparable<T>> void divide(int startIndex, int endIndex, final List<T> list, final Comparator<T> comparator) {

        if (startIndex < endIndex && (endIndex - startIndex) >= 1) {
            int mid = (endIndex + startIndex) / 2;
            divide(startIndex, mid, list, comparator);
            divide(mid + 1, endIndex, list, comparator);
            merger(startIndex, mid, endIndex, list, comparator);
        }
    }

    public static <T extends Comparable<T>> void merger(int startIndex, int midIndex, int endIndex, final List<T> list, final Comparator<T> comparator) {

        List<T> mergedSortedArray = new ArrayList<T>();

        int leftIndex = startIndex;
        int rightIndex = midIndex + 1;

        while (leftIndex <= midIndex && rightIndex <= endIndex) {
            if (comparator.compare(list.get(leftIndex), list.get(rightIndex)) <= 0) {
                mergedSortedArray.add(list.get(leftIndex));
                leftIndex++;
            } else {
                mergedSortedArray.add(list.get(rightIndex));
                rightIndex++;
            }
        }
        while (leftIndex <= midIndex) {
            mergedSortedArray.add(list.get(leftIndex));
            leftIndex++;
        }

        while (rightIndex <= endIndex) {
            mergedSortedArray.add(list.get(rightIndex));
            rightIndex++;
        }
        int i = 0;
        int j = startIndex;
        while (i < mergedSortedArray.size()) {
            list.set(j, mergedSortedArray.get(i++));
            j++;
        }
    }

    /**
     * @param array
     * @param comparator
     * @param <T>
     * @Complexity: Same as List implementation
     * @Implentation Once again mergeSort recursively divides the input array, into 2 halves until the size is less than 2, then merges
     * the sub arrays checking the comparisons to the left and right elements, in-place changing elements
     * of the input array.
     */

    public static <T extends Comparable<T>> void mergeSort(final T[] array, final Comparator<T> comparator) {
        if (array.length < 2) {
            return;
        }
        T[] first = (T[]) new Comparable[array.length / 2];
        T[] second = (T[]) new Comparable[array.length - first.length];
        System.arraycopy(array, 0, first, 0, first.length);
        System.arraycopy(array, first.length, second, 0, second.length);

        mergeSort(first, comparator);
        mergeSort(second, comparator);
        merge(first, second, array, comparator);
    }

    public static <T extends Comparable<T>> void merge(final T[] left, final T[] right, final T[] values, final Comparator<T> comparator) {
        int leftIndex = 0;
        int rightIndex = 0;
        int sortedIndex = 0;

        while (leftIndex < left.length && rightIndex < right.length) {
            if (comparator.compare(left[leftIndex], right[rightIndex]) <= 0) {
                values[sortedIndex] = left[leftIndex];
                leftIndex++;
            } else {
                values[sortedIndex] = right[rightIndex];
                rightIndex++;
            }
            sortedIndex++;
        }
        while (leftIndex < left.length) {
            values[sortedIndex] = left[leftIndex];
            leftIndex++;
            sortedIndex++;
        }

        while (rightIndex < right.length) {
            values[sortedIndex] = right[rightIndex];
            rightIndex++;
            sortedIndex++;
        }
    }

    //----------------------------------------------- Quick Sort ---------------------------------------------------------

    /**
     * @param array
     * @param comparator
     * @param <T>
     * @Complexity: Best Case: O(NlogN)
     * Average Case:  O(Nlog(N))
     * Worst Case: O(N^2)
     * @Implentation Similarly to merge Sort, as the given method returns void, I used a helper function to
     * implement the recursive aspect of the Algorithm.
     * It works by selecting a 'pivot' element from the array and partitioning
     * the other elements into two sub-arrays,
     * according to whether they are less than or greater than the pivot.
     * It moves from 2 pointers towards each other, increasing the pointers depending
     * on whether the
     * <p>
     * <p>
     * <p>
     * Problems with QuickSort()
     * 1) Consider the situation where each pivot quick sort choice divides the list into two lists: one empty list and one list with elements all greater than the pivot.
     * Insertion Sort is the equal of this degenerate case of quick sort.
     * <p>
     * 2) Quick sorting is dependant on selecting a good pivot.
     * The sort will take close to O(nlog(n)) time if each pivot
     * selection divides its list roughly in half.
     * The time spent selecting a good pivot is repaid in the efficiency
     * achieved by reducing the likelihood of faulty pivots.
     * <p>
     * 3) In terms of big-O notation, quick sort's best case is no better than merge sort.
     * they are both O(nlog(n))
     * <p>
     * 4)  the cost of “little” sorts, divide and conquer: most sorts are little
     * insertion sort is faster than quicksort on small arrays
     * Can bail out of quicksort when right-left is smaller than some k
     * can also leave small arrays unsorted
     * use a single (fast!) insertion sort pass at the end
     */

    public static <T extends Comparable<T>> void quickSort(final T[] array, final Comparator<T> comparator) {

        int startIndex = 0;
        int endIndex = array.length - 1;

        if (startIndex < endIndex) {
            int pivotIndex = partition(array, startIndex, endIndex, comparator);
            sort(array, startIndex, pivotIndex, comparator);
            sort(array, pivotIndex + 1, endIndex, comparator);
        }
    }

    public static <T extends Comparable<T>> void sort(final T[] array, int startIndex, int endIndex, final Comparator<T> comparator) {
        if (startIndex < endIndex) {
            int pivotIndex = partition(array, startIndex, endIndex, comparator);
            sort(array, startIndex, pivotIndex, comparator);
            sort(array, pivotIndex + 1, endIndex, comparator);
        }
    }

    private static <T extends Comparable<T>> int partition(T[] array, int startIndex, int endIndex, final Comparator<T> comparator) {
        int pivotIndex = (startIndex + endIndex) / 2;
        T pivotValue = array[pivotIndex];
        startIndex--;
        endIndex++;

        while (true) {
            do startIndex++; while (comparator.compare(array[startIndex], (pivotValue)) < 0);

            do endIndex--; while (comparator.compare(array[endIndex], (pivotValue)) > 0);

            if (startIndex >= endIndex) return endIndex;

            T temp = array[startIndex];
            array[startIndex] = array[endIndex];
            array[endIndex] = temp;
        }
    }

    /**
     * @param list
     * @param comparator
     * @param <T>
     * @Complexity: Best Case: O(NlogN)
     * Average Case:  O(Nlog(N))
     * Worst Case: O(N^2)
     * @Implentation The implementation is almost identical to the array implementation except for using
     * Collections.swap() and list.get() methods to achieve the same functionality
     */

    public static <T extends Comparable<T>> void quickSort(final List<T> list, final Comparator<T> comparator) {
        int startIndex = 0;
        int endIndex = list.size() - 1;

        if (startIndex < endIndex) {
            int pivotIndex = partitionList(list, startIndex, endIndex, comparator);
            sortList(list, startIndex, pivotIndex, comparator);
            sortList(list, pivotIndex + 1, endIndex, comparator);
        }
    }

    public static <T extends Comparable<T>> void sortList(List<T> list, int startIndex, int endIndex, Comparator<T> comparator) {
        if (startIndex < endIndex) {
            int pivotIndex = partitionList(list, startIndex, endIndex, comparator);
            sortList(list, startIndex, pivotIndex, comparator);
            sortList(list, pivotIndex + 1, endIndex, comparator);
        }

    }

    private static <T extends Comparable<T>> int partitionList(List<T> list, int startIndex, int endIndex, Comparator<T> comparator) {
        int pivotIndex = (startIndex + endIndex) / 2;
        T pivotValue = list.get(pivotIndex);
        startIndex--;
        endIndex++;

        while (true) {
            do startIndex++; while (comparator.compare(list.get(startIndex), pivotValue) < 0);

            do endIndex--; while (comparator.compare(list.get(endIndex), pivotValue) > 0);

            if (startIndex >= endIndex) return endIndex;
            Collections.swap(list, startIndex, endIndex);

        }
    }

    public static   <T extends Comparable<T>>void printArray(T[] array) {
        System.out.print("[");
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i]);
            if (i + 1 != array.length) {
                System.out.print(", ");
            }
        }
        System.out.print("]");
    }

    private static ArrayList<Integer> loadFileAsListArray(final String filePath) throws IOException {
        Scanner s = new Scanner(new File(filePath));
        ArrayList<Integer> list = new ArrayList<>();
        while (s.hasNext()) {
            if (s.hasNextInt()) {
                list.add(s.nextInt());
            } else {
                s.next();
            }
        }
        return list;
    }

    public static void main (final String[] args) throws IOException {
        int a = 1;
        long startTime = 0;
        long endTime = 0;
        long totalTime = 0;
        Comparator compare = Comparator.comparing(Integer::intValue);

        System.out.println("-------------Sorting Analysis---------------");

        System.out.println("----------------Merge Sort------------------");
        for (final String path : filePaths) {
            startTime = System.nanoTime();
          mergeSort(loadFileAsListArray(path),compare);
            endTime = System.nanoTime();
            System.out.println("Time Taken for File " + a + " = " + (endTime - startTime) + " ns");
            totalTime += endTime - startTime;
            a++;
        }
        System.out.println("Total Time taken for all 5 Files = " + totalTime + " ns");
        System.out.println("----------------Quick Sort------------------");
        a=0;
        for (final String path : filePaths) {
            startTime = System.nanoTime();
            quickSort(loadFileAsListArray(path),compare);
            endTime = System.nanoTime();
            System.out.println("Time Taken for File " + a + " = " + (endTime - startTime) + " ns");
            totalTime += endTime - startTime;
            a++;
        }
        System.out.println("Total Time taken for all 5 Files = " + totalTime + " ns");
        System.out.println("----------------Bubble Sort------------------");
        a=0;
        for (final String path : filePaths) {
            startTime = System.nanoTime();
            bubbleSort(loadFileAsListArray(path),compare);
            endTime = System.nanoTime();
            System.out.println("Time Taken for File " + a + " = " + (endTime - startTime) + " ns");
            totalTime += endTime - startTime;
            a++;
        }
        System.out.println("Total Time taken for all 5 Files = " + totalTime + " ns");
        System.out.println("----------------Selection Sort------------------");
        a=0;
        for (final String path : filePaths) {
            startTime = System.nanoTime();
            selectionSort(loadFileAsListArray(path),compare);
            endTime = System.nanoTime();
            System.out.println("Time Taken for File " + a + " = " + (endTime - startTime) + " ns");
            totalTime += endTime - startTime;
            a++;
        }
        System.out.println("Total Time taken for all 5 Files = " + totalTime + " ns");


    }




}