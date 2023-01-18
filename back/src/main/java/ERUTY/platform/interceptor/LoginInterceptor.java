package ERUTY.platform.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    public List loginEssential = Arrays.asList("/**");
    public List loginInessential = Arrays.asList("/", "/members/new", "/members/login", "/items/new", "/members/findpwd"));

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        log.info("LoginInterceptor preHandler");

        HttpSession session = request.getSession();

        if(session.getAttribute("loginId") != null) {
            log.info("로그인 상태입니다");
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
            log.info("로그인이 필요합니다");
            response.sendRedirect("/members/login");

            return false;
        }
    }
}
