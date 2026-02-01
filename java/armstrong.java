import java.util.Scanner;

public class armstrong {
    public static void main(String[] args) {
     Scanner nerd= new Scanner(System.in);
     char choice;
     
    do{
    System.out.print("Enter number to verify if is armstrong: ");
      int number = Integer.parseInt(nerd.nextLine());
     int temp=number;
     int digits=0,sum=0;
     
      
     while (temp >0) {
        temp=temp/10;
        digits++;        
     }

     temp=number;

     while (temp>0){
        int lastDigit= temp%10;
        sum= sum +(int)Math.pow(lastDigit,digits);
        temp=temp/10; 
     }

     if (sum == number)
    System.out.println(number + " is an Armstrong number.");
    else
    System.out.println(number + " is not an Armstrong number.");
System.out.print("DO you want to chek another number [Y/N]: ");
choice=nerd.nextLine().charAt(0);

    }
    while(choice == 'Y' || choice == 'y');

            nerd.close();
            System.out.println("Program ended.");

    }  

}