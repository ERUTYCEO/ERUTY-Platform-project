package ERUTY.platform.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    public List loginEssential = Arrays.asList("/members/logout", "/members/changepwd", "/members/authmember", "/items/**", "/members/mypage");
    public List loginInessential = Arrays.asList("/", "/members/new", "/members/login", "/members/findpwd");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        if(session.getAttribute("loginId") != null) {
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

            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html; charset=UTF-8");

            PrintWriter printwriter = response.getWriter();
            printwriter.println("<script>alert('로그인이 필요합니다'); location.href='/members/login';</script>");

            return false;
        }
    }
}
