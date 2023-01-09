package ERUTY.platform.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

public class LoginInterceptor implements HandlerInterceptor {

    public List loginEssential = Arrays.asList("/members/logout");
    public List loginInessential = Arrays.asList("/member/login");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String loginId = (String)request.getSession().getAttribute("loginId");

        if(loginId != null) {
            return true;
        }
        else {
            /*
            //요청했던 경로를 session에 등록
            String destUri = request.getRequestURI();
            String destQuery = request.getQueryString();
            String dest = (destQuery == null) ? destUri : destUri+"?"+destQuery;
            request.getSession().setAttribute("dest", dest);
             */

            response.sendRedirect("/members/login");

            return false;
        }
    }
}
