package com.accountingapp.service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.accountingapp.data.AccountingDataContext;
import com.accountingapp.model.BusinessPartner;
import com.accountingapp.model.CashBox;
import com.accountingapp.model.CashBoxTransferDTO;
import com.accountingapp.model.FinancialMovement;
import com.accountingapp.model.InventoryGroup;
import com.accountingapp.model.InventoryItem;
import com.accountingapp.model.RoleMenuMatch;
import com.accountingapp.model.SellBuyDTO;
import com.accountingapp.model.SellingOperation;
import com.accountingapp.model.StockMovements;
import com.accountingapp.model.User;
import com.accountingapp.model.UserMenu;
import com.accountingapp.model.UserMenuDTO;
import com.accountingapp.model.UserRoleDTO;
import com.accountingapp.model.UserRoles;

public class AccountingService {
	AccountingDataContext ac = null;

	public AccountingService() {
		ac = new AccountingDataContext();
	}

	public void closeService() {
		ac.closeConnection();
	}

	public List<UserRoles> getUserRoles() {
		return ac.getUserRoles();
	}

	public List<BusinessPartner> getBusinessPartner() {
		return ac.getBusinessPartnerList();
	}

	public int saveOrUpdateBusinessPartner(BusinessPartner bp) {
		if (bp.getId() == null) {
			return ac.saveBusinessPartner(bp);
		} else {
			return ac.updateBusinessPartner(bp);
		}
	}

	public int deleteBusinessPartner(int bpId) {
		return ac.deleteBusinessPartner(bpId);
	}

	public int saveOrUpdateUser(User user) {
		if (user.getId() == null) {
			return ac.saveUser(user);
		} else {
			return ac.updateUser(user);
		}
	}

	public int deleteUser(int userId) {
		return ac.deleteUser(userId);
	}

	public List<User> getAllUsers() {
		return ac.getAllUsers();
	}

	public User getUserWithUsermailAndPW(String userMail, String password) {
		return ac.getUserWithUsermailAndPW(userMail, password);
	}

	public User getUserWithId(int userId) {
		return ac.getUserWithId(userId);
	}

	public List<UserMenu> getUserMenuList(int roleId) {
		return ac.getUserMenuList(roleId);
	}

