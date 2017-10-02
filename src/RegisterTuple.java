
/**
 * Tupla para guardar 2 registros. 
 *   
 */
public class RegisterTuple {

    public Register reg1;
    public Register reg2;

    /**
     * Metodo para crear una tupla de registros.
     * @param reg1 Registro 1.
     * @param reg2 Registro 2.
     */
    public RegisterTuple(Register reg1, Register reg2) {
        this.reg1 = reg1;
        this.reg2 = reg2;
    }

    /**
     * Metodo para editar los registros de la tupla.
     * @param reg1 Nuevo registro 1.
     * @param reg2 Nuevo registro 2.
     */
    public void editRegister(Register reg1, Register reg2){
        this.reg1 = reg1;
        this.reg2 = reg2;
    }
}
