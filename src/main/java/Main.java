import java.util.List;

public class Main {
    public static void main(String[] args) {
        DNAReader reader1 = new DNAReader("C:\\Users\\olga1\\Desktop\\projects\\DataBase\\ParseDNA\\src\\main\\resources\\Genome_1-1.txt");
        DNAReader reader2 = new DNAReader("C:\\Users\\olga1\\Desktop\\projects\\DataBase\\ParseDNA\\src\\main\\resources\\Genome_2-1.txt");

        List<String> t2_1 = reader1.take(3);
        System.out.println("end parse 1_1");
        List<String> t5_1 = reader1.take(5);
        System.out.println("end parse 1_2");
        List<String> t9_1 = reader1.take(9);
        System.out.println("end parse 1_3");
        DNA dna1 = new DNA(1, t2_1, t5_1, t9_1);
        List<String> t2_2 = reader2.take(3);
        System.out.println("end parse 2_1");
        List<String> t5_2 = reader2.take(5);
        System.out.println("end parse 2_2");
        List<String> t9_2 = reader2.take(9);
        System.out.println("end parse 2_3");
        DNA dna2 = new DNA(2, t2_2, t5_2, t9_2);
        DNAPostToDb post = new DNAPostToDb();
        post.insertDNA(dna1);
        System.out.println("end insert 1");
        post.insertDNA(dna2);
        System.out.println("end insert 2");
        System.out.println(post.getPerCentOfCoincidencesFor3(1, 2)* 100);
        System.out.println(post.getPerCentOfCoincidencesFor5(1, 2)* 100);
        System.out.println(post.getPerCentOfCoincidencesFor9(1, 2)* 100);
    }
}
