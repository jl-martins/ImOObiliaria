/**
 * Write a description of class ValidadorEmail here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import static java.util.regex.Pattern.compile;

// baseado na resposta de Jason Buberel em https://stackoverflow.com/questions/8204680/java-regex-email
public class ValidadorEmail
{
	private static final Pattern REGEX_EMAIL_VALIDO = 
	   compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static boolean validar(String strEmail){
        Matcher matcher = REGEX_EMAIL_VALIDO.matcher(strEmail);
        return matcher.find();
    }
}
