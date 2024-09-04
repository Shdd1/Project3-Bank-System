package com.example.project3.Controller;

import com.example.project3.Api.ApiException;
import com.example.project3.ApiResponse.ApiResponse;
import com.example.project3.Model.Account;
import com.example.project3.Model.User;
import com.example.project3.Repository.AccountRepository;
import com.example.project3.Repository.AuthRepository;
import com.example.project3.Service.AccountService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@RequiredArgsConstructor
public class AccountController {
    private final AccountService accountService;
//5. List user's accounts
    @GetMapping("/get-my")
    public ResponseEntity getMyAccount(@AuthenticationPrincipal User user){
        return ResponseEntity.status(200).body(accountService.getMyAccount(user.getId()));
    }
    @GetMapping("/get-all")
    public ResponseEntity getAll(){
        return ResponseEntity.status(200).body(accountService.getAllAccount());
    }

   //2. Create a new bank account
    @PostMapping("/add")
    public ResponseEntity addAccount(@AuthenticationPrincipal User user, @RequestBody @Valid Account account){
        accountService.addAccount(user.getId(),account);
        return ResponseEntity.status(200).body(new ApiResponse("Account Added ,"+user.getUsername()));
    }
    //update
    @PutMapping("/update/{account_id}")
    public ResponseEntity updateAccount(@AuthenticationPrincipal User user, @PathVariable Integer account_id , @RequestBody @Valid Account account){
        accountService.updateAccount(user.getId(),account_id,account);
        return ResponseEntity.status(200).body(new ApiResponse("Account updated ,"+user.getUsername()));
    }
    //delete
    @DeleteMapping("/delete/{account_id}")
    public ResponseEntity deleteAccount(@AuthenticationPrincipal User user,@PathVariable Integer account_id){
        accountService.deleteAccount(user.getId(),account_id);
        return ResponseEntity.status(200).body(new ApiResponse("Account deleted ,"+user.getUsername()));
    }

    //3. Active a bank account
    @PutMapping("/activ/{account_id}")
    public ResponseEntity active(@AuthenticationPrincipal User user,@PathVariable Integer account_id ){
        accountService.activateAccount(user.getId(),account_id);
        return ResponseEntity.status(200).body(new ApiResponse("is Activated"));
    }
   //4. View account details
    @GetMapping("/details/{account_id}")
    public ResponseEntity viewAccountDetails(@AuthenticationPrincipal User user ,@PathVariable Integer account_id) {
        return ResponseEntity.ok(accountService.viewAccountDetails(user.getId(),account_id));
    }

   // 6. Deposit money
   @PutMapping("/deposit/{account_id}/{amount}")
   public ResponseEntity depositMoney (@AuthenticationPrincipal User user, @PathVariable Integer account_id , @PathVariable double amount ){
       accountService.depositMoney(user.getId(),account_id,amount);
       return ResponseEntity.status(200).body(new ApiResponse("Deposit money successfully"));
   }

//6.withdraw
@PutMapping("/withdraw/{account_id}/{amount}")
public ResponseEntity withdrawMoney (@AuthenticationPrincipal User user, @PathVariable Integer account_id , @PathVariable double amount ){
    accountService.withdrawMoney(user.getId(),account_id,amount);
    return ResponseEntity.status(200).body(new ApiResponse("Withdraw money successfully"));
}
    //7. Transfer funds between accounts
    @PutMapping("/transferFunds/{fromAccount_id}/{toAccount_id}/{amount}")
    public ResponseEntity transferFunds (@AuthenticationPrincipal User user , @PathVariable Integer fromAccount_id, @PathVariable Integer toAccount_id, @PathVariable double amount) {
        accountService.transferFunds(user.getId(), fromAccount_id, toAccount_id, amount);
        return ResponseEntity.status(200).body(new ApiResponse("Transfer funds successfully"));

    }
     //8. Block bank account
        @PutMapping("/block-account/{account_id}")
        public ResponseEntity blockAccount(@AuthenticationPrincipal User user, @PathVariable Integer account_id){
            accountService.blockAccount(user.getId(),account_id);
            return ResponseEntity.status(200).body(new ApiResponse("Account blocked Successfully"));
        }


}
