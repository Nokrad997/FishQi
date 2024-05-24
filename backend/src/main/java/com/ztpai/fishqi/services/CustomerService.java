package com.ztpai.fishqi.services;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Service;

import com.ztpai.fishqi.DTO.CustomerDTO;
import com.ztpai.fishqi.DTO.UpdateCustomerDTO;
import com.ztpai.fishqi.entity.Customer;
import com.ztpai.fishqi.entity.Files;
import com.ztpai.fishqi.entity.FishQSet;
import com.ztpai.fishqi.entity.FishQa;
import com.ztpai.fishqi.entity.Rating;
import com.ztpai.fishqi.exceptions.UserAlreadyExistsException;
import com.ztpai.fishqi.exceptions.UserDoesntExistException;
import com.ztpai.fishqi.repositories.FilesRepository;
import com.ztpai.fishqi.repositories.FishQRepository;
import com.ztpai.fishqi.repositories.FishQSetRepository;
import com.ztpai.fishqi.repositories.RatingRepository;

import jakarta.transaction.Transactional;

@Service
public class CustomerService {

    private final CustomerSharedService customerSharedService;
    private final FilesRepository filesRepository;
    private final FishQRepository fishQRepository;
    private final FishQSetRepository fishQSetRepository;
    private final RatingRepository ratingRepository;

    public CustomerService(CustomerSharedService customerSharedService, FilesRepository filesRepository,
            FishQRepository fishQRepository, FishQSetRepository fishQSetRepository, RatingRepository ratingRepository) {
        this.customerSharedService = customerSharedService;
        this.filesRepository = filesRepository;
        this.fishQRepository = fishQRepository;
        this.fishQSetRepository = fishQSetRepository;
        this.ratingRepository = ratingRepository;
    }

    public CustomerDTO getCustomerByID(Long userId) {
        Optional<Customer> optCus = this.customerSharedService.getCustomerById(userId);
        Customer customer = optCus.orElseThrow();

        return customer.convertToDTO();
    }

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = this.customerSharedService.getAllCustomers();
        List<CustomerDTO> customerDTOs = customers.stream().map(Customer::convertToDTO).toList();

        return customerDTOs;
    }

    public CustomerDTO getCustomerByEmail(String email) throws UserDoesntExistException {
        Customer customer = this.customerSharedService.getCustomerByEmail(email);

        if (customer == null) {
            throw new UserDoesntExistException("User with that email does not exist");
        }

        return customer.convertToDTO();
    }

    public CustomerDTO updateCustomer(UpdateCustomerDTO requestCustomer, Long userId) {
        Optional<Customer> OptCus = this.customerSharedService.getCustomerById(userId);
        Customer customer = OptCus.orElseThrow();

        String email = requestCustomer.getEmail() == null ? customer.getEmail() : requestCustomer.getEmail();
        String username = requestCustomer.getUsername() == null ? customer.getUsername()
                : requestCustomer.getUsername();
        String password = requestCustomer.getPassword() == null ? customer.getPassword()
                : this.customerSharedService.encodePassword(requestCustomer.getPassword());
        Boolean is_admin = requestCustomer.getIs_admin() == null ? customer.getIs_admin()
                : requestCustomer.getIs_admin();

        customer.setEmail(email);
        customer.setUsername(username);
        customer.setIs_admin(is_admin);
        customer.setPassword(password);

        this.customerSharedService.saveCustomer(customer);

        return customer.convertToDTO();
    }

    public CustomerDTO saveCustomer(CustomerDTO requestCustomer) throws UserAlreadyExistsException {
        if (this.customerSharedService.emailExists(requestCustomer.getEmail())
                || this.customerSharedService.usernameExists(requestCustomer.getUsername())) {
            throw new UserAlreadyExistsException("User with that email/username already exists");
        }
        requestCustomer.setPassword(this.customerSharedService.encodePassword(requestCustomer.getPassword()));
        Customer savedCustomer = customerSharedService.saveCustomer(requestCustomer.convertToEntity());

        return savedCustomer.convertToDTO();
    }
    
    @Transactional
    public void deleteCustomer(Long userId) {

        Customer cust = this.customerSharedService.getCustomerById(userId).orElseThrow();

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

        List<Long> fileIdsToDelete = filesToDelete.stream()
                .map(Files::getFileId) 
                .collect(Collectors.toList());
        
        List<Long> imageIdsToDelete = imagesToDelete.stream()
                .map(Files::getFileId) 
                .collect(Collectors.toList());

        List<Rating> ratingsToDelete = this.ratingRepository.findAllByFishQSetIn(userSets);

        Set<Long> allFilesToDelete = new HashSet<>();
        allFilesToDelete.addAll(fileIdsToDelete);
        allFilesToDelete.addAll(imageIdsToDelete);

        this.ratingRepository.deleteAll(ratingsToDelete);
        this.fishQRepository.deleteAll(userFishQs);
        this.fishQSetRepository.deleteAll(userSets);
        this.filesRepository.deleteAllByIdIn(allFilesToDelete);

        this.customerSharedService.deleteCustomer(cust.getUserId());
    }

    public boolean checkIfAdmin(String email) {
        Customer customer = this.customerSharedService.getCustomerByEmail(email);

        return customer.getIs_admin();
    }
}
