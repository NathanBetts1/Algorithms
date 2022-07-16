public class RLE {

    public static void printRLE(final String input){
        int i=0;
        int counter=0;
        int j=0;
        String encodedString = new String();
        encodedString = "";
        while(j != input.length()-1) {
            while (input.charAt(i) == input.charAt(i + 1) ) {
                counter++;
                if( i+1 != input.length()-1) {
                    i++;
                }else{
                    break;
                }
            }
            encodedString += input.charAt(i) ;
            encodedString+=counter+1;
            counter=0;
            if( i+1 != input.length()-1) {
                i++;
            }else{
                break;
            }
            j++;
        }

        System.out.println(encodedString);

    }
    public static void main(String args[]){
    String example = new String("abbcccdddd");
printRLE(example);    }

}
