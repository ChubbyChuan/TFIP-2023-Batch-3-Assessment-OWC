package myapp;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;



public class Main {

    public static void main(String[] args) throws UnknownHostException, IOException {
        String[] arrSplit = args[0].split(":");
        
        String name = "Ong Wee Chuan";
        String email = "mr.ongwc@gmail.com";
        // opening a socket
        Socket socket = new Socket(arrSplit[0], Integer.parseInt(arrSplit[1])); // host name + port number
        String[] arr; // creating array

        try (OutputStream os = socket.getOutputStream()) { // preparing sending data out using socket to client.
            // try function due to IO error is expected.
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);
            ObjectOutputStream oos = new ObjectOutputStream(dos);

            try (InputStream is = socket.getInputStream()) { // preparing input coming in form the socket
                BufferedInputStream bis = new BufferedInputStream(is);
                DataInputStream dis = new DataInputStream(bis);
                ObjectInputStream ois = new ObjectInputStream(dis);
                // from here, we have finish set up the data input and output. We can move off
                // to wrte
                // the actual program

                while (true) {
                    String response = ois.readUTF();
                    arr = response.split(",");

                    //converting to int array
                    float[] intarr = new float[arr.length];
                    for (int i = 0; i < arr.length; i++) {
                        intarr[i] = Float.parseFloat(arr[i]); 
                    }
                  

                    // from there we are getting the mean of the array
                    // getting the number of elements 
                    int numOfElement = intarr.length;
                    // getting the sum
                    float sum = 0;
                    for (int i = 0; i < intarr.length; i++) {
                        sum += intarr[i];
                    }
                    float mean = sum / numOfElement; //calculating the mean

                    // we will be calculating the std dev
                    // summing the square difference between elements and mean
                    float totaldifference = 0;
                    for (int i = 0; i < intarr.length; i++) {
                        totaldifference += (intarr[i] - mean)*(intarr[i] - mean);
                    }
                    // calculating std_dev
                    double stdDev = Math.sqrt((totaldifference/numOfElement));
                    // recasting for now. dont know how to convert this to a proper float
                    float std_dev = (float)(stdDev);

                    //sending information back to server

                    oos.writeUTF(name);
                    oos.flush();
                    oos.writeUTF(email);
                    oos.flush();
                    oos.writeFloat(mean);
                    oos.flush();
                    oos.writeFloat(std_dev);
                    oos.flush();
                }

            } catch (EOFException ex) {
                ex.printStackTrace();
            }

        } catch (EOFException ex) {
            ex.printStackTrace();
            socket.close(); // close off the socket once its completed.
        }

    }
}
