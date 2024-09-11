package backend.keumbangresource.userdetails;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CustomUserDetails implements UserDetails {
	
	private static final long serialVersionUID = -6706745185267700425L;
	private final String username;
	private final String role;
	private final Long userId;
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> collection = new ArrayList<>();
		collection.add(new GrantedAuthority() {

			private static final long serialVersionUID = -9122644802980538218L;

			@Override
			public String getAuthority() {
				// TODO Auto-generated method stub
				return role;
			}
			
		});
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return username;
	}
	
	public Long getUserId() {
		return userId;
	}

}
