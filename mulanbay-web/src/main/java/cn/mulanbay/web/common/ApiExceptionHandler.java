package cn.mulanbay.web.common;

import cn.mulanbay.business.handler.MessageHandler;
import cn.mulanbay.common.exception.ApplicationException;
import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.exception.PersistentException;
import cn.mulanbay.common.exception.ValidateError;
import cn.mulanbay.web.bean.response.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Controller层异常处理类
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
@ControllerAdvice
public class ApiExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(ApiExceptionHandler.class);

	@Autowired
	protected MessageHandler messageHandler;

	protected boolean doSystemLog(){
		return false;
	}

	/**
	 * 程序异常
	 * 
	 * @param ae
	 * @return
	 */
	@ExceptionHandler(ApplicationException.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResultBean handleApplicationExceptionError(ApplicationException ae,HttpServletRequest request) {
		if (ae.getMyException() == null) {
			logger.error("handleApplicationExceptionError:"+ae.getMessage(),ae);
		} else {
			logger.error("handleApplicationExceptionError:"+ae.getMessage(), ae.getMyException());
		}

		ResultBean rb = new ResultBean();
		rb.setCode(ae.getErrorCode());
		ValidateError ve = messageHandler.getErrorCodeInfo(ae.getErrorCode());
		rb.setMessage(ve.getErrorInfo()+",错误详情:"+ae.getMessage());
		if(doSystemLog()){
			this.addSystemLog(request,ae.getClass(),ve.getErrorInfo(),rb.getMessage(),ae.getErrorCode());
		}
		return rb;
	}

	/**
	 * 处理controller的验证错误处理
	 * 
	 * @param be
	 * @return
	 */
	@ExceptionHandler(BindException.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResultBean handleBindExceptionError(BindException be,HttpServletRequest request) {
		logger.error("handleBindExceptionError",be);
		ResultBean rb = new ResultBean();
		Errors errors = be.getBindingResult();
		ValidateError ve = this.getValidateError(errors);
		//默认选择第一个
		rb.setCode(ve.getCode());
		rb.setMessage(ve.getErrorInfo());
		//rb.setData(es);
		if(doSystemLog()){
			this.addSystemLog(request,be.getClass(),"请求参数验证异常",rb.getMessage()+",代码:("+ve.getField()+")",rb.getCode());
		}
		return rb;
	}

	/**
	 * 获取验证的错误信息，默认是第一个
	 * @param errors
	 * @return
	 */
	private ValidateError getValidateError(Errors errors){
		ObjectError oe =errors.getAllErrors().get(0);
		String key = oe.getDefaultMessage();
		ValidateError ve = messageHandler.getErrorInfo(key);
		return ve;
	}

	/**
	 * 处理spring boot 模式下controller的验证错误处理
	 *
	 * @param mae
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResultBean handleMethodArgumentNotValidExceptionError(MethodArgumentNotValidException mae, HttpServletRequest request) {
		logger.error("MethodArgumentNotValidException",mae);
		ResultBean rb = new ResultBean();
		List<FieldError> errors = mae.getBindingResult().getFieldErrors();
		List<ValidateError> es = messageHandler.getErrorInfo(errors);
		//默认选择第一个
		rb.setCode(es.get(0).getCode());
		rb.setMessage(es.get(0).getErrorInfo());
		//rb.setData(es);
		if(doSystemLog()){
			this.addSystemLog(request,mae.getClass(),
					"请求参数验证异常",rb.getMessage()+",代码:("+es.get(0).getField()+")",rb.getCode());
		}
		return rb;
	}

	/**
	 * 处理PersistentException持久层异常
	 * 
	 * @param pe
	 * @return
	 */
	@ExceptionHandler(PersistentException.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResultBean handlePersistentExceptionError(PersistentException pe,HttpServletRequest request) {
		logger.error("handlePersistentExceptionError:"+pe.getMessage(), pe.getMyException());
		ResultBean rb = new ResultBean();
		rb.setCode(pe.getErrorCode());
		rb.setMessage(pe.getMessage());
		if(doSystemLog()){
			String message = pe.getMyException().getMessage();
			this.addSystemLog(request,pe.getClass(),"持久层处理异常",message,rb.getCode());
		}
		return rb;
	}

	@ExceptionHandler(RuntimeException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ResultBean handleUnexpectedServerError(RuntimeException ex,HttpServletRequest request) {
		logger.error("RuntimeException", ex);
		ResultBean rb = new ResultBean();
		if(ex instanceof NullPointerException){
			rb.setCode(ErrorCode.NULL_POINT_EXCEPTION);
			rb.setMessage("空指针异常");
		}else{
			rb.setCode(ErrorCode.SYSTEM_ERROR);
			rb.setMessage("系统异常：" + ex.getMessage());
		}
		if(doSystemLog()){
			this.addSystemLog(request,ex.getClass(),"未能捕获的运行期异常",rb.getMessage(),rb.getCode());
		}
		return rb;
	}

	/**
	 * 程序异常
	 *
	 * @param ae
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseStatus(value = HttpStatus.OK)
	@ResponseBody
	public ResultBean handleExceptionError(Exception ae,HttpServletRequest request) {
		logger.error("handleExceptionError,class:"+ae.getClass()+",Exception:"+ae.getMessage(),ae);
		ResultBean rb = new ResultBean();
		rb.setCode(ErrorCode.UNKHOWM_ERROR);
		ValidateError ve = messageHandler.getErrorCodeInfo(ErrorCode.UNKHOWM_ERROR);
		rb.setMessage(ve.getErrorInfo()+":"+ae.getMessage());
		if(doSystemLog()){
			this.addSystemLog(request,ae.getClass(),"系统异常",rb.getMessage(),rb.getCode());
		}
		return rb;
	}

	protected void addSystemLog(HttpServletRequest request,Class exceptionClass,String title,String msg,int errorCode){

	}
	
}