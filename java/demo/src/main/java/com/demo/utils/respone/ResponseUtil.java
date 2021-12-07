package com.demo.utils.respone;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.demo.bean.RestResponse;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author Alex
 * @date 2021/9/18 9:43
 */
public class ResponseUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    public static void write(HttpServletResponse httpServletResponse, RestResponse restResponse) throws IOException {
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("application/json");
        httpServletResponse.getWriter().println(mapper.writeValueAsString(restResponse));
    }
}
