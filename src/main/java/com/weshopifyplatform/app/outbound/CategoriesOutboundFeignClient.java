package com.weshopifyplatform.app.outbound;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.weshopifyplatform.app.beans.CategoriesBean;

//@FeignClient(name = "categories-feign-client", url="localhost:5052")
@FeignClient( name="weshopify-categories-mgmt-service")
public interface CategoriesOutboundFeignClient {

	@GetMapping(path="/api/categories/{categoryId}")
	public CategoriesBean getCategory(@PathVariable("categoryId") int categoryId);
}
