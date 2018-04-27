package com.prestamos.dao;



import java.math.BigDecimal;
import java.util.List;

import com.prestamos.api.PrestamosException;
import com.prestamos.model.OpcionUsuario;



public interface OpcionUsuarioDao  {

	void save(OpcionUsuario opcionUsuario) throws PrestamosException;

	void update(OpcionUsuario opcionUsuario) throws PrestamosException;

	void delete(OpcionUsuario opcionUsuario) throws PrestamosException;

	OpcionUsuario getById(BigDecimal idMarca) throws PrestamosException;

	List<OpcionUsuario> getByAll() throws PrestamosException;

	OpcionUsuario getByName(String name)throws PrestamosException;

	List<OpcionUsuario> getByUsuario(Long usuarioId);

	
}
