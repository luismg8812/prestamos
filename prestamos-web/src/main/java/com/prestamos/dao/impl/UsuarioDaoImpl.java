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
import com.prestamos.dao.UsuarioDao;
import com.prestamos.model.Rol;
import com.prestamos.model.Usuario;


@Repository
public class UsuarioDaoImpl extends HibernateDaoSupport implements UsuarioDao {

	// private SessionFactory sessionFactory;

	@Autowired
	public UsuarioDaoImpl(SessionFactory sessionFactory1) {
		super.setSessionFactory(sessionFactory1);
	}

	@Override
	@Transactional
	public void save(Usuario usuario) throws PrestamosException {
		getHibernateTemplate().saveOrUpdate(usuario);
	}

	@Override
	@Transactional
	public void update(Usuario usuario) throws PrestamosException {
		getHibernateTemplate().saveOrUpdate(usuario);

	}

	@Override
	@Transactional
	public void delete(Usuario usuario) throws PrestamosException {
		getHibernateTemplate().delete(usuario);
	}

	@Override
	@Transactional
	public Usuario getById(BigDecimal id) throws PrestamosException {
		final Usuario entity = (Usuario) getHibernateTemplate().load(Usuario.class, id);
		return entity;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Usuario> getByAll() throws PrestamosException {
		List<Usuario> documentoList = new ArrayList<>();
		DetachedCriteria detached = DetachedCriteria.forClass(Usuario.class);
		detached.addOrder(org.hibernate.criterion.Order.asc("descripcion"));
		documentoList = (List<Usuario>) getHibernateTemplate().findByCriteria(detached);
		return documentoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario getByName(String name) throws PrestamosException {
		List<Usuario> usuario = new ArrayList<>();
		DetachedCriteria detached = DetachedCriteria.forClass(Usuario.class);
		detached.add(Restrictions.eq("login", name));
		usuario = (List<Usuario>) getHibernateTemplate().findByCriteria(detached);
		return usuario.isEmpty()?null:usuario.get(0);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Usuario> getByFiltros(String nombre,String documento) throws PrestamosException {
		List<Usuario> documentoList = new ArrayList<>();
		DetachedCriteria detached = DetachedCriteria.forClass(Usuario.class);
		if(nombre!=null && !nombre.isEmpty()){
			detached.add(Restrictions.eq("nombre", nombre));
		}
		if(documento!=null && !documento.isEmpty()){
			detached.add(Restrictions.eq("documento", documento));	
		}
		
		detached.addOrder(org.hibernate.criterion.Order.asc("nombre"));
		documentoList = (List<Usuario>) getHibernateTemplate().findByCriteria(detached);
		return documentoList;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Usuario getByIdentificacion(String identificacion) throws PrestamosException {
		List<Usuario> usuario = new ArrayList<>();
		DetachedCriteria detached = DetachedCriteria.forClass(Usuario.class);
		detached.add(Restrictions.eq("login", identificacion));
		usuario = (List<Usuario>) getHibernateTemplate().findByCriteria(detached);
		return usuario.isEmpty()?null:usuario.get(0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public List<Rol> getByAllRoles() throws PrestamosException {
		List<Rol> documentoList = new ArrayList<>();
		DetachedCriteria detached = DetachedCriteria.forClass(Rol.class);
		detached.addOrder(org.hibernate.criterion.Order.asc("nombre"));
		documentoList = (List<Rol>) getHibernateTemplate().findByCriteria(detached);
		return documentoList;
	}

}