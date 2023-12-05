package org.libertas.bd;

import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.LinkedList;
import java.util.List;


public class JoiaDAO {
	public void inserir(Joia j) {
		Conexao con = new Conexao();
		try {
			String sql = "INSERT INTO joia"
					+ " (nome, tipo, marca, cor, material ) VALUES (?, ?, ?, ?, ?) ";
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setString(1, j.getNome());
			prep.setString(2, j.getTipo());
			prep.setString(3, j.getMarca());
			prep.setString(4, j.getCor());
			prep.setString(5, j.getMaterial());
			prep.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconectar();
	}
	public void alterar(Joia j) {
		Conexao con = new Conexao();
		try {
			String sql = "UPDATE joia"
					+ " SET nome = ?, tipo = ?, marca = ?, cor = ?, material = ?  "
					+ " WHERE id = ? ";
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setString(1, j.getNome());
			prep.setString(2, j.getTipo());
			prep.setString(3, j.getMarca());
			prep.setString(4, j.getCor());
			prep.setString(5, j.getMaterial());
			prep.setInt(6, j.getId());
			prep.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconectar();
		
	}
	
	public void excluir(Joia j) {
		Conexao con = new Conexao();
		try {
			String sql = " DELETE FROM joia "
					+ " WHERE id = ? ";
			PreparedStatement prep = con.getConexao().prepareStatement(sql);
			prep.setInt(1, j.getId());
			
			prep.execute();
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconectar();
		
	}

	public Joia consultar(int id) {

		Joia j = new Joia();
		Conexao con = new Conexao();
		try {
			String sql = "SELECT * FROM joia WHERE id = " + id;
			Statement sta = con.getConexao().createStatement();
			ResultSet res = sta.executeQuery(sql);
			while (res.next()) {
				j.setId(res.getInt("id"));
				j.setNome(res.getString("nome"));
				j.setTipo(res.getString("tipo"));
				j.setMarca(res.getString("marca"));
				j.setCor(res.getString("cor"));
				j.setMaterial(res.getString("material"));
				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconectar();
		return j;
	}
		
	public List<Joia> listar(){
		List<Joia> lista = new LinkedList<Joia>();
			Conexao con = new Conexao();
		
		try {
			String sql = "SELECT * FROM joia ORDER BY id";
			Statement sta = con.getConexao().createStatement();
			ResultSet res = sta.executeQuery(sql);
			while (res.next()) {
				Joia j = new Joia();
				j.setId(res.getInt("id"));
				j.setNome(res.getString("nome"));
				j.setTipo(res.getString("tipo"));
				j.setMarca(res.getString("marca"));
				j.setCor(res.getString("cor"));
				j.setMaterial(res.getString("material"));
				
				lista.add(j);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		con.desconectar();
		return lista;
	}
}

