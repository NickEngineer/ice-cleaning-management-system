package com.SmartBuildingManagementSystem.System.Controller;

import com.SmartBuildingManagementSystem.System.Model.CSSCondition;
import com.SmartBuildingManagementSystem.System.Model.User;
import com.SmartBuildingManagementSystem.System.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody
    List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @RequestMapping(value = "/validate", method = RequestMethod.GET)
    public ModelAndView validateUser() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userFromServer", new User());
        modelAndView.setViewName("UsersLogin");
        return modelAndView;
    }

    @RequestMapping(value = "/about", method = RequestMethod.GET)
    public ModelAndView getAboutSystemPage() {
        ModelAndView aboutSystemPageModelAndView = new ModelAndView("About");
        aboutSystemPageModelAndView.addObject("userFromServer", new User());
        return aboutSystemPageModelAndView;
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public ModelAndView registerUserPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("userFromServer", new User());
        modelAndView.setViewName("Registration");
        return modelAndView;
    }

    @RequestMapping(value = "/addNewUser", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView addNewUser(@ModelAttribute("userFromServer") User user) {
        if (userService.addNewUser(user)) {
            ModelAndView generalPageModelAndView = new ModelAndView("redirect:/validate");
            return generalPageModelAndView;
        } else {
            ModelAndView generalPageModelAndView = new ModelAndView("redirect:/");
            return generalPageModelAndView;
        }

    }

    @RequestMapping(value = "/validate/check", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView checkUser(@ModelAttribute("userFromServer") User user) {
        if ("admin".equals(user.getLogin()) && "admin".equals(user.getPassword())) {
            //getPrivateCab();
            return new ModelAndView("redirect:/private-cabinet");
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("UsersLogin");
        modelAndView.addObject("userFromServer",
                new User(user.getLogin(), user.getPassword()));

        ModelAndView modelAndViewStyle = new ModelAndView("/resources/theme0/css/invalidEnterSystemPageStyle.css");
        modelAndView.addObject(modelAndViewStyle);
        CSSCondition cssCondition = new CSSCondition(true);

        return modelAndView.addObject("cssCondition", cssCondition);
    }

    @RequestMapping(value = "/private-cabinet", method = RequestMethod.GET)
    public @ResponseBody
    ModelAndView getPrivateCab() {
        ModelAndView privateCabinetModelAndView = new ModelAndView("PrivateCabinet");

        return privateCabinetModelAndView;
    }


}