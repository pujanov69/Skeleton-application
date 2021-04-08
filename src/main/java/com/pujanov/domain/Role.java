package com.pujanov.domain;

import org.jdbi.v3.core.mapper.reflect.ColumnName;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 * 
 * @author Pujan KC
 *
 */
public class Role {
	
	private int id;
	private String role;
	
	@ColumnName("r_id")
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	@ColumnName("r_role")
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	@Override
	public String toString() {
		return "Role [id=" + id + ", role=" + role + "]";
	}
	
}
