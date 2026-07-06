package examenpc1;
import java.util.ArrayList;
import java.util.List;

interface Calculable {
    double calcularCostoBase();
}

interface ReporteServicio {
    void generarReporte(Servicio servicio, double costoBase, double totalRecargos, double costoFinal);
}

interface ReglaCosto {
    double calcularRecargo(double costoBase);
}

abstract class Servicio implements Calculable {
    protected String codigo;
    protected double distanciaKm;
    protected double tarifaBase;
    protected double precioPorKm;

    public Servicio(String codigo, double distanciaKm, double tarifaBase, double precioPorKm) {
        this.codigo = codigo;
        this.distanciaKm = distanciaKm;
        this.tarifaBase = tarifaBase;
        this.precioPorKm = precioPorKm;
    }

    @Override
    public double calcularCostoBase() {
        return tarifaBase + (precioPorKm * distanciaKm);
    }

    public String getCodigo() { return codigo; }
    public double getDistanciaKm() { return distanciaKm; }
    
    public abstract String getTipoServicio();
    public abstract double getRecargoPorTipo();
}

class ServicioTaxi extends Servicio {
    public ServicioTaxi(String cod, double dist, double base, double precioKm) { super(cod, dist, base, precioKm); }
    @Override public String getTipoServicio() { return "Taxi"; }
    @Override public double getRecargoPorTipo() { return 0.0; } // 0%
}

class ServicioDelivery extends Servicio {
    public ServicioDelivery(String cod, double dist, double base, double precioKm) { super(cod, dist, base, precioKm); }
    @Override public String getTipoServicio() { return "Delivery"; }
    @Override public double getRecargoPorTipo() { return 0.05; } // 5%
}

class ServicioEjecutivo extends Servicio {
    public ServicioEjecutivo(String cod, double dist, double base, double precioKm) { super(cod, dist, base, precioKm); }
    @Override public String getTipoServicio() { return "Ejecutivo"; }
    @Override public double getRecargoPorTipo() { return 0.10; } // 10%
}

class ReglaNocturno implements ReglaCosto {
    @Override public double calcularRecargo(double costoBase) { return costoBase * 0.15; }
}

class ReglaHoraPunta implements ReglaCosto {
    @Override public double calcularRecargo(double costoBase) { return costoBase * 0.20; }
}

class CalculadoraTarifa {
    private List<ReglaCosto> condicionesExtra;

    public CalculadoraTarifa(List<ReglaCosto> condicionesExtra) {
        this.condicionesExtra = condicionesExtra;
    }

    public double obtenerTotalRecargos(Servicio servicio) {
        double base = servicio.calcularCostoBase();
        double recargos = base * servicio.getRecargoPorTipo();
        
        for (ReglaCosto regla : condicionesExtra) {
            recargos += regla.calcularRecargo(base);
        }
        return recargos;
    }

    public double calcularCostoFinal(Servicio servicio) {
        return servicio.calcularCostoBase() + obtenerTotalRecargos(servicio);
    }
}

class ReporteConsola implements ReporteServicio {
    @Override
    public void generarReporte(Servicio servicio, double costoBase, double totalRecargos, double costoFinal) {
        System.out.println("--- REPORTE DE SERVICIO ---");
        System.out.println("Codigo: " + servicio.getCodigo());
        System.out.println("Tipo: " + servicio.getTipoServicio());
        System.out.println("Distancia: " + servicio.getDistanciaKm() + " km");
        System.out.println("Costo Base: S/ " + costoBase);
        System.out.println("Recargos aplicados: S/ " + totalRecargos);
        System.out.println("Costo Final: S/ " + costoFinal);
        System.out.println("---------------------------");
    }
}


public class ExamenPC1 {
    public static void main(String[] args) {
       
        Servicio miViaje = new ServicioDelivery("DEL-001", 10.0, 5.0, 2.0); 

       
        List<ReglaCosto> condicionesActuales = new ArrayList<>();
        condicionesActuales.add(new ReglaHoraPunta()); 

      
        CalculadoraTarifa calculadora = new CalculadoraTarifa(condicionesActuales);
        double costoBase = miViaje.calcularCostoBase();
        double recargos = calculadora.obtenerTotalRecargos(miViaje);
        double costoFinal = calculadora.calcularCostoFinal(miViaje);

    
        ReporteServicio reporte = new ReporteConsola();
        reporte.generarReporte(miViaje, costoBase, recargos, costoFinal);
    }
}