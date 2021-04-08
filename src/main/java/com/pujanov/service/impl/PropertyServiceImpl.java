package com.pujanov.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.pujanov.dao.PropertyDAO;
import com.pujanov.dto.PropertyDTO;
import com.pujanov.service.PropertyService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PropertyServiceImpl implements PropertyService{

	
	DbService dbService;
	
	public PropertyServiceImpl(DbService dbService) {
		this.dbService = dbService;
	}
	
	
	@Override
	public List<PropertyDTO> getAllProperty() {
		// TODO Auto-generated method stub
		PropertyDAO propertyDao = dbService.getDao(PropertyDAO.class);
		return propertyDao.getAllProperty();
	}

	@Override
	public PropertyDTO getAPropertyById(int id) {
		// TODO Auto-generated method stub
		PropertyDAO propertyDao = dbService.getDao(PropertyDAO.class);
		return propertyDao.getPropertyById(id);
	}

	
	@Override
	public int addProperty(PropertyDTO propertyDTO) {
		// TODO Auto-generated method stub
		PropertyDAO propertyDao = dbService.getDao(PropertyDAO.class);
		return propertyDao.addProperty(propertyDTO);
	}

	@Override
	public void updateProperty(int id, PropertyDTO propertyDTO) {
		PropertyDAO propertyDao = dbService.getDao(PropertyDAO.class);
		// TODO Auto-generated method stub
		propertyDao.updateProperty(id, propertyDTO);
		
	}

	@Override
	public void deleteProperty(int id) {
		// TODO Auto-generated method stub
		PropertyDAO propertyDao = dbService.getDao(PropertyDAO.class);
		propertyDao.deletePropertyById(id);
		
	}


	@Override
	public void approveProperty(int id) {
		// TODO Auto-generated method stub
		PropertyDAO propertyDao = dbService.getDao(PropertyDAO.class);
		propertyDao.approveProperty(id);
		
	}


	@Override
	public List<PropertyDTO> getSearchedResults(String searchKey) {
		PropertyDAO propertyDao = dbService.getDao(PropertyDAO.class);
		return propertyDao.searchProperty(searchKey);
	}

}
