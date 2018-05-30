package com.prestamos.dao.impl;



import java.math.BigDecimal;
import java.util.List;

import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.prestamos.api.PrestamosException;
import com.prestamos.dao.ClienteDao;
import com.prestamos.model.Cliente;


@Repository
public class ClienteDaoImpl extends HibernateDaoSupport implements ClienteDao {


	@Autowired
	public ClienteDaoImpl(SessionFactory sessionFactory1) {
		super.setSessionFactory(sessionFactory1);
	}

	@Override
	@Transactional
	public void save(Cliente cliente) throws PrestamosException {
		getHibernateTemplate().saveOrUpdate(cliente);
	}

	@Override
	@Transactional
	public void update(Cliente cliente) throws PrestamosException {
		getHibernateTemplate().saveOrUpdate(cliente);

	}

	@Override
	@Transactional
	public void delete(Cliente cliente) throws PrestamosException {
		getHibernateTemplate().delete(cliente);
	}

	@Override
	@Transactional
	public Cliente getById(BigDecimal id) throws PrestamosException {
		return   getHibernateTemplate().load(Cliente.class, id);
		
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Cliente> getByAll() throws PrestamosException {
		List<Cliente> documentoList ;
		DetachedCriteria detached = DetachedCriteria.forClass(Cliente.class);
		detached.addOrder(org.hibernate.criterion.Order.asc("nombre"));
		documentoList = (List<Cliente>) getHibernateTemplate().findByCriteria(detached);
		return documentoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Cliente getByName(String name) throws PrestamosException {
		List<Cliente> cliente ;
		DetachedCriteria detached = DetachedCriteria.forClass(Cliente.class);
		detached.add(Restrictions.eq("login", name));
		detached.addOrder(org.hibernate.criterion.Order.asc("nombre"));
		cliente = (List<Cliente>) getHibernateTemplate().findByCriteria(detached);
		return cliente.isEmpty()?null:cliente.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Cliente> getByFiltros(String nombre,String documento) throws PrestamosException {
		List<Cliente> documentoList ;
		DetachedCriteria detached = DetachedCriteria.forClass(Cliente.class);
		if(nombre!=null && !nombre.isEmpty()){
			detached.add(Restrictions.ilike("nombre", "%"+nombre+"%"));
		}
		if(documento!=null && !documento.isEmpty()){
			detached.add(Restrictions.eq("documento", documento));	
		}
		
		detached.addOrder(Order.asc("nombre"));
		documentoList = (List<Cliente>) getHibernateTemplate().findByCriteria(detached);
		return documentoList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Cliente getByDocumento(String documento) throws PrestamosException {
		List<Cliente> cliente ;
		DetachedCriteria detached = DetachedCriteria.forClass(Cliente.class);
		detached.add(Restrictions.eq("documento", documento));
		cliente = (List<Cliente>) getHibernateTemplate().findByCriteria(detached);
		return cliente.isEmpty()?null:cliente.get(0);
	}
	
}