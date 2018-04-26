package com.prestamos.service;


import java.util.List;

import com.prestamos.api.PrestamosException;
import com.prestamos.model.Menu;
import com.prestamos.model.SubMenu;


/**
 * @author Luismg
 *
 */
public interface MenuService {
	

	
	/**
	 * @param id
	 * @return
	 * @throws PrestamosException
	 */
	Menu getById(Long id) throws PrestamosException;

	/**
	 * @return
	 * @throws PrestamosException
	 */
	List<Menu> getByAll() throws PrestamosException;

	/**
	 * @return
	 * @throws PrestamosException
	 */
	List<SubMenu> getSubMenuByMenuId(String menuId)throws PrestamosException;

	/**
	 * @return
	 * @throws PrestamosException
	 */
	List<SubMenu> getByAllSubMenu() throws PrestamosException;

	/**
	 * @param s
	 * @return
	 * @throws PrestamosException
	 */
	SubMenu getByName(String s) throws PrestamosException;
	
	
}
