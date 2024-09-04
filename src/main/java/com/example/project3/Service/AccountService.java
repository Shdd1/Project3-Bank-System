package com.example.project3.Service;

import com.example.project3.Api.ApiException;
import com.example.project3.Model.Account;
import com.example.project3.Model.Customer;
import com.example.project3.Model.User;
import com.example.project3.Repository.AccountRepository;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AccountService {
    private final AccountRepository accountRepository;
    private final AuthRepository authRepository;
    private final CustomerRepository customerRepository;

    public List<Account> getAllAccount() {
        return accountRepository.findAll();
    }

    public List<Account> getMyAccount(Integer id) {
        Customer customer = customerRepository.findCustomerById(id);
        return accountRepository.findAllByCustomer(customer);
    }

    public void addAccount(Integer customer_id, Account account) {
        Customer customer = customerRepository.findCustomerById(customer_id);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        account.setCustomer(customer);
        accountRepository.save(account);
    }

    public void updateAccount(Integer customer_id, Integer account_id, Account account) {
        Customer customer = customerRepository.findCustomerById(customer_id);
        Account oldAccount = accountRepository.findAccountById(account_id);
        if (oldAccount == null) {
            throw new ApiException("Account not found");
        }
        if (customer == null) {
            throw new ApiException("customer not found");
        } else if (oldAccount.getCustomer().getId() != customer_id) {
            throw new ApiException("sorry you dont have authority");
        }
        oldAccount.setBalance(account.getBalance());
        oldAccount.setCustomer(customer);
        accountRepository.save(oldAccount);
    }

    //3. Active a bank account
    public void activateAccount(Integer customerId, Integer account_id) {
        Account account = accountRepository.findAccountById(account_id);
        Customer customer = customerRepository.findCustomerById(customerId);
        if (account == null) {
            throw new ApiException("Account not found");
        }
        if (customer == null) {
            throw new ApiException("customer not found");
        } else if (account.getCustomer().getId() != customerId) {
            throw new ApiException("sorry you dont have authority");
        }
        account.setIsActive(true);
        accountRepository.save(account);


    }

    public void deleteAccount(Integer customer_id, Integer account_id) {
        Customer customer = customerRepository.findCustomerById(customer_id);
        if (customer == null) {
            throw new ApiException("Customer not found");
        }
        Account oldAccount = accountRepository.findAccountById(account_id);
        if (oldAccount == null) {
            throw new ApiException("Account not found");
        } else if (oldAccount.getCustomer().getId() != customer_id) {
            throw new ApiException("Customer id mismatch, u don't have authority");
        }

        accountRepository.delete(oldAccount);

    }
    //4. View account details
    public Account viewAccountDetails(Integer customer_id,Integer account_id) {
        Account account = accountRepository.findAccountById(account_id);
        Customer customer=customerRepository.findCustomerById(customer_id);
        if (account == null) {
            throw new ApiException("Account not found");
        }
        if (customer == null) {
            throw new ApiException("customer not found");
        }
        if(account.getCustomer().getId()!=customer_id){
            throw new ApiException("Customer id mismatch, u don't have authority");
        }

        return account;

    }

    //6.Deposit and withdraw money
    public Account depositMoney(Integer customer_id,Integer account_id,double amount) {
        Account account = accountRepository.findAccountById(account_id);
        if (account == null) {
            throw new ApiException("Account not found");
        }

        if(account.getIsActive()!=true){
          throw new ApiException("Account must be Active");
        }

         if(account.getCustomer().getId()!= customer_id){
            throw new ApiException("Customer id mismatch, u don't have authority");
        }
        account.setBalance(account.getBalance() + amount);
        return accountRepository.save(account);
    }

//6.
    public Account withdrawMoney( Integer customer_id,Integer account_id,double amount) {
        Account account = accountRepository.findAccountById(account_id);
        if (account == null) {
            throw new ApiException("Account not found");
        }
        if(!account.getIsActive().equals(true)){
            throw new ApiException("Account must be Active");
        }
         if(account.getCustomer().getId()!= customer_id){
            throw new ApiException("Customer id mismatch, u don't have authority");
        }
        if (account.getBalance() < amount) {
            throw new ApiException("Insufficient funds");
        }
        account.setBalance(account.getBalance() - amount);
        return accountRepository.save(account);
    }


    //7.Transfer funds between accounts
    public void transferFunds(Integer customer_id,Integer fromAccount_id, Integer toAccount_id,double amount) {
        Account fromAccount = accountRepository.findAccountById(fromAccount_id);
        Account toAccount = accountRepository.findAccountById(toAccount_id);
        if (fromAccount == null || toAccount == null) {
            throw new ApiException("Account not found");
        }
        if (fromAccount.getCustomer().getId() != customer_id) {
            throw new ApiException("Customer id mismatch, u don't have authority");
        }
        if(!fromAccount.getIsActive().equals(true)||!toAccount.getIsActive().equals(true)){
            throw new ApiException("Account must be Active");
        }

        if (fromAccount.getBalance() < amount) {
            throw new ApiException("Insufficient funds");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);
    }

    //8. Block bank account
    public void blockAccount( Integer customer_id,Integer account_id) {
        Account account= accountRepository.findAccountById(account_id);
        if (account==null){
            throw new ApiException("Account not found");
        }
        if(account.getIsActive().equals(false)) {
            throw new ApiException("account is blocked already");
        }
        account.setIsActive(false);
        accountRepository.save(account);
    }


}