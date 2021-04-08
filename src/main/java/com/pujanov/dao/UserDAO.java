package com.pujanov.dao;
import java.util.Collection;
import java.util.List;

import org.jdbi.v3.core.mapper.JoinRow;
import org.jdbi.v3.sqlobject.config.RegisterJoinRowMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import com.pujanov.domain.Role;
import com.pujanov.domain.User;
/**
 * 
 * @author Pujan KC
 *
 */
public interface UserDAO {
	
	@GetGeneratedKeys
	@SqlUpdate("INSERT into user (username, first_name, middle_name, last_name, email, password, enabled, locked) VALUES(:username, :firstName, :middleName, :lastName, :email, :password, :enabled, :locked)")
	public int addUser(@BindBean User user);

	@SqlUpdate("UPDATE user set username = :username, first_name = :firstName, middle_name = :middleName, last_name = :lastName, email = :email, password = :password, enabled = :enabled, locked = :locked WHERE id= :userId")
	public void updateUser(@BindBean User user, @Bind("userId") int userId);

	@SqlUpdate("DELETE FROM user where id = :userId")
	public void deleteUser(@Bind("userId") int userId);
	
	@SqlQuery("SELECT u.*, r.id AS r_id, r.role AS r_role FROM user u INNER JOIN user_role ur ON u.id = ur.user_id "
			+ "INNER JOIN role r ON r.id = ur.role_id "
			+ "WHERE u.username = :username AND u.enabled = 1")
	@RegisterJoinRowMapper({User.class, Role.class})
	public List<JoinRow> getUserByUsername(@Bind("username") String username);
	
	@SqlQuery("SELECT u.*, r.id AS r_id, r.role AS r_role FROM user u INNER JOIN user_role ur ON u.id = ur.user_id "
			+ "INNER JOIN role r ON r.id = ur.role_id WHERE u.enabled = 3 ")
	@RegisterJoinRowMapper({User.class, Role.class})
	public List<JoinRow> getAllUsers();
	
	@SqlQuery("SELECT u.*, r.id AS r_id, r.role AS r_role FROM user u INNER JOIN user_role ur ON u.id = ur.user_id "
			+ "INNER JOIN role r ON r.id = ur.role_id "
			+ "WHERE u.id = :userId")
	@RegisterJoinRowMapper({User.class, Role.class})
	public List<JoinRow> getUserByUserId(@Bind("userId") int userId);

	@SqlBatch("INSERT INTO user_role (user_id, role_id) VALUES (:userId, :role.id)")
	public void addUserRoles(@Bind("userId") int userId, @BindBean("role") Collection<Role> role);
	
	@SqlUpdate("INSERT INTO user_role (user_id, role_id) VALUES (:userId, :roleId)")
	public void addUserRole(@Bind("userId") int userId, @Bind("roleId") int roleId);

	@SqlUpdate("DELETE FROM user_role WHERE user_id = :userId")
	public void deleteUserRole(@Bind("userId") int userId);

}
