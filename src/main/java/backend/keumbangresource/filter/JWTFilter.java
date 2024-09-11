package backend.keumbangresource.filter;

import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import backend.keumbangresource.grpc.GrpcTokenClient;
import backend.keumbangresource.grpc.token.TokenProto.TokenRequestProto;
import backend.keumbangresource.jwt.JWTUtil;
import backend.keumbangresource.userdetails.CustomUserDetails;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

	private final JWTUtil jwtUtil;
	private final GrpcTokenClient grpcTokenClient;
	@Override
	protected void doFilterInternal(
			HttpServletRequest request, 
			HttpServletResponse response,
			FilterChain filterChain)
			throws ServletException, IOException {

		String authorization = request.getHeader("Authorization");
		// 헤더 검증
		if (authorization == null || !authorization.startsWith("Bearer ")) {
			filterChain.doFilter(request, response);
			return; // 헤더 없으면 다음 필터로
		}
		String accessToken = authorization.split(" ")[1];

		try {
			jwtUtil.isExpired(accessToken);
		}catch (ExpiredJwtException e) {
			PrintWriter writer = response.getWriter();
			writer.print("access token expired");

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		String category = jwtUtil.getCategory(accessToken);
		if (!category.equals("access")) {
			PrintWriter writer = response.getWriter();
			writer.print("invalid access token");

			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}

		var protoRequest = TokenRequestProto.newBuilder()
				.setAccess(accessToken)
				.build();
		var userInfo = grpcTokenClient.getUser(protoRequest);
		
		if (!userInfo.getIsValid()) {
			PrintWriter writer = response.getWriter();
			writer.print("invalid access token");
			
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		}
		
		// User 엔티티 생성
		var user = new CustomUserDetails(userInfo.getUser().getUsername(), userInfo.getUser().getRole(), userInfo.getUser().getId());

		// Spring Security 인증 토큰 생성
		Authentication authToken = new UsernamePasswordAuthenticationToken(
				user, null, user.getAuthorities());
		// 세션에 사용자 등록
		SecurityContextHolder.getContext().setAuthentication(authToken);
		// 다음 필터로
		filterChain.doFilter(request, response);
		
	}
}