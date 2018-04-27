package com.prestamos.dao;



import java.math.BigDecimal;
import java.util.List;

import com.prestamos.api.PrestamosException;
import com.prestamos.model.Rol;
import com.prestamos.model.Usuario;



public interface UsuarioDao  {

	void save(Usuario usuario) throws PrestamosException;

	void update(Usuario usuario) throws PrestamosException;

	void delete(Usuario usuario) throws PrestamosException;

	Usuario getById(BigDecimal idMarca) throws PrestamosException;

	List<Usuario> getByAll() throws PrestamosException;

	Usuario getByName(String name)throws PrestamosException;

	List<Usuario> getByFiltros(String nom, String documentoBusqueda)throws PrestamosException;

	Usuario getByIdentificacion(String identificacion)throws PrestamosException;

	List<Rol> getByAllRoles()throws PrestamosException;
}
