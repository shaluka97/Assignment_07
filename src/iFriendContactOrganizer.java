import org.jetbrains.annotations.NotNull;
//this is a test
import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.*;

class CustomerList {
    private Customer first;

    public int length() {
        int count = 0;
        Customer temp = first;
        while (temp != null) {
            count++;
            temp = temp.next;
        }
        return count;
    }

    private boolean isValidIndex(int index) {
        return index >= 0 && index <= length();
    }

    private boolean isempty() {
        return first == null;
    }

    public void add(Customer customer) {
        Customer Newcustomer = new Customer(customer);
        if (first == null) {
            first = Newcustomer;
        } else {
            Customer lastcutomer = first;
            while (lastcutomer.next != null) {
                lastcutomer = lastcutomer.next;
            }
            lastcutomer.next = Newcustomer;
        }
    }

    public void add(Customer customer, int index) {
        Customer Newcustomer = new Customer(customer);
        if (isValidIndex(index)) {
            if (index == 0) {
                Newcustomer.next = first;
                first = Newcustomer;
            } else {
                Customer temp = first;
                for (int i = 1; i < index; i++) {
                    temp = temp.next;
                }
                Newcustomer.next = temp.next;
                temp.next = Newcustomer;
            }
        }
    }

    public int indexof(String searchstring) {
        Customer temp = first;
        int index = -1;
        while (temp != null) {
            index++;
            if (Objects.equals(temp.name, searchstring)) {
                return index;
            }
            if (Objects.equals(temp.phone, searchstring)) {
                return index;
            }
            temp = temp.next;
        }
        return -1;
    }

    public void set(String val, int index, int type) {
        if (isValidIndex(index)) {
            Customer temp = get(index);
            switch (type) {
                case 1:
                    temp.name = val;
                    break;
                case 2:
                    temp.phone = val;
                    break;
                case 3:
                    temp.company = val;
                    break;
            }
            add(temp, index);
        }
    }

    public void set(double salary, int index) {
        if (isValidIndex(index)) {
            Customer temp = get(index);
            temp.salary = salary;
            add(temp, index);
        }
    }

    public void delete(int index) {
        if (isValidIndex(index)) {
            if (index == 0) {
                first = first.next;
            } else {
                Customer temp = first;
                for (int i = 0; i < index - 1; i++) {
                    temp = temp.next;
                }
                temp.next = temp.next.next;
            }
        }
    }

    public Customer get(int index) {
        if (isValidIndex(index)) {
            if (index == 0) {
                return first;
            } else {
                Customer temp = first;
                for (int i = 0; i < index; i++) {
                    temp = temp.next;
                }
                return temp;
            }
        }
        return null;
    }

    public CustomerList sort(int type) {
        Customer temp = first;
        CustomerList temparray = new CustomerList();
        while (temp != null) {
            temparray.add(temp);
            temp = temp.next;
        }
        switch (type) {
            case 1://Sort by name
                for (int i = temparray.length() - 1; i > 0; i--) {
                    for (int j = 0; j < i; j++) {
                        if (temparray.get(j).name.compareTo(temparray.get(j + 1).name) > 0) {
                            temp = temparray.get(j + 1);
                            temparray.add(temparray.get(j), j + 1);
                            temparray.add(temp, j);
                        }
                    }
                }
                return temparray;
            case 2://Sort by salary
                for (int i = temparray.length() - 1; i > 0; i--) {
                    for (int j = 0; j < i; j++) {
                        if (temparray.get(j).salary < temparray.get(j + 1).salary) {
                            temp = temparray.get(j + 1);
                            temparray.add(temparray.get(j), j + 1);
                            temparray.add(temp, j);
                        }

                    }
                }
                return temparray;
            case 3://Sort by dob
                for (int i = temparray.length() - 1; i > 0; i--) {
                    for (int j = 0; j < i; j++) {
                        if (temparray.get(j).dob.compareTo(temparray.get(j + 1).dob) > 0) {
                            temp = temparray.get(j + 1);
                            temparray.add(temparray.get(j), j + 1);
                            temparray.add(temp, j);
                        }
                    }
                }
                return temparray;
        }
        return null;
    }


}

class Customer {
    public Customer next;
    public String id;
    public String name;
    public String phone;
    public String company;
    public String dob;
    public double salary;

    Customer(Customer defaultCustomer) {
        this.next = defaultCustomer.next;
        this.id = defaultCustomer.id;
        this.name = defaultCustomer.name;
        this.phone = defaultCustomer.phone;
        this.company = defaultCustomer.company;
        this.dob = defaultCustomer.dob;
        this.salary = defaultCustomer.salary;
    }

