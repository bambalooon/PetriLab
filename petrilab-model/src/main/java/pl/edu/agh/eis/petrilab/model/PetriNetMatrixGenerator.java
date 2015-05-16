package pl.edu.agh.eis.petrilab.model;

public class PetriNetMatrixGenerator {
	private int[][] inputMatrix;
	private int[][] outputMatrix;
	private int[][] petriNetMatrix;
	
	public PetriNetMatrixGenerator (PetriNet petriNet){
		
		int rows = petriNet.getPlaces().size();
		int columns = petriNet.getTransitions().size();
				
		inputMatrix = new int [rows][columns];
		outputMatrix = new int [rows][columns];
		petriNetMatrix = new int [rows][columns];		
				
	}
	public int[][] getInputMatrix(){
		return inputMatrix;
	}
	
	public int[][] getOutputMatrix(){
		return outputMatrix;
	}
	
	public int[][] getFinalMatrix(){
		return petriNetMatrix;
	}


}
