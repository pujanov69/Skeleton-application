package com.pujanov.dao;

import java.util.List;

import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import com.pujanov.dto.PropertyDTO;



public interface PropertyDAO {

	@SqlQuery("SELECT * FROM property")
	@RegisterBeanMapper(PropertyDTO.class)
	public List<PropertyDTO> getAllProperty();
	
	
	@SqlQuery("SELECT * FROM property WHERE id = :propertyId")
	@RegisterBeanMapper(PropertyDTO.class)
	public PropertyDTO getPropertyById(@Bind("propertyId") Integer propertyId);
	
	@SqlUpdate("INSERT INTO property (type, name, address, owner_name, construction_year, on_sale, approved) VALUE(:type, :name, :address, :ownerName, :constructionYear, :onSale, :approved)")
	@GetGeneratedKeys
	public int addProperty(@BindBean PropertyDTO propertyDTO);
	
	@SqlUpdate("UPDATE property SET type= :type, name = :name , address = :address, owner_name = :ownerName , construction_year= :constructionYear, on_sale = :onSale, approved = :approved WHERE id = :cityId")
	public void updateCity(@Bind("cityId") int id, @BindBean PropertyDTO cityDTO);
	
	@SqlUpdate("DELETE FROM property WHERE id = :propertyId")
	public void deleteCityById(@Bind("propertyId") int id);
	
	
	
}
