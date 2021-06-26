package com.jihad.friendly;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class friendly {

    // NAMA : FRIENDLY JIHAD TAQWANA
    // NIM : 20200040123
    
public static String WorkString = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz `~!@#$%^&*()_-+={}[]|\\:;’<>,.?/";
    
    public static int[][] prosesAdjMatriks(int[][] matriks){
        int[][] adjacentMatriks = new int[2][2];
        adjacentMatriks[0][0] = matriks[1][1];
        adjacentMatriks[1][1] = matriks[0][0];
        adjacentMatriks[0][1] = matriks[0][1]* -1;
        adjacentMatriks[1][0] = matriks[1][0]* -1;
        return adjacentMatriks;
    }
    
    public static int prosesModulo(int number, int moduloNumber) {
        for (int i = 1; i < moduloNumber; i++) {
            if (i * number % moduloNumber == 1) {
                return i;
            }
        }
        return -1;
    }
    
    public static String enkripsiMatriks(int[][] matriks, String wordToByEncrypted) {
        boolean isSingleNumber = false;
        if (wordToByEncrypted.length() % 2 == 1) {
            isSingleNumber = true;
            wordToByEncrypted = wordToByEncrypted.concat("F");
        }
        StringBuilder output = new StringBuilder();
        for (int i = 0; i < wordToByEncrypted.length() - 1; i += 2) {
            char firstChar = wordToByEncrypted.charAt(i);
            char secondChar = wordToByEncrypted.charAt(i + 1);
            int indexOfFirst = WorkString.indexOf(firstChar);
            int indexOfSecond = WorkString.indexOf(secondChar);
            int firstEncrpyted = (indexOfFirst * matriks[0][0] + indexOfSecond * matriks[0][1]);
            int secondEncrypted = (indexOfFirst * matriks[1][0] + indexOfSecond * matriks[1][1]);
            int firstModulo = firstEncrpyted % 84;
            int secondModulo = secondEncrypted % 84;
            String firstEncrpytedString = String.valueOf(WorkString.charAt(firstModulo));
            String secondEncrpytedString = String.valueOf(WorkString.charAt(secondModulo));
            output.append(firstEncrpytedString);
            output.append(secondEncrpytedString);
        }

        if (isSingleNumber){
            return output.toString().substring(0,output.toString().length()-1);
        }
        return output.toString();
    }   
    public static String dekripsiMatriks(int[][] matriks, String wordToBeDecrypted) {
        boolean isSingleNumber = false;
        if(wordToBeDecrypted.length() % 2 == 1){
            isSingleNumber = true;
            wordToBeDecrypted = wordToBeDecrypted.concat("F");

        }
        int det = (matriks[0][0] * matriks[1][1]) - (matriks[0][1]* matriks[1][0]);    
        while(det <= 0){
            det+=84;
        }
        int num = prosesModulo(det,84);
        if(num == -1){
            return "Perkalian matriks tidak benar!";
        }
        StringBuilder output = new StringBuilder();
        for(int i=0;i<wordToBeDecrypted.length();i+=2){
            char firstChar = wordToBeDecrypted.charAt(i);
            char secondChar = wordToBeDecrypted.charAt(i+1);
            int indexOfFirst = WorkString.indexOf(firstChar);
            int indexOfSecond = WorkString.indexOf(secondChar);
            int determinant = (matriks[0][0] * matriks[1][1]) - (matriks[0][1]* matriks[1][0]);
            int inverseOfDet = prosesModulo(determinant,84);
            int[][] adjacentMatriks =  prosesAdjMatriks(matriks);
            adjacentMatriks[0][1] += 84;
            adjacentMatriks[1][0] += 84;
            adjacentMatriks[0][0] = (adjacentMatriks[0][0] * inverseOfDet) % 84;
            adjacentMatriks[0][1] = (adjacentMatriks[0][1] * inverseOfDet) % 84;
            adjacentMatriks[1][0] = (adjacentMatriks[1][0] * inverseOfDet) % 84;
            adjacentMatriks[1][1] = (adjacentMatriks[1][1] * inverseOfDet) % 84;
            int firstValue = (indexOfFirst* adjacentMatriks[0][0] + indexOfSecond * adjacentMatriks[0][1]) % 84;
            int secondValue = (indexOfFirst * adjacentMatriks[1][0] + indexOfSecond * adjacentMatriks[1][1]) %84;
            String firstOutputChar = String.valueOf(WorkString.charAt(firstValue));
            String secondOutputChar = String.valueOf(WorkString.charAt(secondValue));
            output.append(firstOutputChar);
            output.append(secondOutputChar);
        }
        if(isSingleNumber){
            return output.toString().substring(0,output.toString().length()-1);
        }
        return output.toString();
    }
    
    public static void main(String[] args) {
        int[][] matrik = new int[2][2];
        String teks = "";
        ProcessType processType = null;
        try {
            System.out.println("<--- SELAMAT DATANG --->");
            System.out.println("PILIH :\n1.UNTUK ENKRIPSI\n2.UNTUK DEKRIPSI\n");
            System.out.println("MEMILIH : ");
            Scanner choiceScanner = new Scanner(System.in);
            if(choiceScanner.nextInt() == 1){
                processType = ProcessType.ENKRIPSI;
            }
            else{
                processType = ProcessType.DEKRIPSI;
            }
            System.out.println("\n< --- MASUKAN ELEMEN MATRIKS 2x2 UNTUK ENKRIPSI / DEKRIPSI --->.");
            System.out.println("[X][0]\n[0][0]\nMASUKAN ELEMEN : ");
            Scanner scan = new Scanner(System.in);
            int zeroOfZero = scan.nextInt();
            matrik[0][0] = zeroOfZero;
            System.out.println("[0][X]\n[0][0]\nMASUKAN ELEMEN : ");
            int zeroOfOne = scan.nextInt();
            matrik[0][1] = zeroOfOne;
            System.out.println("[0][0]\n[X][0]\nMASUKAN ELEMEN : ");
            int oneOfZero = scan.nextInt();
            matrik[1][0] = oneOfZero;
            System.out.println("[0][0]\n[0][X]\nMASUKAN ELEMEN : ");
            int oneOfOne = scan.nextInt();
            matrik[1][1] = oneOfOne;
            if(processType == ProcessType.ENKRIPSI) {
                System.out.println("\nSEDANG PROSES ENKRIPSI D://input.txt ... ");
            } else{
                System.out.println("\nMASUKAN TEKS UNTUK DI DEKRIPSI: ");
            }
            File file = new File("D:\\input.txt");
       
            Scanner scans = new Scanner(file);
        
            if(scan.hasNextLine()){
                String getDataString = scans.nextLine();
                if(ProcessType.ENKRIPSI.equals(processType)) {
                    String encryptedMessage = enkripsiMatriks(matrik, getDataString);
                    System.out.println("\nHASIL ENKRIPSI :" + encryptedMessage);
                }
                else{
                    Scanner scanner = new Scanner(System.in);
                    teks = scanner.nextLine();
                    String decryptedMessage = dekripsiMatriks(matrik, teks);
                    System.out.println("\nHASIL DEKRIPSI : " + decryptedMessage);
                }
            }                      
           
        } catch (FileNotFoundException ex) {
            System.out.println("FILE TIDAK DITEMUKAN DI \"D://input.txt\" / KESALAHAN DALAM MENGISI!");
        }               
    }
    
    public enum ProcessType {
    ENKRIPSI("ENKRIPSI",1),DEKRIPSI("DEKRIPSI",2);

    private final String key;
    private final Integer value;

    ProcessType(String key, Integer value){
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }
}

}

