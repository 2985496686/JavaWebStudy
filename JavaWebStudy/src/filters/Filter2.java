package filters;

import javax.servlet.*;
import java.io.IOException;

public class Filter2 implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("doFilter2前置代码执行");
        filterChain.doFilter(servletRequest,servletResponse);
        System.out.println("dpFilter2后置代码执行");
    }


}
