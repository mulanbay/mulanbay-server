package cn.mulanbay.web.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 支持请求参数的重复读取
 *
 * @author fenghong
 * @create 2018-01-20 21:44
 */
public class MultipleRequestFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(MultipleRequestFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String contentType = request.getContentType();
        if (contentType != null && contentType.startsWith("multipart/form-data")) {
            chain.doFilter(request, response);
        } else {
            MultipleRequestWrapper requestWrapper = new MultipleRequestWrapper((HttpServletRequest) request);
            logger.debug("MultipleHttpServletRequestWrapper...");
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void destroy() {

    }
}
