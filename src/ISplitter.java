
import java.util.ArrayList;
import java.util.Stack;

/**
 * Interfaz para diferentes heuristicas de split. 
 *
 */
public interface ISplitter {
	
	/**
	 * Metodo para obtener la tupla de registros base de los nuevos nodos que se crearan.
	 * @param registers Lista de registros de las que se obtendra la tupla.
	 * @return Tupla de registros bases para los nuevos nodos.
	 */
    RegisterTuple pickSeeds(ArrayList<Register> registers);
    
    /**
     * Selecciona el proximo registro que se eligira desde la lista de registros.
     * @param n Nuevo Nodo donde se repartiran los registros.
     * @param nn Nuevo Nodo donde se repartiran los registros.
     * @param registers Lista con registros.
     * @return
     */
    Register pickNext(Node n, Node nn, ArrayList<Register> registers);
    
    /**
     * Metodo para hacer split de un Nodo.
     * @param n Nodo en el que se hara split.
     * @param nodes Stack con nodos por actualizar.
     */
    void split(Node n,Stack<Long> nodes);
}
