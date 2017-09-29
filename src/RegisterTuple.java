public class RegisterTuple {

    public Register reg1;
    public Register reg2;

    public RegisterTuple(Register reg1, Register reg2) {
        this.reg1 = reg1;
        this.reg2 = reg2;
    }

    public void editRegister(Register reg1, Register reg2){
        this.reg1 = reg1;
        this.reg2 = reg2;
    }
}
