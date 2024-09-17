package com.weshopifyplatform.app.beans;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import lombok.Data;

@Data
public class BrandsBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -128868370128041790L;

	private String id;

    private String name;
    private String logopath;
    
    private List<CategoriesBean> categories;
}