	public List<UserMenuDTO> getUserListWithRoles() {
		List<UserMenuDTO> list = ac.getUserMenusWithRoles();
		List<UserMenuDTO> groupedList = new ArrayList<UserMenuDTO>();
		List<UserMenu> userMenuList = ac.getAllUserMenu();
		HashMap<Integer, List<Integer>> hm = new HashMap<Integer, List<Integer>>();
		for (int i = 0; i < list.size(); i++) {
			if (hm.containsKey(list.get(i).getId())) {
				if (hm.get(list.get(i).getId()) == null) {
					List<Integer> roles = new ArrayList<Integer>();
					hm.put(list.get(i).getId(), roles);
				}
				hm.get(list.get(i).getId()).add(list.get(i).getRole());
			} else {
				List<Integer> roles = new ArrayList<Integer>();
				roles.add(list.get(i).getRole());
				hm.put(list.get(i).getId(), roles);
			}
		}
		Set set = hm.entrySet();
		Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			Map.Entry mentry = (Map.Entry) iterator.next();
			for (int i = 0; i < userMenuList.size(); i++) {
				if (Integer.valueOf("" + mentry.getKey()) == userMenuList.get(i).getId()) {
					UserMenuDTO userDTO = new UserMenuDTO();
					userDTO.setId(userMenuList.get(i).getId());
					userDTO.setMenuLabel(userMenuList.get(i).getMenuLabel());
					userDTO.setMenuSrc(userMenuList.get(i).getMenuSrc());
					userDTO.setRoles((List<Integer>) mentry.getValue());
					groupedList.add(userDTO);
				}
			}
		}
		return groupedList;
	}

	public int saveOrUpdateUserMenu(UserMenuDTO userMenuDTO) {
		UserMenu userMenu = new UserMenu();
		userMenu.setMenuLabel(userMenuDTO.getMenuLabel());
		userMenu.setMenuSrc(userMenuDTO.getMenuSrc());
		int result = 0;
		if (userMenuDTO.getId() == null) {
			int userMenuSaveResult = ac.saveUserMenu(userMenu);
			result = userMenuSaveResult;
			if (userMenuSaveResult > 0) {
				int savedUserMenuId = ac.getUserMenuId(userMenu.getMenuSrc());
				List<Integer> roleIdList = userMenuDTO.getRoles();
				for (Integer item : roleIdList) {
					RoleMenuMatch rmm = new RoleMenuMatch();
					rmm.setMenuId(savedUserMenuId);
					rmm.setRoleId(item);
					result = ac.saveRoleMenuMatch(rmm);
				}
			}
		} else {
			userMenu.setId(userMenuDTO.getId());
			int userMenuSaveResult = ac.updateUserMenu(userMenu);
			result = userMenuSaveResult;
			if (userMenuSaveResult > 0) {
				ac.deleteRoleMenuMatch(userMenuDTO.getId());
				List<Integer> roleIdList = userMenuDTO.getRoles();
				for (Integer item : roleIdList) {
					RoleMenuMatch rmm = new RoleMenuMatch();
					rmm.setMenuId(userMenuDTO.getId());
					rmm.setRoleId(item);
					result = ac.saveRoleMenuMatch(rmm);
				}
			}
		}
		return result;
	}

	public int deleteUserMenu(int menuId) {
		int result = 0;
		result = ac.deleteUserMenu(menuId);
		if (result > 0) {
			result = ac.deleteRoleMenuMatch(menuId);
		}
		return result;
	}

	public List<UserRoleDTO> getUserRolesWithRoleNames() {
		return ac.getUserRolesWithRoleNames();
	}

	public List<InventoryGroup> getInventoryGroups() {
		return ac.getInventoryGroups();
	}

	public int saveOrUpdateInventoryGroups(InventoryGroup ig) {
		int result = 0;
		if (ig.getId() == null) {
			result = ac.saveInventoryGroup(ig);
		} else {
			result = ac.updateInventoryGroup(ig);
		}
		return result;
	}

	public int deleteInventoryGroup(int id) {
		return ac.deleteInventoryGroup(id);
	}

	public List<InventoryItem> getInventoryItems() {
		return ac.getInventoryItems();
	}

	public int saveOrUpdateInventoryItem(InventoryItem ii) {
		if (ii.getItemId() == null) {
			return ac.saveInventoryItem(ii);
		} else {
			return ac.updateInventoryItem(ii);
		}
	}

	public int deleteInventoryItem(int id) {
		return ac.deleteInventoryItem(id);
	}

	public List<CashBox> getCashBoxes() {
		return ac.getCashBoxes();
	}

	public int saveOrUpdateCashBox(CashBox cb) {
		if (cb.getId() == null) {
			return ac.saveCashBox(cb);
		} else {
			return ac.updateCashBox(cb);
		}
	}

	public int deleteCashBox(int id) {
		CashBox cb = ac.getCashBoxWithId(id);
		int result = 0;
		if (cb.getCashBoxBalance() == 0) {
			result = ac.deleteCashBox(id);
		}
		return result;
	}

	public int transferCashBox(CashBoxTransferDTO cbt) {
		int result = 0;
		CashBox currentCashBox = ac.getCashBoxWithId(cbt.getCurrentBoxId());
		CashBox newCashBox = ac.getCashBoxWithId(cbt.getTransferedBoxId());
		if(cbt.getTransferBalance() > currentCashBox.getCashBoxBalance()) {
			result = 2;
		} else if(cbt.getCurrentBoxId() == cbt.getTransferedBoxId()){
			result = 3;
		} else {
			currentCashBox.setCashBoxBalance(currentCashBox.getCashBoxBalance() - cbt.getTransferBalance());
			newCashBox.setCashBoxBalance(newCashBox.getCashBoxBalance() + cbt.getTransferBalance());
			result = saveOrUpdateCashBox(currentCashBox);
			result = saveOrUpdateCashBox(newCashBox);
		}
		return result;
	}
	
	public int sellingOperation(SellingOperation op) {
		int operationType = 0;
		int result = 1;
		InventoryItem sellingItem = ac.getItemWithId(op.getItem());
		if(sellingItem.getItemQty() < op.getItemQty()) {
			result = 2;
			return result;
		}
		sellingItem.setItemQty(sellingItem.getItemQty() - op.getItemQty());
		int updateItemResult = ac.updateInventoryItem(sellingItem);
		if(updateItemResult != 1) {
			result = 3;
			return result;
		}
		
		double totalPrice = sellingItem.getItemPrice() * op.getItemQty();
		CashBox currentCashBox = ac.getCashBoxWithId(op.getCashBox());
		currentCashBox.setCashBoxBalance(currentCashBox.getCashBoxBalance() + totalPrice);
		int updateCashBoxResult = ac.updateCashBox(currentCashBox);
		if(updateCashBoxResult != 1) {
			result = 4;
			return result;
		}
		long now = new Date().getTime();
		FinancialMovement fm = new FinancialMovement();
		fm.setOperationCustomer(op.getBusinessPartner());
		fm.setOperationItem(sellingItem.getItemId());
		fm.setOperationQty(op.getItemQty());
		fm.setOperationTime(now);
		fm.setOperationType(operationType);
		fm.setItemUnitPrice(sellingItem.getItemPrice());
		int saveFinancialMoveResult = ac.saveFinancialMovement(fm);
		if(saveFinancialMoveResult != 1) {
			result = 5;
			return result;
		}
		return result;
	}
	
	public int buyingOperation(SellingOperation op) {
		int operationType = 1;
		int result = 1;
		InventoryItem buyingItem = ac.getItemWithId(op.getItem());
		buyingItem.setItemQty(buyingItem.getItemQty() + op.getItemQty());
		int updateItemResult = ac.updateInventoryItem(buyingItem);
		if(updateItemResult != 1) {
			result = 2;
			return result;
		}
		double totalPrice = op.getItemPrice() * op.getItemQty();
		CashBox currentCashBox = ac.getCashBoxWithId(op.getCashBox());
		if(currentCashBox.getCashBoxBalance() < totalPrice) {
			result = 3;
			return result;
		}
		currentCashBox.setCashBoxBalance(currentCashBox.getCashBoxBalance() - totalPrice);
		int updateCashBoxResult = ac.updateCashBox(currentCashBox);
		
		if(updateCashBoxResult != 1) {
			result = 4;
			return result;
		}
		long now = new Date().getTime();
		FinancialMovement fm = new FinancialMovement();
		fm.setOperationCustomer(op.getBusinessPartner());
		fm.setOperationItem(buyingItem.getItemId());
		fm.setOperationQty(op.getItemQty());
		fm.setOperationTime(now);
		fm.setOperationType(operationType);
		fm.setItemUnitPrice(op.getItemPrice());
		int saveFinancialMoveResult = ac.saveFinancialMovement(fm);
		if(saveFinancialMoveResult != 1) {
			result = 5;
			return result;
		}
		
		return result;
	}
	
	public SellBuyDTO getLastOperation(int operationType) {
		return ac.getLastOperation(operationType);
	}
	
	public List<SellBuyDTO> getOperations(int operationType){
		return ac.getOperations(operationType);
	}
	
	public List<StockMovements> getStockMovements(){
		return ac.getStockMovements();
	}
	
	public int saveOrUpdateUserRole(UserRoles userRole) {
		int result = 0;
		if(userRole.getId() == null) {
			result = ac.saveUserRole(userRole);
		} else {
			result = ac.updateUserRole(userRole);
		}
		
		return result;
	}
	
	public int updateUserRole(int userId, int userNewRole) {
		User currentUser = getUserWithId(userId);
		currentUser.setUserRole(userNewRole);
		return saveOrUpdateUser(currentUser);
	}
}
