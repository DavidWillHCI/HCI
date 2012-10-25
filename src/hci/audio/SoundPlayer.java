package hci.audio;

import sun.audio.*;
import java.io.*;

public class SoundPlayer 
{
	
	private String filename;

    public SoundPlayer(String filename) {
        this.filename = filename;
    }

    public void play() {
    	
    	try {
    		
    		InputStream in = new FileInputStream(filename);
    		AudioStream as = new AudioStream(in);
    		AudioPlayer.player.start(as); 
    		
    	} catch (IOException e) {
    		e.printStackTrace();
    	}          
    }
	
}
