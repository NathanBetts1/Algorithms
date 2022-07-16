import java.util.Random;

import static java.lang.Math.max;

public class dynamicProgramming {


    //There is a clear improvement on both Time and Space Complexities when using the Dynamic Programming
    //Approach to Function Design, with it seeing a reduction from O(2^n) to O(n) in Time Complexity and from
    // O(n) to O(1) in Space Complexity

    /**
     * @param n
     * @return
     *
     * @Time_Complexity:
     *  O(2^n)
     *
     * @Space_Complexity:
     * O(n)
     *
     * @Implementation:
     * The Recursive Implementation is very simplististic in its Design, with one if condition checking whether n <=1, otherwise
     * Sum the fibonacci's of (n-1) & (n-2)
     *
     */
    public static int fibRecursive(final int n) {
        if (n <= 1) {
            return n;
        } else {
            return fibRecursive(n - 1) + fibRecursive(n - 2);
        }
    }

    /**
     * @param n
     * @return
     *
     * @Time_Complexity:
     *  O(n)
     *
     * @Space_Complexity:
     *   O(1)
     *
     * @Implementation:
     * We can avoid the repeated work done in the Recursive Method by storing the Fibonacci
     * numbers calculated so far. This is easily achieved by assigning the values into an array
     * which we make n+2 in size
     *
     */
    public static int fibDynamic(final int n){
        int f[] = new int[n+2];
        int i;
        f[0] = 0;
        f[1] = 1;
        for (i = 2; i <= n; i++)
        {
            f[i] = f[i-1] + f[i-2];
        }
        return f[n];
    }

    /**
     * @param sOne
     * @param sTwo
     * @return
     *
     * @Time_Complexity:
     * O(n*m)
     *
     * @Space_Complexity:
     * O(n)
     */
    public static String LCS(final String sOne, final String sTwo) {
        char[] sOneArray = sOne.toCharArray();
        char[] sTwoArray = sTwo.toCharArray();
        int sOneLength = sOneArray.length;
        int sTwoLength = sTwoArray.length;
      int[][] substring = new int[sOneLength+1][sTwoLength+1];
      for(int i =0;i<sOneLength;i++){
          for(int j=0;j<sTwoLength;j++){
              if(sOneArray[i]==sTwoArray[j]){
                  substring[i+1][j+1] = substring[i][j] + 1;
              }else{
                  substring[i+1][j+1] = Math.max(substring[i][j+1],substring[i+1][j]);
              }
          }

      }
      return returnLCS(sOneArray,sTwoArray,substring);

    }
    public static String returnLCS(char[] sOneArray,char[]sTwoArray,int[][] substring){
        StringBuilder answer = new StringBuilder();
        int sOneLength = sOneArray.length;
        int sTwoLength = sTwoArray.length;
        while(substring[sOneLength][sTwoLength]>0){
            if(sOneArray[sOneLength-1] == sTwoArray[sTwoLength-1]){
                answer.append(sOneArray[sOneLength-1]);
                sOneLength--;
                sTwoLength--;
            }else if(substring[sOneLength-1][sTwoLength] >= substring[sOneLength][sTwoLength-1]){
                sOneLength--;
            }else{
                sTwoLength--;
            }
        }
        return answer.reverse().toString();
    }

    /**
     * @param W
     * @param w
     * @param vals
     * @param n
     * @return
     *
     * @Time_Complexity
     * O(2^n)
     *
     * @Space_Complexity
     * O(n * CAPACITY)
     *
     */
    public static int knapsackRecursive(final int W, final int[] w, final int[] vals, final int n) {
        if ((n == 0) || (W == 0)) {
            return 0;
        } else if (w[n-1] > W) {
            return knapsackRecursive(W, w, vals, n - 1);
        } else {
            return max(vals[n - 1] + knapsackRecursive(W - w[n - 1], w, vals, n - 1), knapsackRecursive(W, w, vals, n - 1));
        }
    }

    /**
     * @param W
     * @param w
     * @param vals
     * @param n
     * @return
     *
     * @Time_Complexity
     * O(N * CAPACITY)
     *
     * @Space_Complexity
     * O(N * CAPACITY)
     *
     */
    public static int knapsackDynamic(final int W, final int[] w, final int[] vals, final int n) {
        int i, j;
        int K[][] = new int[n + 1][W + 1];

        for (i = 0; i <= n; i++)
        {
            for (j = 0;j <= W; j++)
            {
                if (i == 0 || j == 0)
                {
                    K[i][j] = 0;
                } else if (w[i - 1] <= j) {
                    K[i][j] = max(vals[i - 1] + K[i - 1][j - w[i - 1]], K[i - 1][j]);
                } else {
                    K[i][j] = K[i - 1][j];
                }
            }
        }
        return K[n][W];
    }
    public static void main(String[] args){
        Random r = new Random();
        int val[] =  r.ints(15, 0, 100).toArray();
        int wt[] = r.ints(15, 0, 100).toArray();
        int W = 450;
        int n = val.length;
        long startTime = 0;
        long endTime = 0;
        long totalTime = 0;
        System.out.println("-----------------Dynamic Programming Analysis--------------------");
        System.out.println("----------------Fibonacci Recursive---------------------");
        for(int i=0;i<=10;i++){
            startTime = System.nanoTime();
            fibRecursive(i);
            endTime = System.nanoTime();
            System.out.println("Time Taken for Fib Recursive " + i + " = " + (endTime - startTime) + " ns");
            totalTime += endTime - startTime;
        }
        System.out.println("----------------Fibonacci Dynamic---------------------");
        for(int i=0;i<=10;i++){
            startTime = System.nanoTime();
            fibDynamic(i);
            endTime = System.nanoTime();
            System.out.println("Time Taken for Fib Dynamic " + i + " = " + (endTime - startTime) + " ns");
            totalTime += endTime - startTime;
        }
        System.out.println("----------------Knapsack Recursive---------------------");

        startTime = System.nanoTime();
        knapsackRecursive(W,wt,val,n);
        endTime = System.nanoTime();
        System.out.println("Time Taken for Knapsack Recursive  = " + (endTime - startTime) + " ns");
        System.out.println("----------------Knapsack Dynamic---------------------");

        startTime = System.nanoTime();
        knapsackDynamic(W,wt,val,n);
        endTime = System.nanoTime();
        System.out.println("Time Taken for Knapsack Dynamic  = " + (endTime - startTime) + " ns");
    }
    }

