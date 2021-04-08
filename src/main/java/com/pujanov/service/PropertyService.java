package com.pujanov.service;

import java.util.List;

import com.pujanov.dto.PropertyDTO;



public interface PropertyService {

	public List<PropertyDTO> getAllProperty();
	
	public PropertyDTO getAPropertyById(int id);
	
	public int addProperty(PropertyDTO cityDTO);
	
	public void updateProperty(int id, PropertyDTO cityDTO);
	
	public void deleteProperty(int id);

	public void approveProperty(int id);

	public List<PropertyDTO> getSearchedResults(String searchKey);
	
}
