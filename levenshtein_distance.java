import java.util.Scanner;
import java.io.*;
import static java.lang.System.exit;
import java.util.*;

class Instruction 
{
   String operation;
   int position, position2;
   char character; 
   
   Instruction(String op, int pos, char ca)
   {
       operation=op;
       position=pos;
       character=ca;
   }
   
   Instruction(String op, int pos, int pos2, char ca)
   {
       operation=op;
       position=pos;
       position2=pos2;
       character=ca;
   }
    public Instruction( Instruction right )
    {
        this.operation = right.operation;
        this.position=right.position;
        this.character=right.character;
    }
}
public class levenshtein_distance
{
    public static ArrayList<Instruction> cloneList(ArrayList<Instruction> list) throws CloneNotSupportedException 
    {
        ArrayList<Instruction> clone = new ArrayList<>(list.size());
        list.forEach((item) -> {
            clone.add(new Instruction(item));
        });
        return clone;
    }
    public static StringBuilder manipulateString(StringBuilder sb, String operation, int position, char c) 
    {
        if (null != operation)
        switch (operation) {
            case "delete":
                sb.deleteCharAt(position);
                break;
            case "replace":
                sb.setCharAt(position, c);
                break;
            case "insert":
                sb.insert(position-1, Character.toString(c));
                break;
            default:
                break;
        }
        return sb;
    }
    public static void printInstructions(ArrayList<ArrayList<Instruction>> instructionsList, char[]str1, char[]str2) 
    {
        for(int i=instructionsList.size()-1;i>=0;i--)
        {
            StringBuilder sb = new StringBuilder();
            sb.append(str1);
            System.out.print(str1);
            int deletes=0, inserts=0, replaces=0; 
            for(int j = instructionsList.get(i).size() - 1; j >= 0; j--)
            {      
                Instruction ins = instructionsList.get(i).get(j);
                int position = ins.position;
                if (null != ins.operation)
                switch (ins.operation) {
                    case "insert":
                        inserts++;
                        position += inserts - deletes;
                        System.out.print(" insert " + ins.character + " -> " );
                        break;
                    case "delete":
                        position += inserts - deletes;
                        deletes++;
                        System.out.print(" delete " + str1[ins.position] + " -> " );
                        break;
                    case "replace":
                        position += inserts - deletes;
                        replaces++;
                        System.out.print(" replace " + str1[ins.position]+ " by "+ ins.character + " -> " );
                        break;
                    default:
                        break;
                }
                sb = manipulateString(sb,ins.operation,position,ins.character);
                System.out.print(sb );
            }
           System.out.println();
        }
    }
    static int counter=0;
    static ArrayList<ArrayList<Instruction>> instructionsList = new ArrayList<ArrayList<Instruction>>();
    
    public static ArrayList<ArrayList<Instruction>> printseq(StringBuilder sb, int T[][],int i,int j, char[]str1, char[]str2,ArrayList<Instruction> instructions, String[] sbval) throws CloneNotSupportedException {
    while(true) 
    {
        if(i==0 && j==0)
        {
            counter++; 
            instructionsList.add(instructions);
            sb.setLength(0);
            sb.append(str1);
            break;
        }
        if (i == 0 || j == 0) 
        {
            if(i==0)
            {   
                instructions.add(new Instruction("insert",i,str2[j-1]));
                j=j-1;
            }
            else if(j==0)
            {
                instructions.add(new Instruction("delete",i-1,Character.MIN_VALUE));   
                i=i-1;
            }
            sb.setLength(0);
            sb.append(str1);
            sbval=null; 
        }
        else if (i >0 && j > 0 && str1[i-1] == str2[j-1]) 
        {
            i = i-1;
            j = j-1;       
        } 
        else
        {   
            int minValue = min(T[i-1][j-1], T[i][j-1], T[i-1][j]);
            if (T[i-1][j-1]==minValue)
            {
                ArrayList<Instruction> newins = cloneList(instructions);
                newins.add(new Instruction("replace",i-1,j-1, str2[j-1]));
                printseq(sb, T,i-1,j-1,str1,str2,newins,sbval);
            }   
            if(T[i-1][j] == minValue)
            {
                ArrayList<Instruction> newins = cloneList(instructions);
                newins.add(new Instruction("delete",i-1,Character.MIN_VALUE));
                printseq(sb,T,i-1,j,str1,str2,newins,sbval);
            } 
            if(T[i][j-1]==minValue)
            {
                ArrayList<Instruction> newins = cloneList(instructions);
                newins.add(new Instruction("insert",i,str2[j-1]));
                printseq(sb,T,i,j-1,str1,str2,newins,sbval);     
            }
            break;
         }           
       
     }
     return instructionsList;    
 }
 private static int min(int a,int b,int c)
 {
    return Math.min(Math.min(a, b), c);
 }
 public static int levenshtein_distance(String a, String b, int l1, int l2) throws CloneNotSupportedException                
 {
                    System.out.print("0");

     int i,j; 
    int matrix[][]=new int[l1+1][l2+1];
    int cost;
    char valuea;
    char valueb;
    ArrayList<Instruction> instructions = new ArrayList<>();
    
    for (i = 0; i <= l1; i++)
    {                System.out.print("1");

        matrix[i][0] = i;
    }
    for (j = 0; j <= l2; j++) {
                    System.out.print("2");

            matrix[0][j] = j;
    }
     for (int l=0;l<=l2;l++)
            {
                System.out.print("1`312412");
                for(int m=0;m<=l1;m++)
                {
                    System.out.print(matrix[m][l]);
                }
                System.out.print("\n");
            }
    for (i=1;i<=l1;i++)
    {
        valuea=a.charAt(i-1);   
        for(j=1;j<=l2;j++)
        {
            valueb=b.charAt(j-1);      
            if (a.charAt(i-1) == b.charAt(j-1))
            {
                cost=0;
            }
            else
            {
                cost=1;
            }
            matrix[i][j] = min (matrix[i-1][j]+1, matrix[i][j-1]+1, matrix[i-1][j-1] + cost);
        }
        
    }
    i=matrix.length-1;
    j=matrix[0].length-1;
   
    
    StringBuilder sb = new StringBuilder();
    sb.append(a); 
    String[] sbarray = new String[20];
    instructionsList = printseq(sb, matrix,i,j,a.toCharArray(),b.toCharArray(),instructions, sbarray);
    if (counter>1)
    {
        System.out.println("\nThere are total of "+counter+" sequences");
    }
    else
    {
        System.out.println("\nThere is  a total of "+counter+" sequence");
    }
    printInstructions(instructionsList, a.toCharArray(),b.toCharArray()); 
    return 0;
    
 }    
 public static void main(String args[]) throws CloneNotSupportedException, IOException
 {
    Scanner  strings = new Scanner(System.in);
    String a1=strings.nextLine();
    String a2=strings.nextLine();

    int len1=a1.length();
    int len2=a2.length();     
    levenshtein_distance(a1,a2,len1,len2);   
    }
 }