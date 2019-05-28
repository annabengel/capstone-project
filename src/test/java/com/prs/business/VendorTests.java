package com.prs.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.business.Vendor;
import com.prs.db.VendorRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class VendorTests {
	
	@Autowired
	private VendorRepository vendorRepo;
	
	@Test
	public void testVendorFindAll() {
		Iterable<Vendor> vendors = vendorRepo.findAll();
		assertNotNull(vendors);
	}

	@Before
	public void testVendorAddAndDelete() {
		Vendor v = new Vendor("code", "name", "address", "city", "st", "zip", "555-555-5555", "email", true);
		assertNotNull(vendorRepo.save(v));
		assertEquals("code", v.getCode());
		vendorRepo.delete(v);
		assertFalse(vendorRepo.findById(v.getId()).isPresent());
	}
}
