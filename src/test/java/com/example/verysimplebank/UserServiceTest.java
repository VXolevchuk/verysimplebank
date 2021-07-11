package com.example.verysimplebank;


import com.example.verysimplebank.model.CustomUser;
import com.example.verysimplebank.model.UserRole;
import com.example.verysimplebank.repos.CustomUserRepository;
import com.example.verysimplebank.services.UserService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.platform.engine.discovery.PackageSelector;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {
    @Autowired
    private UserService userService;

    @MockBean
    private CustomUserRepository userRepository;

    @MockBean
    private PasswordEncoder encoder;

    @Test
    public void addUser(){
        CustomUser user = new CustomUser();
        String login = "Jake";
        user.setLogin(login);
        user.setPassword(encoder.encode("123"));
        user.setBlocked(false);
        user.setRole(UserRole.USER);

        userService.addUser(user);

        Mockito.verify(userRepository, Mockito.times(1)).save(user);

        Mockito.doReturn(user)
                .when(userRepository)
                .findCustomUserByLogin(login);

        CustomUser savedUser = userService.findByLogin(login);
        Assert.assertEquals(login, savedUser.getLogin());
        Assert.assertEquals(UserRole.USER, savedUser.getRole());
        Assert.assertFalse(savedUser.isBlocked());

        Mockito.verify(userRepository, Mockito.times(1)).findCustomUserByLogin(login);


    }

}
