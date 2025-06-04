import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Member member = new Member(null);
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("*******FITNESS MEMBERSHIP********");
            System.out.println("1. register ");
            System.out.println("2. renew ");
            System.out.println("3. change plan  ");
            System.out.println("4. show card number ");
            System.out.println("5. show duration");
            System.out.println("6. exit");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Vip or Regular");
                    String choiceString = scanner.nextLine();
                    if(choiceString.equalsIgnoreCase("vip")){
                        member.vipRegister();
                    }
                    if(choiceString.equalsIgnoreCase("regular")){
                        member.regularRegister();
                    }
                    else{
                        System.out.println("error");
                    }
                    break;
                case 2:
                    member.renew();
                    System.out.println("successfully renewed");
                    break;
                case 3:
                    member.changePlan();
                    System.out.println("plan changed successfully "+ member.displayInfo());
                    break;
                case 4:
                    System.out.println(member.displayInfo());
                    break;
                case 5:
                    System.out.println(member.displayDuration());
                    break;
                case 6:
                    break;
                default:
                    break;
            }
            break;
        }
        scanner.close();
    }
}
