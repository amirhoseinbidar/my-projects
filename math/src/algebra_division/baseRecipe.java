package algebra_division;
import com.sun.org.apache.bcel.internal.generic.GOTO;

import javax.lang.model.type.NullType;
import java.nio.charset.CharsetEncoder;
import java.security.PublicKey;

/**
 * Created by abk on 5/6/2017.
 */
public class  baseRecipe {
    /*
    sort a algebra sentence pre a variable
    if you send ' ' lieu variable just  take sentence  part
     */
    protected String[] sort_getArray(String value, char sortBy) {
        String[] data;

        // find sentence number
        data = new String[Numbersentence(value)];

        // strew sentences in data variable
        String a = "";
        int b = 0;
        for (int i = 0; i <= value.length() - 1; i++) {
            if (i == 0) {
                if (value.charAt(i) == '-' || value.charAt(i) == '+')
                    a += value.charAt(i);
                else {
                    a += '+';
                    a += value.charAt(i);

                }
                i++;
            }
            if (value.charAt(i) == '-' || value.charAt(i) == '+') {
                data[b] = a;
                b++;
                a = "";
            }
            a += value.charAt(i);
        }
        data[b] = a;

        // sort sentence
        for (int i = data.length - 1; 0 < i; i--) {
            for (int j = 0; j < i; j++) {
                int beforeNo = 0;
                int nextNo = 0;
                for (int x = 0; x <= data[j].length() - 1; x++) {
                    if (data[j].charAt(x) == sortBy) {
                        try {
                            if ((data[j].charAt(x + 1) == '^') && (data[j].charAt(x + 2) == '(') && (Character.isDigit(data[j].charAt(x + 3)))) {
                                String k = Character.toString(data[j].charAt(x + 2));
                                beforeNo = Integer.parseInt(k);
                            }
                        } catch (Exception ex) {
                            beforeNo = 1;
                        }
                    }
                }
                for (int x = 0; x <= data[j + 1].length() - 1; x++) {
                    if (data[j + 1].charAt(x) == sortBy) {
                        try {
                            if ((data[j + 1].charAt(x + 1) == '^') && (data[j].charAt(x + 2) == '(') && Character.isDigit(data[j + 1].charAt(x + 3)))
                                nextNo = Integer.parseInt(Character.toString(data[j + 1].charAt(x + 2)));
                        } catch (Exception ex) {
                            nextNo = 1;
                        }
                    }
                }

                if (beforeNo < nextNo) {
                    String temp = data[j];
                    data[j] = data[j + 1];
                    data[j + 1] = temp;
                }
            }
        }
        return data;
    }
    protected String sort_getString(String value, char sortBy) {
        String[] a = sort_getArray(value, sortBy);
        String data = "";
        for (int i = 0; i <= a.length - 1; i++)
            data += a[i];
        return data;

    }



    protected int Numbersentence(String value) {
        int number = 0;
        for (int i = 0; i <= value.length() - 1; i++) {
            if (i == 1 && (value.charAt(i) == '-' || value.charAt(i) == '+'))
                number--;
            if (value.charAt(i) == '-' || value.charAt(i) == '+')
                number++;
        }
        number++;
        return number;
    }

