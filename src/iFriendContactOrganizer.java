import org.jetbrains.annotations.NotNull;

import java.sql.SQLOutput;
import java.text.SimpleDateFormat;
import java.util.*;

class valarray {
    private val[] objarray;
    private int nextIndex = 0;
    private final int loadfac;

    valarray(int size, int loadfac) {
        this.loadfac = loadfac;
        objarray = new val[size];
    }

    public int length() {
        return nextIndex;
    }

    public void add(val contact, int index) {
        if (nextIndex >= objarray.length) {
            extendArray();
        }
        objarray[index] = contact;
    }

    public void add(val contact) {
        if (nextIndex >= objarray.length) {
            extendArray();
        }
        objarray[nextIndex++] = contact;
    }

    public int search(String val) {
        if (val != null && objarray[0] != null) {
            for (int i = 0; i < nextIndex; i++) {
                if (Objects.equals(objarray[i].name, val)) {
                    return i;
                }
                if (Objects.equals(objarray[i].phone, val)) {
                    return i;
                }
            }
        }
        return -1;
    }

//    public int search(double val) {
//        for (int i = 0; i < objarray.length; i++) {
//            if (objarray[i].salary == val) {
//                return i;
//            }
//        }
//        return -1;
//    }

    public void set(String val, int index, int type) {
        if (index >= 0 && index <= nextIndex) {
            switch (type) {
                case 1:
                    objarray[index].name = val;
                    break;
                case 2:
                    objarray[index].phone = val;
                    break;
                case 3:
                    objarray[index].company = val;
                    break;
            }
        }
    }

    public void set(double val, int index) {
        if (index >= 0 && index <= nextIndex) {
            objarray[index].salary = val;
        }
    }

    public void delete(int index) {
        if (index >= 0 && index <= nextIndex) {
            for (int i = index; i < nextIndex - 1; i++) {
                objarray[i] = objarray[i + 1];
            }
            nextIndex--;
        }
    }

    public val get(int index) {
        if (index >= 0 && index <= nextIndex) {
            return objarray[index];
        }
        return null;
    }

    public valarray sort(int type) {
        val temp;
        valarray temparray = new valarray(nextIndex, loadfac);
        for (int i = 0; i < nextIndex; i++) {
            temparray.add(objarray[i]);
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

    private void extendArray() {
        val[] exarray = new val[objarray.length + loadfac];
        for (int i = 0; i < nextIndex; i++) {
            exarray[i] = objarray[i];
        }
        objarray = exarray;
    }

}

class val {
    public String id;
    public String name;
    public String phone;
    public String company;
    public String dob;
    public double salary;

    val(String id, String name, String phone, String company, String dob, double salary) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.company = company;
        this.dob = dob;
        this.salary = salary;

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

    public static String uniqueid(valarray ar) {
        if (ar.length() == 0 || ar.get(0) == null) {
            return "C0001";
        }
        int num = Integer.parseInt(ar.get(ar.length() - 1).id.substring(1));
        return String.format("C%04d", num + 1);
    }

    public static void printarray(valarray ar) {
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

    public static void addcontact(valarray x) {
        Outer:
        while (true) {
            val temp = new val("null", "null", "null", "null", "null", 0.0);
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

    public static void updatecontact(valarray x) {
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
            index = x.search(getuserinput("Search Contact by Name or Phone Number - "));
            val temp = x.get(index);
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

    public static void searchcontact(valarray x) {
        int index;
        Outer:
        while (true) {
            System.out.println();
            System.out.println("+---------------------------------------------------------------+");
            System.out.println("|\t\t\t\t\tSEARCH Contacts \t\t\t\t\t|");
            System.out.println("+---------------------------------------------------------------+");
            System.out.println();
            System.out.println();
            index = x.search(getuserinput("Search Contact by Name or Phone Number - "));
            val temp = x.get(index);
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

    public static void deletecontact(valarray x) {
        int index;
        while (true) {
            System.out.println();
            System.out.println("+---------------------------------------------------------------+");
            System.out.println("|\t\t\t\t\tDELETE Contact \t\t\t\t\t|");
            System.out.println("+---------------------------------------------------------------+");
            System.out.println();
            index = x.search(getuserinput("Search Contact by Name or Phone Number - "));
            val temp = x.get(index);
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

    public static void listcontact(valarray x) {
        valarray temp;
        Outer:
        while (true) {
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
            if(exitop("go to Home page")){
                break;
            }

        }


    }

    public static void main(String[] args) {
        valarray temp1 = new valarray(100, 50);
        valarray temp2 = new valarray(100, 50);
        val temp3;

        String[] id = {"C0001", "C0002", "C0003", "C0004", "C0005"};
        String[] name = {"shaluka", "bhanuka", "chanuka", "amith", "perera"};
        String[] phone = {"0758878585", "0728566742", "0758899333", "0658899331", "0728878585"};
        String[] company = {"LSEG", "SYSCO", "WSO2", "SYNOPSYS", "MIT"};
        String[] dob = {"1997-04-04", "1997-05-05", "1994-04-01", "1991-03-03", "1996-05-05"};
        double[] salary = {750000.00, 850000.00, 150000.00, 2000000.00, 50000.00};


//        for (int i = 0; i < id.length; i++) {
//            temp2.add(id[i], name[i], phone[i], company[i], dob[i], salary[i]);
//        }

        for (int i = 0; i < id.length; i++) {
            temp3 = new val(id[i], name[i], phone[i], company[i], dob[i], salary[i]);
            temp2.add(temp3);
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
                    addcontact(temp1);
                    break;
                case 2:
                    clearConsole();
                    updatecontact(temp2);
                    break;
                case 3:
                    clearConsole();
                    deletecontact(temp1);
                    break;
                case 4:
                    clearConsole();
                    searchcontact(temp1);
                    break;
                case 5:
                    clearConsole();
                    listcontact(temp2);
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