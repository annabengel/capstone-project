package com.prs.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.User;
import com.prs.db.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTests {
	
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testUserGetAll() {
		Iterable<User> users = userRepository.findAll();
		assertNotNull(users);
	}
	
	@Before
	public void testUserAddAndDelete() {
		User u = new User("userName", "password", "firstName", "lastName", 
				"phoneNumber", "email", true, true);
		assertNotNull(userRepository.save(u));
		assertEquals("lastName", u.getLastName());
		userRepository.delete(u);
		assertFalse(userRepository.findById(u.getId()).isPresent());
	}

}
