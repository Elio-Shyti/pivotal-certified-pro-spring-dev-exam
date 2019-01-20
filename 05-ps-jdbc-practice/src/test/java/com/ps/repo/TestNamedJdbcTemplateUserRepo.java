package com.ps.repo;

import com.ps.config.AppConfig;
import com.ps.config.TestDataConfig;
import com.ps.ents.User;
import com.ps.repos.UserRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by iuliana.cosmina on 6/4/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TestDataConfig.class, AppConfig.class})
@ActiveProfiles("dev")
// TODO 30. [BONUS] Write test methods to cover all methods in JdbcNamedTemplateUserRepo
public class TestNamedJdbcTemplateUserRepo {

    @Autowired
    @Qualifier("userNamedTemplateRepo")
    UserRepo userRepo;

    @Before
    public void setUp() {
        assertNotNull(userRepo);
    }

    @Test
    public void testFindById() {
        User user = userRepo.findById(1L);
        assertEquals("John", user.getUsername());
    }

    @Test(expected = EmptyResultDataAccessException.class)
    public void testNoFindById() {
        User user = userRepo.findById(99L);
        assertEquals("John", user.getUsername());
    }

    @Test
    public void testUpdatePassword() {
        userRepo.createUser(6L, "myUser", "password", "email");
        User user = userRepo.findById(6L);
        assertNotNull(user);
        userRepo.updatePassword(user.getId(), "newPassword");
        user = userRepo.findById(6L);
        assertEquals(user.password, "newPassword");
    }
    
}
