import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Pattern;

import static org.junit.Assert.assertEquals;

public class pigLatinTranslateExercise {
    public static void main(String[] args) {
        System.out.println("pigLatinTranslate");
    }

    public static String pigLatinTranslate(String input) {

        String output = "";

        String[] outputAux = input.split(" ");

        Pattern regex = Pattern.compile("[$&+,:;=\\\\?@#|/'<>.^*()%!-]");

        for (String i : outputAux) {
            char[] charArray = i.toCharArray();

            char specialChar = '0';
            
            if (regex.matcher(i).find()) {
                specialChar = charArray[charArray.length-1];
                i=i.substring(0,i.length()-1);
                charArray = i.toCharArray();
            }
            
            if(Character.isUpperCase( charArray[0] )){
                i = Character.toLowerCase(charArray[0]) + String.copyValueOf(charArray).substring(1);
                char[] charArray2 = i.substring(1).toCharArray();
                if(specialChar!='0') {
                    output = output + Character.toUpperCase(charArray2[0]) + String.copyValueOf(charArray2).substring(1) + i.charAt(0) + "ay" + specialChar + " ";
                }
                else{
                    output = output + Character.toUpperCase(charArray2[0]) + String.copyValueOf(charArray2).substring(1) + i.charAt(0) + "ay" + " ";
                }
                }else{
                if(specialChar!='0') {
                    output = output + i.substring(1) + i.charAt(0) + "ay" + specialChar + " ";
                }
                else{
                    output = output + i.substring(1) + i.charAt(0) + "ay" + " ";
                }
            }
        }

        output = output.substring(0,output.length()-1);
        return output;
    }

    @Test
    public void pigLatinTranslate_test1() {

        String input = "hello";
        Assert.assertEquals("ellohay", pigLatinTranslate(input));
    }

    @Test
    public void pigLatinTranslate_test2() {

        String input = "hello world";
        Assert.assertEquals("ellohay orldway", pigLatinTranslate(input));
    }

    @Test
    public void pigLatinTranslate_test3() {

        String input = "Hello World";
        Assert.assertEquals("Ellohay Orldway", pigLatinTranslate(input));
    }

    @Test
    public void pigLatinTranslate_test4() {

        String input = "Hello, world!";
        Assert.assertEquals("Ellohay, orldway!", pigLatinTranslate(input));
    }

}
