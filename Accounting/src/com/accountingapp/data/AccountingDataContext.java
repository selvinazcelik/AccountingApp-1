package com.accountingapp.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;


import com.accountingapp.Global;
import com.accountingapp.model.BusinessPartner;
import com.accountingapp.model.CashBox;
import com.accountingapp.model.FinancialMovement;
import com.accountingapp.model.InventoryGroup;
import com.accountingapp.model.InventoryItem;
import com.accountingapp.model.RoleMenuMatch;
import com.accountingapp.model.SellBuyDTO;
import com.accountingapp.model.StockMovements;
import com.accountingapp.model.User;
import com.accountingapp.model.UserMenu;
import com.accountingapp.model.UserMenuDTO;
import com.accountingapp.model.UserRoleDTO;
import com.accountingapp.model.UserRoles;
import com.accountingapp.utils.Utils;

public class AccountingDataContext {
	private Connection conn = null;
	private PreparedStatement ps = null;
	
	public AccountingDataContext() {
		conn = Global.getConnection();
	}
	
	public void closeConnection() {
		try {
			if(ps != null)
			ps.close();
			if(conn != null)
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<UserRoles> getUserRoles(){
		List<UserRoles> list = new ArrayList<UserRoles>();
		String query = "select * from userroles";
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				UserRoles ur = new UserRoles();
				ur.setId(rs.getInt("id"));
				ur.setRoleName(rs.getString("rolename"));
				ur.setRoleCode(rs.getInt("rolecode"));
				list.add(ur);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<BusinessPartner> getBusinessPartnerList(){
		List<BusinessPartner> list = new ArrayList<BusinessPartner>();
		String query = "select * from businesspartners";
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				BusinessPartner bp = new BusinessPartner();
				bp.setPartnerName(rs.getString("partnername"));
				bp.setAddress(rs.getString("address"));
				bp.setEmail(rs.getString("email"));
				bp.setId(rs.getInt("id"));
				bp.setTelno(rs.getString("telno"));
				bp.setNote(rs.getString("note"));
				list.add(bp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int saveBusinessPartner(BusinessPartner bp) {
		String query = "insert into businesspartners(id,partnername,telno,email,address,note) values(null,?,?,?,?,?)";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, bp.getPartnerName());
			ps.setString(2, bp.getTelno());
			ps.setString(3, bp.getEmail());
			ps.setString(4, bp.getAddress());
			ps.setString(5, bp.getNote());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateBusinessPartner(BusinessPartner bp) {
		String query = "update businesspartners set partnername=?,telno=?,email=?,address=?,note=? where id=?";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, bp.getPartnerName());
			ps.setString(2, bp.getTelno());
			ps.setString(3, bp.getEmail());
			ps.setString(4, bp.getAddress());
			ps.setString(5, bp.getNote());
			ps.setInt(6, bp.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	public int deleteBusinessPartner(int bpId) {
		String query = "delete from businesspartners where id=?";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, bpId);
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int saveUser(User user) {
		String query = "insert into users(id,username,usersurname,usermail,userpassword,userrole) values(null,?,?,?,?,?)";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getUserSurname());
			ps.setString(3, user.getUserMail());
			ps.setString(4, user.getUserPassword());
			ps.setInt(5, user.getUserRole());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateUser(User user) {
		String query = "update users set username=?,usersurname=?,usermail=?,userpassword=?,userrole=? where id=?";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, user.getUserName());
			ps.setString(2, user.getUserSurname());
			ps.setString(3, user.getUserMail());
			ps.setString(4, user.getUserPassword());
			ps.setInt(5, user.getUserRole());
			ps.setInt(6, user.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteUser(int userId) {
		String query = "delete from users where id=?";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, userId);
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<User> getAllUsers(){
		String query = "select * from users";
		List<User> list = new ArrayList<User>();
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setUserName(rs.getString("username"));
				user.setUserSurname(rs.getString("usersurname"));
				user.setUserMail(rs.getString("usermail"));
				user.setUserPassword(rs.getString("userpassword"));
				user.setUserRole(rs.getInt("userrole"));
				list.add(user);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public User getUserWithUsermailAndPW(String userMail, String password) {
		String query = "select * from users where usermail=? and userpassword=?";
		User user = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, userMail);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUserName(rs.getString("username"));
				user.setUserSurname(rs.getString("usersurname"));
				user.setUserMail(rs.getString("usermail"));
				user.setUserPassword(rs.getString("userpassword"));
				user.setUserRole(rs.getInt("userrole"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public User getUserWithId(int userId) {
		String query = "select * from users where id=?";
		User user = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, userId);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setUserName(rs.getString("username"));
				user.setUserSurname(rs.getString("usersurname"));
				user.setUserMail(rs.getString("usermail"));
				user.setUserPassword(rs.getString("userpassword"));
				user.setUserRole(rs.getInt("userrole"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public List<UserMenu> getUserMenuList(int roleId){
		List<UserMenu> list = new ArrayList<UserMenu>();
		String query = "select * from rolemenumatch inner join usermenu on rolemenumatch.menuid = usermenu.id where rolemenumatch.roleid=?";
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, roleId);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				UserMenu userMenu = new UserMenu();
				userMenu.setId(rs.getInt("usermenu.id"));
				userMenu.setMenuLabel(rs.getString("menulabel"));
				userMenu.setMenuSrc(rs.getString("menusrc"));
				list.add(userMenu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<UserMenuDTO> getUserMenusWithRoles() {
		String query = "select * from usermenu inner join rolemenumatch on usermenu.id = rolemenumatch.menuid order by usermenu.id asc";
		List<UserMenuDTO> list = new ArrayList<UserMenuDTO>();
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				UserMenuDTO userDTO = new UserMenuDTO();
				userDTO.setId(rs.getInt("usermenu.id"));
				userDTO.setMenuLabel(rs.getString("usermenu.menulabel"));
				userDTO.setMenuSrc(rs.getString("usermenu.menusrc"));
				userDTO.setRole(rs.getInt("rolemenumatch.roleid"));
				list.add(userDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<UserMenu> getAllUserMenu(){
		String query = "select * from usermenu";
		List<UserMenu> list = new ArrayList<UserMenu>();
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				UserMenu userMenu = new UserMenu();
				userMenu.setId(rs.getInt("id"));
				userMenu.setMenuLabel(rs.getString("menulabel"));
				userMenu.setMenuSrc(rs.getString("menusrc"));
				list.add(userMenu);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int saveUserMenu(UserMenu userMenu) {
		String query = "insert into usermenu(id,menulabel,menusrc) values(null,?,?)";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, userMenu.getMenuLabel());
			ps.setString(2, userMenu.getMenuSrc());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateUserMenu(UserMenu userMenu) {
		String query = "update usermenu set menulabel=?,menusrc=? where id=?";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, userMenu.getMenuLabel());
			ps.setString(2, userMenu.getMenuSrc());
			ps.setInt(3, userMenu.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int saveRoleMenuMatch(RoleMenuMatch roleMenuMatch) {
		String query = "insert into rolemenumatch(id,menuid,roleid) values(null,?,?)";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, roleMenuMatch.getMenuId());
			ps.setInt(2, roleMenuMatch.getRoleId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateRoleMenuMatch(RoleMenuMatch roleMenuMatch) {
		String query = "update rolemenumatch set menuid=?,roleid=? where id=?";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, roleMenuMatch.getMenuId());
			ps.setInt(2, roleMenuMatch.getRoleId());
			ps.setInt(3, roleMenuMatch.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int getUserMenuId(String userMenuSrc) {
		String query = "select * from usermenu where menusrc=?";
		int id = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, userMenuSrc);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				id = rs.getInt("id");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return id;
	}
	
	public int deleteAllMatches(int menuId, List<Integer> roleIds) {
		String query = "delete from rolemenumatch where menuid=? and roleid not in (";
		for(int i = 0 ; i < roleIds.size() ; i++) {
			query += roleIds.get(i);
			if(i != roleIds.size() - 1) {
				query += ",";
			}
		}
		query += ")";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, menuId);
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteUserMenu(int menuId) {
		String query = "delete from usermenu where id=?";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, menuId);
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteRoleMenuMatch(int menuId) {
		String query = "delete from rolemenumatch where menuid=?";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, menuId);
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<UserRoleDTO> getUserRolesWithRoleNames(){
		String query = "select * from users inner join userroles on users.userrole = userroles.rolecode";
		List<UserRoleDTO> list = new ArrayList<UserRoleDTO>();
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				UserRoleDTO userRoleDTO = new UserRoleDTO();
				userRoleDTO.setId(rs.getInt("users.id"));
				userRoleDTO.setUserRole(rs.getInt("users.userrole"));
				userRoleDTO.setRoleName(rs.getString("userroles.rolename"));
				userRoleDTO.setUserName(rs.getString("users.username"));
				userRoleDTO.setUserSurname(rs.getString("users.usersurname"));
				userRoleDTO.setUserMail(rs.getString("users.usermail"));
				userRoleDTO.setUserPassword(rs.getString("users.userpassword"));
				list.add(userRoleDTO);
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<InventoryGroup> getInventoryGroups(){
		String query = "select * from inventorygroups";
		List<InventoryGroup> list = new ArrayList<InventoryGroup>();
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				InventoryGroup ig = new InventoryGroup();
				ig.setId(rs.getInt("id"));
				ig.setGroupName(rs.getString("groupname"));
				ig.setGroupCode(rs.getString("groupcode"));
				list.add(ig);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int saveInventoryGroup(InventoryGroup ig) {
		String query = "insert into inventorygroups(id,groupname,groupcode) values(null,?,?)";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, ig.getGroupName());
			ps.setString(2, ig.getGroupCode());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateInventoryGroup(InventoryGroup ig) {
		String query = "update inventorygroups set groupname=?,groupcode=? where id=?";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, ig.getGroupName());
			ps.setString(2, ig.getGroupCode());
			ps.setInt(3, ig.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteInventoryGroup(int id) {
		String query = "delete from inventorygroups where id=?";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public List<InventoryItem> getInventoryItems(){
		String query = "select * from inventorylist inner join inventorygroups on inventorylist.itemgroup = inventorygroups.id";
		List<InventoryItem> list = new ArrayList<InventoryItem>();
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				InventoryItem ii = new InventoryItem();
				ii.setId(rs.getInt("inventorygroups.id"));
				ii.setGroupCode(rs.getString("inventorygroups.groupcode"));
				ii.setGroupName(rs.getString("inventorygroups.groupname"));
				ii.setItemName(rs.getString("inventorylist.itemname"));
				ii.setItemCode(rs.getString("inventorylist.itemcode"));
				ii.setItemId(rs.getInt("inventorylist.id"));
				ii.setItemGroup(rs.getInt("inventorylist.itemgroup"));
				ii.setItemQty(rs.getInt("inventorylist.itemqty"));
				ii.setItemPrice(rs.getDouble("inventorylist.itemprice"));
				list.add(ii);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int saveInventoryItem(InventoryItem ii) {
		String query = "insert into inventorylist(id,itemname,itemcode,itemgroup,itemqty,itemprice) values(null,?,?,?,?,?)";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, ii.getItemName());
			ps.setString(2, ii.getItemCode());
			ps.setInt(3, ii.getItemGroup());
			ps.setInt(4, ii.getItemQty());
			ps.setDouble(5, ii.getItemPrice());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateInventoryItem(InventoryItem ii) {
		String query = "update inventorylist set itemname=?,itemcode=?,itemgroup=?,itemqty=?,itemprice=? where id=?";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, ii.getItemName());
			ps.setString(2, ii.getItemCode());
			ps.setInt(3, ii.getItemGroup());
			ps.setInt(4, ii.getItemQty());
			ps.setDouble(5, ii.getItemPrice());
			ps.setInt(6, ii.getItemId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteInventoryItem(int id) {
		String query = "delete from inventorylist where id=?";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public InventoryItem getItemWithId(int id) {
		String query = "select * from inventorylist where id=?";
		InventoryItem ii = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				ii = new InventoryItem();
				ii.setItemId(rs.getInt("id"));
				ii.setItemCode(rs.getString("itemcode"));
				ii.setItemName(rs.getString("itemname"));
				ii.setItemGroup(rs.getInt("itemgroup"));
				ii.setItemPrice(rs.getDouble("itemprice"));
				ii.setItemQty(rs.getInt("itemqty"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ii;
	}
	
	public List<CashBox> getCashBoxes(){
		String query = "select * from cashbox";
		List<CashBox> list = new ArrayList<CashBox>();
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				CashBox cb = new CashBox();
				cb.setId(rs.getInt("id"));
				cb.setCashBoxName(rs.getString("cashboxname"));
				cb.setCashBoxBalance(rs.getDouble("cashboxbalance"));
				list.add(cb);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int saveCashBox(CashBox cb) {
		String query = "insert into cashbox(id,cashboxname,cashboxbalance) values(null,?,?)";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, cb.getCashBoxName());
			ps.setDouble(2, cb.getCashBoxBalance());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int updateCashBox(CashBox cb) {
		String query = "update cashbox set cashboxname=?,cashboxbalance=? where id=?";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, cb.getCashBoxName());
			ps.setDouble(2, cb.getCashBoxBalance());
			ps.setInt(3, cb.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public int deleteCashBox(int id) {
		String query = "delete from cashbox where id=?";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public CashBox getCashBoxWithId(int id) {
		String query = "select * from cashbox where id=?";
		CashBox cb = null;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				cb = new CashBox();
				cb.setId(rs.getInt("id"));
				cb.setCashBoxName(rs.getString("cashboxname"));
				cb.setCashBoxBalance(rs.getDouble("cashboxbalance"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return cb;
	}
	
	public int saveFinancialMovement(FinancialMovement fm) {
		String query = "insert into financialmovements(id,operationtype,operationtime,operationitem,operationqty,operationcustomer,itemunitprice) values(null,?,?,?,?,?,?)";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, fm.getOperationType());
			ps.setLong(2, fm.getOperationTime());
			ps.setInt(3, fm.getOperationItem());
			ps.setInt(4, fm.getOperationQty());
			ps.setInt(5, fm.getOperationCustomer());
			ps.setDouble(6, fm.getItemUnitPrice());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public SellBuyDTO getLastOperation(int operationType) {
		String query = "select * from financialmovements inner join inventorylist on financialmovements.operationitem = inventorylist.id inner join businesspartners on financialmovements.operationcustomer = businesspartners.id where financialmovements.operationtype=? order by financialmovements.operationtime desc limit 1";
		SellBuyDTO sDTO= null;
		try {
			ps = conn.prepareStatement(query);
			ps.setInt(1, operationType);
			ResultSet rs = ps.executeQuery();
			if(rs.next()) {
				sDTO = new SellBuyDTO();
				sDTO.setItemUnitPrice(rs.getDouble("financialmovements.itemunitprice"));
				sDTO.setItemCode(rs.getString("inventorylist.itemcode"));
				sDTO.setItemName(rs.getString("inventorylist.itemname"));
				sDTO.setItemId(rs.getInt("inventorylist.id"));
				sDTO.setBpId(rs.getInt("businesspartners.id"));
				sDTO.setBpName(rs.getString("businesspartners.partnername"));
				sDTO.setId(rs.getInt("financialmovements.id"));
				sDTO.setItemQty(rs.getInt("financialmovements.operationqty"));
				sDTO.setOperationType(rs.getInt("financialmovements.operationtype"));
				sDTO.setTotalPrice(rs.getInt("financialmovements.operationqty") * rs.getDouble("financialmovements.itemunitprice"));
				sDTO.setOperationTime(Utils.convertTimeStamptoString(rs.getLong("financialmovements.operationtime")));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sDTO;
	}
	
	public List<SellBuyDTO> getOperations(Integer operationType){
		String query = "select * from financialmovements inner join inventorylist on financialmovements.operationitem = inventorylist.id inner join businesspartners on financialmovements.operationcustomer = businesspartners.id";
		List<SellBuyDTO> list = new ArrayList<SellBuyDTO>();
		if(operationType != null) {
			query += " where financialmovements.operationtype=?";
		}
		query += " order by financialmovements.operationtime";
		try {
			ps = conn.prepareStatement(query);
			if(operationType != null) {
				ps.setInt(1, operationType);
			}
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				SellBuyDTO sDTO = new SellBuyDTO();
				sDTO.setItemUnitPrice(rs.getDouble("financialmovements.itemunitprice"));
				sDTO.setItemCode(rs.getString("inventorylist.itemcode"));
				sDTO.setItemName(rs.getString("inventorylist.itemname"));
				sDTO.setItemId(rs.getInt("inventorylist.id"));
				sDTO.setBpId(rs.getInt("businesspartners.id"));
				sDTO.setBpName(rs.getString("businesspartners.partnername"));
				sDTO.setId(rs.getInt("financialmovements.id"));
				sDTO.setItemQty(rs.getInt("financialmovements.operationqty"));
				sDTO.setOperationType(rs.getInt("financialmovements.operationtype"));
				sDTO.setTotalPrice(rs.getInt("financialmovements.operationqty") * rs.getDouble("financialmovements.itemunitprice"));
				sDTO.setOperationTime(Utils.convertTimeStamptoString(rs.getLong("financialmovements.operationtime")));
				list.add(sDTO);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<StockMovements> getStockMovements(){
		String query = "select * from financialmovements inner join inventorylist on financialmovements.operationitem = inventorylist.id inner join inventorygroups on inventorylist.itemgroup = inventorygroups.id inner join businesspartners on financialmovements.operationcustomer = businesspartners.id order by financialmovements.operationtime desc";
		List<StockMovements> list = new ArrayList<StockMovements>();
		try {
			ps = conn.prepareStatement(query);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				StockMovements sm = new StockMovements();
				sm.setBpName(rs.getString("businesspartners.partnername"));
				sm.setId(rs.getInt("financialmovements.id"));
				sm.setItemCode(rs.getString("inventorylist.itemcode"));
				sm.setItemGroupCode(rs.getString("inventorygroups.groupcode"));
				sm.setItemGroupName(rs.getString("inventorygroups.groupname"));
				sm.setItemId(rs.getInt("inventorylist.id"));
				sm.setItemName(rs.getString("inventorylist.itemname"));
				sm.setItemQty(rs.getInt("financialmovements.operationqty"));
				sm.setOperationTime(Utils.convertTimeStamptoString(rs.getLong("financialmovements.operationtime")));
				sm.setOperationType(rs.getInt("financialmovements.operationtype"));
				list.add(sm);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public int saveUserRole(UserRoles userRole) {
		String query = "insert into userroles(id,rolename,rolecode) values(null,?,?)";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, userRole.getRoleName());
			ps.setInt(2, userRole.getRoleCode());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	public int updateUserRole(UserRoles userRole) {
		String query = "update userroles set rolename=?,rolecode=? where id=?";
		int result = 0;
		try {
			ps = conn.prepareStatement(query);
			ps.setString(1, userRole.getRoleName());
			ps.setInt(2, userRole.getRoleCode());
			ps.setInt(3, userRole.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
}
