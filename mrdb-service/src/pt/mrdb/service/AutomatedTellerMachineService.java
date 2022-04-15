package pt.mrdb.service;

import pt.mrbd.exception.FundException;
import pt.mrdb.model.Account;
import pt.mrdb.model.Card;

public interface AutomatedTellerMachineService {

	void withdraw(Account account, Double amount) throws FundException;

	void cashAdvance(Card cardActive, Double amount) throws FundException;

	void deposit(Account account, Double amount) throws FundException;

	void transference(Account owner, Account receiver, Double amount) throws FundException;

}