    Customer() {
    }
}

public class iFriendContactOrganizer {

    public static String getuserinput(String msg) {
        Scanner input = new Scanner(System.in);
        System.out.print(msg);
        return input.nextLine();
    }

    public static boolean isphonenumber(String phone) {
        if (phone.charAt(0) == '0' & phone.length() == 10) {
            for (int i = 1; i < phone.length(); i++) {
                if (Character.isDigit(phone.charAt(i))) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean issalary(double salary) {
        return salary > 0;
    }

    public static boolean isdob(String dob) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        formatter.format(date);
        return dob.charAt(4) == '-' & dob.charAt(7) == '-' & dob.compareTo(formatter.format(date)) < 0;

    }

    public static String uniqueid(CustomerList ar) {
        if (ar.length() == 0 || ar.get(0) == null) {
            return "C0001";
        }
        int num = Integer.parseInt(ar.get(ar.length() - 1).id.substring(1));
        return String.format("C%04d", num + 1);
    }

    public static void printarray(CustomerList ar) {
        System.out.println("Contact ID\t\tName\t\tPhone Number\t\tCompany\t\tSalary\t\tBirthday");
        for (int i = 0; i < ar.length(); i++) {
            System.out.println(ar.get(i).id + "\t\t" + ar.get(i).name + "\t\t" + ar.get(i).phone + "\t\t" + ar.get(i).company + "\t\t" + ar.get(i).salary + "\t\t" + ar.get(i).dob);
        }
    }

    public static void clearConsole() {
        try {
            final String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2j");
                System.out.flush();
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    public static void clearlines(int N) {
        // Move the cursor up five lines
        System.out.print("\033[" + N + "A");
        // Clear the lines
        System.out.print("\033[0J");
    }

    public static boolean exitop(String x) {
        Scanner input = new Scanner(System.in);
        System.out.print("Do you want to " + x + " (Y/N) : ");
        char Exitop = input.next().charAt(0);
        return Exitop == 'Y' || Exitop == 'y';
    }

    public static void addcontact(CustomerList x) {
        Outer:
        while (true) {
            Customer temp = new Customer();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            formatter.format(date);
            System.out.println();
            System.out.println("+---------------------------------------------------------------+");
            System.out.println("|\t\t\t\t\tADD Contacts to the list \t\t\t\t\t|");
            System.out.println("+---------------------------------------------------------------+");
            System.out.println();
            temp.id = uniqueid(x);
            System.out.println(temp.id);
            System.out.println("=================");
            System.out.println();
            temp.name = getuserinput("Name\t\t\t: ");
            while (true) {
                temp.phone = getuserinput("phone\t\t\t: ");
                if (isphonenumber(temp.phone)) {
                    break;
                }
                System.out.println();
                System.out.println("\t Invalid Phone number...");
                System.out.println();
                if (exitop("add Phone Number again")) {
                    clearlines(5);
                } else {
                    clearConsole();
                    break Outer;
                }

            }
            temp.company = getuserinput("Company\t\t\t: ");
            while (true) {
                temp.salary = Double.parseDouble(getuserinput("Salary\t\t\t: "));
                if (issalary(temp.salary)) {
                    break;
                }
                System.out.println();
                System.out.println("\t Invalid salary...");
                System.out.println();
                if (exitop("add salary again")) {
                    clearlines(5);
                } else {
                    clearConsole();
                    break Outer;
                }

            }
            while (true) {
                temp.dob = getuserinput("B'Day(YYYY-MM-DD)\t\t\t: ");
                if (isdob(temp.dob)) {
                    break;
                }
                System.out.println();
                System.out.println("\t Invalid Birthday...");
                System.out.println();
                if (exitop("add Birthday again")) {
                    clearlines(5);
                } else {
                    clearConsole();
                    break Outer;
                }

            }
            x.add(temp);
            System.out.println();
            if (!exitop("add another contact")) {
                clearConsole();
                break;
            }
            clearConsole();
        }
    }

    public static void updatecontact(CustomerList x) {
        int index;
        String msg;
        double sal;
        Outer:
        while (true) {
            System.out.println();
            System.out.println("+---------------------------------------------------------------+");
            System.out.println("|\t\t\t\t\tUPDATE Contact \t\t\t\t\t|");
            System.out.println("+---------------------------------------------------------------+");
            System.out.println();
            index = x.indexof(getuserinput("Search Contact by Name or Phone Number - "));
            Customer temp = x.get(index);
            if (temp == null) {
                System.out.println("\t No Contact found ...  ");
                System.out.println();
                if (!exitop("Search another contact")) {
                    clearConsole();
                    break;
                }
                clearConsole();
                continue;
            }
            System.out.println();
            System.out.println("Contact ID\t\t\t: " + temp.id);
            System.out.println("Name\t\t\t: " + temp.name);
            System.out.println("Phone Number\t\t: " + temp.phone);
            System.out.println("Company name\t\t: " + temp.company);
            System.out.println("Salary\t\t\t: " + temp.salary);
            System.out.println("B'Day(YYYY-MM-DD)\t: " + temp.dob);
            System.out.println();
            Inner:
            while (true) {
                System.out.println();
                System.out.println("What do you want to update...");
                System.out.println("\t[1] Name");
                System.out.println("\t[2] Phone Number");
                System.out.println("\t[3] Company Name");
                System.out.println("\t[4] Salary");
                System.out.println();
                switch (Integer.parseInt(getuserinput("Enter an option to continue : "))) {
                    case 1:
                        clearlines(5);
                        System.out.println("Update Name");
                        System.out.println("============");
                        x.set(getuserinput("Input new name - "), index, 1);
                        break;
                    case 2:
                        clearlines(5);
                        System.out.println("Update Phone Number");
                        System.out.println("===================");
                        if (isphonenumber(msg = getuserinput("Input new Phone Number - "))) {
                            x.set(msg, index, 2);
                            break Inner;
                        } else {
                            clearlines(6);
                            System.out.println("Invalid Phone Number");
                            continue;
                        }
                    case 3:
                        clearlines(5);
                        System.out.println("Update Company Name");
                        System.out.println("===================");
                        x.set(getuserinput("Input new Company Name - "), index, 3);
                        break;
                    case 4:
                        clearlines(5);
                        System.out.println("Update Salary");
                        System.out.println("===================");
                        if (issalary(sal = Double.parseDouble(getuserinput("Input new Salary - ")))) {
                            x.set(sal, index);
                            break Inner;
                        } else {
                            clearlines(6);
                            System.out.println("Invalid Salary");
                        }
                }
            }
            System.out.println();
            System.out.println("Contact has been update successfully...");
            System.out.println();
            if (!exitop("update another contact")) {
                clearConsole();
                break;
            }
            clearConsole();
        }
    }

    public static void searchcontact(CustomerList x) {
        int index;
        Outer:
        while (true) {
            System.out.println();
            System.out.println("+---------------------------------------------------------------+");
            System.out.println("|\t\t\t\t\tSEARCH Contacts \t\t\t\t\t|");
            System.out.println("+---------------------------------------------------------------+");
            System.out.println();
            System.out.println();
            index = x.indexof(getuserinput("Search Contact by Name or Phone Number - "));
            Customer temp = x.get(index);
            if (temp == null) {
                System.out.println("\t No Contact found ...  ");
                System.out.println();
                if (!exitop("Search another contact")) {
                    clearConsole();
                    break;
                }
                clearConsole();
                continue;
            }
            System.out.println();
            System.out.println("Contact ID\t\t\t: " + temp.id);
            System.out.println("Name\t\t\t: " + temp.name);
            System.out.println("Phone Number\t\t: " + temp.phone);
            System.out.println("Company name\t\t: " + temp.company);
            System.out.println("Salary\t\t\t: " + temp.salary);
            System.out.println("B'Day(YYYY-MM-DD)\t: " + temp.dob);
            System.out.println();

        }

    }

    public static void deletecontact(CustomerList x) {
        int index;
        while (true) {
            System.out.println();
            System.out.println("+---------------------------------------------------------------+");
            System.out.println("|\t\t\t\t\tDELETE Contact \t\t\t\t\t|");
            System.out.println("+---------------------------------------------------------------+");
            System.out.println();
            index = x.indexof(getuserinput("Search Contact by Name or Phone Number - "));
            Customer temp = x.get(index);
            if (temp == null) {
                clearConsole();
                continue;
            }
            System.out.println();
            System.out.println("Contact ID\t\t\t: " + temp.id);
            System.out.println("Name\t\t\t: " + temp.name);
            System.out.println("Phone Number\t\t: " + temp.phone);
            System.out.println("Company name\t\t: " + temp.company);
            System.out.println("Salary\t\t\t: " + temp.salary);
            System.out.println("B'Day(YYYY-MM-DD)\t: " + temp.dob);
            System.out.println();
            if (exitop("delete this Contact")) {
                x.delete(index);
                System.out.println();
                System.out.println("\tCustomer has been deleted sucessfully...");
                System.out.println();
            }
            if (exitop("delete another Contact")) {
                clearConsole();
            } else {
                break;
            }
        }

    }

    public static void listcontact(CustomerList x) {
        CustomerList temp;
        Outer:
        do {
            System.out.println();
            System.out.println("+---------------------------------------------------------------+");
            System.out.println("|\t\t\t\t\tSORT Contacts \t\t\t\t\t|");
            System.out.println("+---------------------------------------------------------------+");
            System.out.println();
            System.out.println();
            System.out.println("\t[1] Sorting by Name");
            System.out.println("\t[2] Sorting by Salary");
            System.out.println("\t[3] Sorting by Birthday");
            System.out.println();
            switch (Integer.parseInt(getuserinput("Enter an Option to continue ->"))) {
                case 1:
                    System.out.println();
                    System.out.println("\t\t+---------------------------------------------------------------+");
                    System.out.println("\t\t|\t\t\t\t\tLIST Contact by Name \t\t\t\t\t|");
                    System.out.println("\t\t+---------------------------------------------------------------+");
                    System.out.println();
                    System.out.println();
                    temp = x.sort(1);
                    System.out.println();
                    printarray(temp);
                    break;
                case 2:
                    System.out.println();
                    System.out.println("\t\t+---------------------------------------------------------------+");
                    System.out.println("\t\t|\t\t\t\t\tLIST Contact by Salary \t\t\t\t\t|");
                    System.out.println("\t\t+---------------------------------------------------------------+");
                    System.out.println();
                    System.out.println();
                    temp = x.sort(2);
                    System.out.println();
                    printarray(temp);
                    break;
                case 3:
                    System.out.println();
                    System.out.println("\t\t+---------------------------------------------------------------+");
                    System.out.println("\t\t|\t\t\t\t\tLIST Contact by Birthday \t\t\t\t\t|");
                    System.out.println("\t\t+---------------------------------------------------------------+");
                    System.out.println();
                    System.out.println();
                    temp = x.sort(3);
                    System.out.println();
                    printarray(temp);
                    break;
            }
            System.out.println();

        } while (!exitop("go to Home page"));


    }

    public static void main(String[] args) {
        CustomerList templist1 = new CustomerList();
        CustomerList templist2 = new CustomerList();
        Customer temp3 = new Customer();

        String[] id = {"C0001", "C0002", "C0003", "C0004", "C0005"};
        String[] name = {"shaluka", "bhanuka", "chanuka", "amith", "perera"};
        String[] phone = {"0758878585", "0728566742", "0758899333", "0658899331", "0728878585"};
        String[] company = {"LSEG", "SYSCO", "WSO2", "SYNOPSYS", "MIT"};
        String[] dob = {"1997-04-04", "1997-05-05", "1994-04-01", "1991-03-03", "1996-05-05"};
        double[] salary = {750000.00, 850000.00, 150000.00, 2000000.00, 50000.00};

        for (int i = 0; i < id.length; i++) {
            temp3.id = id[i];
            temp3.name = name[i];
            temp3.phone = phone[i];
            temp3.company = company[i];
            temp3.dob = dob[i];
            temp3.salary = salary[i];
            templist2.add(temp3);
        }

        Outer:
        while (true) {
            System.out.println();
            System.out.println("=================================================================================");
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println("--------------------------- iFRIEND - Contact Organizer--------------------------");
            System.out.println("---------------------------------------------------------------------------------");
            System.out.println("=================================================================================");
            System.out.println();
            System.out.println("		[1] Add Contacts");
            System.out.println("		[2] Update Contacts");
            System.out.println("		[3] Delete Contacts");
            System.out.println("		[4] SEARCH Contacts");
            System.out.println("		[5] LIST Contacts");
            System.out.println("		[6] Exit");
            System.out.println();
            switch (Integer.parseInt(getuserinput("Enter an Option to continue ->"))) {
                case 1:
                    clearConsole();
                    addcontact(templist1);
                    break;
                case 2:
                    clearConsole();
                    updatecontact(templist2);
                    break;
                case 3:
                    clearConsole();
                    deletecontact(templist1);
                    break;
                case 4:
                    clearConsole();
                    searchcontact(templist1);
                    break;
                case 5:
                    clearConsole();
                    listcontact(templist2);
                    break;
                case 6:
                    System.out.println("Exiting the Programme");
                    break Outer;
                default:
                    System.out.println("Invalid Option");
                    continue;
            }

        }

    }
}