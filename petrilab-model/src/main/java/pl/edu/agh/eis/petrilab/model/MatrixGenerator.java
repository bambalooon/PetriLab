package pl.edu.agh.eis.petrilab.model;

public class MatrixGenerator {
	private int[][] inputMatrix;
	private int[][] outputMatrix;
	private int[][] finalMatrix;
	
	public MatrixGenerator (PetriNet petriNet){
		
		int rows = petriNet.getPlaces().size();
		int columns = petriNet.getTransitions().size();
				
		inputMatrix = new int [rows][columns];
		outputMatrix = new int [rows][columns];
		finalMatrix = new int [rows][columns];		
				
	}
	public int[][] getInputMatrix(){
		return inputMatrix;
	}
	
	public int[][] getOutPutMatrix(){
		return outputMatrix;
	}
	
	public int[][] getFinalMatrix(){
		return finalMatrix;
	}


}
