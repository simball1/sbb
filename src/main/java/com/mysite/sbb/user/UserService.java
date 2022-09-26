package com.mysite.sbb.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class UserService {

	private final UserRepository userRepository;	
	private final PasswordEncoder passwordEncoder;
	
	public SiteUser create(String username, String email, String password) {
		SiteUser siteUser = new SiteUser();
		siteUser.setUsername(username);
		siteUser.setEmail(email);
		siteUser.setPassword(passwordEncoder.encode(password));
		this.userRepository.save(siteUser);
		return siteUser;
	}
}
