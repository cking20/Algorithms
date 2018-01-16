/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author chrisrk192
 */
public class Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException{
        // TODO code application logic here
        String tooLong = "123456789101112131415";
        String tooStort = "123";
        String template = "          ";//10
        int allocateSize = Byte.SIZE * template.length();
        byte[] ba; //= new byte[Character.SIZE * 10];
        ByteBuffer bb = ByteBuffer.allocate(allocateSize);
        
        ba = template.getBytes();
        byte[] temp = tooStort.getBytes();
        byte[] whiteSpace = " ".getBytes();
        for(int i = 0; i < Character.SIZE * 10 && i < temp.length && i < ba.length;i++){
            ba[i] = temp[i];
        }
        
        String old = new String(ba, Charset.forName("UTF-8"));
        System.out.println("old " + old + ".");
        bb.put(ba);
        bb.rewind();
        bb.flip();
        getStringFromByteBuffer(bb,1);
        //fileChannelWrite(bb);
        //fileChannelRead();
        bb.rewind();
        bb.flip();
        
        //bb.get(ba);
        //String newer = new String(ba, Charset.forName("UTF-8"));
        //System.out.println("new " + newer + ".");
        //String test = new String(getByteArrayFromString("12345",10), Charset.forName("UTF-8"));
        //System.out.println(test + ".");
        
    }
    private static byte [] getByteArrayFromString(String s, int size){
        String tenTemp = "          ";//10
        String twentyTemp = "                    ";//20
        String hundTemp = "                                                "
                + "                                                    ";//100
        String template;
        switch(size){
            case 10:
                template = tenTemp;
                break;
            case 20:
                template = twentyTemp;
                break;
            case 100:
                template = hundTemp;
                break;
            default:
                template = tenTemp;    
        }
        byte[] ba = template.getBytes();
        byte[] sba = s.getBytes();
        for(int i = 0; i < Character.SIZE * 10 && i < sba.length && i < ba.length;i++){
            ba[i] = sba[i];
        }
        return ba;
    }
    
    public static void fileChannelRead() throws IOException {
		RandomAccessFile randomAccessFile = new RandomAccessFile("byFileChannel.txt",
				"rw");
		FileChannel fileChannel = randomAccessFile.getChannel();
		ByteBuffer byteBuffer = ByteBuffer.allocate(512);
		Charset charset = Charset.forName("US-ASCII");
		while (fileChannel.read(byteBuffer) > 0) {
			byteBuffer.rewind();
			System.out.print("charset.decode buffer" + charset.decode(byteBuffer));
			byteBuffer.flip();
		}
		fileChannel.close();
		randomAccessFile.close();
	}

	public static void fileChannelWrite(ByteBuffer byteBuffer)
			throws IOException {

		Set options = new HashSet();
		options.add(StandardOpenOption.CREATE);
		options.add(StandardOpenOption.APPEND);

		Path path = Paths.get("byFileChannel.txt");

		FileChannel fileChannel = FileChannel.open(path, options);
		fileChannel.write(byteBuffer);
		fileChannel.close();
	}
        
        private static String getStringFromByteBuffer(ByteBuffer populated, int stringSize){
            byte[] wholeArray = populated.array();
            byte[] portion = new byte[stringSize * Character.BYTES];
            for(int i = 0;i < portion.length-1;i++){
                portion[i] = wholeArray[i + populated.arrayOffset()];
            }
            String ret = new String(portion, Charset.forName("UTF-8"));
            System.out.println(ret + "end buff print\n");
            return ret;
            /*byte[] ba = new byte[Character.SIZE *stringSize];
            Charset charset = Charset.forName("US-ASCII");
            populated.get(ba, 0, Character.SIZE * stringSize);
            //populated.get(ba);
            String ret = new String(ba, Charset.forName("UTF-8"));
            System.out.println(ret + "\n");
            return ret;*/
        }
    }

