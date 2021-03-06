package crud.controller;

import crud.model.User;
import crud.service.RoleService;
import crud.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping("/admin")
public class AdminController {
    final RoleService roleService;
    final UserService userService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping()
    public String listUsers(ModelMap model, Principal principal) {
        model.addAttribute("principalUser", userService.getUserByName(principal.getName()));
        model.addAttribute("listUsers", userService.listUsers());
        return "/admin/users";
    }

    @GetMapping("/getUserById")
    @ResponseBody
    public User getUserById(long id) {
        return userService.getUserById(id);
    }

    @PostMapping("/new")
    public String Create(@ModelAttribute("user") @Valid User user,
                         BindingResult bindingResult,
                         @RequestParam(required = false, name = "listRoles") String[] input_roles) {
//        if (bindingResult.hasErrors()) {
//            return "redirect:/admin";
//        }
        user.setRoles(roleService.getRolesByName(input_roles));
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @PatchMapping("/update")
    public String update(@ModelAttribute ("user") @Valid User user, BindingResult bindingResult,
                         @RequestParam(required = false, name = "listRoles") String[] input_roles) {
//        if (bindingResult.hasErrors()) {
//            return "redirect:/admin";
//        }
        user.setRoles(roleService.getRolesByName(input_roles));
        userService.updateUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/delete")
    public String delete(@ModelAttribute ("user") User user) {
        userService.deleteUser(user);
        return "redirect:/admin";
    }

}