    /* Breakdown sentence Like this
       a^(2)b^(5y)43^(31rad(2)) --> { [a][2] } , { [b][5y] } , { [43][31rad(2)] }
      */
    private String[][] breakdownSentence(String value) {


        String[][] data = new String[value.length()][2];
        int q=0;
        boolean w= false;
        for (int i = 0; i <= value.length() - 1; i++) {
            try{
                if ((value.charAt(i) == '^' && value.charAt(i + 1) == '(') ) {
                    int openParenthesis = 1;
                    int closeParenthesis = 0;
                    if (w == false) {
                        data[i - 1][1] = "";
                        data[i - 1][0] = "";
                        data[i - 1][0] = Character.toString(value.charAt(i - 1));
                    }
                    else
                        data[q][1] = "";
                    int j;
                    for (j = i + 2; j <= value.length() - 1; j++) {
                        if (value.charAt(j) == '(')
                            openParenthesis++;
                        if (value.charAt(j) == ')')
                            closeParenthesis++;
                        if (openParenthesis == closeParenthesis)
                            break;
                        if (w == true) {
                            data[q][1] += value.charAt(j);
                        }
                        else {
                            data[i - 1][1] += value.charAt(j);
                        }
                    }
                    if (value.length()-1 <= j)
                        break;
                    i = ++j;
                    w= false;
                }
            }
            catch (Exception ex)
            {}

            if (Character.isDigit(value.charAt(i))) {
                if(w ==false)
                {
                    w=true;
                    q = i;
                    data[i][0] = "";
                }
                data[q][0] += value.charAt(i);
                data[q][1] = "1";
                continue;
            }

            w=false;
            data[i][0] = "";
            data[i][0] += value.charAt(i);
            data[i][1] = "1";
        }
       int number = 0;
        for (int k = 0 ; k<= data.length-1 ; k++) {
            if (!((data[k][0] == null) || (data[k][0] == "")) ) {
               number++;
            }
        }
        String [][] result  = new String[number][2];
        int n =0;
        for (int k = 0 ; k<= data.length-1 ; k++) {
            if (!((data[k][0] == null) || (data[k][0] == "")) ) {
                result[n] = data[k];
                n++;
            }
        }
        result = regularize(result);
        return result;

    }
    private String [][] regularize(String [][] value )  {
        int s = 0;
        for(int i =0; i <=value.length-1 ; i++)
        {
            try {
                 Integer.parseInt(value[i][0]);
                String [] temp = value[s].clone();
                value[s] = value[i].clone();
                value[i] = temp.clone();
                s++;
            }
            catch (Exception ex) { }
        }
        return value;
    }
    private String [] findCoefficient(String [][] value ){
        String [] data = {"",""};
        value = regularize(value);
        for(int i =0;i <=value.length-1; i++)
        {
            try {
                Integer.parseInt(value[i][0]);
                data[0] += value[i][0];
                data[0] += value [i][1];
            }
            catch (Exception ex)
            {
                data[1]+= value[i][0];
                data[1]+= value[i][0];
            }
        }
        return data;
    }
    public String simplifyMultiSentence(String value) {
       String [] data = sort_getArray(value, ' ');
       for(int i=0 ;i <= data.length-1;i++)
       {

       }
       return value;
    }
    //Simplify sentence witch have parenthesis
    //  private String simplifySingleSentence(String value)
    {
        String [] data = new String[1];

        int firstExpNo = 0;
        int secondExpNo = 0;
        for(int i=0; i<= data.length -1 ; i++)
        {
           for(int j=i+1; j<=data.length-1; j++)
           {
               String [] St = data[j].split("");
               if (data[i].charAt(0)==data[j].charAt(0))
               {
                   String k= data[j];
                   data[j]="";
                   try {
                       firstExpNo = Integer.parseInt(Character.toString(data[i].charAt(2)));
                   }
                   catch (Exception ex)
                   {firstExpNo =1;}
                   try {
                       secondExpNo = Integer.parseInt(Character.toString(k.charAt(2)));
                   }
                   catch (Exception ex)
                   {secondExpNo =1;}
                   data[i]= data[i].charAt(0)+"^("+(secondExpNo+firstExpNo)+")";
               }
           }
        }
    }


