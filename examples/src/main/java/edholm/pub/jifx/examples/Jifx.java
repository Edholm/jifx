package edholm.pub.jifx.examples;

import pub.edholm.jifx.library.Message;
import pub.edholm.jifx.library.MessageParser;
import pub.edholm.jifx.library.exceptions.MalformedMessageException;
import pub.edholm.jifx.library.exceptions.MessageParseException;
import pub.edholm.jifx.library.payloads.Get;
import pub.edholm.jifx.library.utils.Constants;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;

/**
 * Created by Emil Edholm on 2016-11-01.
 */
public class Jifx {
    public static void main(String[] args) throws IOException, InterruptedException {
        new DiscoveryThread().start();
    }

    static class DiscoveryThread extends Thread {
        private void send(DatagramSocket socket) throws IOException {
            Message buffer = new Get.Builder().source(0xeda).build();

            InetAddress address = InetAddress.getByName("255.255.255.255");
            DatagramPacket packet = new DatagramPacket(
                    buffer.getContent(), buffer.size(), address, Constants.PORT
            );
            socket.setBroadcast(true);
            socket.send(packet);
            socket.setBroadcast(false);
        }

        @Override
        public void run() {
            DatagramSocket serverSocket;
            try {
                serverSocket = new DatagramSocket(null);
                serverSocket.setReuseAddress(true);
                serverSocket.bind(new InetSocketAddress(3339));

                send(serverSocket);
            } catch (IOException e) {
                System.err.println("Failed to create socket. Giving up!");
                e.printStackTrace();
                return;
            }


            byte[] receiveContents = new byte[Constants.SIZE_HEADER + 456];
            DatagramPacket receivePacket = new DatagramPacket(receiveContents, receiveContents.length);

            while (true) {
                try {
                    System.out.println();
                    serverSocket.receive(receivePacket);
                    System.out.println(String.format("Pkg from %s on port %d", receivePacket.getAddress().getHostAddress(), receivePacket.getPort()));
                } catch (IOException e) {
                    System.err.println("Failed to receive Lifx message");
                    e.printStackTrace();
                }

                try {
                    Message m = MessageParser.parse(receiveContents);
                    System.out.println(m);
                    System.out.println(m.getHeader());
                } catch (MessageParseException | MalformedMessageException e) {
                    System.err.println("Unable to parse incoming message!");
                    e.printStackTrace();
                }
            }
        }
    }
}
