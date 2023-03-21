package seeNear.seeNear_BE.global.common.Filter;

import org.springframework.web.filter.OncePerRequestFilter;
import seeNear.seeNear_BE.global.common.Wrapper.MyRequestWrapper;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class RequestWrappingFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("RequestWrappingFilter");

        MyRequestWrapper requestWrapper = new MyRequestWrapper(request);
        // Pass wrapped objects to the next filter or servlet
        filterChain.doFilter(requestWrapper, response);

    }
}
