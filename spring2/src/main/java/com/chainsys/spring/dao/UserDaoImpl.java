package com.chainsys.spring.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.chainsys.spring.mapper.UserRowMapper;
import com.chainsys.spring.model.Users;

@Repository
public class UserDaoImpl implements UserDAO{
  
	@Autowired
    JdbcTemplate jdbcTemplate;
 	
	
	public void save(Users user) {
		// TODO Auto-generated method stub
		String query = "insert into user_exam(username,password,email,contact_number) values (?,?,?,?)";
		Object[] params = { user.getUsername(), user.getPassword(), user.getEmail(), user.getContactNumber() };
		int rows = jdbcTemplate.update(query, params);
		System.out.println("Inserted rows:" + rows);

	}
	@Override
	public List<Users> getUsers() {
		// TODO Auto-generated method stub
		String query="select id,username,password,email,contact_number from user_exam";
		List<Users>user= jdbcTemplate.query(query,new UserRowMapper());
		return user;
	}

	public int update(Users user) {
		// TODO Auto-generated method stub
		String query="update user_exam set username=?,password=?,email=?,contact_number=? where id=?";
		Object[] params= {user.getUsername(),user.getPassword(),user.getEmail(),user.getContactNumber(),user.getId() };
		int rows=jdbcTemplate.update(query, params);
		System.out.println("Updated rows:"+ rows);
		return rows;
	}

	public void deleteUser(int id) {
		String query="delete from user_exam where id=?";
		Object[] parems= {id};
		jdbcTemplate.update(query, parems);
		
	}
	
	public List<Users> searchUser(String username) {
		String query="select id,username,password,email,contact_number from user_exam where username LIKE ?";
		Object[] param= {username};
		return jdbcTemplate.query(query,new UserRowMapper());
	}
	
	public List<Users> getUpdate(int id) {
		String query="select id,username,password,email,contact_number from user_exam where id=?";
		Object[] param= {id};
		return jdbcTemplate.query(query,new UserRowMapper());		
	}
	@Override
	public Users findByEmailAndPassword(String email, String passWord) {
		String query = "select * from user_exam where email = ? and password = ?";
		Object[] params = { email, passWord };
		try {
			return jdbcTemplate.queryForObject(query, new UserRowMapper(), params);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}

	}
	
	
	
	
	
	
	
	
	
	

	

	}
	

	

	