    public String Division (String a,String b) {

        String [][] one = breakdownSentence(a);
        String [][] two = breakdownSentence(b);


        String [][] word  = new String[one.length + two.length][2];
        int number = 0;
        for(String[] s : one)
        {
            word[number] = s;
            number ++;
        }
        for(String[] s : two)
        {
            word[number] = s;
            number ++;
        }

        for(int i= 0; i <= word.length-1 ;i++) {
            for(int j =i+1 ; j <= word.length-1;j++) {
                String S1 = word[i][0];
                String S2 = word[j][0];

                if(S1.equals(S2))
                {
                    word[i][1] = minus(word[i][1], word[j][1]);
                    word[j][1] = "";
                    word[j][0] = "";
                }
                S1 = word[i][1];
                S2 = word[j][1];
                if(S1.equals(S2)) {
                    try {
                        long x = Long.parseLong(word[i][0]);
                        long y = Long.parseLong(word[j][0]);
                        word[i][0] = Long.toString(x / y);
                        word[j][1] = "";
                        word[j][0] = "";
                    }
                    catch (Exception  ex)
                    {

                    }
                }
                else {
                    try {
                        long L1 = Exponent(Long.parseLong(word[i][0]), Long.parseLong(word[i][1]));
                        word[i][0] = Long.toString(L1);
                        word[i][1] = "1";
                        long L2 = Exponent(Long.parseLong(word[j][0]), Long.parseLong(word[j][1]));
                        word[j][0] = Long.toString(L2);
                        word[j][1] = "1";
                        word[i][0] = Long.toString(L1/L2);
                        word[j][0] = "";
                        word[j][1] = "";
                    }
                    catch (Exception ex) {

                    }
                }
            }
        }

        String data = "";
        for(int i=0 ; i <= word.length-1;i++) {
            if (!(word[i][1].equals("1") || ( word[i][0].equals("") ||word[i][0].isEmpty() )))
                data += word[i][0] + "^(" + word[i][1] + ")";
            else
                data += word[i][0];
        }
        return data;

    }
    public String Multiply (String a , String b) {

        String [][] one = breakdownSentence(a);
        String [][] two = breakdownSentence(b);


        String [][] word  = new String[one.length + two.length][2];
        int number = 0;
        for(int k = 0 ; k<= one.length-1 ; k++)
        {
            word[number] = one[k];
            number ++;
        }
        for(int k = 0 ; k<= two.length-1 ; k++)
        {
            word[number] = two[k];
            number ++;
        }



        for(int i= 0; i <= word.length-1 ;i++) {
            for(int j =i+1 ; j <= word.length-1;j++) {
                String S1 = word[i][0];
                String S2 = word[j][0];

               if(S1.equals(S2))
               {
                   try {
                       word[i][1] = plus(word[i][1], word[j][1]);
                       word[j][1] = "";
                       word[j][0] = "";
                   }
                   catch (Exception ex) {}
               }
                S1 = word[i][1];
                S2 = word[j][1];
               if(S1.equals(S2)) {
                   try {
                       long x = Long.parseLong(word[i][0]);
                       long y = Long.parseLong(word[j][0]);
                       word[i][0] = Long.toString(x * y);
                       word[j][1] = "";
                       word[j][0] = "";
                   }
                   catch (Exception  ex)
                   {

                   }
               }
               else {
                   try {
                       long L1 = Exponent(Long.parseLong(word[i][0]), Long.parseLong(word[i][1]));
                       word[i][0] = Long.toString(L1);
                       word[i][1] = "1";
                       long L2 = Exponent(Long.parseLong(word[j][0]), Long.parseLong(word[j][1]));
                       word[j][0] = Long.toString(L2);
                       word[j][1] = "1";
                       word[i][0] = Long.toString(L1*L2);
                       word[j][0] = "";
                       word[j][1] = "";
                   }
                   catch (Exception ex) {

                   }
                   }
               }
            }
        word= regularize(word);
        String data = "";
        for(int i=0 ; i <= word.length-1;i++) {
            if (!(word[i][1].equals("1") || ( word[i][0].equals("") ||word[i][0].isEmpty() )))
                data += word[i][0] + "^(" + word[i][1] + ")";
            else
                data += word[i][0];
        }
        return data;



    }

    public String plus(String Left,String Right) {
        if(Left.equals("")||Left.isEmpty() || Right.equals("") || Right.isEmpty())
            throw new NullPointerException();
        try {
            int a = Integer.parseInt(Left);
            int b = Integer.parseInt(Right);
            return Integer.toString(a + b);
        } catch (Exception ex) {

            Left = Multiply(Left, "");
            Right = Multiply(Right, "");
            String[] a = findCoefficient(breakdownSentence(Left));
            String[] b = findCoefficient(breakdownSentence(Right));
            if ( this.equals(a[1],b[1]) ) {
               String k = plus(a[0], b[0]);
                return  k+a[1];
            }
            else {
              ;
                return  Left + " + " + Right;
            }
        }
    }
    public String minus(String Left,String Right) {
        if(Left.equals("")||Left.isEmpty() || Right.equals("") || Right.isEmpty())
            throw new NullPointerException();
        String data = "";
        try {
            int a = Integer.parseInt(Left);
            int b = Integer.parseInt(Right);
            return Integer.toString(a - b);
        } catch (Exception ex) {
            if (Left.equals( Right))
                data = "";
            else
                data = Left + "-" + Right;
            return data;
        }
    }
    public boolean equals(String a , String b) {

        String[][] one = breakdownSentence(a);
        String[][] two = breakdownSentence(b);
        boolean k = false;
        for (int i = 0; i <= one.length - 1; i++) {
            k = false;
            for (int j = 0; j <= two.length - 1; j++) {
                if (one[i][0].equals(two[j][0])) {
                    k = true;
                    break;
                }
            }
            if (k == false)
                return false;
        }
        return true;
    }
    private long Exponent(long a, long b){
        long result = 1;
        for (int i = 1 ; i<= b; i++)
        {
            result = result*a;
        }
        return  result;
    }
    // example : String="3^(2)" --> long = 9
    private long Exponent (String s){
return 0;
     }
}
