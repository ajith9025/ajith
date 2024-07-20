package com.chainsys.spring.dao;

import java.util.List;

import org.springframework.stereotype.Repository;
import com.chainsys.spring.model.Users;

@Repository
public interface UserDAO {
	
	public void save(Users user);
	public List<Users> getUsers();
	public void deleteUser(int id);
	public int update(Users user);
	public List<Users> searchUser(String username);
	public List<Users> getUpdate(int id);
	public Users findByEmailAndPassword(String email, String password);
}
