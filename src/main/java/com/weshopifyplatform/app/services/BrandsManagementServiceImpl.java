package com.weshopifyplatform.app.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.weshopifyplatform.app.beans.BrandsBean;
import com.weshopifyplatform.app.beans.CategoriesBean;
import com.weshopifyplatform.app.entities.Brands;
import com.weshopifyplatform.app.outbound.CategoriesOutboundCommunicator;
import com.weshopifyplatform.app.outbound.CategoriesOutboundFeignClient;
import com.weshopifyplatform.app.repos.BrandsDocumentRepository;

@Service
public class BrandsManagementServiceImpl implements BrandsManagementService {
	
	private BrandsDocumentRepository brandsRepo;
	
	private ModelMapper modelMapper;
	
	private CategoriesOutboundFeignClient categoriesClient; 
	
	public BrandsManagementServiceImpl(BrandsDocumentRepository brandsRepo, ModelMapper modelMapper,CategoriesOutboundFeignClient categoriesClient) {
		this.brandsRepo = brandsRepo;
		this.modelMapper= modelMapper;
		this.categoriesClient=categoriesClient;
	}

	@Override
	public BrandsBean createBrand(BrandsBean brandsBean) {
		Brands brandsEntity = mapBeanToEntity(brandsBean);
		List<CategoriesBean> categoriesList = new ArrayList<>();
		if(!CollectionUtils.isEmpty(brandsBean.getCategories())) {
			brandsBean.getCategories().stream().forEach(catBean ->{
				catBean = categoriesClient.getCategory(catBean.getId());
				categoriesList.add(catBean);
			});
		}
		brandsEntity.setCategories(categoriesList);
		brandsRepo.save(brandsEntity);
		brandsBean = mapEntityToBean(brandsEntity);
		return brandsBean;
	}

	@Override
	public BrandsBean updateBrands(BrandsBean brandsBean) {
		Brands brandsEntity = mapBeanToEntity(brandsBean);
		brandsRepo.save(brandsEntity);
		brandsBean = mapEntityToBean(brandsEntity);
		return brandsBean;
	}

	@Override
	public BrandsBean getBrandById(String brandId) {
		Brands brandsEntity = brandsRepo.findById(brandId).get();
		BrandsBean brandsBean = mapEntityToBean(brandsEntity);
		return brandsBean;
	}

	@Override
	public List<BrandsBean> getAllBrands() {
		List<BrandsBean> brandsBeanlist = null;
		
		List<Brands> dbBrands= brandsRepo.findAll();
		
		if(!CollectionUtils.isEmpty(dbBrands)) {
			brandsBeanlist = dbBrands.stream()
					.map(brands->mapEntityToBean(brands))
					.collect(Collectors.toList());
		}
		return brandsBeanlist;
	}

	@Override
	public List<BrandsBean> deleteBrands(String brandId) {
		List<BrandsBean> brandsBeanlist = null;
		
		brandsRepo.deleteById(brandId);
		
		List<Brands> dbBrands= brandsRepo.findAll();
		
		if(!CollectionUtils.isEmpty(dbBrands)) {
			brandsBeanlist = dbBrands.stream()
					.map(brands->mapEntityToBean(brands))
					.collect(Collectors.toList());
		}
		return brandsBeanlist;
	}
	
	private Brands mapBeanToEntity(BrandsBean brandsBean) {
		Brands brandsEntity = modelMapper.map(brandsBean,Brands.class);
		return brandsEntity;
	}
	
	private BrandsBean mapEntityToBean(Brands brands) {
		BrandsBean brandsBean = modelMapper.map(brands, BrandsBean.class);
		return brandsBean;
	}

	@Override
	public void clearDb() {
		brandsRepo.deleteAll();
	}

}
