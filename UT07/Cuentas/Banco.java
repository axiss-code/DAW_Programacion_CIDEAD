package Cuentas;

/**
 * Mantendrá como atributo la estructura que almacena las cuentas.
 * Esta estructura podrá contener un máximo de 100 cuentas bancarias.
 * Para almacenar los objetos de tipo cuenta se deberá utilizar un array.
 * Dispondrá de los siguientes métodos:
 * - Constructor o constructores.
 * - abrirCuenta: recibe por parámetro un objeto CuentaBancaria y lo almacena en la estructura.
 *   Devuelve true o false indicando si la operación se realizó con éxito.
 * - listadoCuentas: no recibe parámetro y devuelve un array donde cada elemento es una cadena que
 *   representa la información de una cuenta.
 * - informacionCuenta: recibe un iban por parámetro y devuelve una cadena con la información de la
 *   cuenta o null si la cuenta no existe.
 * - ingresoCuenta: recibe un iban por parámetro y una cantidad e ingresa la cantidad en la cuenta.
 *   Devuelve true o false indicando si la operación se realizó con éxito.
 * - retiradaCuenta: recibe un iban por parámetro y una cantidad y trata de retirar la cantidad de la
 *   cuenta. Devuelve true o false indicando si la operación se realizó con éxito.
 * - obtenerSaldo: Recibe un iban por parámetro y devuelve el saldo de la cuenta si existe. En caso
 *   contrario devuelve -1.
 */
public class Banco {

    private final CuentaBancaria[] cuentas;
    private final int sizeArrayCuentas = 100;
    private int contadorCuentas;

    public Banco() {
        this.cuentas = new CuentaBancaria[this.sizeArrayCuentas];
        this.contadorCuentas = 0;
    }
    
    //Método privado que nos servirá para saber si existe una cuenta en nuestro array.
    //Nos devolverá la posición del array en la que se encuentra o -1 en caso negativo.
    private int existeCuenta (String iban) {

        for (int i = 0; i < this.contadorCuentas; i++) {
            if (this.cuentas[i].getIban().equals(iban)) {
                return i;
            }
        }
        return -1;
    }
            
    //Recibe por parámetro un objeto CuentaBancaria y lo almacena en la estructura.
    //Devuelve true o false indicando si la operación se realizó con éxito.
    public boolean abrirCuenta(CuentaBancaria cta) {
        
        if (this.contadorCuentas == this.sizeArrayCuentas) {
            System.out.println("Es imposible crear más cuentas."
                    + "\nSe ha alcanzado el límite ("+this.sizeArrayCuentas+") de cuentas permitidas.");
            return false;
        }
        
        if (existeCuenta(cta.getIban()) != -1){
            System.out.print("Ya existe una cuenta con el IBAN introducido");
            return false;
        } else {
            this.cuentas[this.contadorCuentas] = cta;
            this.contadorCuentas++;
            //instanceof CuentaCorrienteEmpresa
            if (cta instanceof CuentaCorrienteEmpresa) {
                System.out.print("Cuenta corriente de empresa creada.");
            } else if (cta instanceof CuentaCorrientePersonal) {
                System.out.print("Cuenta corriente personal creada.");
            } else if (cta instanceof CuentaAhorro){
                System.out.print("Cuenta de ahorro creada.");
            }
            return true;   
        }
    }
    
    //No recibe parámetro y devuelve un array donde cada elemento es una cadena que representa la información de una cuenta.
    public String[] listadoCuentas() {
        
        String [] respuesta;
        if (this.contadorCuentas == 0) {
            respuesta = new String[] {"Aún no existen cuentas creadas."};
            return respuesta;
        } else {
            respuesta = new String [this.contadorCuentas];
            for (int i = 0; i < this.contadorCuentas; i++) {
                respuesta[i] = "------------\n"+this.cuentas[i].devolverInfoString();
                
            }
            return respuesta;
        }
    }
    
