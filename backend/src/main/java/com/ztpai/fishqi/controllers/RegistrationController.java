// package com.ztpai.fishqi.controllers;

// import com.ztpai.fishqi.entity.Customer;
// import com.ztpai.fishqi.services.CustomerService;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RestController;

// @RestController
// @RequestMapping("api/customer")
// public class RegistrationController {

//     @Autowired
//     private CustomerService customerService;

//     @PostMapping("/")
//     public String registerCustomer(@RequestBody Customer customer) {
//         customerService.registerCustomer(customer);
//         return "Customer registered!";
//     }
// }
