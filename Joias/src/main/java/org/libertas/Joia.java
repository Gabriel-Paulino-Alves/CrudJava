package org.libertas;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.libertas.bd.JoiaDAO;

import com.google.gson.Gson;


public class Joia extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Joia() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		//out.println("Executando método GET");
		JoiaDAO jdao = new JoiaDAO();
		List<org.libertas.bd.Joia> lista = jdao.listar();
		Gson gson = new Gson();
		out.print(gson.toJson(lista));
	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		try {
		//PEGA O BOBY DA REQUISIÇÃO CONTENDO OS DADOS PARA SER INSERIDO 
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = request.getReader();
		String line;
		while ((line = reader.readLine()) !=null) {
			sb.append(line);
		}
			String boby = sb.toString();
			
			//TRANSFORMA O PARAÊMETRO DO BODY EM OBJETO JSON
			Gson gson = new Gson();
			org.libertas.bd.Joia j = gson.fromJson(boby, org.libertas.bd.Joia.class);
			
			//INSERE OBJETO NO BANCO DE DADOS
			JoiaDAO pdao = new JoiaDAO();
			pdao.inserir(j);
			
			Retorno r = new Retorno(true, "registro inserido com sucesso");
			out.print(gson.toJson(r));
			
	 } catch (Exception e) {
		 e.printStackTrace();
		 Gson gson = new Gson();
		 Retorno r = new Retorno(false, e.getMessage());
			out.print(gson.toJson(r));
		}
}
		
	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			//PEGA O BOBY DA REQUISIÇÃO CONTENDO OS DADOS PARA SER INSERIDO 
			StringBuilder sb = new StringBuilder();
			BufferedReader reader = request.getReader();
			String line;
			while ((line = reader.readLine()) !=null) {
				sb.append(line);
			}
				String boby = sb.toString();
				
				//TRANSFORMA O PARAÊMETRO DO BODY EM OBJETO JSON
				Gson gson = new Gson();
				org.libertas.bd.Joia j = gson.fromJson(boby, org.libertas.bd.Joia.class);
				
				//INSERE OBJETO NO BANCO DE DADOS
				JoiaDAO pdao = new JoiaDAO();
				pdao.alterar(j);
				
				Retorno r = new Retorno(true, "REGISTRO ALTERADO COM SUCESSO");
				out.print(gson.toJson(r));
				
		 } catch (Exception e) {
			 e.printStackTrace();
			 Gson gson = new Gson();
			 Retorno r = new Retorno(false, e.getMessage());
			 out.print(gson.toJson(r));out.print(e.getMessage());
			}
	}
	
	
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		try {
			//pega o id passando por parâmetro 
			String id = request.getRequestURI();
			id = id.substring(id.lastIndexOf("/")+1);
			
			JoiaDAO jdao = new JoiaDAO();
			org.libertas.bd.Joia j = new org.libertas.bd.Joia();
			j.setId(Integer.parseInt(id));
			jdao.excluir(j);
			
			Retorno r = new Retorno(true, "registro excluido com sucesso");
			Gson gson = new Gson();
			out.print(gson.toJson(r));
		} catch (Exception e) {
			e.printStackTrace();
			Gson gson = new Gson();
			Retorno r = new Retorno(false, e.getMessage());
			out.print(gson.toJson(r));
		}
		
		
		
		
	}

}