    //Recibe un iban por parámetro y devuelve una cadena con la información de la cuenta o null si la cuenta no existe.
    //Utilizo un método similar a devolverInfoString, pero devuelve toda la info detallada de la cuenta correspondiente.
    public String informacionCuenta(String iban) {

        if (existeCuenta(iban) == -1){
            return null;
        } else {
            if (this.cuentas[existeCuenta(iban)] instanceof CuentaCorrienteEmpresa) {
                return "------------\n"+((CuentaCorrienteEmpresa)this.cuentas[existeCuenta(iban)]).devolverStringInfo()+"\n------------"; 
            } else if (this.cuentas[existeCuenta(iban)] instanceof CuentaCorrientePersonal) {
                return "------------\n"+((CuentaCorrientePersonal)this.cuentas[existeCuenta(iban)]).devolverStringInfo()+"\n------------"; 
            } else if (this.cuentas[existeCuenta(iban)] instanceof CuentaAhorro){
                return "------------\n"+((CuentaAhorro)this.cuentas[existeCuenta(iban)]).devolverStringInfo()+"\n------------"; 
            } else {
                return "------------\n"+this.cuentas[existeCuenta(iban)].devolverInfoString()+"\n------------";
            }
        }
    }
    
    //Recibe un iban por parámetro y una cantidad e ingresa la cantidad en la cuenta.
    //Devuelve true o false indicando si la operación se realizó con éxito.
    public boolean ingresoCuenta (String iban, double cantidad) {
        
        if (existeCuenta(iban) == -1){
            System.out.println("No existe una cuenta con el IBAN introducido.");
            return false;
        } else {
            System.out.println("Saldo anterior= "+this.cuentas[existeCuenta(iban)].getSaldo());
            this.cuentas[existeCuenta(iban)].setSaldo(this.cuentas[existeCuenta(iban)].getSaldo()+cantidad);
            System.out.println("Saldo actual= "+this.cuentas[existeCuenta(iban)].getSaldo());
            return true;
        }
    }
    
    //Recibe un iban por parámetro y una cantidad y trata de retirar la cantidad de la cuenta.
    //Devuelve true o false indicando si la operación se realizó con éxito.
    public boolean retiradaCuenta(String iban, double cantidad) {
        
        if (existeCuenta(iban) == -1){
            System.out.println("No existe una cuenta con el IBAN introducido.");
            return false;
        }
        
        if (this.cuentas[existeCuenta(iban)].getSaldo() - cantidad > 0) {
            System.out.println("Saldo anterior= "+this.cuentas[existeCuenta(iban)].getSaldo());
            this.cuentas[existeCuenta(iban)].setSaldo(this.cuentas[existeCuenta(iban)].getSaldo() - cantidad);
            System.out.println("Saldo actual= "+this.cuentas[existeCuenta(iban)].getSaldo());
            return true;
        } else if (this.cuentas[existeCuenta(iban)] instanceof CuentaCorrienteEmpresa) {
            double limite = ((CuentaCorrienteEmpresa)this.cuentas[existeCuenta(iban)]).getMaximoDescubiertoPermitido();
            double comision = ((CuentaCorrienteEmpresa)this.cuentas[existeCuenta(iban)]).getComisionFijaDescubierto();
            double descubierto = this.cuentas[existeCuenta(iban)].getSaldo();
            if (limite + descubierto - comision - cantidad > 0){
                System.out.println("Saldo anterior= "+this.cuentas[existeCuenta(iban)].getSaldo());
                this.cuentas[existeCuenta(iban)].setSaldo(descubierto - cantidad - comision);
                System.out.println("Saldo actual= "+this.cuentas[existeCuenta(iban)].getSaldo());
                return true;
            } else {
                System.out.println("Saldo ("+this.cuentas[existeCuenta(iban)].getSaldo()+") insuficiente.");
                return false;
            }
        } else {
            System.out.println("Saldo ("+this.cuentas[existeCuenta(iban)].getSaldo()+") insuficiente.");
            return false;
        }
    }
    
    //Recibe un iban por parámetro y devuelve el saldo de la cuenta si existe. En caso contrario devuelve -1.
    public double obtenerSaldo (String iban) {
        
        if (existeCuenta(iban) == -1){
            return -1;
        } else {
            return this.cuentas[existeCuenta(iban)].getSaldo();
        }
    }
}
