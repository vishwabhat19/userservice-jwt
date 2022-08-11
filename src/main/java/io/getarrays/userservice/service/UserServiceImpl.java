package io.getarrays.userservice.service;

import io.getarrays.userservice.domain.Role;
import io.getarrays.userservice.domain.User;
import io.getarrays.userservice.repo.RoleRepository;
import io.getarrays.userservice.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserServiceImpl implements UserService, UserDetailsService
{
	private final UserRepository userRepository;

	private final RoleRepository roleRepository;

	private final PasswordEncoder passwordEncoder;

	@Override public User saveUser(User user)
	{
		log.info("Saving new user {} to database", user.getName());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}

	@Override public Role saveRole(Role role)
	{
		log.info("Saving new role {} to database", role.getName());
		return roleRepository.save(role);
	}

	@Override public void addRoleToUser(String username, String roleName)
	{
		log.info("Adding role {} to user {}", roleName, username);
		User user = userRepository.findByUsername(username);
		Role role = roleRepository.findByName(roleName);
		user.getRoles().add(role);
	}

	@Override public User getUser(String username)
	{
		log.info("Fetching user {}", username);
		return userRepository.findByUsername(username);
	}

	@Override public List<User> getUsers()
	{
		log.info("Fetching all the users");
		return userRepository.findAll();
	}

	@Override public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
	{
		User user = userRepository.findByUsername(username);
		if(null == user) {
			log.error("User Not Found!!");
			throw new UsernameNotFoundException("User Not Found!!");
		}
		else {
			log.info("User Found: {}", username);
		}
		Collection<SimpleGrantedAuthority> authorities = new ArrayList();
		user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
	}
}
