public class Operaciones {
    public double suma(double num1,double num2){
        return num1 + num2;
    }

    public double resta (double numero, double restado){
        return numero-restado;
    }

    public double multiplicacion(double numero, double multiplicador){
        return numero * multiplicador;
    }

    public double division(double dividendo, double divisor){
        if(divisor == 0){
            throw new IllegalArgumentException();
        }
        return dividendo / divisor;
    }
}