package br.ages.crud.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import br.ages.crud.dto.UsuarioDTO;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.util.ConexaoUtil;
/**
 * 
 * @author cassio trindade
 *
 */
public class UsuarioDAO {

	/**
	 * Autentica o usuário
	 * @author cassio trindade 
	 * @param usuarioDTO
	 * @return
	 * @throws PersistenciaException
	 */
	
	public boolean validarUsuario(UsuarioDTO usuarioDTO ) throws PersistenciaException {
		try {
			Connection conexao = ConexaoUtil.getConexao();
			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM TB_USUARIO ");
			sql.append("WHERE USUARIO = ? AND SENHA = ?");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setString(1,  usuarioDTO.getUsuario());
			statement.setString(2, usuarioDTO.getSenha());
			
			ResultSet resultSet= statement.executeQuery();
			return resultSet.next();
			
			
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
			throw new PersistenciaException(e);
		}

	}
	
	/**
	 * Lista as pessoas da base
	 * 
	 * @return
	 * @throws PersistenciaException 
	 * @throws SQLException 
	 */
	public List<UsuarioDTO> listarUsuarios() throws PersistenciaException, SQLException {
		ArrayList<UsuarioDTO> listarUsuarios = new ArrayList<>();
		Connection conexao = null;

		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM TB_USUARIO");
			
			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {
				UsuarioDTO dto = new UsuarioDTO();
				dto.setIdUsuario(resultset.getInt("ID_USUARIO"));
				dto.setUsuario(resultset.getString("USUARIO"));
				dto.setSenha(resultset.getString("SENHA"));
				
				listarUsuarios.add(dto);
			}
			
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			conexao.close();
		}
		return listarUsuarios;
	}
}
