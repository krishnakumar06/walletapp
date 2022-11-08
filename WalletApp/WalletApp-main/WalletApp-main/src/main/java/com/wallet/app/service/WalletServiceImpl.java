package com.wallet.app.service;

import com.wallet.app.dao.WalletDao;
import com.wallet.app.dao.WalletDaoImpl;
import com.wallet.app.dto.Wallet;
import com.wallet.app.exception.WalletException;
import com.wallet.app.exception.WalletRepositoryException;

public class WalletServiceImpl implements WalletService {

	private WalletDao walletRepository = new WalletDaoImpl();
	
	
	public Wallet registerWallet(Wallet newWallet) throws WalletException {
		if (this.walletRepository.getWalletById(newWallet.getId()) != null) {

			throw new WalletException ("The given wallet is already registered ");
		}
		
		return this.walletRepository.addWallet(newWallet);
		
	}

	public Boolean login(Integer walletId, String password) throws WalletException {
		// TODO Auto-generated method stub
		Wallet checkWallet = this.walletRepository.getWalletById(walletId);
		if(checkWallet ==null) {
			throw new WalletException("Wallet Id doesn't exsist , id"+walletId);
				
		}
		if(!checkWallet.getPassword().equals(password)) {
			throw new WalletException("incorrect password provided for login");
		}
		
		if(checkWallet.getPassword()==password)
			return true;
		else
		return false;
		
	}

	public Double addFundsToWallet(Integer walletId, Double amount) throws WalletException {
		// TODO Auto-generated method stub
		if(this.walletRepository.getWalletById(walletId)==null) {
			throw new WalletException("Provided Wallet id doesnot exsist for adding funds"+walletId);
		}
		Wallet wallet1=this.walletRepository.getWalletById(walletId);
		wallet1.setBalance(amount+wallet1.getBalance());
		this.walletRepository.updateWallet(wallet1);
		return wallet1.getBalance();
	}

	public Double showWalletBalance(Integer walletId) throws WalletException {
		// TODO Auto-generated method stub
		Wallet checkWallet = this.walletRepository.getWalletById(walletId);
		
		if(checkWallet==null) {
			return this.walletRepository.getWalletById(walletId).getBalance();
		}
		else {
		throw new WalletException("Wallet id for balance checking does not exist");}}

	public Boolean fundTransfer(Integer fromId, Integer toId, Double amount) throws WalletException {
		// TODO Auto-generated method stub
		Wallet fromWallet = this.walletRepository.getWalletById(fromId);
		Wallet toWallet = this.walletRepository.getWalletById(toId);
		
		
		if(fromWallet == null) {
			throw new WalletException("Wallet id for fund transfer does not exist");
		}
		if (toWallet == null) {
			throw new WalletException("Wallet id of reciver for fund transfer does not exist");
		}
		if (fromWallet.getBalance()<amount) {
			throw new WalletException("Currently the balance is low for fund transfer,Balance"+fromWallet.getBalance());
		}
		Double fromWalletOldBalance = fromWallet.getBalance();
		Double toWalletOldBalance = toWallet.getBalance();
		fromWallet.setBalance(fromWallet.getBalance()- amount);
		toWallet.setBalance(toWallet.getBalance() + amount);
		this.walletRepository.updateWallet(fromWallet);
		this.walletRepository.updateWallet(toWallet);
		return true;
	}

	public Wallet unRegisterWallet(Integer walletId, String password) throws WalletException, WalletRepositoryException {
		// TODO Auto-generated method stub
		Wallet unregWallet = this.walletRepository.getWalletById(walletId);
		if(unregWallet==null) {
			throw new WalletException("Wallet id provided to unregister does not exist");
		}
		if(!unregWallet.getPassword().equals(password)) {
			System.out.println(password);
			System.out.println(unregWallet.getPassword());
			throw new WalletException("Password profived for unregistrartion is invalid ,please provide the correct password");
			
		}
			return this.walletRepository.deleteWalletById(walletId);
		}


	public Double withdrawFunds(Integer WalletID, Double amount) throws WalletException {
		Wallet withdrawWallet = this.walletRepository.getWalletById(WalletID);
		if (amount == 0.0) {
			throw new WalletException("The minimum value of withdraw is 1 rs");
			
		}
		if (withdrawWallet== null) {
			
			throw new WalletException("Provide wallet id is not valid");
			
		}
		if (withdrawWallet.getBalance()< amount) {
			throw new WalletException("Entered amount id greater that your current balance,which is : "+ withdrawWallet.getBalance());
			
		}
		withdrawWallet.setBalance(withdrawWallet.getBalance()-amount);
		this.walletRepository.updateWallet(withdrawWallet);
		return withdrawWallet.getBalance();
	}
	

}