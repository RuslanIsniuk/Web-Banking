package ua.rd.webbanking.controllerSpring;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ua.rd.webbanking.controllerSpring.command.impl.CheckLoginInput;
import ua.rd.webbanking.controllerSpring.model.LoginInputForm;
import ua.rd.webbanking.servlets.Servlet;

import javax.validation.Valid;

@Controller
public class ControllerSpringMVC {
    private static final Logger logger = Logger.getLogger(Servlet.class);
    @Autowired
    private CheckLoginInput checkLoginInput;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String viewLoginPage(Model model) {
        LoginInputForm loginInputForm = new LoginInputForm();
        model.addAttribute("loginInputForm", loginInputForm);
        return "index";
    }

    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    public String doLogin(@Valid LoginInputForm loginInputForm, BindingResult result, Model model) {

        String pathToJSP;

        if (result.hasErrors()) {
            loginInputForm.setErrorMessage("Error!!!!");
            model.addAttribute("loginInputForm", loginInputForm);
            return "index";
        }

        pathToJSP = checkLoginInput.execute(loginInputForm);
        model.addAttribute("loginForm", loginInputForm);
        return pathToJSP;
    }

}
