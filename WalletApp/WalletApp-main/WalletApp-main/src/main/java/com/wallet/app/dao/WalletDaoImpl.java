package com.wallet.app.dao;

import java.util.HashMap;
import java.util.Map;

import com.wallet.app.dto.Wallet;
import com.wallet.app.exception.WalletException;

public class WalletDaoImpl implements WalletDao {

	// Create collection to store the Wallet information.
	public Map<Integer, Wallet> wallets = new HashMap<>();

	public Wallet addWallet(Wallet newWallet) throws WalletException {

		 this.wallets.put(newWallet.getId(), newWallet);
		 return this.wallets.get(newWallet.getId());

	}

	public Wallet getWalletById(Integer walletId) throws WalletException {
		// TODO Auto-generated method stub
		return wallets.get(walletId);
	}

	public Wallet updateWallet(Wallet updateWallet) throws WalletException {
		// TODO Auto-generated method stub
		Wallet updatingWallet = this.wallets.get(updateWallet.getId());
		updatingWallet.setBalance(updateWallet.getBalance());
		updatingWallet.setName(updateWallet.getName());
		updatingWallet.setPassword(updateWallet.getPassword());
		
		return updatingWallet;
	}

	public Wallet deleteWalletById(Integer walletID) throws WalletException {
		// TODO Auto-generated method stub
		if (this.wallets.keySet().contains(walletID)) {
			Wallet walletPresent = this.wallets.get(walletID);
			this.wallets.remove(walletID);
			return walletPresent;
		}
		return null;
	}

}
