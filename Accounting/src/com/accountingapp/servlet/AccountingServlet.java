package com.accountingapp.servlet;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import com.accountingapp.model.BusinessPartner;
import com.accountingapp.model.CashBox;
import com.accountingapp.model.CashBoxTransferDTO;
import com.accountingapp.model.InventoryGroup;
import com.accountingapp.model.InventoryItem;
import com.accountingapp.model.RequestModel;
import com.accountingapp.model.SellBuyDTO;
import com.accountingapp.model.SellingOperation;
import com.accountingapp.model.StockMovements;
import com.accountingapp.model.User;
import com.accountingapp.model.UserMenu;
import com.accountingapp.model.UserMenuDTO;
import com.accountingapp.model.UserRoleDTO;
import com.accountingapp.model.UserRoles;
import com.accountingapp.service.AccountingService;
import com.accountingapp.utils.Utils;
import com.google.gson.Gson;


public class AccountingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public AccountingServlet() {
        super();
        
    }
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException,ServletException{
    	
    	Gson gson = new Gson();
    	try {
			request.setCharacterEncoding("UTF-8");
		} catch (UnsupportedEncodingException e2) {
			e2.printStackTrace();
		}
    	response.setCharacterEncoding("UTF-8");
    	response.setContentType("application/json; charset=UTF-8");
    	RequestModel reqBody = Utils.getRequestModel(request);
    	AccountingService as = new AccountingService();
		PrintWriter writer = null;
		String jsonStr = "";
		try {
    		writer = response.getWriter();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(reqBody.getParams()[0].equals("login")) {
			String userMail = reqBody.getParams()[1];
    		String password = reqBody.getParams()[2];
    		String isRemember = reqBody.getParams()[3];
    		User user = as.getUserWithUsermailAndPW(userMail, password);
    		int result = 0;
    		if(user != null) {
    			result = 1;
    			request.getSession().setAttribute("currentUser", user);
    			if(isRemember.equals("true")) {
    				Cookie ck = new Cookie("userId",""+user.getId());
    				ck.setMaxAge(3600);
    				response.addCookie(ck);
    			}
    		}
    		jsonStr = gson.toJson(result);
		}
		
		Utils.sessionControl(request);
		User currentUser = (User) request.getSession().getAttribute("currentUser");
		
		if(currentUser == null && !reqBody.getParams()[0].equals("register")) {
			String r = "redirect_url:" + "../login.jsp";
			jsonStr = gson.toJson(r);
		} else {
			if(reqBody.getParams()[0].equals("save_bp")) {
	    		BusinessPartner bp = gson.fromJson(reqBody.getParams()[1], BusinessPartner.class);
				int result = as.saveOrUpdateBusinessPartner(bp);
				jsonStr = gson.toJson(result);;
	    	} else if(reqBody.getParams()[0].equals("get_all_bp")) {
	    		List<BusinessPartner> bpList = as.getBusinessPartner();
	    		jsonStr = gson.toJson(bpList);
	    	} else if(reqBody.getParams()[0].equals("delete_bp")) {
	    		int bpId = Integer.valueOf(reqBody.getParams()[1]);
				int result = as.deleteBusinessPartner(bpId);
				jsonStr = gson.toJson(result);
	    	} else if(reqBody.getParams()[0].equals("save_user")) {
	    		User user = gson.fromJson(reqBody.getParams()[0], User.class);
	    		int result = as.saveOrUpdateUser(user);
	    		jsonStr = gson.toJson(result);
	    	} else if(reqBody.getParams()[0].equals("exit")) {
	    		Cookie ck = new Cookie("userId","");
	    		ck.setMaxAge(0);
	    		response.addCookie(ck);
	    		request.getSession().removeAttribute("currentUser");
	    		request.getSession().invalidate();
	    		jsonStr = gson.toJson(1);
	    	} else if(reqBody.getParams()[0].equals("get_user_menu")) {
	    		List<UserMenu> list = as.getUserMenuList(currentUser.getUserRole());
	    		jsonStr = gson.toJson(list);
	    	} else if(reqBody.getParams()[0].equals("get_role_names")) {
	    		List<UserRoles> list = as.getUserRoles();
	    		jsonStr = gson.toJson(list);
	    	} else if(reqBody.getParams()[0].equals("get_user_menus_with_roles")) {
	    		List<UserMenuDTO> list = as.getUserListWithRoles();
	    		jsonStr = gson.toJson(list);
	    	} else if(reqBody.getParams()[0].equals("usermenu_save")) {
	    		UserMenuDTO userDTO = gson.fromJson(reqBody.getParams()[1], UserMenuDTO.class);
	    		int result = as.saveOrUpdateUserMenu(userDTO);
	    		jsonStr = gson.toJson(result);
	    	} else if(reqBody.getParams()[0].equals("usermenu_delete")) {
	    		int menuId = Integer.valueOf(reqBody.getParams()[1]);
	    		int result = as.deleteUserMenu(menuId);
	    		jsonStr = gson.toJson(result);
	    	} else if(reqBody.getParams()[0].equals("get_user_roles")) {
	    		List<UserRoleDTO> list = as.getUserRolesWithRoleNames();
	    		jsonStr = gson.toJson(list);
	    	} else if(reqBody.getParams()[0].equals("get_inventory_groups")) {
	    		List<InventoryGroup> list = as.getInventoryGroups();
	    		jsonStr = gson.toJson(list);
	    	} else if(reqBody.getParams()[0].equals("delete_inventory_group")) {
	    		int id = Integer.valueOf(reqBody.getParams()[1]);
	    		int result = as.deleteInventoryGroup(id);
	    		jsonStr = gson.toJson(result);
	    	} else if(reqBody.getParams()[0].equals("save_inventory_group")) {
	    		InventoryGroup ig = gson.fromJson(reqBody.getParams()[1], InventoryGroup.class);
	    		int result = as.saveOrUpdateInventoryGroups(ig);
	    		jsonStr = gson.toJson(result);
	    	} else if(reqBody.getParams()[0].equals("get_inventory_items")) {
	    		List<InventoryItem> list = as.getInventoryItems();
	    		jsonStr = gson.toJson(list);
	    	} else if(reqBody.getParams()[0].equals("delete_inventory_item")) {
	    		int id = Integer.valueOf(reqBody.getParams()[1]);
	    		int result = as.deleteInventoryItem(id);
	    		jsonStr = gson.toJson(result);
	    	} else if(reqBody.getParams()[0].equals("save_inventory_item")) {
	    		InventoryItem ii = gson.fromJson(reqBody.getParams()[1], InventoryItem.class);
	    		int result = as.saveOrUpdateInventoryItem(ii);
	    		jsonStr = gson.toJson(result);
	    	} else if(reqBody.getParams()[0].equals("get_cash_boxes")) {
	    		List<CashBox> list = as.getCashBoxes();
	    		jsonStr = gson.toJson(list);
	    	} else if(reqBody.getParams()[0].equals("delete_cash_box")) {
	    		int id = Integer.valueOf(reqBody.getParams()[1]);
	    		int result = as.deleteCashBox(id);
	    		jsonStr = gson.toJson(result);
	    	} else if(reqBody.getParams()[0].equals("save_cash_box")) {
	    		CashBox cb = gson.fromJson(reqBody.getParams()[1], CashBox.class);
	    		int result = as.saveOrUpdateCashBox(cb);
	    		jsonStr = gson.toJson(result);
	    	} else if(reqBody.getParams()[0].equals("transfer_cash_box")) {
	    		CashBoxTransferDTO cbt = gson.fromJson(reqBody.getParams()[1], CashBoxTransferDTO.class);
	    		int result = as.transferCashBox(cbt);
	    		jsonStr = gson.toJson(result);
	    	} else if(reqBody.getParams()[0].equals("save_selling_operation")) {
	    		SellingOperation so = gson.fromJson(reqBody.getParams()[1],SellingOperation.class);
	    		int result = as.sellingOperation(so);
	    		jsonStr = gson.toJson(result);
	    	} else if(reqBody.getParams()[0].equals("save_buying_operation")) {
	    		SellingOperation so = gson.fromJson(reqBody.getParams()[1],SellingOperation.class);
	    		int result = as.buyingOperation(so);
	    		jsonStr = gson.toJson(result);
	    	} else if(reqBody.getParams()[0].equals("get_last_operation")) {
	    		int operationType = Integer.valueOf(reqBody.getParams()[1]);
	    		SellBuyDTO sDTO = as.getLastOperation(operationType);
	    		jsonStr = gson.toJson(sDTO);
	    	} else if(reqBody.getParams()[0].equals("get_operations")) {
	    		int operationType = Integer.valueOf(reqBody.getParams()[1]);
	    		List<SellBuyDTO> list = as.getOperations(operationType);
	    		jsonStr = gson.toJson(list);
	    	} else if(reqBody.getParams()[0].equals("get_stock_movements")) {
	    		List<StockMovements> list = as.getStockMovements();
	    		jsonStr = gson.toJson(list);
	    	} else if(reqBody.getParams()[0].equals("register")) {
	    		User user = gson.fromJson(reqBody.getParams()[1], User.class);
	    		user.setUserRole(2);
	    		int result = as.saveOrUpdateUser(user);
	    		jsonStr = gson.toJson(result);
	    	} else if(reqBody.getParams()[0].equals("user_role_save")) {
	    		UserRoles userRoles = gson.fromJson(reqBody.getParams()[1], UserRoles.class);
	    		int result = as.saveOrUpdateUserRole(userRoles);
	    		jsonStr = gson.toJson(result);
	    	} else if(reqBody.getParams()[0].equals("update_user_role")) {
	    		int userId = Integer.valueOf(reqBody.getParams()[1]);
	    		int userNewRole = Integer.valueOf(reqBody.getParams()[2]);
	    		int result = as.updateUserRole(userId, userNewRole);
	    		jsonStr = gson.toJson(result);
	    	}
		}
    	
    	try {
    		writer.write(jsonStr);
    		as.closeService();
    		if(writer != null) {
            	writer.close();
    		}
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request,response);
	}

}
