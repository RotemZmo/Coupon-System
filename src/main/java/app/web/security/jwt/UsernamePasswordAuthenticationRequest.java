package app.web.security.jwt;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsernamePasswordAuthenticationRequest {

	private String email;
	private String password;
	
	public UsernamePasswordAuthenticationRequest() {
	}
	
	
	
}
