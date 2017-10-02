import java.io.Serializable;


/**
 * Registro es la tupla de un rectangulo y un identificador 
 * que representa a que nodo/hoja pertenecen y en que archivo deberian ser buscados
 */
@SuppressWarnings("serial")
public class Register implements Serializable{
    Rectangle rectangle;
    Long serialVersionUID;

    /**
     * Registro con un rectangulo y un serial.
     * @param rectangle Rectangulo definido por 2 puntos.
     * @param serialVersionUID Serial.
     */
    public Register(Rectangle rectangle, Long serialVersionUID) {
        this.rectangle = rectangle;
        this.serialVersionUID = serialVersionUID;
    }
}
