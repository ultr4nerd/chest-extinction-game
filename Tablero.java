/**
 *
 * @author Mauricio Chávez
 */
public class Tablero{

	private Pieza[][] tablero;

	public Tablero(){
		tablero = new Pieza[6][6];
	}

	public Tablero(Pieza[][] arregloPiezas){
		arregloPiezas = tablero;
	}

	public Pieza[][] obtenerArregloPiezas(){
		return tablero;
	}

	public Pieza obtenerPieza(int fila,int columna){
		return tablero[fila-1][columna-1];
	}

	public void agregarPieza(Pieza pieza,int fila,int columna){
		tablero[fila-1][columna-1] = pieza;
	}

	public void eliminarPieza(Pieza pieza,Pieza piezaEliminada) throws EliminacionInvalidaExcepcion{
		try{
			pieza.validarEliminar(piezaEliminada);
			tablero[piezaEliminada.obtenerFila()-1][piezaEliminada.obtenerColumna()-1] = null;
		}catch(EliminacionInvalidaExcepcion e){
			System.out.println(e);
		}
	}

	public void quitarPiezaTablero(Pieza pieza){
		tablero[pieza.obtenerFila()-1][pieza.obtenerColumna()-1] = null;
	}

	private void enroque(Pieza rey, Pieza torre,int tipoEnroque){
		if(tipoEnroque==1){
			quitarPiezaTablero(torre);
			agregarPieza(torre,rey.obtenerFila(),rey.obtenerColumna()-1);
		}
		else if(tipoEnroque==2){
			quitarPiezaTablero(torre);
			agregarPieza(torre,rey.obtenerFila(),rey.obtenerColumna()+1);
		}
	}


	public void moverPieza(Pieza pieza,int fila,int columna){
		try{
			pieza.validarMovimiento(fila,columna,this);
			if(pieza.esPosibleEnrocar()){
				enroque(pieza,pieza.obtenerTorreAEnrocar(),pieza.obtenerTipoDeEnroque());
				pieza.deshabilitarEnroque();
			}
			if(obtenerPieza(fila,columna) != null){
				pieza.validarEliminar(obtenerPieza(fila,columna));
				eliminarPieza(pieza,obtenerPieza(fila,columna));
			}
			quitarPiezaTablero(pieza);
			tablero[fila-1][columna-1] = pieza;			
			tablero[fila-1][columna-1].asignarPosicion(fila,columna);
			tablero[fila-1][columna-1].sumarMovimiento();

			
		}catch(MovimientoNoValidoExcepcion e){
			System.out.println(e);
		}catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Movimiento no permitido: está fuera del tablero");
		}catch(NullPointerException e){
			System.out.println("Movimiento no permitido: no puedes mover una pieza que no existe");
		}catch(EliminacionInvalidaExcepcion e){
			System.out.println(e);
		}
	}

	public String toString(){
        return "      A   B   C   D   E   F  \n" +
           	   "    + — + — + — + — + — + — +\n" +
           	   " 1  | " + this.piezasFilaToString1(1) +
           	   "    + — + — + — + — + — + — +\n" +
           	   " 2  | " + this.piezasFilaToString1(2) +
           	   "    + — + — + — + — + — + — +\n" +
           	   " 3  | " + this.piezasFilaToString1(3) +
           	   "    + — + — + — + — + — + — +\n" +
           	   " 4  | " + this.piezasFilaToString1(4) +
           	   "    + — + — + — + — + — + — +\n" +
           	   " 5  | " + this.piezasFilaToString1(5) +
               "    + — + — + — + — + — + — +\n" +
           	   " 6  | " + this.piezasFilaToString1(6) +
         	   "    + — + — + — + — + — + — +";
    }

	public String toString(int numeroJugador){
        if(numeroJugador==1)
        	return "      A   B   C   D   E   F  \n" +
               	   "    + — + — + — + — + — + — +\n" +
               	   " 1  | " + this.piezasFilaToString1(1) +
               	   "    + — + — + — + — + — + — +\n" +
               	   " 2  | " + this.piezasFilaToString1(2) +
               	   "    + — + — + — + — + — + — +\n" +
               	   " 3  | " + this.piezasFilaToString1(3) +
               	   "    + — + — + — + — + — + — +\n" +
               	   " 4  | " + this.piezasFilaToString1(4) +
               	   "    + — + — + — + — + — + — +\n" +
               	   " 5  | " + this.piezasFilaToString1(5) +
              	    "    + — + — + — + — + — + — +\n" +
               	   " 6  | " + this.piezasFilaToString1(6) +
               	   "    + — + — + — + — + — + — +";
        else
        	return "      F   E   D   C   B   A  \n" +
				   "    + — + — + — + — + — + — +\n" +
				   " 6  | " + this.piezasFilaToString2(6) +
				   "    + — + — + — + — + — + — +\n" +
				   " 5  | " + this.piezasFilaToString2(5) +
				   "    + — + — + — + — + — + — +\n" +
				   " 4  | " + this.piezasFilaToString2(4) +
				   "    + — + — + — + — + — + — +\n" +
				   " 3  | " + this.piezasFilaToString2(3) +
				   "    + — + — + — + — + — + — +\n" +
				   " 2  | " + this.piezasFilaToString2(2) +
				   "    + — + — + — + — + — + — +\n" +
				   " 1  | " + this.piezasFilaToString2(1) +
				   "    + — + — + — + — + — + — +";
     }

	private String piezasFilaToString1(int fila){
	    String string = "";
	    for(int i=0;i<=5;i++){
	    	if(tablero[fila-1][i]==null) 
	    		string+= "  | ";
	    	else 
	    		string += tablero[fila-1][i].toString() + " | ";
	    } 
	    string += "\n";

	    return string;
	}

	private String piezasFilaToString2(int fila){
	    String string = "";
	    for(int i=5;i>=0;i--){
	    	if(tablero[fila-1][i]==null) 
	    		string+= "  | ";
	    	else 
	    		string += tablero[fila-1][i].toString() + " | ";
	    } 
	    string += "\n";

	    return string;
	}

}