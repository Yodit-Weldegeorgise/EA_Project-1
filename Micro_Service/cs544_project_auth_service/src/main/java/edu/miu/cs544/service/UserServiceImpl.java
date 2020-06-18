package edu.miu.cs544.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.miu.cs544.domain.User;
import edu.miu.cs544.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public User getByUsername(String username) {
		return userRepository.findByUsername(username).get();
	}

	@Override
	public List<User> getAll() {
		return userRepository.findAll();
	}

}
