package filters;

import javax.servlet.*;
import java.io.IOException;

public class Filter1 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("doFilter1前置代码执行");
        //filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("dpFilter1后置代码执行");
    }
}
