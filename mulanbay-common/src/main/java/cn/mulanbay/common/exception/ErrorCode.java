package cn.mulanbay.common.exception;

/**
 * 全局的错误代码
 * 可以使用的代码段：0-99，10000-19999
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class ErrorCode {

	// 系统级别 start
	public final static int SUCCESS = 0;

	public final static int OBJECT_GET_ERROR = 1;

	public final static int OBJECT_ADD_ERROR = 2;

	public final static int OBJECT_UPDATE_ERROR = 3;

	public final static int OBJECT_DELETE_ERROR = 4;

	public final static int OBJECT_GET_LIST_ERROR = 5;

	public final static int QUERY_BUILD_ERROR = 6;

	public final static int HIBERNATE_EXCEPTION = 7;

	public final static int SYSTEM_ERROR = 8;

	public final static int UNKHOWM_ERROR = 9;

	public final static int PARAMETER_DUPLIDATE_CAL = 10;

	public final static int HTTP_ERROR =11;

	public final static int DO_BUSS_ERROR =12;

	public final static int SYSTEM_STARTED =13;

	public final static int NULL_POINT_EXCEPTION =14;

	public final static int FUNCTION_UN_DEFINE =15;

	public final static int BEAN_GET_CACHE_ERROR =16;

	public final static int GET_CACHE_ERROR =17;
	// 系统级别 end

	public final static int FIELD_VALIDATE_ERROR = 10000;

	public final static int USER_NOTFOUND = 10001;

	public final static int USER_IS_STOP = 10002;

	public final static int USER_EXPIRED = 10003;

	public final static int USER_NOT_LOGIN = 10004;

	public final static int USER_TOKEN_ERROR = 10005;

	public final static int USER_PASSWORD_ERROR = 10006;

	public final static int USER_NOT_AUTH = 10007;

	public final static int EXECUTE_CMD_ERROR = 10008;

	public final static int IP_NOT_ALLOWED = 10009;

	public final static int FILE_PATH_NOT_EXIT = 10010;

	public final static int JSON_PARSE_ERROR =10011;

	public final static int THREAD_CAN_ONLY_DO_ONCE =10012;

	public final static int CREATE_CUSTOM_SSL_ERROR=10013;

	public final static int FTP_ERROR=10014;

	public final static int PAGE_SIZE_OVER_MAX=10015;

	public final static int USER_REQUEST_TOO_FREQ=10016;

	public final static int USER_FUNCTION_TOO_FREQ=10017;

	public final static int USER_LOGIN_FAIL_MAX=10018;

	public final static int USER_SEC_AUTH_FAIL=10019;

	public final static int CMD_EXEC_NOTIFY =10020;

	public final static int CMD_EXEC_ERROR =10021;

	public final static int USER_VERIFY_CODE_ERROR =10022;

	public final static int USER_LOGIN_FAMILY_FAIL =10023;

	public final static int USER_FAMILY_NO_ADMIN =10024;

	public final static int USER_NOT_IN_FAMILY =10025;

}
