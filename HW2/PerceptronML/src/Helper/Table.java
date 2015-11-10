package Helper;

import java.util.ArrayList;

public class Table {
	
	private ArrayList<String> header;
	
	private ArrayList<ArrayList<String>> rows;
	
	public int dimension;
	
	public Table(int dimension){
		if(dimension < 1)
			throw new IllegalArgumentException("dimension less than 1");
		header = new ArrayList<String>(dimension);
		rows = new ArrayList<ArrayList<String>>();
		this.dimension = dimension;
	}
	
	public void SetHeader(ArrayList<String> header){
		if(header == null || header.size()==0)
			throw new IllegalArgumentException("null or empty header specified.");
		
		int size = header.size();
		this.header = new ArrayList<String>(size);
		this.rows = new ArrayList<ArrayList<String>>();
		this.dimension = size;
	}
	
	public void SetHeader(String[] header){
		if(header == null || header.length == 0){
			throw new IllegalArgumentException("null or empty header specified.");
		}
		int size = header.length;
		this.header = new ArrayList<String>(size);
		for(String s : header){
			this.header.add(s);
		}
		this.rows = new ArrayList<ArrayList<String>>();
		this.dimension = size;
	}
	
	public void AddRow(ArrayList<String> row){
		if(row == null)
			return;
		if(row.size() != this.dimension)
			throw new IllegalArgumentException("row specified does not match with the existing table, dimension different.");
		rows.add(row);
	}
	
	public void AddRow(String[] row){
		if(row == null)
			return;
		if(row.length != this.dimension)
			throw new IllegalArgumentException("row specified does not match with the existing table, dimension different.");
		ArrayList<String> _row = new ArrayList<String>();
		for(String s : row)
			_row.add(s);
		rows.add(_row);
	}
	
	public void DisplayTable() {
		System.out.println("Result Table:");
		for (String attribute : header) {
			System.out.print(attribute + " , ");
		}
		System.out.println();
		for (ArrayList<String> row : rows) {
			for (String c : row) {
				System.out.print(c + " , ");
			}
			System.out.println();
		}
	}

}
