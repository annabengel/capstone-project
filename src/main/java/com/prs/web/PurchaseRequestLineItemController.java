package com.prs.web;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.prs.business.JsonResponse;
import com.prs.business.PurchaseRequestLineItem;
import com.prs.db.PurchaseRequestLineItemRepository;
import com.prs.db.PurchaseRequestRepository;

@RestController
@RequestMapping("/purchase-request-line-items")
public class PurchaseRequestLineItemController {
	
	@Autowired
	private PurchaseRequestLineItemRepository purchaseRequestLineItemRepo;
	
	@Autowired
	private PurchaseRequestRepository purchaseRequestRepo;
	
	@GetMapping("/")
	public JsonResponse getAll() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(purchaseRequestLineItemRepo.findAll());
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<PurchaseRequestLineItem> li = purchaseRequestLineItemRepo.findById(id);
			if (li.isPresent())
				jr = JsonResponse.getInstance(li);
			else
				jr = JsonResponse.getInstance("No purchase request line item found for id" + id);
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@PostMapping("/")
	public JsonResponse add(@RequestBody PurchaseRequestLineItem li) {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(purchaseRequestLineItemRepo.save(li));
			recalculateTotal(li);
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	

	private void recalculateTotal(PurchaseRequestLineItem li) {
		double purchaseTotal = 0;
		Iterable<PurchaseRequestLineItem> prlis = 
				purchaseRequestLineItemRepo.findByPurchaseRequestId(li.getPurchaseRequest().getId());
		for (PurchaseRequestLineItem prli : prlis) {
			purchaseTotal = prli.getProduct().getPrice() * prli.getQuantity();
		}
		li.getPurchaseRequest().setTotal(purchaseTotal);
		purchaseRequestRepo.save(li.getPurchaseRequest());
	}
	
	@PutMapping("/")
	public JsonResponse update(@RequestBody PurchaseRequestLineItem li) {
		JsonResponse jr = null;
		try {
			if (purchaseRequestLineItemRepo.existsById(li.getId())) {
				jr = JsonResponse.getInstance(purchaseRequestLineItemRepo.save(li));
				recalculateTotal(li);
			} else {
				jr = JsonResponse.getInstance(
						"Purcase Request Line Item ID: " + li.getId() + "does not exist" + "and you are attempting "
								+ "to save it.");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	
	@DeleteMapping("/")
	public JsonResponse delete(@RequestBody PurchaseRequestLineItem li) {
		JsonResponse jr = null;
		try {
			if (purchaseRequestLineItemRepo.existsById(li.getId())) {
				purchaseRequestLineItemRepo.delete(li);
				recalculateTotal(li);
				jr = JsonResponse.getInstance("Purchase Request Line Item deleted.");
			} else {
				jr = JsonResponse.getInstance(
						"Purchase Request Line Item ID: " + li.getId() + "does not exist" + 
						"and you are attempting to delete it.");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
	

}
