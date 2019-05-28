package com.prs.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.db.ProductRepository;
import com.prs.db.VendorRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductTests {
	
	@Autowired
	private ProductRepository productRepo;
	
	@Autowired
	private VendorRepository vendorRepo;
	
	@Test 
	public void TestProductFindAll() {
		Iterable<Product> products = productRepo.findAll();
		assertNotNull(products);
	}
	
	@Before
	public void TestProductAddAndDelete() {
		Optional<Vendor> v = vendorRepo.findById(1);
		Product p = new Product(v.get(), "part", "computer", 1399.99, null, null);
		assertNotNull(productRepo.save(p));
		assertEquals("part", p.getPartNumber());
		productRepo.delete(p);
		assertFalse(productRepo.findById(p.getId()).isPresent());
		
	}

}
