/**
 * 
 */
package com.leave.request.util;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @author eotayde
 *
 */
public class SecurityUtil {

	public static String getUsername() {
		String username = "";

		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			username = SecurityContextHolder.getContext().getAuthentication().getName();
		}

		return username;
	}

	public static boolean hasRole(String role) {
		boolean authorized = false;
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication()
					.getAuthorities();
			authorized = authorities.contains(new SimpleGrantedAuthority(role));
		}

		return authorized;
	}

}
