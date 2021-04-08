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
		PropertyDAO cityDao = dbService.getDao(PropertyDAO.class);
		return cityDao.getAllProperty();
	}

	@Override
	public PropertyDTO getAPropertyById(int id) {
		// TODO Auto-generated method stub
		PropertyDAO cityDao = dbService.getDao(PropertyDAO.class);
		return cityDao.getPropertyById(id);
	}

	
	@Override
	public int addProperty(PropertyDTO cityDTO) {
		// TODO Auto-generated method stub
		PropertyDAO cityDao = dbService.getDao(PropertyDAO.class);
		return cityDao.addProperty(cityDTO);
	}

	@Override
	public void updateProperty(int id, PropertyDTO cityDTO) {
		PropertyDAO cityDao = dbService.getDao(PropertyDAO.class);
		// TODO Auto-generated method stub
		cityDao.updateCity(id, cityDTO);
		
	}

	@Override
	public void deleteProperty(int id) {
		// TODO Auto-generated method stub
		PropertyDAO cityDao = dbService.getDao(PropertyDAO.class);
		cityDao.deleteCityById(id);
		
	}

}
