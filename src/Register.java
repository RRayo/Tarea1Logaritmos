

//Registro es la tupla de un rectangulo y un identificador que representa a que nodo/hoja pertenecen
//y en que archivo deberian ser buscados
public class Register {
    Rectangle rectangle;
    Long serialVersionUID;

    public Register(Rectangle rectangle, Long serialVersionUID) {
        this.rectangle = rectangle;
        this.serialVersionUID = serialVersionUID;
    }
}
