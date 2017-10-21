package spring.controller;

import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dao.SellerDao;
import model.Seller;
import spring.dao.CustomerDAO;
import spring.model.Customer;

@RestController
public class CustomerRestController {
	
	@Autowired
	private CustomerDAO customerDAO;

	
//	@GetMapping("/customers")
//	public List<Customer> getCustomers() {
//		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/myapi-context.xml");
//	    SellerDao sellerDao = (SellerDao) ctx.getBean("sellerDao");
//		
//		return customerDAO.list();
//	}
	
	@GetMapping("/sellers")
	public List<Seller> getSellers() throws SQLException {
		// Maybe we could use autowired here
		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("spring/myapi-context.xml");
	    SellerDao sellerDao = (SellerDao) ctx.getBean("sellerDao");
	    
	    return sellerDao.getAllSeller();
	}

	@GetMapping("/customers/{id}")
	public ResponseEntity getCustomer(@PathVariable("id") Long id) {

		Customer customer = customerDAO.get(id);
		if (customer == null) {
			return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(customer, HttpStatus.OK);
	}

	@PostMapping(value = "/customers")
	public ResponseEntity createCustomer(@RequestBody Customer customer) {

		customerDAO.create(customer);

		return new ResponseEntity(customer, HttpStatus.OK);
	}

	@DeleteMapping("/customers/{id}")
	public ResponseEntity deleteCustomer(@PathVariable Long id) {

		if (null == customerDAO.delete(id)) {
			return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(id, HttpStatus.OK);

	}

	@PutMapping("/customers/{id}")
	public ResponseEntity updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {

		customer = customerDAO.update(id, customer);

		if (null == customer) {
			return new ResponseEntity("No Customer found for ID " + id, HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity(customer, HttpStatus.OK);
	}

}
