package com.prestamos.dao.impl;



import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.prestamos.api.PrestamosException;
import com.prestamos.dao.MenuDao;
import com.prestamos.model.Menu;
import com.prestamos.model.SubMenu;

@Repository
public class MenuDaoImpl extends HibernateDaoSupport implements MenuDao {


	@Autowired
	public MenuDaoImpl(SessionFactory sessionFactory1) {
		super.setSessionFactory(sessionFactory1);
	}

	

	@Override
	@Transactional
	public Menu getById(Long id) throws PrestamosException {
		final Menu entity = (Menu) getHibernateTemplate()
				.load(Menu.class, id);
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Menu> getByAll() throws PrestamosException {
		return (List<Menu>) getHibernateTemplate()
				.find("from Menu c where c.estado = 1 order by c.menuId");
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<SubMenu> getSubMenuByMenuId(String menuId) throws PrestamosException {
		List<SubMenu> subMenuList = new ArrayList<>();
		try {
			DetachedCriteria detached = DetachedCriteria.forClass(SubMenu.class);	
			detached.add(Restrictions.eq("menuId.menuId",new BigDecimal(menuId)) ) ;
			detached.addOrder(org.hibernate.criterion.Order.asc("nombre"));
			subMenuList = (List<SubMenu>) getHibernateTemplate().findByCriteria(detached);
		} catch (PrestamosException e) {
			throw e;
		}
		return subMenuList;
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<SubMenu> getByAllSubMenu() throws PrestamosException {
		List<SubMenu> documentoList = new ArrayList<>();
		DetachedCriteria detached = DetachedCriteria.forClass(SubMenu.class);
		detached.addOrder(org.hibernate.criterion.Order.asc("nombre"));
		documentoList = (List<SubMenu>) getHibernateTemplate().findByCriteria(detached);
		return documentoList;
	}



	@SuppressWarnings("unchecked")
	@Override
	public SubMenu getByName(String s) throws PrestamosException {
		List<SubMenu> subMenu ;
		DetachedCriteria detached = DetachedCriteria.forClass(SubMenu.class);
		detached.add(Restrictions.eq("nombre", s));
		subMenu = (List<SubMenu>) getHibernateTemplate().findByCriteria(detached);
		return subMenu.isEmpty()?null:subMenu.get(0);
	}
	
	

}
