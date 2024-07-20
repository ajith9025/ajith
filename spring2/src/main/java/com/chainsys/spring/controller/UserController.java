package com.chainsys.spring.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.chainsys.spring.dao.UserDAO;
import com.chainsys.spring.model.Users;
import com.chainsys.spring.validation.UserValidation;

import jakarta.servlet.http.HttpSession;

@Controller
public class UserController {

	@Autowired
	UserDAO userDao;
	
	@Autowired
	UserValidation userValidation;

	@Autowired
	JdbcTemplate jdbcTemplate;

	@RequestMapping("/")
	public String welcome() {
		return "index.jsp";

	}

	@PostMapping("/userExam")
	public String addUser(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("email") String email, @RequestParam("contactNumber") String contactNumber, Model model) {

		if (!userValidation.validateUsername(username)) {
			model.addAttribute("error", "Invalid username ");
			return "userExam.jsp";

		}

		if (!userValidation.passwordsMatch(password)) {
			model.addAttribute("error", "Invalid Password");
			return "userExam.jsp";
		}

		if (!userValidation.validateEmail(email)) {
			model.addAttribute("error", "Invalid email format");
			return "userExam.jsp";
		}

		if (!userValidation.validateMobile(contactNumber)) {
			model.addAttribute("error", "Invalid mobile number format");
			return "userExam.jsp";
		}

		Users user = new Users();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setContactNumber(contactNumber);
		try {
			userDao.save(user);
			model.addAttribute("success", "Registration successfully");

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "userExam.jsp";

		/*
		 * Register register=new Register(); register.setUsername(username);
		 * register.setPassword(password); register.setEmail(email);
		 * register.setContactno(contactno);
		 * System.out.println(register.getUsername()+register.getPassword()+register.
		 * getEmail()+register.getContactno()); registerDao.save(register);
		 * List<Register> users= registerDao.getUsers(); model.addAttribute("users",
		 * users); return "listofUser.jsp";
		 */
	}

	@PostMapping("/update")
	public String updateUser(@RequestParam("username") String username, @RequestParam("password") String password,
			@RequestParam("email") String email, @RequestParam("contact no") String contactno,
			@RequestParam("id") int id, Model model) {
		Users user = new Users();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		user.setContactNumber(contactno);
		user.setId(id);
		userDao.update(user);
		List<Users> users = userDao.getUsers();
		model.addAttribute("users", users);
		return "listofUser.jsp";

	}

	@PostMapping("/delete")
	public String Delete(@RequestParam("id") int id, Model model) {
		userDao.deleteUser(id);
		List<Users> users = userDao.getUsers();
		model.addAttribute("users", users);
		return "listofUser.jsp";
	}

	@PostMapping("/search")
	public String search(@RequestParam("username") String username, Model model) {
		List<Users> users = userDao.searchUser(username);
		model.addAttribute("users", users);
		return "listofUser.jsp";
	}

	@PostMapping("/getupdate")
	public String allUpadte(@RequestParam("id") int id, Model model) {
		List<Users> users = userDao.getUpdate(id);
		model.addAttribute("users", users);
		return "update.jsp";
	}

	@RequestMapping("/login")
	public String login(@RequestParam("email") String email, @RequestParam("password") String password, Model model,
			HttpSession session) {
		Users user = userDao.findByEmailAndPassword(email, password);

		if (user != null) {

			if (email.endsWith("@online.com")) {
				System.out.println("admin");
				return "adminlogin.jsp";
			} else {
				return "index.html";
			}
		} else {
			model.addAttribute("errorMessage", "Incorrect email or password");
			return "login.jsp";
		}
	}
}
