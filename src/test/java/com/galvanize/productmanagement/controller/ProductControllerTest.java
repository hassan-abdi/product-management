package com.galvanize.productmanagement.controller;

import com.galvanize.productmanagement.common.AbstractTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class ProductControllerTest extends AbstractTest {

	@Autowired
	private MockMvc mvc;

	@Test
	public void testFindMostVisitedProducts() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/product/top").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}
}