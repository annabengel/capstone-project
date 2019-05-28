package com.prs.business;

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
import com.prs.db.PurchaseRequestLineItemRepository;
import com.prs.db.PurchaseRequestRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LineItemTests {
	
	@Autowired
	private PurchaseRequestLineItemRepository lineItemRepo;
	@Autowired
	private PurchaseRequestRepository purchaseRequestRepo;
	@Autowired
	private ProductRepository productRepo;
	
	@Test
	public void TestLineItemFindAll() {
		Iterable<PurchaseRequestLineItem> lineItems = lineItemRepo.findAll();
		assertNotNull(lineItems);
	}
	
	@Before
	public void TestLineItemAddAndDelete() {
		
			Optional<PurchaseRequest> pr = purchaseRequestRepo.findById(2);
			Optional<Product> p = productRepo.findById(2);
			PurchaseRequestLineItem li = new PurchaseRequestLineItem(pr.get(), p.get(), 5);
			assertNotNull(lineItemRepo.save(li));
			lineItemRepo.delete(li);
			assertFalse(lineItemRepo.findById(li.getId()).isPresent());

	}
		
}
