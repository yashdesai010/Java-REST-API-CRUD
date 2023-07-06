package com.example.demo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.customer;
import com.example.demo.repository.customerrepo;

@RestController
@RequestMapping("/customer")
public class customercontroller {
    @Autowired
    private customerrepo cr;

    @PostMapping("/add")
    public customer addnewcustomer(@RequestBody customer newcustom) {
        customer c = new customer();
        c.setName(newcustom.getName());
        c.setEmail(newcustom.getEmail());
        cr.save(c);
        return c;
    }

    @GetMapping("view/{id}")
    public Optional<customer> getcustomer(@PathVariable Integer Id) {
        return cr.findById(Id);
    }

    @PutMapping("/edit/{id}")
    public String update(@RequestBody customer updateCustomer, @PathVariable Integer id) {
        return cr.findById(id)
                .map(customer -> {
                    customer.setName(updateCustomer.getName());
                    customer.setEmail(updateCustomer.getEmail());
                    cr.save(customer);
                    return "Customer details have been successfully updated!";
                    
                }).orElseGet(() -> {
                    return "This customer doesn't exist";
                });
    }

    @GetMapping("view/all")
    public @ResponseBody Iterable<customer> getAllCustomers() {
        return cr.findAll();
    }

    @DeleteMapping("delete/{id}")
    public String delete(@PathVariable("id") Integer id) {
        cr.deleteById(id);
        return "Customer has been successfully deleted!";
    }
}
