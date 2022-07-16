import java.util.concurrent.TimeUnit;

public class russianPigeon {
    public static int russianPeasantMult(final int x, final int y){

       int total=0;
       int smaller = x;
       int bigger = y;
        while(smaller!=0){
            if(smaller %2 !=0){
               total = total + bigger;
            }
            smaller = smaller/2;
            bigger =  bigger*2;
        }

        return  total;
    }

    public static void main(String[] args){
        final long startTime = System.nanoTime() ;

        System.out.println(russianPeasantMult(6843,14339));


       final  long endTime =System.nanoTime();
         long time = endTime-startTime;
        long durationInMs = TimeUnit.MILLISECONDS.convert(endTime-startTime, TimeUnit.NANOSECONDS);

        System.out.println(durationInMs);

    }
}
