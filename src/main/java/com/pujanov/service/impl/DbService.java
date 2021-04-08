package com.pujanov.service.impl;

import org.jdbi.v3.core.Jdbi;
import org.springframework.stereotype.Service;
/**
 * 
 * @author Pujan KC
 *
 */
@Service
public class DbService {
	private Jdbi jdbi;

	public DbService(Jdbi jdbi) {
		this.jdbi = jdbi;
	}
	
	//returns the requested dao type class...
	public <T> T getDao(Class<T> daoClass) {
		if(jdbi != null) {
			try {
				if(daoClass != null) {
					return jdbi.onDemand(daoClass);
				}
			} catch(Exception ex) {
				ex.printStackTrace();
			}
		}
		
		throw new InternalError("Can't return dao class...");
	}
	
	public Jdbi getJdbi() {
		if (jdbi != null) {
			return jdbi;
		}
		
		throw new InternalError("Can't return jdbi. Maybe it's uninitialized...");
	}
}
