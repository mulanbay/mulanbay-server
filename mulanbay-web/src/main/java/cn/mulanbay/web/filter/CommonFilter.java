package cn.mulanbay.web.filter;

import cn.mulanbay.common.exception.ErrorCode;
import cn.mulanbay.common.util.JsonUtil;
import cn.mulanbay.web.bean.response.ResultBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 通用拦截：目前处理登录验证及未能捕获的异常
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class CommonFilter implements Filter {

	private static final Logger logger = LoggerFactory.getLogger(CommonFilter.class);

	@Override
	public void destroy() {
		logger.debug("CommonFilter destroy");

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
						 FilterChain chain) throws IOException, ServletException {
		try {
			logger.debug("CommonFilter start");
			logRequest(request);
			chain.doFilter(request, response);

		} catch (Exception e) {
			ResultBean rb = new ResultBean();
			logger.error("系统异常", e);
			rb.setCode(ErrorCode.SYSTEM_ERROR);
			rb.setMessage("系统异常");
			this.handleResult(request, response, rb);
		} finally {
			logger.debug("CommonFilter end");
		}

	}

	protected void logRequest(ServletRequest request){
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		logger.debug("收到请求:"+httpServletRequest.getRequestURI());
		logger.debug("请求参数："+JsonUtil.beanToJson(httpServletRequest.getParameterMap()));
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		logger.debug("CommonFilter init");

	}

	/**
	 *
	 * @param request
	 * @param response
	 * @param rb
	 *            目前一般为String，即拼装好的json数据
	 * @throws IOException
	 */
	void handleResult(ServletRequest request, ServletResponse response,
			ResultBean rb) throws IOException {
		String result = JsonUtil.beanToJson(rb);
		PrintWriter out = response.getWriter();
		String callback = request.getParameter("callback");
		if (callback == null || "".equals(callback)) {
			out.write(result);
			logger.debug("返回数据：" + result);
		} else {
			out.write(callback + "(" + result + ")");
			logger.debug("返回数据：" + callback + "(" + result + ")");
		}

		if (out != null) {
			out.close();
		}
	}

}
