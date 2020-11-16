package mulanbay;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestNumber {

    public static void main(String[] args) {
        String str = "sds7573sdaskds";
        Pattern pattern = Pattern.compile("(?<=\\D)\\d{4}(?!\\d)");
        Matcher matcher = pattern.matcher(str);
        if (matcher.find()) {
            String s = matcher.group();
            System.out.println(s);
        }
    }
}
