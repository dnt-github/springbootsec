package AutoPki.RestCertifications.controller;

import java.security.Principal;
import java.security.cert.X509Certificate;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
	
	@PreAuthorize("hasAuthority('ROLE_USER')")
	@RequestMapping(value = "/user")
    public String user(Model model, Principal principal, HttpServletRequest request) {
		/*
		 * X509Certificate[] data = (X509Certificate[])request.getAttribute(
		 * "javax.servlet.request.X509Certificate"); String value = ""; if(data != null)
		 * { value = data[0].getSubjectDN().getName(); }
		 */
        UserDetails currentUser 
          = (UserDetails) ((Authentication) principal).getPrincipal();
        model.addAttribute("username", currentUser.getUsername());
        return "user";
    }
}