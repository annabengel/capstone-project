package com.prs.business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.prs.db.PurchaseRequestRepository;
import com.prs.db.UserRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PurchaseRequestTests {
	
	@Autowired
	private PurchaseRequestRepository purchaseRequestRepo;
	
	@Autowired
	private UserRepository userRepo;
	
	@Test
	public void TestPurchaseRequestFindAll() {
		Iterable<PurchaseRequest> purchaseRequests = purchaseRequestRepo.findAll();
		assertNotNull(purchaseRequests);
	}
	
	@Before
	public void TestPurchaseRequestAddAndDelete() {
		Optional<User> u = userRepo.findById(1);
		PurchaseRequest pr = new PurchaseRequest(u.get(), "desc", "just", LocalDate.of(2019, 07, 15), "mail", 
				  					"review", 14.99, LocalDateTime.of(2019, 05, 23, 9, 00), "not needed");
		assertNotNull(purchaseRequestRepo.save(pr));
		assertEquals("desc", pr.getDescription());
		purchaseRequestRepo.delete(pr);
		assertFalse(purchaseRequestRepo.findById(pr.getId()).isPresent());
	}

}
