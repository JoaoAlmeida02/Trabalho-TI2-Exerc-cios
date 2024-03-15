package app;

import static spark.Spark.*;
import service.CarroService;

public class Aplicacao {
    
    private static CarroService carroService = new CarroService();
   
    public static void main(String[] args) {
   port(6789);
        
        
        post("/carro/insert", (request, response) -> carroService.insert(request, response));
        get("/carro/:Placa", (request, response) -> carroService.get(request, response));
        get("/carro/list/Placa", (request, response) -> carroService.getAll(request, response)); 
        get("/carro/update/:Placa", (request, response) -> carroService.getToUpdate(request, response));
        get("/carro/delete/:Placa", (request, response) -> carroService.delete(request, response));
    }
    
}
