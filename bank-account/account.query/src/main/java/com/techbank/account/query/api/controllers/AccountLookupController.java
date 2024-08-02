package com.techbank.account.query.api.controllers;

import com.techbank.account.query.api.dto.AccountLookupResponse;
import com.techbank.account.query.api.queries.FindAccountByHolderQuery;
import com.techbank.account.query.api.queries.FindAccountByIdQuery;
import com.techbank.account.query.api.queries.FindAllAccountQuery;
import com.techbank.account.query.domain.BankAccount;
import com.techbank.cqrs.core.infrastructure.QueryDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.stylesheets.LinkStyle;

import java.text.MessageFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@RestController
@RequestMapping(path = "/api/v1/bankAccountLookup")
public class AccountLookupController {
    private final Logger logger = Logger.getLogger(AccountLookupController.class.getName());

    @Autowired
    private QueryDispatcher queryDispatcher;

    @GetMapping(path = "/")
    public ResponseEntity<AccountLookupResponse> getAllAccounts(){
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAllAccountQuery());
            if(accounts == null || accounts.isEmpty()){
                return new ResponseEntity<>(new AccountLookupResponse("No accounts found"), HttpStatus.NO_CONTENT);
            }
            var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Successfully returned {0} accounts", accounts.size()))
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e){
            var safeErrorMessage = "Failed to complete get all accounts request";
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byID/{id}")
    public ResponseEntity<AccountLookupResponse> getAccountByID(@PathVariable(value = "id") String id) {
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByIdQuery(id));
            if(accounts == null || accounts.isEmpty()){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Successfully returned account with ID {0}", id))
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch( Exception e){
            var safeErrorMessage = MessageFormat.format("Failed to complete get account by ID request for ID {0}", id);
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(path = "/byHolder/{accountHolder}")
    public ResponseEntity<AccountLookupResponse> getAccountByHolder(@PathVariable(value = "accountHolder") String accountHolder) {
        try{
            List<BankAccount> accounts = queryDispatcher.send(new FindAccountByHolderQuery(accountHolder));
            if(accounts == null || accounts.isEmpty()){
                return new ResponseEntity<>(null, HttpStatus.NO_CONTENT);
            }
            var response = AccountLookupResponse.builder()
                    .accounts(accounts)
                    .message(MessageFormat.format("Successfully returned account with holder {0}", accountHolder))
                    .build();
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch( Exception e){
            var safeErrorMessage = MessageFormat.format("Failed to complete get account by holder request for holder {0}", accountHolder);
            logger.log(Level.SEVERE, safeErrorMessage, e);
            return new ResponseEntity<>(new AccountLookupResponse(safeErrorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
