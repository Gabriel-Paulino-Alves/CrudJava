var valores = [];
var idpessoa = 0;

function novo(){
    var form = document.getElementById("formulario");
    var lista = document.getElementById("lista");

    //mostra o formulário 
    form.style.display = "block";
    //esconde lista 
    lista.style.display = "none";

    //lista os inputs
    id = 0;
    var nome = document.getElementById("nome");
    var tipo = document.getElementById("tipo");
    var marca = document.getElementById("marca");
    var cor = document.getElementById("cor");
    var material = document.getElementById("material");
    nome.value = "";
    tipo.value = "";
    marca.value = "";
    cor.value = "";
    material.value = "";
    
    //joga o foco no 1º campo:
    nome.focus();
}

function alterar(i){
    var form = document.getElementById("formulario");
    var lista = document.getElementById("lista");

    //mostra o formulário 
    form.style.display = "block";
    //esconde lista 
    lista.style.display = "none";

    //lista os inputs
    id = valores[i].id;
    var nome = document.getElementById("nome");
    var tipo = document.getElementById("tipo");
    var marca = document.getElementById("marca");
    var cor = document.getElementById("cor");
    var material = document.getElementById("material");
    nome.value = valores[i].nome;
    tipo.value = valores[i].tipo;
    marca.value = valores[i].marca;
    cor.value = valores[i].cor;
  	material.value = valores[i].material;
    
    //joga o foco no 1º campo:
    nome.focus();
}

function salvar(){
	//valida campos obrigarotios
	if(document.getElementById("nome").value  == ""){
		alert("campo Nome é obrigaratório!");
		return;
	}
	
    //pega os dados digitados pelo usuário e monta um objeto
    var j = {
		id: id,
		nome:  document.getElementById("nome").value,
		tipo: document.getElementById("tipo").value,
		marca: document.getElementById("marca").value,
		cor: document.getElementById("cor").value,
		material: document.getElementById("material").value
	};
   
   	//define se o método sera para inserir ou alterar
   	if (id == 0) {
		   metodo = "POST";
	   } else {
		   metodo = "PUT";
	   }
   
	//envia os dados para o servidor
	mostraLoading("aguarde, salvando dados...")
	fetch("http://localhost:8080/Joias/Joia",
		{method: metodo,
	    body: JSON.stringify(j)
		}
	).then(resp => resp.json())
	.then(function (Retorno){
		escondeLoading();
		alert(Retorno.mensagem);
		
		var form = document.getElementById("formulario");
    	var lista = document.getElementById("lista");

    	//escondeo o formulário 
    	form.style.display = "none";
    	//mostra a lista 
    	lista.style.display = "block";
    	
    	// recarrega lista
    	listar();
    	
	});
    
}
function excluir(i){
 	id = valores[i].id; 
 
	//envia os dados para o servidor
	mostraLoading("aguarde, excluindo...");
	fetch("http://localhost:8080/Joias/Joia" + id,
		{method: "DELETE",
		}
	).then(resp => resp.json())
	.then(function (retorno){
		escondeLoading();
		alert(retorno.mensagem);
		
		var form = document.getElementById("formulario");
    	var lista = document.getElementById("lista");

    	//escondeo o formulário 
    	form.style.display = "none";
    	//mostra a lista 
    	lista.style.display = "block";
    	
    	// recarrega lista
    	listar();
    	
	});
    
}

function cancelar(){
    var form = document.getElementById("formulario");
    var lista = document.getElementById("lista");

    //escondeo o formulário 
    form.style.display = "none";
    //mostra a lista 
    lista.style.display = "block";
}

function listar(){
	var lista = document.getElementById("dados");
    //limpa a lista
    lista.innerHTML = "<tr><td colspan>aguarde, carregando...</td></tr>";
	
    fetch ("http://localhost:8080/Joias/Joia")
    .then(resp => resp.json())
    .then(dados => mostrar(dados));
}

function mostrar(dados){
	valores = dados;
    var lista = document.getElementById("dados");
    //limpa a lista
    lista.innerHTML ="";
    //percoorre a lista 
    for (var i in dados){
        lista.innerHTML += "<tr>"
                        + "<td>" + dados[i].id + "</td>"
                        + "<td>" + dados[i].nome + "</td>"
                        + "<td>" + dados[i].tipo + "</td>"
                        + "<td>" + dados[i].marca + "</td>"
                        + "<td>" + dados[i].cor + "</td>"
                        + "<td>" + dados[i].material + "</td>"
                        + "<td> <input type='button' value='A' onclick='alterar("+i+")'/></td>"
                        + "<td> <input type='button' value='X' onclick='excluir("+i+")'/>"
                        +"</td>"
                        + "</tr>";
                        
    }
}

function mostraLoading(msg){
	var loa = document.getElementById("loading");
    var con = document.getElementById("conteudo");
    loa.style.display = "block";
	con.style.display = "none";
	loa.innerHTML = msg;
}

function escondeLoading(){
	var loa = document.getElementById("loading");
    var con = document.getElementById("conteudo");
    loa.style.display = "none";
	con.style.display = "block";
	
}
listar();