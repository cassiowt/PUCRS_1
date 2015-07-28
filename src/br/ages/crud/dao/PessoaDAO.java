package br.ages.crud.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import br.ages.crud.dto.CidadeDTO;
import br.ages.crud.dto.EnderecoDTO;
import br.ages.crud.dto.PessoaDTO;
import br.ages.crud.dto.PreferenciaDTO;
import br.ages.crud.dto.UfDTO;
import br.ages.crud.exception.PersistenciaException;
import br.ages.crud.util.ConexaoUtil;

/**
 * Classe de Data Access Object para os cadastros da aplicação
 * 
 * @author Cássio Trindade
 *
 */

public class PessoaDAO {

	private DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	/**
	 * Retorna a lista de estado com id e sigla
	 * 
	 * @return lista
	 * @throws PersistenciaException
	 * @throws SQLException
	 */

	public List<UfDTO> listarUFs() throws PersistenciaException, SQLException {

		Connection conexao = null;
		List<UfDTO> lista = new ArrayList<UfDTO>();
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ID_UF, DESCRICAO FROM TB_UF");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {
				UfDTO dto = new UfDTO(resultset.getInt(1), resultset.getString(2));
				lista.add(dto);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			conexao.close();
		}
		return lista;
	}

	/**
	 * Retorna lista de cidades associadas ao id de uf passado
	 * 
	 * @param idUf
	 * @return
	 * @throws PersistenciaException
	 * @throws SQLException
	 */
	public List<CidadeDTO> consultarCidadesPorUf(Integer idUf) throws PersistenciaException, SQLException {

		Connection conexao = null;
		List<CidadeDTO> listaCidades = new ArrayList<CidadeDTO>();
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM TB_CIDADE");
			sql.append(" WHERE COD_ESTADO = ?");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, idUf);

			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {
				CidadeDTO cidadeDTO = new CidadeDTO(resultset.getInt("id_cidade"), resultset.getString("descricao"));
				UfDTO ufDTO = new UfDTO(resultset.getInt("cod_estado"));

				cidadeDTO.setUfDTO(ufDTO);

				listaCidades.add(cidadeDTO);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			conexao.close();
		}
		return listaCidades;
	}

	/**
	 * Lista as preferências musicais.
	 * 
	 * @return
	 * @throws PersistenciaException
	 * @throws SQLException
	 */
	public List<PreferenciaDTO> listarPreferencias() throws PersistenciaException, SQLException {
		Connection conexao = null;
		List<PreferenciaDTO> lista = new ArrayList<PreferenciaDTO>();
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT * FROM TB_PREFERENCIA");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {
				PreferenciaDTO dto = new PreferenciaDTO(resultset.getInt(1), resultset.getString(2));
				lista.add(dto);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			conexao.close();
		}
		return lista;
	}

	/**
	 * Cadastro de pessoa com endereço Esse método chama os demais métodos de
	 * dados associados a pessoa
	 * 
	 * @param pessoaDTO
	 * @throws PersistenciaException
	 * @throws SQLException
	 */
	public void cadastrarPessoa(PessoaDTO pessoaDTO) throws PersistenciaException, SQLException {
		Connection conexao = null;
		try {
			Integer idPessoa = null;

			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO TB_PESSOA (NOME, CPF, DT_NASC, SEXO , MINI_BIO , COD_ENDERECO, EMAIL)");
			sql.append("VALUES ( ?, ?, ?, ?, ?, ?, ? )");

			// cadastrar o endereço da pessoa
			Integer idEndereco = cadastrarEndereco(pessoaDTO.getEndereco());

			// converte a data para data Juliana, data que o banco reconhece;
			java.sql.Date dataNascimento = new java.sql.Date(dateFormat.parse(pessoaDTO.getDataNascimento()).getTime());

			// Cadastra a pessoa e gera e busca id gerado
			PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, pessoaDTO.getNome());
			statement.setFloat(2, Float.parseFloat(pessoaDTO.getCpf())); // duvida
			statement.setDate(3, dataNascimento);
			statement.setString(4, String.valueOf(pessoaDTO.getSexo()));
			statement.setString(5, pessoaDTO.getMiniBio());
			statement.setInt(6, idEndereco);
			statement.setString(7, pessoaDTO.getEmail());

			statement.executeUpdate();

			ResultSet resultset = statement.getGeneratedKeys();
			if (resultset.first()) {
				idPessoa = resultset.getInt(1);
			}

			// cadastras as preferências de uma pessoa
			cadastrarPreferencia(pessoaDTO.getPreferencias(), idPessoa);

		} catch (ClassNotFoundException | SQLException | ParseException e) {
			throw new PersistenciaException(e);
		} finally {
			conexao.close();
		}
	}

	/**
	 * Cadastra as preferências de uma determinada pessoa
	 * 
	 * @param preferencias
	 * @param idPessoa
	 * @throws PersistenciaException
	 * @throws SQLException
	 */
	public void cadastrarPreferencia(List<PreferenciaDTO> preferencias, Integer idPessoa) throws PersistenciaException, SQLException {
		Connection conexao = null;
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO TB_PREFERENCIA_PESSOA (COD_PREFERENCIA, COD_PESSOA)");
			sql.append("VALUES ( ?, ?)");

			for (PreferenciaDTO preferencia : preferencias) {
				PreparedStatement statement = conexao.prepareStatement(sql.toString());
				statement.setInt(1, preferencia.getIdPreferencia());
				statement.setInt(2, idPessoa);
				statement.executeUpdate();
			}

		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			conexao.close();
		}

	}

	/**
	 * Cadastro de endereço da pessoa e geração do id do endereço
	 * 
	 * @param enderecoDTO
	 * @return idGErado
	 * @throws PersistenciaException
	 * @throws SQLException
	 */
	public Integer cadastrarEndereco(EnderecoDTO enderecoDTO) throws PersistenciaException, SQLException {
		Connection conexao = null;
		Integer idGereado = null;
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("INSERT INTO TB_ENDERECO (LOGRADOURO, COD_CIDADE)");
			sql.append("VALUES ( ?, ? )");

			PreparedStatement statement = conexao.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, enderecoDTO.getLogradouro());
			statement.setInt(2, enderecoDTO.getCidade().getIdCidade());
			statement.executeUpdate();

			ResultSet resultset = statement.getGeneratedKeys();
			if (resultset.first()) {
				idGereado = resultset.getInt(1);
			}
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			conexao.close();
		}

		return idGereado;
	}

	/**
	 * Lista as pessoas da base
	 * 
	 * @return
	 * @throws PersistenciaException
	 * @throws SQLException
	 */
	public List<PessoaDTO> listarPessoa() throws PersistenciaException, SQLException {
		ArrayList<PessoaDTO> listarPessoa = new ArrayList<>();
		Connection conexao = null;

		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT P.*, E.LOGRADOURO, C.DESCRICAO CIDADE, UF.DESCRICAO UF ");
			sql.append("FROM ");
			sql.append("AGES_E.TB_PESSOA P ");
			sql.append("   INNER JOIN ");
			sql.append("TB_ENDERECO E ON P.COD_ENDERECO = ID_ENDERECO ");
			sql.append("        INNER JOIN");
			sql.append("    TB_CIDADE C ON E.COD_CIDADE = C.ID_CIDADE ");
			sql.append("        INNER JOIN");
			sql.append("    TB_UF UF ON C.COD_ESTADO = UF.ID_UF ");
			sql.append("ORDER BY ID_PESSOA");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {
				PessoaDTO pessoaDTO = new PessoaDTO();
				pessoaDTO.setIdPessoa(resultset.getInt("ID_PESSOA"));
				pessoaDTO.setNome(resultset.getString("NOME"));
				pessoaDTO.setEmail(resultset.getString("EMAIL"));
				pessoaDTO.setCpf(resultset.getString("CPF"));
				pessoaDTO.setSexo(resultset.getString("SEXO"));
				pessoaDTO.setDataNascimento(dateFormat.format(resultset.getDate("DT_NASC")));

				EnderecoDTO enderecoDTO = new EnderecoDTO();
				enderecoDTO.setLogradouro(resultset.getString("LOGRADOURO"));

				CidadeDTO cidadeDTO = new CidadeDTO();
				cidadeDTO.setDescricao(resultset.getString("CIDADE"));

				UfDTO ufDTO = new UfDTO();
				ufDTO.setDescricao(resultset.getString("UF"));

				cidadeDTO.setUfDTO(ufDTO);
				enderecoDTO.setCidade(cidadeDTO);
				pessoaDTO.setEndereco(enderecoDTO);
				pessoaDTO.setPreferencias(consultaPreferencias(pessoaDTO.getIdPessoa()));
				listarPessoa.add(pessoaDTO);
			}

		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			conexao.close();
		}
		return listarPessoa;
	}

	/**
	 * Retorna as preferencias musicais de cada pessoa.
	 * 
	 * @param idPessoa
	 * @return
	 * @throws PersistenciaException
	 * @throws SQLException
	 */
	public List<PreferenciaDTO> consultaPreferencias(Integer idPessoa) throws PersistenciaException, SQLException {
		
		List<PreferenciaDTO> listaPreferencias = new ArrayList<PreferenciaDTO>();
		Connection conexao = null;
		try {

			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("SELECT ");
			sql.append("P.* ");
			sql.append("FROM ");
			sql.append("    TB_PREFERENCIA P ");
			sql.append("        INNER JOIN ");
			sql.append("    TB_PREFERENCIA_PESSOA PP ON P.ID_PREFERENCIA = PP.COD_PREFERENCIA ");
			sql.append("WHERE ");
			sql.append("    PP.COD_PESSOA = ?");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, idPessoa);

			ResultSet resultset = statement.executeQuery();

			while (resultset.next()) {
				PreferenciaDTO dto = new PreferenciaDTO(resultset.getInt("ID_PREFERENCIA"), resultset.getString("DESCRICAO"));
				listaPreferencias.add(dto);
			}

		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			conexao.close();
		}

		return listaPreferencias;

	}

	/**
	 * Remove uma pessoa da base
	 * 
	 * @param pessoaDTO
	 * @throws PersistenciaException
	 * @throws SQLException
	 */
	public void removerPessoa(PessoaDTO pessoaDTO) throws PersistenciaException, SQLException {
		Connection conexao = null;
		try {

			removerEndereco(pessoaDTO.getEndereco().getIdEndereco());

			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM TB_PESSOA ID_PESSOA WHERE = ?");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, pessoaDTO.getIdPessoa());

			statement.execute();
		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			conexao.close();
		}

	}

	/**
	 * Remove endereço de pessoa da base pelo id
	 * 
	 * @param idEndereco
	 * @throws PersistenciaException
	 * @throws SQLException
	 */
	public void removerEndereco(Integer idEndereco) throws PersistenciaException, SQLException {

		Connection conexao = null;
		try {
			conexao = ConexaoUtil.getConexao();

			StringBuilder sql = new StringBuilder();
			sql.append("DELETE FROM TB_ENDERECO WHERE ID_ENDERCO = ?");

			PreparedStatement statement = conexao.prepareStatement(sql.toString());
			statement.setInt(1, idEndereco);

			statement.execute();

		} catch (ClassNotFoundException | SQLException e) {
			throw new PersistenciaException(e);
		} finally {
			conexao.close();
		}
	}
}
