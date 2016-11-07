package sg.edu.nus.iss.ejava2016.epod.corsfilter;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/*
	Add this filter to your webapp. 
	Don't forget to change the package name above ^^^^^^
*/

@WebFilter(urlPatterns = "/*", asyncSupported = true, dispatcherTypes = DispatcherType.REQUEST)
public class CORSFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException { }

	@Override
	public void destroy() { }

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		HttpServletResponse resp = (HttpServletResponse)response;
		resp.setHeader("Access-Control-Allow-Origin", "*");

		chain.doFilter(request, response);
	}
}
