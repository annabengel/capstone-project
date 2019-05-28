package com.prs.web;

import java.time.LocalDateTime;
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
import com.prs.business.PurchaseRequest;
import com.prs.business.User;
import com.prs.db.PurchaseRequestRepository;
import com.prs.db.UserRepository;

@RestController
@RequestMapping("/purchase-requests")
public class PurchaseRequestController {

	@Autowired
	private PurchaseRequestRepository purchaseRequestRepo;

	@GetMapping("/")
	public JsonResponse getAll() {
		JsonResponse jr = null;
		try {
			jr = JsonResponse.getInstance(purchaseRequestRepo.findAll());
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@GetMapping("/{id}")
	public JsonResponse get(@PathVariable int id) {
		JsonResponse jr = null;
		try {
			Optional<PurchaseRequest> pr = purchaseRequestRepo.findById(id);
			if (pr.isPresent())
				jr = JsonResponse.getInstance(pr);
			else
				jr = JsonResponse.getInstance("No purchase request found for id" + id);
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@GetMapping("/list-review")
	public JsonResponse getListReview(@RequestBody User u) {
		JsonResponse jr = null;
		try {
			Iterable<PurchaseRequest> pr = purchaseRequestRepo.findByStatusAndUserNot("Review", u);
			jr = JsonResponse.getInstance(pr);
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}



	@PostMapping("/submit-new")
	public JsonResponse setStatusNew(@RequestBody PurchaseRequest pr) {
		JsonResponse jr = null;
		try {
			pr.setStatus("New");
			pr.setSubmittedDate(LocalDateTime.now());
			jr = JsonResponse.getInstance(purchaseRequestRepo.save(pr));
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PutMapping("/submit-review")
	public JsonResponse setStatusReview(@RequestBody PurchaseRequest pr) {
		JsonResponse jr = null;
		try {
			if (pr.getTotal() <= 50.00) {
				pr.setStatus("Approved");
				pr.setSubmittedDate(LocalDateTime.now());
				jr = JsonResponse.getInstance(purchaseRequestRepo.save(pr));
			} else {
				pr.setStatus("Review");
				pr.setSubmittedDate(LocalDateTime.now());
				jr = JsonResponse.getInstance(purchaseRequestRepo.save(pr));
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PutMapping("/approve")
	public JsonResponse setStatusApproved(@RequestBody PurchaseRequest pr) {
		JsonResponse jr = null;
		try {
			if (purchaseRequestRepo.existsById(pr.getId())) {
				pr.setStatus("Approved");
				jr = JsonResponse.getInstance(purchaseRequestRepo.save(pr));
			} else {
				jr = JsonResponse.getInstance("Purchase Request ID: " + pr.getId() + "does not exist"
						+ "and you are attempting to approve it.");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PutMapping("/reject")
	public JsonResponse setStatusRejected(@RequestBody PurchaseRequest pr) {
		JsonResponse jr = null;
		try {
			if (purchaseRequestRepo.existsById(pr.getId())) {
				pr.setStatus("Rejected");
				// reason for rejection set in front end
				jr = JsonResponse.getInstance(purchaseRequestRepo.save(pr));
			} else {
				jr = JsonResponse.getInstance("Purchase Request ID: " + pr.getId() + "does not exist"
						+ "and you are attempting to reject it.");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@PutMapping("/")
	public JsonResponse update(@RequestBody PurchaseRequest pr) {
		JsonResponse jr = null;
		try {
			if (purchaseRequestRepo.existsById(pr.getId())) {
				jr = JsonResponse.getInstance(purchaseRequestRepo.save(pr));
			} else {
				jr = JsonResponse.getInstance(
						"Purcase Request ID: " + pr.getId() + "does not exist" + "and you are attempting to save it.");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}

	@DeleteMapping("/")
	public JsonResponse delete(@RequestBody PurchaseRequest pr) {
		JsonResponse jr = null;
		try {
			if (purchaseRequestRepo.existsById(pr.getId())) {
				purchaseRequestRepo.delete(pr);
				jr = JsonResponse.getInstance("Purchase Request deleted.");
			} else {
				jr = JsonResponse.getInstance("Purchase Request ID: " + pr.getId() + "does not exist"
						+ "and you are attempting to delete it.");
			}
		} catch (Exception e) {
			jr = JsonResponse.getInstance(e);
		}
		return jr;
	}
}
