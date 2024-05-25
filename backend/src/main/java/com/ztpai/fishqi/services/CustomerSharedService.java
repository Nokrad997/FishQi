package com.ztpai.fishqi.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ztpai.fishqi.entity.Customer;
import com.ztpai.fishqi.entity.Files;
import com.ztpai.fishqi.entity.FishQSet;
import com.ztpai.fishqi.entity.FishQa;
import com.ztpai.fishqi.entity.Rating;
import com.ztpai.fishqi.repositories.CustomerRepository;
import com.ztpai.fishqi.repositories.FilesRepository;
import com.ztpai.fishqi.repositories.FishQRepository;
import com.ztpai.fishqi.repositories.FishQSetRepository;
import com.ztpai.fishqi.repositories.RatingRepository;

import io.jsonwebtoken.io.IOException;
import jakarta.transaction.Transactional;

@Service
public class CustomerSharedService {
    private final CustomerRepository customerRepository;
    private PasswordEncoder passwordEncoder;
    private final FTPUploader ftpUploader;
    private final FilesRepository filesRepository;
    private final FishQRepository fishQRepository;
    private final FishQSetRepository fishQSetRepository;
    private final RatingRepository ratingRepository;

    public CustomerSharedService(CustomerRepository customerRepository, PasswordEncoder passwordEncoder,
            FTPUploader ftpUploader, FilesRepository filesRepository, FishQRepository fishQRepository,
            FishQSetRepository fishQSetRepository, RatingRepository ratingRepository) {
        this.customerRepository = customerRepository;
        this.passwordEncoder = passwordEncoder;
        this.ftpUploader = ftpUploader;
        this.filesRepository = filesRepository;
        this.fishQRepository = fishQRepository;
        this.fishQSetRepository = fishQSetRepository;
        this.ratingRepository = ratingRepository;
    }

    public Optional<Customer> getCustomerById(Long id) {
        return this.customerRepository.findById(id);
    }

    public List<Customer> getAllCustomers() {
        return this.customerRepository.findAll();
    }

    public Customer getCustomerByEmail(String email) {
        try {
            return this.customerRepository.findByEmail(email);
        } catch (Exception e) {
            return new Customer();
        }
    }

    public boolean emailExists(String email) {
        Customer customer = this.customerRepository.findByEmail(email);
        return customer != null && customer.getEmail() != null;
    }

    public boolean usernameExists(String username) {
        Customer customer = this.customerRepository.findByUsername(username);
        return customer != null && customer.getEmail() != null;
    }

    public Customer saveCustomer(Customer customer) {
        return this.customerRepository.save(customer);
    }

    public void deleteCustomer(Long id) {
        this.customerRepository.deleteById(id);
    }

    public String encodePassword(String password) {
        return this.passwordEncoder.encode(password);
    }

    public Boolean decodePassword(String password, String email) {
        Customer customer = this.customerRepository.findByEmail(email);

        if (customer == null || customer.getEmail() == null) {
            return false;
        }

        return this.passwordEncoder.matches(password, customer.getPassword());
    }

    @Transactional
    public void deleteCustomerThings(Customer cust) {
        if (cust == null) {
            return;
        }

        List<FishQSet> userSets = this.fishQSetRepository.findFishQSetByOwner(cust);

        List<Files> imagesToDelete = userSets.stream()
                .map(FishQSet::getImage)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<FishQa> userFishQs = this.fishQRepository.findFishQaBySetIn(userSets);
        List<Files> filesToDelete = userFishQs.stream()
                .map(FishQa::getFile)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        List<Rating> ratingsToDelete = this.ratingRepository.findAllByCustomer(cust);
        
        this.ratingRepository.deleteAll(ratingsToDelete);
        this.fishQRepository.deleteAll(userFishQs);
        this.fishQSetRepository.deleteAll(userSets);
        this.filesRepository.deleteByFtpPathIn(filesToDelete);
        this.filesRepository.deleteByFtpPathIn(imagesToDelete);

        this.ratingRepository.flush();
        this.fishQRepository.flush();
        this.fishQSetRepository.flush();
        this.filesRepository.flush();

        this.ftpUploader.deleteUserCatalog(cust.getUserId());
    }

}
