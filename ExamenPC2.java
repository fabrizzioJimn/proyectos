package examenpc2;

abstract class Transporte {
    protected String origen;
    protected String destino;
    protected double distancia;

    public Transporte(String origen, String destino, double distancia) {
        this.origen = origen;
        this.destino = destino;
        this.distancia = distancia;
    }

    public abstract double calcularCosto();
    public double getDistancia() { return distancia; }
}

class TransporteBus extends Transporte {
    public TransporteBus(String origen, String destino, double distancia) {
        super(origen, destino, distancia);
    }

    @Override
    public double calcularCosto() {
        return 2.50;
    }
}

class TransporteTaxi extends Transporte {
    public TransporteTaxi(String origen, String destino, double distancia) {
        super(origen, destino, distancia);
    }

    @Override
    public double calcularCosto() {
        return distancia * 3.00;
    }
}

class TransporteBicicleta extends Transporte {
    public TransporteBicicleta(String origen, String destino, double distancia) {
        super(origen, destino, distancia);
    }

    @Override
    public double calcularCosto() {
        return 0.00;
    }

    public double calcularTiempo() {
        return distancia / 15.0;
    }
}

class TransporteFactory {
    public static Transporte crearTransporte(String tipo, String origen, String destino, double distancia) {
        if (tipo.equalsIgnoreCase("bus")) {
            return new TransporteBus(origen, destino, distancia);
        }
        if (tipo.equalsIgnoreCase("taxi")) {
            return new TransporteTaxi(origen, destino, distancia);
        }
        if (tipo.equalsIgnoreCase("bicicleta")) {
            return new TransporteBicicleta(origen, destino, distancia);
        }
        return null;
    }
}

public class ExamenPC2 {
    public static void main(String[] args) {
        Transporte bus = TransporteFactory.crearTransporte("bus", "Centro", "Sur", 10.0);
        System.out.println("Transporte BUS:");
        System.out.printf("Costo: S/ %.2f\n", bus.calcularCosto());
        System.out.println("----------------------------");

        Transporte taxi = TransporteFactory.crearTransporte("taxi", "Centro", "Sur", 10.0);
        System.out.println("Transporte TAXI:");
        System.out.println("Distancia: " + (int)taxi.getDistancia() + " km");
        System.out.printf("Costo: S/ %.2f\n", taxi.calcularCosto());
        System.out.println("----------------------------");

        Transporte bici = TransporteFactory.crearTransporte("bicicleta", "Centro", "Sur", 10.0);
        System.out.println("Transporte BICICLETA:");
        System.out.println("Distancia: " + (int)bici.getDistancia() + " km");
        System.out.printf("Costo: S/ %.2f\n", bici.calcularCosto());
        System.out.printf("Tiempo estimado: %.2f horas\n", ((TransporteBicicleta)bici).calcularTiempo());
    }
}