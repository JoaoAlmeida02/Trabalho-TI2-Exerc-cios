package service;

import java.util.Scanner;
import java.io.File;
import java.util.List;
import dao.CarroDAO;
import model.Carro;
import spark.Request;
import spark.Response;


public class CarroService {

	private CarroDAO carroDAO = new CarroDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_PLACA = 1;
	private final int FORM_ORDERBY_MODELO = 2;
	private final int FORM_ORDERBY_ANOFABRI = 3;
	
	 public Object getAllCars(Request request, Response response) {
	        List<Carro> carros = carroDAO.get();
	        makeForm(FORM_DETAIL, carros.get(0), FORM_ORDERBY_MODELO);
	        response.status(200);
	        return form;
	    }
	 
	public CarroService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Carro(), FORM_ORDERBY_MODELO);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Carro(), orderBy);
	}

	
	public void makeForm(int tipo, Carro carro, int orderBy) {
		String nomeArquivo = "form.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umCarro = "";
		if(tipo != FORM_INSERT) {
			umCarro += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/carro/list/1\">Novo Carro</a></b></font></td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t</table>";
			umCarro += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/carro/";
			String modelo, placa, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				modelo = "Fusca";
				placa = "ABC1234";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + carro.getPlaca();
				modelo = carro.getModelo();
				placa = carro.getPlaca();
				buttonLabel = "Atualizar";
			}
			umCarro += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umCarro += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + modelo + "</b></font></td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td>&nbsp;Modelo: <input class=\"input--register\" type=\"text\" name=\"modelo\" value=\""+ modelo +"\"></td>";
			umCarro += "\t\t\t<td>Placa: <input class=\"input--register\" type=\"text\" name=\"placa\" value=\""+ placa +"\"></td>";
			umCarro += "\t\t\t<td>Ano de Fabricação: <input class=\"input--register\" type=\"text\" name=\"anofabri\" value=\""+ carro.getAnoFabri() +"\"></td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td align=\"center\" colspan=\"3\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t</table>";
			umCarro += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umCarro += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Carro (Placa " + carro.getPlaca() + ")</b></font></td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t\t<tr>";
			umCarro += "\t\t\t<td>&nbsp;Modelo: "+ carro.getModelo() +"</td>";
			umCarro += "\t\t\t<td>Placa: "+ carro.getPlaca() +"</td>";
			umCarro += "\t\t\t<td>Ano de Fabricação: "+ carro.getAnoFabri() +"</td>";
			umCarro += "\t\t</tr>";
			umCarro += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-CARRO>", umCarro);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Carros</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/carro/list/" + FORM_ORDERBY_PLACA + "\"><b>Placa</b></a></td>\n" +
        		"\t<td><a href=\"/carro/list/" + FORM_ORDERBY_MODELO + "\"><b>Modelo</b></a></td>\n" +
        		"\t<td><a href=\"/carro/list/" + FORM_ORDERBY_ANOFABRI + "\"><b>Ano de Fabricação</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Carro> carros;
		if (orderBy == FORM_ORDERBY_PLACA) {                 	carros = carroDAO.getOrderByPlaca();
		} else if (orderBy == FORM_ORDERBY_MODELO) {			carros = carroDAO.getOrderByModelo();
		} else if (orderBy == FORM_ORDERBY_ANOFABRI) {		carros = carroDAO.getOrderByAnoFabri();
		} else {											carros = carroDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Carro c : carros) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + c.getPlaca() + "</td>\n" +
            		  "\t<td>" + c.getModelo() + "</td>\n" +
            		  "\t<td>" + c.getAnoFabri() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/carro/" + c.getPlaca() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/carro/update/" + c.getPlaca() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"javascript:confirmarDeleteCarro('" + c.getPlaca() + "', '" + c.getModelo() + "', '" + c.getAnoFabri() + "');\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-CARRO>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
        String modelo = request.queryParams("modelo");
        String placa = request.queryParams("placa");
        String renavam = request.queryParams("renavam"); 
        String chassi = request.queryParams("chassi"); 
        int anoFabri = Integer.parseInt(request.queryParams("anofabri"));
        
        String resp = "";
        
        Carro carro = new Carro(modelo, placa, renavam, chassi, anoFabri); 
        
        if(carroDAO.insert(carro) == true) {
            resp = "Carro (" + modelo + ") inserido!";
            response.status(201); 
        } else {
            resp = "Carro (" + modelo + ") não inserido!";
            response.status(404); 
        }
            
        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
    }

    public Object update(Request request, Response response) {
        String placa = request.params(":placa"); 
        Carro carro = (Carro) carroDAO.get(placa); 
        String resp = "";       

        if (carro != null) {
            carro.setModelo(request.queryParams("modelo"));
            carro.setPlaca(request.queryParams("placa"));
            carro.setRenavam(request.queryParams("renavam"));
            carro.setChassi(request.queryParams("chassi")); 
            carro.setAnoFabri(Integer.parseInt(request.queryParams("anofabri")));
            carroDAO.update(carro);
            response.status(200); 
            resp = "Carro (Placa " + carro.getPlaca() + ") atualizado!"; 
        } else {
            response.status(404);
            resp = "Carro (Placa " + placa + ") não encontrado!"; 
        }
        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
    }


    public Object get(Request request, Response response) {
        String placa = request.params(":placa");
        Carro carro = (Carro) carroDAO.get(placa);

        if (carro != null) {
            response.status(200); 
            makeForm(FORM_DETAIL, carro, FORM_ORDERBY_MODELO);
        } else {
            response.status(404); 
            String resp = "Carro com a placa " + placa + " não encontrado.";
            makeForm();
            form = form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

        return form;
    }


    public Object getAll(Request request, Response response) {
        List<Carro> carros = carroDAO.get();
        makeForm(FORM_DETAIL, carros.get(0), FORM_ORDERBY_MODELO); 
        response.status(200); 
        return form;
    }

    public Object getToUpdate(Request request, Response response) {
        String placa = request.params(":placa");
        Carro carro = (Carro) carroDAO.get(placa);

        if (carro != null) {
            makeForm(FORM_UPDATE, carro, FORM_ORDERBY_MODELO);
            response.status(200); 
        } else {
            response.status(404); 
            String resp = "Carro com a placa " + placa + " não encontrado.";
            makeForm();
            form = form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

        return form;
    }

    public Object delete(Request request, Response response) {
        String placa = request.params(":placa");
        Carro carro = (Carro) carroDAO.get(placa);
        String resp = "";

        if (carro != null) {
            carroDAO.delete(placa);
            response.status(200);
            resp = "Carro com a placa " + placa + " excluído.";
        } else {
            response.status(404); 
            resp = "Carro com a placa " + placa + " não encontrado.";
        }
        makeForm();
        return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
    }

	
}