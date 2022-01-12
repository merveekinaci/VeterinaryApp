package com.veterinary.app.test;

import static org.assertj.core.api.Assertions.assertThat;
import com.veterinary.app.model.User;
import com.veterinary.app.service.UserService;
import com.veterinary.app.dto.UserRegistrationDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

@SpringBootTest
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
public class UserRegistrationControllerTests {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private UserService userService;

    @Test
    public void testRegisterForm() throws Exception {
        mvc.perform(post("/registration").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf())
                .param("firstName", "myFirstName")
                .param("lastName", "myLastName")
                .param("email", "globl@gmail.com")
                .param("confirmEmail", "globl@gmail.com")
                .param("password", "12345")
                .param("confirmPassword", "12345")
                .param("address", "myaddress")
                .param("phone", "55548422")
                .param("terms", "true")).andDo(print()).andExpect(status().isFound());
        User user = userService.findByEmail("globl@gmail.com");
        assertThat(user.getEmail()).isEqualTo("globl@gmail.com");
    }

    @Test
    public void testLoginForm() throws Exception {
        UserRegistrationDto reg = new UserRegistrationDto();
        reg.setEmail("loginTest@gmail.com");
        reg.setPassword("secret");
        userService.save(reg);
        mvc.perform(post("/login").contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .with(csrf())
                .param("username", "loginTest@gmail.com")
                .param("password", "12345")).andDo(print()).andExpect(status().isFound())
                .andExpect(redirectedUrl("/"));
    }
}
