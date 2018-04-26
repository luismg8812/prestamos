package com.prestamos.dao;





import java.util.List;

import com.prestamos.api.PrestamosException;
import com.prestamos.model.Menu;
import com.prestamos.model.SubMenu;




public interface MenuDao {

	Menu getById(Long abono)throws PrestamosException;
	List<Menu> getByAll() throws PrestamosException;
	List<SubMenu> getSubMenuByMenuId(String menuId)throws PrestamosException;
	List<SubMenu> getByAllSubMenu()throws PrestamosException;
	SubMenu getByName(String s)throws PrestamosException;
		
}
