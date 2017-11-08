package br.com.videoconverter;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Converter {

	public static void main(String[] args) throws IOException, InterruptedException {

		String outfile = "C://sleepy22.mp4";
		
		File encodingFile = new File(outfile + ".encoding");
		ProcessBuilder pb = new ProcessBuilder("C://ffmpg//bin//ffmpeg.exe", "-i", "C://sleepy.mp4", "-y", "-s", "-vcodec", "libvpx", "C://sleepwwy.mp4"); //or other command....
		encodingFile.createNewFile();
		pb.redirectErrorStream(true);
		pb.redirectInput(ProcessBuilder.Redirect.PIPE); //optional, default behavior
		pb.redirectOutput(encodingFile);
		Process p = pb.start();

		// if you want to wait for the process to finish
		p.waitFor();
		encodingFile.delete();
		
//		Process process = new ProcessBuilder("C://ffmpg//bin//ffmpeg.exe","-i","C://sleepy.mp4", "-c:v","libvpx", "-b:v", "1M", "-c:a", "libvorbis", "C://sleepy5.webm").start();

//		String cmd = "C://ffmpg//bin//ffmpeg.exe -v quiet -i C://sleepy.mp4 -c:v libvpx -qmin 0 -qmax 50 -crf 5 -b:v 1M -c:a libvorbis C://sleepy_novo.webm";
//		
//		Process process = Runtime.getRuntime().exec(cmd);
//		process.waitFor();
		
//		InputStream is = process.getInputStream();
//		InputStreamReader isr = new InputStreamReader(is);
//		BufferedReader br = new BufferedReader(isr);
//		String line;
//
//		System.out.printf("Output of running %s is:", Arrays.toString(args));
//
//		while ((line = br.readLine()) != null) {
//			System.out.println(line);
//		}

	}

}
