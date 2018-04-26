package com.prestamos.service.impl;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.prestamos.api.PrestamosException;
import com.prestamos.dao.MenuDao;
import com.prestamos.model.Menu;
import com.prestamos.model.SubMenu;
import com.prestamos.service.MenuService;



@Service
public class MenuServiceImpl implements MenuService {

	@Autowired
	private MenuDao menuDao;

	
	public Menu getById(Long id) throws PrestamosException {
		if (id == null) {
			throw new PrestamosException("El documento es obligatorio");
		}
		return menuDao.getById(id);
	}

	public List<Menu> getByAll() throws PrestamosException {
		return menuDao.getByAll();
	}

	@Override
	public List<SubMenu> getSubMenuByMenuId(String menuId) throws PrestamosException {
		return menuDao.getSubMenuByMenuId(menuId);
	}

	@Override
	public List<SubMenu> getByAllSubMenu() throws PrestamosException {
		return menuDao.getByAllSubMenu();
	}

	@Override
	public SubMenu getByName(String s) throws PrestamosException {
		return menuDao.getByName(s);
	}

	
}
