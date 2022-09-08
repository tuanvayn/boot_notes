package com.vmodev.tuanva.notes.repo;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.vmodev.tuanva.notes.model.UserInfo;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepoTest {
	@Autowired
	UserRepo repo;

	static final String USERNAME = "admin";

	@Test
	void givenUsername_checkExistedByUsername() {
		boolean isExist = repo.existsByUsername(USERNAME);
		assertEquals(true, isExist);
	}

	@Test
	void givenUsername_findUserInfoByUsername() {
		UserInfo user = repo.findByUsername(USERNAME).get();
		assertEquals(USERNAME, user.getUsername());
		assertNotNull(user.getUsername());
	}
}
