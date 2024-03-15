package model;

public class Carro {
    private String modelo;
    private String placa;
    private String renavam;
    private String chassi;
    private int anoFabri;

    public Carro() {
        modelo = "";
        placa = "";
        renavam = "";
        chassi = "";
        anoFabri = 0;
    }

    public Carro(String modelo, String placa, String renavam, String chassi, int anoFabri) {
        setModelo(modelo);
        setPlaca(placa);
        setRenavam(renavam);
        setChassi(chassi);
        setAnoFabri(anoFabri);
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }


    public void setRenavam(String renavam) {
        this.renavam = renavam;
    }

    public String getChassi() {
        return chassi;
    }

    public void setChassi(String chassi) {
        this.chassi = chassi;
    }

    public int getAnoFabri() {
        return anoFabri;
    }

    public void setAnoFabri(int anoFabri) {
        this.anoFabri = anoFabri;
    }

    @Override
    public String toString() {
        return "Carro: " + modelo + "   Placa: " + placa + "   Ano de Fabricação: " + anoFabri;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Carro carro = (Carro) obj;
        return placa == carro.placa;
    }

	public String getRenavam() {
		
		return renavam;
	}
}
