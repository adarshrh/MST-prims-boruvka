package axh190002;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GenerateGraphForTest {
    public static void main(String[] args) throws IOException {
        BufferedWriter fw = new BufferedWriter(new FileWriter("D:\\Sem2\\IDSA\\JAVAProject\\src\\axh190002\\SP9_test_cases\\mst-100k-300M.txt"));
        Graph.Timer timer = new Graph.Timer();
        timer.start();
        int vertices=100000;
        int edges=300000000;
        String first = vertices+" "+edges;
        fw.write(first);
        fw.newLine();
        Random rd = new Random();
        int edgeCount=0;
        for(int i=1;i<vertices;i++){
            fw.write(i+" "+(i+1) + " " + rd.nextInt(vertices));
            fw.newLine();
            edgeCount++;
        }
        int v=vertices-2;
        while(edgeCount<=edges){
            for (int i=v+2;i<=vertices;i++){
                fw.write(v+" "+i + " " + rd.nextInt(vertices));
                fw.newLine();
                edgeCount++;
            }
            v--;
        }
        fw.close();
        System.out.println(timer.end());
    }
}
