package mulanbay;

import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestString {

    public static void main(String[] args) {
        String ss = "abcd '{0}'  ccccc  '{1}' '测试'";
        System.out.println(ss);
        String[] pp = new String[]{"235", "999"};
        Matcher m = Pattern.compile("\\{(\\d)\\}").matcher(ss);
        while (m.find()) {
            ss = ss.replace(m.group(), pp[Integer.parseInt(m.group(1))]);
        }
        System.out.println(MessageFormat.format(ss, "235", "999"));
    }
}
