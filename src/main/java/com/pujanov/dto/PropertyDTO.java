package com.pujanov.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PropertyDTO {
	
	private int id;
	private String type;
	private String name;
	private String address;
	private String ownerName;
	private int constructionYear;
	private boolean onSale;
	private boolean approved;

}
