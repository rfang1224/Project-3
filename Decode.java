package project3;

import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;
import java.util.Iterator;

/**
 * This program process a sequence of numbers according to given decodeKey.
 * The sequence of values provided by the user is a comma and space separated list of integers. For example: {@code 12, 43, 189, 42, 1, 35}.
 * The decodeKey consist of characters 'F', 'B' and 'R':
 * - F - drop the first element of the sequence
 * - B - drop the last element of the sequence
 * - R - reverse the sequence.
 *
 * The program outputs the resulting numerical sequence after the decodeKey were processed.
 *
 * For example, if the initial sequence is {@code 12, 43, 189, 42, 1, 35} and the decodeKey are {@code FRB}, the resulting sequence should be
 * {@code [35, 1, 42, 189]}.
 *
 * @author Joanna Klukowska
 * @author ADD YOUR NAME HERE
 *
 */
public class Decode {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        //get sequence and decoding key from the user
        System.out.println("Enter the sequence to process: ") ;
        String sequence = in.nextLine();

        System.out.println("Enter the decoding key: ");
        String decodeKey = in.nextLine();

        //validate decoding key  
        if (! isValid(decodeKey) ) {
            System.out.println("ERROR: decoding key is invalid.");
            System.exit(3);
        }

        MDeque<Integer> list = null;

        try {
            list = parseSequence (sequence);
        }
        catch(IllegalArgumentException ex){
            System.out.println("Invalid numerical sequence. ") ;
            System.exit(1);
        }

        try {
            decode( list , decodeKey);
        }
        catch (NoSuchElementException ex ) {
            System.out.println("Instructions are invalid for the given sequence.");
            System.exit(2);
        }

        System.out.println("Decoded sequence is: ");
        System.out.println(list);

        in.close();

    }

    /**
     * Decode the sequence represented by the {@code list} mdeque following the
     * {@code decodeKey}.
     * @param list the mdeque with sequence to decode
     * @param decodeKey decodeKey to follow to decode the {@code list}
     * @throws NoSuchElementException when the sequence is empty and the next instruction
     * is either 'F' or 'B'
     */
    public static void decode (MDeque<Integer> list , String decodeKey) throws NoSuchElementException {
        //loops through each character of the decodeKey
        for(int i = 0; i < decodeKey.length(); i++){
            if(list.size() == 0 && (decodeKey.charAt(i) == 'F' || decodeKey.charAt(i) == 'B')){
                throw new NoSuchElementException("MDeque is empty");
            }
            //if character is F then pop the front
            if(decodeKey.charAt(i) == 'F'){
                list.popFront();
            }

            //if character is B then pop the back
            else if(decodeKey.charAt(i) == 'B'){
                list.popBack();
            }

            //if character is R then reverse the lsit
            else if(decodeKey.charAt(i) == 'R'){
                MDeque<Integer> revList = new MDeque<>();

                Iterator<Integer> rev = list.reverseIterator();

                //creates a new MDeque with the values in reverse
                while(rev.hasNext()){
                    revList.pushBack(rev.next());
                }

                Iterator<Integer> forward = revList.iterator();

                //pops all nodes in the list
                while(list.size() != 0){
                    list.popBack();
                }

                //pushes all nodes from the new MDeque to list
                while(forward.hasNext()){
                    list.pushBack(forward.next());
                }
            }
        }

    }
    /**
     * Convert a given sequence from string format to mdeque of interger values.
     * @param sequence string with comma and space separated values
     * @return mdeque with the same values as the ones listed in the {@code sequence}
     * @throws IllegalArgumentException when the sequence contains values that cannot be
     * converted to a list of integer due to invalid characters or invalid separators
     */
    public static MDeque<Integer> parseSequence (String sequence ) throws IllegalArgumentException {
        MDeque<Integer> list = new MDeque<>();
        //creates a new MDeque and pushes each character separated by a comma to the new MDeque
        try {
            String [] splitSequence = sequence.split(", ");

            for (int i = 0; i < splitSequence.length; i++ ) {
                list.pushBack(Integer.parseInt( splitSequence[i]) );
            }

        }
        catch (PatternSyntaxException ex ) {
            System.err.println("THIS SHOULD NOT HAPPEN!");
        }
        catch (NumberFormatException ex ) {
            throw new IllegalArgumentException ( "invalid value in the sequence");
        }
        return list;

    }


    /**
     * Determines if the sequence of decodeKey is valid. A valid sequence consists of characters:
     * 'R', 'F' and 'B' in any order.
     * @param decodeKey instruction string
     * @return {@code true} if decodeKey are valid, {@code false} otherwise
     */
    public static boolean isValid ( String decodeKey ) {
        //if decodeKeys length is 0 then return true
        if(decodeKey.length() == 0){
            return true;
        }
        //return the result of the isValid function called on decodeKey without the first character
        char firstChar = decodeKey.charAt(0);
        if(firstChar == 'F' || firstChar == 'B' || firstChar == 'R'){
            return isValid(decodeKey.substring(1));
        }
        //return false if otherwise
        else{
            return false;
        }
    }
}
