import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.image.*;

import javax.imageio.ImageIO;

public class CharExtract {
	
	private ArrayList<Tile> tiles;
	private Picture picture;
	
	public CharExtract( File img ) throws IOException{
		picture = new Picture( img.getAbsolutePath() );
		createTiles();
	}
	
	public void createTiles()
	{
		int[][] pixels = picture.getGrayscale();
		double t = getThreshold( pixels );
		
		for( int row = 0; row < pixels.length; row++ )
		{
			for( int col = 0; col < pixels[row].length; col++ )
			{
				if( pixels[row][col] > t && !withinTiles( row , col ) )
				{
					tiles.add( getTile( pixels , t , row , col ) );
				}
			}
		}
		
	}
	
	private boolean withinTiles(int row, int col) {
		for( Tile t : tiles )
		{
			if( t.withinTile( col , row ) ) // x and y
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * @param int[][] of pixel intensities
	 */
	private double getThreshold( int[][] pixels )
	{
		int t = 0;
		for( int i = 0; i < pixels.length; i++ )
		{
			for( int j = 0; j < pixels[i].length; j++ )
			{
				
			}
		}
		return t;
	}
	
	private Tile getTile( int[][] pixels , double threshold , int x , int y )
	{
		Mask m = new Mask( x , y , threshold , pixels );
		
		
	}
	
}
