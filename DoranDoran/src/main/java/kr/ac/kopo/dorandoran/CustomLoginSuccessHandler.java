package kr.ac.kopo.dorandoran;

import java.io.IOException;
import java.util.Collection;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		// 1. 권한 체크
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		for (GrantedAuthority authority : authorities) {
			if ("ROLE_ADMIN".equals(authority.getAuthority())) {
				response.sendRedirect("/admin");
				return;
			}
		}

		// 2. 일반 사용자 리다이렉트 처리
		HttpSession session = request.getSession();
		String redirectUrl = (String) session.getAttribute("prePage");

		if (redirectUrl != null) {
			session.removeAttribute("prePage"); // 사용 후 제거

			if (redirectUrl.contains("/signup")) {
				response.sendRedirect("/");
			} else {
				response.sendRedirect(redirectUrl);
			}
		} else {
			SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(request, response);
			if (savedRequest != null) {
				String savedUrl = savedRequest.getRedirectUrl();

				if (savedUrl != null && savedUrl.contains("/signup")) {
					response.sendRedirect("/");
				} else {
					response.sendRedirect(savedUrl);
				}
			} else {
				response.sendRedirect("/");
			}
		}
	}
}
