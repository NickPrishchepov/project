package com.epam.horseraces.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import java.io.IOException;

/**
 * The {@code EncodingFilter} sets the one of the needed
 * encodings before the beginning of working with the web
 * pages
 *
 * @author Nick Prishchepov
 * @version %I%,%G%
 */
@WebFilter(urlPatterns = {"/*"},
        initParams = {
                @WebInitParam(name = "encoding", value = "UTF-8", description = "Encoding Param")
        })
public class EncodingFilter implements Filter {

    /**
     *  The page encoding
     */
    private String code;

    /**
     * @param config The {@code FilterConfing} object
     */
    public void init(FilterConfig config) {
        code = config.getInitParameter("encoding");
    }

    /**
     * @param servletRequest  It's used for setting the encoding which
     *                        is needed
     * @param servletResponse It's used for setting the encoding which
     *                        is needed
     * @param filterChain     The {@code FilterChain} object
     * @throws ServletException If the ServletException  was occurred while
     *                          making the filter operations
     * @throws IOException      If the IOException  was occurred while
     *                          making the filter operations
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String codeRequest = servletRequest.getCharacterEncoding();
        if (code != null && !code.equalsIgnoreCase(codeRequest)) {
            servletRequest.setCharacterEncoding(code);
            servletResponse.setCharacterEncoding(code);
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * The method sets the null to the
     * existing code
     */
    @Override
    public void destroy() {
        code = null;
    }
}
