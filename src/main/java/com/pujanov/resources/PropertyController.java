package com.pujanov.resources;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.pujanov.dto.PropertyDTO;
import com.pujanov.dto.ResponseDTO;
import com.pujanov.exception.CustomSqlException;
import com.pujanov.service.PropertyService;

@RequestMapping("/property")
@RestController
public class PropertyController {

	private final PropertyService propertyService;
	
	public PropertyController(PropertyService cityService) {
		this.propertyService = cityService;
	}
	

	@GetMapping("/all")
	public ResponseDTO<List<PropertyDTO>> getAllProperty() throws CustomSqlException {
		return new ResponseDTO<List<PropertyDTO>> (propertyService.getAllProperty());
	}
	
	@GetMapping("/{id}")
	public ResponseDTO<PropertyDTO> getPropertyById(@PathVariable("id") int id) throws CustomSqlException {
		return new ResponseDTO<PropertyDTO> (propertyService.getAPropertyById(id));
	}
	
	@PostMapping("")
	public ResponseDTO<Integer> addProperty(@RequestBody PropertyDTO propertyDTO) throws CustomSqlException {
		return new ResponseDTO<Integer> (propertyService.addProperty(propertyDTO));
	}
	
	@PutMapping("/{id}")
	public void updateProperty(@RequestBody PropertyDTO propertyDTO, @PathVariable("id") int id) throws CustomSqlException {
		propertyService.updateProperty(id, propertyDTO);
	}
	
	@DeleteMapping("/{id}")
	public void deleteProperty(@PathVariable("id") int id) throws CustomSqlException{
		propertyService.deleteProperty(id);
	}
}
