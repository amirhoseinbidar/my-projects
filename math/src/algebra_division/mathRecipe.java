package algebra_division;
/**
 * Created by abk on 5/5/2017.
 */
public class mathRecipe extends baseRecipe  {
    public String divisionSimplify(String Up, String Down) {
        String result = "";
        int upNumber = Numbersentence(Up);
        int downNumber = Numbersentence(Down);

        if (upNumber == 1 && downNumber == 1) {

        }

        if (downNumber == 1) {
        }

        char variable = ' ';
        for (int i = 0; i <= Down.length() - 1; i++)
            if (Character.isLetter(Down.charAt(i))) {
                variable = Down.charAt(i);
                break;
            }
        String[] up = sort_getArray(Up, variable);
        return result;
    }



    //Division Simplify Witch have one sentence denominator
    private String[] DSWHOSD_getArray(String value)
    {
       String data [] =value.split("/");
        return data;
    }
}
