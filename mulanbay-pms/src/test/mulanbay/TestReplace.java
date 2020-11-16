package mulanbay;

/**
 * ${DESCRIPTION}
 *
 * @author fenghong
 * @create 2018-02-17 15:34
 */
public class TestReplace {

    public static void main(String[] args) {

        String ss = "select * from aa wherer userId = {userId} and sasda={userId}";
        ss = ss.replaceAll("\\{userId\\}", "11");
        System.out.println(ss);

        String ec = "select * from aa wherer userId = {userId} {extraCondition}";
        ec = ec.replaceAll("\\{extraCondition\\}", "and a>0");
        System.out.println(ec);

    }
}
