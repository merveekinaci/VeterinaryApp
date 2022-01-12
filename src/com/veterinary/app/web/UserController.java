package com.veterinary.app.web;

import javax.annotation.security.RolesAllowed;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.veterinary.app.model.User;
import com.veterinary.app.service.UserService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/{id}/profile")
    public String showUserProfile(@PathVariable Long id, Model model) throws ResponseStatusException {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı");
        }
        model.addAttribute("user", user.get());
        return "userProfile";
    }

    private User getUser(Long id, Authentication auth) throws ResponseStatusException {
        Optional<User> user = userService.findById(id);
        if (user.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Kullanıcı bulunamadı");
        }

        UserDetails userDetails = userService.loadUserByUsername(auth.getName());
        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"));
        if (isAdmin) {
            return user.get();
        }

        User currentUser = userService.findByEmail(auth.getName());
        if (!currentUser.equals(user.get())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Bunu yapmaa yetkiniz yok");
        }
        return user.get();
    }

    @ModelAttribute("user")
    public UserUpdateDto userUpdateDto() { return new UserUpdateDto(); }

    @GetMapping("/{id}/update")
    public String showUserProfileForm(@PathVariable Long id, Model model, Authentication auth) throws ResponseStatusException {
        User user = this.getUser(id, auth);
        model.addAttribute("user", user);
        model.addAttribute("userId", user.getId());
        return "updateUserProfile";
    }

    @PostMapping("/{id}/update")
    public String updateUserProfile(@PathVariable Long id, @ModelAttribute("user") @Valid UserUpdateDto userUpdateDto,
                                    BindingResult result, Authentication auth, Model model) throws ResponseStatusException {
        if (result.hasErrors()) {
            model.addAttribute("userId", id);
            return "updateUserProfile?hasErrors";
        }

        User user = this.getUser(id, auth);
        userService.update(userUpdateDto, user);
        return "redirect:/user/" + id + "/profile";
    }

    @GetMapping("/search")
    public String search(@RequestParam(value = "name", required = false) String name, Model model) {
        if (name == null) {
            return "searchByUserName";
        }
        List<User> owners = userService.findByFirstNameContainingOrLastNameContaining(name, name);
        if (owners.isEmpty()) {
            return "redirect:/user/search?noResult";
        }

        model.addAttribute("owners", owners);
        return "searchByUserName";
    }
}
